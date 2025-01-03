package com.calahorra.culturaJean.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.dtos.PaginatedPurchaseDTO;
import com.calahorra.culturaJean.dtos.PaginatedSupplyOrderDTO;
import com.calahorra.culturaJean.dtos.PurchaseFiltersDataDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderFiltersDataDTO;
import com.calahorra.culturaJean.entities.Member;
import com.calahorra.culturaJean.entities.UserRole;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IPurchaseService;
import com.calahorra.culturaJean.services.ISupplyOrderService;
import com.calahorra.culturaJean.services.implementation.MemberService;
import com.calahorra.culturaJean.services.implementation.UserRoleService;

///Clase MemberController:
@Controller
public class MemberController 
{
	//Atributos:
	private IPurchaseService purchaseService;
	private ISupplyOrderService supplyOrderService;
	private MemberService memberService;
	private UserRoleService userRoleService;
	
	//Constructor:
	public MemberController(IPurchaseService purchaseService, ISupplyOrderService supplyOrderService, MemberService memberService,
							UserRoleService userRoleService) 
	{
		this.purchaseService = purchaseService;
		this.supplyOrderService = supplyOrderService;
		this.memberService = memberService;
		this.userRoleService = userRoleService;
	}
	
	//Respondemos a las peticiones de /login cargando la vista que permite loguearse:
	@GetMapping("/login")
	public String login(Model model,
						@RequestParam(name="error",required=false) String error,
						@RequestParam(name="logout", required=false) String logout)
	{
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return ViewRouteHelper.LOGIN;
	}

	//Respondemos a las peticiones /logout cerrando la sesión y redirigiendo a la vista del visitante:
	@GetMapping("/logout")
	public String logout(Model model) 
	{
		return ViewRouteHelper.VISITOR_INDEX;
	}

	//En caso de que el login sea exitoso, redireccionamos al controlador que presentará el index correspondiente al usuario:
	@GetMapping("/loginsuccess")
	public RedirectView loginCheck() 
	{
		return new RedirectView(ViewRouteHelper.REDIRECT_INDEX);
	}
	
