package com.calahorra.culturaJean.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.calahorra.culturaJean.entities.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
class IProductRepositoryTest 
{
	//Atributos:
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	//Encontrar:
	
	@Test
	@DisplayName("Prueba de obtención de un producto mediante su id.")
	public void when_findByProductId() 
	{
		//Arrange:
		Product product = new Product("JaM6-M", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		testEntityManager.persist(product);
		testEntityManager.flush();
		int expectedId = 1;
		
		//Act:
		Product foundProduct = productRepository.findByProductId(1);
		
		//Assert:
		assertEquals(expectedId, foundProduct.getProductId());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un producto mediante su código.")
	public void when_findByCode() 
	{
		//Arrange:
		Product product = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		testEntityManager.persist(product);
		testEntityManager.flush();
		String expectedCode = "JaM6-S";
		
		//Act:
		Product foundProduct = productRepository.findByCode("JaM6-S");
		
		//Assert:
		assertEquals(expectedCode, foundProduct.getCode());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de una categoría.")
	public void when_findByCategory() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeM6-M", "Jeans", 'M', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeM6", true));
		products.add(new Product("JuM6-XS", "Jumpers", 'M', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuM6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", true));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Light Blue", 27, 54, "Printed Jumper", "Printed Light Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		String expectedCategory = "Jumpers";
		int expectedAmount = 3;
		
		//Act:
		List<Product> foundProducts = productRepository.findByCategory("Jumpers");
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertEquals(expectedCategory, product.getCategory());
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de un género.")
	public void when_findByGender() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", true));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Light Blue", 27, 54, "Printed Jumper", "Printed Light Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		Character expectedGender = 'W';
		int expectedAmount = 2;
		
		//Act:
		List<Product> foundProducts = productRepository.findByGender('W');
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertEquals(expectedGender, product.getGender());
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de un talle.")
	public void when_findBySize() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", true));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Light Blue", 27, 54, "Printed Jumper", "Printed Light Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		String expectedSize = "30/M";
		int expectedAmount = 2;
		
		//Act:
		List<Product> foundProducts = productRepository.findBySize("30/M");
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertEquals(expectedSize, product.getSize());
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de un color.")
	public void when_findByColor() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", true));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		String expectedColor = "Blue";
		int expectedAmount = 2;
		
		//Act:
		List<Product> foundProducts = productRepository.findByColor("Blue");
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertEquals(expectedColor, product.getColor());
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos activos.")
	public void when_findByEnabled() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", false));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		boolean expectedEnabled = false;
		int expectedAmount = 2;
		
		//Act:
		List<Product> foundProducts = productRepository.findByEnabled(false);
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertEquals(expectedEnabled, product.isEnabled());
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta igual a 54.")
	public void when_findBySalePrice() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		float expectedSalePrice = 54;
		int expectedAmount = 3;
		
		//Act:
		List<Product> foundProducts = productRepository.findBySalePrice(54);
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertEquals(expectedSalePrice, product.getSalePrice(), 0);
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta mayor o igual a 52 y menor o igual a 58.")
	public void when_findBySalePriceRange() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		float expectedMinimumSalePrice = 52;
		float expectedMaximumSalePrice = 58;
		int expectedAmount = 3;
		
		//Act:
		List<Product> foundProducts = productRepository.findBySalePriceRange(52, 58);
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertTrue(product.getSalePrice() >= expectedMinimumSalePrice);
			assertTrue(product.getSalePrice() <= expectedMaximumSalePrice);
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta menor o igual a 52.")
	public void when_findBySalePriceLessThanOrEqualTo() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
		
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		float expectedMaximumSalePrice = 52;
		int expectedAmount = 1;
		
		//Act:
		List<Product> foundProducts = productRepository.findBySalePriceLessThanOrEqualTo(52);
		
		//Assert:
		for(Product product: foundProducts) 
		{
			assertTrue(product.getSalePrice() <= expectedMaximumSalePrice);
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta mayor o igual a 58.")
	public void when_findBySalePriceGreaterThanOrEqualTo() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
				
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		float expectedMinimumSalePrice = 58;
		int expectedAmount = 1;
				
		//Act:
		List<Product> foundProducts = productRepository.findBySalePriceGreaterThanOrEqualTo(58);
				
		//Assert:
		for(Product product: foundProducts) 
		{
			assertTrue(product.getSalePrice() >= expectedMinimumSalePrice);
		}
		assertEquals(expectedAmount, foundProducts.size());
	}
	
	//Ordenar:
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por su código de forma ascendente.")
	public void when_getAllInOrderAscByCode() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("JuW6-XS", "Jumpers", 'W', "26/XS", "Orange", 27, 54, "Printed Jumper", "Printed Orange Jumper", "JuW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("JuM6-M", "Jumpers", 'M', "30/M", "Blue", 27, 54, "Printed Jumper", "Printed Blue Jumper", "JuM6", true));
				
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		String[] expectedOrderOfCodes = {"JaM6-XL", "JeW6-M", "JuM6-M", "JuM6-S", "JuW6-XS"};
		
		//Act:
		List<Product> foundProducts = productRepository.getAllInOrderAscByCode();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfCodes[i], foundProducts.get(i).getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por su categoría de forma ascendente.")
	public void when_getAllInOrderAscByCategory() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("PW6-S", "Pants", 'W', "S", "White", 25, 50, "Classic Pants", "Classic White Pants", "PW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("LSTSM6-M", "Long Sleeve T-Shirts", 'M', "30/M", "Yellow", 15, 25, "Classic Long Sleeve T-Shirt", "Classic Yellow Long Sleeve T-Shirt", "LSTSM6", true));
				
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		String[] expectedOrderOfCategories = {"Jackets", "Jeans", "Jumpers", "Long Sleeve T-Shirts", "Pants"};
		
		//Act:
		List<Product> foundProducts = productRepository.getAllInOrderAscByCategory();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfCategories[i], foundProducts.get(i).getCategory());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por su precio de venta de forma descendente.")
	public void when_getAllInOrderDescBySalePrice() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("PW6-S", "Pants", 'W', "S", "White", 25, 50, "Classic Pants", "Classic White Pants", "PW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("LSTSM6-M", "Long Sleeve T-Shirts", 'M', "30/M", "Yellow", 15, 25, "Classic Long Sleeve T-Shirt", "Classic Yellow Long Sleeve T-Shirt", "LSTSM6", true));
				
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		float[] expectedOrderOfSalePrices = {60, 54, 50, 50, 25};
		
		//Act:
		List<Product> foundProducts = productRepository.getAllInOrderDescBySalePrice();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfSalePrices[i], foundProducts.get(i).getSalePrice(), 0);
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por su nombre de forma inversa al alfabeto.")
	public void when_getAllInOrderDescByName() 
	{
		//Arrange:
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("JaM6-XL", "Jackets", 'M', "34/XL", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true));
		products.add(new Product("JeW6-M", "Jeans", 'W', "30/M", "Blue", 25, 50, "Jean", "Blue Jean", "JeW6", true));
		products.add(new Product("PW6-S", "Pants", 'W', "S", "White", 25, 50, "Classic Pants", "Classic White Pants", "PW6", true));
		products.add(new Product("JuM6-S", "Jumpers", 'M', "28/S", "Red", 27, 54, "Printed Jumper", "Printed Red Jumper", "JuM6", false));
		products.add(new Product("LSTSM6-M", "Long Sleeve T-Shirts", 'M', "30/M", "Yellow", 15, 25, "Classic Long Sleeve T-Shirt", "Classic Yellow Long Sleeve T-Shirt", "LSTSM6", true));
				
		for(Product product: products) 
		{
			testEntityManager.persist(product);
			testEntityManager.flush();
		}
		String[] expectedOrderOfNames = {"Printed Jumper", "Printed Jacket", "Jean", "Classic Pants", "Classic Long Sleeve T-Shirt"};
		
		//Act:
		List<Product> foundProducts = productRepository.getAllInOrderDescByName();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfNames[i], foundProducts.get(i).getName());
		}
	}
}
