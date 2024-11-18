package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase SupplyOrderFiltersDataDTO:
@Getter @Setter @NoArgsConstructor
public class SupplyOrderFiltersDataDTO 
{
	//Atributos:
	private String order;
	private List<String> productCodes;
	private List<String> supplierNames;
	private List<String> adminUsernames;
	private String amount;
	private String fromAmount;
	private String untilAmount;
	private String rangeFromAmount;
	private String rangeUntilAmount;
	private String delivered;
}
