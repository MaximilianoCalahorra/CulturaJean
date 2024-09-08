package com.calahorra.culturaJean.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.calahorra.culturaJean.dtos.PurchaseDTO;
import com.calahorra.culturaJean.entities.Purchase;

public interface IPurchaseService 
{
	//Encontrar:
	
	//Encontramos la compra con determinado id:
	public Purchase findByPurchaseId(int purchaseId);
	
	//Encontramos la compra con determinado id y el miembro e ítems de la compra asociados:
	public Purchase findByPurchaseIdWithMemberAndPurchaseItems(int purchaseId);
	
	//Encontramos las compras con determinado método de pago y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByMethodOfPay(String methodOfPay);
	
	//Encontramos las compras con determinada fecha y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDate(LocalDate date);
	
	//Encontramos las compras con una fecha anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateBeforeThanOrEqual(LocalDate date);
	
	//Encontramos las compras con una fecha posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateAfterThanOrEqual(LocalDate date);
	
	//Encontramos las compras con una fecha en un rango determinado y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateRange(LocalDate fromDate, LocalDate untilDate);
	
	//Encontramos las compras con determinada hora y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByTime(LocalTime time);
	
	//Encontramos las compras con una hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByTimeBeforeThanOrEqual(LocalTime time);
	
	//Encontramos las compras con una hora posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByTimeAfterThanOrEqual(LocalTime time);
	
	//Encontramos las compras con una hora en un rango determinado y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByTimeRange(LocalTime fromTime, LocalTime untilTime);
	
	//Encontramos las compras con determinada fecha y hora y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateTime(LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateTimeBeforeThanOrEqual(LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateTimeAfterThanOrEqual(LocalDateTime dateTime);
	
	//Encontramos las compras con una fecha y hora en un rango determinado y el miembro e ítems de la compra asociados:
	public List<PurchaseDTO> findByDateTimeRange(LocalDateTime fromDateTime, LocalDateTime untilDateTime);
	
	//Encontramos las compras de un determinado miembro por su nombre de usuario:
	public List<PurchaseDTO> findByMember(String username);
	
	//Encontramos los nombres de usuario de los clientes que realizaron compras ordenados de forma alfabética:
	public List<String> getAllUsernames();
		
	//Obtener:
	
	//Obtener todas las compras:
	public List<PurchaseDTO> getAll();
	
	//Ordenar:
	
	//Ordenamos las compras por método de pago de manera alfabética:
	public List<PurchaseDTO> getAllInOrderAscByMethodOfPay();
	
	//Ordenamos las compras por método de pago de manera inversa al alfabeto:
	public List<PurchaseDTO> getAllInOrderDescByMethodOfPay();
	
	//Ordenamos las compras por nombre de usuario de manera alfabética:
	public List<PurchaseDTO> getAllInOrderAscByUserUsername();
	
	//Ordenamos las compras por nombre de usuario de manera inversa al alfabeto:
	public List<PurchaseDTO> getAllInOrderDescByUserUsername();
	
	//Ordenamos las compras por fecha y hora de manera ascendente:
	public List<PurchaseDTO> getAllInOrderAscByDateTime();
	
	//Ordenamos las compras por fecha y hora de manera descendente:
	public List<PurchaseDTO> getAllInOrderDescByDateTime();
	
	//Ordenamos las compras por fecha y hora de manera ascendente:
	public List<PurchaseDTO> inOrderAscByDateTime(List<PurchaseDTO> purchases);
	
	//Ordenamos las compras por fecha y hora de manera descendente:
	public List<PurchaseDTO> inOrderDescByDateTime(List<PurchaseDTO> purchases);
	
	//Ordenamos las compras por nombre de usuario de manera alfabética:
	public List<PurchaseDTO> inOrderAscByUsername(List<PurchaseDTO> purchases);
	
	//Ordenamos las compras por nombre de usuario de manera inversa al alfabeto:
	public List<PurchaseDTO> inOrderDescByUsername(List<PurchaseDTO> purchases);
	
	//Ordenamos las compras por método de pago de manera alfabética:
	public List<PurchaseDTO> inOrderAscByMethodOfPay(List<PurchaseDTO> purchases);
	
	//Ordenamos las compras por método de pago de manera inversa al alfabeto:
	public List<PurchaseDTO> inOrderDescByMethodOfPay(List<PurchaseDTO> purchases);
	
	//Agregar:
	
	//Agregamos una compra a la base de datos:
	public PurchaseDTO insert(PurchaseDTO purchaseDTO);
	
	//Filtrar:
	
	//Filtramos las compras por fecha:
	public List<PurchaseDTO> filterByDate(List<PurchaseDTO> purchases, LocalDate date);
	
	//Filtramos las compras por posteriores o iguales a una fecha:
	public List<PurchaseDTO> filterByFromDate(List<PurchaseDTO> purchases, LocalDate fromDate);
	
	//Filtramos las compras por anteriores o iguales a una fecha:
	public List<PurchaseDTO> filterByUntilDate(List<PurchaseDTO> purchases, LocalDate untilDate);
	
	//Filtramos las compras por un rango de fechas:
	public List<PurchaseDTO> filterByDateRange(List<PurchaseDTO> purchases, LocalDate fromDate, LocalDate untilDate);
	
	//Filtramos las compras por hora:
	public List<PurchaseDTO> filterByTime(List<PurchaseDTO> purchases, LocalTime time);
	
	//Filtramos las compras por posteriores o iguales a una hora:
	public List<PurchaseDTO> filterByFromTime(List<PurchaseDTO> purchases, LocalTime fromTime);
	
	//Filtramos las compras por anteriores o iguales a una hora:
	public List<PurchaseDTO> filterByUntilTime(List<PurchaseDTO> purchases, LocalTime untilTime);
	
	//Filtramos las compras por un rango de horas:
	public List<PurchaseDTO> filterByTimeRange(List<PurchaseDTO> purchases, LocalTime fromTime, LocalTime untilTime);
	
	//Filtramos las compras por nombre de usuario:
	public List<PurchaseDTO> filterByUsername(List<PurchaseDTO> purchases, String username);
	
	//Filtramos las compras por método de pago:
	public List<PurchaseDTO> filterByMethodOfPay(List<PurchaseDTO> purchases, String methodOfPay);
	
	//Verificación de valores de inputs tipo date y time:
	
	//Verificamos o corregimos los valores que llegan de los inputs tipo date y time para evitar la presencia de ',' en ellos:
	public String verifyOrCorrectValue(String value);
}
