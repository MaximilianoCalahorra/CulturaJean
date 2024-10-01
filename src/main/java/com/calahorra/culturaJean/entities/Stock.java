package com.calahorra.culturaJean.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Entidad Stock:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="stock") //Mapeamos la entidad con la tabla.
public class Stock 
{
	//Atributos:
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int stockId;
	
	//Relación one to one entre Stock y Product:
	@OneToOne()
	@JoinColumn(name = "product_id", referencedColumnName = "productId", nullable=false)
	private Product product;
	
	@Column(name="desirable_amount", nullable=false)
	private int desirableAmount;
	
	@Column(name="minimum_amount", nullable=false)
	private int minimumAmount;
	
	@Column(name="actual_amount", nullable=false)
	private int actualAmount;
	
	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime updatedAt;
	
	//Constructor:
	public Stock(Product product, int desirableAmount, int minimumAmount) 
	{
		this.product = product;
		this.desirableAmount = desirableAmount;
		this.minimumAmount = minimumAmount;
		this.actualAmount = 0; //Al instanciar un stock su cantidad actual es 0.
	}
}
