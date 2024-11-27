package com.calahorra.culturaJean.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase LotFiltersDataDTO:
@Getter @Setter @NoArgsConstructor
public class LotFiltersDataDTO 
{
	//Atributos:
	private String order;
	private List<String> stockIds;
	private String receptionDate;
	private String fromReceptionDate;
	private String untilReceptionDate;
	private String rangeFromReceptionDate;
	private String rangeUntilReceptionDate;
	private String amount;
	private String fromAmount;
	private String untilAmount;
	private String rangeFromAmount;
	private String rangeUntilAmount;
}
