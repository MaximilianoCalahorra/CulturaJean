package com.calahorra.culturaJean.repositories.custom;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.entities.SupplyOrder;

///Interfaz ICustomSupplyOrderRepository:
public interface ICustomSupplyOrderRepository
{
	//Obtenemos las opciones de filtros según los pedidos de aprovisionamiento que cumplen con los filtros:
	public abstract List<Map<String, Object>> findFiltersOptions(List<String> productCodes, List<String> supplierNames, 
																 List<String> adminUsernames, Integer amount, Integer fromAmount, 
																 Integer untilAmount, Integer rangeFromAmount, Integer rangeUntilAmount,
																 Boolean delivered, String findOptionsQueryBase);
	
	//Obtenemos los pedidos de aprovisionamiento que cumplen con los filtros dentro de una página:
	public abstract Page<SupplyOrder> findFilteredSupplyOrders(List<String> productCodes, List<String> supplierNames, 
															   List<String> adminUsernames, Integer amount, Integer fromAmount, 
															   Integer untilAmount, Integer rangeFromAmount, Integer rangeUntilAmount, 
															   Boolean delivered, String sort, Pageable pageable, String findSOQueryBase,
															   String countSOQueryBase);
	
	//Obtenemos la cantidad de pedidos de aprovisionamiento que cumplen con los filtros:
	public abstract Long getTotalCount(List<String> productCodes, List<String> supplierNames, List<String> adminUsernames, Integer amount, 
									   Integer fromAmount, Integer untilAmount, Integer rangeFromAmount, Integer rangeUntilAmount, 
									   Boolean delivered, String countSOQueryBase);
}
