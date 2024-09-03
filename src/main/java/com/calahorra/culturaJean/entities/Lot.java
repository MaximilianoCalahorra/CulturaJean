package com.calahorra.culturaJean.entities;

import java.time.LocalDate;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

///Entidad Lot:
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="lot") //Mapeamos la entidad con la tabla.
public class Lot
{
	//Atributos:
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int lotId;
	
	//Relación many to one entre Lot y Stock:
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stock_id", nullable=false)
	private Stock stock;
	
	//Relación one to one entre Lot y SupplyOrder:
	@OneToOne()
    @JoinColumn(name="supply_order_id", referencedColumnName="supplyOrderId", nullable=false)
	private SupplyOrder supplyOrder;
	
	@Column(name="reception_date", nullable=true)
	private LocalDate receptionDate;
	
	@Column(name="initial_amount", nullable=false)
	private int initialAmount;
	
	@Column(name="existing_amount", nullable=false)
	private int existingAmount;
	
	@Column(name="purchase_price", nullable=false)
	private float purchasePrice;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	//Constructor:
	public Lot(Stock stock, SupplyOrder supplyOrder, LocalDate receptionDate, int initialAmount, int existingAmount, float purchasePrice) 
	{
		this.stock = stock;
		this.supplyOrder = supplyOrder;
		this.receptionDate = receptionDate;
		this.initialAmount = initialAmount;
		this.existingAmount = existingAmount;
		this.purchasePrice = purchasePrice;
	}
}