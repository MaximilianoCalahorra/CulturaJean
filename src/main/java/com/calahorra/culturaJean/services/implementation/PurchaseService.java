package com.calahorra.culturaJean.services.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.PurchaseDTO;
import com.calahorra.culturaJean.entities.Purchase;
import com.calahorra.culturaJean.entities.PurchaseItem;
import com.calahorra.culturaJean.repositories.IPurchaseRepository;
import com.calahorra.culturaJean.services.IPurchaseService;

///Clase PurchaseService:
@Service("purchaseService")
public class PurchaseService implements IPurchaseService
{
	//Atributos:
	private IPurchaseRepository purchaseRepository;
	private PurchaseItemService purchaseItemService;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public PurchaseService(IPurchaseRepository purchaseRepository, PurchaseItemService purchaseItemService) 
	{
		this.purchaseRepository = purchaseRepository;
		this.purchaseItemService = purchaseItemService;
	}
	
	//Encontrar:
	
	//Encontramos la compra con determinado id:
	@Override
	public Purchase findByPurchaseId(int purchaseId) 
	{
		return purchaseRepository.findByPurchaseId(purchaseId);
	}
	
	//Encontramos la compra con determinado id y el usuario e ítems de la compra asociados:
	@Override
	public Purchase findByPurchaseIdWithUserAndPurchaseItems(int purchaseId) 
	{
		return purchaseRepository.findByPurchaseIdWithUserAndPurchaseItems(purchaseId);
	}
	
	//Encontramos las compras con determinado método de pago y el usuario e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByMethodOfPay(String methodOfPay)
	{
		return purchaseRepository.findByMethodOfPay(methodOfPay) //Obtenemos las compras con ese método de pago como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con determinada fecha y hora y el usuario e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTime(LocalDateTime dateTime)
	{
		return purchaseRepository.findByDateTime(dateTime) //Obtenemos las compras con esa fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el usuario e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTimeBeforeThanOrEqual(LocalDateTime dateTime)
	{
		return purchaseRepository.findByDateTimeBeforeThanOrEqual(dateTime) //Obtenemos las compras con ese tipo de fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha y hora posterior o igual a una determinada y el usuario e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTimeAfterThanOrEqual(LocalDateTime dateTime)
	{
		return purchaseRepository.findByDateTimeAfterThanOrEqual(dateTime) //Obtenemos las compras con ese tipo de fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha y hora en un rango determinado y el usuario e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTimeRange(LocalDateTime fromDateTime, LocalDateTime untilDateTime)
	{
		return purchaseRepository.findByDateTimeRange(fromDateTime, untilDateTime) //Obtenemos las compras con ese tipo de fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras de un determinado usuario por su nombre de usuario:
	@Override
	public List<PurchaseDTO> findByUser(String username)
	{
		return purchaseRepository.findByUser(username) //Obtenemos las compras con ese nombre de usuario como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Obtener:
	
	//Obtener todas las compras:
	@Override
	public List<PurchaseDTO> getAll()
	{
		return purchaseRepository.findAll() //Obtenemos las compras como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordernar:
	
	//Ordenamos las compras por método de pago de manera alfabética:
	@Override
	public List<PurchaseDTO> getAllInOrderAscByMethodOfPay()
	{
		return purchaseRepository.getAllInOrderAscByMethodOfPay() //Obtenemos las compras ordenadas como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos las compras por método de pago de manera inversa al alfabeto:
	@Override
	public List<PurchaseDTO> getAllInOrderDescByMethodOfPay()
	{
		return purchaseRepository.getAllInOrderDescByMethodOfPay() //Obtenemos las compras ordenadas como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos las compras por nombre de usuario de manera alfabética:
	@Override
	public List<PurchaseDTO> getAllInOrderAscByUserUsername()
	{
		return purchaseRepository.getAllInOrderAscByUserUsername() //Obtenemos las compras ordenadas como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos las compras por nombre de usuario de manera inversa al alfabeto:
	@Override
	public List<PurchaseDTO> getAllInOrderDescByUserUsername()
	{
		return purchaseRepository.getAllInOrderDescByUserUsername() //Obtenemos las compras ordenadas como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos las compras por fecha y hora de manera ascendente:
	@Override
	public List<PurchaseDTO> getAllInOrderAscByDateTime()
	{
		return purchaseRepository.getAllInOrderAscByDateTime() //Obtenemos las compras ordenadas como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos las compras por fecha y hora de manera descendente:
	@Override
	public List<PurchaseDTO> getAllInOrderDescByDateTime()
	{
		return purchaseRepository.getAllInOrderDescByDateTime() //Obtenemos las compras ordenadas como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Agregar:
	
	//Agregamos una compra a la base de datos:
	@Override
	public PurchaseDTO insert(PurchaseDTO purchaseDTO) 
	{
		Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class); 
		for(PurchaseItem purchaseItem: purchase.getPurchaseItems()) 
		{
			purchaseItemService.insert(purchaseItem); //Inserto cada ítem de la compra en la base de datos.
		}
		return modelMapper.map(purchaseRepository.save(purchase), PurchaseDTO.class); //Inserto la compra en la base de datos y la
																					  //retorno como DTO.
	}
}
