package com.calahorra.culturaJean.repositories.custom.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Member;
import com.calahorra.culturaJean.repositories.custom.ICustomMemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

///Clase CustomMemberRepository:
@Repository("customMemberRepository")
public class CustomMemberRepository implements ICustomMemberRepository 
{
	//Atributo:
    @PersistenceContext
    private EntityManager entityManager;

    //Construimos el texto de la consulta según el principio dado para la misma y el estado de los filtros:
    private StringBuilder buildTextOfQuery(String role, Boolean enabled, String baseQuery) 
    {
    	StringBuilder queryBuilder = new StringBuilder(baseQuery); //Definimos la base de la consulta.
    	
    	//Agregamos la línea de cada filtro que corresponda:
    	if(role != null) queryBuilder.append(" AND ur.role = :role");
    	if(enabled != null) queryBuilder.append(" AND m.enabled = :enabled");
    	
    	return queryBuilder; //Retornamos el texto de la consulta.
    }
    
    //Cargamos la información de los filtros que correspondan en el objeto Query:
    private Query chargeFiltersDataOnQuery(String role, Boolean enabled, Query query) 
    {
    	//Cargamos los datos de los filtros que correspondan:
    	if(role != null) query.setParameter("role", role);
    	if(enabled != null) query.setParameter("enabled", enabled);
    	
    	return query; //Retornamos el objeto Query con los filtros correspondientes.
    }
    
    //Obtenemos los miembros que cumplen con los filtros dentro de una página:
    @Override
    public Page<Member> findFilteredMembers(String role, Boolean enabled, String sort, Pageable pageable) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
    	SELECT m.*
	    FROM member m
	    INNER JOIN user_role ur ON m.member_id = ur.member_id
	    WHERE 1=1
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(role, enabled, baseQuery);
    	
    	String orderBy = " ORDER BY " + sort; //Construímos dinámicamente el criterio de ordenamiento según lo envíado:
    	
    	queryBuilder.append(orderBy); //Agregamos el criterio de ordenamiento a la consulta.
    	
    	//Instanciamos un objeto Query con la consulta definida y el mapeo de los resultados a la entidad Member:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString(), Member.class);
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(role, enabled, query);
    	
		//Agregamos paginación (LIMIT y OFFSET):
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;
		query.setMaxResults(pageSize);
		query.setFirstResult(offset);
		
		//Ejecutamos la consulta y obtenemos el resultado:
		@SuppressWarnings("unchecked")
		List<Member> members = query.getResultList();
		
		//Obtenemos la cantidad de miembros que cumplen con los filtros:
		Long totalMembers = getTotalCount(role, enabled);
		
		//Retornamos el paginado con la información adjunta:
		return new PageImpl<>(members, pageable, totalMembers);
	}
    
    //Obtenemos la cantidad de miembros que cumplen con los filtros:
    @Override
	public Long getTotalCount(String role, Boolean enabled) 
    {
    	//Definimos el principio de la consulta:
    	String baseQuery = """
	    SELECT COUNT(*)
	    FROM member m
	    INNER JOIN user_role ur ON m.member_id = ur.member_id
	    WHERE 1=1	
    	""";
    	
    	//Unimos el principio de la consulta con la fila de cada filtro que corresponda:
    	StringBuilder queryBuilder = buildTextOfQuery(role, enabled, baseQuery);
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(queryBuilder.toString());
    	
    	//Agregamos los datos de los filtros que correspondan al objeto Query:
    	query = chargeFiltersDataOnQuery(role, enabled, query);
    	
		//Retornamos la cantidad de miembros hallada:
		return ((Number) query.getSingleResult()).longValue();
	}
}
