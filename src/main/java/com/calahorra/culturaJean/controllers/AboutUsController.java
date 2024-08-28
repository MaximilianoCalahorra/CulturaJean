package com.calahorra.culturaJean.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calahorra.culturaJean.helpers.ViewRouteHelper;

///Clase AboutUsController:
@Controller
@RequestMapping("/aboutUs")
public class AboutUsController 
{
	//Respondemos a las peticiones de información sobre la empresa para el visitante:
	@GetMapping("/visitor")
	public String visitor() 
	{
		return ViewRouteHelper.ABOUT_US_VISITOR;
	}
	
	//Respondemos a las peticiones de información sobre la empresa para el cliente:
	@GetMapping("/customer")
	public String customer() 
	{
		return ViewRouteHelper.ABOUT_US_CUSTOMER;
	}
}
