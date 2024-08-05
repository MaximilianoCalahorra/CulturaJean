package com.calahorra.culturaJean.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calahorra.culturaJean.entities.PurchaseItem;

///Interfaz IPurchaseItemRepository:
@Repository("purchaseItemRepository")
public interface IPurchaseItemRepository extends JpaRepository<PurchaseItem, Serializable>
{
	//Encontrar:
	
	//Encontramos el ítem de la compra con determinado id:
	public abstract PurchaseItem findByPurchaseItemId(int purchaseItemId);
	
	//Encontramos el ítem de la compra con determinado id y su producto asociado:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.product WHERE pi.purchaseItemId = (:purchaseItemId)")
	public abstract PurchaseItem findByPurchaseItemIdWithProduct(@Param("purchaseItemId")int purchaseItemId);
	
	//Encontramos el ítem de la compra con determinado id y su compra asociada:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.purchase WHERE pi.purchaseItemId = (:purchaseItemId)")
	public abstract PurchaseItem findByPurchaseItemIdWithPurchase(@Param("purchaseItemId")int purchaseItemId);
	
	//Encontramos el ítem de la compra con determinado id y su producto y compra asociados:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.product INNER JOIN FETCH pi.purchase WHERE pi.purchaseItemId = (:purchaseItemId)")
	public abstract PurchaseItem findByPurchaseItemIdWithProductAndPurchase(@Param("purchaseItemId")int purchaseItemId);	
	
	//Encontramos los ítem de compras de determinado producto:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.product p WHERE p.productId = (:productId)")
	public abstract List<PurchaseItem> findByProduct(@Param("productId")int productId);
	
	//Encontramos los ítem de compras de determinado producto con la compra asociada:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.purchase INNER JOIN FETCH pi.product p WHERE p.productId = (:productId)")
	public abstract List<PurchaseItem> findByProductWithPurchase(@Param("productId")int productId);
	
	//Encontramos los ítem de compras de determinada compra:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.purchase p WHERE p.purchaseId = (:purchaseId)")
	public abstract List<PurchaseItem> findByPurchase(@Param("purchaseId")int purchaseId);
	
	//Encontramos los ítem de compras de determinada compra con el producto asociado:
	@Query("SELECT pi FROM PurchaseItem pi INNER JOIN FETCH pi.product INNER JOIN FETCH pi.purchase p WHERE p.purchaseId = (:purchaseId)")
	public abstract List<PurchaseItem> findByPurchaseWithProduct(@Param("purchaseId")int purchaseId);
}
