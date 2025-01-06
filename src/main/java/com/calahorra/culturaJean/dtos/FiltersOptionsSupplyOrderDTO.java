package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase FiltersOptionsSupplyOrderDTO:
@Getter @Setter @NoArgsConstructor
public class FiltersOptionsSupplyOrderDTO 
{
	//Atributos:
    private List<String> productCodes;
    private List<String> supplierNames;
    private List<String> adminUsernames;
}
