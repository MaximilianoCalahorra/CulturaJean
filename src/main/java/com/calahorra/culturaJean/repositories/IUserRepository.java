package com.calahorra.culturaJean.repositories;

import java.io.Serializable;

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
}
