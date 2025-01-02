package com.calahorra.culturaJean.repositories.custom.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.PurchaseDTO;
import com.calahorra.culturaJean.dtos.PurchaseItemDTO;
import com.calahorra.culturaJean.entities.Member;
import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.entities.Purchase;
import com.calahorra.culturaJean.repositories.custom.ICustomPurchaseRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

///Clase CustomPurchaseRepository:
@Repository("customPurchaseRepository")
public class CustomPurchaseRepository implements ICustomPurchaseRepository 
{
	//Atributo:
    @PersistenceContext
    private EntityManager entityManager;

    //Construimos el texto de la consulta sobre las compras según el principio dado para la misma y el estado de los filtros
    private StringBuilder buildTextOfQuery(LocalDate date, LocalDate fromDate, LocalDate untilDate, LocalDate rangeFromDate, 
    									   LocalDate rangeUntilDate, LocalTime fromTime, LocalTime untilTime, LocalTime rangeFromTime, 
    									   LocalTime rangeUntilTime, List<String> methodsOfPay, List<String> usernames, Float fromPrice,
    									   Float untilPrice, Float rangeFromPrice, Float rangeUntilPrice, String baseQuery) 
    {
    	StringBuilder queryBuilder = new StringBuilder(baseQuery); //Definimos la base de la consulta.
    	
    	//Agregamos la línea de cada filtro que corresponda:
    	if(date != null) queryBuilder.append(" AND DATE(p.date_time) = :date");
    	if(fromDate != null) queryBuilder.append(" AND DATE(p.date_time) >= :fromDate");
    	if(untilDate != null) queryBuilder.append(" AND DATE(p.date_time) <= :untilDate");
    	if(rangeFromDate != null) queryBuilder.append(" AND DATE(p.date_time) >= :rangeFromDate"); 
    	if(rangeUntilDate != null) queryBuilder.append(" AND DATE(p.date_time) <= :rangeUntilDate");
    	if(fromTime != null) queryBuilder.append(" AND TIME(p.date_time) >= :fromTime");
    	if(untilTime != null) queryBuilder.append(" AND TIME(p.date_time) <= :untilTime");
    	if(rangeFromTime != null) queryBuilder.append(" AND TIME(p.date_time) >= :rangeFromTime");
    	if(rangeUntilTime != null) queryBuilder.append(" AND TIME(p.date_time) <= :rangeUntilTime");
    	if(methodsOfPay != null) queryBuilder.append(" AND p.method_of_pay IN :methodsOfPay");
    	if(usernames != null) queryBuilder.append(" AND m.username IN :usernames");
    	if(fromPrice != null) queryBuilder.append(" AND p.total_price >= :fromPrice");
    	if(untilPrice != null) queryBuilder.append(" AND p.total_price <= :untilPrice");
    	if(rangeFromPrice != null) queryBuilder.append(" AND p.total_price >= :rangeFromPrice");
    	if(rangeUntilPrice != null) queryBuilder.append(" AND p.total_price <= :rangeUntilPrice");
    	
    	return queryBuilder; //Retornamos el texto de la consulta.
    }
    
    //Cargamos la información de los filtros de las compras que correspondan en el objeto Query:
    private Query chargeFiltersDataOnQuery(LocalDate date, LocalDate fromDate, LocalDate untilDate, LocalDate rangeFromDate, 
										   LocalDate rangeUntilDate, LocalTime fromTime, LocalTime untilTime, LocalTime rangeFromTime, 
										   LocalTime rangeUntilTime, List<String> methodsOfPay, List<String> usernames, Float fromPrice, 
										   Float untilPrice, Float rangeFromPrice, Float rangeUntilPrice, Query query) 
    {
    	//Cargamos los datos de los filtros que correspondan:
    	if(date != null) query.setParameter("date", date);
    	if(fromDate != null) query.setParameter("fromDate", fromDate);
    	if(untilDate != null) query.setParameter("untilDate", untilDate);
    	if(rangeFromDate != null) query.setParameter("rangeFromDate", rangeFromDate);
    	if(rangeUntilDate != null) query.setParameter("rangeUntilDate", rangeUntilDate);
    	if(fromTime != null) query.setParameter("fromTime", fromTime);
    	if(untilTime != null) query.setParameter("untilTime", untilTime);
    	if(rangeFromTime != null) query.setParameter("rangeFromTime", rangeFromTime);
    	if(rangeUntilTime != null) query.setParameter("rangeUntilTime", rangeUntilTime);
    	if(methodsOfPay != null) query.setParameter("methodsOfPay", methodsOfPay);
    	if(usernames != null) query.setParameter("usernames", usernames);
    	if(fromPrice != null) query.setParameter("fromPrice", fromPrice);
    	if(untilPrice != null) query.setParameter("untilPrice", untilPrice);
    	if(rangeFromPrice != null) query.setParameter("rangeFromPrice", rangeFromPrice);
    	if(rangeUntilPrice != null) query.setParameter("rangeUntilPrice", rangeUntilPrice);
    	
    	return query; //Retornamos el objeto Query con los filtros correspondientes.
    }
    
