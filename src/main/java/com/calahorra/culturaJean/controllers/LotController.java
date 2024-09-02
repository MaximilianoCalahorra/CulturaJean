package com.calahorra.culturaJean.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ModelAndView lots() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTS);
		
		//Obtenemos los lotes que pueden ser dados de alta:
		//Primero obtenemos los pedidos de aprovisionamiento que han sido entregados:
		List<SupplyOrderDTO> deliveredSupplyOrders = supplyOrderService.findByDelivered(true); 
		
		//Definimos un listado donde estarán los pedidos de aprovisionamiento que pueden dar de alta lotes:
		List<SupplyOrderDTO> supplyOrdersToLots = new ArrayList<SupplyOrderDTO>();
		
		//Recorremos el listado de pedidos de aprovisionamiento entregados para operar solo con los que no tengan un lote asociado:
		for(SupplyOrderDTO supplyOrder: deliveredSupplyOrders) 
		{
			//En caso de que el pedido de aprovisionamiento no tenga un lote asociado:
			if(lotService.findBySupplyOrder(supplyOrder.getSupplyOrderId()) == null) 
			{
				supplyOrdersToLots.add(supplyOrder); //Agregamos el pedido de aprovisionamiento a la lista de los que pueden dar de alta lotes.
			}
		}
		
		//Obtenemos los lotes ordenados de forma ascendente por cantidad existente:
		List<LotDTO> lotsRegistered = lotService.getAllInOrderAscByExistingAmount();
		
		//Agregamos la información a la vista:
		modelAndView.addObject("supplyOrdersToLots", supplyOrdersToLots);
		modelAndView.addObject("lotsRegistered", lotsRegistered);
		
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
