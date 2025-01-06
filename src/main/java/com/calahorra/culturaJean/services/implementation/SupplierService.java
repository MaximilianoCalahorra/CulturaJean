package com.calahorra.culturaJean.services.implementation;

import java.util.List;
import java.util.stream.Collectors; 

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.PaginatedSupplierDTO;
import com.calahorra.culturaJean.dtos.SupplierDTO;
import com.calahorra.culturaJean.entities.Supplier;
import com.calahorra.culturaJean.repositories.ISupplierRepository;
import com.calahorra.culturaJean.repositories.custom.ICustomSupplierRepository;
import com.calahorra.culturaJean.services.ISupplierService;

///Clase SupplierService:
@Service("supplierService")
public class SupplierService implements ISupplierService
{
	//Atributos:
	private ISupplierRepository supplierRepository;
	private ICustomSupplierRepository customSupplierRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public SupplierService(ISupplierRepository supplierRepository, ICustomSupplierRepository customSupplierRepository) 
	{
		this.supplierRepository = supplierRepository;
		this.customSupplierRepository = customSupplierRepository;
	}
	
	//Encontrar:
	
	//Encontramos el proveedor con determinado id:
	@Override
	public Supplier findBySupplierId(int supplierId)
	{
		return supplierRepository.findBySupplierId(supplierId); //Buscamos el proveedor en la base de datos.
	}
	
	//Enconrtamos el proveedor con determinado nombre:
	@Override
	public Supplier findByName(String name)
	{
		return supplierRepository.findByName(name); //Buscamos el proveedor en la base de datos.
	}
	
	//Enconrtamos el proveedor con determinado nombre:
	@Override
	public SupplierDTO findDTOByName(String name)
	{
		return modelMapper.map(supplierRepository.findByName(name), SupplierDTO.class); //Buscamos el proveedor en la base de datos.
	}
	
	//Encontramos el proveedor con determinado número de teléfono:
	@Override
	public Supplier findByPhoneNumber(String phoneNumber) 
	{
		return supplierRepository.findByPhoneNumber(phoneNumber); //Buscamos el proveedor en la base de datos.
	}
	
	//Encontramos el proveedor con determinado email:
	@Override
	public Supplier findByEmail(String email) 
	{
		return supplierRepository.findByEmail(email); //Buscamos el proveedor en la base de datos.
	}
	
	//Obtener:
	
	//Obtenemos todos los proveedores:
	@Override
	public List<Supplier> getAll()
	{
		return supplierRepository.findAll();
	}
	
	//Obtenemos los proveedores de una página ordenados:
	@Override
	public PaginatedSupplierDTO getOrderedSuppliers(String sort, int page, int size)
	{
		//Instanciamos un Pageable con la página y la cantidad de elementos a traer para hacer la query:
        Pageable pageable = PageRequest.of(page, size);
        
        //Obtenemos la página de proveedores según el criterio de ordenamiento:
        Page<Supplier> supplierPage = customSupplierRepository.findOrderedSuppliers(sort, pageable);
        
        //Construimos el objeto paginado con su información:
        PaginatedSupplierDTO paginatedDTO = new PaginatedSupplierDTO();
        paginatedDTO.setSuppliers(supplierPage.map(supplier -> modelMapper.map(supplier, SupplierDTO.class)).getContent()); //Proveedores.
        paginatedDTO.setTotalPages(supplierPage.getTotalPages()); //Cantidad de páginas.
        paginatedDTO.setTotalElements(supplierPage.getTotalElements()); //Cantidad de proveedores.
        
        return paginatedDTO; //Retornamos el objeto paginado.
	}
	
	//Ordenar:
	
	//Ordenamos los proveedores por nombre de manera ascendente:
	@Override
	public List<SupplierDTO> getAllInOrderAscByName()
	{
		return supplierRepository.getAllInOrderAscByName() //Obtenemos los proveedores ordenados como entidades.
				.stream() 
				.map(supplier -> modelMapper.map(supplier, SupplierDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los proveedores por nombre de manera descendente:
	@Override
	public List<SupplierDTO> getAllInOrderDescByName()
	{
		return supplierRepository.getAllInOrderDescByName() //Obtenemos los proveedores ordenados como entidades.
				.stream()
				.map(supplier -> modelMapper.map(supplier, SupplierDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los proveedores por id de manera ascendente:
	@Override
	public List<SupplierDTO> getAllInOrderAscBySupplierId()
	{
		return supplierRepository.getAllInOrderAscBySupplierId() //Obtenemos los proveedores ordenados como entidades.
				.stream()
				.map(supplier -> modelMapper.map(supplier, SupplierDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los proveedores por id de manera descendente:
	@Override
	public List<SupplierDTO> getAllInOrderDescBySupplierId()
	{
		return supplierRepository.getAllInOrderDescBySupplierId() //Obtenemos los proveedores ordenados como entidades.
				.stream()
				.map(supplier -> modelMapper.map(supplier, SupplierDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
}
