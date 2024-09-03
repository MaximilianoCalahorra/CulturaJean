package com.calahorra.culturaJean.services.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	
	//Obtener:
	
	//Obtenemos todos los productos:
	@Override
	public List<Product> getAll()
	{
		return productRepository.findAll();
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
}
