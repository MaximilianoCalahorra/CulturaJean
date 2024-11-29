package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PurchaseFiltersDataDTO:
@Getter @Setter @NoArgsConstructor
public class PurchaseFiltersDataDTO 
{
	//Atributos:
	private String order;
	private String date;
	private String fromDate;
	private String untilDate;
	private String rangeFromDate;
	private String rangeUntilDate;
	private String fromTime;
	private String untilTime;
	private String rangeFromTime;
	private String rangeUntilTime;
	private List<String> usernames;
	private List<String> methodsOfPay;
	private String fromPrice;
	private String untilPrice;
	private String rangeFromPrice;
	private String rangeUntilPrice;
}
