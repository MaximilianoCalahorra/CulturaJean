package com.calahorra.culturaJean.repositories.custom;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.entities.Lot;

///Interfaz ICustomLotRepository:
public interface ICustomLotRepository
{
	//Obtenemos las opciones de filtros según los lotes que cumplen con los filtros:
	public abstract List<String> findFiltersOptions(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
												    LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, 
												    LocalDate rangeUntilReceptionDate, Integer existingAmount, Integer fromExistingAmount,
												    Integer untilExistingAmount, Integer rangeFromExistingAmount, 
												    Integer rangeUntilExistingAmount);
	
	//Obtenemos los lotes que cumplen con los filtros dentro de una página:
	public abstract Page<Lot> findFilteredLots(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
											   LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, 
											   LocalDate rangeUntilReceptionDate, Integer existingAmount, Integer fromExistingAmount, 
											   Integer untilExistingAmount, Integer rangeFromExistingAmount, 
											   Integer rangeUntilExistingAmount, String sort, Pageable pageable);
	
	//Obtenemos la cantidad de lotes que cumplen con los filtros:
	public abstract Long getTotalCount(List<Integer> stockIds, LocalDate receptionDate, LocalDate fromReceptionDate, 
									   LocalDate untilReceptionDate, LocalDate rangeFromReceptionDate, LocalDate rangeUntilReceptionDate, 
									   Integer existingAmount, Integer fromExistingAmount, Integer untilExistingAmount, 
									   Integer rangeFromExistingAmount, Integer rangeUntilExistingAmount);
}
