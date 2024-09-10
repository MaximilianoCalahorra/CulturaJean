package com.calahorra.culturaJean.services.implementation;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.entities.SupplyOrder;
import com.calahorra.culturaJean.repositories.ISupplyOrderRepository;
import com.calahorra.culturaJean.services.ISupplyOrderService;

///Clase SupplyOrderService:
@Service("supplyOrderService")
public class SupplyOrderService implements ISupplyOrderService
{
	//Atributos:
	private ISupplyOrderRepository supplyOrderRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public SupplyOrderService(ISupplyOrderRepository supplyOrderRepository) 
	{
		this.supplyOrderRepository = supplyOrderRepository;
	}
	
	//Encontrar:
	
	//Encontramos el pedido de aprovisionamiento con determinado id:
	@Override
	public SupplyOrder findBySupplyOrderId(int supplyOrderId) 
	{
		return supplyOrderRepository.findBySupplyOrderId(supplyOrderId);
	}
	
	//Encontramos el pedido de aprovisionamiento con determinado id y su producto, miembro y proveedor asociados:
	@Override
	public SupplyOrderDTO findBySupplyOrderIdWithProductAndMemberAndSupplier(int supplyOrderId) 
	{
		return modelMapper.map(supplyOrderRepository.findBySupplyOrderIdWithProductAndMemberAndSupplier(supplyOrderId), SupplyOrderDTO.class);
	}
	
