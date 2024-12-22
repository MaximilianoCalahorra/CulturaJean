package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase ProductFiltersDataDTO:
@Getter @Setter @NoArgsConstructor
public class ProductFiltersDataDTO 
{
	//Atributos:
	private String order;
	private List<String> categories;
	private List<String> genders;
	private List<String> sizes;
	private List<String> colors;
	private String salePrice;
	private String fromSalePrice;
	private String untilSalePrice;
	private String rangeFromSalePrice;
	private String rangeUntilSalePrice;
	private String actualAmount;
	private String fromActualAmount;
	private String untilActualAmount;
	private String rangeFromActualAmount;
	private String rangeUntilActualAmount;
	private String state;
}
