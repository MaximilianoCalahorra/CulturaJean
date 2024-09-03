package com.calahorra.culturaJean.services.implementation;

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
}
