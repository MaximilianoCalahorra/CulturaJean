package com.calahorra.culturaJean.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.PurchaseDTO;
import com.calahorra.culturaJean.dtos.PurchaseItemDTO;
import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.entities.Purchase;
import com.calahorra.culturaJean.entities.PurchaseItem;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IPurchaseItemService;
import com.calahorra.culturaJean.services.IPurchaseService;
import com.calahorra.culturaJean.services.IStockService;
import com.calahorra.culturaJean.services.implementation.MemberService;

///Clase PurchaseController:
@Controller
@SessionAttributes("purchaseItems") //Le indicamos a Spring que queremos almacenar "purchaseItems".
@RequestMapping("/purchase")
public class PurchaseController 
{
	//Atributos:
	private IPurchaseService purchaseService;
	private IPurchaseItemService purchaseItemService;
	private IProductService productService;
	private IStockService stockService;
	private MemberService memberService;
	
	//Constructor:
	public PurchaseController(IPurchaseService purchaseService, IPurchaseItemService purchaseItemService, IProductService productService,
							  IStockService stockService, MemberService memberService) 
	{
		this.purchaseService = purchaseService;
		this.purchaseItemService = purchaseItemService;
		this.productService = productService;
		this.stockService = stockService;
		this.memberService = memberService;
	}
	
	//Método que inicializa el mapa de ítems de la compra si no existe en la sesión:
	@ModelAttribute("purchaseItems")
	public Map<Integer, PurchaseItemDTO> initializePurchaseItems() 
	{
		return new HashMap<>(); //Devuelve un mapa vacío al principio.
	}
	
