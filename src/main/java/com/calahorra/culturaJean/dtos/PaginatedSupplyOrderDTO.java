package com.calahorra.culturaJean.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedSupplyOrderDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedSupplyOrderDTO 
{
	//Atributos:
    private List<SupplyOrderDTO> supplyOrders;
    private int totalPages;
    private long totalElements;
    private FiltersOptionsSupplyOrderDTO filtersOptions;
}