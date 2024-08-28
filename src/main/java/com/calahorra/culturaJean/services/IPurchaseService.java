package com.calahorra.culturaJean.services;

import java.time.LocalDateTime;
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
	
	//Agregar:
	
	//Agregamos una compra a la base de datos:
	public PurchaseDTO insert(PurchaseDTO purchaseDTO);
}
