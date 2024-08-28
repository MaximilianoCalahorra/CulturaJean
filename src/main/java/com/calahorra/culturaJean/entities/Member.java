package com.calahorra.culturaJean.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Entidad Member:
@Entity
@Getter @Setter @NoArgsConstructor
public class Member 
{
	//Atributos:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberId;

	@Column(name="username", unique=true, nullable=false, length=45)
	private String username;

	@Column(name="password", nullable=false, length=60)
	private String password;

	@Column(name="enabled", nullable=false)
	private boolean enabled;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="member")
	private Set<UserRole> userRoles = new HashSet<>();
	
	@Column(name="name", nullable=false, length=30)
	private String name;
	
	@Column(name="last_name", nullable=false, length=30)
	private String lastName;
	
	@Column(name="email", nullable=false, length=60)
	private String email;

	//Constructores:
	//Sin listado de roles asignado:
	public Member(String username, String password, boolean enabled, String name, String lastName, String email) 
	{
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

	//Con listado de roles asignado:
	public Member(String username, String password, boolean enabled, String name, String lastName, String email, Set<UserRole> userRoles)
	{
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.userRoles = userRoles;
	}
}
