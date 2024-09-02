package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PurchaseDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IPurchaseService;

///Clase SaleController:
@Controller
@RequestMapping("/sale")
public class SaleController 
{
	//Atributo:
	private IPurchaseService purchaseService;
	
	//Constructor:
	public SaleController(IPurchaseService purchaseService) 
	{
		this.purchaseService = purchaseService;
	}
	
	//Respondemos a las peticiones de información sobre las ventas para el administrador:
	@GetMapping("/sales")
	public ModelAndView sales() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SALES);
		
		//Obtenemos las ventas ordenadas de forma descendente por fecha y hora:
		List<PurchaseDTO> sales = purchaseService.getAllInOrderDescByDateTime(); 
		
		//Agregamos las ventas a la vista:
		modelAndView.addObject("sales", sales);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
