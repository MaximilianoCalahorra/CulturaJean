package com.calahorra.culturaJean.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.entities.Lot;
import com.calahorra.culturaJean.repositories.ILotRepository;
import com.calahorra.culturaJean.services.ILotService;

///Clase LotService:
@Service("lotService")
public class LotService implements ILotService
{
	//Atributos:
	private ILotRepository lotRepository;
	private ModelMapper modelMapper;
	
	//Constructor:
	public LotService(ILotRepository lotRepository) 
	{
		this.lotRepository = lotRepository;
	}
	
	//Encontrar:
	
	//Encontramos el lote con determinado id:
	@Override
	public Lot findByLotId(int lotId) 
	{
		return lotRepository.findByLotId(lotId);
	}
	
	//Encontramos el lote con determinado id y su stock asociado:
	@Override
	public Lot findByLotIdWithStock(int lotId) 
	{
		return lotRepository.findByLotIdWithStock(lotId);
	}
	
	//Encontramos el lote con determinado id y su pedido de aprovisionamiento asociado:
	@Override
	public Lot findByLotIdWithSupplyOrder(int lotId) 
	{
		return lotRepository.findByLotIdWithSupplyOrder(lotId);
	}
	
	//Encontramos el lote con determinado id y su stock y su pedido de aprovisionamiento asociados:
	@Override
	public Lot findByLotIdWithStockAndSupplyOrder(int lotId) 
	{
		return lotRepository.findByLotIdWithStockAndSupplyOrder(lotId);
	}
	
