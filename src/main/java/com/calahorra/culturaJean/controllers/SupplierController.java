package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.SupplierDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.ISupplierService;

///Clase SupplierController:
@Controller
@RequestMapping("/supplier")
public class SupplierController 
{
	//Atributo:
	private ISupplierService supplierService;
	
	//Constructor:
	public SupplierController(ISupplierService supplierService) 
	{
		this.supplierService = supplierService;
	}
	
	//Respondemos a las peticiones para cargar todos los proveedores:
	@GetMapping("/suppliers")
	public ModelAndView suppliers() 
	{
		//Definimos la vista a cargar:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLIERS);
		
		//Obtenemos todos los proveedores ordenados alfabéticamente por nombre:
		List<SupplierDTO> suppliers = supplierService.getAllInOrderAscByName();
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", "orderAscByName"); //Indicamos por cuál atributo está ordenado el listado y en qué sentido.
		modelAndView.addObject("suppliers", suppliers); //Agregamos los proveedores ordenados.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las solicitudes de filtrado/ordenamiento sobre los proveedores:
	@GetMapping("/suppliers/{order}")
	public ResponseEntity<List<SupplierDTO>> orderSuppliers(@PathVariable("order") String order) 
	{
		//Instanciamos una lista de proveedores para posteriormente cargarla con los proveedores ordenados:
		List<SupplierDTO> suppliers = new ArrayList<>(); 
		
		//En base al tipo de ordenamiento elegido, obtenemos la lista de proveedores ordenada:
		switch(order) 
		{
			case "orderAscByName": suppliers = supplierService.getAllInOrderAscByName(); break; //Alfabéticamente por nombre.
			case "orderDescByName": suppliers = supplierService.getAllInOrderDescByName(); break; //Inverso al alfabeto por nombre.
			case "orderAscBySupplierId": suppliers = supplierService.getAllInOrderAscBySupplierId(); break; //Menor a mayor por id.
			case "orderDescBySupplierId": suppliers = supplierService.getAllInOrderDescBySupplierId(); break; //Mayor a menor por id.
		}
		
		return ResponseEntity.ok(suppliers); //Retornamos los proveedores ordenados como JSON.
	}
}
