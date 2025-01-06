package com.calahorra.culturaJean.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedLotDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedLotDTO 
{
	//Atributos:
    private List<LotDTO> lots;
    private int totalPages;
    private long totalElements;
    private FiltersOptionsLotDTO filtersOptions;
}