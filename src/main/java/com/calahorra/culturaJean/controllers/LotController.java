package com.calahorra.culturaJean.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.dtos.LotFiltersDataDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderFiltersDataDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.ILotService;
import com.calahorra.culturaJean.services.IStockService;
import com.calahorra.culturaJean.services.ISupplyOrderService;

///Clase LotController:
@Controller
@RequestMapping("/lot")
public class LotController 
{
	//Atributos:
	private ILotService lotService;
	private ISupplyOrderService supplyOrderService;
	private IStockService stockService;
	
	//Constructor:
	public LotController(ILotService lotService, ISupplyOrderService supplyOrderService, IStockService stockService) 
	{
		this.lotService = lotService;
		this.supplyOrderService = supplyOrderService;
		this.stockService = stockService;
	}
	
	//Cargamos la vista con los pedidos de aprovisionamiento que pueden dar de alta lotes y los lotes que ya están dados de alta:
	@GetMapping("/lots")
	public ModelAndView lots()
	{
		//Definimos la vista a cargar:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTS);
		
		//Obtenemos los lotes que pueden ser dados de alta:
		//Primero obtenemos los pedidos de aprovisionamiento que han sido entregados:
		List<SupplyOrderDTO> supplyOrdersToLots = supplyOrderService.findByDelivered(true); 
				
		//Ahora filtramos esos pedidos de aprovisionamiento para quedarnos solo con los que no hayan generado un pedido de aprovisionamiento:
		supplyOrdersToLots = lotService.filterBySupplyOrderWithInexistingLot(supplyOrdersToLots);
		
		//Aplicamos el ordenamiento por defecto:
		String orderSupplyOrders = "orderDescByAmount";
		supplyOrdersToLots = supplyOrderService.applyOrder(supplyOrdersToLots, orderSupplyOrders);
		
		//Obtenemos todos los lotes:
		List<LotDTO> lotsRegistered = lotService.getAll();
		
		//Aplicamos el ordenamiento por defecto:
		String orderLots = "orderAscByExistingAmount";
		lotsRegistered = lotService.applyOrder(lotsRegistered, orderLots);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("orderSO", orderSupplyOrders); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento.
		modelAndView.addObject("pCode", "all"); //Adjuntamos el código del producto del filtro.
		modelAndView.addObject("sName", "all"); //Adjuntamos el nombre del proveedor del filtro.
		modelAndView.addObject("supplyOrdersToLots", supplyOrdersToLots); //Adjuntamos los pedidos de aprovisionamiento que pueden generar lotes.
		modelAndView.addObject("productCodes", supplyOrderService.findUniqueEachProductCode(supplyOrdersToLots)); //Adjuntamos los códigos de los productos.
		modelAndView.addObject("supplierNames", supplyOrderService.findUniqueEachSupplierName(supplyOrdersToLots)); //Adjuntamos los nombres de los proveedores.
		
		modelAndView.addObject("orderL", orderLots); //Adjuntamos el criterio de ordenamiento de los lotes registrados.
		modelAndView.addObject("stockId", "all"); //Adjuntamos el filtro de un stock específico.
		modelAndView.addObject("lotsRegistered", lotsRegistered); //Adjuntamos los lotes registrados.
		modelAndView.addObject("stockIds", lotService.findUniqueEachStockId(lotsRegistered)); //Adjuntamos los ids de los stocks.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a las peticiones de filtrado y/u ordenamiento sobre los pedidos de aprovisionamiento que pueden dar de alta lotes:
	@PostMapping("/lots/supplyOrdersToLots/filter")
	public ResponseEntity<List<SupplyOrderDTO>> filteredSupplyOrdersToLots(@RequestBody SupplyOrderFiltersDataDTO filtersData)
	{
		//Obtenemos los valores asignados a los filtros:
		String order = filtersData.getOrder();
		List<String> productCodes = filtersData.getProductCodes();
		List<String> supplierNames = filtersData.getSupplierNames();
		List<String> adminUsernames = filtersData.getAdminUsernames();
		int amount = Integer.parseInt(filtersData.getAmount());
    	int fromAmount = Integer.parseInt(filtersData.getFromAmount());
    	int untilAmount = Integer.parseInt(filtersData.getUntilAmount());
    	int rangeFromAmount = Integer.parseInt(filtersData.getRangeFromAmount());
    	int rangeUntilAmount = Integer.parseInt(filtersData.getRangeUntilAmount());
		
		//Obtenemos los lotes que pueden ser dados de alta:
		//Primero obtenemos los pedidos de aprovisionamiento que han sido entregados:
		List<SupplyOrderDTO> supplyOrdersToLots = supplyOrderService.findByDelivered(true); 
		
		//Ahora filtramos esos pedidos de aprovisionamiento para quedarnos solo con los que no hayan generado un pedido de aprovisionamiento:
		supplyOrdersToLots = lotService.filterBySupplyOrderWithInexistingLot(supplyOrdersToLots);
		
		//Aplicamos los filtros seleccionados:
		supplyOrdersToLots = supplyOrderService.applyFilters(supplyOrdersToLots, productCodes, supplierNames, adminUsernames, amount, 
															 fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount);
				
		//Aplicamos el ordenamiento seleccionado:
		supplyOrdersToLots = supplyOrderService.applyOrder(supplyOrdersToLots, order);
		
		return ResponseEntity.ok(supplyOrdersToLots); //Retornamos los pedidos.
	}
	
	//Respondemos a las peticiones de filtrado y/u ordenamiento sobre los lotes:
	@PostMapping("/lots/filter")
	public ResponseEntity<List<LotDTO>> filteredLots(@RequestBody LotFiltersDataDTO filtersData)
	{
		//Obtenemos los valores asignados a los filtros:
		String order = filtersData.getOrder();
		List<String> stockIds = filtersData.getStockIds(); 
		String receptionDate = filtersData.getReceptionDate();
		String fromReceptionDate = filtersData.getFromReceptionDate();
		String untilReceptionDate = filtersData.getUntilReceptionDate();
		String rangeFromReceptionDate = filtersData.getRangeFromReceptionDate();
		String rangeUntilReceptionDate = filtersData.getRangeUntilReceptionDate();
		int amount = Integer.parseInt(filtersData.getAmount());
		int fromAmount = Integer.parseInt(filtersData.getFromAmount());
		int untilAmount = Integer.parseInt(filtersData.getUntilAmount());
		int rangeFromAmount = Integer.parseInt(filtersData.getRangeFromAmount());
		int rangeUntilAmount = Integer.parseInt(filtersData.getRangeUntilAmount());
		
		//Obtenemos todos los lotes:
		List<LotDTO> lots = lotService.getAll();
		
		//Aplicamos los filtros seleccionados:
		lots = lotService.applyFilters(lots, stockIds, receptionDate, fromReceptionDate, untilReceptionDate, rangeFromReceptionDate,
									   rangeUntilReceptionDate, amount, fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount);
		
		//Aplicamos el ordenamiento seleccionado:
		lots = lotService.applyOrder(lots, order);
		
		return ResponseEntity.ok(lots); //Retornamos los lotes.
	}
	
	//Respondemos a la petición de dar de alta un lote:
	@PostMapping("/registerLot/{supplyOrderId}")
	@ResponseBody
	public Map<String, Object> registerLot(@PathVariable("supplyOrderId")int supplyOrderId) 
	{
		//Definimos un objeto donde guardaremos el resultado de la operación:
  		Map<String, Object> response = new HashMap<>();
		
  		//Intentamos llevar adelante el proceso:
  		try 
  		{
  			//Obtenemos el pedido de aprovisionamiento que va a permitir la alta del lote:
  			SupplyOrderDTO supplyOrder = supplyOrderService.findBySupplyOrderIdWithProductAndMemberAndSupplier(supplyOrderId);
  			ProductDTO product = supplyOrder.getProduct(); //Obtenemos el producto asociado al pedido de aprovisionamiento.
  			StockDTO stock = stockService.findByProduct(product.getProductId()); //Obtenemos el stock al que se corresponde el producto.
  			int amount = supplyOrder.getAmount(); //Obtenemos la cantidad pedida del mismo.
  			
  			LotDTO lot = new LotDTO(); //Instanciamos un lote, de momento vacío.
  			
  			//Cargamos el lote con la información que corresponde:
  			lot.setLotId(supplyOrder.getSupplyOrderId()); //El lote tiene el mismo id que el pedido de aprovisionamiento.
  			lot.setStock(stock); //Incluimos el stock en el lote.
  			lot.setSupplyOrder(supplyOrder); //Incluimos el pedido de aprovisionamiento en el lote.
  			lot.setReceptionDate(LocalDate.now()); //Establecemos la fecha de recepción con la actual.
  			lot.setInitialAmount(amount); //La cantidad inicial del lote es la que se pidió.
  			lot.setExistingAmount(amount); //La cantidad existente también es la que se pidió porque es un lote nuevo, sin todavía consumo.
  			lot.setPurchasePrice(product.getCost() * amount); //El precio de compra del lote es el costo del producto multiplicado por la cantidad solicitada.
  			
  			//Insertamos el lote en la base de datos:
  			lotService.insertOrUpdate(lot);
  			
  			//Ahora actualizamos el stock teniendo en cuenta la cantidad que suma el nuevo lote:
  			int actualAmount = stock.getActualAmount() + lot.getInitialAmount(); //Obtenemos la nueva cantidad actual de stock.
  			stock.setActualAmount(actualAmount); //Seteamos la cantidad actual con la nueva.
  			stockService.insertOrUpdate(stock); //Actualizamos el stock en la base de datos para persistir la nueva cantidad.
  			
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
}
