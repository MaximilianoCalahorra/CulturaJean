package com.calahorra.culturaJean.services.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
	@Override
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
	
	//Aplicamos el ordenamiento elegido:
	public List<PurchaseDTO> applyOrder(List<PurchaseDTO> purchases, String order)
	{
		//Ordenamos el listado de compras/ventas resultante de los procesos anteriores en base al tipo de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByDateTime": purchases = inOrderAscByDateTime(purchases); break; //Ascendente por fecha y hora.
			case "orderDescByDateTime": purchases = inOrderDescByDateTime(purchases); break; //Descendente por fecha y hora.
			case "orderAscByUsername": purchases = inOrderAscByUsername(purchases); break; //Alfabéticamente por nombre de usuario.
			case "orderDescByUsername": purchases = inOrderDescByUsername(purchases); break; //Inverso al alfabeto por nombre de usuario.
			case "orderAscByMethodOfPay": purchases = inOrderAscByMethodOfPay(purchases); break; //Alfabéticamente por método de pago.
			case "orderDescByMethodOfPay": purchases = inOrderDescByMethodOfPay(purchases); break; //Inverso al alfabeto por método de pago.
			case "orderAscByPurchasePrice": purchases = inOrderAscByPurchasePrice(purchases); break; //Ascendente por precio de compra.
			case "orderDescByPurchasePrice": purchases = inOrderDescByPurchasePrice(purchases); break; //Descendente por precio de compra.
		}
		return purchases; //Retornamos el listado ordenado.
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
	
	//Aplicamos el filtro elegido de la sección fechas:
	@Override
	public List<PurchaseDTO> applyFilterTypeDate(String date, String fromDate, String untilDate, String rangeFromDate, String rangeUntilDate)
	{
		//Instanciamos una lista de compras/ventas para cargarla con las ventas filtradas y ordenadas:
		List<PurchaseDTO> purchases = new ArrayList<PurchaseDTO>(); 
		
		//Aplicamos el filtro de fecha que corresponda:
		if(!date.equals("") && fromDate.equals("") && untilDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por fecha en específico.
		{
			LocalDate dateObject = LocalDate.parse(date); //Convertimos la cadena a un objeto LocalDate.
			purchases = findByDate(dateObject); //Obtenemos las compras/ventas en esa fecha.
		}
		else if(!fromDate.equals("") && date.equals("") && untilDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por posteriores o iguales a una fecha.
		{
			LocalDate fromDateObject = LocalDate.parse(fromDate); //Convertimos la cadena a un objeto LocalDate.
			purchases = findByDateAfterThanOrEqual(fromDateObject); //Obtenemos las compras/ventas posteriores o iguales a esa fecha.
		}
				else if(!untilDate.equals("") && date.equals("") && fromDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por anteriores o iguales a una fecha.
		{
			LocalDate untilDateObject = LocalDate.parse(untilDate); //Convertimos la cadena a un objeto LocalDate.
			purchases = findByDateBeforeThanOrEqual(untilDateObject); //Obtenemos las compras/ventas anteriores o iguales a esa fecha.
		}
		else if(!rangeFromDate.equals("") && !rangeUntilDate.equals("") && fromDate.equals("") && untilDate.equals("") && date.equals("")) //Filtramos por un rango de fechas.
		{
			LocalDate rangeFromDateObject = LocalDate.parse(rangeFromDate); //Convertimos la cadena a un objeto LocalDate.
			LocalDate rangeUntilDateObject = LocalDate.parse(rangeUntilDate); //Convertimos la cadena a un objeto LocalDate.
			purchases = findByDateRange(rangeFromDateObject, rangeUntilDateObject); //Obtenemos las compras/ventas en ese rango de fechas.
		}
		else //Si no se aplicó ningún filtro de fechas:
		{
			purchases = getAll(); //Obtenemos todas las compras/ventas de la base de datos.
		}
		return purchases; //Retornamos el listado de compras/ventas filtrado por el criterio de fechas elegido.
	}
	
	//Aplicamos el filtro elegido de la sección fechas a un listado que le pasamos:
	@Override
	public List<PurchaseDTO> applyFilterTypeDateOnList(List<PurchaseDTO> purchases, String date, String fromDate, String untilDate, String rangeFromDate, String rangeUntilDate)
	{
		//Aplicamos el filtro de fecha que corresponda:
		if(!date.equals("") && fromDate.equals("") && untilDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por fecha en específico.
		{
			LocalDate dateObject = LocalDate.parse(date); //Convertimos la cadena a un objeto LocalDate.
			purchases = filterByDate(purchases, dateObject); //Nos quedamos con las compras/ventas en esa fecha.
		}
		else if(!fromDate.equals("") && date.equals("") && untilDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por posteriores o iguales a una fecha.
		{
			LocalDate fromDateObject = LocalDate.parse(fromDate); //Convertimos la cadena a un objeto LocalDate.
			purchases = filterByFromDate(purchases, fromDateObject); //Nos quedamos con las compras/ventas posteriores o iguales a esa fecha.
		}
		else if(!untilDate.equals("") && date.equals("") && fromDate.equals("") && rangeFromDate.equals("") && rangeUntilDate.equals("")) //Filtramos por anteriores o iguales a una fecha.
		{
			LocalDate untilDateObject = LocalDate.parse(untilDate); //Convertimos la cadena a un objeto LocalDate.
			purchases = filterByUntilDate(purchases, untilDateObject); //Nos quedamos con las compras/ventas anteriores o iguales a esa fecha.
		}
		else if(!rangeFromDate.equals("") && !rangeUntilDate.equals("") && fromDate.equals("") && untilDate.equals("") && date.equals("")) //Filtramos por un rango de fechas.
		{
			LocalDate rangeFromDateObject = LocalDate.parse(rangeFromDate); //Convertimos la cadena a un objeto LocalDate.
			LocalDate rangeUntilDateObject = LocalDate.parse(rangeUntilDate); //Convertimos la cadena a un objeto LocalDate.
			purchases = filterByDateRange(purchases, rangeFromDateObject, rangeUntilDateObject); //Nos quedamos con las compras/ventas en ese rango de fechas.
		}
		return purchases; //Retornamos el listado de compras/ventas filtrado por el criterio de fechas elegido.
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
	
	//Aplicamos el filtro elegido de la sección horas:
	@Override
	public List<PurchaseDTO> applyFilterTypeTime(List<PurchaseDTO> purchases, String fromTime, String untilTime, String rangeFromTime, String rangeUntilTime)
	{
		//Aplicamos el filtro de hora que corresponda:
		if(!fromTime.equals("") && untilTime.equals("") && rangeFromTime.equals("") && rangeUntilTime.equals("")) //Filtramos por posteriores o iguales a una hora.
		{
			LocalTime fromTimeObject = LocalTime.parse(fromTime); //Convertimos la cadena a un objeto LocalTime.
			purchases = filterByFromTime(purchases, fromTimeObject); //Nos quedamos las compras/ventas en esa hora o posteriores.
		}
		else if(!untilTime.equals("") && fromTime.equals("") && rangeFromTime.equals("") && rangeUntilTime.equals("")) //Filtramos por anteriores o iguales a una hora.
		{
			LocalTime untilTimeObject = LocalTime.parse(untilTime); //Convertimos la cadena a un objeto LocalTime.
			purchases = filterByUntilTime(purchases, untilTimeObject); //Nos quedamos las compras/ventas en esa hora o anteriores.
		}
		else if(!rangeFromTime.equals("") && !rangeUntilTime.equals("") && fromTime.equals("") && untilTime.equals("")) //Filtramos por un rango de horas.
		{
			LocalTime rangeFromTimeObject = LocalTime.parse(rangeFromTime); //Convertimos la cadena a un objeto LocalTime.
			LocalTime rangeUntilTimeObject = LocalTime.parse(rangeUntilTime); //Convertimos la cadena a un objeto LocalTime.
			purchases = filterByTimeRange(purchases, rangeFromTimeObject, rangeUntilTimeObject); //Nos quedamos con las compras/ventas en ese rango de horas.
		}
		return purchases; //Retornamos el listado de compras/ventas filtrado por el criterio de horas elegido.
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
	
	//Aplicamos el filtro elegido de la sección precio de la compra/venta:
	@Override
	public List<PurchaseDTO> applyFilterTypePurchasePrice(List<PurchaseDTO> purchases, String fromPurchasePrice, String untilPurchasePrice,
														  String rangeFromPurchasePrice, String rangeUntilPurchasePrice)
	{
		//Aplicamos filtros según corresponda por precio de la venta:
		if(!fromPurchasePrice.equals("") && untilPurchasePrice.equals("") && rangeFromPurchasePrice.equals("") && rangeUntilPurchasePrice.equals("")) //Filtramos por mayores o iguales a un precio.
		{
			float fromPurchasePriceNumber = Float.parseFloat(fromPurchasePrice); //Convertimos la cadena a float.
			purchases = filterByFromPurchasePrice(purchases, fromPurchasePriceNumber); //Nos quedamos con las compras/ventas con ese tipo de precio.
		}
		else if(!untilPurchasePrice.equals("") && fromPurchasePrice.equals("") && rangeFromPurchasePrice.equals("") && rangeUntilPurchasePrice.equals("")) //Filtramos por menores o iguales a un precio.
		{
			float untilPurchasePriceNumber = Float.parseFloat(untilPurchasePrice); //Convertimos la cadena a float.
			purchases = filterByUntilPurchasePrice(purchases, untilPurchasePriceNumber); //Nos quedamos con las compras/ventas con ese tipo de precio.
		}
		else if(!rangeFromPurchasePrice.equals("") && !rangeUntilPurchasePrice.equals("") && fromPurchasePrice.equals("") && untilPurchasePrice.equals("")) //Filtramos por un rango de precios.
		{
			float rangeFromPurchasePriceNumber = Float.parseFloat(rangeFromPurchasePrice); //Convertimos la cadena a float.
			float rangeUntilPurchasePriceNumber = Float.parseFloat(rangeUntilPurchasePrice); //Convertimos la cadena a float.
			purchases = filterByPurchasePriceRange(purchases, rangeFromPurchasePriceNumber, rangeUntilPurchasePriceNumber); //Nos quedamos con las compras/ventas con ese tipo de precio.
		}
		return purchases; //Retornamos el listado filtrado por el criterio elegido de precio de compra/venta.
	}
	
	//Verificar o corregir:
	
	//Verificamos o corregimos los valores que llegan de los inputs tipo date y time para evitar la presencia de ',' en ellos:
	@Override
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
