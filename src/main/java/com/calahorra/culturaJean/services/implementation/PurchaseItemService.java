package com.calahorra.culturaJean.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.PurchaseItemDTO;
import com.calahorra.culturaJean.entities.PurchaseItem;
import com.calahorra.culturaJean.repositories.IPurchaseItemRepository;
import com.calahorra.culturaJean.services.IPurchaseItemService;

///Clase PurchaseItemService:
@Service("purchaseItemService")
public class PurchaseItemService implements IPurchaseItemService
{
	//Atributos:
	private IPurchaseItemRepository purchaseItemRepository;
	private ModelMapper modelMapper;
	
	//Constructor:
	public PurchaseItemService(IPurchaseItemRepository purchaseItemRepository) 
	{
		this.purchaseItemRepository = purchaseItemRepository;
	}
	
	//Encontrar:
	
	//Encontramos el ítem de la compra con determinado id:
	@Override
	public PurchaseItem findByPurchaseItemId(int purchaseItemId) 
	{
		return purchaseItemRepository.findByPurchaseItemId(purchaseItemId);
	}
	
	//Encontramos el ítem de la compra con determinado id y su producto asociado:
	@Override
	public PurchaseItem findByPurchaseItemIdWithProduct(int purchaseItemId) 
	{
		return purchaseItemRepository.findByPurchaseItemIdWithProduct(purchaseItemId);
	}
	
	//Encontramos el ítem de la compra con determinado id y su compra asociada:
	@Override
	public PurchaseItem findByPurchaseItemIdWithPurchase(int purchaseItemId) 
	{
		return purchaseItemRepository.findByPurchaseItemIdWithPurchase(purchaseItemId);
	}
	
	//Encontramos el ítem de la compra con determinado id y su producto y compra asociados:
	@Override
	public PurchaseItem findByPurchaseItemIdWithProductAndPurchase(int purchaseItemId) 
	{
		return purchaseItemRepository.findByPurchaseItemIdWithProductAndPurchase(purchaseItemId);
	}
	
	//Encontramos los ítem de compras de determinado producto con la compra asociada:
	@Override
	public List<PurchaseItemDTO> findByProduct(int productId)
	{
		return purchaseItemRepository.findByProduct(productId) //Obtenemos los ítems de compra de ese producto como entidades.
				.stream()
				.map(purchaseItem -> modelMapper.map(purchaseItem, PurchaseItemDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los ítem de compras de determinado producto con la compra asociada:
	@Override
	public List<PurchaseItem> findByProductWithPurchase(int productId)
	{
		return purchaseItemRepository.findByProductWithPurchase(productId);
	}
	
	//Encontramos lo ítem de compras de determinada compra:
	@Override
	public List<PurchaseItem> findByPurchase(int purchaseId)
	{
		return purchaseItemRepository.findByPurchase(purchaseId);
	}
	
	//Encontramos los ítem de compras de determinada compra con el producto asociado:
	@Override
	public List<PurchaseItemDTO> findByPurchaseWithProduct(int purchaseId)
	{
		return purchaseItemRepository.findByPurchaseWithProduct(purchaseId) //Obtenemos los ítems de compra de esa compra como entidades.
				.stream()
				.map(purchaseItem -> modelMapper.map(purchaseItem, PurchaseItemDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtener:
	
	//Obtenemos todos los ítems de compras:
	@Override
	public List<PurchaseItem> getAll()
	{
		return purchaseItemRepository.findAll();
	}
	
	//Agregar:
	
	//Agregamos un ítem de compra a la base de datos:
	@Override
	public PurchaseItem insert(PurchaseItem purchaseItem) 
	{
		return purchaseItemRepository.save(purchaseItem);
	}
	
	//Calcular:
	
	//Calculamos el subtotal del ítem de la compra:
	@Override
	public float calculateSubtotal(int purchaseItemId) 
	{
		PurchaseItemDTO purchaseItem = modelMapper.map(findByPurchaseItemIdWithProduct(purchaseItemId), PurchaseItemDTO.class);
		return purchaseItem.getAmount() * purchaseItem.getProduct().getSalePrice();
	}
}
