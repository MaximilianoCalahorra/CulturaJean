package com.calahorra.culturaJean.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PurchaseDTO:
@Getter @Setter @NoArgsConstructor
public class PurchaseDTO
{
	//Atributos:
	private int purchaseId;
	private UserDTO user;
	private Set<PurchaseItemDTO> purchaseItems;
	private String methodOfPay;
	private LocalDateTime dateTime;
	
	//Constructor:
	public PurchaseDTO(int purchaseId, UserDTO user,String methodOfPay, LocalDateTime dateTime) 
	{
		setPurchaseId(purchaseId);
		this.user = user;
		this.methodOfPay = methodOfPay;
		this.dateTime = dateTime;
	}
}