	//Respondemos a las peticiones de registro de visitante como cliente presentando una vista con un formulario:
	@GetMapping("/register")
	public ModelAndView registerForm() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.REGISTER_FORM);
		
		modelAndView.addObject("customer", new Member()); //Agregamos un Member vacío para ser cargado a la vista.
		
		return modelAndView; //Retornamos la vista con el DTO adjunto.
	}
	
	//Respondemos al envío del formulario con los datos del visitante que quiere registrarse como cliente:
	@PostMapping("/registerAdd")
	public ModelAndView registerAdd(@ModelAttribute("customer")Member customer) 
	{
		//Suponemos que no va a ser posible registrar al visitante como cliente, por lo que le cargamos nuevamente el formulario:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.REGISTER_FORM);
		
		//Verificamos que el email tenga un formato válido:
		if(memberService.validateEmail(customer.getEmail()))
		{
			//Ya tenemos el que va a ser nuestro nuevo cliente con toda su información cargada.
			//Ahora debemos encriptar la contraseña que nos dio y definirlo como habilitado para poder guardarlo en la base de datos:
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			customer.setPassword(pe.encode(customer.getPassword()));
			customer.setEnabled(true); 
			
			//Intentamos guardar el cliente en la base de datos junto a su rol de cliente:
			try 
			{
				//Se insertará el cliente solo si no hay otro miembro con el mismo username:
				MemberDTO newCustomer = memberService.insert(customer);
				//Como el cliente pudo ser insertado, ahora insertamos su rol:
				UserRole userRole = new UserRole(customer, "ROLE_CUSTOMER");
				userRoleService.insert(userRole);
				
				//Definimos la vista a la cual dirigimos al cliente y adjuntamos los datos del mismo a la vista:
				modelAndView.setViewName(ViewRouteHelper.POST_REGISTER);
				modelAndView.addObject("newCustomer", newCustomer);
			}
			catch(Exception e) 
			{
				//Debido a que se encontró otro miembro con el mismo username:
				modelAndView.addObject("customer", new Member()); //Adjuntamos nuevamente un objeto Member para poder cargarlo con los nuevos datos.
				modelAndView.addObject("error", e.getMessage()); //Adjuntamos un mensaje que indique el error ocurrido como indicación para el visitante.
			}
		}
		else 
		{
			//En caso de que no sea válido, adjuntamos el mensaje de error a la vista:
			modelAndView.addObject("error", "The email " + customer.getEmail() + " is invalid.");
		}
			
		return modelAndView; //Dirigimos al visitante a la vista que corresponda según el resultado de la inserción.
	}
	
	//Respondemos a las peticiones de acceso al perfil del cliente presentando la vista:
	@GetMapping("/myAccount/customer")
	public ModelAndView myAccountCustomer()
											
	{
		//Definimos la vista a cargar:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MY_ACCOUNT_CUSTOMER);
		
		//Obtenemos el cliente que inició sesión:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Obtenemos el DTO del cliente:
		MemberDTO member = memberService.findByUsername(user.getUsername());
	
		String defaultOrder = "p.date_time DESC"; //Definimos el criterio de ordenamiento por defecto.
		
		PurchaseFiltersDataDTO filters = new PurchaseFiltersDataDTO(); //Definimos todos los filtros en su estado por defecto.
		filters.setOrder(defaultOrder); //Pasamos el criterio de ordenamiento.
		filters.setUsernames(Arrays.asList(member.getUsername())); //Pasamos el username del cliente.
		int page = 0; //Definimos que es la primera página.
		int size = 6; //Definimos la cantidad de elementos de la página.
		
		PaginatedPurchaseDTO paginated = purchaseService.getFilteredPurchases(filters, page, size); //Obtenemos las compras de la página.
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", defaultOrder); //Adjuntamos el criterio de ordenamiento elegido.
		modelAndView.addObject("methodOfPay", "all"); //Adjuntamos el método de pago aplicado como filtro.
		modelAndView.addObject("fromPrice", ""); //Adjuntamos el filtro por mayores o iguales a un precio.
		modelAndView.addObject("untilPrice", ""); //Adjuntamos el filtro por menores o iguales a un precio.
		modelAndView.addObject("rangeFromPrice", ""); //Adjuntamos el filtro por mayores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("rangeUntilPrice", ""); //Adjuntamos el filtro por menores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("member", member); //Adjuntamos el cliente.
		modelAndView.addObject("methodsOfPay", paginated.getFiltersOptions().getMethodsOfPay()); //Adjuntamos las opciones de método de pago.
		modelAndView.addObject("paginated", paginated); //Adjuntamos el paginado.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las peticiones de filtrado/ordenamiento de las compras:
	@PostMapping("/myAccount/customer/filter")
	public ResponseEntity<PaginatedPurchaseDTO> filteredPurchases(@RequestBody PurchaseFiltersDataDTO filters, 
																  @RequestParam("page")int page, @RequestParam("size")int size) 
	{
		return ResponseEntity.ok(purchaseService.getFilteredPurchases(filters, page, size)); //Retornamos el paginado.
	}
	
	//Respondemos a las peticiones de acceso al perfil del administrador presentando la vista:
	@GetMapping("/myAccount/admin")
	public ModelAndView myAccountAdmin() 
	{
		//Definimos la vista a cargar:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MY_ACCOUNT_ADMIN);
		
		//Obtenemos el administrador que inició sesión:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//Obtenemos el DTO del administrador:
		MemberDTO member = memberService.findByUsername(user.getUsername());
		
		String defaultOrder = "p.code ASC"; //Definimos el criterio de ordenamiento por defecto.
		
		SupplyOrderFiltersDataDTO filters = new SupplyOrderFiltersDataDTO(); //Definimos todos los filtros en su estado por defecto.
		filters.setOrder(defaultOrder); //Pasamos el criterio de ordenamiento.
		filters.setAdminUsernames(Arrays.asList(member.getUsername())); //Pasamos el username del administrador.
		int page = 0; //Definimos que es la primera página.
		int size = 10; //Definimos la cantidad de elementos de la página.
		
		 //Obtenemos los pedidos de aprovisionamiento de la página:
		PaginatedSupplyOrderDTO paginated = supplyOrderService.getFilteredSupplyOrders(filters, page, size);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", defaultOrder); //Adjuntamos el criterio de ordenamiento.
		modelAndView.addObject("pCode", "all"); //Adjuntamos el código del producto del filtro.
		modelAndView.addObject("sName", "all"); //Adjuntamos el nombre del proveedor del filtro.
		modelAndView.addObject("delivered", "all"); //Adjuntamos el filtro de estado de entrega.
		modelAndView.addObject("member", member); //Adjuntamos el administrador.
		modelAndView.addObject("paginated", paginated); //Adjuntamos el paginado.
		modelAndView.addObject("productCodes", paginated.getFiltersOptions().getProductCodes()); //Adjuntamos los códigos de los productos.
		modelAndView.addObject("supplierNames", paginated.getFiltersOptions().getSupplierNames()); //Adjuntamos los nombres de los proveedores.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las peticiones de filtrado y/u ordenamiento de los pedidos generados por el administrador:
	@PostMapping("/myAccount/admin/filter")
	public ResponseEntity<PaginatedSupplyOrderDTO> filteredSupplyOrders(@RequestBody SupplyOrderFiltersDataDTO filters,
																	 @RequestParam("page")int page, @RequestParam("size")int size) 
	{	
		return ResponseEntity.ok(supplyOrderService.getFilteredSupplyOrders(filters, page, size)); //Retornamos el paginado.
	}
	
	//Respondemos a la solicitud de modificación del perfil con una vista que tiene un formulario para ello:
	@GetMapping("/modifyProfileForm/{role}/{username}")
	public ModelAndView modifyProfileForm(@PathVariable("role")String role, @PathVariable("username")String username) 
	{
		//La vista a presentar será la que permite modificar los datos de la cuenta:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MODIFY_PROFILE_FORM); 
		
		//Obtenemos el miembro del cual se quiere modificar la información:
		Member member = memberService.findByUsernameAndFetchUserRolesEagerly(username);
		
		//Agregamos el miembro y su rol a la vista:
		modelAndView.addObject("role", role);
		modelAndView.addObject("member", member);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos al intento de modificación del perfil del miembro:
	@PostMapping("/modifyProfile/{role}")
	public ModelAndView modifyProfile(@PathVariable("role")String role, @ModelAttribute("member")Member member) 
	{
		//Suponemos que no se va a poder completar la modificación del perfil, por lo que la vista a mostrar será la del formulario nuevamente
		//con un mensaje indicando el error:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MODIFY_PROFILE_FORM);
		
		//Verificamos que el email tenga un formato válido:
		if(memberService.validateEmail(member.getEmail())) 
		{
			//Tenemos el miembro con toda su información cargada.
			//Ahora debemos encriptar la contraseña que nos dio para poder actualizarlo en la base de datos:
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			member.setPassword(pe.encode(member.getPassword()));
			
			//Intentamos actualizar el cliente en la base de datos:
			try 
			{
				//Se actualizará el cliente solo si no hay otro miembro con el mismo username o si es el propietario de ese username pero modifica
				//algún otro dato:
				MemberDTO updatedMember = memberService.update(member);
				
				//Definimos la vista a la cual dirigimos al miembro y adjuntamos los datos del mismo a la vista:
				modelAndView.setViewName(ViewRouteHelper.POST_MODIFY_PROFILE);
				modelAndView.addObject("updatedMember", updatedMember);
			}
			catch(Exception e) 
			{
				//Debido a que se encontró otro miembro con el mismo username:
				modelAndView.addObject("member", member); //Adjuntamos nuevamente el objeto Member para poder cargarlo con los nuevos datos.
				modelAndView.addObject("error", e.getMessage()); //Adjuntamos un mensaje que indique el error ocurrido como indicación para el miembro.
			}
		}
		else 
		{
			//En caso de que no sea válido, adjuntamos el mensaje de error a la vista:
			modelAndView.addObject("error", "The email " + member.getEmail() + " is invalid.");
		}
		
		modelAndView.addObject("role", role); //Agregamos el rol del miembro.
		
		return modelAndView; //Retornamos la vista e información que corresponda según el resultado de la operación.
	}
}
