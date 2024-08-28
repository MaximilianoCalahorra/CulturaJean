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

///Entidad SupplyOrder:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="supply_order") //Mapeamos la entidad con la tabla.
public class SupplyOrder 
{
	//Atributos:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int supplyOrderId;
	
	//Relación many to one entre SupplyOrder y Product:
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	//Relación many to one entre SupplyOrder y Member:
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	private Member member;
	
	//Relación many to one entre SupplyOrder y Supplier:
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="supplier_id", nullable=false)
	private Supplier supplier;
	
	@Column(name="amount", nullable=false)
	private int amount;
	
	@Column(name="delivered", nullable=false)
	private boolean delivered;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	//Constructor:
	public SupplyOrder(Product product, Member member, Supplier supplier, int amount, boolean delivered) 
	{
		this.product = product;
		this.member = member;
		this.supplier = supplier;
		this.amount = amount;
		this.delivered = delivered;
	}
}
