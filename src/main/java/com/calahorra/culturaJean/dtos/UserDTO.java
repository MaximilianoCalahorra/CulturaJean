package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase UserDTO:
@Getter @Setter @NoArgsConstructor
public class UserDTO 
{
	//Atributos:
	private int userId;
	private String username;
	private boolean enabled;
	private String name;
	private String lastName;
	private String email;
	
	//Constructor:
	//Sin listado de roles asignado:
	public UserDTO(int userId, String username, boolean enabled, String name, String lastName, String email) 
	{
		setUserId(userId);
		this.username = username;
		this.enabled = enabled;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}
}
