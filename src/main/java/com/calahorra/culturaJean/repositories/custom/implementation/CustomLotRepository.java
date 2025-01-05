package com.calahorra.culturaJean.repositories.custom.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Lot;
import com.calahorra.culturaJean.repositories.custom.ICustomLotRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

///Clase CustomLotRepository:
@Repository("customLotRepository")
public class CustomLotRepository implements ICustomLotRepository 
{
	//Atributo:
    @PersistenceContext
    private EntityManager entityManager;

    //Construimos el texto de la consulta según el principio dado para la misma y el estado de los filtros:
    private StringBuilder buildTextOfQuery(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
    									   LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, 
    									   LocalDate rangeUntilReceptionDate, Integer existingAmount, Integer fromExistingAmount, 
    									   Integer untilExistingAmount, Integer rangeFromExistingAmount, Integer rangeUntilExistingAmount, 
    									   String baseQuery) 
    {
    	StringBuilder queryBuilder = new StringBuilder(baseQuery); //Definimos la base de la consulta.
    	
    	//Agregamos la línea de cada filtro que corresponda:
    	if(stockIds != null) queryBuilder.append(" AND s.stock_id IN :stockIds");
    	if(receptionDate != null) queryBuilder.append(" AND l.reception_date = :receptionDate");
    	if(fromReceptionDate != null) queryBuilder.append(" AND l.reception_date >= :fromReceptionDate");
    	if(untilReceptionDate != null) queryBuilder.append(" AND l.reception_date <= :untilReceptionDate");
    	if(rangeFromReceptionDate != null) queryBuilder.append(" AND l.reception_date >= :rangeFromReceptionDate");
    	if(rangeUntilReceptionDate != null) queryBuilder.append(" AND l.reception_date <= :rangeUntilReceptionDate");
    	if(existingAmount != null) queryBuilder.append(" AND l.existing_amount = :existingAmount");
    	if(fromExistingAmount != null) queryBuilder.append(" AND l.existing_amount >= :fromExistingAmount");
    	if(untilExistingAmount != null) queryBuilder.append(" AND l.existing_amount <= :untilExistingAmount");
    	if(rangeFromExistingAmount != null) queryBuilder.append(" AND l.existing_amount >= :rangeFromExistingAmount");
    	if(rangeUntilExistingAmount != null) queryBuilder.append(" AND l.existing_amount <= :rangeUntilExistingAmount");
    	
    	return queryBuilder; //Retornamos el texto de la consulta.
    }
    
    //Cargamos la información de los filtros que correspondan en el objeto Query:
    private Query chargeFiltersDataOnQuery(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
    									   LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, 
    									   LocalDate rangeUntilReceptionDate, Integer existingAmount, Integer fromExistingAmount, 
    									   Integer untilExistingAmount, Integer rangeFromExistingAmount, Integer rangeUntilExistingAmount, 
    									   Query query) 
    {
    	//Cargamos los datos de los filtros que correspondan:
    	if(stockIds != null) query.setParameter("stockIds", stockIds);
    	if(receptionDate != null) query.setParameter("receptionDate", receptionDate);
    	if(fromReceptionDate != null) query.setParameter("fromReceptionDate", fromReceptionDate);
    	if(untilReceptionDate != null) query.setParameter("untilReceptionDate", untilReceptionDate);
    	if(rangeFromReceptionDate != null) query.setParameter("rangeFromReceptionDate", rangeFromReceptionDate);
    	if(rangeUntilReceptionDate != null) query.setParameter("rangeUntilReceptionDate", rangeUntilReceptionDate);
    	if(existingAmount != null) query.setParameter("existingAmount", existingAmount);
    	if(fromExistingAmount != null) query.setParameter("fromExistingAmount", fromExistingAmount);
    	if(untilExistingAmount != null) query.setParameter("untilExistingAmount", untilExistingAmount);
    	if(rangeFromExistingAmount != null) query.setParameter("rangeFromExistingAmount", rangeFromExistingAmount);
    	if(rangeUntilExistingAmount != null) query.setParameter("rangeUntilExistingAmount", rangeUntilExistingAmount);
    	
    	return query; //Retornamos el objeto Query con los filtros correspondientes.
    }
    
