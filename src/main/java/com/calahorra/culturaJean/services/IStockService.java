package com.calahorra.culturaJean.services;

import java.util.List;

import com.calahorra.culturaJean.dtos.StockDTO;
import com.calahorra.culturaJean.entities.Stock;

///Interfaz IStockService:
public interface IStockService
{
	//Encontrar:
	
	//Encontramos el stock con determinado id:
	public Stock findByStockId(int stockId);
	
	//Encontramos el stock con determinado id y su producto asociado:
	public Stock findByStockIdWithProduct(int stockId);
	
	//Encontramos el stock de determinado producto:
	public StockDTO findByProduct(int productId);
	
	//Encontramos el stock del producto con determinado código:
	public StockDTO findByProductCode(String code);
	
	//Encontramos el stock del producto con determinado nombre:
	public StockDTO findByProductName(String name);
		
	//Encontramos el stock de cada producto de determinada categoría:
	public List<StockDTO> findByProductCategory(String category);
	
	//Encontramos el stock de cada producto de determinado género:
	public List<StockDTO> findByProductGender(Character gender);
	
	//Encontramos el stock de cada producto de determinado talle:
	public List<StockDTO> findByProductSize(String size);
	
	//Encontramos el stock de cada producto de determinado color:
	public List<StockDTO> findByProductColor(String color);
	
	//Encontramos el stock de cada producto habilitado/deshabilitado:
	public List<StockDTO> findByProductEnabled(boolean enabled);
	
	//Encontramos el stock de cada producto de determinado precio de venta:
	public List<StockDTO> findByProductSalePrice(float salePrice);
	
	//Encontramos el stock de cada producto con un precio de venta menor o igual a uno determinado:
	public List<StockDTO> findByProductSalePriceLessThanOrEqualTo(float maximumPrice);
	
	//Encontramos el stock de cada producto con un precio de venta mayor o igual a uno determinado:
	public List<StockDTO> findByProductSalePriceGreaterThanOrEqualTo(float minimumPrice);
		
	//Encontramos los productos con un precio de venta entre determinado rango:
	public List<StockDTO> findBySalePriceRange(float minimumPrice, float maximumPrice);
	
	//Obtener:
	
	//Obtenemos todos los stocks:
	public List<StockDTO> getAll();
	
	//Ordenar:
	
	//Ordenamos los stocks por id de manera ascendente con su producto asociado:
	public List<StockDTO> getAllInOrderAscByStockId();
	
	//Ordenamos los stocks por id de manera descendente con su producto asociado:
	public List<StockDTO> getAllInOrderDescByStockId();
	
	//Ordenamos los stocks por cantidad deseable de manera ascendente con su producto asociado:
	public List<StockDTO> getAllInOrderAscByDesirableAmount();
	
	//Ordenamos los stocks por cantidad deseable de manera descendente con su producto asociado:
	public List<StockDTO> getAllInOrderDescByDesirableAmount();
	
	//Ordenamos los stocks por cantidad mínima de manera ascendente con su producto asociado:
	public List<StockDTO> getAllInOrderAscByMinimumAmount();
	
	//Ordenamos los stocks por cantidad mínima de manera descendente con su producto asociado:
	public List<StockDTO> getAllInOrderDescByMinimumAmount();
	
	//Ordenamos los stocks por cantidad actual de manera ascendente con su producto asociado:
	public List<StockDTO> getAllInOrderAscByActualAmount();
	
	//Ordenamos los stocks por cantidad actual de manera descendente con su producto asociado:
	public List<StockDTO> getAllInOrderDescByActualAmount();
	
	//Ordenamos los stocks por el código del producto asociado de manera ascendente:
	public List<StockDTO> getAllInOrderAscByProductCode();
	
	//Ordenamos los stocks por el código del producto asociado de manera descendente:
	public List<StockDTO> getAllInOrderDescByProductCode();
	
