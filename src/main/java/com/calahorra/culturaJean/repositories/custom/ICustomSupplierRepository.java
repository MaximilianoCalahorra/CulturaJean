package com.calahorra.culturaJean.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.entities.Supplier;

///Interfaz ICustomSupplierRepository:
public interface ICustomSupplierRepository
{	
	//Obtenemos los proveedores de una página ordenados:
	public abstract Page<Supplier> findOrderedSuppliers(String sort, Pageable pageable);
	
	//Obtenemos la cantidad de proveedores que tenemos:
	public abstract Long getTotalCount();
}
