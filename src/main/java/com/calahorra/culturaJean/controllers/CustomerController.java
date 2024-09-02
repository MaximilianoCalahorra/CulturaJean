package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.implementation.MemberService;

///Clase CustomerController:
@Controller
@RequestMapping("/customer")
public class CustomerController 
{
	//Atributo:
	private MemberService userService;
	
	//Constructor:
	public CustomerController(MemberService userService) 
	{
		this.userService = userService;
	}
	
	//Respondemos a las peticiones de información sobre los clientes para el administrador:
	@GetMapping("/customers")
	public ModelAndView customers() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.CUSTOMERS);
		
		//Obtenemos los clientes ordenados de forma alfabética por apellido:
		List<MemberDTO> customers = userService.getByUserRoleInOrderAscByLastName("ROLE_CUSTOMER");
		
		//Agregamos los clientes a la vista:
		modelAndView.addObject("customers", customers);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
