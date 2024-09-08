package com.calahorra.culturaJean.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
							  @RequestParam(value = "methodOfPay", defaultValue = "All")String methodOfPay) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SALES);
		
		List<PurchaseDTO> sales = new ArrayList<PurchaseDTO>(); //Instanciamos una lista de ventas para cargarla con las compras filtrados y/u ordenados.
		
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
		
		//Aplicamos el filtro de fecha que corresponda:
		if(!date.equals("") && fromDate.equals("") && untilDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por fecha en específico.
		{
			LocalDate dateObject = LocalDate.parse(date); //Convertimos la cadena a un objeto LocalDate.
			sales = purchaseService.findByDate(dateObject); //Obtenemos las compras en esa fecha.
		}
		else if(!fromDate.equals("") && date.equals("") && untilDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por posteriores o iguales a una fecha.
		{
			LocalDate fromDateObject = LocalDate.parse(fromDate); //Convertimos la cadena a un objeto LocalDate.
			sales = purchaseService.findByDateAfterThanOrEqual(fromDateObject); //Obtenemos las compras posteriores o iguales a esa fecha.
		}
		else if(!untilDate.equals("") && date.equals("") && fromDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por anteriores o iguales a una fecha.
		{
			LocalDate untilDateObject = LocalDate.parse(untilDate); //Convertimos la cadena a un objeto LocalDate.
			sales = purchaseService.findByDateBeforeThanOrEqual(untilDateObject); //Obtenemos las compras anteriores o iguales a esa fecha.
		}
		else if(!rangeFromDate.equals("") && !rangeUntilDate.equals("") && fromDate.equals("") && untilDate.equals("") && date.equals("")) //Filtramos por un rango de fechas.
		{
			LocalDate rangeFromDateObject = LocalDate.parse(rangeFromDate); //Convertimos la cadena a un objeto LocalDate.
			LocalDate rangeUntilDateObject = LocalDate.parse(rangeUntilDate); //Convertimos la cadena a un objeto LocalDate.
			sales = purchaseService.findByDateRange(rangeFromDateObject, rangeUntilDateObject); //Obtenemos las compras en ese rango de fechas.
		}
		else //Si no hay filtrado por fechas:
		{
			sales = purchaseService.getAll(); //Obtenemos todas las compras.
		}
		
		//Aplicamos el filtro de hora que corresponda:
		if(!fromTime.equals("") && untilTime.equals("") && rangeFromTime.equals("") && rangeUntilTime.equals("")) //Filtramos por posteriores o iguales a una hora.
		{
			LocalTime fromTimeObject = LocalTime.parse(fromTime); //Convertimos la cadena a un objeto LocalTime.
			sales = purchaseService.filterByFromTime(sales, fromTimeObject); //Nos quedamos las compras en esa hora o posteriores.
		}
		else if(!untilTime.equals("") && fromTime.equals("") && rangeFromTime.equals("") && rangeUntilTime.equals("")) //Filtramos por anteriores o iguales a una hora.
		{
			LocalTime untilTimeObject = LocalTime.parse(untilTime); //Convertimos la cadena a un objeto LocalTime.
			sales = purchaseService.filterByUntilTime(sales, untilTimeObject); //Nos quedamos las compras en esa hora o anteriores.
		}
		else if(!rangeFromTime.equals("") && !rangeUntilTime.equals("") && fromTime.equals("") && untilTime.equals("")) //Filtramos por un rango de horas.
		{
			LocalTime rangeFromTimeObject = LocalTime.parse(rangeFromTime); //Convertimos la cadena a un objeto LocalTime.
			LocalTime rangeUntilTimeObject = LocalTime.parse(rangeUntilTime); //Convertimos la cadena a un objeto LocalTime.
			sales = purchaseService.filterByTimeRange(sales, rangeFromTimeObject, rangeUntilTimeObject); //Nos quedamos con las compras en ese rango de horas.
		}
		
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
		
		//Ordenamos el listado de ventas resultante de los procesos anteriores en base al tipo de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByDateTime": sales = purchaseService.inOrderAscByDateTime(sales); break; //Ascendente por fecha y hora.
			case "orderDescByDateTime": sales = purchaseService.inOrderDescByDateTime(sales); break; //Descendente por fecha y hora.
			case "orderAscByUsername": sales = purchaseService.inOrderAscByUsername(sales); break; //Alfabéticamente por nombre de usuario.
			case "orderDescByUsername": sales = purchaseService.inOrderDescByUsername(sales); break; //Inverso al alfabeto por nombre de usuario.
			case "orderAscByMethodOfPay": sales = purchaseService.inOrderAscByMethodOfPay(sales); break; //Alfabéticamente por método de pago.
			case "orderDescByMethodOfPay": sales = purchaseService.inOrderDescByMethodOfPay(sales); break; //Inverso al alfabeto por método de pago.
		}
		
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
		modelAndView.addObject("usernames", purchaseService.getAllUsernames()); //Adjuntamos un listado con los nombres de usuarios que tienen compras.
		modelAndView.addObject("username", username); //Adjuntamos el nombre de usuario aplicado como filtro.
		modelAndView.addObject("methodOfPay", methodOfPay); //Adjuntamos el método de pago aplicado como filtro.
		modelAndView.addObject("sales", sales); //Adjuntamos las ventas.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
