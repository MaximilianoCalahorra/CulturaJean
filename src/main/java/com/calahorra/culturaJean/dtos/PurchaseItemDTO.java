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
	private ProductDTO product;
	private int amount;
	
	//Constructor:
	public PurchaseItemDTO(int purchaseItemId, ProductDTO product, int amount) 
	{
		setPurchaseItemId(purchaseItemId);
		this.product = product;
		this.amount = amount;
	}
	
	//Calcular:
	
	//Calculamos el subtotal del ítem de la compra:
	public float calculateSubtotalSale() 
	{
		return getAmount() * getProduct().getSalePrice();
	}
}
