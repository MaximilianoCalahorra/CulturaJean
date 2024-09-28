package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Product;

///Interfaz IProductRepository:
@Repository("productRepository")
public interface IProductRepository extends JpaRepository<Product, Serializable>
{
	//Encontrar:
	
	//Encontramos el producto con determinado id:
	public abstract Product findByProductId(int productId);
	
	//Encontramos el producto con determinado código:
	public abstract Product findByCode(String code);
	
	//Encontramos el producto con determinado nombre:
	public abstract Product findByName(String name);
	
	//Encontramos los productos de determinada categoría:
	public abstract List<Product> findByCategory(String category);
	
	//Encontramos los productos para determinado género:
	public abstract List<Product> findByGender(Character gender);
	
	//Encontramos los productos de determinado talle:
	public abstract List<Product> findBySize(String size);
	
	//Encontramos los productos de determinado color:
	public abstract List<Product> findByColor(String color);

	//Encontramos los productos activos/inactivos:
	public abstract List<Product> findByEnabled(boolean enabled);

	//Encontramos los productos con determinado precio de venta:
	public abstract List<Product> findBySalePrice(float salePrice);
	
	//Encontramos los productos con un precio de venta entre determinado rango:
	@Query("SELECT p FROM Product p WHERE p.salePrice >= (:minimumPrice) AND p.salePrice <= (:maximumPrice)")
	public abstract List<Product> findBySalePriceRange(@Param("minimumPrice")float minimumPrice, @Param("maximumPrice")float maximumPrice);
	
	//Encontramos los productos con un precio de venta menor o igual a uno determinado:
	@Query("SELECT p FROM Product p WHERE p.salePrice <= (:price)")
	public abstract List<Product> findBySalePriceLessThanOrEqualTo(@Param("price")float price);
	
	//Encontramos los productos con un precio de venta mayor o igual a uno determinado:
	@Query("SELECT p FROM Product p WHERE p.salePrice >= (:price)")
	public abstract List<Product> findBySalePriceGreaterThanOrEqualTo(@Param("price")float price);
	
	//Encontramos los productos con determinado nombre de imagen:
	@Query("SELECT p FROM Product p WHERE p.imageName = (:imageName) AND p.enabled = (:enabled)")
	public abstract List<Product> findByImageNameAndEnabled(@Param("imageName")String imageName, @Param("enabled")boolean enabled);
	
	//Encontramos un producto por cada nombre de imagen:
	@Query(value = "SELECT p.* FROM product p INNER JOIN (SELECT MIN(product_id) AS product_id FROM product GROUP BY image_name) AS subquery ON p.product_id = subquery.product_id", 
           nativeQuery = true)
	public abstract List<Product> findUniqueEachImageName();
	
	//Encontramos un producto habilitado/deshabilitado por cada nombre de imagen:
	@Query(value = "SELECT p.* FROM product p INNER JOIN (SELECT MIN(product_id) AS product_id FROM product WHERE enabled = (:enabled) GROUP BY image_name) AS subquery ON p.product_id = subquery.product_id", 
			nativeQuery = true)
	public abstract List<Product> findUniqueEnabledEachImageName(@Param("enabled")boolean enabled);
	
	//Encontramos un producto habilitado/deshabilitado de determinado talle por cada nombre de imagen:
	@Query(value = "SELECT p.* FROM product p INNER JOIN (SELECT MIN(product_id) AS product_id FROM product WHERE enabled = (:enabled) AND size = (:size) GROUP BY image_name) AS subquery ON p.product_id = subquery.product_id", 
			nativeQuery = true)
	public abstract List<Product> findUniqueSizeAndEnabledEachImageName(@Param("enabled")boolean enabled, @Param("size")String size);
	
	//Encontramos los talles de un producto según el filtro indicado:
	@Query("SELECT p.size FROM Product p WHERE p.imageName = (:imageName)")
	public abstract List<String> findUniqueEnabledEachSize(@Param("imageName")String imageName);

	//Ordenar:
	
	//Ordenamos los productos por el código de manera alfabética:
	@Query("SELECT p FROM Product p ORDER BY p.code")
	public abstract List<Product> getAllInOrderAscByCode();
	
	//Ordenamos los productos por el código de manera inversa al alfabeto:
	@Query("SELECT p FROM Product p ORDER BY p.code DESC")
	public abstract List<Product> getAllInOrderDescByCode();
		
	//Ordenamos los productos por la categoría de manera alfabética:
	@Query("SELECT p FROM Product p ORDER BY p.category")
	public abstract List<Product> getAllInOrderAscByCategory();
	
	//Ordenamos los productos por la categoría de manera inversa al alfabeto:
	@Query("SELECT p FROM Product p ORDER BY p.category DESC")
	public abstract List<Product> getAllInOrderDescByCategory();
		
	//Ordenamos los productos por el precio de venta de manera ascendente:
	@Query("SELECT p FROM Product p ORDER BY p.salePrice")
	public abstract List<Product> getAllInOrderAscBySalePrice();
	
	//Ordenamos los productos por el precio de venta de manera descendente:
	@Query("SELECT p FROM Product p ORDER BY p.salePrice DESC")
	public abstract List<Product> getAllInOrderDescBySalePrice();
	
	//Ordenamos los productos por el nombre de manera alfabética:
	@Query("SELECT p FROM Product p ORDER BY p.name")
	public abstract List<Product> getAllInOrderAscByName();
	
	//Ordenamos los productos por el nombre de manera inversa al alfabeto:
	@Query("SELECT p FROM Product p ORDER BY p.name DESC")
	public abstract List<Product> getAllInOrderDescByName();
}
