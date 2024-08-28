package com.calahorra.culturaJean.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calahorra.culturaJean.helpers.ViewRouteHelper;

///Clase StoresController:
@Controller
@RequestMapping("/stores")
public class StoresController 
{
	//Respondemos a las peticiones de información sobre las tiendas para el visitante:
	@GetMapping("/visitor")
	public String visitor() 
	{
		return ViewRouteHelper.STORES_VISITOR;
	}
	
	//Respondemos a las peticiones de información sobre las tiendas para el cliente:
	@GetMapping("/customer")
	public String customer() 
	{
		return ViewRouteHelper.STORES_CUSTOMER;
	}
}
