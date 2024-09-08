package com.calahorra.culturaJean.services.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.MemberDTO;
import com.calahorra.culturaJean.entities.Member;
import com.calahorra.culturaJean.entities.UserRole;
import com.calahorra.culturaJean.repositories.IMemberRepository;

///Clase MemberService:
@Service("memberService")
public class MemberService implements UserDetailsService
{
	//Atributos:
	private IMemberRepository memberRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public MemberService(IMemberRepository memberRepository)
	{
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Member member = memberRepository.findByUsernameAndFetchUserRolesEagerly(username);
		return buildUser(member, buildGrantedAuthorities(member.getUserRoles()));
	}

	private User buildUser(Member member, List<GrantedAuthority> grantedAuthorities)
	{
		return new User(member.getUsername(), member.getPassword(), member.isEnabled(),
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
	
	//Miembro con sus roles por determinado nombre de usuario:
	public Member findByUsernameAndFetchUserRolesEagerly(String username) 
	{
		return memberRepository.findByUsernameAndFetchUserRolesEagerly(username);
	}
	
	//Miembro como DTO por determinado nombre de usuario:
	public MemberDTO findByUsername(String username) 
	{
		return modelMapper.map(memberRepository.findByUsernameAndFetchUserRolesEagerly(username), MemberDTO.class);
	}
	
	//Encontramos el miembro con determinado id y sus roles asociados:
	public Member findByMemberIdWithUserRoles(int memberId)
	{
		return memberRepository.findByMemberIdWithUserRoles(memberId);
	}
	
	//Encontramos los miembros con determinado rol:
	public List<MemberDTO> findByUserRole(String role)
	{
		return memberRepository.findByUserRole(role) //Obtenemos los miembros con ese rol como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los miembros habilitados/deshabilitados con determinado rol:
	public List<MemberDTO> findByEnabledAndUserRole(boolean enabled, String role)
	{
		return memberRepository.findByEnabledAndUserRole(enabled, role) //Obtenemos los miembros en ese estado y con ese rol como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtener:
	
	//Obtenemos todos los miembros:
	public List<Member> getAll()
	{
		return memberRepository.findAll();
	}
	
	//Ordenar:
	
	//Ordenamos los miembros de determinado rol por nombre de forma ascendente:
	public List<MemberDTO> getByUserRoleInOrderAscByName(String role)
	{
		return memberRepository.getByUserRoleInOrderAscByName(role) //Obtenemos los miembros ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los miembros de determinado rol por nombre de forma descendente:
	public List<MemberDTO> getByUserRoleInOrderDescByName(String role)
	{
		return memberRepository.getByUserRoleInOrderDescByName(role) //Obtenemos los miembros ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los miembros de determinado rol por apellido de forma ascendente:
	public List<MemberDTO> getByUserRoleInOrderAscByLastName(String role)
	{
		return memberRepository.getByUserRoleInOrderAscByLastName(role) //Obtenemos los miembros ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los miembros de determinado rol por apellido de forma descendente:
	public List<MemberDTO> getByUserRoleInOrderDescByLastName(String role)
	{
		return memberRepository.getByUserRoleInOrderDescByLastName(role) //Obtenemos los miembros ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los miembros de determinado rol por nombre de usuario de forma ascendente:
	public List<MemberDTO> getByUserRoleInOrderAscByUsername(String role)
	{
		return memberRepository.getByUserRoleInOrderAscByUsername(role) //Obtenemos los miembros ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los miembros de determinado rol por nombre de usuario de forma descendente:
	public List<MemberDTO> getByUserRoleInOrderDescByUsername(String role)
	{
		return memberRepository.getByUserRoleInOrderDescByUsername(role) //Obtenemos los miembros ordenados como entidades.
				.stream()
				.map(user -> modelMapper.map(user, MemberDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los miembros por nombre de forma alfabética:
	public List<MemberDTO> inOrderAscByName(List<MemberDTO> members)
	{
		Collections.sort(members, (m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));
		return members;
	}
	
	//Ordenamos los miembros por nombre de forma inversa al alfabeto:
	public List<MemberDTO> inOrderDescByName(List<MemberDTO> members)
	{
		Collections.sort(members, (m1, m2) -> m2.getName().compareToIgnoreCase(m1.getName()));
		return members;
	}
	
	//Ordenamos los miembros por apellido de forma alfabética:
	public List<MemberDTO> inOrderAscByLastName(List<MemberDTO> members)
	{
		Collections.sort(members, (m1, m2) -> m1.getLastName().compareToIgnoreCase(m2.getLastName()));
		return members;
	}
	
	//Ordenamos los miembros por apellido de forma inversa al alfabeto:
	public List<MemberDTO> inOrderDescByLastName(List<MemberDTO> members)
	{
		Collections.sort(members, (m1, m2) -> m2.getLastName().compareToIgnoreCase(m1.getLastName()));
		return members;
	}
	
	//Ordenamos los miembros por nombre de usuario de forma alfabética:
	public List<MemberDTO> inOrderAscByUsername(List<MemberDTO> members)
	{
		Collections.sort(members, (m1, m2) -> m1.getUsername().compareToIgnoreCase(m2.getUsername()));
		return members;
	}
	
	//Ordenamos los miembros por nombre de usuario de forma inversa al alfabeto:
	public List<MemberDTO> inOrderDescByUsername(List<MemberDTO> members)
	{
		Collections.sort(members, (m1, m2) -> m2.getUsername().compareToIgnoreCase(m1.getUsername()));
		return members;
	}
	
	//Agregar:
	
	//Agregamos un mimebro a la base de datos:
	public MemberDTO insert(Member member) throws Exception
	{
		//Validamos que no exista otro miembro con el nombre de usuario elegido:
		if(findByUsernameAndFetchUserRolesEagerly(member.getUsername()) != null) 
		{
			throw new Exception("There is alredy a member with username " + member.getUsername());
		}
		return modelMapper.map(memberRepository.save(member), MemberDTO.class); //Insertamos el miembro en la base de datos y lo retornamos como DTO.
	}
	
	//Modificar:
	
	//Modificamos un miembro de la base de datos:
	public MemberDTO update(Member member) throws Exception
	{
		Member existingMember = findByUsernameAndFetchUserRolesEagerly(member.getUsername());
		/*
		Validamos dos cuestiones:
		1. Si existe un miembro con ese nombre de usuario.
		2. Si el miembro existente tiene un id distinto que el que se quiere modificar.
		De esta forma, logramos que no se le pueda asignar un nombre de usuario de otro miembro a uno que se está modificando a menos
		de que sea el miempro propietario de ese nombre de usuario y se estén modificando otros datos del mismo.
		*/
		if(existingMember != null && existingMember.getMemberId() != member.getMemberId()) 
		{
			throw new Exception("There is alredy a member with username " + member.getUsername());
		}
		return modelMapper.map(memberRepository.save(member), MemberDTO.class); //Modificamos el miembro en la base de datos y lo retornamos como DTO.
	}
	
	//Eliminar:
	
	//Deshabilitamos el miembro con determinado nombre de usuario:
	public boolean logicalDelete(String username)
	{
		try 
		{
			Member member = findByUsernameAndFetchUserRolesEagerly(username); //Buscamos el miembro.
			member.setEnabled(false); //Deshabilitamos el miembro.
			memberRepository.save(member); //Modificamos el registro del miembro en la base de datos.
			return true;
		}
		catch(Exception e) 
		{
			return false;
		}
	}
	
	//Validar:
	
	//Validamos que el email indicado para un miembro sea del formato apropiado:
	public boolean validateEmail(String email)
	{
		//Los emails válidos son aquellos que tengan:
		//- Debe empezar con una letra minúscula o mayúscula.
		//- Debe haber como máximo uno de '-', '.', '_', '+' o '&'.
		//- Debe continuar con números, letras minúsculas o mayúsculas.
		//- Debe seguir con un '@'.
		//- Debe seguir con números, letras minúsculas o mayúsculas y/o '-'.
		//- Debe continuar con un '.'
		//- Las dos condiciones anteriores se pueden repetir pero en ese orden.
		//- Debe finalizar con letras minúsculas o mayúsculas con una cantidad mínima de 2 y máxima de 6.
		Pattern pattern = Pattern.compile("^([a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$"); //Definimos la expresión regular.
		Matcher matcher = pattern.matcher(email); //Comparamos el email con la expresión regular.
		return matcher.matches(); //Retornamos si el email es válido o no.
	}
}
