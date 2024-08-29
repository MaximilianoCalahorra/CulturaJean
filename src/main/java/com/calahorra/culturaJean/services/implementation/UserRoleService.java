package com.calahorra.culturaJean.services.implementation;

import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.entities.UserRole;
import com.calahorra.culturaJean.repositories.IUserRoleRepository;
import com.calahorra.culturaJean.services.IUserRoleService;

///Clase UserRoleService:
@Service("userRoleService")
public class UserRoleService implements IUserRoleService
{
	//Atributo:
	private IUserRoleRepository userRoleRepository;
	
	//Constructor:
	public UserRoleService(IUserRoleRepository userRoleRepository) 
	{
		this.userRoleRepository = userRoleRepository;
	}
	
	//Agregar:
	
	//Agregamos un rol de usuario a la base de datos:
	@Override
	public UserRole insert(UserRole userRole) 
	{
		return userRoleRepository.save(userRole);
	}
}
