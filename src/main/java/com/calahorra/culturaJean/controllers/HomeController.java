package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IStockService;

///CLase HomeController:
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
	public ModelAndView index(@RequestParam(value = "order", defaultValue = "orderAscByActualAmount")String order,
							  @RequestParam(value = "cat", defaultValue = "all")String category,
							  @RequestParam(value = "gen", defaultValue = "all")String gender,
							  @RequestParam(value = "size", defaultValue = "all")String size,
							  @RequestParam(value = "col", defaultValue = "all")String color,
							  @RequestParam(value = "sPri", defaultValue = "")String salePrice,
							  @RequestParam(value = "fSPri", defaultValue = "")String fromSalePrice,
							  @RequestParam(value = "uSPri", defaultValue = "")String untilSalePrice,
							  @RequestParam(value = "rFSPri", defaultValue = "")String rangeFromSalePrice,
							  @RequestParam(value = "rUSPri", defaultValue = "")String rangeUntilSalePrice,
							  @RequestParam(value = "ena", defaultValue = "all")String enabled)
	{
		//Obtenemos el usuario que se logueó:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView modelAndView;
		
		//Si se trata de un administrador:
		if(user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
		{
			//La vista a cargar es la de inicio del administrador:
			modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_INDEX);
			
			//Instanciamos un listado de productos donde vamos a cargar los productos filtrados y ordenados.
			List<ProductDTO> products = new ArrayList<ProductDTO>(); 
			
			//Aplicamos el filtro seleccionado de la sección estado de los productos:
			if(!enabled.equals("all")) //Si se elegió la opción de habilitados o la deshabilitados:
			{
				boolean enabledValue = Boolean.parseBoolean(enabled); //Convertimos la cadena a valor booleano.
				products = productService.findByEnabled(enabledValue); //Obtenemos los productos habilitados/inhabilitados desde la base de datos. 
			}
			else //Por el contrario, si se eligió la de todos:
			{
				products = productService.getAllProducts(); //Obtenemos todos los productos desde la base de datos.
			}
			
			//Aplicamos los filtros seleccionados de las secciones categoría, género, talle, color y precio de venta:
			products = productService.applyFilters(products, category, gender, size, color, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice);
			
			//Instanciamos un listado de stock donde se guardarán el stock de cada producto filtrado y ordenado:
			List<StockDTO> stocks = new ArrayList<StockDTO>();
			
			//Recorremos los productos:
			for(ProductDTO product: products) 
			{
				//Obtenemos el stock de cada uno y lo agregamos a la lista:
				stocks.add(stockService.findByProduct(product.getProductId()));
			}
			
			//Aplicamos el criterio de ordenamiento seleccionado:
			stocks = stockService.applyOrder(stocks, order);
			
			//Agregamos la información a la vista:
			modelAndView.addObject("order", order); //Adjuntamos el criterio de ordenamiento.
			modelAndView.addObject("cat", category); //Adjuntamos la categoría para el filtro.
			modelAndView.addObject("gen", gender); //Adjuntamos el género para el filtro.
			modelAndView.addObject("size", size); //Adjuntamos el talle para el filtro.
			modelAndView.addObject("col", color); //Adjuntamos el color para el filtro.
			modelAndView.addObject("sPri", salePrice); //Adjuntamos el precio de venta para el filtro.
			modelAndView.addObject("fSPri", fromSalePrice); //Adjuntamos el precio de venta mayor o igual para el filtro.
			modelAndView.addObject("uSPri", untilSalePrice); //Adjuntamos el precio de venta menor o igual para el filtro.
			modelAndView.addObject("rFSPri", rangeFromSalePrice); //Adjuntamos el precio mayor o igual de un rango para el filtro.
			modelAndView.addObject("rUSPri", rangeUntilSalePrice); //Adjuntamos el precio menor o igual de un rango para el filtro.
			modelAndView.addObject("ena", enabled); //Adjuntamos el estado de los productos para el filtro.
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
