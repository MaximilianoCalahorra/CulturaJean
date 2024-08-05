package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.User;

///Interfaz IUserRepository:
@Repository("userRepository")
public interface IUserRepository extends JpaRepository<User, Serializable> 
{
	//Encontrar:
	
	//Usuario con sus roles por determinado nombre de usuario:
	@Query("SELECT u FROM User u JOIN FETCH u.userRoles WHERE u.username = (:username)")
	public abstract User findByUsernameAndFetchUserRolesEagerly(@Param("username") String username);
	
	//Encontramos el usuario con determinado id y sus roles asociados:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles WHERE u.userId = (:userId)")
	public abstract User findByUserIdWithUserRoles(@Param("userId")int userId);
	
	//Encontramos los usuarios con determinado rol:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role)")
	public abstract List<User> findByUserRole(@Param("role")String role);
	
	//Ordenar:
	
	//Ordenamos los usuarios de determinado rol por nombre de forma ascendente:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role) ORDER BY u.name")
	public abstract List<User> getByUserRoleInOrderAscByName(@Param("role")String role);
	
	//Ordenamos los usuarios de determinado rol por nombre de forma descendente:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role) ORDER BY u.name DESC")
	public abstract List<User> getByUserRoleInOrderDescByName(@Param("role")String role);
	
	//Ordenamos los usuarios de determinado rol por apellido de forma ascendente:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role) ORDER BY u.lastName")
	public abstract List<User> getByUserRoleInOrderAscByLastName(@Param("role")String role);
	
	//Ordenamos los usuarios de determinado rol por apellido de forma descendente:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role) ORDER BY u.lastName")
	public abstract List<User> getByUserRoleInOrderDescByLastName(@Param("role")String role);
	
	//Ordenamos los usuarios de determinado rol por nombre de usuario de forma ascendente:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role) ORDER BY u.username")
	public abstract List<User> getByUserRoleInOrderAscByUsername(@Param("role")String role);
	
	//Ordenamos los usuarios de determinado rol por nombre de usuario de forma descendente:
	@Query("SELECT u FROM User u INNER JOIN FETCH u.userRoles ur WHERE ur.role = (:role) ORDER BY u.username")
	public abstract List<User> getByUserRoleInOrderDescByUsername(@Param("role")String role);
}
