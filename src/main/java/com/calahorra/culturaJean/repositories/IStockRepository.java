package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Stock;

///Interfaz IStockRepository:
@Repository("stockRepository")
public interface IStockRepository extends JpaRepository<Stock, Serializable>
{
	//Encontrar:
	
	//Encontramos el stock con determinado id:
	public abstract Stock findByStockId(int stockId);
	
	//Encontramos el stock con determinado id y su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product WHERE s.stockId = (:stockId)")
	public abstract Stock findByStockIdWithProduct(@Param("stockId")int stockId);
	
	//Encontramos el stock de determinado producto:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.productId = (:productId)")
	public abstract Stock findByProduct(@Param("productId")int productId);
	
	//Encontramos el stock del producto con determinado código:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.code = (:code)")
	public abstract Stock findByProductCode(@Param("code")String code);
	
	//Encontramos el stock del producto con determinado nombre:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.name = (:name)")
	public abstract Stock findByProductName(@Param("name")String name);
		
	//Encontramos el stock de cada producto de determinada categoría:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.category = (:category)")
	public abstract List<Stock> findByProductCategory(@Param("category")String category);
	
	//Encontramos el stock de cada producto de determinado género:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.gender = (:gender)")
	public abstract List<Stock> findByProductGender(@Param("gender")Character gender);
	
	//Encontramos el stock de cada producto de determinado talle:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.size = (:size)")
	public abstract List<Stock> findByProductSize(@Param("size")String size);
	
	//Encontramos el stock de cada producto de determinado color:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.color = (:color)")
	public abstract List<Stock> findByProductColor(@Param("color")String color);
	
	//Encontramos el stock de cada producto habilita:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.enabled = (:enabled)")
	public abstract List<Stock> findByProductEnabled(@Param("enabled")boolean enabled);
	
	//Encontramos el stock de cada producto de determinado precio de venta:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.salePrice = (:salePrice)")
	public abstract List<Stock> findByProductSalePrice(@Param("salePrice")float salePrice);
	
	//Encontramos el stock de cada producto con un precio de venta menor o igual a uno determinado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.salePrice <= (:maximumPrice)")
	public abstract List<Stock> findByProductSalePriceLessThanOrEqualTo(@Param("maximumPrice")float maximumPrice);
	
	//Encontramos el stock de cada producto con un precio de venta mayor o igual a uno determinado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.salePrice >= (:minimumPrice)")
	public abstract List<Stock> findByProductSalePriceGreaterThanOrEqualTo(@Param("minimumPrice")float minimumPrice);
		
	//Encontramos los productos con un precio de venta entre determinado rango:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p WHERE p.salePrice >= (:minimumPrice) AND p.salePrice <= (:maximumPrice)")
	public abstract List<Stock> findBySalePriceRange(@Param("minimumPrice")float minimumPrice, @Param("maximumPrice")float maximumPrice);
		
	//Ordenar:
	
	//Ordenamos los stocks por id de manera ascendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.stockId")
	public abstract List<Stock> getAllInOrderAscByStockId();
	
	//Ordenamos los stocks por id de manera descendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.stockId DESC")
	public abstract List<Stock> getAllInOrderDescByStockId();
	
	//Ordenamos los stocks por cantidad deseable de manera ascendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.desirableAmount")
	public abstract List<Stock> getAllInOrderAscByDesirableAmount();
	
	//Ordenamos los stocks por cantidad deseable de manera descendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.desirableAmount DESC")
	public abstract List<Stock> getAllInOrderDescByDesirableAmount();
	
	//Ordenamos los stocks por cantidad mínima de manera ascendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.minimumAmount")
	public abstract List<Stock> getAllInOrderAscByMinimumAmount();
	
	//Ordenamos los stocks por cantidad mínima de manera descendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.minimumAmount DESC")
	public abstract List<Stock> getAllInOrderDescByMinimumAmount();
	
	//Ordenamos los stocks por cantidad actual de manera ascendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.actualAmount")
	public abstract List<Stock> getAllInOrderAscByActualAmount();
	
	//Ordenamos los stocks por cantidad actual de manera descendente con su producto asociado:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product ORDER BY s.actualAmount DESC")
	public abstract List<Stock> getAllInOrderDescByActualAmount();
	
	//Ordenamos los stocks por el código del producto asociado de manera ascendente:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.code")
	public abstract List<Stock> getAllInOrderAscByProductCode();
	
	//Ordenamos los stocks por el código del producto asociado de manera descendente:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.code DESC")
	public abstract List<Stock> getAllInOrderDescByProductCode();
	
	//Ordenamos los stocks por la categoría del producto asociado de manera alfabética:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.category")
	public abstract List<Stock> getAllInOrderAscByProductCategory();
	
	//Ordenamos los stocks por la categoría del producto asociado de manera inversa al alfabeto:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.category DESC")
	public abstract List<Stock> getAllInOrderDescByProductCategory();
	
	//Ordenamos los stocks por el precio de venta del producto asociado de manera ascendente:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.salePrice")
	public abstract List<Stock> getAllInOrderAscByProductSalePrice();
	
	//Ordenamos los stocks por el precio de venta del producto asociado de manera descendente:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.salePrice DESC")
	public abstract List<Stock> getAllInOrderDescByProductSalePrice();
	
	//Ordenamos los stocks por el nombre del producto asociado de manera alfabética:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.name")
	public abstract List<Stock> getAllInOrderAscByProductName();
	
	//Ordenamos los stocks por el nombre del producto asociado de manera inversa al alfabeto:
	@Query("SELECT s FROM Stock s INNER JOIN FETCH s.product p ORDER BY p.name DESC")
	public abstract List<Stock> getAllInOrderDescByProductName();
}
