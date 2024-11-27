package com.calahorra.culturaJean.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calahorra.culturaJean.helpers.ViewRouteHelper;

///Clase HelpController:
@Controller
@RequestMapping("/help")
public class HelpController 
{
	//Respondemos a las peticiones de información sobre ayuda para el cliente/visitante:
	@GetMapping("/{role}")
	public String loadView(@PathVariable("role")String role) 
	{
		String view = "";
			
		//Según el rol que peticionó:
		switch(role) 
		{
			case "customer": view = ViewRouteHelper.HELP_CUSTOMER; break; //Le mostramos la vista del cliente.
			case "visitor": view = ViewRouteHelper.HELP_VISITOR; break; //Le mostramos la vista del visitante.
		}

		return view; //Retornamos la vista que corresponda.
	}
}
