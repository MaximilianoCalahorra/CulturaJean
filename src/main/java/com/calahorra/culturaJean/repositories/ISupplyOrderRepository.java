package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.SupplyOrder;

///Interfaz ISupplyOrderRepository:
@Repository("supplyOrderRepository")
public interface ISupplyOrderRepository extends JpaRepository<SupplyOrder, Serializable>
{
	//Encontrar:
	
	//Encontramos el pedido de aprovisionamiento con determinado id:
	public abstract SupplyOrder findBySupplyOrderId(int supplyOrderId);
	
	//Encontramos el pedido de aprovisionamiento con determinado id y su producto, usuario y proveedor asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
		   + "WHERE so.supplyOrderId = (:supplyOrderId)")
	public abstract SupplyOrder findBySupplyOrderIdWithProductAndUserAndSupplier(@Param("supplyOrderId")int supplyOrderId);
	
	//Encontramos los pedidos de aprovisionamiento de un producto determinado por su código con los objetos asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product p INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
		   + "WHERE p.code = (:code)")
	public abstract List<SupplyOrder> findByProductCode(@Param("code")String code);
	
	//Encontramos los pedidos de aprovisionamiento de un proveedor determinado por su nombre con los objetos asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier s "
			+ "WHERE s.name = (:name)")
	public abstract List<SupplyOrder> findBySupplierName(@Param("name")String name);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad determinada con los objetos asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "WHERE so.amount = (:amount)")
	public abstract List<SupplyOrder> findByAmount(@Param("amount")int amount);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad menor o igual a una determinada con los objetos asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "WHERE so.amount <= (:amount)")
	public abstract List<SupplyOrder> findByAmountLessThanOrEqualTo(@Param("amount")int amount);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad mayor o igual a una determinada con los objetos asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "WHERE so.amount >= (:amount)")
	public abstract List<SupplyOrder> findByAmountGreaterThanOrEqualTo(@Param("amount")int amount);
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad dentro de un intervalo con los objetos asociados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "WHERE so.amount >= (:minimumAmount) AND so.amount <= (:maximumAmount)")
	public abstract List<SupplyOrder> findByAmountRange(@Param("minimumAmount")int minimumAmount, @Param("maximumAmount")int maximumAmount);
	
	//Encontramos los pedidos de aprovisionamiento entregados/no entregados:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "WHERE so.delivered = (:delivered)")
	public abstract List<SupplyOrder> findByDelivered(@Param("delivered")boolean delivered);
	
	//Ordernar:
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma ascendente:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product p INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
		   + "ORDER BY p.code")
	public abstract List<SupplyOrder> getAllInOrderAscByProductCode();
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma descendente:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product p INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "ORDER BY p.code DESC")
	public abstract List<SupplyOrder> getAllInOrderDescByProductCode();
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma alfabética:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier s "
		   + "ORDER BY s.name")
	public abstract List<SupplyOrder> getAllInOrderAscBySupplierName();
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma inversa al alfabeto:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier s "
			+ "ORDER BY s.name DESC")
	public abstract List<SupplyOrder> getAllInOrderDescBySupplierName();
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma ascendente:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "ORDER BY so.amount")
	public abstract List<SupplyOrder> getAllInOrderAscByAmount();
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma descendente:
	@Query("SELECT so FROM SupplyOrder so INNER JOIN FETCH so.product INNER JOIN FETCH so.user INNER JOIN FETCH so.supplier "
			+ "ORDER BY so.amount DESC")
	public abstract List<SupplyOrder> getAllInOrderDescByAmount();
}
