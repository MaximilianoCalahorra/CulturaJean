package com.calahorra.culturaJean.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.entities.Product;

///Interfaz IProductService:
public interface IProductService
{
	//Encontrar:
	
	//Encontramos el producto con determinado id:
	public Product findByProductId(int productId);
	
	//Encontramos el producto con determinado código:
	public ProductDTO findByCode(String code);
	
	//Encontramos el producto con determinado nombre:
	public ProductDTO findByName(String name);
	
	//Encontramos los productos de determinada categoría:
	public List<ProductDTO> findByCategory(String category);
	
	//Encontramos los productos para determinado género:
	public List<ProductDTO> findByGender(Character gender);
	
	//Encontramos los productos de determinado talle:
	public List<ProductDTO> findBySize(String size);
	
	//Encontramos los productos de determinado color:
	public List<ProductDTO> findByColor(String color);

	//Encontramos los productos activos/inactivos:
	public List<ProductDTO> findByEnabled(boolean enabled);

	//Encontramos los productos con determinado precio de venta:
	public List<ProductDTO> findBySalePrice(float salePrice);
	
	//Encontramos los productos con un precio de venta entre determinado rango:
	public List<ProductDTO> findBySalePriceRange(float minimumPrice, float maximumPrice);
	
	//Encontramos los productos con un precio de venta menor o igual a uno determinado:
	public List<ProductDTO> findBySalePriceLessThanOrEqualTo(float price);
	
	//Encontramos los productos con un precio de venta mayor o igual a uno determinado:
	public List<ProductDTO> findBySalePriceGreaterThanOrEqualTo(float price);
	
	//Obtener:
	
	//Obtenemos todos los productos:
	public List<Product> getAll();
	
	//Ordenar:
	
	//Ordenamos los productos por el código de manera alfabética:
	public List<ProductDTO> getAllInOrderAscByCode();
	
	//Ordenamos los productos por el código de manera inversa al alfabeto:
	public List<ProductDTO> getAllInOrderDescByCode();
		
	//Ordenamos los productos por la categoría de manera alfabética:
	public List<ProductDTO> getAllInOrderAscByCategory();
	
	//Ordenamos los productos por la categoría de manera inversa al alfabeto:
	public List<ProductDTO> getAllInOrderDescByCategory();
		
	//Ordenamos los productos por el precio de venta de manera ascendente:
	public List<ProductDTO> getAllInOrderAscBySalePrice();
	
	//Ordenamos los productos por el precio de venta de manera descendente:
	public List<ProductDTO> getAllInOrderDescBySalePrice();
	
	//Ordenamos los productos por el nombre de manera alfabética:
	public List<ProductDTO> getAllInOrderAscByName();
	
	//Ordenamos los productos por el nombre de manera inversa al alfabeto:
	public List<ProductDTO> getAllInOrderDescByName();
	
	//Agregar:
	
	//Agregamos un producto a la base de datos:
	public ProductDTO insert(ProductDTO product) throws Exception;
	
	//Modificar:
	
	//Modificamos un producto de la base de datos:
	public ProductDTO update(ProductDTO product) throws Exception;
	
	//Eliminar:
	
	//Deshabilitamos el producto con determinado id de la base de datos:
	public boolean logicalDelete(int productId);
	
	//Subir:
	
	//Subimos una imagen al servidor y retornamos el producto que la llevará:
	public boolean uploadImage(ProductDTO product, MultipartFile file);
}
