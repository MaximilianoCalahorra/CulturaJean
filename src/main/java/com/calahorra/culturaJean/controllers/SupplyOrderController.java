package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.SupplierDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.entities.Supplier;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.ISupplierService;
import com.calahorra.culturaJean.services.ISupplyOrderService;
import com.calahorra.culturaJean.services.implementation.MemberService;

///Clase SupplyOrderController:
@Controller
@RequestMapping("/supplyOrder")
public class SupplyOrderController 
{
	//Atributos:
	private ISupplyOrderService supplyOrderService;
	private ISupplierService supplierService;
	private IProductService productService;
	private MemberService memberService;
	
	//Constructor:
	public SupplyOrderController(ISupplyOrderService supplyOrderService, ISupplierService supplierService, IProductService productService,
								 MemberService memberService) 
	{
		this.supplyOrderService = supplyOrderService;
		this.supplierService = supplierService;
		this.productService = productService;
		this.memberService = memberService;
	}
	
	
	//Respondemos a las peticiones de información sobre los pedidos de aprovisionamiento para el administrador:
	@GetMapping("/supplyOrders")
	public ModelAndView supplyOrders(@RequestParam(value = "orderD", defaultValue = "orderAscByProductCode")String orderDelivered,
									 @RequestParam(value = "pCodeD", defaultValue = "all")String productCodeDelivered,
									 @RequestParam(value = "sNameD", defaultValue = "all")String supplierNameDelivered,
									 @RequestParam(value = "usernameD", defaultValue = "all")String adminUsernameDelivered,
									 @RequestParam(value = "amountD", defaultValue = "")String amountDelivered,
									 @RequestParam(value = "fAmountD", defaultValue = "")String fromAmountDelivered,
									 @RequestParam(value = "uAmountD", defaultValue = "")String untilAmountDelivered,
									 @RequestParam(value = "rFAmountD", defaultValue = "")String rangeFromAmountDelivered,
									 @RequestParam(value = "rUAmountD", defaultValue = "")String rangeUntilAmountDelivered,
									 @RequestParam(value = "orderU", defaultValue = "orderAscByProductCode")String orderUndelivered,
									 @RequestParam(value = "pCodeU", defaultValue = "all")String productCodeUndelivered,
									 @RequestParam(value = "sNameU", defaultValue = "all")String supplierNameUndelivered,
									 @RequestParam(value = "usernameU", defaultValue = "all")String adminUsernameUndelivered,
									 @RequestParam(value = "amountU", defaultValue = "")String amountUndelivered,
									 @RequestParam(value = "fAmountU", defaultValue = "")String fromAmountUndelivered,
									 @RequestParam(value = "uAmountU", defaultValue = "")String untilAmountUndelivered,
									 @RequestParam(value = "rFAmountU", defaultValue = "")String rangeFromAmountUndelivered,
									 @RequestParam(value = "rUAmountU", defaultValue = "")String rangeUntilAmountUndelivered) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLY_ORDERS);
		
		//Obtenemos los pedidos de aprovisionamiento sin entregar:
		List<SupplyOrderDTO> undeliveredSupplyOrders = supplyOrderService.findByDelivered(false);
		
		//Aplicamos el filtro de nombre de usuario del administrador que generó el pedido de aprovisionamiento:
		if(!adminUsernameUndelivered.equals("all")) 
		{
			undeliveredSupplyOrders = supplyOrderService.filterByAdminUsername(undeliveredSupplyOrders, adminUsernameUndelivered);
		}
		
		//Aplicamos los filtros seleccionados:
		undeliveredSupplyOrders = supplyOrderService.applyFilters(undeliveredSupplyOrders, productCodeUndelivered, supplierNameUndelivered, 
																  amountUndelivered, fromAmountUndelivered, untilAmountUndelivered,
																  rangeFromAmountUndelivered, rangeUntilAmountUndelivered);
		
		//Aplicamos el ordenamiento seleccionado:
		undeliveredSupplyOrders = supplyOrderService.applyOrder(undeliveredSupplyOrders, orderUndelivered);
		
		//Obtenemos los pedidos de aprovisionamiento entregados:
		List<SupplyOrderDTO> deliveredSupplyOrders = supplyOrderService.findByDelivered(true);
		
		//Aplicamos el filtro de nombre de usuario del administrador que generó el pedido de aprovisionamiento:
		if(!adminUsernameDelivered.equals("all")) 
		{
			deliveredSupplyOrders = supplyOrderService.filterByAdminUsername(deliveredSupplyOrders, adminUsernameDelivered);
		}
		
		//Aplicamos los filtros seleccionados:
		deliveredSupplyOrders = supplyOrderService.applyFilters(deliveredSupplyOrders, productCodeDelivered, supplierNameDelivered,
																amountDelivered, fromAmountDelivered, untilAmountDelivered, 
																rangeFromAmountDelivered, rangeUntilAmountDelivered);
		
		//Aplicamos el ordenamiento seleccionado:
		deliveredSupplyOrders = supplyOrderService.applyOrder(deliveredSupplyOrders, orderDelivered);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("orderU", orderUndelivered); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("pCodeU", productCodeUndelivered); //Adjuntamos el código del producto del filtro de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("sNameU", supplierNameUndelivered); //Adjuntamos el nombre del proveedor del filtro de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("usernameU", adminUsernameUndelivered); //Adjuntamos el nombre de usuario del administrador del filtro de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("amountU", amountUndelivered); //Adjuntamos la cantidad del filtro de una cantidad específica de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("fAmountU", fromAmountUndelivered); //Adjuntamos el filtro de la cantidad mayor o igual a una cantidad específica de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("uAmountU", untilAmountUndelivered); //Adjuntamos el filtro de la cantidad menor o igual a una cantidad específica de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("rFAmountU", rangeFromAmountUndelivered); //Adjuntamos el filtro de una cantidad mayor o igual en un rango de cantidades de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("rUAmountU", rangeUntilAmountUndelivered); //Adjuntamos el filtro de una cantidad menor o igual en un rango de cantidades de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("undeliveredSupplyOrders", undeliveredSupplyOrders); //Adjuntamos los pedidos de aprovisionamiento sin entregar.
		
		modelAndView.addObject("orderD", orderDelivered); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("pCodeD", productCodeDelivered); //Adjuntamos el código del producto del filtro de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("sNameD", supplierNameDelivered); //Adjuntamos el nombre del proveedor del filtro de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("usernameD", adminUsernameDelivered); //Adjuntamos el nombre de usuario del administrador del filtro de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("amountD", amountDelivered); //Adjuntamos la cantidad del filtro de una cantidad específica de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("fAmountD", fromAmountDelivered); //Adjuntamos el filtro de la cantidad mayor o igual a una cantidad específica de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("uAmountD", untilAmountDelivered); //Adjuntamos el filtro de la cantidad menor o igual a una cantidad específica de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("rFAmountD", rangeFromAmountDelivered); //Adjuntamos el filtro de una cantidad mayor o igual en un rango de cantidades de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("rUAmountD", rangeUntilAmountDelivered); //Adjuntamos el filtro de una cantidad menor o igual en un rango de cantidades de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("deliveredSupplyOrders", deliveredSupplyOrders); //Adjuntamos los pedidos de aprovisionamiento entregados.
		
		modelAndView.addObject("productCodesU", supplyOrderService.findUniqueEachProductCodeDelivered(false)); //Adjuntamos los códigos de los productos de pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("supplierNamesU", supplyOrderService.findUniqueEachSupplierNameDelivered(false)); //Adjuntamos los nombres de los proveedores de pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("adminUsernamesU", supplyOrderService.findUniqueEachAdminUsernameDelivered(false)); //Adjuntamos los nombres de usuario de los administradores con pedidos de aprovisionamiento no entregados.
		
		modelAndView.addObject("productCodesD", supplyOrderService.findUniqueEachProductCodeDelivered(true)); //Adjuntamos los códigos de los productos de pedidos de aprovisionamiento entregados.
		modelAndView.addObject("supplierNamesD", supplyOrderService.findUniqueEachSupplierNameDelivered(true)); //Adjuntamos los nombres de los proveedores de pedidos de aprovisionamiento entregados.
		modelAndView.addObject("adminUsernamesD", supplyOrderService.findUniqueEachAdminUsernameDelivered(true)); //Adjuntamos los nombres de usuario de los administradores con pedidos de aprovisionamiento entregados.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	/*
	//Respondemos a las peticiones de información sobre los pedidos de aprovisionamiento para el administrador:
	@GetMapping("/supplyOrders")
	public ModelAndView supplyOrders() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLY_ORDERS);
		
		//Obtenemos los pedidos de aprovisionamiento sin entregar:
		List<SupplyOrderDTO> undeliveredsupplyOrders = supplyOrderService.findByDelivered(false);
		
		//Obtenemos los pedidos de aprovisionamiento entregados:
		List<SupplyOrderDTO> deliveredsupplyOrders = supplyOrderService.findByDelivered(true);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("undeliveredSupplyOrders", undeliveredsupplyOrders);
		modelAndView.addObject("deliveredSupplyOrders", deliveredsupplyOrders);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	*/
	
	//Respondemos a las petición de registrar como entregado un pedido de aprovisionamiento:
	@GetMapping("/registerDelivered/{supplyOrderId}")
	public RedirectView registerDelivered(@PathVariable("supplyOrderId")int supplyOrderId) 
	{
		//Obtenemos el pedido de aprovisionamiento con el id indicado en la URL:
		SupplyOrderDTO supplyOrder = supplyOrderService.findBySupplyOrderIdWithProductAndMemberAndSupplier(supplyOrderId);
		supplyOrder.setDelivered(true); //Seteamos el pedido de aprovisionamiento como entregado.
		supplyOrderService.insert(supplyOrder); //Insertamos el pedido de aprovisionamiento actualizado en la base de datos.
		return new RedirectView(ViewRouteHelper.REDIRECT_SUPPLY_ORDERS); //Dirigimos el flujo para mostrar los pedidos de aprovisionamiento.
	}
	
	//Respondemos a las peticiones de generar un nuevo pedido de aprovisionamiento para un producto con una vista que presenta un formulario para ello:
	@GetMapping("/supplyOrderForm/{productId}")
	public ModelAndView supplyOrderForm(@PathVariable("productId")int productId) 
	{
		//Definimos que la vista a cargar será la del formulario:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLY_ORDER_FORM);
		
		//Obtenemos un listado con el nombre de cada proveedor:
		List<String> suppliersNames = new ArrayList<String>(); 
		for(Supplier supplier: supplierService.getAll()) 
		{
			suppliersNames.add(supplier.getName());
		}
		
		//Adjuntamos a la vista el código del producto y la lista de nombres:
		modelAndView.addObject("productCode", productService.findByProductId(productId).getCode());
		modelAndView.addObject("suppliersNames", suppliersNames);
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos al envío de los datos del nuevo pedido de aprovisionamiento agregándolo a la base de datos:
	@PostMapping("/supplyOrderAdd")
	public ModelAndView supplyOrderAdd(@RequestParam("productCode")String productCode, @RequestParam("supplierName")String supplierName,
									   @RequestParam("amount")int amount)
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.POST_ADD_SUPPLY_ORDER);
		
		ProductDTO product = productService.findByCode(productCode); //Obtenemos el producto del pedido de aprovisionamiento.
		SupplierDTO supplier = supplierService.findDTOByName(supplierName); //Obtenemos el proveedor del pedido de aprovisionamiento.
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MemberDTO member = memberService.findByUsername(user.getUsername()); //Obtenemos el administrador que generó el pedido de aprovisionamiento.
		
		//Cargamos un pedido de aprovisionamiento con la información que corresponde:
		SupplyOrderDTO supplyOrder = new SupplyOrderDTO();
		supplyOrder.setProduct(product); //Cargamos el producto.
		supplyOrder.setMember(member); //Cargamos el administrador.
		supplyOrder.setSupplier(supplier); //Cargamos el proveedor.
		supplyOrder.setDelivered(false); //En principio, el pedido de aprovisionamiento no está entregado:
		supplyOrder.setAmount(amount); //Cargamos la cantidad pedida.
		
		SupplyOrderDTO newSupplyOrder = supplyOrderService.insert(supplyOrder); //Insertamos el pedido de aprovisionamiento en la base de datos y obtenemos el DTO.
		
		modelAndView.addObject("newSupplyOrder", newSupplyOrder); //Adjuntamos el nuevo pedido de aprovisionamiento a la vista.
		
		return modelAndView; //Retornamos la vista que muestra la información del nuevo pedido de aprovisionamiento.
	}
}
