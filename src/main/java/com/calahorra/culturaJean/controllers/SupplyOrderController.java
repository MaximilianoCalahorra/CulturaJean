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
	//Atributo:
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