    //Obtenemos las opciones de filtros según los lotes que cumplen con los filtros:
    @SuppressWarnings("unchecked")
	@Override
    public List<String> findFiltersOptions(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
    									   LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, 
    									   LocalDate rangeUntilReceptionDate, Integer existingAmount, Integer fromExistingAmount, 
    									   Integer untilExistingAmount, Integer rangeFromExistingAmount, Integer rangeUntilExistingAmount) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT DISTINCT (s.stock_id::TEXT) AS stockIds
	    FROM lot l
	    INNER JOIN stock s ON l.stock_id = s.stock_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, 
    												  rangeFromReceptionDate, rangeUntilReceptionDate, existingAmount, fromExistingAmount, 
    												  untilExistingAmount, rangeFromExistingAmount, rangeUntilExistingAmount, baseQuery);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Construimos un objeto Query con la consulta definida anteriormente y los datos de los filtros que correspondan:
    	query = chargeFiltersDataOnQuery(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, rangeFromReceptionDate, 
    									 rangeUntilReceptionDate, existingAmount, fromExistingAmount, untilExistingAmount, 
    									 rangeFromExistingAmount, rangeUntilExistingAmount, query);
    	
    	//Retornamos los resultados de la consulta:
		return query.getResultList();
    }
    
    //Obtenemos los lotes que cumplen con los filtros dentro de una página:
    @Override
    public Page<Lot> findFilteredLots(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
    								  LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, LocalDate rangeUntilReceptionDate, 
    								  Integer existingAmount, Integer fromExistingAmount, Integer untilExistingAmount, 
    								  Integer rangeFromExistingAmount, Integer rangeUntilExistingAmount, String sort, Pageable pageable) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT l.*
	    FROM lot l
	    INNER JOIN stock s ON l.stock_id = s.stock_id
	    INNER JOIN supply_order so ON l.supply_order_id = so.supply_order_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, 
    												  rangeFromReceptionDate, rangeUntilReceptionDate, existingAmount, fromExistingAmount, 
    												  untilExistingAmount, rangeFromExistingAmount, rangeUntilExistingAmount, baseQuery);
    	
    	String orderBy = " ORDER BY " + sort; //Construímos dinámicamente el criterio de ordenamiento según lo envíado:
    	
    	queryBuilder.append(orderBy); //Agregamos el criterio de ordenamiento a la consulta.
    	
    	//Instanciamos un objeto Query con la consulta definida y el mapeo de los resultados a la entidad Stock:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString(), Lot.class);
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, rangeFromReceptionDate, 
    									 rangeUntilReceptionDate, existingAmount, fromExistingAmount, untilExistingAmount, 
    									 rangeFromExistingAmount, rangeUntilExistingAmount, query);
    	
		//Agregamos paginación (LIMIT y OFFSET):
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;
		query.setMaxResults(pageSize);
		query.setFirstResult(offset);
		
		//Ejecutamos la consulta y obtenemos el resultado:
		@SuppressWarnings("unchecked")
		List<Lot> lots = query.getResultList();
		
		//Obtenemos la cantidad de lotes que cumplen con los filtros:
		Long totalLots = getTotalCount(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, rangeFromReceptionDate, 
				 					   rangeUntilReceptionDate, existingAmount, fromExistingAmount, untilExistingAmount, 
				 					   rangeFromExistingAmount, rangeUntilExistingAmount);
		
		//Retornamos el paginado con la información adjunta:
		return new PageImpl<>(lots, pageable, totalLots);
	}
    
    //Obtenemos la cantidad de lotes que cumplen con los filtros:
    @Override
	public Long getTotalCount(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, LocalDate untilReceptionDate, 
							  LocalDate rangeFromReceptionDate, LocalDate rangeUntilReceptionDate, Integer existingAmount, 
							  Integer fromExistingAmount, Integer untilExistingAmount, Integer rangeFromExistingAmount, 
							  Integer rangeUntilExistingAmount) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT COUNT(*)
	    FROM lot l
	    INNER JOIN stock s ON l.stock_id = s.stock_id
	    WHERE 1=1		
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, 
    												  rangeFromReceptionDate, rangeUntilReceptionDate, existingAmount, 
    												  fromExistingAmount, untilExistingAmount, rangeFromExistingAmount, 
    												  rangeUntilExistingAmount, baseQuery);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(stockIds, receptionDate, fromReceptionDate, untilReceptionDate, rangeFromReceptionDate, 
				   						 rangeUntilReceptionDate, existingAmount, fromExistingAmount, untilExistingAmount, 
				   						 rangeFromExistingAmount, rangeUntilExistingAmount, query);
    	
		//Retornamos la cantidad de pedidos de aprovisionamiento hallada:
		return ((Number) query.getSingleResult()).longValue();
	}
}
