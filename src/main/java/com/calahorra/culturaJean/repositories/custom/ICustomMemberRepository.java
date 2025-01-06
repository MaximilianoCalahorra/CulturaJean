package com.calahorra.culturaJean.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.calahorra.culturaJean.entities.Member;

///Interfaz ICustomMemberRepository:
public interface ICustomMemberRepository
{	
	//Obtenemos los miembros que cumplen con los filtros dentro de una página:
	public abstract Page<Member> findFilteredMembers(String role, Boolean enabled, String sort, Pageable pageable);
	
	//Obtenemos la cantidad de miembros que cumplen con los filtros:
	public abstract Long getTotalCount(String role, Boolean enabled);
}
