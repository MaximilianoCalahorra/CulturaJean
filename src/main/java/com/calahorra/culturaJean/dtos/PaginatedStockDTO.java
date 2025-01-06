package com.calahorra.culturaJean.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedStockDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedStockDTO 
{
	//Atributos:
    private List<StockDTO> stocks;
    private int totalPages;
    private long totalElements;
    private FiltersOptionsProductDTO filtersOptions;
}