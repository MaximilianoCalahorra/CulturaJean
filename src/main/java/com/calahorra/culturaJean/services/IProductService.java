package com.calahorra.culturaJean.services;

import java.util.List;

import org.springframework.data.repository.query.Param;
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
	
	//Encontramos los productos con determinado nombre de imagen que estén habilitados/deshabilitados:
	public List<ProductDTO> findByImageNameAndEnabled(String imageName, boolean enabled);
	
	//Encontramos un producto por cada nombre de imagen:
	public List<ProductDTO> findUniqueEachImageName();
	
	//Encontramos un producto habilitado/deshabilitado por cada nombre de imagen:
	public List<ProductDTO> findUniqueEnabledEachImageName(boolean enabled);
	
	//Encontramos un producto habilitado/deshabilitado de determinado talle por cada nombre de imagen:
	public List<ProductDTO> findUniqueSizeAndEnabledEachImageName(@Param("enabled")boolean enabled, @Param("size")String size);
	
	//Encontramos un ejemplar de cada categoría de producto:
	public List<String> findUniqueEachCategory(List<ProductDTO> products);
		
	//Encontramos un ejemplar de cada género de producto:
	public List<Character> findUniqueEachGender(List<ProductDTO> products);
		
	//Encontramos un ejemplar de cada talle de producto:
	public List<String> findUniqueEachSize(List<ProductDTO> products);
	
	//Encontramos un ejemplar de cada talle de producto:
	public List<String> findUniqueEnabledEachSize(List<ProductDTO> products, String sizeFilter);
		
	//Encontramos un ejemplar de cada color de producto:
	public List<String> findUniqueEachColor(List<ProductDTO> products);
	
	//Obtener:
	
	//Obtenemos todos los productos:
	public List<Product> getAll();
	
	//Obtenemos todos los productos como DTOs:
	public List<ProductDTO> getAllProducts();
	
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
	
	//Ordenamos el listado de productos por nombre de manera alfabética:
	public List<ProductDTO> inOrderAscByName(List<ProductDTO> products);
	
	//Ordenamos el listado de productos por nombre de manera inversa al alfabeto:
	public List<ProductDTO> inOrderDescByName(List<ProductDTO> products);
	
	//Ordenamos el listado de productos por precio de venta de manera ascendente:
	public List<ProductDTO> inOrderAscBySalePrice(List<ProductDTO> products);
	
	//Ordenamos el listado de productos por precio de venta de manera descendente:
	public List<ProductDTO> inOrderDescBySalePrice(List<ProductDTO> products);
	
	//Aplicamos el criterio de ordenamiento seleccionado:
	public List<ProductDTO> applyOrder(List<ProductDTO> products, String order);
	
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
	
	//Filtrar:
	
	//Filtramos el listado de productos por la categoría del producto:
	public List<ProductDTO> filterByCategory(List<ProductDTO> products, String category);
		
	//Filtramos el listado de productos por el género del producto:
	public List<ProductDTO> filterByGender(List<ProductDTO> products, Character gender);
		
	//Filtramos el listado de productos por el talle del producto:
	public List<ProductDTO> filterBySize(List<ProductDTO> products, String size);
		
	//Filtramos el listado de productos por el color del producto:
	public List<ProductDTO> filterByColor(List<ProductDTO> products, String color);
		
	//Filtramos el listado de productos por el precio de venta del producto:
	public List<ProductDTO> filterBySalePrice(List<ProductDTO> products, float salePrice);
		
	//Filtramos el listado de productos por el precio de venta del producto mayor o igual a uno determinado:
	public List<ProductDTO> filterByFromSalePrice(List<ProductDTO> products, float fromSalePrice);
		
	//Filtramos el listado de productos por el precio de venta del producto menor o igual a uno determinado:
	public List<ProductDTO> filterByUntilSalePrice(List<ProductDTO> products, float untilSalePrice);
		
	//Filtramos el listado de productos por el precio de venta del producto dentro de un rango determinado:
	public List<ProductDTO> filterBySalePriceRange(List<ProductDTO> products, float rangeFromSalePrice, float rangeUntilSalePrice);
		
	//Aplicamos el filtro seleccionado de la sección precio de venta:
	public List<ProductDTO> applyFilterTypeSalePrice(List<ProductDTO> products, String salePrice, String fromSalePrice, String untilSalePrice,
															  String rangeFromSalePrice, String rangeUntilSalePrice);
		
	//Aplicamos los filtros seleccionados de las secciones categoría, género,  color y precio de venta del producto:
	public List<ProductDTO> applyFilters(List<ProductDTO> products, String category, String gender, String color, String salePrice,
										   String fromSalePrice, String untilSalePrice, String rangeFromSalePrice, String rangeUntilSalePrice);
	
	//Aplicamos los filtros seleccionados de las secciones categoría, género, talle, color y precio de venta del producto:
	public List<ProductDTO> applyFilters(List<ProductDTO> products, String category, String gender, String size, String color, String salePrice,
			String fromSalePrice, String untilSalePrice, String rangeFromSalePrice, String rangeUntilSalePrice);
}
