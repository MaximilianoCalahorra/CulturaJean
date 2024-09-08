package com.calahorra.culturaJean.services.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
	
	//Encontramos la compra con determinado id y el miembro e ítems de la compra asociados:
	@Override
	public Purchase findByPurchaseIdWithMemberAndPurchaseItems(int purchaseId) 
	{
		return purchaseRepository.findByPurchaseIdWithMemberAndPurchaseItems(purchaseId);
	}
	
	//Encontramos las compras con determinado método de pago y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByMethodOfPay(String methodOfPay)
	{
		return purchaseRepository.findByMethodOfPay(methodOfPay) //Obtenemos las compras con ese método de pago como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con determinada fecha y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDate(LocalDate date)
	{
		return purchaseRepository.findByDate(date) //Obtenemos las compras con esa fecha como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateBeforeThanOrEqual(LocalDate date)
	{
		return purchaseRepository.findByDateBeforeThanOrEqual(date) //Obtenemos las compras con ese tipo de fecha como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateAfterThanOrEqual(LocalDate date)
	{
		return purchaseRepository.findByDateAfterThanOrEqual(date) //Obtenemos las compras con ese tipo de fecha como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha en un rango determinado y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateRange(LocalDate fromDate, LocalDate untilDate)
	{
		return purchaseRepository.findByDateRange(fromDate, untilDate) //Obtenemos las compras con ese tipo de fecha como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con determinada hora y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByTime(LocalTime time)
	{
		return purchaseRepository.findByTime(time) //Obtenemos las compras con esa hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByTimeBeforeThanOrEqual(LocalTime time)
	{
		return purchaseRepository.findByTimeBeforeThanOrEqual(time) //Obtenemos las compras con ese tipo de hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una hora posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByTimeAfterThanOrEqual(LocalTime time)
	{
		return purchaseRepository.findByTimeAfterThanOrEqual(time) //Obtenemos las compras con ese tipo de hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una hora en un rango determinado y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByTimeRange(LocalTime fromTime, LocalTime untilTime)
	{
		return purchaseRepository.findByTimeRange(fromTime, untilTime) //Obtenemos las compras con ese tipo de hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con determinada fecha y hora y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTime(LocalDateTime dateTime)
	{
		return purchaseRepository.findByDateTime(dateTime) //Obtenemos las compras con esa fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha y hora anterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTimeBeforeThanOrEqual(LocalDateTime dateTime)
	{
		return purchaseRepository.findByDateTimeBeforeThanOrEqual(dateTime) //Obtenemos las compras con ese tipo de fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha y hora posterior o igual a una determinada y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTimeAfterThanOrEqual(LocalDateTime dateTime)
	{
		return purchaseRepository.findByDateTimeAfterThanOrEqual(dateTime) //Obtenemos las compras con ese tipo de fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras con una fecha y hora en un rango determinado y el miembro e ítems de la compra asociados:
	@Override
	public List<PurchaseDTO> findByDateTimeRange(LocalDateTime fromDateTime, LocalDateTime untilDateTime)
	{
		return purchaseRepository.findByDateTimeRange(fromDateTime, untilDateTime) //Obtenemos las compras con ese tipo de fecha y hora como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos las compras de un determinado miembro por su nombre de usuario:
	@Override
	public List<PurchaseDTO> findByMember(String username)
	{
		return purchaseRepository.findByMember(username) //Obtenemos las compras con ese nombre de usuario como entidades.
				.stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los nombres de usuario de los clientes que realizaron compras ordenados de forma alfabética:
	public List<String> getAllUsernames()
	{
		return purchaseRepository.getAllUsernames(); //Retornamos el listado de nombres de usuario ordenado.
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
		
	//Ordenamos las compras por fecha y hora de forma ascendente:
	@Override
	public List<PurchaseDTO> inOrderAscByDateTime(List<PurchaseDTO> purchases)
	{
		Collections.sort(purchases, (p1, p2) -> p1.getDateTime().compareTo(p2.getDateTime()));
		return purchases;
	}
	
	//Ordenamos las compras por fecha y hora de forma descendente:
	@Override
	public List<PurchaseDTO> inOrderDescByDateTime(List<PurchaseDTO> purchases)
	{
		Collections.sort(purchases, (p1, p2) -> p2.getDateTime().compareTo(p1.getDateTime()));
		return purchases;
	}
	
	//Ordenamos las compras por nombre de usuario de forma alfabética:
	@Override
	public List<PurchaseDTO> inOrderAscByUsername(List<PurchaseDTO> purchases)
	{
		Collections.sort(purchases, (p1, p2) -> p1.getMember().getUsername().compareToIgnoreCase(p2.getMember().getUsername()));
		return purchases;
	}
	
	//Ordenamos las compras por nombre de usuario de forma inversa al alfabeto:
	@Override
	public List<PurchaseDTO> inOrderDescByUsername(List<PurchaseDTO> purchases)
	{
		Collections.sort(purchases, (p1, p2) -> p2.getMember().getUsername().compareToIgnoreCase(p1.getMember().getUsername()));
		return purchases;
	}
	
	//Ordenamos las compras por método de pago de forma alfabética:
	@Override
	public List<PurchaseDTO> inOrderAscByMethodOfPay(List<PurchaseDTO> purchases)
	{
		Collections.sort(purchases, (p1, p2) -> p1.getMethodOfPay().compareToIgnoreCase(p2.getMethodOfPay()));
		return purchases;
	}
	
	//Ordenamos las compras por método de pago de forma inversa al alfabeto:
	@Override
	public List<PurchaseDTO> inOrderDescByMethodOfPay(List<PurchaseDTO> purchases)
	{
		Collections.sort(purchases, (p1, p2) -> p2.getMethodOfPay().compareToIgnoreCase(p1.getMethodOfPay()));
		return purchases;
	}
	
	//Ordenamos las compras por el importe total de manera ascendente:
	@Override
	public List<PurchaseDTO> inOrderAscByPurchasePrice(List<PurchaseDTO> purchases)
	{
		purchases.sort(Comparator.comparingDouble(PurchaseDTO::calculateTotalSale));
		return purchases;
	}
	
	//Ordenamos las compras por el importe total de manera descendente:
	@Override
	public List<PurchaseDTO> inOrderDescByPurchasePrice(List<PurchaseDTO> purchases)
	{
		purchases.sort(Comparator.comparingDouble(PurchaseDTO::calculateTotalSale).reversed());
		return purchases;
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
	
	//Filtrar:
	
	//Filtramos las compras por nombre de usuario:
	@Override
	public List<PurchaseDTO> filterByUsername(List<PurchaseDTO> purchases, String username)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (!purchase.getMember().getUsername().equals(username)) 
			{
				iterator.remove(); //En caso de que el miembro asociado no tenga el mismo nombre de usuario del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
		
	//Filtramos las compras por método de pago:
	@Override
	public List<PurchaseDTO> filterByMethodOfPay(List<PurchaseDTO> purchases, String methodOfPay)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (!purchase.getMethodOfPay().equals(methodOfPay)) 
			{
				iterator.remove(); //En caso de que no tenga el mismo medio de pago del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por fecha:
	@Override
	public List<PurchaseDTO> filterByDate(List<PurchaseDTO> purchases, LocalDate date)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (!purchase.getDateTime().toLocalDate().equals(date)) 
			{
				iterator.remove(); //En caso de que no tenga la misma fecha del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por posteriores o iguales a una fecha:
	@Override
	public List<PurchaseDTO> filterByFromDate(List<PurchaseDTO> purchases, LocalDate fromDate)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.getDateTime().toLocalDate().isBefore(fromDate)) 
			{
				iterator.remove(); //En caso de que no tenga una fecha posterior o igual a la del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por anteriores o iguales a una fecha:
	@Override
	public List<PurchaseDTO> filterByUntilDate(List<PurchaseDTO> purchases, LocalDate untilDate)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.getDateTime().toLocalDate().isAfter(untilDate)) 
			{
				iterator.remove(); //En caso de que no tenga una fecha anterior o igual a la del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por un rango de fechas:
	@Override
	public List<PurchaseDTO> filterByDateRange(List<PurchaseDTO> purchases, LocalDate fromDate, LocalDate untilDate)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.getDateTime().toLocalDate().isBefore(fromDate) || purchase.getDateTime().toLocalDate().isAfter(untilDate)) 
			{
				iterator.remove(); //En caso de que no tenga una fecha entre el rango de las del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por hora:
	@Override
	public List<PurchaseDTO> filterByTime(List<PurchaseDTO> purchases, LocalTime time)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (!purchase.getDateTime().toLocalTime().equals(time)) 
			{
				iterator.remove(); //En caso de que no tenga la misma hora del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por posteriores o iguales a una hora:
	@Override
	public List<PurchaseDTO> filterByFromTime(List<PurchaseDTO> purchases, LocalTime fromTime)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.getDateTime().toLocalTime().isBefore(fromTime)) 
			{
				iterator.remove(); //En caso de que no tenga una hora posterior o igual a la del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por anteriores o iguales a una hora:
	@Override
	public List<PurchaseDTO> filterByUntilTime(List<PurchaseDTO> purchases, LocalTime untilTime)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.getDateTime().toLocalTime().isAfter(untilTime)) 
			{
				iterator.remove(); //En caso de que no tenga una fecha anterior o igual a la del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por un rango de horas:
	@Override
	public List<PurchaseDTO> filterByTimeRange(List<PurchaseDTO> purchases, LocalTime fromTime, LocalTime untilTime)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.getDateTime().toLocalTime().isBefore(fromTime) || purchase.getDateTime().toLocalTime().isAfter(untilTime)) 
			{
				iterator.remove(); //En caso de que no tenga una fecha entre el rango de las del filtro, la removemos del listado.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Filtramos las compras por el importe de la misma mayor o igual a uno determinado:
	@Override
	public List<PurchaseDTO> filterByFromPurchasePrice(List<PurchaseDTO> purchases, float fromPurchasePrice)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.calculateTotalSale() < fromPurchasePrice) 
			{
				iterator.remove(); //En caso de que no tenga un precio mayor o igual al mínimo del filtro, la removemos.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
		
	//Filtramos las compras por el importe de la misma menor o igual a uno determinado:
	@Override
	public List<PurchaseDTO> filterByUntilPurchasePrice(List<PurchaseDTO> purchases, float untilPurchasePrice)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.calculateTotalSale() > untilPurchasePrice) 
			{
				iterator.remove(); //En caso de que no tenga un precio menor o igual al mínimo del filtro, la removemos.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
		
	//Filtramos las compras por el importe de la misma entre un rango determinado:
	@Override
	public List<PurchaseDTO> filterByPurchasePriceRange(List<PurchaseDTO> purchases, float rangeFromPurchasePrice, float rangeUntilPurchasePrice)
	{
		Iterator<PurchaseDTO> iterator = purchases.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya una compra por analizar:
		while(iterator.hasNext())
		{
			PurchaseDTO purchase = iterator.next(); //Obtenemos esa compra.
			if (purchase.calculateTotalSale() < rangeFromPurchasePrice || purchase.calculateTotalSale() > rangeUntilPurchasePrice) 
			{
				iterator.remove(); //En caso de que no tenga un precio entre las del rango del filtro, la removemos.
	        }
	    }
		return purchases; //Retornamos el listado de compras filtrado.
	}
	
	//Verificar o corregir:
	
	//Verificamos o corregimos los valores que llegan de los inputs tipo date y time para evitar la presencia de ',' en ellos:
	public String verifyOrCorrectValue(String value) 
	{
		//La clave está en saber si contiene una ',' o no:
		if(value.contains(",")) //En caso de que haya:
		{
			int commaIndex = value.indexOf(","); //Obtenemos su posición en la cadena de texto.
			
			//Si se encuentra al principio:
			if(commaIndex == 0) 
			{
				value = value.substring(1); //El valor correcto es desde el caracter que ocupa la posición 1 en adelante.
			}
			else //Por el contrario, si la coma se encuentra al final de la cadena:
			{
				value = value.substring(0, commaIndex); //El valor correcto es desde el primer caracter hasta la posición anterior a la ',' inclusive.
			}
		}
		return value; //Retornamos el valor verificado/corregido.
	}
}
