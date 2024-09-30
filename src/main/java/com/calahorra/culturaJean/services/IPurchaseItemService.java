package com.calahorra.culturaJean.services;

import java.util.List;

import com.calahorra.culturaJean.dtos.PurchaseItemDTO;
import com.calahorra.culturaJean.entities.PurchaseItem;

///Interfaz IPurchaseItemService:
public interface IPurchaseItemService
{
	//Encontrar:
	
	//Encontramos el ítem de la compra con determinado id:
	public PurchaseItem findByPurchaseItemId(int purchaseItemId);
	
	//Encontramos el ítem de la compra con determinado id y su producto asociado:
	public PurchaseItem findByPurchaseItemIdWithProduct(int purchaseItemId);
	
	//Encontramos el ítem de la compra con determinado id y su compra asociada:
	public PurchaseItem findByPurchaseItemIdWithPurchase(int purchaseItemId);
	
	//Encontramos el ítem de la compra con determinado id y su producto y compra asociados:
	public PurchaseItem findByPurchaseItemIdWithProductAndPurchase(int purchaseItemId);	
	
	//Encontramos los ítem de compras de determinado producto:
	public List<PurchaseItemDTO> findByProduct(int productId);
	
	//Encontramos los ítem de compras de determinado producto con la compra asociada:
	public List<PurchaseItem> findByProductWithPurchase(int productId);
	
	//Encontramos los ítem de compras de determinada compra:
	public List<PurchaseItem> findByPurchase(int purchaseId);
	
	//Encontramos los ítem de compras de determinada compra con el producto asociado:
	public List<PurchaseItemDTO> findByPurchaseWithProduct(int purchaseId);
	
	//Obtener:
	
	//Obtenemos todos los ítems de compras:
	public List<PurchaseItem> getAll();
	
	//Agregar:
	
	//Agregamos un ítem de compra a la base de datos:
	public PurchaseItemDTO insert(PurchaseItem purchaseItem);
}
