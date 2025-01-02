package com.calahorra.culturaJean.repositories.custom;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.dtos.PurchaseDTO;

///Interfaz ICustomPurchaseRepository:
public interface ICustomPurchaseRepository
{
	//Obtenemos las opciones de filtros según las compras que cumplen con los filtros:
	public abstract List<Map<String, Object>> findFiltersOptions(LocalDate date, LocalDate fromDate, LocalDate untilDate,
																 LocalDate rangeFromDate, LocalDate rangeUntilDate, LocalTime fromTime, 
																 LocalTime untilTime, LocalTime rangeFromTime, LocalTime rangeUntilTime, 
																 List<String> methodsOfPay, List<String> usernames, Float fromPrice, 
																 Float untilPrice, Float rangeFromPrice, Float rangeUntilPrice);
	
	//Obtenemos las compras que cumplen con los filtros dentro de una página:
	public abstract Page<PurchaseDTO> findFilteredPurchases(LocalDate date, LocalDate fromDate, LocalDate untilDate, 
															LocalDate rangeFromDate, LocalDate rangeUntilDate, LocalTime fromTime, 
															LocalTime untilTime, LocalTime rangeFromTime, LocalTime rangeUntilTime, 
															List<String> methodsOfPay, List<String> usernames, Float fromPrice, 
															Float untilPrice, Float rangeFromPrice, Float rangeUntilPrice, String sort, 
															Pageable pageable);
	
	//Obtenemos la cantidad de compras que cumplen con los filtros:
	public abstract Long getTotalCount(LocalDate date, LocalDate fromDate, LocalDate untilDate, LocalDate rangeFromDate, 
									   LocalDate rangeUntilDate, LocalTime fromTime, LocalTime untilTime, LocalTime rangeFromTime,
									   LocalTime rangeUntilTime, List<String> methodsOfPay, List<String> usernames, Float fromPrice, 
									   Float untilPrice, Float rangeFromPrice, Float rangeUntilPrice);
}
