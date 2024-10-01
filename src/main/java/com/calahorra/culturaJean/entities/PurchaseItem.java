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

///Entidad PurchaseItem:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="purchase_item") //Mapeamos la entidad con la tabla.
public class PurchaseItem
{
	//Atributos:
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int purchaseItemId;
	
	//Relación many to one entre PurchaseItem y Purchase:
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="purchase_id", nullable=false)
	private Purchase purchase;
	
	//Relación many to one entre PurchaseItem y Product:
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@Column(name="amount", nullable=false)
	private int amount;
	
	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime updatedAt;
	
	//Constructor:
	public PurchaseItem(Purchase purchase, Product product, int amount) 
	{
		this.purchase = purchase;
		this.product = product;
		this.amount = amount;
	}
}
