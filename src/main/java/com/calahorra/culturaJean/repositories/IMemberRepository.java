package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Member;

///Interfaz IMemberRepository:
@Repository("memberRepository")
public interface IMemberRepository extends JpaRepository<Member, Serializable> 
{
	//Encontrar:
	
	//Miembro con sus roles por determinado nombre de usuario:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles WHERE m.username = (:username)")
	public abstract Member findByUsernameAndFetchUserRolesEagerly(@Param("username") String username);
	
	//Encontramos el miembro con determinado id y sus roles asociados:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles WHERE m.memberId = (:memberId)")
	public abstract Member findByMemberIdWithUserRoles(@Param("memberId")int memberId);
	
	//Encontramos los miembros con determinado rol:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role)")
	public abstract List<Member> findByUserRole(@Param("role")String role);
	
	//Encontramos los miembros habilitados/deshabilitados con determinado rol:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE m.enabled = (:enabled) AND ur.role = (:role)")
	public abstract List<Member> findByEnabledAndUserRole(@Param("enabled")boolean enabled, @Param("role")String role);
	
	//Ordenar:
	
	//Ordenamos los miembros de determinado rol por nombre de forma ascendente:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role) ORDER BY m.name")
	public abstract List<Member> getByUserRoleInOrderAscByName(@Param("role")String role);
	
	//Ordenamos los miembros de determinado rol por nombre de forma descendente:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role) ORDER BY m.name DESC")
	public abstract List<Member> getByUserRoleInOrderDescByName(@Param("role")String role);
	
	//Ordenamos los miembros de determinado rol por apellido de forma ascendente:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role) ORDER BY m.lastName")
	public abstract List<Member> getByUserRoleInOrderAscByLastName(@Param("role")String role);
	
	//Ordenamos los miembros de determinado rol por apellido de forma descendente:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role) ORDER BY m.lastName DESC")
	public abstract List<Member> getByUserRoleInOrderDescByLastName(@Param("role")String role);
	
	//Ordenamos los miembros de determinado rol por nombre de usuario de forma ascendente:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role) ORDER BY m.username")
	public abstract List<Member> getByUserRoleInOrderAscByUsername(@Param("role")String role);
	
	//Ordenamos los miembros de determinado rol por nombre de usuario de forma descendente:
	@Query("SELECT m FROM Member m INNER JOIN FETCH m.userRoles ur WHERE ur.role = (:role) ORDER BY m.username DESC")
	public abstract List<Member> getByUserRoleInOrderDescByUsername(@Param("role")String role);
}
