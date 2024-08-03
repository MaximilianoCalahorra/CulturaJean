package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PurchaseItemDTO:
@Getter @Setter @NoArgsConstructor
public class PurchaseItemDTO
{
	//Atributos:
	private int purchaseItemId;
	private int amount;
	
	//Constructor:
	public PurchaseItemDTO(int purchaseItemId, int amount) 
	{
		setPurchaseItemId(purchaseItemId);
		this.amount = amount;
	}
}
