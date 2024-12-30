package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase FiltersOptionsProductDTO:
@Getter @Setter @NoArgsConstructor
public class FiltersOptionsProductDTO 
{
	//Atributos:
    private List<String> categories;
    private List<String> genders;
    private List<String> sizes;
    private List<String> colors;
}
