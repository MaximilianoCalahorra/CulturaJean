package com.calahorra.culturaJean.services;

import java.util.List;

import com.calahorra.culturaJean.dtos.SupplierDTO;
import com.calahorra.culturaJean.entities.Supplier;

///Interfaz ISupplierService:
public interface ISupplierService
{
	//Encontrar:
	
	//Encontramos el proveedor con determinado id:
	public Supplier findBySupplierId(int supplierId);
	
	//Encontramos el proveedor con determinado nombre:
	public Supplier findByName(String name);
	
	//Encontramos el proveedor con determinado nombre:
	public SupplierDTO findDTOByName(String name);
	
	//Encontramos el proveedor con determinado número de teléfono:
	public Supplier findByPhoneNumber(String phoneNumber);
	
	//Encontramos el proveedor con determinado email:
	public Supplier findByEmail(String email);
	
	//Obtener:
	
	//Obtenemos todos los proveedores:
	public List<Supplier> getAll();
	
	//Ordenar:
	
	//Ordenamos los proveedores por nombre de manera ascendente:
	public List<SupplierDTO> getAllInOrderAscByName();
	
	//Ordenamos los proveedores por nombre de manera descendente:
	public List<SupplierDTO> getAllInOrderDescByName();
	
	//Ordenamos los proveedores por id de manera ascendente:
	public List<SupplierDTO> getAllInOrderAscBySupplierId();
	
	//Ordenamos los proveedores por id de manera ascendente:
	public List<SupplierDTO> getAllInOrderDescBySupplierId();
}
