package com.calahorra.culturaJean.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
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
	
	//Respondemos a las peticiones de información sobre los lotes para el administrador:
	@GetMapping("/lots")
	public ModelAndView lots(@RequestParam(value = "orderSO", defaultValue = "orderDescByAmount")String orderSupplyOrders,
						     @RequestParam(value = "pCode", defaultValue = "all")String productCode,
						     @RequestParam(value = "sName", defaultValue = "all")String supplierName,
							 @RequestParam(value = "amount", defaultValue = "")String amount,
							 @RequestParam(value = "fAmount", defaultValue = "")String fromAmount,
							 @RequestParam(value = "uAmount", defaultValue = "")String untilAmount,
							 @RequestParam(value = "rFAmount", defaultValue = "")String rangeFromAmount,
							 @RequestParam(value = "rUAmount", defaultValue = "")String rangeUntilAmount,
							 @RequestParam(value = "orderL", defaultValue = "orderAscByExistingAmount")String orderLots,
							 @RequestParam(value = "stockId", defaultValue = "all")String stockId,
							 @RequestParam(value = "rDate", defaultValue = "")String receptionDate, 
							 @RequestParam(value = "fRDate", defaultValue = "")String fromReceptionDate,
							 @RequestParam(value = "uRDate", defaultValue = "")String untilReceptionDate,
							 @RequestParam(value = "rFRDate", defaultValue = "")String rangeFromReceptionDate,
							 @RequestParam(value = "rURDate", defaultValue = "")String rangeUntilReceptionDate,
							 @RequestParam(value = "eAmount", defaultValue = "")String existingAmount,
							 @RequestParam(value = "fEAmount", defaultValue = "")String fromExistingAmount,
							 @RequestParam(value = "uEAmount", defaultValue = "")String untilExistingAmount,
							 @RequestParam(value = "rFEAmount", defaultValue = "")String rangeFromExistingAmount,
							 @RequestParam(value = "rUEAmount", defaultValue = "")String rangeUntilExistingAmount) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTS);
		
		//Obtenemos los lotes que pueden ser dados de alta:
		//Primero obtenemos los pedidos de aprovisionamiento que han sido entregados:
		List<SupplyOrderDTO> supplyOrdersToLots = supplyOrderService.findByDelivered(true); 
				
		//Ahora filtramos esos pedidos de aprovisionamiento para quedarnos solo con los que no hayan generado un pedido de aprovisionamiento:
		supplyOrdersToLots = lotService.filterBySupplyOrderWithInexistingLot(supplyOrdersToLots);
		
		//Obtenemos los códigos de productos de pedidos de aprovisionamiento entregados.
		List<String> productCodes = supplyOrderService.findUniqueEachProductCodeDelivered(true); 
		//Sin embargo, hay que filtrar ese listado para quedarnos solo los que estén en pedidos de aprovisionamiento sin lote generado:
		productCodes = lotService.filterByProductCodeOnSupplyOrderWithInexistingLot(supplyOrdersToLots, productCodes); 
		
		//Obtenemos los nombres de proveedores de pedidos de aprovisionamiento entregados.
		List<String> supplierNames = supplyOrderService.findUniqueEachSupplierNameDelivered(true); //Obtenemos los nombres de proveedores de pedidos de aprovisionamiento entregados.
		//Sin embargo, hay que filtrar ese listado para quedarnos solo los que estén en pedidos de aprovisionamiento sin lote generado:
		supplierNames = lotService.filterBySupplierNameOnSupplyOrderWithInexistingLot(supplyOrdersToLots, supplierNames);		
		
		//Aplicamos los filtros seleccionados de las secciones código de producto, nombre de proveedor y cantidad:
		supplyOrdersToLots = supplyOrderService.applyFilters(supplyOrdersToLots, productCode, supplierName, amount, fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount);
		
		//Aplicamos el ordenamiento seleccionado:
		supplyOrdersToLots = supplyOrderService.applyOrder(supplyOrdersToLots, orderSupplyOrders);
		
		//Obtenemos todos los lotes:
		List<LotDTO> lotsRegistered = lotService.getAll();
		
		//Manejo de posibles envíos de ',' en inputs de tipo date vacíos:
		if(receptionDate.equals(",")) receptionDate = "";
		if(fromReceptionDate.equals(",")) fromReceptionDate = "";
		if(untilReceptionDate.equals(",")) untilReceptionDate = "";
		if(rangeFromReceptionDate.equals(",")) rangeFromReceptionDate = "";
		if(rangeUntilReceptionDate.equals(",")) rangeUntilReceptionDate = "";
						
		//Manejo de posibles envíos de ',' de inputs de tipo date al enviar una fecha:
		receptionDate = lotService.verifyOrCorrectValue(receptionDate);
		fromReceptionDate = lotService.verifyOrCorrectValue(fromReceptionDate);
		untilReceptionDate = lotService.verifyOrCorrectValue(untilReceptionDate);
		rangeFromReceptionDate = lotService.verifyOrCorrectValue(rangeFromReceptionDate);
		rangeUntilReceptionDate = lotService.verifyOrCorrectValue(rangeUntilReceptionDate);
		
		//Aplicamos los filtros seleccionados de las secciones stock, fecha de recepción y cantidad existente:
		lotsRegistered = lotService.applyFilters(lotsRegistered, stockId, receptionDate, fromReceptionDate, untilReceptionDate,
												 rangeFromReceptionDate, rangeUntilReceptionDate, existingAmount, fromExistingAmount,
												 untilExistingAmount, rangeFromExistingAmount, rangeUntilExistingAmount);
		
		//Aplicamos el ordenamiento seleccionado:
		lotsRegistered = lotService.applyOrder(lotsRegistered, orderLots);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("orderSO", orderSupplyOrders); //Adjuntamos el criterio de ordenamiento de los pedidos de aprovisionamiento.
		modelAndView.addObject("pCode", productCode); //Adjuntamos el código del producto del filtro.
		modelAndView.addObject("sName", supplierName); //Adjuntamos el nombre del proveedor del filtro.
		modelAndView.addObject("amount", amount); //Adjuntamos la cantidad del filtro de una cantidad específica.
		modelAndView.addObject("fAmount", fromAmount); //Adjuntamos el filtro de la cantidad mayor o igual a una cantidad específica.
		modelAndView.addObject("uAmount", untilAmount); //Adjuntamos el filtro de la cantidad menor o igual a una cantidad específica.
		modelAndView.addObject("rFAmount", rangeFromAmount); //Adjuntamos el filtro de una cantidad mayor o igual en un rango de cantidades.
		modelAndView.addObject("rUAmount", rangeUntilAmount); //Adjuntamos el filtro de una cantidad menor o igual en un rango de cantidades.
		modelAndView.addObject("supplyOrdersToLots", supplyOrdersToLots); //Adjuntamos los pedidos de aprovisionamiento que pueden generar lotes.
		modelAndView.addObject("productCodes", productCodes); //Adjuntamos los códigos de los productos.
		modelAndView.addObject("supplierNames", supplierNames); //Adjuntamos los nombres de los proveedores.
		
		modelAndView.addObject("orderL", orderLots); //Adjuntamos el criterio de ordenamiento de los lotes registados.
		modelAndView.addObject("stockId", stockId); //Adjuntamos el filtro de un stock específico.
		modelAndView.addObject("rDate", receptionDate); //Adjuntamos el filtro de una fecha de recepción específica.
		modelAndView.addObject("fRDate", fromReceptionDate); //Adjuntamos el filtro de la fecha de recepción posterior o igual a una específica.
		modelAndView.addObject("uRDate", untilReceptionDate); //Adjuntamos el filtro de la fecha de recepción anterior o igual a una específica.
		modelAndView.addObject("rFRDate", rangeFromReceptionDate); //Adjuntamos el filtro de la fecha de recepción posterior o igual dentro de un rango.
		modelAndView.addObject("rURDate", rangeUntilReceptionDate); //Adjuntamos el filtro de la fecha de recepción anterior o igual dentro de un rango.
		modelAndView.addObject("eAmount", existingAmount); //Adjuntamos la cantidad existente del filtro de una cantidad específica.
		modelAndView.addObject("fEAmount", fromExistingAmount); //Adjuntamos el filtro de la cantidad existente mayor o igual a una cantidad específica.
		modelAndView.addObject("uEAmount", untilExistingAmount); //Adjuntamos el filtro de la cantidad existente menor o igual a una cantidad específica.
		modelAndView.addObject("rFEAmount", rangeFromExistingAmount); //Adjuntamos el filtro de una cantidad existente mayor o igual en un rango de cantidades.
		modelAndView.addObject("rUEAmount", rangeUntilExistingAmount); //Adjuntamos el filtro de una cantidad existente menor o igual en un rango de cantidades.
		modelAndView.addObject("lotsRegistered", lotsRegistered); //Adjuntamos los lotes registrados.
		modelAndView.addObject("stockIds", lotService.findUniqueEachStockId()); //Adjuntamos los ids de los stocks.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos a la petición de dar de alta un lote:
	@GetMapping("/registerLot/{supplyOrderId}")
	public RedirectView registerLot(@PathVariable("supplyOrderId")int supplyOrderId) 
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
		
		return new RedirectView(ViewRouteHelper.REDIRECT_LOTS); //Dirigimos el flujo para mostrar la vista de lotes nuevamente.
	}
}
