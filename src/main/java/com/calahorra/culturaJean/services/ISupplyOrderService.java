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
	
	//Encontramos el pedido de aprovisionamiento con determinado id y su producto, miembro y proveedor asociados:
	public SupplyOrderDTO findBySupplyOrderIdWithProductAndMemberAndSupplier(int supplyOrderId);
	
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
	
	//Encontramos los pedidos de aprovisionamiento de determinado administrador por su nombre de usuario:
	public List<SupplyOrderDTO> findByMember(String username);
	
	//Encontramos un ejemplar de cada código de producto de los cuales hay pedidos de aprovisionamiento:
	public List<String> findUniqueEachProductCode(List<SupplyOrderDTO> supplyOrders);
		
	//Encontramos un ejemplar de cada nombre de proveedor de los cuales hay pedidos de aprovisionamiento:
	public List<String> findUniqueEachSupplierName(List<SupplyOrderDTO> supplyOrders);
	
	//Encontramos un ejemplar de cada nombre de usuario de los administradores de los cuales hay pedidos de aprovisionamiento:
	public List<String> findUniqueEachAdminUsername(List<SupplyOrderDTO> supplyOrders);
		
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
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma alfabética:
	public List<SupplyOrderDTO> inOrderAscByProductCode(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma inversa al alfabeto:
	public List<SupplyOrderDTO> inOrderDescByProductCode(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma alfabética:
	public List<SupplyOrderDTO> inOrderAscBySupplierName(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma inversa al alfabeto:
	public List<SupplyOrderDTO> inOrderDescBySupplierName(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma alfabética:
	public List<SupplyOrderDTO> inOrderAscByAdminUsername(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma inversa al alfabeto:
	public List<SupplyOrderDTO> inOrderDescByAdminUsername(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma ascendente:
	public List<SupplyOrderDTO> inOrderAscByAmount(List<SupplyOrderDTO> supplyOrders);
	
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma descendente:
	public List<SupplyOrderDTO> inOrderDescByAmount(List<SupplyOrderDTO> supplyOrders);
	
	//Aplicamos el ordenamiento seleccionado:
	public List<SupplyOrderDTO> applyOrder(List<SupplyOrderDTO> supplyOrders, String order);
	
	//Agregar:
	
	//Agregamos un pedido de aprovisonamiento a la base de datos:
	public SupplyOrderDTO insert(SupplyOrderDTO supplyOrder);
	
	//Filtrar:
	
	//Filtramos los pedidos de aprovisionamiento por el código del producto asociado:
	public List<SupplyOrderDTO> filterByProductCode(List<SupplyOrderDTO> supplyOrders, String productCode);
	
	//Filtramos los pedidos de aprovisionamiento por el nombre del proveedor asociado:
	public List<SupplyOrderDTO> filterBySupplierName(List<SupplyOrderDTO> supplyOrders, String supplierName);
	
	//Filtramos los pedidos de aprovisionamiento por el nombre de usuario del administrador que lo generó:
	public List<SupplyOrderDTO> filterByAdminUsername(List<SupplyOrderDTO> supplyOrders, String adminUsername);
	
	//Filtramos los pedidos de aprovisionamiento por la cantidad del mismo:
	public List<SupplyOrderDTO> filterByAmount(List<SupplyOrderDTO> supplyOrders, int amount);
	
	//Filtramos los pedidos de aprovisionamiento por la cantidad si es mayor o igual a una determinada:
	public List<SupplyOrderDTO> filterByFromAmount(List<SupplyOrderDTO> supplyOrders, int fromAmount);
	
	//Filtramos los pedidos de aprovisionamiento por la cantidad si es menor o igual a una determinada:
	public List<SupplyOrderDTO> filterByUntilAmount(List<SupplyOrderDTO> supplyOrders, int untilAmount);
	
	//Filtramos los pedidos de aprovisionamiento por la cantidad si está dentro de un rango determinado:
	public List<SupplyOrderDTO> filterByAmountRange(List<SupplyOrderDTO> supplyOrders, int rangeFromAmount, int rangeUntilAmount);
	
	//Aplicamos el filtro seleccionado de la sección cantidad:
	public List<SupplyOrderDTO> applyFilterTypeAmount(List<SupplyOrderDTO> supplyOrders, String amount, String fromAmount, String untilAmount,
													  String rangeFromAmount, String rangeUntilAmount);
	
	//Filtramos los pedidos de aprovisionamiento por el estado de la entrega:
	public List<SupplyOrderDTO> filterByDelivered(List<SupplyOrderDTO> supplyOrders, boolean delivered); 
	
	//Aplicamos todos los filtros seleccionados:
	public List<SupplyOrderDTO> applyFilters(List<SupplyOrderDTO> supplyOrders, String productCode, String supplierName, String amount,
											 String fromAmount, String untilAmount, String rangeFromAmount, String rangeUntilAmount);
}
