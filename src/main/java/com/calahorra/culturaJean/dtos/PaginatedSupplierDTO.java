package com.calahorra.culturaJean.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PaginatedSupplierDTO:
@Getter @Setter @NoArgsConstructor
public class PaginatedSupplierDTO 
{
	//Atributos:
    private List<SupplierDTO> suppliers;
    private int totalPages;
    private long totalElements;
}