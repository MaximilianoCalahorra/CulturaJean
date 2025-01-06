package com.calahorra.culturaJean.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PaginatedPurchaseDTO;
import com.calahorra.culturaJean.dtos.PurchaseFiltersDataDTO;
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
	
	//Respondemos a las peticiones de información sobre todas las ventas para el administrador:
	@GetMapping("/sales")
	public ModelAndView sales() 
	{
		//Definimos la vista a cargar:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SALES);
		
		String defaultOrder = "p.date_time DESC"; //Definimos el criterio de ordenamiento por defecto.
		
		PurchaseFiltersDataDTO filters = new PurchaseFiltersDataDTO(); //Definimos todos los filtros en su estado por defecto.
		filters.setOrder(defaultOrder); //Pasamos el criterio de ordenamiento.
		int page = 0; //Definimos que es la primera página.
		int size = 6; //Definimos la cantidad de elementos de la página.
		
		PaginatedPurchaseDTO paginated = purchaseService.getFilteredPurchases(filters, page, size); //Obtenemos las compras de la página.
		
		//Agregamos la siguiente información a la vista:
		modelAndView.addObject("order", defaultOrder); //Adjuntamos el criterio de ordenamiento por defecto.
		modelAndView.addObject("methodsOfPay", paginated.getFiltersOptions().getMethodsOfPay()); //Adjuntamos un listado con los métodos de pago que tienen las compras.
		modelAndView.addObject("usernames", paginated.getFiltersOptions().getUsernames()); //Adjuntamos un listado con los nombres de usuarios que tienen compras.
		modelAndView.addObject("username", "all"); //Adjuntamos el nombre de usuario aplicado como filtro.
		modelAndView.addObject("methodOfPay", "all"); //Adjuntamos el método de pago aplicado como filtro.
		modelAndView.addObject("fromPrice", ""); //Adjuntamos el filtro por mayores o iguales a un precio.
		modelAndView.addObject("untilPrice", ""); //Adjuntamos el filtro por menores o iguales a un precio.
		modelAndView.addObject("rangeFromPrice", ""); //Adjuntamos el filtro por mayores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("rangeUntilPrice", ""); //Adjuntamos el filtro por menores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("paginated", paginated); //Adjuntamos el paginado.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las peticiones de filtrado/ordenamiento de las ventas:
	@PostMapping("/sales/filter")
	public ResponseEntity<PaginatedPurchaseDTO> filteredSales(@RequestBody PurchaseFiltersDataDTO filters, @RequestParam("page")int page,
		    											   	  @RequestParam("size")int size) 
	{
		return ResponseEntity.ok(purchaseService.getFilteredPurchases(filters, page, size)); //Retornamos el paginado.
	}
}
