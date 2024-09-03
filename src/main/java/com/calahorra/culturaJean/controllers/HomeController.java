package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IStockService;

///CLase HomeController:
@Controller
@RequestMapping("/")
public class HomeController
{
	//Atributo:
	private IStockService stockService;
	
	//Constructor:
	public HomeController(IStockService stockService) 
	{
		this.stockService = stockService;
	} 
	
	//Respondemos a la ruta base del sitio web con la vista de inicio del visitante:
	@GetMapping("/")
	public String start() 
	{
		return ViewRouteHelper.VISITOR_INDEX;
	}
	
	//Respondemos a las peticiones de cargar la vista principal con la vista en cuestión según el tipo de miembro:
	@GetMapping("/index")
	public ModelAndView index()
	{
		//Obtenemos el usuario que se logueó:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView modelAndView;
		
		//Si se trata de un administrador:
		if(user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
		{
			//La vista a cargar es la de inicio del administrador:
			modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_INDEX);
			
			//Obtenemos de la base de datos la información a presentar en la vista:
			//En este caso, cada stock con su producto ordenados de menor a mayor por la cantidad actual.
			List<StockDTO> stocks = stockService.getAllInOrderAscByActualAmount();
			
			//Agregamos la información a la vista:
			modelAndView.addObject("stocks", stocks);
			
		}
		else //En caso contrario, el miembro logueado es un cliente, por lo que procedemos de la siguiente forma:
		{
			//La vista a cargar es la de inicio de los clientes:
			modelAndView = new ModelAndView(ViewRouteHelper.CUSTOMER_INDEX);
		}
		return modelAndView; //Retornamos la vista que corresponda.
	}
}