	//Encontramos los pedidos de aprovisionamiento de un producto determinado por su código con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByProductCode(String code)
	{
		return supplyOrderRepository.findByProductCode(code) //Obtenemos los pedidos de aprovisionamiento de ese producto como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de un proveedor determinado por su nombre con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findBySupplierName(String name)
	{
		return supplyOrderRepository.findBySupplierName(name) //Obtenemos los pedidos de aprovisionamiento de ese proveedor como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad determinada con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmount(int amount)
	{
		return supplyOrderRepository.findByAmount(amount) //Obtenemos los pedidos de aprovisionamiento de esa cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad menor o igual a una determinada con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmountLessThanOrEqualTo(int amount)
	{
		return supplyOrderRepository.findByAmountLessThanOrEqualTo(amount) //Obtenemos los pedidos de aprovisionamiento de ese tipo de cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad mayor o igual a una determinada con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmountGreaterThanOrEqualTo(int amount)
	{
		return supplyOrderRepository.findByAmountGreaterThanOrEqualTo(amount) //Obtenemos los pedidos de aprovisionamiento de ese tipo de cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad dentro de un intervalo con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmountRange(int minimumAmount, int maximumAmount)
	{
		return supplyOrderRepository.findByAmountRange(minimumAmount, maximumAmount) //Obtenemos los pedidos de aprovisionamiento de ese tipo de cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento entregados/no entregados:
	@Override
	public List<SupplyOrderDTO> findByDelivered(boolean delivered)
	{
		return supplyOrderRepository.findByDelivered(delivered) //Obtenemos los pedidos de aprovisionamiento en ese estado como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de determinado administrador por su nombre de usuario:
	@Override
	public List<SupplyOrderDTO> findByMember(String username)
	{
		return supplyOrderRepository.findByMember(username) //Obtenemos los pedidos de aprovisionamiento de ese miembro como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un ejemplar de cada código de producto de los cuales hay pedidos de aprovisionamiento:
	public List<String> findUniqueEachProductCode()
	{
		return supplyOrderRepository.findUniqueEachProductCode(); //Retornamos el listado de códigos de producto ordenado.
	}
			
	//Encontramos un ejemplar de cada nombre de proveedor de los cuales hay pedidos de aprovisionamiento:
	public List<String> findUniqueEachSupplierName()
	{
		return supplyOrderRepository.findUniqueEachSupplierName(); //Retornamos el listado de nombres de proveedor ordenado.
	}
	
	//Encontramos un ejemplar de cada código de producto de los cuales hay pedidos de aprovisionamiento entregados/no entregados:
	public List<String> findUniqueEachProductCodeDelivered(boolean delivered)
	{
		return supplyOrderRepository.findUniqueEachProductCodeDelivered(delivered); //Retornamos el listado de códigos de producto ordenado.
	}
			
	//Encontramos un ejemplar de cada nombre de proveedor de los cuales hay pedidos de aprovisionamiento entregados/no entregados:
	public List<String> findUniqueEachSupplierNameDelivered(boolean delivered)
	{
		return supplyOrderRepository.findUniqueEachSupplierNameDelivered(delivered); //Retornamos el listado de nombres de proveedor ordenado.
	}
	
	//Encontramos un ejemplar de cada nombre de usuario de los administradores de los cuales hay pedidos de aprovisionamiento entregados/no entregados:
	public List<String> findUniqueEachAdminUsernameDelivered(@Param("delivered")boolean delivered)
	{
		return supplyOrderRepository.findUniqueEachAdminUsernameDelivered(delivered); //Retornamos el listado de nombres de usuario de los administradores ordenado.
	}
	
	//Obtener:
	
	//Obtenemos todos los pedidos de aprovisionamiento:
	@Override
	public List<SupplyOrder> getAll()
	{
		return supplyOrderRepository.findAll();
	}
	
	//Ordenar:
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma ascendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderAscByProductCode()
	{
		return supplyOrderRepository.getAllInOrderAscByProductCode() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma descendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderDescByProductCode()
	{
		return supplyOrderRepository.getAllInOrderDescByProductCode() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma alfabética:
	@Override
	public List<SupplyOrderDTO> getAllInOrderAscBySupplierName()
	{
		return supplyOrderRepository.getAllInOrderAscBySupplierName() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> getAllInOrderDescBySupplierName()
	{
		return supplyOrderRepository.getAllInOrderDescBySupplierName() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma ascendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderAscByAmount()
	{
		return supplyOrderRepository.getAllInOrderAscByAmount() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma descendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderDescByAmount()
	{
		return supplyOrderRepository.getAllInOrderDescByAmount() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma alfabética:
	public List<SupplyOrderDTO> inOrderAscByProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getProduct().getCode().compareToIgnoreCase(so2.getProduct().getCode()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma inversa al alfabeto:
	public List<SupplyOrderDTO> inOrderDescByProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getProduct().getCode().compareToIgnoreCase(so1.getProduct().getCode()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma alfabética:
	public List<SupplyOrderDTO> inOrderAscBySupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getSupplier().getName().compareToIgnoreCase(so2.getSupplier().getName()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma inversa al alfabeto:
	public List<SupplyOrderDTO> inOrderDescBySupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getSupplier().getName().compareToIgnoreCase(so1.getSupplier().getName()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma alfabética:
	public List<SupplyOrderDTO> inOrderAscByAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getMember().getUsername().compareToIgnoreCase(so2.getMember().getUsername()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma inversa al alfabeto:
	public List<SupplyOrderDTO> inOrderDescByAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getMember().getUsername().compareToIgnoreCase(so1.getMember().getUsername()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma ascendente:
	public List<SupplyOrderDTO> inOrderAscByAmount(List<SupplyOrderDTO> supplyOrders)
	{
		supplyOrders.sort(Comparator.comparingInt(SupplyOrderDTO::getAmount));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma descendente:
	public List<SupplyOrderDTO> inOrderDescByAmount(List<SupplyOrderDTO> supplyOrders)
	{
		supplyOrders.sort(Comparator.comparingInt(SupplyOrderDTO::getAmount).reversed());
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Aplicamos el ordenamiento seleccionado:
	public List<SupplyOrderDTO> applyOrder(List<SupplyOrderDTO> supplyOrders, String order)
	{
		//Ordenamos el listado de pedidos de aprovisionamiento resultante de los procesos anteriores en base al tipo de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByProductCode": supplyOrders = inOrderAscByProductCode(supplyOrders); break; //Alfabéticamente por código de producto.
			case "orderDescByProductCode": supplyOrders = inOrderDescByProductCode(supplyOrders); break; //Inverso al alfabeto por código de producto. 
			case "orderAscBySupplierName": supplyOrders = inOrderAscBySupplierName(supplyOrders); break; //Alfabéticamente por nombre del proveedor.
			case "orderDescBySupplierName": supplyOrders = inOrderDescBySupplierName(supplyOrders); break; //Inverso al alfabeto por nombre del proveedor.
			case "orderAscByAdminUsername": supplyOrders = inOrderAscByAdminUsername(supplyOrders); break; //Alfabéticamente por nombre de usuario del administrador.
			case "orderDescByAdminUsername": supplyOrders = inOrderDescByAdminUsername(supplyOrders); break; //Inverso al alfabeto por nombre de usuario del administrador.
			case "orderAscByAmount": supplyOrders = inOrderAscByAmount(supplyOrders); break; //Ascendente por cantidad.
			case "orderDescByAmount": supplyOrders = inOrderDescByAmount(supplyOrders); break; //Descendente por cantidad. 
		}
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
	
	//Agregar:
	
	//Agregamos un pedido de aprovisionamiento a la base de datos:
	public SupplyOrderDTO insert(SupplyOrderDTO supplyOrder) 
	{
		SupplyOrder savedSupplyOrder = modelMapper.map(supplyOrder, SupplyOrder.class);
		return modelMapper.map(supplyOrderRepository.save(savedSupplyOrder), SupplyOrderDTO.class); //Insertamos el pedido de
																								    //aprovisonamiento en la
																						            //base de datos y lo
																									//retornamos como DTO.
	}
	
	//Filtrar:
	
	//Filtramos los pedidos de aprovisionamiento por el código del producto asociado:
	public List<SupplyOrderDTO> filterByProductCode(List<SupplyOrderDTO> supplyOrders, String productCode)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (!supplyOrder.getProduct().getCode().equals(productCode)) 
			{
				iterator.remove(); //En caso de que no tenga un código de producto como el del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por el nombre del proveedor asociado:
	public List<SupplyOrderDTO> filterBySupplierName(List<SupplyOrderDTO> supplyOrders, String supplierName)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (!supplyOrder.getSupplier().getName().equals(supplierName)) 
			{
				iterator.remove(); //En caso de que no tenga un nombre de proveedor como el del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
	
	//Filtramos los pedidos de aprovisionamiento por el nombre de usuario del administrador que lo generó:
	public List<SupplyOrderDTO> filterByAdminUsername(List<SupplyOrderDTO> supplyOrders, String adminUsername)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (!supplyOrder.getMember().getUsername().equals(adminUsername)) 
			{
				iterator.remove(); //En caso de que no tenga un nombre de usuario como el del filtro, lo removemos.
			}
		}
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad del mismo:
	public List<SupplyOrderDTO> filterByAmount(List<SupplyOrderDTO> supplyOrders, int amount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() != amount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad como la del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad si es mayor o igual a una determinada:
	public List<SupplyOrderDTO> filterByFromAmount(List<SupplyOrderDTO> supplyOrders, int fromAmount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() < fromAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad mayor o igual a la del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad si es menor o igual a una determinada:
	public List<SupplyOrderDTO> filterByUntilAmount(List<SupplyOrderDTO> supplyOrders, int untilAmount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() > untilAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad menor o igual a la del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad si está dentro de un rango determinado:
	public List<SupplyOrderDTO> filterByAmountRange(List<SupplyOrderDTO> supplyOrders, int rangeFromAmount, int rangeUntilAmount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() < rangeFromAmount || supplyOrder.getAmount() > rangeUntilAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad mayor o igual a la mínima y menor o igual a la máxima del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Aplicamos el filtro seleccionado de la sección cantidad:
	public List<SupplyOrderDTO> applyFilterTypeAmount(List<SupplyOrderDTO> supplyOrders, String amount, String fromAmount, String untilAmount,
														  String rangeFromAmount, String rangeUntilAmount)
	{
		//Aplicamos el filtro que corresponda de la sección cantidad al listado:
		if(!amount.equals("") && fromAmount.equals("") && untilAmount.equals("") && rangeFromAmount.equals("") && rangeUntilAmount.equals("")) //Filtro por cantidad específica.
		{
			int amountNumber = Integer.parseInt(amount); //Convertimos la cadena a número.
			supplyOrders = filterByAmount(supplyOrders, amountNumber); //Nos quedamos solo con los que cumplen el filtro.
		}
		else if(!fromAmount.equals("") && amount.equals("") && untilAmount.equals("") && rangeFromAmount.equals("") && rangeUntilAmount.equals("")) //Filtro por cantidad mayor o igual a una específica.
		{
			int fromAmountNumber = Integer.parseInt(fromAmount); //Convertimos la cadena a número.
			supplyOrders = filterByFromAmount(supplyOrders, fromAmountNumber); //Nos quedamos solo con los que cumplen el filtro.
		}
		else if(!untilAmount.equals("") && amount.equals("") && fromAmount.equals("") && rangeFromAmount.equals("") && rangeUntilAmount.equals("")) //Filtro por cantidad menor o igual a una específica.
		{
			int untilAmountNumber = Integer.parseInt(untilAmount); //Convertimos la cadena a número.
			supplyOrders = filterByUntilAmount(supplyOrders, untilAmountNumber); //Nos quedamos solo con los que cumplen el filtro.
		}
		else if(!rangeFromAmount.equals("") && !rangeUntilAmount.equals("") && amount.equals("") && fromAmount.equals("") && untilAmount.equals("")) //Filtro por una cantidad entre un rango específico.
		{
			int rangeFromAmountNumber = Integer.parseInt(rangeFromAmount); //Convertimos la cadena a número.
			int rangeUntilAmountNumber = Integer.parseInt(rangeUntilAmount); //Convertimos la cadena a número.
			supplyOrders = filterByAmountRange(supplyOrders, rangeFromAmountNumber, rangeUntilAmountNumber); //Nos quedamos solo con los que cumplen el filtro.
		}
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
	
	//Filtramos los pedidos de aprovisionamiento por el estado de la entrega:
	public List<SupplyOrderDTO> filterByDelivered(List<SupplyOrderDTO> supplyOrders, boolean delivered)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (!supplyOrder.isDelivered() == delivered) 
			{
				iterator.remove(); //En caso de que no tenga el estado del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
	
	//Aplicamos todos los filtros seleccionados:
	public List<SupplyOrderDTO> applyFilters(List<SupplyOrderDTO> supplyOrders, String productCode, String supplierName, String amount,
											 String fromAmount, String untilAmount, String rangeFromAmount, String rangeUntilAmount)
	{
		//Si el filtro por código de producto está seleccionado por alguno en específico:
		if(!productCode.equals("all")) 
		{

			supplyOrders = filterByProductCode(supplyOrders, productCode); //Aplicamos el filtro por código de producto.
		}
		
		//Si el filtro por nombre de proveedor está seleccionado por alguno en específico:
		if(!supplierName.equals("all"))
		{
			supplyOrders = filterBySupplierName(supplyOrders, supplierName); //Aplicamos el filtro por nombre de proveedor.
		}
		
		//Aplicamos el filtro elegido de la sección cantidad:
		supplyOrders = applyFilterTypeAmount(supplyOrders, amount, fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount); 
		
		return supplyOrders; //Retornamos el listado filtrado.
	}
}
