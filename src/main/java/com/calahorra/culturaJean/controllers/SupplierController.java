package com.calahorra.culturaJean.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PaginatedSupplierDTO;
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
		
		String defaultOrder = "s.name ASC"; //Definimos el criterio de ordenamiento por defecto.
    	
		int page = 0; //Definimos que es la primera página.
		int size = 10; //Definimos la cantidad de elementos de la página.
		
		//Obtenemos los proveedores de la página:
		PaginatedSupplierDTO paginated = supplierService.getOrderedSuppliers(defaultOrder, page, size);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", defaultOrder); //Indicamos por cuál atributo está ordenado el paginado y en qué sentido.
		modelAndView.addObject("paginated", paginated); //Agregamos el paginado.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las solicitudes de ordenamiento sobre los proveedores:
	@GetMapping("/suppliers/order")
	public ResponseEntity<PaginatedSupplierDTO> orderSuppliers(@RequestParam("order") String order, @RequestParam("page")int page,
		    												   @RequestParam("size")int size) 
	{
		return ResponseEntity.ok(supplierService.getOrderedSuppliers(order, page, size)); //Retornamos el paginado.
	}
}
