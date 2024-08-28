package com.calahorra.culturaJean.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Clase MemberDTO:
@Getter @Setter @NoArgsConstructor
public class MemberDTO 
{
	//Atributos:
	private int MemberId;
	private String username;
	private boolean enabled;
	private String name;
	private String lastName;
	private String email;
	
	//Constructor:
	//Sin listado de roles asignado:
	public MemberDTO(int memberId, String username, boolean enabled, String name, String lastName, String email) 
	{
		setMemberId(memberId);
		this.username = username;
		this.enabled = enabled;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}
}
