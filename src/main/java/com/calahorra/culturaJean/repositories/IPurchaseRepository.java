package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	
	//Encontramos la compra con determinado id y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.purchaseId = (:purchaseId)")
	public abstract Purchase findByPurchaseIdWithMemberAndPurchaseItems(@Param("purchaseId")int purchaseId);
	
	//Encontramos las compras con determinado método de pago y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.methodOfPay = (:methodOfPay)")
	public abstract List<Purchase> findByMethodOfPay(@Param("methodOfPay")String methodOfPay);
	
	//Encontramos las compras con determinada fecha y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE DATE(p.dateTime) = (:date)")
	public abstract List<Purchase> findByDate(@Param("date")LocalDate date);
	
	//Encontramos las compras con una fecha anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE DATE(p.dateTime) <= (:date)")
	public abstract List<Purchase> findByDateBeforeThanOrEqual(@Param("date")LocalDate date);
	
	//Encontramos las compras con una fecha posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE DATE(p.dateTime) >= (:date)")
	public abstract List<Purchase> findByDateAfterThanOrEqual(@Param("date")LocalDate date);
	
	//Encontramos las compras con una fecha en un rango determinado y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE DATE(p.dateTime) BETWEEN (:fromDate) AND (:untilDate)")
	public abstract List<Purchase> findByDateRange(@Param("fromDate")LocalDate fromDate,  @Param("untilDate")LocalDate untilDate);
	
	//Encontramos las compras con determinada hora y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE TIME(p.dateTime) = (:time)")
	public abstract List<Purchase> findByTime(@Param("time")LocalTime time);
	
	//Encontramos las compras con una hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE TIME(p.dateTime) <= (:time)")
	public abstract List<Purchase> findByTimeBeforeThanOrEqual(@Param("time")LocalTime time);
	
	//Encontramos las compras con una hora posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE TIME(p.dateTime) >= (:time)")
	public abstract List<Purchase> findByTimeAfterThanOrEqual(@Param("time")LocalTime time);
	
	//Encontramos las compras con una hora en un rango determinado y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE TIME(p.dateTime) BETWEEN (:fromTime) AND (:untilTime)")
	public abstract List<Purchase> findByTimeRange(@Param("fromTime")LocalTime fromTime, @Param("untilTime")LocalTime untilTime);
	
	//Encontramos las compras con determinada fecha y hora y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime = (:dateTime)")
	public abstract List<Purchase> findByDateTime(@Param("dateTime")LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime <= (:dateTime)")
	public abstract List<Purchase> findByDateTimeBeforeThanOrEqual(@Param("dateTime")LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime >= (:dateTime)")
	public abstract List<Purchase> findByDateTimeAfterThanOrEqual(@Param("dateTime")LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora en un rango determinado y el miembro e ítems de la compra asociados:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE p.dateTime BETWEEN (:fromDateTime) AND (:untilDateTime)")
	public abstract List<Purchase> findByDateTimeRange(@Param("fromDateTime")LocalDateTime fromDateTime, 
			                                           @Param("untilDateTime")LocalDateTime untilDateTime);
	
	//Encontramos las compras de un determinado member por su nombre de usuario:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member m INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "WHERE m.username = (:username)")
	public abstract List<Purchase> findByMember(@Param("username")String username);
	
	//Ordenar:
	
	//Ordenamos las compras por método de pago de manera alfabética:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.methodOfPay")
	public abstract List<Purchase> getAllInOrderAscByMethodOfPay();
	
	//Ordenamos las compras por método de pago de manera inversa al alfabeto:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.methodOfPay DESC")
	public abstract List<Purchase> getAllInOrderDescByMethodOfPay();
	
	//Ordenamos las compras por nombre de usuario de manera alfabética:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member u INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
		    + "ORDER BY u.username")
	public abstract List<Purchase> getAllInOrderAscByUserUsername();
	
	//Ordenamos las compras por nombre de usuario de manera inversa al alfabeto:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member u INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY u.username DESC")
	public abstract List<Purchase> getAllInOrderDescByUserUsername();
	
	//Ordenamos las compras por fecha y hora de manera ascendente:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.dateTime")
	public abstract List<Purchase> getAllInOrderAscByDateTime();
	
	//Ordenamos las compras por fecha y hora de manera descendente:
	@Query("SELECT p FROM Purchase p INNER JOIN FETCH p.member INNER JOIN FETCH p.purchaseItems pi INNER JOIN FETCH pi.product "
			+ "ORDER BY p.dateTime DESC")
	public abstract List<Purchase> getAllInOrderDescByDateTime();
}
