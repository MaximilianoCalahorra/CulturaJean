package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.calahorra.culturaJean.dtos.PaginatedProductDTO;
import com.calahorra.culturaJean.dtos.PaginatedStockDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.ProductFiltersDataDTO;
import com.calahorra.culturaJean.dtos.PurchaseItemDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IStockService;

import jakarta.servlet.http.HttpSession;

///Clase ProductController:
@Controller
@RequestMapping("/product")
public class ProductController 
{
	//Atributos:
	private IProductService productService;
	private IStockService stockService;
	
	//Constructor:
	public ProductController(IProductService productService, IStockService stockService) 
	{
		this.productService = productService;
		this.stockService = stockService;
	}
	
	//Respondemos a las peticiones de productos para el cliente/visitante:
	@GetMapping("/products/{role}")
	public ModelAndView products(@PathVariable("role")String role) 
	{
		ModelAndView modelAndView = new ModelAndView();
		
		//Según el rol del usuario que peticionó:
		switch(role) 
		{
			case "customer": modelAndView.setViewName(ViewRouteHelper.SHOP_CUSTOMER); break; //Al cliente le mostramos su vista.
			case "visitor": modelAndView.setViewName(ViewRouteHelper.SHOP_VISITOR); break; //Al visitante le mostramos su vista.
		}
		
		String defaultOrder = "p.name ASC"; //Definimos el criterio de ordenamiento por defecto.
		
		ProductFiltersDataDTO filters = new ProductFiltersDataDTO(); //Definimos todos los filtros en su estado por defecto.
		filters.setOrder(defaultOrder); //Pasamos el criterio de ordenamiento.
		int page = 0; //Definimos que es la primera página.
		int size = 6; //Definimos la cantidad de elementos de la página.
		
		PaginatedProductDTO paginated = productService.getFilteredProducts(filters, page, size); //Obtenemos los productos de la página.
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", defaultOrder); //Adjuntamos el criterio de ordenamiento.
		modelAndView.addObject("cat", "all"); //Adjuntamos la categoría para el filtro.
		modelAndView.addObject("gen", "all"); //Adjuntamos el género para el filtro.
		modelAndView.addObject("size", "all"); //Adjuntamos el talle para el filtro.
		modelAndView.addObject("col", "all"); //Adjuntamos el color para el filtro.
		modelAndView.addObject("sPri", ""); //Adjuntamos el precio de venta para el filtro.
		modelAndView.addObject("fSPri", ""); //Adjuntamos el precio de venta mayor o igual para el filtro.
		modelAndView.addObject("uSPri", ""); //Adjuntamos el precio de venta menor o igual para el filtro.
		modelAndView.addObject("rFSPri", ""); //Adjuntamos el precio mayor o igual de un rango para el filtro.
		modelAndView.addObject("rUSPri", ""); //Adjuntamos el precio menor o igual de un rango para el filtro.
		
		modelAndView.addObject("categories", paginated.getFiltersOptions().getCategories()); //Adjuntamos el listado de categorías de producto.
		modelAndView.addObject("genders", paginated.getFiltersOptions().getGenders()); //Adjuntamos el listado de géneros de producto.
		modelAndView.addObject("sizes", paginated.getFiltersOptions().getSizes()); //Adjuntamos el listado de talles de producto.
		modelAndView.addObject("colors", paginated.getFiltersOptions().getColors()); //Adjuntamos el listado de colores de producto.
		
		modelAndView.addObject("paginated", paginated); //Adjuntamos el paginado.
		
		return modelAndView; //Retornamos la vista con la información obtenida.
	}
	
	//Respondemos a las peticiones de productos ordenados y filtrados para los clientes y visitantes:
	@PostMapping("/products/filter")
	public ResponseEntity<PaginatedProductDTO> productsFiltered(@RequestBody ProductFiltersDataDTO filters, @RequestParam("page")int page,
															    @RequestParam("size")int size) 
	{
		return ResponseEntity.ok(productService.getFilteredProducts(filters, page, size)); //Retornamos el paginado.
	}
	
	//Respondemos a las peticiones de stocks ordenados y filtrados para el administrador:
	@PostMapping("/products/admin/filter")
	public ResponseEntity<PaginatedStockDTO> productsAdminFiltered(@RequestBody ProductFiltersDataDTO filters, 
																   @RequestParam("page")int page, @RequestParam("size")int size) 
	{
		return ResponseEntity.ok(stockService.getFilteredStocks(filters, page, size)); //Retornamos el paginado.
	}
	
