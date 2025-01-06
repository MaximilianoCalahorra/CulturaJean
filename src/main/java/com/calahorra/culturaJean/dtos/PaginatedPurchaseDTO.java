package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedPurchaseDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedPurchaseDTO 
{
	//Atributos:
    private List<PurchaseDTO> purchases;
    private int totalPages;
    private long totalElements;
    private FiltersOptionsPurchaseDTO filtersOptions;
}