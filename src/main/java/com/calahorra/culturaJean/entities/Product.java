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

///Entidad Product:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="product") //Mapeamos la entidad con la tabla.
public class Product
{
	//Atributos:
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int productId;
	
	@Column(name="code", nullable=false, length=10)
	private String code;
	
	@Column(name="category", nullable=false, length=30)
	private String category;
	
	@Column(name="gender", nullable=false, length=1)
	private Character gender;
	
	@Column(name="size", nullable=false, length=5)
	private String size;
	
	@Column(name="color", nullable=false, length=30)
	private String color;
	
	@Column(name="cost", nullable=false)
	private float cost;
	
	@Column(name="sale_price", nullable=false)
	private float salePrice;
	
	@Column(name="name", nullable=false, length=50)
	private String name;
	
	@Column(name="description", nullable=false, length=100)
	private String description;
	
	@Column(name="enabled", nullable=false)
	private boolean enabled;
	
	@Column(name="image_name", nullable=false, length=20)
	private String imageName;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	//Constructor:
	public Product(String code, String category, Character gender, String size, String color, float cost, float salePrice, String name,
			       String description, boolean enabled) 
	{
		this.code = code;
		this.category = category;
		this.gender = gender;
		this.size = size;
		this.color = color;
		this.cost = cost;
		this.salePrice = salePrice;
		this.name = name;
		this.description = description;
		this.enabled = enabled;
	}
}
