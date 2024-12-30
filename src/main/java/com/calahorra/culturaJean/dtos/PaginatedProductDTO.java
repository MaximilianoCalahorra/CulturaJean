package com.calahorra.culturaJean.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedProductDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedProductDTO 
{
	//Atributos:
    private List<ProductDTO> products;
    private int totalPages;
    private long totalElements;
    private FiltersOptionsProductDTO filtersOptions;
}