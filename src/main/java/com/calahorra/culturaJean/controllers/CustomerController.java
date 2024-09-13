package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.entities.Member;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.implementation.MemberService;

///Clase CustomerController:
@Controller
@RequestMapping("/customer")
public class CustomerController 
{
	//Atributo:
	private MemberService memberService;
	
	//Constructor:
	public CustomerController(MemberService memberService) 
	{
		this.memberService = memberService;
	}
	
	//Respondemos a las peticiones de información sobre los clientes ordenados/filtrados:
	@GetMapping("/customers")
	public ModelAndView customers(@RequestParam(value = "enabled", defaultValue = "all")String enabled,
								  @RequestParam(value = "order", defaultValue = "orderAscByLastName")String order) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.CUSTOMERS);
		
		List<MemberDTO> customers = new ArrayList<MemberDTO>(); //Instanciamos una lista de miembros para cargarla con los clientes filtrados y/u ordenados.
		
		//Aplicamos el filtro de clientes habilitados/inhabilitados o no:
		switch(enabled) 
		{
			case "all": customers = memberService.findByUserRole("ROLE_CUSTOMER"); break; //Obtenemos todos los clientes.
			case "true": customers = memberService.findByEnabledAndUserRole(true, "ROLE_CUSTOMER"); break; //Obtenemos los clientes habilitados.
			case "false": customers = memberService.findByEnabledAndUserRole(false, "ROLE_CUSTOMER"); break; //Obtenemos los clientes deshabilitados.
		}
		
		//Ordenamos el listado de clientes resultante del proceso anterior en base al tipo de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByName": customers = memberService.inOrderAscByName(customers); break; //Alfabéticamente por nombre.
			case "orderDescByName": customers = memberService.inOrderDescByName(customers); break; //Inverso al alfabeto por nombre.
			case "orderAscByLastName": customers = memberService.inOrderAscByLastName(customers); break; //Alfabéticamente por apellido.
			case "orderDescByLastName": customers = memberService.inOrderDescByLastName(customers); break; //Inverso al alfabeto por apellido.
			case "orderAscByUsername": customers = memberService.inOrderAscByUsername(customers); break; //Alfabéticamente por nombre de usuario.
			case "orderDescByUsername": customers = memberService.inOrderDescByUsername(customers); break; //Inverso al alfabeto por nombre de usuario.
		}
		
		//Agregamos la información a la vista:
		modelAndView.addObject("enabled", enabled); //Indicamos si la lista está filtrada (si es habilitados o inhabilitados) o no.
		modelAndView.addObject("order", order); //Indicamos por cuál atributo está ordenado el listado y en qué sentido.
		modelAndView.addObject("customers", customers); //Agregamos los clientes filtrados y/u ordenados a la vista.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//
	@GetMapping("/changeEnabled/{memberId}/{enabled}")
	public RedirectView changeEnabled(@PathVariable("memberId")int memberId, @PathVariable("enabled")boolean enabled) 
	{
		//Obtenemos el cliente al que se le quiere cambiar el estado:
		Member customer = memberService.findByMemberIdWithUserRoles(memberId);
		
		customer.setEnabled(!enabled); //Le seteamos el estado al contrario que tiene.
		
		//Intentamos modificar el cliente en la base de datos con su nuevo estado:
		try 
		{
			memberService.update(customer);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new RedirectView(ViewRouteHelper.REDIRECT_CUSTOMERS); //Redirigimos el flujo para cargar el listado de clientes nuevamente.
	}
}
