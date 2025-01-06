package com.calahorra.culturaJean.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase PurchaseItemDTO:
@Getter @Setter @NoArgsConstructor
public class PurchaseItemDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//Atributos:
	private int purchaseItemId;
	private ProductDTO product;
	private int amount;
	private float totalPrice;
	
	//Constructor:
	public PurchaseItemDTO(ProductDTO product, int amount, float totalPrice) 
	{
		this.product = product;
		this.amount = amount;
		this.totalPrice = totalPrice;
	}
	
	public PurchaseItemDTO(int purchaseItemId, ProductDTO product, int amount, float totalPrice) 
	{
		setPurchaseItemId(purchaseItemId);
		this.product = product;
		this.amount = amount;
		this.totalPrice = totalPrice;
	}
}