	//Invertimos el estado del producto indicado:
  	@PostMapping("/changeEnabled/{productId}/{enabled}")
  	@ResponseBody
  	public Map<String, Object> changeEnabled(@PathVariable("productId")int productId, @PathVariable("enabled")boolean enabled) 
  	{
  		//Definimos un objeto donde guardaremos el resultado de la operación:
  		Map<String, Object> response = new HashMap<>();
  		
  		//Intentamos modificar el producto en la base de datos con su nuevo estado:
  		try 
  		{
  			//Obtenemos el producto al que se le quiere cambiar el estado:
  	  		ProductDTO product = productService.findDTOByProductId(productId);
  	  		
  	  		product.setEnabled(!enabled); //Le seteamos el estado al contrario que tiene.
  	  		
  			productService.update(product); //Intentamos persistir el cambio.
  			
  			//Adjuntamos la información al JSON:
  			response.put("success", true);
  	        response.put("newStatus", !enabled);
  		} 
  		catch(Exception e)
  		{
  			response.put("success", false);
  	        response.put("error", e.getMessage());
  		}
  		
  		return response; //Retornamos el JSON para modificar el frontend.
  	}
	
	//Respondemos a la solicitud de agregar un producto presentando una vista con un formulario para ello:
	@GetMapping("/add")
	public ModelAndView addForm() 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ADD_PRODUCT_FORM); //Definimos la vista a cargar.
		
		modelAndView.addObject("stock", new StockDTO()); //Adjuntamos un objeto StockDTO para cargarlo en la vista.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
	
	//Respondemos al envío de los datos del nuevo producto:
	@PostMapping("/postAdd")
	public ModelAndView postAdd(@ModelAttribute("stock")StockDTO stock, @RequestParam("imageFile")MultipartFile file) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ADD_PRODUCT_FORM); //En principio, la vista a cargar será la del formulario nuevamente.
		
		//Definimos el nombre de la imagen para el producto en base a su código:
		String productCode = stock.getProduct().getCode();
	    int imageNameLimit = productCode.indexOf('-');
	    //El nombre de la imagen será la parte anterior al '-' del código del producto:
	    //Ej: si el código es 'JaM1-S', el nombre de la imagen será 'JaM1' porque lo posterior representa el talle, pero todos tienen la misma imagen.
	    String imageName = productCode.substring(0, imageNameLimit); 
	    stock.getProduct().setImageName(imageName); //Seteamos el nombre de la imagen en el producto.
	    
	    StockDTO newStock = null; //Acá se guardará el stock que se insertó en la base de datos para adjuntarlo a la vista.
	    
	    //Intentamos agregar tanto el producto como su stock a la base de datos:
		try 
		{
			ProductDTO newProduct = productService.insert(stock.getProduct()); //Insertamos el producto.
			
			//Intentamos subir una imagen nueva si el archivo está cargado o simplemente saltamos el proceso:
			if(!productService.uploadImage(newProduct, file)) 
			{
				//Como no se pudo subir la imagen por un error:
				modelAndView.addObject("error", "There is already an image with that name."); //Adjuntamos un mensaje de error a la vista del formulario.
			}
			
			//Ahora vamos a insertar el stock:
			stock.setActualAmount(0); //Seteamos la cantidad actual del mismo en 0.
			stock.setProduct(newProduct); //Seteamos el producto del stock con el que agregamos a la base de datos.
			newStock = stockService.insertOrUpdate(stock); //Insertamos el stock.
		} 
		catch(Exception e) 
		{
			modelAndView.addObject("error", e.getMessage()); //Adjuntamos a la vista el error asociado a la inserción del producto.
		}
	
		//Si se pudo realizar todo el proceso, el stock debe estar cargado:
		if(newStock != null) 
		{
			modelAndView.setViewName(ViewRouteHelper.POST_ADD_PRODUCT); //Definimos que la vista a cargar es una donde presentamos la información del stock del nuevo producto.
			modelAndView.addObject("newStock", newStock); //Adjuntamos la información a la vista.
		}
		
		return modelAndView; //Retornamos la vista y la información adjunta que corresponda según el resultado de la operación.
	}
	
	//Respondemos a la solicitud de editar un producto presentando una vista con un formulario para ello:
	@GetMapping("/edit/{productId}")
	public ModelAndView editForm(@PathVariable("productId")int productId) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.EDIT_PRODUCT_FORM); //Definimos la vista a presentar.
		
		modelAndView.addObject("product", productService.findByProductId(productId)); //Adjuntamos el producto a la vista.
		
		return modelAndView; //Retornamos la vista.
	}
	
	//Respondemos al envío de los datos del producto modificado:
	@PostMapping("/postEdit")
	public ModelAndView postEdit(@ModelAttribute("product")ProductDTO product, @RequestParam("imageFile")MultipartFile file) 
	{
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.EDIT_PRODUCT_FORM); //Si la modificación no se puede llevar a cabo cargamos el formulario nuevamente.
		
		//Vamos a ver si es necesario cambiar el nombre de la imagen guardado en el producto:
		//El "nuevo" nombre en base al código sería:
		String productCode = product.getCode();
	    int imageNameLimit = productCode.indexOf('-');
	    //El nombre de la imagen será la parte anterior al '-' del código del producto:
	    //Ej: si el código es 'JaM1-S', el nombre de la imagen será 'JaM1' porque lo posterior representa el talle, pero todos tienen la misma imagen.
	    String imageName = productCode.substring(0, imageNameLimit); 
	    
	    //Verificamos si hay un cambio con respecto al que estaba:
	    boolean equalsImageNames = imageName.equals(product.getImageName());
	    if(!equalsImageNames) 
	    {
	        //En caso de que sí, guardamos el nombre de la imagen en el producto:
	        product.setImageName(imageName); 
		}
	
        //Intentamos modificar el producto en la base de datos:
        try 
		{
			ProductDTO modifiedProduct = productService.update(product);
			
			//En caso de que hayamos podido realizar la modificación:
			boolean completed = true; //Bandera para saber cómo resultó el proceso de la carga de la imagen. Suponemos que todo va a funcionar.
			
			//En caso de que se quiera cambiar la imagen asociada al producto:
			if(!equalsImageNames) 
			{
				//Intentamos subir una imagen nueva si el archivo está cargado o simplemente saltamos el proceso:
				if(!productService.uploadImage(modifiedProduct, file)) 
				{
					//Como no se pudo subir la imagen por un error:
					completed = false; //Evitamos que se cambie la vista posteriormente.
					modelAndView.addObject("error", "There is already an image with that name."); //Adjuntamos un mensaje de error a la vista del formulario.
				}
			}
			
			//Preguntamos si todo salió bien:
			if(completed) 
			{
				//Debido a que la operación se pudo completar:
	            modelAndView.setViewName(ViewRouteHelper.POST_EDIT_PRODUCT); //Definimos que la vista a cargar es una donde se presenta el producto modificado.
	            modelAndView.addObject("product", modifiedProduct); //Adjuntamos el producto a la vista.
			}
		}
		catch(Exception e) 
		{
			modelAndView.addObject("error", e.getMessage()); //Adjuntamos el mensaje de error relacionado a la modificación del producto a la vista del formulario.
		}
        
		return modelAndView; //Retornamos la vista y la información adjunta que corresponda según el resultado de la operación.
	}
	
	//Respondemos a la solicitud de un visitante de ver más detalles de un producto determinado: 
	@GetMapping("/moreDetails/{role}/{productId}")
	public ModelAndView moreDetails(@PathVariable("role")String role, @PathVariable("productId")int productId, HttpSession session) 
	{
		ModelAndView modelAndView = new ModelAndView();
		
		//Según el rol del usuario que peticionó:
		switch(role) 
		{
			case "customer": modelAndView.setViewName(ViewRouteHelper.MORE_DETAILS_PRODUCT_CUSTOMER); break; //Al cliente le mostramos su vista.
			case "visitor": modelAndView.setViewName(ViewRouteHelper.MORE_DETAILS_PRODUCT_VISITOR); break; //Al administrador le mostramos su vista.
		}
		
		String imageName = productService.findByProductId(productId).getImageName(); //Obtenemos el nombre de imagen de los productos a mostrar.
		List<ProductDTO> products = productService.findByImageNameAndEnabled(imageName, true); //Obtenemos todos los productos con ese nombre de imagen que estén activos.
		
		//Obtenemos el listado de ítems del carrito del cliente:
		@SuppressWarnings("unchecked")
		Map<Integer, PurchaseItemDTO> purchaseItems = (Map<Integer, PurchaseItemDTO>) session.getAttribute("purchaseItems");
		
		//Recorremos el listado de productos:
		List<StockDTO> stocks = new ArrayList<StockDTO>();
		for(ProductDTO product: products) 
		{
			StockDTO stock = stockService.findByProduct(product.getProductId()); //Obtenemos el stock de cada uno de esos productos.
			
			//Si existe un carrito para el cliente:
			if(purchaseItems != null) 
			{
				//Obtenemos el ítem del carrito que tiene ese producto para comprar:
				PurchaseItemDTO purchaseItem = purchaseItems.get(product.getProductId());
					
				//Si un ítem tiene ese producto:
				if(purchaseItem != null) 
				{
					//La cantidad de stock que podemos ofrecer viene dada por la que tenemos realmente menos la que se está intentando comprar:
					stock.setActualAmount(stock.getActualAmount() - purchaseItem.getAmount());
				}
			}
			
			//Agregamos el stock del producto al listado:
			stocks.add(stock);
		}
		
		modelAndView.addObject("stocks", stocks); //Adjuntamos los stocks de los productos a la vista.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
