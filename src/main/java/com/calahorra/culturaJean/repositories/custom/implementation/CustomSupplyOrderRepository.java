package com.calahorra.culturaJean.repositories.custom.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.SupplyOrder;
import com.calahorra.culturaJean.repositories.custom.ICustomSupplyOrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

///Clase CustomSupplyOrderRepository:
@Repository("customSupplyOrderRepository")
public class CustomSupplyOrderRepository implements ICustomSupplyOrderRepository 
{
	//Atributo:
    @PersistenceContext
    private EntityManager entityManager;

    //Construimos el texto de la consulta según el principio dado para la misma y el estado de los filtros:
    private StringBuilder buildTextOfQuery(List<String> productCodes, List<String> supplierNames, List<String> adminUsernames, 
    									   Integer amount, Integer fromAmount, Integer untilAmount, Integer rangeFromAmount, 
    									   Integer rangeUntilAmount, Boolean delivered, String baseQuery) 
    {
    	StringBuilder queryBuilder = new StringBuilder(baseQuery); //Definimos la base de la consulta.
    	
    	//Agregamos la línea de cada filtro que corresponda:
    	if(productCodes != null) queryBuilder.append(" AND p.code IN :productCodes");
    	if(supplierNames != null) queryBuilder.append(" AND s.name IN :supplierNames");
    	if(adminUsernames != null) queryBuilder.append(" AND m.username IN :adminUsernames");
    	if(amount != null) queryBuilder.append(" AND so.amount = :amount");
    	if(fromAmount != null) queryBuilder.append(" AND so.amount >= :fromAmount");
    	if(untilAmount != null) queryBuilder.append(" AND so.amount <= :untilAmount");
    	if(rangeFromAmount != null) queryBuilder.append(" AND so.amount >= :rangeFromAmount");
    	if(rangeUntilAmount != null) queryBuilder.append(" AND so.amount <= :rangeUntilAmount");
    	if(delivered != null) queryBuilder.append(" AND so.delivered = :delivered");
    	
    	return queryBuilder; //Retornamos el texto de la consulta.
    }
    
    //Cargamos la información de los filtros que correspondan en el objeto Query:
    private Query chargeFiltersDataOnQuery(List<String> productCodes, List<String> supplierNames, List<String> adminUsernames, 
    									   Integer amount, Integer fromAmount, Integer untilAmount, Integer rangeFromAmount, 
    									   Integer rangeUntilAmount, Boolean delivered, Query query) 
    {
    	//Cargamos los datos de los filtros que correspondan:
    	if(productCodes != null) query.setParameter("productCodes", productCodes);
    	if(supplierNames != null) query.setParameter("supplierNames", supplierNames);
    	if(adminUsernames != null) query.setParameter("adminUsernames", adminUsernames);
    	if(amount != null) query.setParameter("amount", amount);
    	if(fromAmount != null) query.setParameter("fromAmount", fromAmount);
    	if(untilAmount != null) query.setParameter("untilAmount", untilAmount);
    	if(rangeFromAmount != null) query.setParameter("rangeFromAmount", rangeFromAmount);
    	if(rangeUntilAmount != null) query.setParameter("rangeUntilAmount", rangeUntilAmount);
    	if(delivered != null) query.setParameter("delivered", delivered);
    	
    	return query; //Retornamos el objeto Query con los filtros correspondientes.
    }
    
    //Obtenemos las opciones de filtros según los pedidos de aprovisionamiento que cumplen con los filtros:
    @Override
    public List<Map<String, Object>> findFiltersOptions(List<String> productCodes, List<String> supplierNames, List<String> adminUsernames,
    													Integer amount, Integer fromAmount, Integer untilAmount, Integer rangeFromAmount, 
    													Integer rangeUntilAmount, Boolean delivered, String findOptionsQueryBase) 
    {
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount,
    												  rangeFromAmount, rangeUntilAmount, delivered, findOptionsQueryBase);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Construimos un objeto Query con la consulta definida anteriormente y los datos de los filtros que correspondan:
    	query = chargeFiltersDataOnQuery(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount, rangeFromAmount, 
    									 rangeUntilAmount, delivered, query);
    	
    	//Obtenemos los resultados de la consulta:
        @SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

        //Mapeamos los resultados a un List<Map<String, Object>> y retornamos el resultado:
        return results.stream().map(result -> 
        {
            Map<String, Object> map = new HashMap<>();
            map.put("productCodes", result[0]); //Códigos de producto.
            map.put("supplierNames", result[1]); //Nombres de proveedor.
            map.put("adminUsernames", result[2]); //Usernames de administrador.
            return map;
        }).collect(Collectors.toList());
    }
    
    //Obtenemos los pedidos de aprovisionamiento que cumplen con los filtros dentro de una página:
    @Override
    public Page<SupplyOrder> findFilteredSupplyOrders(List<String> productCodes, List<String> supplierNames, List<String> adminUsernames, 
    												  Integer amount, Integer fromAmount, Integer untilAmount, Integer rangeFromAmount, 
    												  Integer rangeUntilAmount, Boolean delivered, String sort, Pageable pageable,
    												  String findSOQueryBase, String countSOQueryBase) 
    {
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount,
				  									  rangeFromAmount, rangeUntilAmount, delivered, findSOQueryBase);
    	
    	String orderBy = " ORDER BY " + sort; //Construímos dinámicamente el criterio de ordenamiento según lo envíado:
    	
    	queryBuilder.append(orderBy); //Agregamos el criterio de ordenamiento a la consulta.
    	
    	//Instanciamos un objeto Query con la consulta definida y el mapeo de los resultados a la entidad SupplyOrder:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString(), SupplyOrder.class);
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount, rangeFromAmount, 
    									 rangeUntilAmount, delivered, query);
    	
		//Agregamos paginación (LIMIT y OFFSET):
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;
		query.setMaxResults(pageSize);
		query.setFirstResult(offset);
		
		//Ejecutamos la consulta y obtenemos el resultado:
		@SuppressWarnings("unchecked")
		List<SupplyOrder> supplyOrders = query.getResultList();
		
		//Obtenemos la cantidad de pedidos de aprovisionamiento que cumplen con los filtros:
		Long totalSupplyOrders = getTotalCount(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount, 
											   rangeFromAmount, rangeUntilAmount, delivered, countSOQueryBase);
		
		//Retornamos el paginado con la información adjunta:
		return new PageImpl<>(supplyOrders, pageable, totalSupplyOrders);
	}
    
    //Obtenemos la cantidad de pedidos de aprovisionamiento que cumplen con los filtros:
    @Override
	public Long getTotalCount(List<String> productCodes, List<String> supplierNames, List<String> adminUsernames, Integer amount, 
							  Integer fromAmount, Integer untilAmount, Integer rangeFromAmount, Integer rangeUntilAmount, 
							  Boolean delivered, String countSOQueryBase) 
    {
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount,
				  									  rangeFromAmount, rangeUntilAmount, delivered, countSOQueryBase);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(productCodes, supplierNames, adminUsernames, amount, fromAmount, untilAmount, rangeFromAmount, 
    									 rangeUntilAmount, delivered, query);
    	
		//Retornamos la cantidad de pedidos de aprovisionamiento hallada:
		return ((Number) query.getSingleResult()).longValue();
	}
}
