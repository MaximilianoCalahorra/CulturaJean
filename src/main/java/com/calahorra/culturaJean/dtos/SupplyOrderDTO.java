package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase SupplyOrderDTO:
@Getter @Setter @NoArgsConstructor
public class SupplyOrderDTO
{
	//Atributos:
	private int supplyOrderId;
	private int amount;
	private boolean delivered;
	
	//Constructor:
	public SupplyOrderDTO(int supplyOrderId, int amount, boolean delivered) 
	{
		setSupplyOrderId(supplyOrderId);
		this.amount = amount;
		this.delivered = delivered;
	}
}
