package com.calahorra.culturaJean.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.dtos.PurchaseDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
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
	public ModelAndView myAccountCustomer(@RequestParam(value = "order", defaultValue = "orderDescByDateTime")String order,
										  @RequestParam(value = "date", defaultValue = "")String date,
										  @RequestParam(value = "fromDate", defaultValue = "")String fromDate,
										  @RequestParam(value = "untilDate", defaultValue = "")String untilDate,
										  @RequestParam(value = "rangeFromDate", defaultValue = "")String rangeFromDate,
										  @RequestParam(value = "rangeUntilDate", defaultValue = "")String rangeUntilDate,
										  @RequestParam(value = "fromTime", defaultValue = "")String fromTime,
										  @RequestParam(value = "untilTime", defaultValue = "")String untilTime,
										  @RequestParam(value = "rangeFromTime", defaultValue = "")String rangeFromTime,
										  @RequestParam(value = "rangeUntilTime", defaultValue = "")String rangeUntilTime,
										  @RequestParam(value = "methodOfPay", defaultValue = "All")String methodOfPay,
										  @RequestParam(value = "fromPurchasePrice", defaultValue = "")String fromPurchasePrice,
										  @RequestParam(value = "untilPurchasePrice", defaultValue = "")String untilPurchasePrice,
										  @RequestParam(value = "rangeFromPurchasePrice", defaultValue = "")String rangeFromPurchasePrice,
										  @RequestParam(value = "rangeUntilPurchasePrice", defaultValue = "")String rangeUntilPurchasePrice)
											
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MY_ACCOUNT_CUSTOMER);
		
		//Obtenemos el cliente que inició sesión:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Obtenemos el DTO del cliente:
		MemberDTO member = memberService.findByUsername(user.getUsername());
		
		List<PurchaseDTO> purchases = purchaseService.findByMember(member.getUsername()); //Obtenemos todas las compras del cliente.
		
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
		purchases = purchaseService.applyFilterTypeDateOnList(purchases, date, fromDate, untilDate, rangeFromDate, rangeUntilDate);
		
		//Aplicamos el filtro de hora que corresponda:
		purchases = purchaseService.applyFilterTypeTime(purchases, fromTime, untilTime, rangeFromTime, rangeUntilTime);
		
		//Aplicamos filtros según corresponda por método de pago:
		if(!methodOfPay.equals("All")) 
		{
			purchases = purchaseService.filterByMethodOfPay(purchases, methodOfPay);
		}
		
		//Aplicamos filtros según corresponda por precio de la compra:
		purchases = purchaseService.applyFilterTypePurchasePrice(purchases, fromPurchasePrice, untilPurchasePrice, rangeFromPurchasePrice, rangeUntilPurchasePrice);
		
		//Ordenamos el listado de ventas resultante de los procesos anteriores en base al tipo de ordenamiento elegido:
		purchases = purchaseService.applyOrder(purchases, order);
		
		//Agregamos la información a la vista:
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
		modelAndView.addObject("methodOfPay", methodOfPay); //Adjuntamos el método de pago aplicado como filtro.
		modelAndView.addObject("fromPurchasePrice", fromPurchasePrice); //Adjuntamos el filtro por mayores o iguales a un precio.
		modelAndView.addObject("untilPurchasePrice", untilPurchasePrice); //Adjuntamos el filtro por menores o iguales a un precio.
		modelAndView.addObject("rangeFromPurchasePrice", rangeFromPurchasePrice); //Adjuntamos el filtro por mayores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("rangeUntilPurchasePrice", rangeUntilPurchasePrice); //Adjuntamos el filtro por menores o iguales a un precio dentro de un rango elegido.
		modelAndView.addObject("member", member); //Adjuntamos el cliente.
		modelAndView.addObject("purchases", purchases); //Adjuntamos su listado de compras.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las peticiones de acceso al perfil del administrador presentando la vista:
	@GetMapping("/myAccount/admin")
	public ModelAndView myAccountAdmin(@RequestParam(value = "order", defaultValue = "orderAscByProductCode")String order,
									   @RequestParam(value = "productCode", defaultValue = "all")String productCode,
									   @RequestParam(value = "supplierName", defaultValue = "all")String supplierName,
									   @RequestParam(value = "amount", defaultValue = "")String amount,
									   @RequestParam(value = "fromAmount", defaultValue = "")String fromAmount,
									   @RequestParam(value = "untilAmount", defaultValue = "")String untilAmount,
									   @RequestParam(value = "rangeFromAmount", defaultValue = "")String rangeFromAmount,
									   @RequestParam(value = "rangeUntilAmount", defaultValue = "")String rangeUntilAmount) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MY_ACCOUNT_ADMIN);
		
		//Obtenemos el administrador que inició sesión:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//Obtenemos el DTO del administrador:
		MemberDTO member = memberService.findByUsername(user.getUsername());
		
		//Obtenemos los pedidos de aprovisionamiento del administrador:
		List<SupplyOrderDTO> supplyOrders = supplyOrderService.findByMember(member.getUsername());
		
		//Aplicamos el filtro seleccionado de la sección código de producto:
		if(!productCode.equals("all")) 
		{
			supplyOrders = supplyOrderService.filterByProductCode(supplyOrders, productCode);
		}
		
		//Aplicamos el filtro seleccionado de la sección nombre de proveedor:
		if(!supplierName.equals("all")) 
		{
			supplyOrders = supplyOrderService.filterBySupplierName(supplyOrders, supplierName);
		}
		
		//Aplicamos el filtro seleccionado de la sección cantidad:
		supplyOrders = supplyOrderService.applyFilterTypeAmount(supplyOrders, amount, fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount);
		
		//Aplicamos el ordenamiento seleccionado:
		supplyOrders = supplyOrderService.applyOrder(supplyOrders, order);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", order); //Adjuntamos el criterio de ordenamiento.
		modelAndView.addObject("productCode", productCode); //Adjuntamos el código del producto del filtro.
		modelAndView.addObject("supplierName", supplierName); //Adjuntamos el nombre del proveedor del filtro.
		modelAndView.addObject("amount", amount); //Adjuntamos la cantidad del filtro de una cantidad específica.
		modelAndView.addObject("fromAmount", fromAmount); //Adjuntamos el filtro de la cantidad mayor o igual a una cantidad específica.
		modelAndView.addObject("untilAmount", untilAmount); //Adjuntamos el filtro de la cantidad menor o igual a una cantidad específica.
		modelAndView.addObject("rangeFromAmount", rangeFromAmount); //Adjuntamos el filtro de una cantidad mayor o igual en un rango de cantidades.
		modelAndView.addObject("rangeUntilAmount", rangeUntilAmount); //Adjuntamos el filtro de una cantidad menor o igual en un rango de cantidades.
		modelAndView.addObject("member", member); //Adjuntamos el administrador.
		modelAndView.addObject("supplyOrders", supplyOrders); //Adjuntamos los pedidos de aprovisionamiento.
		modelAndView.addObject("productCodes", supplyOrderService.findUniqueEachProductCode()); //Adjuntamos los códigos de los productos.
		modelAndView.addObject("supplierNames", supplyOrderService.findUniqueEachSupplierName()); //Adjuntamos los nombres de los proveedores.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a la solicitud de modificación del perfil con una vista que tiene un formulario para ello:
	@GetMapping("/modifyProfileForm/{username}")
	public ModelAndView modifyProfileForm(@PathVariable("username")String username) 
	{
		//La vista a presentar será la que permite modificar los datos de la cuenta:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.MODIFY_PROFILE_FORM); 
		
		//Obtenemos el miembro del cual se quiere modificar la información:
		Member member = memberService.findByUsernameAndFetchUserRolesEagerly(username);
		
		//Agregamos el miembro a la vista:
		modelAndView.addObject("member", member);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos al intento de modificación del perfil del miembro:
	@PostMapping("/modifyProfile")
	public ModelAndView modifyProfile(@ModelAttribute("member")Member member) 
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
		
		return modelAndView; //Retornamos la vista e información que corresponda según el resultado de la operación.
	}
}
