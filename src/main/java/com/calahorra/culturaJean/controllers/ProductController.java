package com.calahorra.culturaJean.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.PurchaseItemDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IStockService;

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
	public ModelAndView products(@PathVariable("role")String role,
								 @RequestParam(value = "order", defaultValue = "orderAscByName")String order,
							     @RequestParam(value = "cat", defaultValue = "all")String category,
							     @RequestParam(value = "gen", defaultValue = "all")String gender,
							     @RequestParam(value = "size", defaultValue = "all")String size,
							     @RequestParam(value = "col", defaultValue = "all")String color,
							     @RequestParam(value = "sPri", defaultValue = "")String salePrice,
							     @RequestParam(value = "fSPri", defaultValue = "")String fromSalePrice,
							     @RequestParam(value = "uSPri", defaultValue = "")String untilSalePrice,
							     @RequestParam(value = "rFSPri", defaultValue = "")String rangeFromSalePrice,
							     @RequestParam(value = "rUSPri", defaultValue = "")String rangeUntilSalePrice) 
	{
		ModelAndView modelAndView = new ModelAndView();
		
		//Según el rol del usuario que peticionó:
		switch(role) 
		{
			case "customer": modelAndView.setViewName(ViewRouteHelper.SHOP_CUSTOMER); break; //Al cliente le mostramos su vista.
			case "visitor": modelAndView.setViewName(ViewRouteHelper.SHOP_VISITOR); break; //Al administrador le mostramos su vista.
		}
		
		//Instanciamos una lista donde se van a cargar los productos filtrados y ordenados:
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		
		//Aplicamos el filtro seleccionado de la sección talle:
		if(!size.equals("all")) //Si se eligió alguno:
		{
			//Obtenemos un producto habilitado por cada nombre de imagen con ese talle:
			products = productService.findUniqueSizeAndEnabledEachImageName(true, size); 
		}
		else //Si se eligió todos los talles:
		{
			//Obtenemos un producto habilitado por cada nombre de imagen, es decir, de cualquier talle:
			products = productService.findUniqueEnabledEachImageName(true);
		}
		
		//Aplicamos los filtros de las secciones categoría, género, color y precio de venta:
		products = productService.applyFilters(products, category, gender, color, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice);
		
		//Aplicamos el criterio de ordenamiento elegido:
		products = productService.applyOrder(products, order);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("order", order); //Adjuntamos el criterio de ordenamiento.
		modelAndView.addObject("cat", category); //Adjuntamos la categoría para el filtro.
		modelAndView.addObject("gen", gender); //Adjuntamos el género para el filtro.
		modelAndView.addObject("size", size); //Adjuntamos el talle para el filtro.
		modelAndView.addObject("col", color); //Adjuntamos el color para el filtro.
		modelAndView.addObject("sPri", salePrice); //Adjuntamos el precio de venta para el filtro.
		modelAndView.addObject("fSPri", fromSalePrice); //Adjuntamos el precio de venta mayor o igual para el filtro.
		modelAndView.addObject("uSPri", untilSalePrice); //Adjuntamos el precio de venta menor o igual para el filtro.
		modelAndView.addObject("rFSPri", rangeFromSalePrice); //Adjuntamos el precio mayor o igual de un rango para el filtro.
		modelAndView.addObject("rUSPri", rangeUntilSalePrice); //Adjuntamos el precio menor o igual de un rango para el filtro.
		modelAndView.addObject("categories", productService.findUniqueEachCategory(products)); //Adjuntamos el listado de categorías de producto.
		modelAndView.addObject("genders", productService.findUniqueEachGender(products)); //Adjuntamos el listado de géneros de producto.
		modelAndView.addObject("sizes", productService.findUniqueEnabledEachSize(products, size)); //Adjuntamos el listado de talles de producto.
		modelAndView.addObject("colors", productService.findUniqueEachColor(products)); //Adjuntamos el listado de colores de producto.
		modelAndView.addObject("products", products); //Adjuntamos los productos.
		
		return modelAndView; //Retornamos la vista con la información obtenida.
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
	
	//Respondemos a la solicitud de deshabilitar un producto:
	@GetMapping("/remove/{productId}")
	public RedirectView remove(@PathVariable("productId")int productId) 
	{
		productService.logicalDelete(productId); //Deshabilitamos el producto y guardamos el cambio en la base de datos.
		return new RedirectView(ViewRouteHelper.REDIRECT_INDEX); //Dirigimos el flujo cargando el listado de productos nuevamente con el cambio.
	}
	
	//Respondemos a la solicitud de un visitante de ver más detalles de un producto determinado: 
	@GetMapping("/moreDetails/{role}/{productId}")
	public ModelAndView moreDetailsVisitor(@PathVariable("role")String role, @PathVariable("productId")int productId) 
	{
		ModelAndView modelAndView = new ModelAndView();
		
		//Según el rol del usuario que peticionó:
		switch(role) 
		{
			case "customer":
			{
				modelAndView.setViewName(ViewRouteHelper.MORE_DETAILS_PRODUCT_CUSTOMER);  //Al cliente le mostramos su vista.
				modelAndView.addObject("purchaseItem", new PurchaseItemDTO()); //Adjuntamos a la vista un objeto ítem de compra para cargarlo.
			} break;
			case "visitor": modelAndView.setViewName(ViewRouteHelper.MORE_DETAILS_PRODUCT_VISITOR); break; //Al administrador le mostramos su vista.
		}
		
		String imageName = productService.findByProductId(productId).getImageName(); //Obtenemos el nombre de imagen de los productos a mostrar.
		List<ProductDTO> products = productService.findByImageNameAndEnabled(imageName, true); //Obtenemos todos los productos con ese nombre de imagen que estén activos.
		
		//Recorremos el listado de productos:
		List<StockDTO> stocks = new ArrayList<StockDTO>();
		for(ProductDTO product: products) 
		{
			stocks.add(stockService.findByProduct(product.getProductId())); //Obtenemos el stock de cada uno de esos productos.
		}
		
		modelAndView.addObject("stocks", stocks); //Adjuntamos los stocks de los productos a la vista.
		
		return modelAndView; //Retornamos la vista con la información adjunta.
	}
}
