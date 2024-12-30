package com.calahorra.culturaJean.repositories.custom;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.entities.Stock;

///Interfaz ICustomStockRepository:
public interface ICustomStockRepository
{
	//Obtenemos las opciones de filtros según los stocks que cumplen con los filtros:
	public abstract List<Map<String, Object>> findFiltersOptions(List<String> categories, List<Character> genders, List<String> sizes, 
																 List<String> colors, Float salePrice, Float fromSalePrice,
																 Float untilSalePrice, Float rangeFromSalePrice, Float rangeUntilSalePrice,
																 Integer actualAmount, Integer fromActualAmount, Integer untilActualAmount, 
																 Integer rangeFromActualAmount, Integer rangeUntilActualAmount, 
																 Boolean state);
	
	//Obtenemos los stocks que cumplen con los filtros dentro de una página:
	public abstract Page<Stock> findFilteredStocks(List<String> categories, List<Character> genders, List<String> sizes, 
												   List<String> colors, Float salePrice, Float fromSalePrice, Float untilSalePrice, 
												   Float rangeFromSalePrice, Float rangeUntilSalePrice, Integer actualAmount, 
												   Integer fromActualAmount, Integer untilActualAmount, Integer rangeFromActualAmount, 
												   Integer rangeUntilActualAmount, Boolean state, String sort, Pageable pageable);
	
	//Obtenemos la cantidad de stocks que cumplen con los filtros:
	public abstract Long getTotalCount(List<String> categories, List<Character> genders, List<String> sizes, List<String> colors, 
									   Float salePrice, Float fromSalePrice, Float untilSalePrice, Float rangeFromSalePrice, 
									   Float rangeUntilSalePrice, Integer actualAmount, Integer fromActualAmount, Integer untilActualAmount,
									   Integer rangeFromActualAmount, Integer rangeUntilActualAmount, Boolean state);
}
