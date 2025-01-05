package com.calahorra.culturaJean.services.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.calahorra.culturaJean.dtos.FiltersOptionsSupplyOrderDTO;
import com.calahorra.culturaJean.dtos.PaginatedSupplyOrderDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderDTO;
import com.calahorra.culturaJean.dtos.SupplyOrderFiltersDataDTO;
import com.calahorra.culturaJean.entities.SupplyOrder;
import com.calahorra.culturaJean.repositories.ISupplyOrderRepository;
import com.calahorra.culturaJean.repositories.custom.ICustomSupplyOrderRepository;
import com.calahorra.culturaJean.services.ISupplyOrderService;
import com.calahorra.culturaJean.services.IUtilsService;

///Clase SupplyOrderService:
@Service("supplyOrderService")
public class SupplyOrderService implements ISupplyOrderService
{
	//Atributos:
	private ISupplyOrderRepository supplyOrderRepository;
	private ICustomSupplyOrderRepository customSupplyOrderRepository;
	private IUtilsService utilsService;
	private ModelMapper modelMapper = new ModelMapper();
	
	//Constructor:
	public SupplyOrderService(ISupplyOrderRepository supplyOrderRepository, ICustomSupplyOrderRepository customSupplyOrderRepository,
							  IUtilsService utilsService) 
	{
		this.supplyOrderRepository = supplyOrderRepository;
		this.customSupplyOrderRepository = customSupplyOrderRepository;
		this.utilsService = utilsService;
	}
	
	//Encontrar:
	
	//Encontramos el pedido de aprovisionamiento con determinado id:
	@Override
	public SupplyOrder findBySupplyOrderId(int supplyOrderId) 
	{
		return supplyOrderRepository.findBySupplyOrderId(supplyOrderId);
	}
	
	//Encontramos el pedido de aprovisionamiento con determinado id y su producto, miembro y proveedor asociados:
	@Override
	public SupplyOrderDTO findBySupplyOrderIdWithProductAndMemberAndSupplier(int supplyOrderId) 
	{
		return modelMapper.map(supplyOrderRepository.findBySupplyOrderIdWithProductAndMemberAndSupplier(supplyOrderId), SupplyOrderDTO.class);
	}
	
