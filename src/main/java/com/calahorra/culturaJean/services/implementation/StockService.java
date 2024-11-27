package com.calahorra.culturaJean.services.implementation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.LotDTO;
import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.entities.Stock;
import com.calahorra.culturaJean.entities.Supplier;
import com.calahorra.culturaJean.entities.SupplyOrder;
import com.calahorra.culturaJean.entities.Member;
import com.calahorra.culturaJean.repositories.IStockRepository;
import com.calahorra.culturaJean.services.ILotService;
import com.calahorra.culturaJean.services.IStockService;
import com.calahorra.culturaJean.services.ISupplierService;
import com.calahorra.culturaJean.services.ISupplyOrderService;

///Clase StockService:
@Service("stockService")
public class StockService implements IStockService
{
	//Atributos:
	private IStockRepository stockRepository;
	private ILotService lotService;
	private ISupplyOrderService supplyOrderService;
	private ISupplierService supplierService;
	private MemberService memberService;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public StockService(IStockRepository stockRepository, ILotService lotService, ISupplyOrderService supplyOrderService,
						ISupplierService supplierService, MemberService memberService) 
	{
		this.stockRepository = stockRepository;
		this.lotService = lotService;
		this.supplyOrderService = supplyOrderService;
		this.supplierService = supplierService;
		this.memberService = memberService;
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
	public List<StockDTO> getAll()
	{
		return stockRepository.findAll() //Obtenemos todos los stocks de productos como entidades.
				.stream()
				.map(stock -> modelMapper.map(stock, StockDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
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
	
	//Ordenamos los stocks por la cantidad deseable de forma ascendente:
	@Override
	public List<StockDTO> inOrderAscByDesirableAmount(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingInt(stock -> stock.getDesirableAmount()));
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por la cantidad deseable de forma descendente:
	@Override
	public List<StockDTO> inOrderDescByDesirableAmount(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingInt(stock -> ((StockDTO)stock).getDesirableAmount()).reversed());
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por la cantidad mínima de forma ascendente:
	@Override
	public List<StockDTO> inOrderAscByMinimumAmount(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingInt(stock -> stock.getMinimumAmount()));
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por la cantidad mínima de forma descendente:
	@Override
	public List<StockDTO> inOrderDescByMinimumAmount(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingInt(stock -> ((StockDTO)stock).getMinimumAmount()).reversed());
		return stocks; //Retornamos los stocks ordenados.
	}

	//Ordenamos los stocks por la cantidad actual de forma ascendente:
	@Override
	public List<StockDTO> inOrderAscByActualAmount(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingInt(stock -> stock.getActualAmount()));
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por la cantidad actual de forma descendente:
	@Override
	public List<StockDTO> inOrderDescByActualAmount(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingInt(stock -> ((StockDTO)stock).getActualAmount()).reversed());
		return stocks; //Retornamos los stocks ordenados.
	}
	
	//Ordenamos los stocks por código de producto de forma alfabética:
	@Override
	public List<StockDTO> inOrderAscByProductCode(List<StockDTO> stocks)
	{
		Collections.sort(stocks, (s1, s2) -> s1.getProduct().getCode().compareToIgnoreCase(s2.getProduct().getCode()));
		return stocks; //Retornamos los stocks ordenados.
	}
	
	//Ordenamos los stocks por código de producto de forma inversa al alfabeto:
	@Override
	public List<StockDTO> inOrderDescByProductCode(List<StockDTO> stocks)
	{
		Collections.sort(stocks, (s1, s2) -> s2.getProduct().getCode().compareToIgnoreCase(s1.getProduct().getCode()));
		return stocks; //Retornamos los stocks ordenados.
	}

	//Ordenamos los stocks por categoría de producto de forma alfabética:
	@Override
	public List<StockDTO> inOrderAscByProductCategory(List<StockDTO> stocks)
	{
		Collections.sort(stocks, (s1, s2) -> s1.getProduct().getCategory().compareToIgnoreCase(s2.getProduct().getCategory()));
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por categoría de producto de forma inversa al alfabeto:
	@Override
	public List<StockDTO> inOrderDescByProductCategory(List<StockDTO> stocks)
	{
		Collections.sort(stocks, (s1, s2) -> s2.getProduct().getCategory().compareToIgnoreCase(s1.getProduct().getCategory()));
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por la precio de venta del producto de forma ascendente:
	@Override
	public List<StockDTO> inOrderAscByProductSalePrice(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingDouble(stock -> stock.getProduct().getSalePrice()));
		return stocks; //Retornamos los stocks ordenados.
	}
			
	//Ordenamos los stocks por precio de venta del producto de forma descendente:
	@Override
	public List<StockDTO> inOrderDescByProductSalePrice(List<StockDTO> stocks)
	{
		stocks.sort(Comparator.comparingDouble(stock -> ((StockDTO)stock).getProduct().getSalePrice()).reversed());
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Ordenamos los stocks por nombre de producto de forma alfabética:
	@Override
	public List<StockDTO> inOrderAscByProductName(List<StockDTO> stocks)
	{
		Collections.sort(stocks, (s1, s2) -> s1.getProduct().getName().compareToIgnoreCase(s2.getProduct().getName()));
		return stocks; //Retornamos los stocks ordenados.
	}
	
	//Ordenamos los stocks por nombre de producto de forma inversa al alfabeto:
	@Override
	public List<StockDTO> inOrderDescByProductName(List<StockDTO> stocks)
	{
		Collections.sort(stocks, (s1, s2) -> s2.getProduct().getName().compareToIgnoreCase(s1.getProduct().getName()));
		return stocks; //Retornamos los stocks ordenados.
	}
		
	//Aplicamos el criterio de ordenamiento elegido:
	@Override
	public List<StockDTO> applyOrder(List<StockDTO> stocks, String order)
	{
		//Según el criterio de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByDesirableAmount": stocks = inOrderAscByDesirableAmount(stocks); break; //Ascendente por cantidad deseable.
			case "orderDescByDesirableAmount": stocks = inOrderDescByDesirableAmount(stocks); break; //Descendente por cantidad deseable.
			case "orderAscByMinimumAmount": stocks = inOrderAscByMinimumAmount(stocks); break; //Ascendente por cantidad mínima.
			case "orderDescByMinimumAmount": stocks = inOrderDescByMinimumAmount(stocks); break; //Descendente por cantidad mínima.
			case "orderAscByActualAmount": stocks = inOrderAscByActualAmount(stocks); break; //Ascendente por cantidad actual.
			case "orderDescByActualAmount": stocks = inOrderDescByActualAmount(stocks); break; //Descendente por cantidad actual.
			case "orderAscByProductCode": stocks = inOrderAscByProductCode(stocks); break; //Alfabéticamente por código de producto.
			case "orderDescByProductCode": stocks = inOrderDescByProductCode(stocks); break; //Inverso al alfabeto por código de producto.
			case "orderAscByProductCategory": stocks = inOrderAscByProductCategory(stocks); break; //Alfabéticamente por cateoría de producto.
			case "orderDescByProductCategory": stocks = inOrderDescByProductCategory(stocks); break; //Inverso al alfabeto por cateoría de producto.
			case "orderAscByProductSalePrice": stocks = inOrderAscByProductSalePrice(stocks); break; //Ascendente por precio de venta de producto.
			case "orderDescByProductSalePrice": stocks = inOrderDescByProductSalePrice(stocks); break; //Descendente por precio de venta de producto.
			case "orderAscByProductName": stocks = inOrderAscByProductName(stocks); break; //Alfabéticamente por nombre de producto.
			case "orderDescByProductName": stocks = inOrderDescByProductName(stocks); break; //Inverso al alfabeto por nombre de producto.
		}
		return stocks; //Retornamos los stocks ordenados.
	}
	
	//Agregar o modificar:
	
	//Agregamos o modificamos un stock en la base de datos:
	@Override
	public StockDTO insertOrUpdate(StockDTO stock) 
	{
		Stock saveStock = modelMapper.map(stock, Stock.class);
		return modelMapper.map(stockRepository.save(saveStock), StockDTO.class); //Guardamos el stock en la base de datos y lo
																			     //retornamos como DTO.
	}
	
	//Agregamos un pedido de aprovisionamiento de un producto de forma automática:
	@Override
	public void generateAutomaticSupplyOrder(int productId) 
	{
		StockDTO stockDTO = findByProduct(productId); //Obtenemos el stock relacionado con el producto.
		
		//Si el stock actual alcanzó o perforó la cantidad mínima que se quiere tener:
		if(stockDTO.getActualAmount() <= stockDTO.getMinimumAmount()) 
		{
			//Vamos a generar un pedido de aprovisionamiento automático que lleve la cantidad actual al nivel de la deseable al dar de alta
			//el lote que se puede generar con este pedido:
			int supplyOrderAmount = stockDTO.getDesirableAmount() - stockDTO.getActualAmount(); //Calculamos la cantidad que tendrá el pedido de aprovisionamiento.
			Product product = modelMapper.map(stockDTO.getProduct(), Product.class); //Obtenemos el producto del que se hará el pedido de aprovisionamiento.
			Member member = memberService.findByUsernameAndFetchUserRolesEagerly("culti_bot"); //El pedido de aprovisionamiento es realizado por el bot administrador.
				
			//Definimos el proveedor del pedido de aprovisionamiento:
			Supplier supplier = null; 
			//Para esto tenemos en cuenta la categoría del producto:
			switch(product.getCategory()) 
			{
				case "Short Sleeve T-Shirts": supplier = supplierService.findByName("Short Sleeve T-Shirts"); break;
				case "Long Sleeve T-Shirts": supplier = supplierService.findByName("Long Sleeve T-Shirts"); break;
				case "Jumpers": supplier = supplierService.findByName("Jumpers"); break;
				case "Jackets": supplier = supplierService.findByName("Jackets"); break;
				case "Jeans": supplier = supplierService.findByName("Jeans"); break;
				case "Pants": supplier = supplierService.findByName("Pants"); break;
			}
				
			//Instanciamos un nuevo pedido de aprovisionamiento con la información correspondiente y lo insertamos en la base de datos:
			SupplyOrder supplyOrder = new SupplyOrder(product, member, supplier, supplyOrderAmount, false);
			supplyOrderService.insert(modelMapper.map(supplyOrder, SupplyOrderDTO.class)); 
		}
	}
	
	//Calcular:
	
	//Calculamos la cnatidad actual de stock:
	@Override
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
	@Override
	public void decreaseStock(int productId, int amount) throws Exception
	{
		StockDTO stock = findByProduct(productId); //Obtenemos el stock del producto.
		int totalStock = actualAmount(stock.getStockId()); //Obtenemos la cantidad actual del stock.
		
		//Si el stock del producto no alcanza para satisfacer la demanda:
		if(amount > totalStock) 
		{
			throw new Exception("The stock of product " + stock.getProduct().getName() + " of size " + stock.getProduct().getSize() + " is insufficient."
							  + "There are " + stock.getActualAmount() + " units and you want " + amount + ". Sorry!");
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
				
				lot.setExistingAmount(newExistingAmount); //Seteamos la cantidad existente del lote teniendo en cuenta las bajas.
				lotService.insertOrUpdate(lot); //Actualizamos el lote en la base de datos con su nueva cantidad existente.
			}
			i++; //Repetimos el proceso con el siguiente lote.
		}
		totalStock -= amount; //Disminuimos el stock total con la cantidad comprada.
		stock.setActualAmount(totalStock); //Actualizamos la cantidad actual del stock.
		insertOrUpdate(stock); //Actualizamos el stock en la base de datos con la baja producida.
	}
}
