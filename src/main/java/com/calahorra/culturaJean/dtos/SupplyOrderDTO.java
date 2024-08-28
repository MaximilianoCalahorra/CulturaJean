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
	private ProductDTO product;
	private MemberDTO member;
	private SupplierDTO supplier;
	private int amount;
	private boolean delivered;
	
	//Constructor:
	public SupplyOrderDTO(int supplyOrderId, ProductDTO product, MemberDTO member, SupplierDTO supplier, int amount, boolean delivered) 
	{
		setSupplyOrderId(supplyOrderId);
		this.product = product;
		this.member = member;
		this.supplier = supplier;
		this.amount = amount;
		this.delivered = delivered;
	}
}
