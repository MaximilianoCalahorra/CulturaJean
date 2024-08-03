package com.calahorra.culturaJean.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Entidad Purchase:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="purchase") //Mapeamos la entidad con la tabla.
public class Purchase
{
	//Atributos:
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int purchaseId;
	
	//Relación many to one entre Purchase y User:
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column(name="method_of_pay", nullable=false, length=20)
	private String methodOfPay;
	
	@CreationTimestamp
	private LocalDateTime dateTime;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	//Constructor:
	public Purchase(User user, String methodOfPay) 
	{
		this.user = user;
		this.methodOfPay = methodOfPay;
	}
}