	//Ordenamos los stocks por la categoría del producto asociado de manera alfabética:
	public List<StockDTO> getAllInOrderAscByProductCategory();
	
	//Ordenamos los stocks por la categoría del producto asociado de manera inversa al alfabeto:
	public List<StockDTO> getAllInOrderDescByProductCategory();
	
	//Ordenamos los stocks por el precio de venta del producto asociado de manera ascendente:
	public List<StockDTO> getAllInOrderAscByProductSalePrice();
	
	//Ordenamos los stocks por el precio de venta del producto asociado de manera descendente:
	public List<StockDTO> getAllInOrderDescByProductSalePrice();
	
	//Ordenamos los stocks por el nombre del producto asociado de manera alfabética:
	public List<StockDTO> getAllInOrderAscByProductName();
	
	//Ordenamos los stocks por el nombre del producto asociado de manera inversa al alfabeto:
	public List<StockDTO> getAllInOrderDescByProductName();
	
	//Ordenamos los stocks por la cantidad deseable de forma ascendente:
	public List<StockDTO> inOrderAscByDesirableAmount(List<StockDTO> stocks);
	
	//Ordenamos los stocks por la cantidad deseable de forma descendente:
	public List<StockDTO> inOrderDescByDesirableAmount(List<StockDTO> stocks);
	
	//Ordenamos los stocks por la cantidad mínima de forma ascendente:
	public List<StockDTO> inOrderAscByMinimumAmount(List<StockDTO> stocks);
	
	//Ordenamos los stocks por la cantidad mínima de forma descendente:
	public List<StockDTO> inOrderDescByMinimumAmount(List<StockDTO> stocks);

	//Ordenamos los stocks por la cantidad actual de forma ascendente:
	public List<StockDTO> inOrderAscByActualAmount(List<StockDTO> stocks);
	
	//Ordenamos los stocks por la cantidad actual de forma descendente:
	public List<StockDTO> inOrderDescByActualAmount(List<StockDTO> stocks);
	
	//Ordenamos los stocks por código de producto de forma alfabética:
	public List<StockDTO> inOrderAscByProductCode(List<StockDTO> stocks);
	
	//Ordenamos los stocks por código de producto de forma inversa al alfabeto:
	public List<StockDTO> inOrderDescByProductCode(List<StockDTO> stocks);
	
	//Ordenamos los stocks por categoría de producto de forma alfabética:
	public List<StockDTO> inOrderAscByProductCategory(List<StockDTO> stocks);
	
	//Ordenamos los stocks por categoría de producto de forma inversa al alfabeto:
	public List<StockDTO> inOrderDescByProductCategory(List<StockDTO> stocks);
	
	//Ordenamos los stocks por la precio de venta del producto de forma ascendente:
	public List<StockDTO> inOrderAscByProductSalePrice(List<StockDTO> stocks);
		
	//Ordenamos los stocks por precio de venta del producto de forma descendente:
	public List<StockDTO> inOrderDescByProductSalePrice(List<StockDTO> stocks);
	
	//Ordenamos los stocks por nombre de producto de forma alfabética:
	public List<StockDTO> inOrderAscByProductName(List<StockDTO> stocks);
	
	//Ordenamos los stocks por nombre de producto de forma inversa al alfabeto:
	public List<StockDTO> inOrderDescByProductName(List<StockDTO> stocks);
	
	//Aplicamos el criterio de ordenamiento elegido:
	public List<StockDTO> applyOrder(List<StockDTO> stocks, String order);
	
	//Agregar o modificar:
	
	//Agregamos o modificamos un stock en la base de datos:
	public StockDTO insertOrUpdate(StockDTO stock);
	
	//Calcular:
	
	//Calculamos la cantidad actual de stock:
	public int actualAmount(int stockId);
	
	//Disminuir:
	
	//Disminuimos el stock de los lotes que sean necesarios para satisfacer la compra:
	public void decreaseStock(int productId, int amount) throws Exception;
}
