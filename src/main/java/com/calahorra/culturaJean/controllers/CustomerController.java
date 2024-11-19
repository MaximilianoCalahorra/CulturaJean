package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	//Respondemos a las peticiones para cargar todos los clientes:
    @GetMapping("/customers")
    public ModelAndView customers()
    {
    	//Definimos la vista a cargar:
    	ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.CUSTOMERS);
    	
        //Obtenemos todos los clientes:
        List<MemberDTO> customers = memberService.findByUserRole("ROLE_CUSTOMER");
        
        //Los ordenamos por apellido de forma alfabética:
        customers = memberService.inOrderAscByLastName(customers);
        
        //Adjuntamos a la vista los clientes y los criterios de filtro y ordenamiento aplicados:
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("order", "orderAscByLastName");
        modelAndView.addObject("enabled", "all");
        
        return modelAndView; //Retornamos la vista con la información adjunta.
    }
    
    //Respondemos a las solicitudes de filtrado/ordenamiento sobre los clientes:
    @GetMapping("/customers/filter")
    public ResponseEntity<List<MemberDTO>> filteredCustomers(@RequestParam("order") String order, @RequestParam("enabled") String enabled) 
    {
    	//Instanciamos una lista de miembros para cargarla con los clientes filtrados y/u ordenados:
    	List<MemberDTO> customers = new ArrayList<MemberDTO>(); 
		
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

        return ResponseEntity.ok(customers); //Retornamos los clientes como JSON.
    }
    
    //Invertimos el estado del cliente indicado:
  	@PostMapping("/changeEnabled/{memberId}/{enabled}")
  	@ResponseBody
  	public Map<String, Object> changeEnabled(@PathVariable("memberId")int memberId, @PathVariable("enabled")boolean enabled) 
  	{
  		//Definimos un objeto donde guardaremos el resultado de la operación:
  		Map<String, Object> response = new HashMap<>();
  		
  		//Intentamos modificar el cliente en la base de datos con su nuevo estado:
  		try 
  		{
  			//Obtenemos el cliente al que se le quiere cambiar el estado:
  	  		Member customer = memberService.findByMemberIdWithUserRoles(memberId);
  	  		
  	  		customer.setEnabled(!enabled); //Le seteamos el estado al contrario que tiene.
  	  		
  			memberService.update(customer); //Intentamos persistir el cambio.
  			
  			//Adjuntamos la información al JSON:
  			response.put("success", true);
  	        response.put("newStatus", !enabled);
  		} 
  		catch(Exception e)
  		{
  			response.put("success", false);
  	        response.put("error", e.getMessage());
  		}
  		
  		return response; //Retornamos el JSON para modificar el frontend.
  	}
}
