package com.calahorra.culturaJean.services.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.repositories.IProductRepository;
import com.calahorra.culturaJean.services.IProductService;

///Clase ProductService:
@Service("productService")
public class ProductService implements IProductService
{
	//Atributos:
	private IProductRepository productRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public ProductService(IProductRepository productRepository) 
	{
		this.productRepository = productRepository;
	}
	
	//Encontrar:
	
	//Encontramos el producto con determinado id:
	@Override
	public Product findByProductId(int productId) 
	{
		return productRepository.findByProductId(productId);
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
	public List<ProductDTO> findUniqueSizeAndEnabledEachImageName(@Param("enabled")boolean enabled, @Param("size")String size)
	{
		return productRepository.findUniqueSizeAndEnabledEachImageName(enabled, size) //Obtenemos un producto en ese estado y de ese talle por cada tipo de imagen.
				.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un ejemplar de cada categoría de producto:
	@Override
	public List<String> findUniqueEachCategory()
	{
		return productRepository.findUniqueEachCategory(); //Retornamos el listado de categorías de producto ordenado.
	}
			
	//Encontramos un ejemplar de cada género de producto:
	@Override
	public List<String> findUniqueEachGender()
	{
		return productRepository.findUniqueEachGender(); //Retornamos el listado de géneros de producto ordenado.
	}
			
	//Encontramos un ejemplar de cada talle de producto:
	@Override
	public List<String> findUniqueEachSize()
	{
		return productRepository.findUniqueEachSize(); //Retornamos el listado de talles de producto ordenado.
	}
		
	//Encontramos un ejemplar de cada color de producto:
	@Override
	public List<String> findUniqueEachColor()
	{
		return productRepository.findUniqueEachColor(); //Retornamos el listado de colores de producto ordenado.
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
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.;
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
	public List<ProductDTO> filterByCategory(List<ProductDTO> products, String category)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (!product.getCategory().equals(category)) 
			{
				iterator.remove(); //En caso de que no tenga una categoría como la del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
			
	//Filtramos el listado de productos por el género del producto:
	@Override
	public List<ProductDTO> filterByGender(List<ProductDTO> products, Character gender)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (!product.getGender().equals(gender)) 
			{
				iterator.remove(); //En caso de que no tenga un género como el del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
			
	//Filtramos el listado de productos por el talle del producto:
	@Override
	public List<ProductDTO> filterBySize(List<ProductDTO> products, String size)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (!product.getSize().equals(size)) 
			{
				iterator.remove(); //En caso de que no tenga un talle como el del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
	}
			
	//Filtramos el listado de productos por el color del producto:
	@Override
	public List<ProductDTO> filterByColor(List<ProductDTO> products, String color)
	{
		Iterator<ProductDTO> iterator = products.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un producto por analizar:
		while(iterator.hasNext())
		{
			ProductDTO product = iterator.next(); //Obtenemos ese producto.
			if (!product.getColor().equals(color)) 
			{
				iterator.remove(); //En caso de que no tenga un color como el del filtro, lo removemos.
	        }
	    }
		return products; //Retornamos los productos filtrados.
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
	public List<ProductDTO> applyFilterTypeSalePrice(List<ProductDTO> products, String salePrice, String fromSalePrice, String untilSalePrice,
													 String rangeFromSalePrice, String rangeUntilSalePrice)
	{
		if(!salePrice.equals("") && fromSalePrice.equals("") && untilSalePrice.equals("") && rangeFromSalePrice.equals("") && 
				 rangeUntilSalePrice.equals("")) //Filtro por precio de venta:
		{
			float salePriceNumber = Float.parseFloat(salePrice); //Convertimos la cadena a número.
			products = filterBySalePrice(products, salePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		else if(!fromSalePrice.equals("") && salePrice.equals("") && untilSalePrice.equals("") && rangeFromSalePrice.equals("") && 
				rangeUntilSalePrice.equals("")) //Filtro por precio de venta mayor o igual a uno determinado:
		{
			float fromSalePriceNumber = Float.parseFloat(fromSalePrice); //Convertimos la cadena a número.
			products = filterByFromSalePrice(products, fromSalePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		else if(!untilSalePrice.equals("") && salePrice.equals("") && fromSalePrice.equals("") && rangeFromSalePrice.equals("") && 
				rangeUntilSalePrice.equals("")) //Filtro por precio de venta menor o igual a uno determinado:
		{
			float untilSalePriceNumber = Float.parseFloat(untilSalePrice); //Convertimos la cadena a número.
			products = filterByUntilSalePrice(products, untilSalePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		else if(!rangeFromSalePrice.equals("") && !rangeUntilSalePrice.equals("") && salePrice.equals("") && fromSalePrice.equals("") && 
				untilSalePrice.equals("")) //Filtro por precio de venta dentro de un rango determinado:
		{
			float rangeFromSalePriceNumber = Float.parseFloat(rangeFromSalePrice); //Convertimos la cadena a número.
			float rangeUntilSalePriceNumber = Float.parseFloat(rangeUntilSalePrice); //Convertimos la cadena a número.
			products = filterBySalePriceRange(products, rangeFromSalePriceNumber, rangeUntilSalePriceNumber); //Nos quedamos con los productos que cumplan el filtro.
		}
		return products; //Retornamos los productos filtrados.
	}
			
	//Aplicamos los filtros seleccionados de las secciones categoría, género, color y precio de venta del producto:
	@Override
	public List<ProductDTO> applyFilters(List<ProductDTO> products, String category, String gender, String color, String salePrice,
											   String fromSalePrice, String untilSalePrice, String rangeFromSalePrice, String rangeUntilSalePrice)
	{
		//Aplicamos el filtro seleccionado de la sección categoría:
		if(!category.equals("all")) //Si se eligió alguna de las categorías:
		{
			products = filterByCategory(products, category); //Nos quedamos con los productos de la categoría del filtro.
		}
				
		//Aplicamos el filtro seleccionado de la sección género:
		if(!gender.equals("all")) //Si se eligió alguno de los géneros:
		{
			Character genderChar = gender.charAt(0); //Convertimos la cadena en un caracter.
			products = filterByGender(products, genderChar); //Nos quedamos con los productos del género del filtro.
		}
				
		//Aplicamos el filtro seleccionado de la sección color:
		if(!color.equals("all")) //Si se eligió alguno de los colores:
		{
			products = filterByColor(products, color); //Nos quedamos con los productos del color del filtro.
		}
				
		//Aplicamos el filtro seleccionado de la sección precio de venta:
		products = applyFilterTypeSalePrice(products, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice); 
				
		return products; //Retornamos los productos filtrados.
	}
	
	
	//Aplicamos los filtros seleccionados de las secciones categoría, género, talle, color y precio de venta del producto:
	@Override
	public List<ProductDTO> applyFilters(List<ProductDTO> products, String category, String gender, String size, String color, String salePrice,
			String fromSalePrice, String untilSalePrice, String rangeFromSalePrice, String rangeUntilSalePrice)
	{
		//Aplicamos el filtro seleccionado de la sección categoría:
		if(!category.equals("all")) //Si se eligió alguna de las categorías:
		{
			products = filterByCategory(products, category); //Nos quedamos con los productos de la categoría del filtro.
		}
		
		//Aplicamos el filtro seleccionado de la sección género:
		if(!gender.equals("all")) //Si se eligió alguno de los géneros:
		{
			Character genderChar = gender.charAt(0); //Convertimos la cadena en un caracter.
			products = filterByGender(products, genderChar); //Nos quedamos con los productos del género del filtro.
		}
		
		//Aplicamos el filtro seleccionado de la sección talle:
		if(!size.equals("all")) //Si se eligió alguno de los talles:
		{
			products = filterBySize(products, size); //Nos quedamos con los productos del talle del filtro.
		}
		
		//Aplicamos el filtro seleccionado de la sección color:
		if(!color.equals("all")) //Si se eligió alguno de los colores:
		{
			products = filterByColor(products, color); //Nos quedamos con los productos del color del filtro.
		}
		
		//Aplicamos el filtro seleccionado de la sección precio de venta:
		products = applyFilterTypeSalePrice(products, salePrice, fromSalePrice, untilSalePrice, rangeFromSalePrice, rangeUntilSalePrice); 
		
		return products; //Retornamos los productos filtrados.
	}
}
