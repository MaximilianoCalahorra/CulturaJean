package com.calahorra.culturaJean.services;

import java.util.List;

import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.entities.SupplyOrder;

///Interfaz ISupplyOrderService:
public interface ISupplyOrderService
{
	//Encontrar:
	
	//Encontramos el pedido de aprovisionamiento con determinado id:
	public SupplyOrder findBySupplyOrderId(int supplyOrderId);
	
	//Encontramos el pedido de aprovisionamiento con determinado id y su producto, usuario y proveedor asociados:
	public SupplyOrder findBySupplyOrderIdWithProductAndUserAndSupplier(int supplyOrderId);
	
	//Encontramos los pedidos de aprovisionamiento de un producto determinado por su código con los objetos asociados:
	public List<SupplyOrderDTO> findByProductCode(String code);
	
	//Encontramos los pedidos de aprovisionamiento de un proveedor determinado por su nombre con los objetos asociados:
	public List<SupplyOrderDTO> findBySupplierName(String name);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad determinada con los objetos asociados:
	public List<SupplyOrderDTO> findByAmount(int amount);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad menor o igual a una determinada con los objetos asociados:
	public List<SupplyOrderDTO> findByAmountLessThanOrEqualTo(int amount);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad mayor o igual a una determinada con los objetos asociados:
	public List<SupplyOrderDTO> findByAmountGreaterThanOrEqualTo(int amount);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad dentro de un intervalo con los objetos asociados:
	public List<SupplyOrderDTO> findByAmountRange(int minimumAmount, int maximumAmount);
	
	//Encontramos los pedidos de aprovisionamiento entregados/no entregados:
	public List<SupplyOrderDTO> findByDelivered(boolean delivered);
	
	//Obtener:
	
	//Obtenemos todos los pedidos de aprovisionamiento:
	public List<SupplyOrder> getAll();
	
	//Ordernar:
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma ascendente:
	public List<SupplyOrderDTO> getAllInOrderAscByProductCode();
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma descendente:
	public List<SupplyOrderDTO> getAllInOrderDescByProductCode();
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma alfabética:
	public List<SupplyOrderDTO> getAllInOrderAscBySupplierName();
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma inversa al alfabeto:
	public List<SupplyOrderDTO> getAllInOrderDescBySupplierName();
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma ascendente:
	public List<SupplyOrderDTO> getAllInOrderAscByAmount();
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma descendente:
	public List<SupplyOrderDTO> getAllInOrderDescByAmount();
	
	//Agregar:
	
	//Agregamos un pedido de aprovisonamiento a la base de datos:
	public SupplyOrderDTO insert(SupplyOrderDTO supplyOrder);
}