	//Encontramos los lotes con determinada fecha de recepción:
	@Override
	public List<LotDTO> findByReceptionDate(LocalDate receptionDate)
	{
		return lotRepository.findByReceptionDate(receptionDate) //Obtenemos los lotes de esa fecha como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una fecha de recepción anterior o igual a una determinada:
	@Override
	public List<LotDTO> findByReceptionDateBeforeThanOrEqualTo(LocalDate date)
	{
		return lotRepository.findByReceptionDateBeforeThanOrEqualTo(date) //Obtenemos los lotes de ese tipo de fecha como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una fecha de recepción posterior o igual a una determinada:
	@Override
	public List<LotDTO> findByReceptionDateAfterThanOrEqualTo(LocalDate date)
	{
		return lotRepository.findByReceptionDateAfterThanOrEqualTo(date) //Obtenemos los lotes de ese tipo de fecha como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una fecha de recepción dentro de un intervalo de fechas:
	@Override
	public List<LotDTO> findByReceptionDateRange(LocalDate fromDate, LocalDate untilDate)
	{
		return lotRepository.findByReceptionDateRange(fromDate, untilDate) //Obtenemos los lotes de ese tipo de fecha como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con determinado precio de compra:
	@Override
	public List<LotDTO> findByPurchasePrice(float purchasePrice)
	{
		return lotRepository.findByPurchasePrice(purchasePrice) //Obtenemos los lotes de ese precio como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con un precio de compra menor o igual a uno determinado:
	@Override
	public List<LotDTO> findByPurchasePriceLessThanOrEqualTo(float purchasePrice)
	{
		return lotRepository.findByPurchasePriceLessThanOrEqualTo(purchasePrice) //Obtenemos los lotes de ese tipo de precio como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con un precio de compra mayor o igual a uno determinado:
	@Override
	public List<LotDTO> findByPurchasePriceGreaterThanOrEqualTo(float purchasePrice)
	{
		return lotRepository.findByPurchasePriceGreaterThanOrEqualTo(purchasePrice) //Obtenemos los lotes de ese tipo de precio como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con un precio de compra dentro de un intervalo determinado:
	@Override
	public List<LotDTO> findByPurchasePriceRange(float minimumPrice, float maximumPrice)
	{
		return lotRepository.findByPurchasePriceRange(minimumPrice, maximumPrice) //Obtenemos los lotes de ese tipo de precio como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad existente determinada:
	@Override
	public List<LotDTO> findByExistingAmount(int existingAmount)
	{
		return lotRepository.findByExistingAmount(existingAmount) //Obtenemos los lotes de esa cantidad existente como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad existente menor o igual a una determinada:
	@Override
	public List<LotDTO> findByExistingAmountLessThanOrEqualTo(int existingAmount)
	{
		return lotRepository.findByExistingAmountLessThanOrEqualTo(existingAmount) //Obtenemos los lotes de ese tipo de cantidad existente como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad existente mayor o igual a una determinada:
	@Override
	public List<LotDTO> findByExistingAmountGreaterThanOrEqualTo(int existingAmount)
	{
		return lotRepository.findByExistingAmountGreaterThanOrEqualTo(existingAmount) //Obtenemos los lotes de ese tipo de cantidad existente como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad existente entre un intervalo determinado:
	@Override
	public List<LotDTO> findByExistingAmountRange(int minimumAmount, float maximumAmount)
	{
		return lotRepository.findByExistingAmountRange(minimumAmount, maximumAmount) //Obtenemos los lotes de ese tipo de cantidad existente como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad inicial determinada:
	@Override
	public List<LotDTO> findByInitialAmount(int initialAmount)
	{
		return lotRepository.findByInitialAmount(initialAmount) //Obtenemos los lotes de esa cantidad inicial como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad inicial menor o igual a una determinada:
	@Override
	public List<LotDTO> findByInitialAmountLessThanOrEqualTo(int initialAmount)
	{
		return lotRepository.findByInitialAmountLessThanOrEqualTo(initialAmount) //Obtenemos los lotes de ese tipo de cantidad inicial como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad inicial mayor o igual a una determinada:
	@Override
	public List<LotDTO> findByInitialAmountGreaterThanOrEqualTo(int initialAmount)
	{
		return lotRepository.findByInitialAmountGreaterThanOrEqualTo(initialAmount) //Obtenemos los lotes de ese tipo de cantidad inicial como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes con una cantidad inicial entre un intervalo determinado:
	@Override
	public List<LotDTO> findByInitialAmountRange(int minimumAmount, float maximumAmount)
	{
		return lotRepository.findByInitialAmountRange(minimumAmount, maximumAmount) //Obtenemos los lotes de ese tipo de cantidad inicial como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los lotes de determinado stock:
	@Override
	public List<LotDTO> findByStock(int stockId)
	{
		return lotRepository.findByStock(stockId) //Obtenemos los lotes de ese stock como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtener:
	
	//Obtenemos todos los lotes:
	@Override
	public List<Lot> getAll()
	{
		return lotRepository.findAll();
	}
	
	//Ordenar:
	
	//Ordenamos los lotes por fecha de recepción de manera ascendente:
	@Override
	public List<LotDTO> getAllInOrderAscByReceptionDate()
	{
		return lotRepository.getAllInOrderAscByReceptionDate() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por fecha de recepción de manera descendente:
	@Override
	public List<LotDTO> getAllInOrderDescByReceptionDate()
	{
		return lotRepository.getAllInOrderDescByReceptionDate() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por cantidad existente de manera ascendente:
	@Override
	public List<LotDTO> getAllInOrderAscByExistingAmount()
	{
		return lotRepository.getAllInOrderAscByExistingAmount() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por cantidad existente de manera descendente:
	@Override
	public List<LotDTO> getAllInOrderDescByExistingAmount()
	{
		return lotRepository.getAllInOrderDescByExistingAmount() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por cantidad inicial de manera ascendente:
	@Override
	public List<LotDTO> getAllInOrderAscByInitialAmount()
	{
		return lotRepository.getAllInOrderAscByInitialAmount() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por cantidad inicial de manera descendente:
	@Override
	public List<LotDTO> getAllInOrderDescByInitialAmount()
	{
		return lotRepository.getAllInOrderDescByInitialAmount() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por precio de compra de manera ascendente:
	@Override
	public List<LotDTO> getAllInOrderAscByPurchasePrice()
	{
		return lotRepository.getAllInOrderAscByPurchasePrice() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por precio de compra de manera descendente:
	@Override
	public List<LotDTO> getAllInOrderDescByPurchasePrice()
	{
		return lotRepository.getAllInOrderDescByPurchasePrice() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por el id del stock asociado a cada uno de manera ascendente:
	@Override
	public List<LotDTO> getAllInOrderAscByStockId()
	{
		return lotRepository.getAllInOrderAscByStockId() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los lotes por el id del stock asociado a cada uno de manera descendente:
	@Override
	public List<LotDTO> getAllInOrderDescByStockId()
	{
		return lotRepository.getAllInOrderDescByStockId() //Obtenemos los lotes ordenados como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Agregar o modificar:
	
	//Agregamos o modificamos un producto en la base de datos:
	public LotDTO insertOrUpdate(LotDTO lot) 
	{
		Lot saveLot = modelMapper.map(lot, Lot.class);
		return modelMapper.map(lotRepository.save(saveLot), LotDTO.class); //Insertamos el lote en la base de datos y lo
																		   //retornamos como DTO.
	}
}
