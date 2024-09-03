package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Lot;

///Interfaz ILotRepository:
@Repository("lotRepository")
public interface ILotRepository extends JpaRepository<Lot, Serializable>
{
	//Encontrar:
	
	//Encontramos el lote con determinado id:
	public abstract Lot findByLotId(int lotId);
	
	//Encontramos el lote con determinado id y su stock asociado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock WHERE l.lotId = (:lotId)")
	public abstract Lot findByLotIdWithStock(@Param("lotId")int lotId);
	
	//Encontramos el lote con determinado id y su pedido de aprovisionamiento asociado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.supplyOrder WHERE l.lotId = (:lotId)")
	public abstract Lot findByLotIdWithSupplyOrder(@Param("lotId")int lotId);
	
	//Encontramos el lote con determinado id y su stock y su pedido de aprovisionamiento asociados:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.lotId = (:lotId)")
	public abstract Lot findByLotIdWithStockAndSupplyOrder(@Param("lotId")int lotId);
	
	//Encontramos el lote que se corresponde con determinado pedido de aprovisionamiento por el id del último:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder so WHERE so.supplyOrderId = (:supplyOrderId)")
	public abstract Lot findBySupplyOrder(@Param("supplyOrderId")int supplyOrderId);
	
	//Encontramos los lotes con determinada fecha de recepción:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.receptionDate = (:receptionDate)")
	public abstract List<Lot> findByReceptionDate(@Param("receptionDate")LocalDate receptionDate);
	
	//Encontramos los lotes con una fecha de recepción anterior o igual a una determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.receptionDate <= (:date)")
	public abstract List<Lot> findByReceptionDateBeforeThanOrEqualTo(@Param("date")LocalDate date);
	
	//Encontramos los lotes con una fecha de recepción posterior o igual a una determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.receptionDate >= (:date)")
	public abstract List<Lot> findByReceptionDateAfterThanOrEqualTo(@Param("date")LocalDate date);
	
	//Encontramos los lotes con una fecha de recepción dentro de un intervalo de fechas:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.receptionDate BETWEEN (:fromDate) AND "
		 + "(:untilDate)")
	public abstract List<Lot> findByReceptionDateRange(@Param("fromDate")LocalDate fromDate, @Param("untilDate")LocalDate untilDate);
	
	//Encontramos los lotes con determinado precio de compra:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.purchasePrice = (:purchasePrice)")
	public abstract List<Lot> findByPurchasePrice(@Param("purchasePrice")float purchasePrice);
	
	//Encontramos los lotes con un precio de compra menor o igual a uno determinado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.purchasePrice <= (:purchasePrice)")
	public abstract List<Lot> findByPurchasePriceLessThanOrEqualTo(@Param("purchasePrice")float purchasePrice);
	
	//Encontramos los lotes con un precio de compra mayor o igual a uno determinado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.purchasePrice >= (:purchasePrice)")
	public abstract List<Lot> findByPurchasePriceGreaterThanOrEqualTo(@Param("purchasePrice")float purchasePrice);
	
	//Encontramos los lotes con un precio de compra dentro de un intervalo determinado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.purchasePrice >= (:minimumPrice) AND "
		 + "l.purchasePrice <= (:maximumPrice)")
	public abstract List<Lot> findByPurchasePriceRange(@Param("minimumPrice")float minimumPrice, @Param("maximumPrice")float maximumPrice);
	
	//Encontramos los lotes con una cantidad existente determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.existingAmount = (:existingAmount)")
	public abstract List<Lot> findByExistingAmount(@Param("existingAmount")int existingAmount);
	
	//Encontramos los lotes con una cantidad existente menor o igual a una determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.existingAmount <= (:existingAmount)")
	public abstract List<Lot> findByExistingAmountLessThanOrEqualTo(@Param("existingAmount")int existingAmount);
	
	//Encontramos los lotes con una cantidad existente mayor o igual a una determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.existingAmount >= (:existingAmount)")
	public abstract List<Lot> findByExistingAmountGreaterThanOrEqualTo(@Param("existingAmount")int existingAmount);
	
	//Encontramos los lotes con una cantidad existente entre un intervalo determinado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.existingAmount >= (:minimumAmount) AND "
		 + "l.existingAmount <= (:maximumAmount)")
	public abstract List<Lot> findByExistingAmountRange(@Param("minimumAmount")int minimumAmount, @Param("maximumAmount")float maximumAmount);
	
	//Encontramos los lotes con una cantidad inicial determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.initialAmount = (:initialAmount)")
	public abstract List<Lot> findByInitialAmount(@Param("initialAmount")int initialAmount);
	
	//Encontramos los lotes con una cantidad inicial menor o igual a una determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.initialAmount <= (:initialAmount)")
	public abstract List<Lot> findByInitialAmountLessThanOrEqualTo(@Param("initialAmount")int initialAmount);
	
	//Encontramos los lotes con una cantidad inicial mayor o igual a una determinada:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.initialAmount >= (:initialAmount)")
	public abstract List<Lot> findByInitialAmountGreaterThanOrEqualTo(@Param("initialAmount")int initialAmount);
	
	//Encontramos los lotes con una cantidad inicial entre un intervalo determinado:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder WHERE l.initialAmount >= (:minimumAmount) AND "
			+ "l.initialAmount <= (:maximumAmount)")
	public abstract List<Lot> findByInitialAmountRange(@Param("minimumAmount")int minimumAmount, @Param("maximumAmount")float maximumAmount);
	
	//Encontramos los lotes de determinado stock:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock s INNER JOIN FETCH l.supplyOrder WHERE s.stockId = (:stockId)")
	public abstract List<Lot> findByStock(@Param("stockId")int stockId);
	
	//Ordernar:
	
	//Ordenamos los lotes por fecha de recepción de manera ascendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.receptionDate")
	public abstract List<Lot> getAllInOrderAscByReceptionDate();
	
	//Ordenamos los lotes por fecha de recepción de manera descendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.receptionDate DESC")
	public abstract List<Lot> getAllInOrderDescByReceptionDate();
	
	//Ordenamos los lotes por cantidad existente de manera ascendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.existingAmount")
	public abstract List<Lot> getAllInOrderAscByExistingAmount();
	
	//Ordenamos los lotes por cantidad existente de manera descendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.existingAmount DESC")
	public abstract List<Lot> getAllInOrderDescByExistingAmount();
	
	//Ordenamos los lotes por cantidad inicial de manera ascendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.initialAmount")
	public abstract List<Lot> getAllInOrderAscByInitialAmount();
	
	//Ordenamos los lotes por cantidad inicial de manera descendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.initialAmount DESC")
	public abstract List<Lot> getAllInOrderDescByInitialAmount();
	
	//Ordenamos los lotes por precio de compra de manera ascendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.purchasePrice")
	public abstract List<Lot> getAllInOrderAscByPurchasePrice();
	
	//Ordenamos los lotes por precio de compra de manera descendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock INNER JOIN FETCH l.supplyOrder ORDER BY l.purchasePrice DESC")
	public abstract List<Lot> getAllInOrderDescByPurchasePrice();
	
	//Ordenamos los lotes por el id del stock asociado a cada uno de manera ascendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock s INNER JOIN FETCH l.supplyOrder ORDER BY s.stockId")
	public abstract List<Lot> getAllInOrderAscByStockId();
	
	//Ordenamos los lotes por el id del stock asociado a cada uno de manera descendente:
	@Query("SELECT l FROM Lot l INNER JOIN FETCH l.stock s INNER JOIN FETCH l.supplyOrder ORDER BY s.stockId DESC")
	public abstract List<Lot> getAllInOrderDescByStockId();
}
