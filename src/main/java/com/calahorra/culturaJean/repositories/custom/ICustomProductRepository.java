package com.calahorra.culturaJean.repositories.custom;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.entities.Product;

///Interfaz ICustomProductRepository:
public interface ICustomProductRepository
{
	//Obtenemos las opciones de filtros según los productos que cumplen con los filtros:
	public abstract List<Map<String, Object>> findFiltersOptions(List<String> categories, List<Character> genders, List<String> sizes, 
																 List<String> colors, Float salePrice, Float fromSalePrice,
																 Float untilSalePrice, Float rangeFromSalePrice, Float rangeUntilSalePrice, 
																 Boolean state);
	
	//Obtenemos los productos que cumplen con los filtros dentro de una página:
	public abstract Page<Product> findFilteredProducts(List<String> categories, List<Character> genders, List<String> sizes, 
												       List<String> colors, Float salePrice, Float fromSalePrice, Float untilSalePrice, 
												       Float rangeFromSalePrice, Float rangeUntilSalePrice, Boolean state, String sort, 
												       Pageable pageable);
	
	//Obtenemos la cantidad de productos que cumplen con los filtros:
	public abstract Long getTotalCount(List<String> categories, List<Character> genders, List<String> sizes, List<String> colors, 
									   Float salePrice, Float fromSalePrice, Float untilSalePrice, Float rangeFromSalePrice, 
									   Float rangeUntilSalePrice, Boolean state);
}
