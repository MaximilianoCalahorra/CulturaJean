package com.calahorra.culturaJean.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calahorra.culturaJean.helpers.ViewRouteHelper;

///Clase HelpController:
@Controller
@RequestMapping("/help")
public class HelpController 
{
	//Respondemos a las peticiones de ayuda para el visitante:
	@GetMapping("/visitor")
	public String visitor() 
	{
		return ViewRouteHelper.HELP_VISITOR;
	}
	
	//Respondemos a las peticiones de ayuda para el cliente:
	@GetMapping("/customer")
	public String customer() 
	{
		return ViewRouteHelper.HELP_CUSTOMER;
	}
}
