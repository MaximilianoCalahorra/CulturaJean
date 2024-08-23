package com.calahorra.culturaJean.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.entities.Stock;

@RunWith(SpringRunner.class)
@DataJpaTest
class IStockRepositoryTest 
{
	//Atributos:
	@Autowired
	private IStockRepository stockRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	//Encontrar:
	
	@Test
	@DisplayName("Prueba de obtención de un stock mediante su id.")
	public void when_findByStockId() 
	{
		//Arrange:
		Product product = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock = new Stock(product, 20, 10);
		
		testEntityManager.persist(product);
		testEntityManager.persist(stock);
		testEntityManager.flush();
		
		int expectedStockId = 1;
		
		//Act:
		Stock actualStock = stockRepository.findByStockId(1);
		
		//Assert:
		assertEquals(expectedStockId, actualStock.getStockId());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un stock mediante su id.")
	public void when_findByStockIdWithProduct() 
	{
		//Arrange:
		Product product = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock = new Stock(product, 20, 10);
		
		testEntityManager.persist(product);
		testEntityManager.persist(stock);
		testEntityManager.flush();
		
		int expectedStockId = 1;
		String expectedProductCode = "JaM6-S";
		
		//Act:
		Stock actualStock = stockRepository.findByStockIdWithProduct(1);
		
		//Assert:
		assertEquals(expectedStockId, actualStock.getStockId());
		assertEquals(expectedProductCode, actualStock.getProduct().getCode());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un stock mediante el id del producto.")
	public void when_findByProduct() 
	{
		//Arrange:
		Product product = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock = new Stock(product, 20, 10);
		
		testEntityManager.persist(product);
		testEntityManager.persist(stock);
		testEntityManager.flush();
		
		String expectedProductCode = "JaM6-S";
		
		//Act:
		Stock actualStock = stockRepository.findByProduct(1);
		
		//Assert:
		assertEquals(expectedProductCode, actualStock.getProduct().getCode());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un stock mediante el código del producto.")
	public void when_findByProductCode() 
	{
		//Arrange:
		Product product = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock = new Stock(product, 20, 10);
		
		testEntityManager.persist(product);
		testEntityManager.persist(stock);
		testEntityManager.flush();
		
		String expectedProductCode = "JaM6-S";
		
		//Act:
		Stock actualStock = stockRepository.findByProductCode("JaM6-S");
		
		//Assert:
		assertEquals(expectedProductCode, actualStock.getProduct().getCode());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un stock mediante el nombre del producto.")
	public void when_findByProductName() 
	{
		//Arrange:
		Product product = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock = new Stock(product, 20, 10);
		
		testEntityManager.persist(product);
		testEntityManager.persist(stock);
		testEntityManager.flush();
		
		String expectedProductName = "Printed Jacket";
		
		//Act:
		Stock actualStock = stockRepository.findByProductName("Printed Jacket");
		
		//Assert:
		assertEquals(expectedProductName, actualStock.getProduct().getName());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de determinada categoría de producto.")
	public void when_findByProductCategory() 
	{
		//Arrange:
		Product product1 = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuM6-M", "Jumpers", 'M', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuM6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String expectedCategory = "Jackets";
		int expectedAmount = 2;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductCategory("Jackets");
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertEquals(expectedCategory, stock.getProduct().getCategory());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de productos de determinado género.")
	public void when_findByProductGender() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		Character expectedGender = 'W';
		int expectedAmount = 2;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductGender('W');
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertEquals(expectedGender, stock.getProduct().getGender());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de determinado talle de producto.")
	public void when_findByProductSize() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String expectedSize = "30/M";
		int expectedAmount = 3;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductSize("30/M");
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertEquals(expectedSize, stock.getProduct().getSize());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de determinado color de producto.")
	public void when_findByProductColor() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String expectedColor = "Brown";
		int expectedAmount = 2;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductColor("Brown");
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertEquals(expectedColor, stock.getProduct().getColor());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de productos habilitados.")
	public void when_findByProductEnabled() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		boolean expectedEnabled = true;
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductEnabled(true);
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertEquals(expectedEnabled, stock.getProduct().isEnabled());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de productos con determinado precio de venta.")
	public void when_findByProductSalePrice() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		float expectedSalePrice = 60;
		int expectedAmount = 2;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductSalePrice(60);
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertEquals(expectedSalePrice, stock.getProduct().getSalePrice(), 0);
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de productos con un precio de venta menor o igual a uno determinado.")
	public void when_findByProductSalePriceLessThanOrEqualTo() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		float expectedMaximumSalePrice = 45;
		int expectedAmount = 2;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductSalePriceLessThanOrEqualTo(45);
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertTrue(stock.getProduct().getSalePrice() <= expectedMaximumSalePrice);
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de productos con un precio de venta mayor o igual a uno determinado.")
	public void when_findByProductSalePriceGreaterThanOrEqualTo() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		float expectedMinimumSalePrice = 45;
		int expectedAmount = 3;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findByProductSalePriceGreaterThanOrEqualTo(45);
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertTrue(stock.getProduct().getSalePrice() >= expectedMinimumSalePrice);
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los stocks de productos con un precio de venta dentro de un rango determinado.")
	public void when_findByProductSalePriceRange() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		float expectedMinimumSalePrice = 40;
		float expectedMaximumSalePrice = 50;
		int expectedAmount = 2;
		
		//Act:
		List<Stock> actualStocks = stockRepository.findBySalePriceRange(40, 50);
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(Stock stock: actualStocks) 
		{
			assertTrue(stock.getProduct().getSalePrice() >= expectedMinimumSalePrice);
			assertTrue(stock.getProduct().getSalePrice() <= expectedMaximumSalePrice);
		}
	}
	
	//Ordenar:
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera descendente por id.")
	public void when_getAllInOrderDescByStockId() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"JaM6-M", "JuW6-M", "JeM6-M", "SSTSM6-M", "JaW6-S"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderDescByStockId();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera descendente por cantidad deseable.")
	public void when_getAllInOrderDescByDesirableAmount() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"SSTSM6-M", "JaW6-S", "JaM6-M", "JuW6-M", "JeM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderDescByDesirableAmount();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera ascendente por cantidad mínima.")
	public void when_getAllInOrderAscByMinimumAmount() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"JeM6-M", "JuW6-M", "JaW6-S", "JaM6-M", "SSTSM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderAscByMinimumAmount();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera ascendente por cantidad actual.")
	public void when_getAllInOrderAscByActualAmount() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		stock1.setActualAmount(10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		stock2.setActualAmount(12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		stock3.setActualAmount(5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		stock4.setActualAmount(8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		stock5.setActualAmount(15);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"JeM6-M", "JuW6-M", "JaW6-S", "SSTSM6-M", "JaM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderAscByActualAmount();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera ascendente por el código del producto.")
	public void when_getAllInOrderAscByProductCode() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		stock1.setActualAmount(10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		stock2.setActualAmount(12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		stock3.setActualAmount(5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		stock4.setActualAmount(8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		stock5.setActualAmount(15);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"JaM6-M", "JaW6-S", "JeM6-M", "JuW6-M",  "SSTSM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderAscByProductCode();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera ascendente por la categoría del producto.")
	public void when_getAllInOrderAscByProductCategory() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		stock1.setActualAmount(10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		stock2.setActualAmount(12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		stock3.setActualAmount(5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		stock4.setActualAmount(8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		stock5.setActualAmount(15);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"JaW6-S", "JaM6-M", "JeM6-M", "JuW6-M", "SSTSM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderAscByProductCategory();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera descendente por el precio de venta del producto.")
	public void when_getAllInOrderDescByProductSalePrice() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		stock1.setActualAmount(10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		stock2.setActualAmount(12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		stock3.setActualAmount(5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		stock4.setActualAmount(8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		stock5.setActualAmount(15);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"JaW6-S", "JaM6-M", "JuW6-M", "JeM6-M", "SSTSM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderDescByProductSalePrice();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de todos los stocks ordenados de manera descendente por el nombre del producto.")
	public void when_getAllInOrderDescByProductName() 
	{
		//Arrange:
		Product product1 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaW6", true);
		Stock stock1 = new Stock(product1, 20, 10);
		stock1.setActualAmount(10);
		Product product2 = new Product("SSTSM6-M", "Short Sleeve T-Shirts", 'M', "30/M", "Orange", 15, 30, "Printed T-Shirt", "Printed Orange T-Shirt", "SSTSM6", true);
		Stock stock2 = new Stock(product2, 25, 12);
		stock2.setActualAmount(12);
		Product product3 = new Product("JeM6-M", "Jeans", 'M', "M", "White", 20, 40, "Jean", "White Jean", "JeM6", true);
		Stock stock3 = new Stock(product3, 10, 5);
		stock3.setActualAmount(5);
		Product product4 = new Product("JuW6-M", "Jumpers", 'W', "30/M", "Red", 25, 50, "Printed Jumper", "Printed Red Jumper", "JuW6", true);
		Stock stock4 = new Stock(product4, 15, 8);
		stock4.setActualAmount(8);
		Product product5 = new Product("JaM6-M", "Jackets", 'M', "30/M", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		Stock stock5 = new Stock(product5, 20, 10);
		stock5.setActualAmount(15);
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.persist(product3);
		testEntityManager.persist(product4);
		testEntityManager.persist(product5);
		testEntityManager.persist(stock1);
		testEntityManager.persist(stock2);
		testEntityManager.persist(stock3);
		testEntityManager.persist(stock4);
		testEntityManager.persist(stock5);
		testEntityManager.flush();
		
		String[] expectedOrderOfProductCode = {"SSTSM6-M", "JuW6-M", "JaW6-S", "JaM6-M", "JeM6-M"};
		int expectedAmount = 5;
		
		//Act:
		List<Stock> actualStocks = stockRepository.getAllInOrderDescByProductName();
		
		//Assert:
		assertEquals(expectedAmount, actualStocks.size());
		for(int i = 0; i < actualStocks.size(); i++)
		{
			assertEquals(expectedOrderOfProductCode[i], actualStocks.get(i).getProduct().getCode());
		}
	}
}
