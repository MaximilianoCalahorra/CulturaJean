package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PurchaseDTO;
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
	
	//Respondemos a las peticiones de información sobre las ventas para el administrador:
	@GetMapping("/sales")
	public ModelAndView sales(@RequestParam(value = "order", defaultValue = "orderDescByDateTime")String order,
			  				  @RequestParam(value = "date", defaultValue = "")String date,
							  @RequestParam(value = "fromDate", defaultValue = "")String fromDate,
							  @RequestParam(value = "untilDate", defaultValue = "")String untilDate,
							  @RequestParam(value = "rangeFromDate", defaultValue = "")String rangeFromDate,
							  @RequestParam(value = "rangeUntilDate", defaultValue = "")String rangeUntilDate,
							  @RequestParam(value = "fromTime", defaultValue = "")String fromTime,
							  @RequestParam(value = "untilTime", defaultValue = "")String untilTime,
							  @RequestParam(value = "rangeFromTime", defaultValue = "")String rangeFromTime,
							  @RequestParam(value = "rangeUntilTime", defaultValue = "")String rangeUntilTime,
							  @RequestParam(value = "username", defaultValue = "All")String username,
							  @RequestParam(value = "methodOfPay", defaultValue = "All")String methodOfPay,
							  @RequestParam(value = "fromSalePrice", defaultValue = "")String fromSalePrice,
							  @RequestParam(value = "untilSalePrice", defaultValue = "")String untilSalePrice,
							  @RequestParam(value = "rangeFromSalePrice", defaultValue = "")String rangeFromSalePrice,
							  @RequestParam(value = "rangeUntilSalePrice", defaultValue = "")String rangeUntilSalePrice) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SALES);
		
		//Manejo de posibles envíos de ',' en inputs de tipo date o time vacíos:
		if(date.equals(",")) date = "";
		if(fromDate.equals(",")) fromDate = "";
		if(untilDate.equals(",")) untilDate = "";
		if(rangeFromDate.equals(",")) rangeFromDate = "";
		if(rangeUntilDate.equals(",")) rangeUntilDate = "";
		if(fromTime.equals(",")) fromTime = "";
		if(untilTime.equals(",")) untilTime = "";
		if(rangeFromTime.equals(",")) rangeFromTime = "";
		if(rangeUntilTime.equals(",")) rangeUntilTime = "";
		
		//Manejo de posibles envíos de ',' de inputs de tipo date o time al enviar una fecha u hora, respectivamente:
		date = purchaseService.verifyOrCorrectValue(date);
		fromDate = purchaseService.verifyOrCorrectValue(fromDate);
		untilDate = purchaseService.verifyOrCorrectValue(untilDate);
		rangeFromDate = purchaseService.verifyOrCorrectValue(rangeFromDate);
		rangeUntilDate = purchaseService.verifyOrCorrectValue(rangeUntilDate);
		fromTime = purchaseService.verifyOrCorrectValue(fromTime);
		untilTime = purchaseService.verifyOrCorrectValue(untilTime);
		rangeFromTime = purchaseService.verifyOrCorrectValue(rangeFromTime);
		rangeUntilTime = purchaseService.verifyOrCorrectValue(rangeUntilTime);
		
		
		//Aplicamos filtros según corresponda por fecha y obtenemos el listado inicial:
		List<PurchaseDTO> sales = purchaseService.applyFilterTypeDate(date, fromDate, untilDate, rangeFromDate, rangeUntilDate);
		
		//Aplicamos filtros según corresponda por hora:
		sales = purchaseService.applyFilterTypeTime(sales, fromTime, untilTime, rangeFromTime, rangeUntilTime);
		
		//Aplicamos filtros según corresponda por nombre de usuario:
		if(!username.equals("All")) 
		{
			sales = purchaseService.filterByUsername(sales, username);
		}
		
		//Aplicamos filtros según corresponda por método de pago:
		if(!methodOfPay.equals("All")) 
		{
			sales = purchaseService.filterByMethodOfPay(sales, methodOfPay);
		}
		
		//Aplicamos filtros según corresponda por precio de la venta:
		sales = purchaseService.applyFilterTypePurchasePrice(sales, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice);
		
		//Aplicamos el ordenamiento elegido y obtenemos la lista ordenada según el criterio indicado:
		sales = purchaseService.applyOrder(sales, order);
		
		//Agregamos la siguiente información a la vista:
		modelAndView.addObject("order", order); //Adjuntamos el criterio de ordenamiento elegido.
		modelAndView.addObject("date", date); //Adjuntamos el filtro por fecha específica elegido.
		modelAndView.addObject("fromDate", fromDate); //Adjuntamos el filtro por posteriores o iguales a una fecha elegido.
		modelAndView.addObject("untilDate", untilDate); //Adjuntamos el filtro por anteriores o iguales a una fecha elegido.
		modelAndView.addObject("rangeFromDate", rangeFromDate); //Adjuntamos el filtro de posteriores o iguales a una fecha dentro de un rango elegido.
		modelAndView.addObject("rangeUntilDate", rangeUntilDate); //Adjuntamos el filtro de anteriores o iguales a una fecha dentro de un rango elegido.
		modelAndView.addObject("fromTime", fromTime); //Adjuntamos el filtro por posteriores o iguales a una hora elegido.
		modelAndView.addObject("untilTime", untilTime); //Adjuntamos el filtro por anteriores o iguales a una hora elegido.
		modelAndView.addObject("rangeFromTime", rangeFromTime); //Adjuntamos el filtro de posteriores o iguales a una hora dentro de un rango elegido.
		modelAndView.addObject("rangeUntilTime", rangeUntilTime); //Adjuntamos el filtro de anteriores o iguales a una hora dentro de un rango elegido.
		modelAndView.addObject("usernames", purchaseService.getAllUsernames(sales)); //Adjuntamos un listado con los nombres de usuarios que tienen compras.
		modelAndView.addObject("username", username); //Adjuntamos el nombre de usuario aplicado como filtro.
		modelAndView.addObject("methodOfPay", methodOfPay); //Adjuntamos el método de pago aplicado como filtro.
		modelAndView.addObject("fromSalePrice", fromSalePrice); //Adjuntamos el filtro por mayores o iguales a un precio.
		modelAndView.addObject("untilSalePrice", untilSalePrice); //Adjuntamos el filtro por menores o iguales a un precio.
		modelAndView.addObject("rangeFromSalePrice", rangeFromSalePrice); //Adjuntamos el filtro por mayores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("rangeUntilSalePrice", rangeUntilSalePrice); //Adjuntamos el filtro por menores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("sales", sales); //Adjuntamos las ventas.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
