package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase ProductDTO:
@Getter @Setter @NoArgsConstructor
public class ProductDTO
{
	//Atributos:
	private int productId;
	private String code;
	private String category;
	private Character gender;
	private String size;
	private String color;
	private float cost;
	private float salePrice;
	private String name;
	private String description;
	private String imageName;
	private boolean enabled;
	
	//Constructor:
	public ProductDTO(int productId, String code, String category, Character gender, String size, String color, float cost,
			          float salePrice, String name, String description, String imageName, boolean enabled) 
	{
		setProductId(productId);
		this.code = code;
		this.category = category;
		this.gender = gender;
		this.size = size;
		this.color = color;
		this.cost = cost;
		this.salePrice = salePrice;
		this.name = name;
		this.description = description;
		this.imageName = imageName;
		this.enabled = enabled;
	}
}
