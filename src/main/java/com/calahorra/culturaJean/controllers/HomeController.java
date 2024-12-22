package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IStockService;

///Clase HomeController:
@Controller
@RequestMapping("/")
public class HomeController
{
	//Atributos:
	private IStockService stockService;
	private IProductService productService;
	
	//Constructor:
	public HomeController(IStockService stockService, IProductService productService) 
	{
		this.stockService = stockService;
		this.productService = productService;
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
			
			//Instanciamos un listado de stock donde se guardarán el stock de cada producto filtrado y ordenado:
			List<StockDTO> stocks = stockService.getAllInOrderAscByActualAmount();
			
			//Copiamos a otra lista los productos para obtener información sobre ellos posteriormente:
			List<ProductDTO> products = new ArrayList<>();
			for(StockDTO stock: stocks) 
			{
				products.add(stock.getProduct());
			}
			
			//Agregamos la información a la vista:
			modelAndView.addObject("order", "orderAscByActualAmount"); //Adjuntamos el criterio de ordenamiento.
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
			modelAndView.addObject("productCategories", productService.findUniqueEachCategory(products)); //Adjuntamos el listado de categorías de producto.
			modelAndView.addObject("productGenders", productService.findUniqueEachGender(products)); //Adjuntamos el listado de géneros de producto.
			modelAndView.addObject("productSizes", productService.findUniqueEachSize(products)); //Adjuntamos el listado de talles de producto.
			modelAndView.addObject("productColors", productService.findUniqueEachColor(products)); //Adjuntamos el listado de colores de producto.
			modelAndView.addObject("stocks", stocks); //Adjuntamos los stocks filtrados y ordenados.
		}
		else //En caso contrario, el miembro logueado es un cliente, por lo que procedemos de la siguiente forma:
		{
			//La vista a cargar es la de inicio de los clientes:
			modelAndView = new ModelAndView(ViewRouteHelper.CUSTOMER_INDEX);
		}
		return modelAndView; //Retornamos la vista que corresponda.
	}
}
