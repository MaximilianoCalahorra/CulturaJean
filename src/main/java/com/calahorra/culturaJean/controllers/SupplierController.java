package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//Respondemos a las peticiones de información sobre los proveedores para el administrador:
	@GetMapping("/suppliers")
	public ModelAndView suppliers(@RequestParam(value = "order", defaultValue = "orderAscByName")String order) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLIERS);
		
		List<SupplierDTO> suppliers = new ArrayList<SupplierDTO>(); //Instanciamos una lista de proveedores vacía para cargarla.
		
		//En base al tipo de ordenamiento elegido, ordenamos la lista de proveedores:
		switch(order) 
		{
			case "orderAscByName": suppliers = supplierService.getAllInOrderAscByName(); break; //Alfabéticamente por nombre.
			case "orderDescByName": suppliers = supplierService.getAllInOrderDescByName(); break; //Inverso al alfabeto por nombre.
			case "orderAscBySupplierId": suppliers = supplierService.getAllInOrderAscBySupplierId(); break; //Menor a mayor por id.
			case "orderDescBySupplierId": suppliers = supplierService.getAllInOrderDescBySupplierId(); break; //Mayor a menor por id.
		}
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", order); //Indicamos por cuál atributo está ordenado el listado y en qué sentido.
		modelAndView.addObject("suppliers", suppliers); //Agregamos los proveedores ordenados.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
