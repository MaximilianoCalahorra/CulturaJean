package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase StockDTO:
@Getter @Setter @NoArgsConstructor
public class StockDTO
{
	//Atributos:
	private int stockId;
	private int desirableAmount;
	private int minimumAmount;
	private int actualAmount;
	
	//Constructor:
	public StockDTO(int StockId, int desirableAmount, int minimumAmount) 
	{
		setStockId(stockId);
		this.desirableAmount = desirableAmount;
		this.minimumAmount = minimumAmount;
		this.actualAmount = 0; //Al instanciar un stock su cantidad actual es 0.
	}
}