	//Encontramos los pedidos de aprovisionamiento de un producto determinado por su código con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByProductCode(String code)
	{
		return supplyOrderRepository.findByProductCode(code) //Obtenemos los pedidos de aprovisionamiento de ese producto como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de un proveedor determinado por su nombre con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findBySupplierName(String name)
	{
		return supplyOrderRepository.findBySupplierName(name) //Obtenemos los pedidos de aprovisionamiento de ese proveedor como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad determinada con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmount(int amount)
	{
		return supplyOrderRepository.findByAmount(amount) //Obtenemos los pedidos de aprovisionamiento de esa cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad menor o igual a una determinada con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmountLessThanOrEqualTo(int amount)
	{
		return supplyOrderRepository.findByAmountLessThanOrEqualTo(amount) //Obtenemos los pedidos de aprovisionamiento de ese tipo de cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad mayor o igual a una determinada con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmountGreaterThanOrEqualTo(int amount)
	{
		return supplyOrderRepository.findByAmountGreaterThanOrEqualTo(amount) //Obtenemos los pedidos de aprovisionamiento de ese tipo de cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de una cantidad dentro de un intervalo con los objetos asociados:
	@Override
	public List<SupplyOrderDTO> findByAmountRange(int minimumAmount, int maximumAmount)
	{
		return supplyOrderRepository.findByAmountRange(minimumAmount, maximumAmount) //Obtenemos los pedidos de aprovisionamiento de ese tipo de cantidad como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento entregados/no entregados:
	@Override
	public List<SupplyOrderDTO> findByDelivered(boolean delivered)
	{
		return supplyOrderRepository.findByDelivered(delivered) //Obtenemos los pedidos de aprovisionamiento en ese estado como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos los pedidos de aprovisionamiento de determinado administrador por su nombre de usuario:
	@Override
	public List<SupplyOrderDTO> findByMember(String username)
	{
		return supplyOrderRepository.findByMember(username) //Obtenemos los pedidos de aprovisionamiento de ese miembro como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Encontramos un ejemplar de cada código de producto de los cuales hay pedidos de aprovisionamiento:
	@Override
	public List<String> findUniqueEachProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		List<String> productCodes = new ArrayList<String>(); //Definimos un listado donde se guardarán los códigos de producto.
		
		//Analizamos cada pedido de aprovisionamiento para saber si su código de producto se encuentra en el listado:
		for(SupplyOrderDTO supplyOrder: supplyOrders) 
		{
			String productCode = supplyOrder.getProduct().getCode(); //Obtenemos el código del producto.
			
			//Si el código no está en el listado:
			if(!productCodes.contains(productCode)) 
			{
				productCodes.add(productCode); //Agregamos el código.
			}
		}
		
		//Ordenamos el listado de códigos de producto de forma alfabética:
		productCodes.sort(null);
		
		return productCodes; //Retornamos el listado de códigos de producto.
	}
			
	//Encontramos un ejemplar de cada nombre de proveedor de los cuales hay pedidos de aprovisionamiento:
	@Override
	public List<String> findUniqueEachSupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		List<String> supplierNames = new ArrayList<String>(); //Definimos un listado donde se guardarán los nombres de proveedor.
		
		//Analizamos cada pedido de aprovisionamiento para saber si su nombre de proveedor se encuentra en el listado:
		for(SupplyOrderDTO supplyOrder: supplyOrders) 
		{
			String supplierName = supplyOrder.getSupplier().getName(); //Obtenemos el nombre del proveedor.
			
			//Si el nombre no está en el listado:
			if(!supplierNames.contains(supplierName)) 
			{
				supplierNames.add(supplierName); //Agregamos el nombre.
			}
		}
		
		//Ordenamos el listado de nombres de proveedor de forma alfabética:
		supplierNames.sort(null);
		
		return supplierNames; //Retornamos el listado de nombres de proveedor.
	}
	
	//Encontramos un ejemplar de cada nombre de usuario de los administradores de los cuales hay pedidos de aprovisionamiento:
	@Override
	public List<String> findUniqueEachAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		List<String> adminUsernames = new ArrayList<String>(); //Definimos un listado donde se guardarán los nombres de usuari de los administradores.
		
		//Analizamos cada pedido de aprovisionamiento para saber si su nombre de usuario del administrador se encuentra en el listado:
		for(SupplyOrderDTO supplyOrder: supplyOrders) 
		{
			String adminUsername = supplyOrder.getMember().getUsername(); //Obtenemos el nombre de usuario del administrador.
			
			//Si el nombre de usuario no está en el listado:
			if(!adminUsernames.contains(adminUsername)) 
			{
				adminUsernames.add(adminUsername); //Agregamos el nombre de usuario.
			}
		}
		
		//Ordenamos el listado de nombres de usuario de administrador de forma alfabética:
		adminUsernames.sort(null);
		
		return adminUsernames; //Retornamos el listado de nombres de usuario de los administradores.
	}
	
	//Obtener:
	
	//Obtenemos todos los pedidos de aprovisionamiento:
	@Override
	public List<SupplyOrderDTO> getAll()
	{
		return supplyOrderRepository.findAll()
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.;
	}
	
	//Obtenemos los pedidos de aprovisionamiento filtrados de una página:
	@Override
	public PaginatedSupplyOrderDTO getFilteredSupplyOrders(@Param("filters")SupplyOrderFiltersDataDTO filters, int page, int size) 
	{
		//Instanciamos un Pageable con la página y la cantidad de elementos a traer para hacer la query:
        Pageable pageable = PageRequest.of(page, size);

        //Adaptamos los filtros para poder hacer la consulta:
        List<String> productCodes = utilsService.cleanFilter(filters.getProductCodes());
        List<String> supplierNames = utilsService.cleanFilter(filters.getSupplierNames());
        List<String> adminUsernames = utilsService.cleanFilter(filters.getAdminUsernames());
        Integer amount = utilsService.convertStringFilterToInteger(filters.getAmount());
        Integer fromAmount = utilsService.convertStringFilterToInteger(filters.getFromAmount());
        Integer untilAmount = utilsService.convertStringFilterToInteger(filters.getUntilAmount());
        Integer rangeFromAmount = utilsService.convertStringFilterToInteger(filters.getRangeFromAmount());
        Integer rangeUntilAmount = utilsService.convertStringFilterToInteger(filters.getRangeUntilAmount());
        Boolean delivered = utilsService.convertStringFilterToBoolean(filters.getDelivered());
        
        //Obtenemos el criterio de ordenamiento:
        String sort = filters.getOrder();
        
        //Definimos el principio de la query para obtener los pedidos de aprovisionamiento:
        String findSOQueryBase = """
    	SELECT so.*
	    FROM supply_order so
	    INNER JOIN product p ON so.product_id = p.product_id
	    INNER JOIN supplier s ON so.supplier_id = s.supplier_id
	    INNER JOIN member m ON so.member_id = m.member_id
	    WHERE 1=1	
    	""";
        
        //Definimos el prinicipio de la query para obtener la cantidad de pedidos de aprovisionamiento obtenidos con la query anterior:
        String countSOQueryBase = """
    	SELECT COUNT(*)
	    FROM supply_order so
	    INNER JOIN product p ON so.product_id = p.product_id
	    INNER JOIN supplier s ON so.supplier_id = s.supplier_id
	    INNER JOIN member m ON so.member_id = m.member_id
	    WHERE 1=1	
    	""";
        
        //Obtenemos la página de pedidos de aprovisionamiento según los filtros y el criterio de ordenamiento:
        Page<SupplyOrder> supplyOrderPage = customSupplyOrderRepository.findFilteredSupplyOrders(productCodes, supplierNames, 
        																					     adminUsernames, amount, fromAmount, 
        																					     untilAmount, rangeFromAmount, 
        																					     rangeUntilAmount, delivered, sort, 
        																					     pageable, findSOQueryBase, 
        																					     countSOQueryBase);

        //Definimos el principio de la query para obtener las opciones de los filtros de los pedidos de aprovisionamiento resultantes:
        String findOptionsQueryBase = """
	    SELECT 
	        ARRAY_AGG(DISTINCT p.code)::TEXT[] AS productCodes,
	        ARRAY_AGG(DISTINCT s.name)::TEXT[] AS supplierNames,
	        ARRAY_AGG(DISTINCT m.username)::TEXT[] AS adminUsernames
	    FROM supply_order so
	    INNER JOIN product p ON so.product_id = p.product_id
	    INNER JOIN supplier s ON so.supplier_id = s.supplier_id
	    INNER JOIN member m ON so.member_id = m.member_id
	    WHERE 1=1		
		""";
        
        //Obtenemos todas las opciones de cada sección de filtro según la configuración de filtros aplicada:
        List<Map<String, Object>> results = customSupplyOrderRepository.findFiltersOptions(productCodes, supplierNames, adminUsernames,
				   																		   amount, fromAmount, untilAmount, 
				   																		   rangeFromAmount, rangeUntilAmount, delivered,
				   																		   findOptionsQueryBase); 
        
        //Desglosamos las opciones y las asignamos a cada parte de nuestro DTO específico de opciones de filtros de pedidos de aprovisionamiento:
        FiltersOptionsSupplyOrderDTO filtersOptionsDTO = new FiltersOptionsSupplyOrderDTO();
        for(Map<String, Object> result : results) 
        { 
        	filtersOptionsDTO.setProductCodes(utilsService.convertPostgresArrayToList((String[]) result.get("productCodes"))); //Códigos de producto.
        	filtersOptionsDTO.setSupplierNames(utilsService.convertPostgresArrayToList((String[]) result.get("supplierNames"))); //Nombres de proveedor.
        	filtersOptionsDTO.setAdminUsernames(utilsService.convertPostgresArrayToList((String[]) result.get("adminUsernames"))); //Usernames de administrador.
        }
        
        //Construimos el objeto paginado con su información:
        PaginatedSupplyOrderDTO paginatedDTO = new PaginatedSupplyOrderDTO();
        paginatedDTO.setSupplyOrders(supplyOrderPage.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)).getContent()); //Pedidos de aprovisionamiento.
        paginatedDTO.setTotalPages(supplyOrderPage.getTotalPages()); //Cantidad de páginas.
        paginatedDTO.setTotalElements(supplyOrderPage.getTotalElements()); //Cantidad de pedidos de aprovisionamiento.
        paginatedDTO.setFiltersOptions(filtersOptionsDTO); //Opciones de cada sección de filtro.
        
        return paginatedDTO; //Retornamos el objeto paginado.
	}
	
	//Obtenemos los pedidos de aprovisionamiento que pueden dar de alta un lote filtrados de una página:
	@Override
	public PaginatedSupplyOrderDTO getFilteredSupplyOrdersToLots(@Param("filters")SupplyOrderFiltersDataDTO filters, int page, int size) 
	{
		//Instanciamos un Pageable con la página y la cantidad de elementos a traer para hacer la query:
		Pageable pageable = PageRequest.of(page, size);
		
		//Adaptamos los filtros para poder hacer la consulta:
		List<String> productCodes = utilsService.cleanFilter(filters.getProductCodes());
		List<String> supplierNames = utilsService.cleanFilter(filters.getSupplierNames());
		List<String> adminUsernames = utilsService.cleanFilter(filters.getAdminUsernames());
		Integer amount = utilsService.convertStringFilterToInteger(filters.getAmount());
		Integer fromAmount = utilsService.convertStringFilterToInteger(filters.getFromAmount());
		Integer untilAmount = utilsService.convertStringFilterToInteger(filters.getUntilAmount());
		Integer rangeFromAmount = utilsService.convertStringFilterToInteger(filters.getRangeFromAmount());
		Integer rangeUntilAmount = utilsService.convertStringFilterToInteger(filters.getRangeUntilAmount());
		Boolean delivered = utilsService.convertStringFilterToBoolean(filters.getDelivered());
		
		//Obtenemos el criterio de ordenamiento:
		String sort = filters.getOrder();
		
		//Definimos el principio de la query para obtener los pedidos de aprovisionamiento:
		String findSOQueryBase = """
    	SELECT so.*
	    FROM supply_order so
	    INNER JOIN product p ON so.product_id = p.product_id
	    INNER JOIN supplier s ON so.supplier_id = s.supplier_id
	    INNER JOIN member m ON so.member_id = m.member_id
	    LEFT JOIN lot l ON so.supply_order_id = l.supply_order_id
	    WHERE l.supply_order_id IS NULL
    	""";
		
		//Definimos el prinicipio de la query para obtener la cantidad de pedidos de aprovisionamiento obtenidos con la query anterior:
		String countSOQueryBase = """
    	SELECT COUNT(*)
	    FROM supply_order so
	    INNER JOIN product p ON so.product_id = p.product_id
	    INNER JOIN supplier s ON so.supplier_id = s.supplier_id
	    INNER JOIN member m ON so.member_id = m.member_id
	    LEFT JOIN lot l ON so.supply_order_id = l.supply_order_id
	    WHERE l.supply_order_id IS NULL	
    	""";
		
		//Obtenemos la página de pedidos de aprovisionamiento según los filtros y el criterio de ordenamiento:
		Page<SupplyOrder> supplyOrderPage = customSupplyOrderRepository.findFilteredSupplyOrders(productCodes, supplierNames, 
																								 adminUsernames, amount, fromAmount, 
																								 untilAmount, rangeFromAmount, 
																								 rangeUntilAmount, delivered, sort, 
																								 pageable, findSOQueryBase, 
																								 countSOQueryBase);
		
		//Definimos el principio de la query para obtener las opciones de los filtros de los pedidos de aprovisionamiento resultantes:
		String findOptionsQueryBase = """
	    SELECT 
	        ARRAY_AGG(DISTINCT p.code)::TEXT[] AS productCodes,
	        ARRAY_AGG(DISTINCT s.name)::TEXT[] AS supplierNames,
	        ARRAY_AGG(DISTINCT m.username)::TEXT[] AS adminUsernames
	    FROM supply_order so
	    INNER JOIN product p ON so.product_id = p.product_id
	    INNER JOIN supplier s ON so.supplier_id = s.supplier_id
	    INNER JOIN member m ON so.member_id = m.member_id
	    LEFT JOIN lot l ON so.supply_order_id = l.supply_order_id
	    WHERE l.supply_order_id IS NULL		
		""";
		
		//Obtenemos todas las opciones de cada sección de filtro según la configuración de filtros aplicada:
		List<Map<String, Object>> results = customSupplyOrderRepository.findFiltersOptions(productCodes, supplierNames, adminUsernames,
																						   amount, fromAmount, untilAmount, 
																						   rangeFromAmount, rangeUntilAmount, delivered,
																						   findOptionsQueryBase); 
		
		//Desglosamos las opciones y las asignamos a cada parte de nuestro DTO específico de opciones de filtros de pedidos de aprovisionamiento:
		FiltersOptionsSupplyOrderDTO filtersOptionsDTO = new FiltersOptionsSupplyOrderDTO();
		for(Map<String, Object> result : results) 
		{ 
			filtersOptionsDTO.setProductCodes(utilsService.convertPostgresArrayToList((String[]) result.get("productCodes"))); //Códigos de producto.
			filtersOptionsDTO.setSupplierNames(utilsService.convertPostgresArrayToList((String[]) result.get("supplierNames"))); //Nombres de proveedor.
			filtersOptionsDTO.setAdminUsernames(utilsService.convertPostgresArrayToList((String[]) result.get("adminUsernames"))); //Usernames de administrador.
		}
		
		//Construimos el objeto paginado con su información:
		PaginatedSupplyOrderDTO paginatedDTO = new PaginatedSupplyOrderDTO();
		paginatedDTO.setSupplyOrders(supplyOrderPage.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)).getContent()); //Pedidos de aprovisionamiento.
		paginatedDTO.setTotalPages(supplyOrderPage.getTotalPages()); //Cantidad de páginas.
		paginatedDTO.setTotalElements(supplyOrderPage.getTotalElements()); //Cantidad de pedidos de aprovisionamiento.
		paginatedDTO.setFiltersOptions(filtersOptionsDTO); //Opciones de cada sección de filtro.
		
		return paginatedDTO; //Retornamos el objeto paginado.
	}
	
	//Ordenar:
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma ascendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderAscByProductCode()
	{
		return supplyOrderRepository.getAllInOrderAscByProductCode() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por el código del producto asociado de forma descendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderDescByProductCode()
	{
		return supplyOrderRepository.getAllInOrderDescByProductCode() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma alfabética:
	@Override
	public List<SupplyOrderDTO> getAllInOrderAscBySupplierName()
	{
		return supplyOrderRepository.getAllInOrderAscBySupplierName() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por el nombre del proveedor asociado de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> getAllInOrderDescBySupplierName()
	{
		return supplyOrderRepository.getAllInOrderDescBySupplierName() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma ascendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderAscByAmount()
	{
		return supplyOrderRepository.getAllInOrderAscByAmount() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos los pedidos de aprovisionamiento por la cantidad solicitada de forma descendente:
	@Override
	public List<SupplyOrderDTO> getAllInOrderDescByAmount()
	{
		return supplyOrderRepository.getAllInOrderDescByAmount() //Obtenemos los pedidos de aprovisionamiento ordenados como entidades.
				.stream()
				.map(supplyOrder -> modelMapper.map(supplyOrder, SupplyOrderDTO.class)) //Convertimos cada entidad en un DTO.
				.collect(Collectors.toList()); //Almacenamos cada DTO en una lista y la retornamos.
	}
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma alfabética:
	@Override
	public List<SupplyOrderDTO> inOrderAscByProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getProduct().getCode().compareToIgnoreCase(so2.getProduct().getCode()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el código del producto de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> inOrderDescByProductCode(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getProduct().getCode().compareToIgnoreCase(so1.getProduct().getCode()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma alfabética:
	@Override
	public List<SupplyOrderDTO> inOrderAscBySupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getSupplier().getName().compareToIgnoreCase(so2.getSupplier().getName()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre del proveedor de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> inOrderDescBySupplierName(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getSupplier().getName().compareToIgnoreCase(so1.getSupplier().getName()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
	
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma alfabética:
	@Override
	public List<SupplyOrderDTO> inOrderAscByAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so1.getMember().getUsername().compareToIgnoreCase(so2.getMember().getUsername()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por el nombre de usuario del administrador de forma inversa al alfabeto:
	@Override
	public List<SupplyOrderDTO> inOrderDescByAdminUsername(List<SupplyOrderDTO> supplyOrders)
	{
		Collections.sort(supplyOrders, (so1, so2) -> so2.getMember().getUsername().compareToIgnoreCase(so1.getMember().getUsername()));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma ascendente:
	@Override
	public List<SupplyOrderDTO> inOrderAscByAmount(List<SupplyOrderDTO> supplyOrders)
	{
		supplyOrders.sort(Comparator.comparingInt(SupplyOrderDTO::getAmount));
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Ordenamos el listado de pedidos de aprovisionamiento por la cantidad de forma descendente:
	@Override
	public List<SupplyOrderDTO> inOrderDescByAmount(List<SupplyOrderDTO> supplyOrders)
	{
		supplyOrders.sort(Comparator.comparingInt(SupplyOrderDTO::getAmount).reversed());
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
		
	//Aplicamos el ordenamiento seleccionado:
	@Override
	public List<SupplyOrderDTO> applyOrder(List<SupplyOrderDTO> supplyOrders, String order)
	{
		//Ordenamos el listado de pedidos de aprovisionamiento resultante de los procesos anteriores en base al tipo de ordenamiento elegido:
		switch(order) 
		{
			case "orderAscByProductCode": supplyOrders = inOrderAscByProductCode(supplyOrders); break; //Alfabéticamente por código de producto.
			case "orderDescByProductCode": supplyOrders = inOrderDescByProductCode(supplyOrders); break; //Inverso al alfabeto por código de producto. 
			case "orderAscBySupplierName": supplyOrders = inOrderAscBySupplierName(supplyOrders); break; //Alfabéticamente por nombre del proveedor.
			case "orderDescBySupplierName": supplyOrders = inOrderDescBySupplierName(supplyOrders); break; //Inverso al alfabeto por nombre del proveedor.
			case "orderAscByAdminUsername": supplyOrders = inOrderAscByAdminUsername(supplyOrders); break; //Alfabéticamente por nombre de usuario del administrador.
			case "orderDescByAdminUsername": supplyOrders = inOrderDescByAdminUsername(supplyOrders); break; //Inverso al alfabeto por nombre de usuario del administrador.
			case "orderAscByAmount": supplyOrders = inOrderAscByAmount(supplyOrders); break; //Ascendente por cantidad.
			case "orderDescByAmount": supplyOrders = inOrderDescByAmount(supplyOrders); break; //Descendente por cantidad. 
		}
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento ordenados.
	}
	
	//Agregar:
	
	//Agregamos un pedido de aprovisionamiento a la base de datos:
	@Override
	public SupplyOrderDTO insert(SupplyOrderDTO supplyOrder) 
	{
		SupplyOrder savedSupplyOrder = modelMapper.map(supplyOrder, SupplyOrder.class);
		return modelMapper.map(supplyOrderRepository.save(savedSupplyOrder), SupplyOrderDTO.class); //Insertamos el pedido de
																								    //aprovisonamiento en la
																						            //base de datos y lo
																									//retornamos como DTO.
	}
	
	//Filtramos los pedidos de aprovisionamiento por el código del producto asociado:
	@Override
	public List<SupplyOrderDTO> filterByProductCodes(List<SupplyOrderDTO> supplyOrders, List<String> productCodes) 
	{
	    //Convertimos la lista de códigos de producto a un Set para optimizar la búsqueda:
	    Set<String> productCodeSet = new HashSet<>(productCodes);
	    
	    //Si se eligió alguna opción de filtro:
	    if(!productCodeSet.contains("all")) 
	    {
	    	//Filtramos los pedidos cuyo código de producto esté en el conjunto:
		    supplyOrders = supplyOrders.stream()
				            .filter(supplyOrder -> productCodeSet.contains(supplyOrder.getProduct().getCode()))
				            .collect(Collectors.toList());
	    }
	    
	    return supplyOrders; //Retornamos los pedidos.
	}
		
	//Filtramos los pedidos de aprovisionamiento por el nombre del proveedor asociado:
	@Override
	public List<SupplyOrderDTO> filterBySupplierNames(List<SupplyOrderDTO> supplyOrders, List<String> supplierNames) 
	{
	    //Convertimos la lista de nombres de proveedores a un Set para optimizar la búsqueda:
	    Set<String> supplierNameSet = new HashSet<>(supplierNames);
	    
	    //Si se eligió alguna opción de filtro:
	    if(!supplierNames.contains("all")) 
	    {
	    	//Filtramos los pedidos cuyo nombre de proveedor esté en el conjunto:
		    supplyOrders = supplyOrders.stream()
				            .filter(supplyOrder -> supplierNameSet.contains(supplyOrder.getSupplier().getName()))
				            .collect(Collectors.toList());
	    }

	    return supplyOrders; //Retornamos los pedidos.
	}
	
	//Filtramos los pedidos de aprovisionamiento por el nombre de usuario del administrador que lo generó:
	@Override
	public List<SupplyOrderDTO> filterByAdminUsernames(List<SupplyOrderDTO> supplyOrders, List<String> adminUsernames) 
	{
	    //Convertimos la lista de usernames de administradores a un Set para optimizar la búsqueda:
	    Set<String> adminUsernameSet = new HashSet<>(adminUsernames);

	    //Si se eligió alguna opción de filtro:
	    if(!adminUsernames.contains("all")) 
	    {
	    	//Filtramos los pedidos cuyo username de administrador esté en el conjunto:
		    supplyOrders = supplyOrders.stream()
				            .filter(supplyOrder -> adminUsernameSet.contains(supplyOrder.getMember().getUsername()))
				            .collect(Collectors.toList());
	    }
	    
	    return supplyOrders; //Retornamos los pedidos.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad del mismo:
	@Override
	public List<SupplyOrderDTO> filterByAmount(List<SupplyOrderDTO> supplyOrders, int amount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if(supplyOrder.getAmount() != amount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad como la del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad si es mayor o igual a una determinada:
	@Override
	public List<SupplyOrderDTO> filterByFromAmount(List<SupplyOrderDTO> supplyOrders, int fromAmount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() < fromAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad mayor o igual a la del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad si es menor o igual a una determinada:
	@Override
	public List<SupplyOrderDTO> filterByUntilAmount(List<SupplyOrderDTO> supplyOrders, int untilAmount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() > untilAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad menor o igual a la del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Filtramos los pedidos de aprovisionamiento por la cantidad si está dentro de un rango determinado:
	@Override
	public List<SupplyOrderDTO> filterByAmountRange(List<SupplyOrderDTO> supplyOrders, int rangeFromAmount, int rangeUntilAmount)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (supplyOrder.getAmount() < rangeFromAmount || supplyOrder.getAmount() > rangeUntilAmount) 
			{
				iterator.remove(); //En caso de que no tenga una cantidad mayor o igual a la mínima y menor o igual a la máxima del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
		
	//Aplicamos el filtro seleccionado de la sección cantidad:
	@Override
	public List<SupplyOrderDTO> applyFilterTypeAmount(List<SupplyOrderDTO> supplyOrders, int amount, int fromAmount, int untilAmount,
													  int rangeFromAmount, int rangeUntilAmount)
	{
		//Aplicamos el filtro que corresponda de la sección cantidad al listado:
		if(amount >= 1) //Filtro por cantidad específica.
		{
			supplyOrders = filterByAmount(supplyOrders, amount); //Nos quedamos solo con los que cumplen el filtro.
		}
		
		if(fromAmount >= 1) //Filtro por cantidad mayor o igual a una específica.
		{
			supplyOrders = filterByFromAmount(supplyOrders, fromAmount); //Nos quedamos solo con los que cumplen el filtro.
		}
		
		if(untilAmount >= 1) //Filtro por cantidad menor o igual a una específica.
		{
			supplyOrders = filterByUntilAmount(supplyOrders, untilAmount); //Nos quedamos solo con los que cumplen el filtro.
		}
		
		if(rangeFromAmount >= 1 && rangeUntilAmount >= 1) //Filtro por una cantidad entre un rango específico.
		{
			supplyOrders = filterByAmountRange(supplyOrders, rangeFromAmount, rangeUntilAmount); //Nos quedamos solo con los que cumplen el filtro.
		}
		
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
	
	//Filtramos los pedidos de aprovisionamiento por el estado de la entrega:
	@Override
	public List<SupplyOrderDTO> filterByDelivered(List<SupplyOrderDTO> supplyOrders, boolean delivered)
	{
		Iterator<SupplyOrderDTO> iterator = supplyOrders.iterator(); //Definimos un objeto Iterator para el listado.
		
		//Mientras haya un pedido de aprovisionamiento por analizar:
		while(iterator.hasNext())
		{
			SupplyOrderDTO supplyOrder = iterator.next(); //Obtenemos ese pedido de aprovisionamiento.
			if (!supplyOrder.isDelivered() == delivered) 
			{
				iterator.remove(); //En caso de que no tenga el estado del filtro, lo removemos.
	        }
	    }
		return supplyOrders; //Retornamos los pedidos de aprovisionamiento filtrados.
	}
	
	//Aplicamos todos los filtros seleccionados:
	@Override
	public List<SupplyOrderDTO> applyFilters(List<SupplyOrderDTO> supplyOrders, List<String> productCodes, List<String> supplierNames, 
			 								 List<String> adminUsernames, int amount, int fromAmount, int untilAmount, 
			 								 int rangeFromAmount, int rangeUntilAmount)
	{
		//Si se eligió por lo menos una opción para el filtro de códigos de producto:
		if(productCodes.size() > 0) 
		{
			supplyOrders = filterByProductCodes(supplyOrders, productCodes); //Aplicamos el filtro por códigos de producto.
		}
		
		//Si se eligió por lo menos una opción para el filtro de nombres de proveedor:
		if(supplierNames.size() > 0) 
		{
			supplyOrders = filterBySupplierNames(supplyOrders, supplierNames); //Aplicamos el filtro por nombres de proveedor.
		}
		
		//Si se eligió por lo menos una opción para el filtro de usernames de administrador:
		if(adminUsernames.size() > 0) 
		{
			supplyOrders = filterByAdminUsernames(supplyOrders, adminUsernames); //Aplicamos el filtro por usernames de administrador.
		}

		//Aplicamos el filtro elegido de la sección cantidad:
		supplyOrders = applyFilterTypeAmount(supplyOrders, amount, fromAmount, untilAmount, rangeFromAmount, rangeUntilAmount); 
		
		return supplyOrders; //Retornamos el listado filtrado.
	}
}
