package com.calahorra.culturaJean.services;

import com.calahorra.culturaJean.entities.UserRole;

///Interfaz IUserRoleService:
public interface IUserRoleService
{
	//Agregar:
	
	//Agregamos un rol de usuario a la base de datos:
	public UserRole insert(UserRole userRole);
}
