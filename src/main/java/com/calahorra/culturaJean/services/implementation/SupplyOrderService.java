package com.calahorra.culturaJean.services.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	@Override
	public List<String> findUniqueEachProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		List<String> productCodes = new ArrayList<String>(); //Definimos un listado donde se guardarán los códigos de producto.
		
		//Analizamos cada pedido de aprovisionamiento para saber si su código de producto se encuentra en el listado:
		for(SupplyOrderDTO supplyOrder: supplyOrders) 
		{
			String productCode = supplyOrder.getProduct().getCode(); //Obtenemos el código del producto.
			
			//Si el código no está en el listado:
			if(!productCodes.contains(productCode)) 
			{
				productCodes.add(productCode); //Agregamos el código.
			}
		}
		
		//Ordenamos el listado de códigos de producto de forma alfabética:
		productCodes.sort(null);
		
		return productCodes; //Retornamos el listado de códigos de producto.
	}
			
	//Encontramos un ejemplar de cada nombre de proveedor de los cuales hay pedidos de aprovisionamiento:
	@Override
	public List<String> findUniqueEachSupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		List<String> supplierNames = new ArrayList<String>(); //Definimos un listado donde se guardarán los nombres de proveedor.
		
		//Analizamos cada pedido de aprovisionamiento para saber si su nombre de proveedor se encuentra en el listado:
		for(SupplyOrderDTO supplyOrder: supplyOrders) 
		{
			String supplierName = supplyOrder.getSupplier().getName(); //Obtenemos el nombre del proveedor.
			
			//Si el nombre no está en el listado:
			if(!supplierNames.contains(supplierName)) 
			{
				supplierNames.add(supplierName); //Agregamos el nombre.
			}
		}
		
		//Ordenamos el listado de nombres de proveedor de forma alfabética:
		supplierNames.sort(null);
		
		return supplierNames; //Retornamos el listado de nombres de proveedor.
	}
	
	//Encontramos un ejemplar de cada nombre de usuario de los administradores de los cuales hay pedidos de aprovisionamiento:
	@Override
	public List<String> findUniqueEachAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		List<String> adminUsernames = new ArrayList<String>(); //Definimos un listado donde se guardarán los nombres de usuari de los administradores.
		
		//Analizamos cada pedido de aprovisionamiento para saber si su nombre de usuario del administrador se encuentra en el listado:
		for(SupplyOrderDTO supplyOrder: supplyOrders) 
		{
			String adminUsername = supplyOrder.getMember().getUsername(); //Obtenemos el nombre de usuario del administrador.
			
			//Si el nombre de usuario no está en el listado:
			if(!adminUsernames.contains(adminUsername)) 
			{
				adminUsernames.add(adminUsername); //Agregamos el nombre de usuario.
			}
		}
		
		//Ordenamos el listado de nombres de usuario de administrador de forma alfabética:
		adminUsernames.sort(null);
		
		return adminUsernames; //Retornamos el listado de nombres de usuario de los administradores.
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
	@Override
	public List<SupplyOrderDTO> inOrderAscByProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getProduct().getCode().compareToIgnoreCase(so2.getProduct().getCode()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> inOrderDescByProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getProduct().getCode().compareToIgnoreCase(so1.getProduct().getCode()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma alfabética:
	@Override
	public List<SupplyOrderDTO> inOrderAscBySupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getSupplier().getName().compareToIgnoreCase(so2.getSupplier().getName()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del) proveedor de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> inOrderDescBySupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getSupplier().getName().compareToIgnoreCase(so1.getSupplier().getName()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma alfabética:
	@Override
	public List<SupplyOrderDTO> inOrderAscByAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getMember().getUsername().compareToIgnoreCase(so2.getMember().getUsername()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> inOrderDescByAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getMember().getUsername().compareToIgnoreCase(so1.getMember().getUsername()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma ascendente:
	@Override
	public List<SupplyOrderDTO> inOrderAscByAmount(List<SupplyOrderDTO> supplyOrders)
	{
		supplyOrders.sort(Comparator.comparingInt(SupplyOrderDTO::getAmount));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma descendente:
	@Override
	public List<SupplyOrderDTO> inOrderDescByAmount(List<SupplyOrderDTO> supplyOrders)
	{
		supplyOrders.sort(Comparator.comparingInt(SupplyOrderDTO::getAmount).reversed());
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Aplicamos el ordenamiento seleccionado:
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
