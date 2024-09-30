package com.calahorra.culturaJean.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Entidad Supplier:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="supplier") //Mapeamos la entidad con la tabla.
public class Supplier
{
	//Atributos:
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int supplierId;
	
	@Column(name="name", nullable=false, length=50)
	private String name;
	
	@Column(name="address", nullable=false, length=100)
	private String address;
	
	@Column(name="phone_number", nullable=false, length=20)
	private String phoneNumber;
	
	@Column(name="email", nullable=false, length=60)
	private String email;
	
	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime updatedAt;
	
	//Constructor:
	public Supplier(String name, String address, String phoneNumber, String email)
	{
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
}
