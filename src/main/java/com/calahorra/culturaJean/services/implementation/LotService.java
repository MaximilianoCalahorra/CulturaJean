package com.calahorra.culturaJean.services.implementation;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.entities.Lot;
import com.calahorra.culturaJean.repositories.ILotRepository;
import com.calahorra.culturaJean.services.ILotService;

///Clase LotService:
@Service("lotService")
public class LotService implements ILotService
{
	//Atributos:
	private ILotRepository lotRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
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
	
	//Encontramos el lote que se corresponde con determinado pedido de aprovisionamiento por el id del último:
	@Override
	public LotDTO findBySupplyOrder(int supplyOrderId)
	{
		Lot lot = lotRepository.findBySupplyOrder(supplyOrderId);
		if(lot == null) 
		{
			return null;
		}
		return modelMapper.map(lot, LotDTO.class);
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
	
	//Encontramos un ejemplar de cada id de stock de los cuales hay lotes:
	@Override
	public List<String> findUniqueEachStockId()
	{
		return lotRepository.findUniqueEachStockId(); //Retornamos el listado de ids de stocks ordenado.
	}
	
	//Obtener:
	
	//Obtenemos todos los lotes:
	@Override
	public List<LotDTO> getAll()
	{
		return lotRepository.findAll() //Obtenemos todos los lotes como entidades.
				.stream()
				.map(lot -> modelMapper.map(lot, LotDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
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
	
	//Ordenamos el listado de lotes por id del stock asociado de forma ascendente:
	@Override
	public List<LotDTO> inOrderAscByStockId(List<LotDTO> lots)
	{
		lots.sort(Comparator.comparingInt(lot -> lot.getStock().getStockId()));
		return lots; //Retornamos los lotes ordenados.
	}
		
	//Ordenamos el listado de lotes por id del stock asociado de forma descendente:
	@Override
	public List<LotDTO> inOrderDescByStockId(List<LotDTO> lots)
	{
		lots.sort(Comparator.comparingInt(lot -> ((LotDTO) lot).getStock().getStockId()).reversed());
		return lots; //Retornamos los lotes ordenados.
	}
		
	//Ordenamos el listado de lotes por fecha de recepción de forma ascendente:
	@Override
	public List<LotDTO> inOrderAscByReceptionDate(List<LotDTO> lots)
	{
		Collections.sort(lots, (l1, l2) -> l1.getReceptionDate().compareTo(l2.getReceptionDate()));
		return lots; //Retornamos los lotes ordenados.
	}
		
	//Ordenamos el listado de lotes por fecha de recepción de forma ascendente:
	@Override
	public List<LotDTO> inOrderDescByReceptionDate(List<LotDTO> lots)
	{
		Collections.sort(lots, (l1, l2) -> l2.getReceptionDate().compareTo(l1.getReceptionDate()));
		return lots; //Retornamos los lotes ordenados.
	}
	
	//Ordenamos el listado de lotes por cantidad existente de forma ascendente:
	@Override
	public List<LotDTO> inOrderAscByExistingAmount(List<LotDTO> lots)
	{
		lots.sort(Comparator.comparingInt(LotDTO::getExistingAmount));
		return lots; //Retornamos los lotes ordenados.
	}
		
	//Ordenamos el listado de lotes por cantidad existente de forma ascendente:
	@Override
	public List<LotDTO> inOrderDescByExistingAmount(List<LotDTO> lots)
	{
		lots.sort(Comparator.comparingInt(LotDTO::getExistingAmount).reversed());
		return lots; //Retornamos los lotes ordenados.
	}
		
	//Aplicamos el criterio de ordenamiento seleccionado:
	@Override
	public List<LotDTO> applyOrder(List<LotDTO> lots, String order)
	{
		//Según el criterio de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByStockId": lots = inOrderAscByStockId(lots); break; //Ascendente por id de stock.
			case "orderDescByStockId": lots = inOrderDescByStockId(lots); break; //Descendente por id de stock.
			case "orderAscByReceptionDate": lots = inOrderAscByReceptionDate(lots); break; //Ascendente por fecha de recepción.
			case "orderDescByReceptionDate": lots = inOrderDescByReceptionDate(lots); break; //Descendente por fecha de recepción.
			case "orderAscByExistingAmount": lots = inOrderAscByExistingAmount(lots); break; //Ascendente por cantidad existente.
			case "orderDescByExistingAmount": lots = inOrderDescByExistingAmount(lots); break; //Descendente por cantidad existente.
		}
		return lots; //Retornamos los lotes ordenados.
	}
	
	//Agregar o modificar:
	
	//Agregamos o modificamos un producto en la base de datos:
	@Override
	public LotDTO insertOrUpdate(LotDTO lot) 
	{
		Lot saveLot = modelMapper.map(lot, Lot.class);
		return modelMapper.map(lotRepository.save(saveLot), LotDTO.class); //Insertamos el lote en la base de datos y lo
																		   //retornamos como DTO.
	}
	
	//Filtrar:
	
	//Filtramos los pedidos de aprovisionamiento entregados para quedarnos solo con los que todavía no tienen un lote asociado:
	@Override
	public List<SupplyOrderDTO> filterBySupplyOrderWithInexistingLot(List<SupplyOrderDTO> supplyOrders)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (findBySupplyOrder(supplyOrder.getSupplyOrderId()) != null) 
			{
				iterator.remove(); //En caso de que haya un lote asociado al pedido de aprovisionamiento, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados, de forma que quedaron solo los que no generaron lotes todavía.
	}
	
	//Filtramos los códigos de productos de pedidos de aprovisionamiento para quedarnos solo con los que figuran en pedidos sin lote generado:
	@Override
	public List<String> filterByProductCodeOnSupplyOrderWithInexistingLot(List<SupplyOrderDTO> supplyOrders, List<String> productCodes)
	{
		Iterator<String> iterator = productCodes.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un código de producto por analizar:
		while(iterator.hasNext())
		{
			String productCode = iterator.next(); //Obtenemos ese código de producto.
			boolean match = false; //Suponemos que no está en algún pedido de aprovisionamiento que puede generar un lote.
			int i = 0;
			
			//Vamos a comparar contra todos los pedidos de aprovisionamiento mientras no se haya levantado la bandera de que está en al menos
			//uno y queden pedidos de aprovisionamiento por analizar:
			while(!match && i < supplyOrders.size()) 
			{
				if (supplyOrders.get(i).getProduct().getCode().equals(productCode)) //Hay coincidencia de código:
				{
					match = true; //Levantamos la bandera.
		        }
				i++;				
			}
			
			//En caso de que el código no haya estado en ningún pedido de aprovisionamiento:
			if(!match) 
			{
				iterator.remove(); //Lo removemos.
			}
	    }
		return productCodes; //Retornamos el listado de códigos de producto filtrado.
	}
		
	//Filtramos los nombre de proveedores de pedidos de aprovisionamiento para quedarnos solo con los que figuran en pedidos sin lote generado:
	@Override
	public List<String> filterBySupplierNameOnSupplyOrderWithInexistingLot(List<SupplyOrderDTO> supplyOrders, List<String> supplierNames)
	{
		Iterator<String> iterator = supplierNames.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un nombre de proveedor por analizar:
		while(iterator.hasNext())
		{
			String supplierName = iterator.next(); //Obtenemos ese nombre de proveedor.
			boolean match = false; //Suponemos que no está en algún pedido de aprovisionamiento que puede generar un lote.
			int i = 0;
			
			//Vamos a comparar contra todos los pedidos de aprovisionamiento mientras no se haya levantado la bandera de que está en al menos
			//uno y queden pedidos de aprovisionamiento por analizar:
			while(!match && i < supplyOrders.size()) 
			{
				if (supplyOrders.get(i).getSupplier().getName().equals(supplierName)) //Hay coincidencia de nombre:
				{
					match = true; //Levantamos la bandera.
		        }
				i++;				
			}
			
			//En caso de que el nombre no haya estado en ningún pedido de aprovisionamiento:
			if(!match) 
			{
				iterator.remove(); //Lo removemos.
			}
	    }
		return supplierNames; //Retornamos el listado de nombres de proveedores filtrado.
	}
	
	//Filtramos los lotes por id del stock asociado:
	@Override
	public List<LotDTO> filterByStockId(List<LotDTO> lots, int stockId)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getStock().getStockId() != stockId) 
			{
				iterator.remove(); //En caso de que no tenga un id de stock igual al del filtro, lo removemos.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por determinada fecha de recepción:
	@Override
	public List<LotDTO> filterByReceptionDate(List<LotDTO> lots, LocalDate receptionDate)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (!lot.getReceptionDate().equals(receptionDate)) 
			{
				iterator.remove(); //En caso de que no tenga la misma fecha de recepción del filtro, la removemos del listado.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por posteriores o iguales a una fecha de recepción:
	@Override
	public List<LotDTO> filterByFromReceptionDate(List<LotDTO> lots, LocalDate fromReceptionDate)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getReceptionDate().isBefore(fromReceptionDate)) 
			{
				iterator.remove(); //En caso de que tenga una fecha de recepción anterior a la del filtro, la removemos del listado.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
	
	//Filtramos los lotes por anteriores o iguales a una fecha de recepción:
	@Override
	public List<LotDTO> filterByUntilReceptionDate(List<LotDTO> lots, LocalDate untilReceptionDate)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getReceptionDate().isAfter(untilReceptionDate)) 
			{
				iterator.remove(); //En caso de que tenga una fecha de recepción posterior a la del filtro, la removemos del listado.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por dentro de un rango de fechas de recepción:
	@Override
	public List<LotDTO> filterByReceptionDateRange(List<LotDTO> lots, LocalDate rangeFromReceptionDate, LocalDate rangeUntilReceptionDate)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getReceptionDate().isBefore(rangeFromReceptionDate) || lot.getReceptionDate().isAfter(rangeUntilReceptionDate)) 
			{
				iterator.remove(); //En caso de que tenga una fecha de recepción anterior a la desde o posterior a la hasta del filtro, la removemos del listado.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por id del stock asociado:
	@Override
	public List<LotDTO> applyFilterTypeReceptionDate(List<LotDTO> lots, String receptionDate, String fromReceptionDate, String untilReceptionDate,
												  		 String rangeFromReceptionDate, String rangeUntilReceptionDate)
	{
		if(!receptionDate.equals("") && fromReceptionDate.equals("") && untilReceptionDate.equals("") && rangeFromReceptionDate.equals("") 
		   && rangeUntilReceptionDate.equals("")) //Filtro por fecha de recepción determinada:
		{
			LocalDate receptionDateObject = LocalDate.parse(receptionDate); //Convertimos la cadena en un objeto LocalDate.
			lots = filterByReceptionDate(lots, receptionDateObject); //Nos quedamos los lotes que cumplen el filtro.
		}
		else if(!fromReceptionDate.equals("") && receptionDate.equals("") && untilReceptionDate.equals("") && rangeFromReceptionDate.equals("") 
				&& rangeUntilReceptionDate.equals("")) //Filtro por fecha de recepción posterior o igual a una determinada:
		{
			LocalDate fromReceptionDateObject = LocalDate.parse(fromReceptionDate); //Convertimos la cadena en un objeto LocalDate.
			lots = filterByFromReceptionDate(lots, fromReceptionDateObject); //Nos quedamos los lotes que cumplen el filtro.
		}
		else if(!untilReceptionDate.equals("") && receptionDate.equals("") && fromReceptionDate.equals("") && rangeFromReceptionDate.equals("") 
				&& rangeUntilReceptionDate.equals("")) //Filtro por fecha de recepción anterior o igual a una determinada:
		{
			LocalDate untilReceptionDateObject = LocalDate.parse(untilReceptionDate); //Convertimos la cadena en un objeto LocalDate.
			lots = filterByUntilReceptionDate(lots, untilReceptionDateObject); //Nos quedamos los lotes que cumplen el filtro.
		}
		else if(!rangeFromReceptionDate.equals("") && !rangeUntilReceptionDate.equals("") && receptionDate.equals("") && fromReceptionDate.equals("") 
				&& untilReceptionDate.equals("")) //Filtro por fecha de recepción en un rango determinado:
		{
			LocalDate rangeFromReceptionDateObject = LocalDate.parse(rangeFromReceptionDate); //Convertimos la cadena en un objeto LocalDate.
			LocalDate rangeUntilReceptionDateObject = LocalDate.parse(rangeUntilReceptionDate); //Convertimos la cadena en un objeto LocalDate.
			lots = filterByReceptionDateRange(lots, rangeFromReceptionDateObject, rangeUntilReceptionDateObject); //Nos quedamos los lotes que cumplen el filtro.
		}
		return lots; //Retornamos los lotes filtrados por el criterio seleccionado de fecha de recepción.
	}
		
	//Filtramos los lotes por determinada cantidad existente:
	@Override
	public List<LotDTO> filterByExistingAmount(List<LotDTO> lots, int existingAmount)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getExistingAmount() != existingAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad existente igual a la del filtro, lo removemos.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por mayores o iguales a cantidad existente:
	@Override
	public List<LotDTO> filterByFromExistingAmount(List<LotDTO> lots, int fromExistingAmount)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getExistingAmount() < fromExistingAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad existente mayor o igual a la del filtro, lo removemos.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por menores o iguales a una cantidad existente:
	@Override
	public List<LotDTO> filterByUntilExistingAmount(List<LotDTO> lots, int untilExistingAmount)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getExistingAmount() > untilExistingAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad existente menor o igual a la del filtro, lo removemos.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por dentro de un rango de cantidades existentes:
	@Override
	public List<LotDTO> filterByExistingAmountRange(List<LotDTO> lots, int rangeFromExistingAmount, int rangeUntilExistingAmount)
	{
		Iterator<LotDTO> iterator = lots.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un lote por analizar:
		while(iterator.hasNext())
		{
			LotDTO lot = iterator.next(); //Obtenemos ese lote.
			if (lot.getExistingAmount() < rangeFromExistingAmount || lot.getExistingAmount() > rangeUntilExistingAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad existente entre las del rango del filtro, lo removemos.
	        }
	    }
		return lots; //Retornamos el listado de lotes filtrado.
	}
		
	//Filtramos los lotes por id del stock asociado:
	@Override
	public List<LotDTO> applyFilterTypeExistingAmount(List<LotDTO> lots, String existingAmount, String fromExistingAmount, String untilExistingAmount,
														  String rangeFromExistingAmount, String rangeUntilExistingAmount)
	{
		if(!existingAmount.equals("") && fromExistingAmount.equals("") && untilExistingAmount.equals("") && rangeFromExistingAmount.equals("")
		   && rangeUntilExistingAmount.equals("")) //Filtro por cantidad existente:
		{
			int existingAmountNumber = Integer.parseInt(existingAmount); //Convertimos la cadena a número.
			lots = filterByExistingAmount(lots, existingAmountNumber); //Nos quedamos los lotes que cumplen el filtro.
		}
		else if(!fromExistingAmount.equals("") && existingAmount.equals("") && untilExistingAmount.equals("") &&  rangeFromExistingAmount.equals("")
				&& rangeUntilExistingAmount.equals("")) //Filtro por cantidad existente mayor o igual a una determinada:
		{
			int fromExistingAmountNumber = Integer.parseInt(fromExistingAmount); //Convertimos la cadena a número.
			lots = filterByFromExistingAmount(lots, fromExistingAmountNumber); //Nos quedamos los lotes que cumplen el filtro.
		}
		else if(!untilExistingAmount.equals("") && existingAmount.equals("") && fromExistingAmount.equals("") &&  rangeFromExistingAmount.equals("")
				&& rangeUntilExistingAmount.equals("")) //Filtro por cantidad existente menor o igual a una determinada:
		{
			int untilExistingAmountNumber = Integer.parseInt(untilExistingAmount); //Convertimos la cadena a número.
			lots = filterByUntilExistingAmount(lots, untilExistingAmountNumber); //Nos quedamos los lotes que cumplen el filtro.
		}
		else if(!rangeFromExistingAmount.equals("") && !rangeUntilExistingAmount.equals("") && existingAmount.equals("") &&  fromExistingAmount.equals("")
				&& untilExistingAmount.equals("")) //Filtro por cantidad existente en un rango determinado:
		{
			int rangeFromExistingAmountNumber = Integer.parseInt(rangeFromExistingAmount); //Convertimos la cadena a número.
			int rangeUntilExistingAmountNumber = Integer.parseInt(rangeUntilExistingAmount); //Convertimos la cadena a número.
			lots = filterByExistingAmountRange(lots, rangeFromExistingAmountNumber, rangeUntilExistingAmountNumber); //Nos quedamos los lotes que cumplen el filtro.
		}
		return lots; //Retornamos los lotes filtrados por el criterio de cantidad existente seleccionado.
	}
		
	//Aplicamos todos los filtros seleccionados:
	@Override
	public List<LotDTO> applyFilters(List<LotDTO> lots, String stockId, String receptionDate, String fromReceptionDate, 
										 String untilReceptionDate, String rangeFromReceptionDate, String rangeUntilReceptionDate, 
										 String existingAmount, String fromExistingAmount, String untilExistingAmount, 
										 String rangeFromExistingAmount, String rangeUntilExistingAmount)
	{
		//Si se seleccionó un filtro de la sección id de stock:
		if(!stockId.equals("all")) 
		{
			int stockIdNumber = Integer.parseInt(stockId); //Convertimos la cadena en un número.
			lots = filterByStockId(lots, stockIdNumber); //Nos quedamos con los lotes que cumplan el filtro.
		}
		
		//Aplicamos el filtro seleccionado de fecha de recepción:
		lots = applyFilterTypeReceptionDate(lots, receptionDate, fromReceptionDate, untilReceptionDate, rangeFromReceptionDate, rangeUntilReceptionDate);
		
		//Aplicamos el filtro seleccionado de cantidad existente:
		lots = applyFilterTypeExistingAmount(lots, existingAmount, fromExistingAmount, untilExistingAmount, rangeFromExistingAmount, rangeUntilExistingAmount);
		
		return lots; //Retornamos los lotes filtrados por los criterios seleccionados.
	}
	
	//Verificar o corregir:
	
	//Verificamos o corregimos los valores que llegan de los inputs tipo date y time para evitar la presencia de ',' en ellos:
	@Override
	public String verifyOrCorrectValue(String value) 
	{
		//La clave está en saber si contiene una ',' o no:
		if(value.contains(",")) //En caso de que haya:
		{
			int commaIndex = value.indexOf(","); //Obtenemos su posición en la cadena de texto.
			
			//Si se encuentra al principio:
			if(commaIndex == 0) 
			{
				value = value.substring(1); //El valor correcto es desde el caracter que ocupa la posición 1 en adelante.
			}
			else //Por el contrario, si la coma se encuentra al final de la cadena:
			{
				value = value.substring(0, commaIndex); //El valor correcto es desde el primer caracter hasta la posición anterior a la ',' inclusive.
			}
		}
		return value; //Retornamos el valor verificado/corregido.
	}
}