    //Obtenemos las opciones de filtros según las compras que cumplen con los filtros:
    @Override
    public List<Map<String, Object>> findFiltersOptions(LocalDate date, LocalDate fromDate, LocalDate untilDate, LocalDate rangeFromDate, 
    													LocalDate rangeUntilDate, LocalTime fromTime, LocalTime untilTime, 
    													LocalTime rangeFromTime, LocalTime rangeUntilTime, List<String> methodsOfPay, 
    													List<String> usernames, Float fromPrice, Float untilPrice, Float rangeFromPrice, 
    													Float rangeUntilPrice) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT 
    		ARRAY_AGG(DISTINCT p.method_of_pay)::TEXT[] AS methodsOfPay,
    		ARRAY_AGG(DISTINCT m.username)::TEXT[] AS usernames
	    FROM purchase p
	    INNER JOIN member m ON p.member_id = m.member_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, 
    												  rangeFromTime, rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice,
    												  rangeFromPrice, rangeUntilPrice, baseQuery);
    	
    	queryBuilder.append(" GROUP BY p.purchase_id"); //Devolvemos una vez cada compra con todos sus ítems asociados.
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Construimos un objeto Query con la consulta definida anteriormente y los datos de los filtros que correspondan:
    	query = chargeFiltersDataOnQuery(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, rangeFromTime, 
    									 rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice, rangeFromPrice, rangeUntilPrice, 
    									 query);
    	
    	//Obtenemos los resultados de la consulta:
        @SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

        //Mapeamos los resultados a un List<Map<String, Object>> y retornamos el resultado:
        return results.stream().map(result -> 
        {
            Map<String, Object> map = new HashMap<>();
            map.put("methodsOfPay", result[0]); //Métodos de pago.
            map.put("usernames", result[1]); //Usernames.
            return map;
        }).collect(Collectors.toList());
    }
    
    //Obtenemos las compras que cumplen con los filtros dentro de una página:
    @Override
    public Page<PurchaseDTO> findFilteredPurchases(LocalDate date, LocalDate fromDate, LocalDate untilDate, LocalDate rangeFromDate, 
    											   LocalDate rangeUntilDate, LocalTime fromTime, LocalTime untilTime, 
    											   LocalTime rangeFromTime, LocalTime rangeUntilTime, List<String> methodsOfPay, 
    											   List<String> usernames, Float fromPrice, Float untilPrice, Float rangeFromPrice, 
    											   Float rangeUntilPrice, String sort, Pageable pageable) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT p.*
	    FROM purchase p
	    INNER JOIN member m ON p.member_id = m.member_id
	    INNER JOIN purchase_item pi ON p.purchase_id = pi.purchase_id
	    INNER JOIN product pr ON pi.product_id = pr.product_id
	    WHERE 1=1
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, 
    												  rangeFromTime, rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice,
    												  rangeFromPrice, rangeUntilPrice, baseQuery);
    	
    	queryBuilder.append(" GROUP BY p.purchase_id"); //Devolvemos una vez cada compra con todos sus ítems asociados.
    	
    	String orderBy = " ORDER BY " + sort; //Construímos dinámicamente el criterio de ordenamiento según lo envíado.
    	
    	queryBuilder.append(orderBy); //Agregamos el criterio de ordenamiento a la consulta.
    	
    	//Instanciamos un objeto Query con la consulta definida y el mapeo de los resultados a la entidad Purchase:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString(), Purchase.class);
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, rangeFromTime, 
    									 rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice, rangeFromPrice, rangeUntilPrice, 
    									 query);
    	
		//Agregamos paginación (LIMIT y OFFSET):
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;
		query.setMaxResults(pageSize);
		query.setFirstResult(offset);
		
		//Ejecutamos la consulta y obtenemos el resultado:
		@SuppressWarnings("unchecked")
		List<Purchase> purchases = query.getResultList();
		
		//Obtenemos la cantidad de compras que cumplen con los filtros:
		Long totalPurchases = getTotalCount(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, rangeFromTime, 
											rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice, rangeFromPrice, 
											rangeUntilPrice);
		
		//Mapeamos las entidades a DTOs:
	    List<PurchaseDTO> purchasesDTOs = purchases.stream().map(p -> 
	    {
	    	PurchaseDTO dto = new PurchaseDTO(); //DTO vacío.
	    	
	    	//Empezamos a pasarle la información:
	        dto.setPurchaseId(p.getPurchaseId());
	        dto.setMethodOfPay(p.getMethodOfPay());
	        dto.setDateTime(p.getDateTime());
	        dto.setTotalPrice(p.getTotalPrice());

	        //Mapear PurchaseItems a PurchaseItemDTO:
	        Set<PurchaseItemDTO> purchaseItemDTOs = p.getPurchaseItems().stream().map(pi -> 
	        {
	        	//Mapeamos el producto asociado al ítem:
	        	Product product = pi.getProduct();
	        	ProductDTO productDTO = new ProductDTO(product.getProductId(), product.getCode(), product.getCategory(), 
	        										   product.getGender(), product.getSize(), product.getColor(), product.getCost(), 
	        										   product.getSalePrice(), product.getName(), product.getDescription(), 
	        										   product.isEnabled(), product.getImageName());
	        	
	        	//Cargamos la información del ítem en su versión DTO:
	            PurchaseItemDTO piDTO = new PurchaseItemDTO();
	            piDTO.setPurchaseItemId(pi.getPurchaseItemId());
	            piDTO.setAmount(pi.getAmount());
	            piDTO.setTotalPrice(pi.getTotalPrice());
	            piDTO.setProduct(productDTO);
	            
	            return piDTO; //Retornamos el ítem.
	        }).collect(Collectors.toSet());
	        
	        //Cargamos los ítems en la compra:
	        dto.setPurchaseItems(purchaseItemDTOs);
	        
	        //Mapeamos el cliente asociado a la compra:
	        Member member = p.getMember();
	    	MemberDTO memberDTO = new MemberDTO(member.getMemberId(), member.getUsername(), member.isEnabled(), member.getName(), 
	    										member.getLastName(), member.getEmail());
	    	
	    	//Cargamos el cliente en la compra:
	    	dto.setMember(memberDTO);
	    	
	        return dto; //Retornamos la compra.
	    }).collect(Collectors.toList());
		
		//Retornamos el paginado con la información adjunta:
		return new PageImpl<>(purchasesDTOs, pageable, totalPurchases);
	}
    
    //Obtenemos la cantidad de compras que cumplen con los filtros:
    @Override
	public Long getTotalCount(LocalDate date, LocalDate fromDate, LocalDate untilDate, LocalDate rangeFromDate, LocalDate rangeUntilDate, 
							  LocalTime fromTime, LocalTime untilTime, LocalTime rangeFromTime, LocalTime rangeUntilTime, 
							  List<String> methodsOfPay, List<String> usernames, Float fromPrice, Float untilPrice, Float rangeFromPrice, 
							  Float rangeUntilPrice) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
	    SELECT COUNT(DISTINCT p.purchase_id)
	    FROM purchase p
	    INNER JOIN member m ON p.member_id = m.member_id
	    WHERE 1=1	
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, 
    												  rangeFromTime, rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice,
    												  rangeFromPrice, rangeUntilPrice, baseQuery);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(date, fromDate, untilDate, rangeFromDate, rangeUntilDate, fromTime, untilTime, rangeFromTime, 
    									 rangeUntilTime, methodsOfPay, usernames, fromPrice, untilPrice, rangeFromPrice, 
    									 rangeUntilPrice, query);
    	
		//Retornamos la cantidad de compras hallada:
		return ((Number) query.getSingleResult()).longValue();
	}
}
