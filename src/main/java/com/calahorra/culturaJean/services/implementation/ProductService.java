package com.calahorra.culturaJean.services.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calahorra.culturaJean.dtos.FiltersOptionsProductDTO;
import com.calahorra.culturaJean.dtos.PaginatedProductDTO;
import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.dtos.ProductFiltersDataDTO;
import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.repositories.IProductRepository;
import com.calahorra.culturaJean.repositories.custom.ICustomProductRepository;
import com.calahorra.culturaJean.services.IProductService;
import com.calahorra.culturaJean.services.IUtilsService;

///Clase ProductService:
@Service("productService")
public class ProductService implements IProductService
{
	//Atributos:
	private IProductRepository productRepository;
	private ICustomProductRepository customProductRepository;
	private IUtilsService utilsService;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public ProductService(IProductRepository productRepository, ICustomProductRepository customProductRepository, 
						  IUtilsService utilsService) 
	{
		this.productRepository = productRepository;
		this.customProductRepository = customProductRepository;
		this.utilsService = utilsService;
	}
	
	//Encontrar:
	
	//Encontramos el producto con determinado id:
	@Override
	public Product findByProductId(int productId) 
	{
		return productRepository.findByProductId(productId);
	}
	
	//Encontramos el DTO del producto con determinado id:
	@Override
	public ProductDTO findDTOByProductId(int productId) 
	{
		return modelMapper.map(productRepository.findByProductId(productId), ProductDTO.class);
	}
	
	//Encontramos el producto con determinado código:
	@Override
	public ProductDTO findByCode(String code) 
	{
		return modelMapper.map(productRepository.findByCode(code), ProductDTO.class);
	}
	
	//Encontramos el producto con determinado nombre:
	@Override
	public ProductDTO findByName(String name) 
	{
		return modelMapper.map(productRepository.findByName(name), ProductDTO.class);
	}
	
