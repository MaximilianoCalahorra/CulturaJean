package com.calahorra.culturaJean.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.entities.Stock;
import com.calahorra.culturaJean.repositories.IStockRepository;
import com.calahorra.culturaJean.services.ILotService;
import com.calahorra.culturaJean.services.IStockService;

///Clase StockService:
@Service("stockService")
public class StockService implements IStockService
{
	//Atributos:
	private IStockRepository stockRepository;
	private ILotService lotService;
	private ModelMapper modelMapper;
	
	//Constructor:
	public StockService(IStockRepository stockRepository, ILotService lotService) 
	{
		this.stockRepository = stockRepository;
		this.lotService = lotService;
	}
	
	//Encontrar:
	
	//Encontramos el stock con determinado id:
	@Override
	public Stock findByStockId(int stockId) 
	{
		return stockRepository.findByStockId(stockId);
	}
	
	//Encontramos el stock con determinado id y su producto asociado:
	@Override
	public Stock findByStockIdWithProduct(int stockId) 
	{
		return stockRepository.findByStockIdWithProduct(stockId);
	}
	
	//Encontramos el stock de determinado producto:
	@Override
	public StockDTO findByProduct(int productId) 
	{
		return modelMapper.map(stockRepository.findByProduct(productId), StockDTO.class);
	}
	
	//Encontramos el stock del producto con determinado código:
	@Override
	public StockDTO findByProductCode(String code) 
	{
		return modelMapper.map(stockRepository.findByProductCode(code), StockDTO.class);
	}
	
	//Encontramos el stock del producto con determinado nombre:
	@Override
	public StockDTO findByProductName(String name) 
	{
		return modelMapper.map(stockRepository.findByProductName(name), StockDTO.class);
	}
	
