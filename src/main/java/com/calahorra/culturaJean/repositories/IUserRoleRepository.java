package com.calahorra.culturaJean.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.UserRole;

///Interfaz IUserRoleRepository:
@Repository("userRoleRepository")
public interface IUserRoleRepository extends JpaRepository<UserRole, Serializable>
{
	
}
