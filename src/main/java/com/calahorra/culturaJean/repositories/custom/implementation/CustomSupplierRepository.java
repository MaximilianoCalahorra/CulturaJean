package com.calahorra.culturaJean.repositories.custom.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Supplier;
import com.calahorra.culturaJean.repositories.custom.ICustomSupplierRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

///Clase CustomSupplierRepository:
@Repository("customSupplierRepository")
public class CustomSupplierRepository implements ICustomSupplierRepository 
{
	//Atributo:
    @PersistenceContext
    private EntityManager entityManager;

    //Obtenemos los proveedores de una página ordenados:
    @Override
    public Page<Supplier> findOrderedSuppliers(String sort, Pageable pageable) 
    {
    	//Definimos el retorno y el criterio de ordenamiento de la consulta:
    	String stringQuery = """
    	SELECT s.*
	    FROM supplier s
	    ORDER BY
	    """ + " " + sort + " ";
    	
    	//Instanciamos un objeto Query con la consulta definida y el mapeo de los resultados a la entidad Supplier:
    	Query query = entityManager.createNativeQuery(stringQuery, Supplier.class);
    	
		//Agregamos paginación (LIMIT y OFFSET):
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int offset = pageNumber * pageSize;
		query.setMaxResults(pageSize);
		query.setFirstResult(offset);
		
		//Ejecutamos la consulta y obtenemos el resultado:
		@SuppressWarnings("unchecked")
		List<Supplier> suppliers = query.getResultList();
		
		//Obtenemos la cantidad de proveedores:
		Long totalSuppliers = getTotalCount();
		
		//Retornamos el paginado con la información adjunta:
		return new PageImpl<>(suppliers, pageable, totalSuppliers);
	}
    
    //Obtenemos la cantidad de proveedores que tenemos:
    @Override
	public Long getTotalCount() 
    {
    	//Definimos la consulta:
    	String stringQuery = """
	    SELECT COUNT(*)
	    FROM supplier s
    	""";
    	
    	//Instanciamos un objeto Query con la consulta definida:
    	Query query = entityManager.createNativeQuery(stringQuery);
    	
		//Retornamos la cantidad de proveedores hallada:
		return ((Number) query.getSingleResult()).longValue();
	}
}
