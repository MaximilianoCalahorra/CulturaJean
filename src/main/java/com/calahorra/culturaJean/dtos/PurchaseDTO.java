package com.calahorra.culturaJean.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PurchaseDTO:
@Getter @Setter @NoArgsConstructor
public class PurchaseDTO
{
	//Atributos:
	private int purchaseId;
	private String methodOfPay;
	private LocalDateTime dateTime;
	
	//Constructor:
	public PurchaseDTO(int purchaseId, String methodOfPay, LocalDateTime dateTime) 
	{
		setPurchaseId(purchaseId);
		this.methodOfPay = methodOfPay;
		this.dateTime = dateTime;
	}
}
