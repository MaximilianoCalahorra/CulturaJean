package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase FiltersOptionsPurchaseDTO:
@Getter @Setter @NoArgsConstructor
public class FiltersOptionsPurchaseDTO 
{
	//Atributos:
    private List<String> methodsOfPay;
    private List<String> usernames;
}
