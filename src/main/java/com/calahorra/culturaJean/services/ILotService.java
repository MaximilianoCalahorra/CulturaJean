package com.calahorra.culturaJean.services;

import java.time.LocalDate;
import java.util.List;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.entities.Lot;

///Interfaz ILotService:
public interface ILotService 
{
	//Encontrar:
	
	//Encontramos el lote con determinado id:
	public Lot findByLotId(int lotId);
	
	//Encontramos el lote con determinado id y su stock asociado:
	public Lot findByLotIdWithStock(int lotId);
	
	//Encontramos el lote con determinado id y su pedido de aprovisionamiento asociado:
	public Lot findByLotIdWithSupplyOrder(int lotId);
	
	//Encontramos el lote con determinado id y su stock y su pedido de aprovisionamiento asociados:
	public Lot findByLotIdWithStockAndSupplyOrder(int lotId);
	
	//Encontramos el lote que se corresponde con determinado pedido de aprovisionamiento por el id del último:
	public LotDTO findBySupplyOrder(int supplyOrderId);
	
	//Encontramos los lotes con determinada fecha de recepción:
	public List<LotDTO> findByReceptionDate(LocalDate receptionDate);
	
	//Encontramos los lotes con una fecha de recepción anterior o igual a una determinada:
	public List<LotDTO> findByReceptionDateBeforeThanOrEqualTo(LocalDate date);
	
	//Encontramos los lotes con una fecha de recepción posterior o igual a una determinada:
	public List<LotDTO> findByReceptionDateAfterThanOrEqualTo(LocalDate date);
	
	//Encontramos los lotes con una fecha de recepción dentro de un intervalo de fechas:
	public List<LotDTO> findByReceptionDateRange(LocalDate fromDate, LocalDate untilDate);
	
	//Encontramos los lotes con determinado precio de compra:
	public List<LotDTO> findByPurchasePrice(float purchasePrice);
	
	//Encontramos los lotes con un precio de compra menor o igual a uno determinado:
	public List<LotDTO> findByPurchasePriceLessThanOrEqualTo(float purchasePrice);
	
	//Encontramos los lotes con un precio de compra mayor o igual a uno determinado:
	public List<LotDTO> findByPurchasePriceGreaterThanOrEqualTo(float purchasePrice);
	
	//Encontramos los lotes con un precio de compra dentro de un intervalo determinado:
	public List<LotDTO> findByPurchasePriceRange(float minimumPrice, float maximumPrice);
	
	//Encontramos los lotes con una cantidad existente determinada:
	public List<LotDTO> findByExistingAmount(int existingAmount);
	
	//Encontramos los lotes con una cantidad existente menor o igual a una determinada:
	public List<LotDTO> findByExistingAmountLessThanOrEqualTo(int existingAmount);
	
	//Encontramos los lotes con una cantidad existente mayor o igual a una determinada:
	public List<LotDTO> findByExistingAmountGreaterThanOrEqualTo(int existingAmount);
	
	//Encontramos los lotes con una cantidad existente entre un intervalo determinado:
	public List<LotDTO> findByExistingAmountRange(int minimumAmount, float maximumAmount);
	
	//Encontramos los lotes con una cantidad inicial determinada:
	public List<LotDTO> findByInitialAmount(int initialAmount);
	
	//Encontramos los lotes con una cantidad inicial menor o igual a una determinada:
	public List<LotDTO> findByInitialAmountLessThanOrEqualTo(int initialAmount);
	
	//Encontramos los lotes con una cantidad inicial mayor o igual a una determinada:
	public List<LotDTO> findByInitialAmountGreaterThanOrEqualTo(int initialAmount);
	
	//Encontramos los lotes con una cantidad inicial entre un intervalo determinado:
	public List<LotDTO> findByInitialAmountRange(int minimumAmount, float maximumAmount);
	
	//Encontramos los lotes de determinado stock:
	public List<LotDTO> findByStock(int stockId);
	
	//Obtener:
	
	//Obtenemos todos los lotes:
	public List<Lot> getAll();
	
	//Ordernar:
	
	//Ordenamos los lotes por fecha de recepción de manera ascendente:
	public List<LotDTO> getAllInOrderAscByReceptionDate();
	
	//Ordenamos los lotes por fecha de recepción de manera descendente:
	public List<LotDTO> getAllInOrderDescByReceptionDate();
	
	//Ordenamos los lotes por cantidad existente de manera ascendente:
	public List<LotDTO> getAllInOrderAscByExistingAmount();
	
	//Ordenamos los lotes por cantidad existente de manera descendente:
	public List<LotDTO> getAllInOrderDescByExistingAmount();
	
	//Ordenamos los lotes por cantidad inicial de manera ascendente:
	public List<LotDTO> getAllInOrderAscByInitialAmount();
	
	//Ordenamos los lotes por cantidad inicial de manera descendente:
	public List<LotDTO> getAllInOrderDescByInitialAmount();
	
	//Ordenamos los lotes por precio de compra de manera ascendente:
	public List<LotDTO> getAllInOrderAscByPurchasePrice();
	
	//Ordenamos los lotes por precio de compra de manera descendente:
	public List<LotDTO> getAllInOrderDescByPurchasePrice();
	
	//Ordenamos los lotes por el id del stock asociado a cada uno de manera ascendente:
	public List<LotDTO> getAllInOrderAscByStockId();
	
	//Ordenamos los lotes por el id del stock asociado a cada uno de manera descendente:
	public List<LotDTO> getAllInOrderDescByStockId();
	
	//Agregar o modificar:
	
	//Agregamos o modificamos un lote en la base de datos:
	public LotDTO insertOrUpdate(LotDTO lot);
}
