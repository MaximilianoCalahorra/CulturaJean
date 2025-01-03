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
import com.calahorra.culturaJean.dtos.PaginatedSupplyOrderDTO;
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
    	
		String defaultOrder = "p.code ASC"; //Definimos el criterio de ordenamiento por defecto.
		
		int page = 0; //Definimos que es la primera página.
		int size = 10; //Definimos la cantidad de elementos de la página.
		
		SupplyOrderFiltersDataDTO filters = new SupplyOrderFiltersDataDTO(); //Definimos todos los filtros en su estado por defecto.
		filters.setOrder(defaultOrder); //Pasamos el criterio de ordenamiento.
		filters.setDelivered("false"); //Queremos los no entregados.
		
		//Obtenemos los pedidos de aprovisionamiento no entregados de la página:
		PaginatedSupplyOrderDTO uPaginated = supplyOrderService.getFilteredSupplyOrders(filters, page, size);
		
		filters.setDelivered("true"); //Queremos los entregados.
		
		//Obtenemos los pedidos de aprovisionamiento no entregados de la página:
		PaginatedSupplyOrderDTO dPaginated = supplyOrderService.getFilteredSupplyOrders(filters, page, size);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("uPaginated", uPaginated); //Adjuntamos el paginado de los pedidos de aprovisionamiento sin entregar.
		modelAndView.addObject("orderU", defaultOrder); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("pCodeU", "all"); //Adjuntamos el criterio aplicado para el filtro de códigos de producto.
		modelAndView.addObject("sNameU", "all"); //Adjuntamos el criterio aplicado para el filtro de nombres de proveedor.
		modelAndView.addObject("usernameU", "all"); //Adjuntamos el criterio aplicado para el filtro de usernames de administrador.
		
		modelAndView.addObject("dPaginated", dPaginated); //Adjuntamos el paginado de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("orderD", defaultOrder); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento entregados.
		modelAndView.addObject("pCodeD", "all"); //Adjuntamos el criterio aplicado para el filtro de códigos de producto.
		modelAndView.addObject("sNameD", "all"); //Adjuntamos el criterio aplicado para el filtro de nombres de proveedor.
		modelAndView.addObject("usernameD", "all"); //Adjuntamos el criterio aplicado para el filtro de usernames de administrador.
		
		modelAndView.addObject("productCodesU", uPaginated.getFiltersOptions().getProductCodes()); //Adjuntamos los códigos de los productos de pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("supplierNamesU", uPaginated.getFiltersOptions().getSupplierNames()); //Adjuntamos los nombres de los proveedores de pedidos de aprovisionamiento no entregados.
		modelAndView.addObject("adminUsernamesU", uPaginated.getFiltersOptions().getAdminUsernames()); //Adjuntamos los nombres de usuario de los administradores con pedidos de aprovisionamiento no entregados.
		
		modelAndView.addObject("productCodesD", uPaginated.getFiltersOptions().getProductCodes()); //Adjuntamos los códigos de los productos de pedidos de aprovisionamiento entregados.
		modelAndView.addObject("supplierNamesD", uPaginated.getFiltersOptions().getSupplierNames()); //Adjuntamos los nombres de los proveedores de pedidos de aprovisionamiento entregados.
		modelAndView.addObject("adminUsernamesD", uPaginated.getFiltersOptions().getAdminUsernames()); //Adjuntamos los nombres de usuario de los administradores con pedidos de aprovisionamiento entregados.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
    }
    
    //Respondemos a las solicitudes de filtrado/ordenamiento sobre los pedidos de aprovisionamiento entregados/no entregados:
    @PostMapping("/supplyOrders/filter")
    public ResponseEntity<PaginatedSupplyOrderDTO> filteredSupplyOrders(@RequestBody SupplyOrderFiltersDataDTO filters, 
    																    @RequestParam("page") int page, @RequestParam("size") int size) 
    {
    	return ResponseEntity.ok(supplyOrderService.getFilteredSupplyOrders(filters, page, size)); //Retornamos el paginado.
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
