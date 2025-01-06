package com.calahorra.culturaJean.controllers;

import java.util.HashMap;
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

import com.calahorra.culturaJean.dtos.PaginatedMemberDTO;
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
    	
    	String defaultOrder = "m.last_name ASC"; //Definimos el criterio de ordenamiento por defecto.
		String enabled = "all"; //Definimos el filtro de estado por defecto (sin filtrado).
    	
		int page = 0; //Definimos que es la primera página.
		int size = 10; //Definimos la cantidad de elementos de la página.
		
		//Obtenemos los miembros de la página:
		PaginatedMemberDTO paginated = memberService.getFilteredCustomers(enabled, defaultOrder, page, size);
        
        //Adjuntamos a la vista el paginado y el criterio de ordenamiento y filtro por defecto:
        modelAndView.addObject("paginated", paginated);
        modelAndView.addObject("order", defaultOrder);
        modelAndView.addObject("enabled", enabled);
        
        return modelAndView; //Retornamos la vista con la información adjunta.
    }
    
    //Respondemos a las solicitudes de filtrado/ordenamiento sobre los clientes:
    @GetMapping("/customers/filter")
    public ResponseEntity<PaginatedMemberDTO> filteredCustomers(@RequestParam("order") String order, 
    															@RequestParam("enabled") String enabled, @RequestParam("page")int page,
															    @RequestParam("size")int size) 
    {
    	return ResponseEntity.ok(memberService.getFilteredCustomers(enabled, order, page, size)); //Retornamos el paginado.
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
