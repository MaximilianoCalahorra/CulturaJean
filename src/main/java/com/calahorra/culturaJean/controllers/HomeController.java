package com.calahorra.culturaJean.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PaginatedStockDTO;
import com.calahorra.culturaJean.dtos.ProductFiltersDataDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IStockService;

///Clase HomeController:
@Controller
@RequestMapping("/")
public class HomeController
{
	//Atributos:
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
			
			String defaultOrder = "s.actual_amount ASC"; //Definimos el criterio de ordenamiento por defecto.
			
			ProductFiltersDataDTO filters = new ProductFiltersDataDTO(); //Definimos todos los filtros en su estado por defecto.
			filters.setOrder(defaultOrder); //Pasamos el criterio de ordenamiento.
			int page = 0; //Definimos que es la primera página.
			int size = 12; //Definimos la cantidad de elementos de la página.
			
			PaginatedStockDTO paginated = stockService.getFilteredStocks(filters, page, size); //Obtenemos los stocks de la página.
			
			//Agregamos la información a la vista:
			modelAndView.addObject("order", defaultOrder); //Adjuntamos el criterio de ordenamiento.
			modelAndView.addObject("cat", "all"); //Adjuntamos la categoría para el filtro.
			modelAndView.addObject("gen", "all"); //Adjuntamos el género para el filtro.
			modelAndView.addObject("size", "all"); //Adjuntamos el talle para el filtro.
			modelAndView.addObject("col", "all"); //Adjuntamos el color para el filtro.
			modelAndView.addObject("ena", "all"); //Adjuntamos el estado de los productos para el filtro.
			modelAndView.addObject("sPri", ""); //Adjuntamos el precio de venta para el filtro.
			modelAndView.addObject("fSPri", ""); //Adjuntamos el precio de venta mayor o igual para el filtro.
			modelAndView.addObject("uSPri", ""); //Adjuntamos el precio de venta menor o igual para el filtro.
			modelAndView.addObject("rFSPri", ""); //Adjuntamos el precio mayor o igual de un rango para el filtro.
			modelAndView.addObject("rUSPri", ""); //Adjuntamos el precio menor o igual de un rango para el filtro.
			
			modelAndView.addObject("categories", paginated.getFiltersOptions().getCategories()); //Adjuntamos el listado de categorías de producto.
			modelAndView.addObject("genders", paginated.getFiltersOptions().getGenders()); //Adjuntamos el listado de géneros de producto.
			modelAndView.addObject("sizes", paginated.getFiltersOptions().getSizes()); //Adjuntamos el listado de talles de producto.
			modelAndView.addObject("colors", paginated.getFiltersOptions().getColors()); //Adjuntamos el listado de colores de producto.
			
			modelAndView.addObject("paginated", paginated); //Adjuntamos el paginado.
		}
		else //En caso contrario, el miembro logueado es un cliente, por lo que procedemos de la siguiente forma:
		{
			//La vista a cargar es la de inicio de los clientes:
			modelAndView = new ModelAndView(ViewRouteHelper.CUSTOMER_INDEX);
		}
		return modelAndView; //Retornamos la vista que corresponda.
	}
}
