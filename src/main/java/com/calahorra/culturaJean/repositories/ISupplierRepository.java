package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Supplier;

///Interfaz ISupplierRepository:
@Repository("supplierRepository")
public interface ISupplierRepository extends JpaRepository<Supplier, Serializable>
{
	//Encontrar:
	
	//Encontramos el proveedor con determinado id:
	public abstract Supplier findBySupplierId(int supplierId);
	
	//Encontramos el proveedor con determinado nombre:
	public abstract Supplier findByName(String name);
	
	//Encontramos el proveedor con determinado número de teléfono:
	public abstract Supplier findByPhoneNumber(String phoneNumber);
	
	//Encontramos el proveedor con determinado email:
	public abstract Supplier findByEmail(String email);
	
	//Ordenar:
	
	//Ordenamos los proveedores por nombre de manera ascendente:
	@Query("SELECT s FROM Supplier s ORDER BY s.name")
	public abstract List<Supplier> getAllInOrderAscByName();
	
	//Ordenamos los proveedores por nombre de manera descendente:
	@Query("SELECT s FROM Supplier s ORDER BY s.name DESC")
	public abstract List<Supplier> getAllInOrderDescByName();
	
	//Ordenamos los proveedores por id de manera ascendente:
	@Query("SELECT s FROM Supplier s ORDER BY s.supplierId")
	public abstract List<Supplier> getAllInOrderAscBySupplierId();
	
	//Ordenamos los proveedores por id de manera ascendente:
	@Query("SELECT s FROM Supplier s ORDER BY s.supplierId DESC")
	public abstract List<Supplier> getAllInOrderDescBySupplierId();
}
