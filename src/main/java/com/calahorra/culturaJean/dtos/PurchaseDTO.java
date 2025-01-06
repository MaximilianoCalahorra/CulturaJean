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
	private MemberDTO member;
	private Set<PurchaseItemDTO> purchaseItems;
	private String methodOfPay;
	private float totalPrice;
	private LocalDateTime dateTime;
	
	//Constructor:
	public PurchaseDTO(MemberDTO member, Set<PurchaseItemDTO> purchaseItems) 
	{
		this.member = member;
		this.purchaseItems = purchaseItems;
	}
	
	public PurchaseDTO(int purchaseId, MemberDTO member, String methodOfPay, float totalPrice, LocalDateTime dateTime) 
	{
		setPurchaseId(purchaseId);
		this.member = member;
		this.methodOfPay = methodOfPay;
		this.totalPrice = totalPrice;
		this.dateTime = dateTime;
	}
}
