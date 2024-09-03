package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	//Respondemos a las peticiones de información sobre los proveedores para el administrador:
	@GetMapping("/suppliers")
	public ModelAndView suppliers() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLIERS);
		
		//Obtenemos los proveedores ordenados por nombre de forma alfabética:
		List<SupplierDTO> suppliers = supplierService.getAllInOrderAscByName();
		
		//Agregamos la información a la vista:
		modelAndView.addObject("suppliers", suppliers);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
