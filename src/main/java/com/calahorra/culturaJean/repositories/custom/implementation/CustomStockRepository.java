package com.calahorra.culturaJean.repositories.custom.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Stock;
import com.calahorra.culturaJean.repositories.custom.ICustomStockRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

///Clase CustomStockRepository:
@Repository("customStockRepository")
public class CustomStockRepository implements ICustomStockRepository 
{
	//Atributo:
    @PersistenceContext
    private EntityManager entityManager;

    //Construimos el texto de la consulta según el principio dado para la misma y el estado de los filtros:
    private StringBuilder buildTextOfQuery(List<String> categories, List<Character> genders, List<String> sizes, List<String> colors, 
    									   Float salePrice, Float fromSalePrice, Float untilSalePrice, Float rangeFromSalePrice, 
    									   Float rangeUntilSalePrice, Integer actualAmount, Integer fromActualAmount, 
    									   Integer untilActualAmount, Integer rangeFromActualAmount, Integer rangeUntilActualAmount, 
    									   Boolean state, String baseQuery) 
    {
    	StringBuilder queryBuilder = new StringBuilder(baseQuery); //Definimos la base de la consulta.
    	
    	//Agregamos la línea de cada filtro que corresponda:
    	if(categories != null) queryBuilder.append(" AND p.category IN :categories");
    	if(genders != null) queryBuilder.append(" AND p.gender IN :genders");
    	if(sizes != null) queryBuilder.append(" AND p.size IN :sizes");
    	if(colors != null) queryBuilder.append(" AND p.color IN :colors");
    	if(salePrice != null) queryBuilder.append(" AND p.sale_price = :salePrice");
    	if(fromSalePrice != null) queryBuilder.append(" AND p.sale_price = :fromSalePrice");
    	if(untilSalePrice != null) queryBuilder.append(" AND p.sale_price = :untilSalePrice");
    	if(rangeFromSalePrice != null) queryBuilder.append(" AND p.sale_price = :rangeFromSalePrice");
    	if(rangeUntilSalePrice != null) queryBuilder.append(" AND p.sale_price = :rangeUntilSalePrice");
    	if(actualAmount != null) queryBuilder.append(" AND s.actual_amount = :actualAmount");
    	if(fromActualAmount != null) queryBuilder.append(" AND s.actual_amount = :fromActualAmount");
    	if(untilActualAmount != null) queryBuilder.append(" AND s.actual_amount = :untilActualAmount");
    	if(rangeFromActualAmount != null) queryBuilder.append(" AND s.actual_amount = :rangeFromActualAmount");
    	if(rangeUntilActualAmount != null) queryBuilder.append(" AND s.actual_amount = :rangeUntilActualAmount");
    	if(state != null) queryBuilder.append(" AND p.enabled = :state");
    	
    	return queryBuilder; //Retornamos el texto de la consulta.
    }
    
    //Cargamos la información de los filtros que correspondan en el objeto Query:
    private Query chargeFiltersDataOnQuery(List<String> categories, List<Character> genders, List<String> sizes, List<String> colors, 
			   Float salePrice, Float fromSalePrice, Float untilSalePrice, Float rangeFromSalePrice, Float rangeUntilSalePrice, 
			   Integer actualAmount, Integer fromActualAmount, Integer untilActualAmount, Integer rangeFromActualAmount, 
			   Integer rangeUntilActualAmount, Boolean state, Query query) 
    {
    	//Cargamos los datos de los filtros que correspondan:
    	if(categories != null) query.setParameter("categories", categories);
    	if(genders != null) query.setParameter("genders", genders);
    	if(sizes != null) query.setParameter("sizes", sizes);
    	if(colors != null) query.setParameter("colors", colors);
    	if(salePrice != null) query.setParameter("salePrice", salePrice);
    	if(fromSalePrice != null) query.setParameter("fromSalePrice", fromSalePrice);
    	if(untilSalePrice != null) query.setParameter("untilSalePrice", untilSalePrice);
    	if(rangeFromSalePrice != null) query.setParameter("rangeFromSalePrice", rangeFromSalePrice);
    	if(rangeUntilSalePrice != null) query.setParameter("rangeUntilSalePrice", rangeUntilSalePrice);
    	if(actualAmount != null) query.setParameter("actualAmount", actualAmount);
    	if(fromActualAmount != null) query.setParameter("fromActualAmount", fromActualAmount);
    	if(untilActualAmount != null) query.setParameter("untilActualAmount", untilActualAmount);
    	if(rangeFromActualAmount != null) query.setParameter("rangeFromActualAmount", rangeFromActualAmount);
    	if(rangeUntilActualAmount != null) query.setParameter("rangeUntilActualAmount", rangeUntilActualAmount);
    	if(state != null) query.setParameter("state", state);
    	
    	return query; //Retornamos el objeto Query con los filtros correspondientes.
    }
    
