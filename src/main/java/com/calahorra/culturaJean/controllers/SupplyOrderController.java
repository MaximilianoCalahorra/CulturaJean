package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.SupplierDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderFiltersDataDTO;
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
	
	//Respondemos a las peticiones para cargar todos los pedidos:
    @GetMapping("/supplyOrders")
    public ModelAndView supplyOrders()
    {
    	//Definimos la vista a cargar:
    	ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUPPLY_ORDERS);
    	
    	//Obtenemos los pedidos de aprovisionamiento sin entregar:
		List<SupplyOrderDTO> undeliveredSupplyOrders = supplyOrderService.findByDelivered(false);
		
		//Los ordenamos por código de producto de forma alfabética:
		String orderUndelivered = "orderAscByProductCode";
		undeliveredSupplyOrders = supplyOrderService.applyOrder(undeliveredSupplyOrders, orderUndelivered);
		
		//Obtenemos los pedidos de aprovisionamiento entregados:
		List<SupplyOrderDTO> deliveredSupplyOrders = supplyOrderService.findByDelivered(true);
		
		//Los ordenamos por código de producto de forma alfabética:
		String orderDelivered = "orderAscByProductCode";
		deliveredSupplyOrders = supplyOrderService.applyOrder(deliveredSupplyOrders, orderDelivered);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("undeliveredSupplyOrders", undeliveredSupplyOrders); //Adjuntamos los pedidos de aprovisionamiento sin entregar.
		modelAndView.addObject("orderU", orderUndelivered); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("pCodeU", "all"); //Adjuntamos el criterio aplicado para el filtro de códigos de producto.
		modelAndView.addObject("sNameU", "all"); //Adjuntamos el criterio aplicado para el filtro de nombres de proveedor.
		modelAndView.addObject("usernameU", "all"); //Adjuntamos el criterio aplicado para el filtro de usernames de administrador.
		
		modelAndView.addObject("deliveredSupplyOrders", deliveredSupplyOrders); //Adjuntamos los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("orderD", orderDelivered); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("pCodeD", "all"); //Adjuntamos el criterio aplicado para el filtro de códigos de producto.
		modelAndView.addObject("sNameD", "all"); //Adjuntamos el criterio aplicado para el filtro de nombres de proveedor.
		modelAndView.addObject("usernameD", "all"); //Adjuntamos el criterio aplicado para el filtro de usernames de administrador.
		
		modelAndView.addObject("productCodesU", supplyOrderService.findUniqueEachProductCode(undeliveredSupplyOrders)); //Adjuntamos los códigos de los productos de pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("supplierNamesU", supplyOrderService.findUniqueEachSupplierName(undeliveredSupplyOrders)); //Adjuntamos los nombres de los proveedores de pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("adminUsernamesU", supplyOrderService.findUniqueEachAdminUsername(undeliveredSupplyOrders)); //Adjuntamos los nombres de usuario de los administradores con pedidos de aprovisionamiento no entregados.
		
		modelAndView.addObject("productCodesD", supplyOrderService.findUniqueEachProductCode(deliveredSupplyOrders)); //Adjuntamos los códigos de los productos de pedidos de aprovisionamiento entregados.
		modelAndView.addObject("supplierNamesD", supplyOrderService.findUniqueEachSupplierName(deliveredSupplyOrders)); //Adjuntamos los nombres de los proveedores de pedidos de aprovisionamiento entregados.
		modelAndView.addObject("adminUsernamesD", supplyOrderService.findUniqueEachAdminUsername(deliveredSupplyOrders)); //Adjuntamos los nombres de usuario de los administradores con pedidos de aprovisionamiento entregados.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
    }
    
    //Respondemos a las solicitudes de filtrado/ordenamiento sobre los pedidos de aprovisionamiento entregados/no entregados:
    @PostMapping("/supplyOrders/filter")
    public ResponseEntity<List<SupplyOrderDTO>> filteredSupplyOrders(@RequestBody SupplyOrderFiltersDataDTO filtersData) 
    {
    	//Obtenemos los valores seleccionados para hacer el filtrado y ordenamiento:
    	String order = filtersData.getOrder();
    	List<String> productCodes = filtersData.getProductCodes();
    	List<String> supplierNames = filtersData.getSupplierNames();
    	List<String> adminUsernames = filtersData.getAdminUsernames();
    	int amount = Integer.parseInt(filtersData.getAmount());
    	int fromAmount = Integer.parseInt(filtersData.getFromAmount());
    	int untilAmount = Integer.parseInt(filtersData.getUntilAmount());
    	int rangeFromAmount = Integer.parseInt(filtersData.getRangeFromAmount());
    	int rangeUntilAmount = Integer.parseInt(filtersData.getRangeUntilAmount());
    	boolean delivered = Boolean.parseBoolean(filtersData.getDelivered());
    	
    	//Obtenemos los pedidos de aprovisionamiento entregados/no entregados sin filtrar ni ordenar:
		List<SupplyOrderDTO> supplyOrders = supplyOrderService.findByDelivered(delivered);
		
		//Aplicamos los filtros seleccionados:	
		supplyOrders = supplyOrderService.applyFilters(supplyOrders, productCodes, supplierNames, adminUsernames, amount, fromAmount, 
													   untilAmount, rangeFromAmount, rangeUntilAmount);
	
		//Aplicamos el ordenamiento seleccionado:
		supplyOrders = supplyOrderService.applyOrder(supplyOrders, order);
		
        return ResponseEntity.ok(supplyOrders); //Retornamos los pedidos filtrados y ordenados como JSON.
    }
	
	//Respondemos a las petición de registrar como entregado un pedido de aprovisionamiento:
  	@PostMapping("/registerDelivered/{supplyOrderId}")
  	@ResponseBody
  	public Map<String, Object> registerDelivered(@PathVariable("supplyOrderId")int supplyOrderId) 
  	{
  		//Definimos un objeto donde guardaremos el resultado de la operación:
  		Map<String, Object> response = new HashMap<>();
  		
  		//Intentamos modificar el pedido en la base de datos con su nuevo estado:
  		try 
  		{
  			//Obtenemos el pedido de aprovisionamiento con el id indicado en la URL:
  			SupplyOrderDTO supplyOrder = supplyOrderService.findBySupplyOrderIdWithProductAndMemberAndSupplier(supplyOrderId);
  			supplyOrder.setDelivered(true); //Seteamos el pedido de aprovisionamiento como entregado.
  			supplyOrderService.insert(supplyOrder); //Insertamos el pedido de aprovisionamiento actualizado en la base de datos.
  			
  			//Adjuntamos el resultado al JSON:
  			response.put("success", true);
  		} 
  		catch(Exception e)
  		{
  			response.put("success", false);
  	        response.put("error", e.getMessage());
  		}
  		
  		return response; //Retornamos el JSON para modificar el frontend.
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
