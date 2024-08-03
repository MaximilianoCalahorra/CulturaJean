package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase SupplierDTO:
@Getter @Setter @NoArgsConstructor
public class SupplierDTO
{
	//Atributos:
	private int supplierId;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	
	//Constructor:
	public SupplierDTO(int supplierId, String name, String address, String phoneNumber, String email)
	{
		setSupplierId(supplierId);
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
}
