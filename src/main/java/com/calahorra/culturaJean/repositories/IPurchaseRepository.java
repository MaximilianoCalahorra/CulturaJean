package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.Purchase;

///Interfaz IPurchaseRepository:
@Repository("purchaseRepository")
public interface IPurchaseRepository extends JpaRepository<Purchase, Serializable> 
{
	//Encontrar:
	
	//Encontramos la compra con determinado id:
	public abstract Purchase findByPurchaseId(int purchaseId);
	
	//Encontramos la compra con determinado id y el usuario e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.purchaseId = (:purchaseId)")
	public abstract Purchase findByPurchaseIdWithUserAndPurchaseItems(@Param("purchaseId")int purchaseId);
	
	//Encontramos las compras con determinado método de pago y el usuario e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.methodOfPay = (:methodOfPay)")
	public abstract List<Purchase> findByMethodOfPay(@Param("methodOfPay")String methodOfPay);
	
	//Encontramos las compras con determinada fecha y hora y el usuario e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime = (:dateTime)")
	public abstract List<Purchase> findByDateTime(@Param("dateTime")LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el usuario e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime <= (:dateTime)")
	public abstract List<Purchase> findByDateTimeBeforeThanOrEqual(@Param("dateTime")LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el usuario e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime >= (:dateTime)")
	public abstract List<Purchase> findByDateTimeAfterThanOrEqual(@Param("dateTime")LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora en un rango determinado y el usuario e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime BETWEEN (:fromDateTime) AND (:untilDateTime)")
	public abstract List<Purchase> findByDateTimeRange(@Param("fromDateTime")LocalDateTime fromDateTime, 
			                                           @Param("untilDateTime")LocalDateTime untilDateTime);
	
	//Encontramos las compras de un determinado usuario por su nombre de usuario:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user u INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE u.username = (:username)")
	public abstract List<Purchase> findByUser(@Param("username")String username);
	
	//Ordenar:
	
	//Ordenamos las compras por método de pago de manera alfabética:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.methodOfPay")
	public abstract List<Purchase> getAllInOrderAscByMethodOfPay();
	
	//Ordenamos las compras por método de pago de manera inversa al alfabeto:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.methodOfPay DESC")
	public abstract List<Purchase> getAllInOrderDescByMethodOfPay();
	
	//Ordenamos las compras por nombre de usuario de manera alfabética:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user u INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
		    + "ORDER BY u.username")
	public abstract List<Purchase> getAllInOrderAscByUserUsername();
	
	//Ordenamos las compras por nombre de usuario de manera inversa al alfabeto:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user u INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY u.username DESC")
	public abstract List<Purchase> getAllInOrderDescByUserUsername();
	
	//Ordenamos las compras por fecha y hora de manera ascendente:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.dateTime")
	public abstract List<Purchase> getAllInOrderAscByDateTime();
	
	//Ordenamos las compras por fecha y hora de manera descendente:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.user INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.dateTime DESC")
	public abstract List<Purchase> getAllInOrderDescByDateTime();
}