	//Encontramos los productos de determinada categoría:
	@Override
	public List<ProductDTO> findByCategory(String category)
	{
		return productRepository.findByCategory(category) //Obtenemos los productos de esa categoría como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos de determinada género:
	@Override
	public List<ProductDTO> findByGender(Character gender)
	{
		return productRepository.findByGender(gender) //Obtenemos los productos de ese género como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos de determinado talle:
	@Override
	public List<ProductDTO> findBySize(String size)
	{
		return productRepository.findBySize(size) //Obtenemos los productos de ese talle como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos de determinado color:
	@Override
	public List<ProductDTO> findByColor(String color)
	{
		return productRepository.findByColor(color) //Obtenemos los productos de ese color como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos activos/inactivos:
	@Override
	public List<ProductDTO> findByEnabled(boolean enabled)
	{
		return productRepository.findByEnabled(enabled) //Obtenemos los productos en ese estado como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos con determinado precio de venta:
	@Override
	public List<ProductDTO> findBySalePrice(float salePrice)
	{
		return productRepository.findBySalePrice(salePrice) //Obtenemos los productos con ese precio de venta como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos con un precio de venta entre determinado rango:
	@Override
	public List<ProductDTO> findBySalePriceRange(float minimumPrice, float maximumPrice)
	{
		return productRepository.findBySalePriceRange(minimumPrice, maximumPrice) //Obtenemos los productos con un precio de venta entre ese rango como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos con un precio de venta menor o igual a uno determinado:
	@Override
	public List<ProductDTO> findBySalePriceLessThanOrEqualTo(float price)
	{
		return productRepository.findBySalePriceLessThanOrEqualTo(price) //Obtenemos los productos con un precio de venta menor o igual como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos con un precio de venta mayor o igual a uno determinado:
	@Override
	public List<ProductDTO> findBySalePriceGreaterThanOrEqualTo(float price)
	{
		return productRepository.findBySalePriceGreaterThanOrEqualTo(price) //Obtenemos los productos con un precio de venta mayor o igual como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos con determinado nombre de imagen que estén habilitados/deshabilitados:
	@Override
	public List<ProductDTO> findByImageNameAndEnabled(String imageName, boolean enabled)
	{
		return productRepository.findByImageNameAndEnabled(imageName, enabled) //Obtenemos los productos con ese nombre de imagen y en ese estado.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un producto por cada nombre de imagen:
	@Override
	public List<ProductDTO> findUniqueEachImageName()
	{
		return productRepository.findUniqueEachImageName() //Obtenemos un producto por cada tipo de imagen.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un producto habilitado/deshabilitado por cada nombre de imagen:
	@Override
	public List<ProductDTO> findUniqueEnabledEachImageName(boolean enabled)
	{
		return productRepository.findUniqueEnabledEachImageName(enabled) //Obtenemos un producto en ese estado por cada tipo de imagen.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un producto habilitado/deshabilitado de determinado talle por cada nombre de imagen:
	@Override
	public List<ProductDTO> findUniqueSizeAndEnabledEachImageName(@Param("enabled")boolean enabled, @Param("sizes")List<String> sizes)
	{
		return productRepository.findUniqueSizeAndEnabledEachImageName(enabled, sizes) //Obtenemos un producto en ese estado y de ese talle por cada tipo de imagen.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un ejemplar de cada categoría de producto:
	@Override
	public List<String> findUniqueEachCategory(List<ProductDTO> products)
	{
		List<String> categories = new ArrayList<String>(); //Definimos un listado donde se guardarán las categorías.
		
		//Analizamos cada producto para saber si su categoría se encuentra en el listado:
		for(ProductDTO product: products) 
		{
			String category = product.getCategory(); //Obtenemos la categoría del producto.
			
			//Si la categoría no está en el listado:
			if(!categories.contains(category)) 
			{
				categories.add(category); //Agregamos la categoría.
			}
		}
		
		//Ordenamos el listado de categorías de forma alfabética:
		categories.sort(null);
		
		return categories; //Retornamos el listado de categorías de producto.
	}
			
	//Encontramos un ejemplar de cada género de producto:
	@Override
	public List<Character> findUniqueEachGender(List<ProductDTO> products)
	{
		List<Character> genders = new ArrayList<Character>(); //Definimos un listado donde se guardarán los géneros.
		
		//Analizamos cada producto para saber si su género se encuentra en el listado:
		for(ProductDTO product: products) 
		{
			Character gender = product.getGender(); //Obtenemos el género del producto.
			
			//Si el género no está en el listado:
			if(!genders.contains(gender)) 
			{
				genders.add(gender); //Agregamos el género.
			}
		}
		
		//Ordenamos el listado de géneros de forma alfabética:
		genders.sort(null);
		
		return genders; //Retornamos el listado de géneros de producto.
	}
			
	//Encontramos un ejemplar de cada talle de producto:
	@Override
	public List<String> findUniqueEachSize(List<ProductDTO> products)
	{
		List<String> sizes = new ArrayList<String>(); //Definimos un listado donde se guardarán los talles.
		
		//Analizamos cada producto para saber si su talle se encuentra en el listado:
		for(ProductDTO product: products) 
		{
			String size = product.getSize(); //Obtenemos el talle del producto.
			
			//Si el talle no está en el listado:
			if(!sizes.contains(size)) 
			{
				sizes.add(size); //Agregamos el talle.
			}
		}
		
		//Ordenamos el listado de talles de forma alfabética:
		sizes.sort(null);
		
		return sizes; //Retornamos el listado de talles de producto.
	}
	
	//Encontramos un ejemplar de cada talle de producto habilitado/deshabilitado:
	@Override 
	public List<String> findUniqueEnabledEachSize(List<ProductDTO> products, boolean enabled)
	{
	    //Lista de imageNames para la consulta obtenidas analizando los productos:
	    List<String> imageNames = products.stream()
	                                      .map(ProductDTO::getImageName)
	                                      .collect(Collectors.toList());

	    //Obtenemos los talles de todos los productos y luego los ordenamos:
	    List<String> sizes = productRepository.findUniqueEnabledEachSizeForMultipleImages(imageNames, enabled);
	    sizes.sort(String::compareTo);

	    return sizes; //Retornamos los talles únicos.
	}
		
	//Encontramos un ejemplar de cada color de producto:
	@Override
	public List<String> findUniqueEachColor(List<ProductDTO> products)
	{
		List<String> colors = new ArrayList<String>(); //Definimos un listado donde se guardarán los colores.
		
		//Analizamos cada producto para saber si su color se encuentra en el listado:
		for(ProductDTO product: products) 
		{
			String color = product.getColor(); //Obtenemos el color del producto.
			
			//Si el color no está en el listado:
			if(!colors.contains(color)) 
			{
				colors.add(color); //Agregamos el color.
			}
		}
		
		//Ordenamos el listado de colores de forma alfabética:
		colors.sort(null);
		
		return colors; //Retornamos el listado de colores de producto.
	}
	
	//Obtener:
	
	//Obtenemos todos los productos:
	@Override
	public List<Product> getAll()
	{
		return productRepository.findAll();
	}
	
	//Obtenemos todos los productos como DTOs:
	@Override
	public List<ProductDTO> getAllProducts()
	{
		return productRepository.findAll() //Obtenemos cada producto como una entidad.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtenemos los productos filtrados de una página:
	@Override
	public PaginatedProductDTO getFilteredProducts(@Param("filters")ProductFiltersDataDTO filters, int page, int size)
	{
		//Instanciamos un Pageable con la página y la cantidad de elementos a traer para hacer la query:
        Pageable pageable = PageRequest.of(page, size);

        //Adaptamos los filtros para poder hacer la consulta:
        List<String> categories = utilsService.cleanFilter(filters.getCategories());
        List<Character> genders = utilsService.convertListStringFilterToListCharacter(filters.getGenders());
        List<String> sizes = utilsService.cleanFilter(filters.getSizes());
        List<String> colors = utilsService.cleanFilter(filters.getColors());
        Float salePrice = utilsService.convertStringFilterToFloat(filters.getSalePrice());
        Float fromSalePrice = utilsService.convertStringFilterToFloat(filters.getFromSalePrice());
        Float untilSalePrice = utilsService.convertStringFilterToFloat(filters.getUntilSalePrice());
        Float rangeFromSalePrice = utilsService.convertStringFilterToFloat(filters.getRangeFromSalePrice());
        Float rangeUntilSalePrice = utilsService.convertStringFilterToFloat(filters.getRangeUntilSalePrice());
        Boolean state = utilsService.convertStringFilterToBoolean(filters.getState());
        
        //Obtenemos el criterio de ordenamiento:
        String sort = filters.getOrder();
        
        //Obtenemos la página de productos según los filtros y el criterio de ordenamiento:
        Page<Product> productPage = customProductRepository.findFilteredProducts(categories, genders, sizes, colors, salePrice, 
        																		 fromSalePrice, untilSalePrice, rangeFromSalePrice, 
        																		 rangeUntilSalePrice, state, sort, pageable);

        //Obtenemos todas las opciones de cada sección de filtro según la configuración de filtros aplicada:
        List<Map<String, Object>> results = customProductRepository.findFiltersOptions(categories, genders, sizes, colors, salePrice, 
        																	   		   fromSalePrice, untilSalePrice, rangeFromSalePrice,
        																	   		   rangeUntilSalePrice, state); 
        
        //Desglosamos las opciones y las asignamos a cada parte de nuestro DTO específico de opciones de filtros de productos:
        FiltersOptionsProductDTO filtersOptionsDTO = new FiltersOptionsProductDTO();
        for(Map<String, Object> result : results) 
        { 
        	filtersOptionsDTO.setCategories(utilsService.convertPostgresArrayToList((String[]) result.get("categories"))); //Categorías.
        	filtersOptionsDTO.setGenders(utilsService.convertPostgresArrayToList((String[]) result.get("genders"))); //Géneros.
        	filtersOptionsDTO.setSizes(utilsService.convertPostgresArrayToList((String[]) result.get("sizes"))); //Talles.
        	filtersOptionsDTO.setColors(utilsService.convertPostgresArrayToList((String[]) result.get("colors"))); //Colores.
        }
        
        //Construimos el objeto paginado con su información:
        PaginatedProductDTO paginatedDTO = new PaginatedProductDTO();
        paginatedDTO.setProducts(productPage.map(product -> modelMapper.map(product, ProductDTO.class)).getContent()); //Productos.
        paginatedDTO.setTotalPages(productPage.getTotalPages()); //Cantidad de páginas.
        paginatedDTO.setTotalElements(productPage.getTotalElements()); //Cantidad de productos.
        paginatedDTO.setFiltersOptions(filtersOptionsDTO); //Opciones de cada sección de filtro.
        
        return paginatedDTO; //Retornamos el objeto paginado.
	}
	
	//Ordenar:
	
	//Ordenamos los productos por el código de manera alfabética:
	@Override
	public List<ProductDTO> getAllInOrderAscByCode()
	{
		return productRepository.getAllInOrderAscByCode() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por el código de manera inversa al alfabeto:
	@Override
	public List<ProductDTO> getAllInOrderDescByCode()
	{
		return productRepository.getAllInOrderDescByCode() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por la categoría de manera alfabética:
	@Override
	public List<ProductDTO> getAllInOrderAscByCategory()
	{
		return productRepository.getAllInOrderAscByCategory() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por la categoría de manera inversa al alfabeto:
	@Override
	public List<ProductDTO> getAllInOrderDescByCategory()
	{
		return productRepository.getAllInOrderDescByCategory() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por el precio de venta de manera ascendente:
	@Override
	public List<ProductDTO> getAllInOrderAscBySalePrice()
	{
		return productRepository.getAllInOrderAscBySalePrice() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por el precio de venta de manera descendente:
	@Override
	public List<ProductDTO> getAllInOrderDescBySalePrice()
	{
		return productRepository.getAllInOrderDescBySalePrice() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por el nombre de manera alfabética:
	@Override
	public List<ProductDTO> getAllInOrderAscByName()
	{
		return productRepository.getAllInOrderAscByName() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los productos por el nombre de manera inversa al alfabeto:
	@Override
	public List<ProductDTO> getAllInOrderDescByName()
	{
		return productRepository.getAllInOrderDescByName() //Obtenemos los productos ordenados como entidades.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos el listado de productos por nombre de manera alfabética:
	@Override
	public List<ProductDTO> inOrderAscByName(List<ProductDTO> products)
	{
		Collections.sort(products, (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
		return products; //Retornamsos los productos ordenados.
	}
		
	//Ordenamos el listado de productos por nombre de manera inversa al alfabeto:
	@Override
	public List<ProductDTO> inOrderDescByName(List<ProductDTO> products)
	{
		Collections.sort(products, (p1, p2) -> p2.getName().compareToIgnoreCase(p1.getName()));
		return products; //Retornamsos los productos ordenados.
	}
		
	//Ordenamos el listado de productos por precio de venta de manera ascendente:
	@Override
	public List<ProductDTO> inOrderAscBySalePrice(List<ProductDTO> products)
	{
		products.sort(Comparator.comparingDouble(ProductDTO::getSalePrice));
		return products; //Retornamsos los productos ordenados.
	}
		
	//Ordenamos el listado de productos por precio de venta de manera descendente:
	@Override
	public List<ProductDTO> inOrderDescBySalePrice(List<ProductDTO> products)
	{
		products.sort(Comparator.comparingDouble(ProductDTO::getSalePrice).reversed());
		return products; //Retornamsos los productos ordenados.
	}
		
	//Aplicamos el criterio de ordenamiento seleccionado:
	@Override
	public List<ProductDTO> applyOrder(List<ProductDTO> products, String order)
	{
		//Según el criterio de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByName": products = inOrderAscByName(products); break; //Alfabéticamente por nombre.
			case "orderDescByName": products = inOrderDescByName(products); break; //Inverso al alfabeto por nombre.
			case "orderAscBySalePrice": products = inOrderAscBySalePrice(products); break; //Ascendente por precio de venta.
			case "orderDescBySalePrice": products = inOrderDescBySalePrice(products); break; //Descendente por precio de venta.
		}
		return products; //Retornamsos los productos ordenados.
	}
	
	//Agregar:
	
	//Agregamos un producto a la base de datos:
	@Override
	public ProductDTO insert(ProductDTO product) throws Exception
	{
		//Si existe otro producto con el mismo código evitamos que se registre en la base de datos:
		if(productRepository.findByCode(product.getCode()) != null) 
		{
			throw new Exception("There is already a product with code #" + product.getCode());
		}
		Product savedProduct = productRepository.save(modelMapper.map(product, Product.class)); //Insertamos el producto en la base de datos.
		return modelMapper.map(savedProduct, ProductDTO.class); //Convertimos en DTO el producto y lo retornamos.
	}
	
	//Modificar:
	
	//Modificamos un producto de la base de datos:
	@Override
	public ProductDTO update(ProductDTO product) throws Exception
	{
		Product existingProduct = productRepository.findByCode(product.getCode()); //Buscamos si existe un producto con el código del que se quiere modificar.
		/*
			Validamos dos cuestiones:
			1. Si existe un producto con ese código.
			2. Si el producto existente tiene un id distinto que el que se quiere modificar.
			De esta forma, logramos que no se le pueda asignar un código de otro producto a uno que se está modificando a menos de que sea el producto
			propietario de ese código y se estén modificando otros datos del mismo.
		*/
		if(existingProduct != null && existingProduct.getProductId() != product.getProductId()) 
		{
			throw new Exception("There is already a product with code #" + product.getCode());
		}
		Product updatedProduct = productRepository.save(modelMapper.map(product, Product.class)); //Modificamos el producto en la base de datos.
		return modelMapper.map(updatedProduct, ProductDTO.class); //Convertimos en DTO el producto y lo retornamos.
	}
	
	//Eliminar:
	
	//Deshabilitamos el producto con determinado id de la base de datos:
	@Override
	public boolean logicalDelete(int productId) 
	{
		try 
		{
			Product product = findByProductId(productId); //Buscamos el producto.
			
			//Solo lo actualizamos en la base de datos si está habilitado:
			if(product.isEnabled()) 
			{
				product.setEnabled(false); //Deshabilitamos el producto.
				productRepository.save(product); //Modificamos el registro del producto en la base de datos.
				return true;
			}
			return false;
		}
		catch(Exception e) 
		{
			return false;
		}
	}
	
	//Subir:
	
	//Subimos una imagen al servidor y retornamos el producto que la llevará:
	@Override
	public boolean uploadImage(ProductDTO product, MultipartFile file) 
	{
		boolean completed = true; //Suponemos que el proceso se va a completar:
		
		//Verificamos si se subió alguna imagen o no:
		//En caso de que no se haya subido ninguna imagen se entiende que es porque se cambió el código del producto para que tome la imagen
		//de otro.
		if(!file.isEmpty()) 
		{
			//En caso de que sí:
			try 
			{
				//Definimos la ruta donde se va a subir la imagen asociada al producto:
				String upload_address = "src/main/resources/static/assets/img/products/" + product.getGender() + "/" + product.getCategory() + "/";
				//Verificamos que el nombre del archivo no sea vacío:
	            //Definimos el nombre del archivo que se va a subir:
	            String extension = ".jpeg"; //Definimos que la extensión será '.jpeg' porque es el único tipo de archivo que dejamos subir.
	            String newFileName =  product.getImageName() + extension; //El nuevo nombre del archivo viene dado por la concatenación entre el nombre y la extensión que definimos.
	 
	            //Guardamos la imagen en la carpeta especificada:
	            Path path = Paths.get(upload_address + newFileName); //Definimos el path, que será la dirección + el nombre del archivo.
	            Files.copy(file.getInputStream(), path); //Obtenemos el archivo y lo guardamos en esa dirección.
	           
	            //Hacemos un poco de tiempo para que se suba la imagen:
	            try 
	            {
	    			Thread.sleep(3000);
	    		} 
	            catch(InterruptedException e) 
	            {
	    			e.printStackTrace();
	    		}
	        } 
			catch(IOException e) 
			{
				completed = false; //Falló el proceso de subida de la imagen.
			}
		}
		return completed; //Retornamos el resultado de la operación.
	}
	
	//Filtrar:
	
	//Filtramos el listado de productos por la categoría del producto:
	@Override
	public List<ProductDTO> filterByCategory(List<ProductDTO> products, List<String> categories)
	{
		//Convertimos la lista de categorías a un Set para optimizar la búsqueda:
	    Set<String> categoriesSet = new HashSet<>(categories);
		
	    //Si se eligió alguna opción de filtro:
	    if(!categoriesSet.contains("all")) 
	    {
	    	//Filtramos los productos cuya categoría esté en el conjunto:
		    products = products.stream()
					            .filter(product -> categoriesSet.contains(product.getCategory()))
					            .collect(Collectors.toList());
	    }
	    
		return products; //Retornamos los productos.
	}
			
	//Filtramos el listado de productos por el género del producto:
	@Override
	public List<ProductDTO> filterByGender(List<ProductDTO> products, List<Character> genders)
	{
		//Convertimos la lista de géneros a un Set para optimizar la búsqueda:
	    Set<Character> gendersSet = new HashSet<>(genders);
		
	    //Filtramos los productos cuyo género esté en el conjunto:
		products = products.stream()
				            .filter(product -> gendersSet.contains(product.getGender()))
				            .collect(Collectors.toList());
	    
		return products; //Retornamos los productos.
	}
			
	//Filtramos el listado de productos por el talle del producto:
	@Override
	public List<ProductDTO> filterBySize(List<ProductDTO> products, List<String> sizes)
	{
		//Convertimos la lista de talles a un Set para optimizar la búsqueda:
	    Set<String> sizesSet = new HashSet<>(sizes);
		
	    //Si se eligió alguna opción de filtro:
	    if(!sizesSet.contains("all")) 
	    {
	    	//Filtramos los productos cuyo talle esté en el conjunto:
		    products = products.stream()
					            .filter(product -> sizesSet.contains(product.getSize()))
					            .collect(Collectors.toList());
	    }
	    
		return products; //Retornamos los productos.
	}
			
	//Filtramos el listado de productos por el color del producto:
	@Override
	public List<ProductDTO> filterByColor(List<ProductDTO> products, List<String> colors)
	{
		//Convertimos la lista de colores a un Set para optimizar la búsqueda:
	    Set<String> colorsSet = new HashSet<>(colors);
		
	    //Si se eligió alguna opción de filtro:
	    if(!colorsSet.contains("all")) 
	    {
	    	//Filtramos los productos cuyo color esté en el conjunto:
		    products = products.stream()
					            .filter(product -> colorsSet.contains(product.getColor()))
					            .collect(Collectors.toList());
	    }
	    
		return products; //Retornamos los productos.
	}
		
	//Filtramos el listado de productos por el precio de venta del producto:
	@Override
	public List<ProductDTO> filterBySalePrice(List<ProductDTO> products, float salePrice)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (product.getSalePrice() != salePrice) 
			{
				iterator.remove(); //En caso de que no tenga un precio de venta como el del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
			
	//Filtramos el listado de productos por el precio de venta del producto mayor o igual a uno determinado:
	@Override
	public List<ProductDTO> filterByFromSalePrice(List<ProductDTO> products, float fromSalePrice)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (product.getSalePrice() < fromSalePrice) 
			{
				iterator.remove(); //En caso de que tenga un precio de venta menor al del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
		
	//Filtramos el listado de productos por el precio de venta del producto menor o igual a uno determinado:
	@Override
	public List<ProductDTO> filterByUntilSalePrice(List<ProductDTO> products, float untilSalePrice)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (product.getSalePrice() > untilSalePrice) 
			{
				iterator.remove(); //En caso de que tenga un precio de venta mayor al del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
			
	//Filtramos el listado de productos por el precio de venta del producto dentro de un rango determinado:
	@Override
	public List<ProductDTO> filterBySalePriceRange(List<ProductDTO> products, float rangeFromSalePrice, float rangeUntilSalePrice)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (product.getSalePrice() < rangeFromSalePrice || product.getSalePrice() > rangeUntilSalePrice) 
			{
				iterator.remove(); //En caso de que no tenga un precio de venta dentro del rango del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
			
	//Aplicamos el filtro seleccionado de la sección precio de venta:
	@Override
	public List<ProductDTO> applyFilterTypeSalePrice(List<ProductDTO> products, String salePrice, String fromSalePrice, String untilSalePrice,
													 String rangeFromSalePrice, String rangeUntilSalePrice)
	{
		if(!salePrice.equals("")) //Filtro por precio de venta:
		{
			float salePriceNumber = Float.parseFloat(salePrice); //Convertimos la cadena a número.
			products = filterBySalePrice(products, salePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		
		if(!fromSalePrice.equals("")) //Filtro por precio de venta mayor o igual a uno determinado:
		{
			float fromSalePriceNumber = Float.parseFloat(fromSalePrice); //Convertimos la cadena a número.
			products = filterByFromSalePrice(products, fromSalePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		
		if(!untilSalePrice.equals("")) //Filtro por precio de venta menor o igual a uno determinado:
		{
			float untilSalePriceNumber = Float.parseFloat(untilSalePrice); //Convertimos la cadena a número.
			products = filterByUntilSalePrice(products, untilSalePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		
		if(!rangeFromSalePrice.equals("") && !rangeUntilSalePrice.equals("")) //Filtro por precio de venta dentro de un rango determinado:
		{
			float rangeFromSalePriceNumber = Float.parseFloat(rangeFromSalePrice); //Convertimos la cadena a número.
			float rangeUntilSalePriceNumber = Float.parseFloat(rangeUntilSalePrice); //Convertimos la cadena a número.
			products = filterBySalePriceRange(products, rangeFromSalePriceNumber, rangeUntilSalePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		
		return products; //Retornamos los productos filtrados.
	}
			
	//Aplicamos los filtros seleccionados de las secciones categoría, género, color y precio de venta del producto:
	@Override
	public List<ProductDTO> applyFilters(List<ProductDTO> products, List<String> categories, List<String> genders, List<String> colors,
										 String salePrice, String fromSalePrice, String untilSalePrice, String rangeFromSalePrice,
										 String rangeUntilSalePrice)
	{
		//Aplicamos el filtro seleccionado de la sección categoría:
		products = filterByCategory(products, categories);
		
		//Si se indicó algún filtro en la sección géneros:
		if(genders.indexOf("all") == -1) 
		{
			//Pasamos cada género en Character a String:
			List<Character> gendersChar = new ArrayList<>();
			for(String gender: genders) 
			{
				if(!gender.isEmpty()) gendersChar.add(gender.charAt(0));
			}
			
			//Aplicamos el filtro seleccionado de la sección género:
			products = filterByGender(products, gendersChar);
		}
			
		//Aplicamos el filtro seleccionado de la sección color:
		products = filterByColor(products, colors);
				
		//Aplicamos el filtro seleccionado de la sección precio de venta:
		products = applyFilterTypeSalePrice(products, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice); 
				
		return products; //Retornamos los productos filtrados.
	}
	
	//Aplicamos los filtros seleccionados de las secciones categoría, género, talle, color y precio de venta del producto:
	@Override
	public List<ProductDTO> applyFilters(List<ProductDTO> products, List<String> categories, List<String> genders, List<String> sizes,
										 List<String> colors, String salePrice, String fromSalePrice, String untilSalePrice, 
										 String rangeFromSalePrice, String rangeUntilSalePrice)
	{
		//Aplicamos el filtro seleccionado de la sección categoría:
		products = filterByCategory(products, categories);
		
		//Si se indicó algún filtro en la sección géneros:
		if(genders.indexOf("all") == -1) 
		{
			//Pasamos cada género en Character a String:
			List<Character> gendersChar = new ArrayList<>();
			for(String gender: genders) 
			{
				if(!gender.isEmpty()) gendersChar.add(gender.charAt(0));
			}
			
			//Aplicamos el filtro seleccionado de la sección género:
			products = filterByGender(products, gendersChar);
		}
		
		//Aplicamos el filtro seleccionado de la sección talle:
		products = filterBySize(products, sizes);
		
		//Aplicamos el filtro seleccionado de la sección color:
		products = filterByColor(products, colors);
		
		//Aplicamos el filtro seleccionado de la sección precio de venta:
		products = applyFilterTypeSalePrice(products, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice); 
		
		return products; //Retornamos los productos filtrados.
	}
	
	//Mapear:
	
	//Mapeamos un ProductDTO a Entity Product:
	@Override
	public Product mapDTOToEntity(ProductDTO productDTO) 
	{
		return modelMapper.map(productDTO, Product.class); //Retornamos el mapeo del DTO a entity.
	}
}
