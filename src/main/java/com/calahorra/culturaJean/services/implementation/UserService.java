package com.calahorra.culturaJean.services.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.UserDTO;
import com.calahorra.culturaJean.entities.UserRole;
import com.calahorra.culturaJean.repositories.IUserRepository;

///Clase UserService:
@Service("userService")
public class UserService implements UserDetailsService
{
	//Atributos:
	private IUserRepository userRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public UserService(IUserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		com.calahorra.culturaJean.entities.User user = userRepository.findByUsernameAndFetchUserRolesEagerly(username);
		return buildUser(user, buildGrantedAuthorities(user.getUserRoles()));
	}

	private User buildUser(com.calahorra.culturaJean.entities.User user, List<GrantedAuthority> grantedAuthorities)
	{
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
						true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
						grantedAuthorities);
	}

	private List<GrantedAuthority> buildGrantedAuthorities(Set<UserRole> userRoles)
	{
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for(UserRole userRole: userRoles)
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<>(grantedAuthorities);
	}
	
	//Encontrar:
	
	//Usuario con sus roles por determinado nombre de usuario:
	public com.calahorra.culturaJean.entities.User findByUsernameAndFetchUserRolesEagerly(String username) 
	{
		return userRepository.findByUsernameAndFetchUserRolesEagerly(username);
	}
	
	//Encontramos el usuario con determinado id y sus roles asociados:
	public com.calahorra.culturaJean.entities.User findByUserIdWithUserRoles(int userId)
	{
		return userRepository.findByUserIdWithUserRoles(userId);
	}
	
	//Encontramos los usuarios con determinado rol:
	public List<UserDTO> findByUserRole(String role)
	{
		return userRepository.findByUserRole(role) //Obtenemos los usuarios con ese rol como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtener:
	
	//Obtenemos todos los usuarios:
	public List<com.calahorra.culturaJean.entities.User> getAll()
	{
		return userRepository.findAll();
	}
	
	//Ordenar:
	
	//Ordenamos los usuarios de determinado rol por nombre de forma ascendente:
	public List<UserDTO> getByUserRoleInOrderAscByName(String role)
	{
		return userRepository.getByUserRoleInOrderAscByName(role) //Obtenemos los usuarios ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los usuarios de determinado rol por nombre de forma descendente:
	public List<UserDTO> getByUserRoleInOrderDescByName(String role)
	{
		return userRepository.getByUserRoleInOrderDescByName(role) //Obtenemos los usuarios ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los usuarios de determinado rol por apellido de forma ascendente:
	public List<UserDTO> getByUserRoleInOrderAscByLastName(String role)
	{
		return userRepository.getByUserRoleInOrderAscByLastName(role) //Obtenemos los usuarios ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los usuarios de determinado rol por apellido de forma descendente:
	public List<UserDTO> getByUserRoleInOrderDescByLastName(String role)
	{
		return userRepository.getByUserRoleInOrderDescByLastName(role) //Obtenemos los usuarios ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los usuarios de determinado rol por nombre de usuario de forma ascendente:
	public List<UserDTO> getByUserRoleInOrderAscByUsername(String role)
	{
		return userRepository.getByUserRoleInOrderAscByUsername(role) //Obtenemos los usuarios ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los usuarios de determinado rol por nombre de usuario de forma descendente:
	public List<UserDTO> getByUserRoleInOrderDescByUsername(String role)
	{
		return userRepository.getByUserRoleInOrderDescByUsername(role) //Obtenemos los usuarios ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, UserDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Agregar:
	
	//Agregamos un usuario a la base de datos:
	public UserDTO insert(com.calahorra.culturaJean.entities.User user) throws Exception
	{
		//Validamos que no exista otro usuario con el nombre de usuario elegido:
		if(findByUsernameAndFetchUserRolesEagerly(user.getUsername()) != null) 
		{
			throw new Exception("Error! There is a user with username " + user.getUsername());
		}
		return modelMapper.map(userRepository.save(user), UserDTO.class); //Insertamos el usuario en la base de datos y lo retornamos como DTO.
	}
	
	//Modificar:
	
	//Modificamos un usuario de la base de datos:
	public UserDTO update(com.calahorra.culturaJean.entities.User user) throws Exception
	{
		com.calahorra.culturaJean.entities.User existingUser = findByUsernameAndFetchUserRolesEagerly(user.getUsername());
		/*
		Validamos dos cuestiones:
		1. Si existe un usuario con ese nombre de usuario.
		2. Si el usuario existente tiene un id distinto que el que se quiere modificar.
		De esta forma, logramos que no se le pueda asignar un nombre de usuario de otro usuario a uno que se está modificando a menos
		de que sea el usuario propietario de ese nombre de usuario y se estén modificando otros datos del mismo.
		*/
		if(existingUser != null && existingUser.getUserId() != user.getUserId()) 
		{
			throw new Exception("Error! There is a user with username " + user.getUsername());
		}
		return modelMapper.map(userRepository.save(user), UserDTO.class); //Modificamos el usuario en la base de datos y lo retornamos como DTO.
	}
	
	//Eliminar:
	
	//Deshabilitamos el usuario con determinado nombre de usuario:
	public boolean logicalDelete(String username)
	{
		try 
		{
			com.calahorra.culturaJean.entities.User user = findByUsernameAndFetchUserRolesEagerly(username); //Buscamos el usuario.
			user.setEnabled(false); //Deshabilitamos el usuario.
			userRepository.save(user); //Modificamos el registro del usuario en la base de datos.
			return true;
		}
		catch(Exception e) 
		{
			return false;
		}
	}
}