	//Método para mostrar los ítems de la compra:
	@GetMapping("/purchaseItems")
	public ModelAndView showPurchaseItems(@ModelAttribute("purchaseItems") Map<Integer, PurchaseItemDTO> purchaseItems) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PURCHASE_ITEMS); //Definimos la vista a mostrar.

		float purchaseTotal = 0; //Definimos un acumulador para obtener el total de la compra.
		
		//Obtenemos el stock actual de cada producto que se quiere comprar para saber hasta cuántos se puede pedir:
		List<Integer> actualAmounts = new ArrayList<>(); //Definimos una lista en donde guardaremos esas cantidades.

		////Recorremos los ítems de la compra para hallar esos valores y el total de la compra:
		for(PurchaseItemDTO purchaseItem : purchaseItems.values()) 
		{
			purchaseTotal += purchaseItem.calculateSubtotalSale(); //Acumulamos el subtotal del ítem.
			
			//Del ítem de la compra obtenemos el producto, a partir de este su stock asociado y de este útlimo la cantidad actual para agregarla a la lista:
			actualAmounts.add(stockService.findByProduct(purchaseItem.getProduct().getProductId()).getActualAmount());
		}

		modelAndView.addObject("purchaseTotal", purchaseTotal); //Cargamos el total de la compra en la vista.
		modelAndView.addObject("actualAmounts", actualAmounts); //Cargamos la lista de stocks actuales en la vista.
		modelAndView.addObject("purchaseItems", purchaseItems); //Enviamos los ítems a la vista como una colección.
		//modelAndView.addObject("purchaseItems", purchaseItems.values()); //Enviamos los ítems a la vista como una colección.

		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Método para agregar un ítem a la compra:
	@PostMapping("/addPurchaseItem")
	public RedirectView addPurchaseItem(@RequestParam("productId") int productId, @RequestParam("amount") int amount,
										@ModelAttribute("purchaseItems") Map<Integer, PurchaseItemDTO> purchaseItems) 
	{
		ProductDTO product = productService.findDTOByProductId(productId); //Obtenemos el producto que se quiere agregar al carrito.

		//Verificamos si el producto ya está en el carrito:
		if(purchaseItems.containsKey(productId))
		{
			PurchaseItemDTO purchaseItem = purchaseItems.get(productId); //Obtenemos el ítem que tiene ese producto.
			
			int amountStock = stockService.findByProduct(productId).getActualAmount();
			
			if((purchaseItem.getAmount() + amount) <= amountStock) 
			{
				//Actualizamos la cantidad acumulando a la actual la que se pidió sumar.
				purchaseItem.setAmount(purchaseItem.getAmount() + amount); 
			}
		}
		else
		{
			//Como no existe un ítem con ese producto, debemos crear uno nuevo y agregarlo al mapa:s
			PurchaseItemDTO purchaseItem = new PurchaseItemDTO(product, amount); //Instanciamos un nuevo ítem con el producto y cantidad indicados.
			purchaseItems.put(productId, purchaseItem); //Agregamos el ítem al mapa.
		}

		return new RedirectView(ViewRouteHelper.REDIRECT_PURCHASE_ITEMS); //Redirigimos al método que muestra los ítems de la compra.
	}
	
	//Método para remover un ítem de la compra:
	@GetMapping("/removePurchaseItem/{productId}")
	public RedirectView removePurchaseItem(@PathVariable("productId") int productId,
										   @ModelAttribute("purchaseItems") Map<Integer, PurchaseItemDTO> purchaseItems)
	{
		purchaseItems.remove(productId); //Removemos el ítem del producto indicado.
		return new RedirectView(ViewRouteHelper.REDIRECT_PURCHASE_ITEMS); //Redirigimos al método que muestra los ítems de la compra.
	}
	
	//Método para limpiar la lista de ítems de la compra:
	@GetMapping("/clearPurchaseItems")
	public RedirectView clearPurchaseItems(SessionStatus sessionStatus) 
	{
		sessionStatus.setComplete(); //Limpiamos los datos almacenados en la sesión.
		return new RedirectView(ViewRouteHelper.REDIRECT_PURCHASE_ITEMS); //Redirigimos al método que muestra los ítems de la compra.
	}
	
	//Método para mostrar el formulario de compra:
	@GetMapping("/purchaseForm")
	public ModelAndView showPurchaseForm(@ModelAttribute("purchaseItems") Map<Integer, PurchaseItemDTO> purchaseItems)
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PURCHASE_FORM); //Definimos la vista a cargar.

		//Obtenemos el cliente logueado:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Obtenemos el DTO del cliente:
		MemberDTO member = memberService.findByUsername(user.getUsername());

		//Definimos una compra con el cliente y los ítems:
		PurchaseDTO purchase = new PurchaseDTO(member, new HashSet<>(purchaseItems.values()));

		modelAndView.addObject("purchase", purchase); //Agregamos la compra.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Método para concluir el proceso de compra:
	@PostMapping("/savePurchase")
	public ModelAndView savePurchase(@ModelAttribute("purchaseItems") Map<Integer, PurchaseItemDTO> purchaseItems,
									 @ModelAttribute("purchase") PurchaseDTO purchaseDTO, SessionStatus sessionStatus)
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.POST_PURCHASE); //Definimos la vista a cargar.

		//Definimos un listado de Strings donde guardaremos el mensaje de excepción por stock insuficiente de cada ítem:
		List<String> messagesItemsWithoutStock = new ArrayList<>();

		//Analizamos cada ítem de la compra para que se realicen las bajas de stock de los ítems que se pueden satisfacer:
		Iterator<Map.Entry<Integer, PurchaseItemDTO>> iterator = purchaseItems.entrySet().iterator(); //Definimos un objeto Iterator para el mapa.

		//Mientras haya un ítem de la compra por analizar:
		while(iterator.hasNext()) 
		{
			Map.Entry<Integer, PurchaseItemDTO> entry = iterator.next();
			PurchaseItemDTO purchaseItem = entry.getValue(); //Obtenemos ese ítem de la compra.
			try
			{	
				int productId = purchaseItem.getProduct().getProductId(); //Obtenemos el id del producto que tiene el ítem.
				int orderAmount = purchaseItem.getAmount(); //Obtenemos la cantidad pedida del producto.

				//Intentamos satisfacer la demanda del ítem decrementando la cantidad de los lotes que sean necesarios y actualizamos la
				//información del stock y los lotes afectados:
				stockService.decreaseStock(productId, orderAmount);
				
				//Verificamos si es necesario realizar un pedido de aprovisionamiento automático debido a que el stock del producto perforó
				//o igualó el nivel mínimo que se quiere tener:
				//En caso de que sí, con este pedido llevamos la cantidad actual a la deseable al dar de alta el lote que se corresponde con este pedido:
				stockService.generateAutomaticSupplyOrder(productId);
			}
			catch(Exception e) //En caso de que el stock actual no sea suficiente con respecto a la cantidad que indica el ítem:
			{
				//Capturamos la excepción y añadimos el mensaje asociado a la lista de mensajes de ítems sin stock:
				messagesItemsWithoutStock.add(e.getMessage());
				
				iterator.remove(); //Eliminamos del listado de ítems el ítem del cual no hay stock suficiente para satisfacerlo.
			}
		}

		//Agregamos los mensajes de ítems insatisfechos a la vista:
		modelAndView.addObject("messagesItemsWithoutStock", messagesItemsWithoutStock);

		//Obtenemos el cliente logueado:
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Obtenemos el DTO del cliente:
		MemberDTO member = memberService.findByUsername(user.getUsername());

		//En caso de que por lo menos se haya logrado satisfacer la demanda de un ítem:
		if(purchaseItems.size() > messagesItemsWithoutStock.size()) 
		{
			purchaseDTO.setMember(member); //Guardamos el cliente en la compra.
			purchaseDTO.setPurchaseItems(new HashSet<>(purchaseItems.values())); //Guardamos los ítems de la compra.
			purchaseDTO.setDateTime(LocalDateTime.now().withNano(0)); //Establecemos la fecha y hora de la compra.

			PurchaseDTO purchaseSaved = purchaseService.insert(purchaseDTO); //Insertamos la compra en la base de datos.
			purchaseSaved.setDateTime(purchaseSaved.getDateTime().withNano(0)); //Eliminamos los nanosegundos de la hora.
			Purchase purchase = purchaseService.mapDTOToEntity(purchaseSaved); //Obtenemos la entity de la compra guardada.

			//Insertamos cada uno de los ítems de la compra en la base de datos:
			for(PurchaseItemDTO purchaseItemDTO : purchaseDTO.getPurchaseItems()) 
			{
				Product product = productService.mapDTOToEntity(purchaseItemDTO.getProduct()); //Obtenemos la entity del producto.
				PurchaseItem purchaseItem = new PurchaseItem(purchase, product, purchaseItemDTO.getAmount()); //Definimos una entity del ítem.
				purchaseItemService.insert(purchaseItem); //Insertamos la entity del ítem de la compra en la base de datos.
			}

			modelAndView.addObject("purchase", purchaseSaved); //Agregamos la compra a la vista.
			
			sessionStatus.setComplete(); //Eliminamos los ítems del mapa.
		}
		else 
		{
			modelAndView.setViewName(ViewRouteHelper.PURCHASE_FORM); //Definimos que la vista a cargar es la del formulario de compra.
			
			PurchaseDTO purchase = new PurchaseDTO(member, new HashSet<>(purchaseItems.values())); //Definimos una compra con el cliente y los ítems.
			
			modelAndView.addObject("purchase", purchase); //Agregamos la compra a la vista.
		}
			
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Método para aumentar/decrementar la cantidad de un ítem:
	@PostMapping("/updatePurchaseItem")
	@ResponseBody
	public Map<String, Object> updatePurchaseItem(@RequestBody Map<String, Object> data, 
												  @ModelAttribute("purchaseItems") Map<Integer, PurchaseItemDTO> purchaseItems)
	{
	    int productId = Integer.parseInt((String) data.get("productId")); //Obtenemos el id del producto del ítem.
	    int change = (int) data.get("change"); //Obtenemos la cantidad a aumentar/decrementar.

	    //Obtenemos el ítem del carrito que tiene ese producto:
	    PurchaseItemDTO purchaseItem = purchaseItems.get(productId);
	    
	    //Obtenemos la cantidad de stock del producto:
	    int actualAmount = stockService.findByProduct(productId).getActualAmount();
	    
	    int newAmount = purchaseItem.getAmount() + change; //Calculamos lo que sería la nueva cantidad.
	    
	    //Verificamos si podemos aumentar/disminuir la cantidad indicada según el stock actual:
	    if(newAmount >= 1 && newAmount <= actualAmount) 
	    {
	        purchaseItem.setAmount(newAmount); //Actualizamos la cantidad del ítem.
	    }

	    //Calculamos el subtotal y el total de la compra según cómo haya quedado conformado el ítem:
	    float subtotal = purchaseItem.calculateSubtotalSale();
	    float purchaseTotal = (float)purchaseItems.values().stream().mapToDouble(PurchaseItemDTO::calculateSubtotalSale).sum();
	    
	    //Banderas para detectar si la cantidad alcanzó el mínimo o el máximo que se puede comprar:
	    //En principio, suponemos que no:
	    boolean maximumStock = false; 
	    boolean minimumStock = false;
	    
	    if(newAmount == actualAmount) maximumStock = true; //Si alcanzó el máximo, levantamos la bandera correspondiente.
	    if(newAmount == 1) minimumStock = true; //Si alcanzó el mínimo, levantamos la bandera correspondiente.

	    //Preparamos la respuesta en formato JSON:
	    Map<String, Object> response = new HashMap<>();
	    response.put("productId", productId);
	    response.put("amount", purchaseItem.getAmount());
	    response.put("subtotal", subtotal);
	    response.put("purchaseTotal", purchaseTotal);
	    response.put("maximumStock", maximumStock);
	    response.put("minimumStock", minimumStock);

	    return response; //Enviamos la respuesta JSON al frontend.
	}
}