    //Obtenemos las opciones de filtros según los stocks que cumplen con los filtros:
    @Override
    public List<Map<String, Object>> findFiltersOptions(List<String> categories, List<Character> genders, List<String> sizes, 
    													List<String> colors, Float salePrice, Float fromSalePrice, Float untilSalePrice,
    													Float rangeFromSalePrice, Float rangeUntilSalePrice, Integer actualAmount, 
    													Integer fromActualAmount, Integer untilActualAmount, Integer rangeFromActualAmount,
    													Integer rangeUntilActualAmount, Boolean state) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT 
            ARRAY_AGG(DISTINCT p.category)::TEXT[] AS categories,
            ARRAY_AGG(DISTINCT p.gender)::TEXT[] AS genders,
            ARRAY_AGG(DISTINCT p.size)::TEXT[] AS sizes,
            ARRAY_AGG(DISTINCT p.color)::TEXT[] AS colors
	    FROM stock s
	    INNER JOIN product p ON s.product_id = p.product_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice,
    												  rangeFromSalePrice, rangeUntilSalePrice, actualAmount, fromActualAmount,
    												  untilActualAmount, rangeFromActualAmount, rangeUntilActualAmount, state, baseQuery);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Construimos un objeto Query con la consulta definida anteriormente y los datos de los filtros que correspondan:
    	query = chargeFiltersDataOnQuery(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, 
    									 rangeUntilSalePrice, actualAmount, fromActualAmount, untilActualAmount, rangeFromActualAmount, 
    									 rangeUntilActualAmount, state, query);
    	
    	//Obtenemos los resultados de la consulta:
        @SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

        //Mapeamos los resultados a un List<Map<String, Object>> y retornamos el resultado:
        return results.stream().map(result -> 
        {
            Map<String, Object> map = new HashMap<>();
            map.put("categories", result[0]); //Categorías.
            map.put("genders", result[1]); //Géneros.
            map.put("sizes", result[2]); //Talles.
            map.put("colors", result[3]); //Colores.
            return map;
        }).collect(Collectors.toList());
    }
    
    //Obtenemos los stocks que cumplen con los filtros dentro de una página:
    @Override
    public Page<Stock> findFilteredStocks(List<String> categories, List<Character> genders, List<String> sizes, List<String> colors, 
			   							  Float salePrice, Float fromSalePrice, Float untilSalePrice, Float rangeFromSalePrice, 
			   							  Float rangeUntilSalePrice, Integer actualAmount, Integer fromActualAmount, 
			   							  Integer untilActualAmount, Integer rangeFromActualAmount, Integer rangeUntilActualAmount, 
			   							  Boolean state, String sort, Pageable pageable) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT s.*
	    FROM stock s
	    INNER JOIN product p ON s.product_id = p.product_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice,
    												  rangeFromSalePrice, rangeUntilSalePrice, actualAmount, fromActualAmount,
    												  untilActualAmount, rangeFromActualAmount, rangeUntilActualAmount, state, baseQuery);
    	
    	String orderBy = " ORDER BY " + sort; //Construímos dinámicamente el criterio de ordenamiento según lo envíado:
    	
    	queryBuilder.append(orderBy); //Agregamos el criterio de ordenamiento a la consulta.
    	
    	//Instanciamos un objeto Query con la consulta definida y el mapeo de los resultados a la entidad Stock:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString(), Stock.class);
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice,
    									 rangeUntilSalePrice, actualAmount, fromActualAmount, untilActualAmount, rangeFromActualAmount, 
    									 rangeUntilActualAmount, state, query);
    	
		//Agregamos paginación (LIMIT y OFFSET):
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;
		query.setMaxResults(pageSize);
		query.setFirstResult(offset);
		
		//Ejecutamos la consulta y obtenemos el resultado:
		@SuppressWarnings("unchecked")
		List<Stock> stocks = query.getResultList();
		
		//Obtenemos la cantidad de stocks que cumplen con los filtros:
		Long totalStocks = getTotalCount(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice,
										 rangeUntilSalePrice, actualAmount, fromActualAmount, untilActualAmount, rangeFromActualAmount,
										 rangeUntilActualAmount, state);
		
		//Retornamos el paginado con la información adjunta:
		return new PageImpl<>(stocks, pageable, totalStocks);
	}
    
    //Obtenemos la cantidad de stocks que cumplen con los filtros:
    @Override
	public Long getTotalCount(List<String> categories, List<Character> genders, List<String> sizes, List<String> colors, Float salePrice, 
							  Float fromSalePrice, Float untilSalePrice, Float rangeFromSalePrice, Float rangeUntilSalePrice, 
							  Integer actualAmount, Integer fromActualAmount, Integer untilActualAmount, Integer rangeFromActualAmount, 
							  Integer rangeUntilActualAmount, Boolean state) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT COUNT(*)
	    FROM stock s
	    INNER JOIN product p ON s.product_id = p.product_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice,
    												  rangeFromSalePrice, rangeUntilSalePrice, actualAmount, fromActualAmount,
    												  untilActualAmount, rangeFromActualAmount, rangeUntilActualAmount, state, baseQuery);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(categories, genders, sizes, colors, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, 
    									 rangeUntilSalePrice, actualAmount, fromActualAmount, untilActualAmount, rangeFromActualAmount, 
    									 rangeUntilActualAmount, state, query);
    	
		//Retornamos la cantidad de stocks hallada:
		return ((Number) query.getSingleResult()).longValue();
	}
}
