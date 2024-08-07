package com.calahorra.culturaJean.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase Lot:
@Getter @Setter @NoArgsConstructor
public class LotDTO
{
	//Atributos:
	private int lotId;
	private StockDTO stock;
	private SupplyOrderDTO supplyOrder;
	private LocalDate receptionDate;
	@Min(1)
	private int initialAmount;
	private int existingAmount;
	private float purchasePrice;
	
	//Constructor:
	public LotDTO(int lotId, StockDTO stock, SupplyOrderDTO supplyOrder, LocalDate receptionDate, int initialAmount, int existingAmount,
			      float purchasePrice) 
	{
		setLotId(lotId);
		this.stock = stock;
		this.supplyOrder = supplyOrder;
		this.initialAmount = initialAmount;
		this.existingAmount = existingAmount;
		this.purchasePrice = purchasePrice;
	}
}
