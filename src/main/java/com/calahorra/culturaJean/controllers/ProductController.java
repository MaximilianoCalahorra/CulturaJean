package com.calahorra.culturaJean.controllers;

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
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.helpers.ViewRouteHelper;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IStockService;

///Clase ProductController:
@Controller
@RequestMapping("/product")
public class ProductController 
{
	//Atributo:
	private IProductService productService;
	private IStockService stockService;
	
	//Constructor:
	public ProductController(IProductService productService, IStockService stockService) 
	{
		this.productService = productService;
		this.stockService = stockService;
	}
	
	//Respondemos a las peticiones de productos para el visitante:
	@GetMapping("/visitor")
	public ModelAndView visitor() 
	{
		//La vista a presentar será la de los productos para los visitantes:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SHOP_VISITOR);
		
		//Obtenemos los productos activos de la base de datos:
		List<ProductDTO> products = productService.findByEnabled(true);
		
		//Agregamos la información a la vista:
		modelAndView.addObject("products", products);
		
		return modelAndView; //Retornamos la vista con la información obtenida.
	}
	
	//Respondemos a las peticiones de productos para el cliente:
	@GetMapping("/customer")
	public ModelAndView customer() 
	{
		//La vista a presentar será la de los productos para los visitantes:
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SHOP_CUSTOMER);
		
		//Obtenemos los productos activos de la base de datos:
		List<ProductDTO> products = productService.findByEnabled(true);
		
		
		//Agregamos la información a la vista:
		modelAndView.addObject("products", products);
		
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
}
