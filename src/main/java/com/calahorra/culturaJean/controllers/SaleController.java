package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PurchaseDTO;
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
		
		//Obtenemos las ventas ordenadas por el criterio predeterminado:
		List<PurchaseDTO> sales = purchaseService.getAllInOrderDescByDateTime();
		
		//Agregamos la siguiente información a la vista:
		modelAndView.addObject("order", "orderDescByDateTime"); //Adjuntamos el criterio de ordenamiento por defecto.
		modelAndView.addObject("usernames", purchaseService.getAllUsernames(sales)); //Adjuntamos un listado con los nombres de usuarios que tienen compras.
		modelAndView.addObject("username", "all"); //Adjuntamos el nombre de usuario aplicado como filtro.
		modelAndView.addObject("methodOfPay", "all"); //Adjuntamos el método de pago aplicado como filtro.
		modelAndView.addObject("fromSalePrice", ""); //Adjuntamos el filtro por mayores o iguales a un precio.
		modelAndView.addObject("untilSalePrice", ""); //Adjuntamos el filtro por menores o iguales a un precio.
		modelAndView.addObject("rangeFromSalePrice", ""); //Adjuntamos el filtro por mayores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("rangeUntilSalePrice", ""); //Adjuntamos el filtro por menores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("sales", sales); //Adjuntamos las ventas.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las peticiones de filtrado/ordenamiento de las ventas:
	@PostMapping("/sales/filter")
	public ResponseEntity<List<PurchaseDTO>> filteredSales(@RequestBody PurchaseFiltersDataDTO filtersData) 
	{
		//Obtenemos los valores seleccionados para hacer el filtrado y ordenamiento:
    	String order = filtersData.getOrder();
    	String date = filtersData.getDate();
    	String fromDate = filtersData.getFromDate();
    	String untilDate = filtersData.getUntilDate();
    	String rangeFromDate = filtersData.getRangeFromDate();
    	String rangeUntilDate = filtersData.getRangeUntilDate();
    	String fromTime = filtersData.getFromTime();
    	String untilTime = filtersData.getUntilTime();
    	String rangeFromTime = filtersData.getRangeFromTime();
    	String rangeUntilTime = filtersData.getRangeUntilTime();
    	List<String> usernames = filtersData.getUsernames();
    	List<String> methodsOfPay = filtersData.getMethodsOfPay();
    	float fromPrice = Float.parseFloat(filtersData.getFromPrice());
    	float untilPrice = Float.parseFloat(filtersData.getUntilPrice());
    	float rangeFromPrice = Float.parseFloat(filtersData.getRangeFromPrice());
    	float rangeUntilPrice = Float.parseFloat(filtersData.getRangeUntilPrice());
    	
    	//Obtenemos las ventas filtradas por los criterios elegidos de fechas:
		List<PurchaseDTO> sales = purchaseService.applyFilterTypeDate(date, fromDate, untilDate, rangeFromDate, rangeUntilDate);
		
		//Aplicamos los filtros seleccionados de horas:	
		sales = purchaseService.applyFilterTypeTime(sales, fromTime, untilTime, rangeFromTime, rangeUntilTime);
		
		//Aplicamos los filtros seleccionados de usernames:
		sales = purchaseService.filterByUsername(sales, usernames);
		
		//Aplicamos los filtros seleccionados de métodos de pago:
		sales = purchaseService.filterByMethodOfPay(sales, methodsOfPay);
	
		//Aplicamos los filtros seleccionados de precios:
		sales = purchaseService.applyFilterTypePurchasePrice(sales, fromPrice, untilPrice, rangeFromPrice, rangeUntilPrice);
		
		//Aplicamos el ordenamiento seleccionado:
		sales = purchaseService.applyOrder(sales, order);
		
        return ResponseEntity.ok(sales); //Retornamos las ventas filtradas y ordenadas como JSON.
	}
}