	//Encontramos el stock de cada producto de determinada categoría:
	@Override
	public List<StockDTO> findByProductCategory(String category)
	{
		return stockRepository.findByProductCategory(category) //Obtenemos los stocks de productos de esa categoría como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto de determinado género:
	@Override
	public List<StockDTO> findByProductGender(Character gender)
	{
		return stockRepository.findByProductGender(gender) //Obtenemos los stocks de productos de ese género como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto de determinado talle:
	@Override
	public List<StockDTO> findByProductSize(String size)
	{
		return stockRepository.findByProductSize(size) //Obtenemos los stocks de productos de ese talle como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto de determinado color:
	@Override
	public List<StockDTO> findByProductColor(String color)
	{
		return stockRepository.findByProductColor(color) //Obtenemos los stocks de productos de ese color como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto habilitado/deshabilitado:
	@Override
	public List<StockDTO> findByProductEnabled(boolean enabled)
	{
		return stockRepository.findByProductEnabled(enabled) //Obtenemos los stocks de productos en ese estado como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto de determinado precio de venta:
	@Override
	public List<StockDTO> findByProductSalePrice(float salePrice)
	{
		return stockRepository.findByProductSalePrice(salePrice) //Obtenemos los stocks de productos de ese precio como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto con un precio de venta menor o igual a uno determinado:
	@Override
	public List<StockDTO> findByProductSalePriceLessThanOrEqualTo(float maximumPrice)
	{
		return stockRepository.findByProductSalePriceLessThanOrEqualTo(maximumPrice) //Obtenemos los stocks de productos con ese tipo de precio como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos el stock de cada producto con un precio de venta mayor o igual a uno determinado:
	@Override
	public List<StockDTO> findByProductSalePriceGreaterThanOrEqualTo(float minimumPrice)
	{
		return stockRepository.findByProductSalePriceGreaterThanOrEqualTo(minimumPrice) //Obtenemos los stocks de productos con ese tipo de precio como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los productos con un precio de venta entre determinado rango:
	@Override
	public List<StockDTO> findBySalePriceRange(float minimumPrice, float maximumPrice)
	{
		return stockRepository.findBySalePriceRange(minimumPrice, maximumPrice) //Obtenemos los stocks de productos con ese tipo de precio como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtener:
	
	//Obtenemos todos los stocks:
	@Override
	public List<Stock> getAll()
	{
		return stockRepository.findAll();
	}
	
	//Ordenar:
	
	//Ordenamos los stocks por id de manera ascendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderAscByStockId()
	{
		return stockRepository.getAllInOrderAscByStockId() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por id de manera descendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderDescByStockId()
	{
		return stockRepository.getAllInOrderDescByStockId() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por cantidad deseable de manera ascendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderAscByDesirableAmount()
	{
		return stockRepository.getAllInOrderAscByDesirableAmount() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por cantidad deseable de manera descendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderDescByDesirableAmount()
	{
		return stockRepository.getAllInOrderDescByDesirableAmount() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por cantidad mínima de manera ascendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderAscByMinimumAmount()
	{
		return stockRepository.getAllInOrderAscByMinimumAmount() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por cantidad mínima de manera descendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderDescByMinimumAmount()
	{
		return stockRepository.getAllInOrderDescByMinimumAmount() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por cantidad actual de manera ascendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderAscByActualAmount()
	{
		return stockRepository.getAllInOrderAscByActualAmount() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por cantidad actual de manera descendente con su producto asociado:
	@Override
	public List<StockDTO> getAllInOrderDescByActualAmount()
	{
		return stockRepository.getAllInOrderDescByActualAmount() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por el código del producto asociado de manera ascendente:
	@Override
	public List<StockDTO> getAllInOrderAscByProductCode()
	{
		return stockRepository.getAllInOrderAscByProductCode() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por el código del producto asociado de manera descendente:
	@Override
	public List<StockDTO> getAllInOrderDescByProductCode()
	{
		return stockRepository.getAllInOrderDescByProductCode() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por la categoría del producto asociado de manera alfabética:
	@Override
	public List<StockDTO> getAllInOrderAscByProductCategory()
	{
		return stockRepository.getAllInOrderAscByProductCategory() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por la categoría del producto asociado de manera inversa al alfabeto:
	@Override
	public List<StockDTO> getAllInOrderDescByProductCategory()
	{
		return stockRepository.getAllInOrderDescByProductCategory() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por el precio de venta del producto asociado de manera ascendente:
	@Override
	public List<StockDTO> getAllInOrderAscByProductSalePrice()
	{
		return stockRepository.getAllInOrderAscByProductSalePrice() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por el precio de venta del producto asociado de manera descendente:
	@Override
	public List<StockDTO> getAllInOrderDescByProductSalePrice()
	{
		return stockRepository.getAllInOrderDescByProductSalePrice() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por el nombre del producto asociado de manera alfabética:
	@Override
	public List<StockDTO> getAllInOrderAscByProductName()
	{
		return stockRepository.getAllInOrderAscByProductName() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los stocks por el nombre del producto asociado de manera inversa al alfabeto:
	@Override
	public List<StockDTO> getAllInOrderDescByProductName()
	{
		return stockRepository.getAllInOrderDescByProductName() //Obtenemos los stocks de productos ordenados como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Agregar o modificar:
	
	//Agregamos o modificamos un stock en la base de datos:
	public StockDTO insertOrUpdate(StockDTO stock) 
	{
		Stock saveStock = modelMapper.map(stock, Stock.class);
		return modelMapper.map(stockRepository.save(saveStock), StockDTO.class); //Guardamos el stock en la base de datos y lo
																			     //retornamos como DTO.
	}
	
	//Calcular:
	
	//Calculamos la cnatidad actual de stock:
	public int actualAmount(int stockId) 
	{
		int actualAmount = 0;
		//Recorremos los lotes del stock y acumulamos la cantidad de cada uno:
		for(LotDTO lot: lotService.findByStock(stockId)) 
		{
			actualAmount += lot.getExistingAmount();
		}
		return actualAmount;
	}
	
	//Disminuir:
	
	//Disminuimos el stock de los lotes que sean necesarios para satisfacer la compra:
	public void decreaseStock(int productId, int amount) throws Exception
	{
		StockDTO stock = findByProduct(productId); //Obtenemos el stock del producto.
		int totalStock = actualAmount(stock.getStockId()); //Obtenemos la cantidad actual del stock.
		
		//Si el stock del producto no alcanza para satisfacer la demanda:
		if(amount > totalStock) 
		{
			throw new Exception("Error! The product stock is insufficient.");
		}
		
		//Por el contrario, el stock es suficiente y realizamos el proceso de baja de stock en los lotes que correspondan y en el stock:
		int purchaseAmount = amount; //Definimos una variable auxiliar que contiene la cantidad por comprar.
	
		int i = 0;
		List<LotDTO> lots = lotService.findByStock(stock.getStockId()); //Obtenemos los lotes de ese stock.
		
		//Recorremos cada lote hasta satisfacer la cantidad que se quiere comprar:
		while(i < lots.size() && purchaseAmount > 0) 
		{
			LotDTO lot = lots.get(i); //Obtenemos el lote.
			
			//Si el lote no está vacío:
			if(lot.getExistingAmount() > 0) 
			{
				int newExistingAmount = lot.getExistingAmount(); //La nueva cantidad en principio es la actual.
				
				//Si la cantidad existente en el lote alcanza para satisfacer la demanda:
				if(lot.getExistingAmount() >= purchaseAmount) 
				{
					newExistingAmount -= purchaseAmount; //La nueva cantidad existente será la que estaba menos la que se compra.
					purchaseAmount = 0; //No quedan unidades por comprar.
				}
				else 
				{
					newExistingAmount = 0; //La nueva cantidad existente será 0 porque compramos todos las unidades que se pudo.
					purchaseAmount -= lot.getExistingAmount(); //La cantidad restante por comprar será la original menos la comprada.
				}
				
				//Actualizamos el lote en la base de datos con su nueva cantidad existente:
				lotService.insertOrUpdate(lot);
			}
			i++; //Repetimos el proceso con el siguiente lote.
		}
		totalStock -= amount; //Disminuimos el stock total con la cantidad comprada.
		stock.setActualAmount(totalStock); //Actualizamos la cantidad actual del stock.
		insertOrUpdate(stock); //Actualizamos el stock en la base de datos con la baja producida.
	}
}
