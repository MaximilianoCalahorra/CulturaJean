package com.calahorra.culturaJean.services.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.calahorra.culturaJean.dtos.ProductDTO;
import com.calahorra.culturaJean.entities.Product;
import com.calahorra.culturaJean.repositories.IProductRepository;
import com.calahorra.culturaJean.services.IProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application_test.properties")
class ProductServiceTest
{
	//Atributo:
	@Autowired
	private IProductService productService;
	
	@MockBean
	private IProductRepository productRepository;
	
	@BeforeEach
	public void setUp() 
	{
		Product product1 = new Product("SSTSM6-XXL", "Short Sleeve T-Shirts", 'M', "36/XXL", "Pink", 12, 25, "Classic Printed T-Shirt", "Classic Printed Pink T-Shirt", "SSTSM6", true);
		product1.setProductId(1);
		Product product2 = new Product("JaM6-S", "Jackets", 'M', "28/S", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", "JaM6", true);
		product2.setProductId(2);
		Product product3 = new Product("JuM6-L", "Jumpers", 'M', "32/L", "Black", 27, 54, "Printed Jumper", "Printed Black Jumper", "JuM6", true);
		product3.setProductId(3);
		Product product4 = new Product("JeM6-XL", "Jeans", 'M', "XL", "Black", 25, 50, "Jean", "Black Jean", "JeM6", false);
		product4.setProductId(4);
		Product product5 = new Product("SSTSM7-L", "Short Sleeve T-Shirts", 'M', "32/L", "Orange", 12, 25, "Classic Printed T-Shirt", "Classic Printed Orange T-Shirt", "SSTSM7", false);
		product5.setProductId(5);
		Product product6 = new Product("JaW6-S", "Jackets", 'W', "28/S", "Lilac", 30, 60, "Printed Jacket", "Printed Lilac Jacket", "JaW6", true);
		product6.setProductId(2);
		
		List<Product> jackets = new ArrayList<Product>();
		jackets.add(product2);
		jackets.add(product6);
		
		List<Product> womanProducts = new ArrayList<Product>();
		womanProducts.add(product6);
		
		List<Product> sizeProducts = new ArrayList<Product>();
		sizeProducts.add(product2);
		sizeProducts.add(product6);
		
		List<Product> colorProducts = new ArrayList<Product>();
		colorProducts.add(product3);
		colorProducts.add(product4);
		
		List<Product> disabledProducts = new ArrayList<Product>();
		disabledProducts.add(product4);
		disabledProducts.add(product5);
		
		List<Product> salePriceProducts = new ArrayList<Product>();
		salePriceProducts.add(product1);
		salePriceProducts.add(product5);
		
		List<Product> salePriceRangeProducts = new ArrayList<Product>();
		salePriceRangeProducts.add(product3);
		salePriceRangeProducts.add(product4);
		
		List<Product> salePriceLessThanOrEqualToProducts = new ArrayList<Product>();
		salePriceLessThanOrEqualToProducts.add(product1);
		salePriceLessThanOrEqualToProducts.add(product5);
		
		List<Product> salePriceGreaterThanOrEqualToProducts = new ArrayList<Product>();
		salePriceGreaterThanOrEqualToProducts.add(product2);
		salePriceGreaterThanOrEqualToProducts.add(product6);
		
		List<Product> allProducts = new ArrayList<Product>();
		allProducts.add(product1);
		allProducts.add(product2);
		allProducts.add(product3);
		allProducts.add(product4);
		allProducts.add(product5);
		allProducts.add(product6);
		
		List<Product> allProductsInOrderDescByCode = new ArrayList<Product>();
		allProductsInOrderDescByCode.add(product5);
		allProductsInOrderDescByCode.add(product1);
		allProductsInOrderDescByCode.add(product3);
		allProductsInOrderDescByCode.add(product4);
		allProductsInOrderDescByCode.add(product6);
		allProductsInOrderDescByCode.add(product2);
		
		List<Product> allProductsInOrderDescByCategory = new ArrayList<Product>();
		allProductsInOrderDescByCategory.add(product5);
		allProductsInOrderDescByCategory.add(product1);
		allProductsInOrderDescByCategory.add(product3);
		allProductsInOrderDescByCategory.add(product4);
		allProductsInOrderDescByCategory.add(product6);
		allProductsInOrderDescByCategory.add(product2);
		
		List<Product> allProductsInOrderAscBySalePrice = new ArrayList<Product>();
		allProductsInOrderAscBySalePrice.add(product1);
		allProductsInOrderAscBySalePrice.add(product5);
		allProductsInOrderAscBySalePrice.add(product4);
		allProductsInOrderAscBySalePrice.add(product3);
		allProductsInOrderAscBySalePrice.add(product2);
		allProductsInOrderAscBySalePrice.add(product6);
		
		List<Product> allProductsInOrderAscByName = new ArrayList<Product>();
		allProductsInOrderAscByName.add(product1);
		allProductsInOrderAscByName.add(product5);
		allProductsInOrderAscByName.add(product4);
		allProductsInOrderAscByName.add(product2);
		allProductsInOrderAscByName.add(product6);
		allProductsInOrderAscByName.add(product3);
		
		Mockito.when(productRepository.findByProductId(product1.getProductId())).thenReturn(product1);
		Mockito.when(productRepository.findByCode(product2.getCode())).thenReturn(product2);
		Mockito.when(productRepository.findByCode(product6.getCode())).thenReturn(product6);
		Mockito.when(productRepository.findByCategory("Jackets")).thenReturn(jackets);
		Mockito.when(productRepository.findByGender('W')).thenReturn(womanProducts);
		Mockito.when(productRepository.findBySize("28/S")).thenReturn(sizeProducts);
		Mockito.when(productRepository.findByColor("Black")).thenReturn(colorProducts);
		Mockito.when(productRepository.findByEnabled(false)).thenReturn(disabledProducts);
		Mockito.when(productRepository.findBySalePrice(25)).thenReturn(salePriceProducts);
		Mockito.when(productRepository.findBySalePriceRange(48, 56)).thenReturn(salePriceRangeProducts);
		Mockito.when(productRepository.findBySalePriceLessThanOrEqualTo(48)).thenReturn(salePriceLessThanOrEqualToProducts);
		Mockito.when(productRepository.findBySalePriceGreaterThanOrEqualTo(56)).thenReturn(salePriceGreaterThanOrEqualToProducts);
		Mockito.when(productRepository.findAll()).thenReturn(allProducts);
		Mockito.when(productRepository.getAllInOrderDescByCode()).thenReturn(allProductsInOrderDescByCode);
		Mockito.when(productRepository.getAllInOrderDescByCategory()).thenReturn(allProductsInOrderDescByCategory);
		Mockito.when(productRepository.getAllInOrderAscBySalePrice()).thenReturn(allProductsInOrderAscBySalePrice);
		Mockito.when(productRepository.getAllInOrderAscByName()).thenReturn(allProductsInOrderAscByName);
	}
	
	//Encontrar:
	
	@Test
	@DisplayName("Prueba de obtención de un producto mediante su id.")
	public void when_findByProductId() 
	{
		//Arrange:
		int expectedProductId = 1;
		
		//Act:
		Product foundProduct = productService.findByProductId(1);
		
		//Assert:
		assertEquals(expectedProductId, foundProduct.getProductId());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un producto mediante su código.")
	public void when_findByCode() 
	{
		//Arrange:
		String expectedCode = "JaM6-S";
		
		//Act:
		ProductDTO foundProduct = productService.findByCode("JaM6-S");
		
		//Assert:
		assertEquals(expectedCode, foundProduct.getCode());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de una categoría.")
	public void when_findByCategory() 
	{
		//Arrange:
		String expectedCategory = "Jackets";
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findByCategory("Jackets");
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertEquals(expectedCategory, product.getCategory());
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de un género.")
	public void when_findByGender() 
	{
		//Arrange:
		Character expectedGender = 'W';
		int expectedLength = 1;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findByGender('W');
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertEquals(expectedGender, product.getGender());
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de un talle.")
	public void when_findBySize() 
	{
		//Arrange:
		String expectedSize = "28/S";
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findBySize("28/S");
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertEquals(expectedSize, product.getSize());
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos de determinado color.")
	public void when_findByColor() 
	{
		//Arrange:
		String expectedColor = "Black";
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findByColor("Black");
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertEquals(expectedColor, product.getColor());
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos activos.")
	public void when_findByEnabled() 
	{
		//Arrange:
		boolean expectedEnabled = false;
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findByEnabled(false);
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertEquals(expectedEnabled, product.isEnabled());
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con determinado precio de venta.")
	public void when_findBySalePrice() 
	{
		//Arrange:
		float expectedSalePrice = 25;
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findBySalePrice(25);
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertEquals(expectedSalePrice, product.getSalePrice(), 0);
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta entre un rango determinado.")
	public void when_findBySalePriceRange() 
	{
		//Arrange:
		float expectedMinimumSalePrice = 48;
		float expectedMaximumSalePrice = 56;
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findBySalePriceRange(48, 56);
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertTrue(product.getSalePrice() >= expectedMinimumSalePrice);
			assertTrue(product.getSalePrice() <= expectedMaximumSalePrice);
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta menor o igual a uno determinado.")
	public void when_findBySalePriceLessThanOrEqualTo() 
	{
		//Arrange:
		float expectedMaximumSalePrice = 48;
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findBySalePriceLessThanOrEqualTo(48);
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertTrue(product.getSalePrice() <= expectedMaximumSalePrice);
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos con un precio de venta mayor o igual a uno determinado.")
	public void when_findBySalePriceGreaterThanOrEqualTo() 
	{
		//Arrange:
		float expectedMinimumSalePrice = 56;
		int expectedLength = 2;
		
		//Act:
		List<ProductDTO> foundProducts = productService.findBySalePriceGreaterThanOrEqualTo(56);
		
		//Assert:
		for(ProductDTO product: foundProducts) 
		{
			assertTrue(product.getSalePrice() >= expectedMinimumSalePrice);
		}
		assertEquals(expectedLength, foundProducts.size());
	}
	
	//Obtener:
	
	@Test
	@DisplayName("Prueba de obtención de todos los productos.")
	public void when_getAll() 
	{
		//Arrange:
		int expectedLength = 6;
		
		//Act:
		List<Product> foundProducts = productService.getAll();
		
		//Assert:
		assertEquals(expectedLength, foundProducts.size());
	}
	
	//Ordenar:
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por código de forma descendente.")
	public void when_getAllInOrderDescByCode() 
	{
		//Arrange:
		String[] expectedOrderOfCodes = {"SSTSM7-L", "SSTSM6-XXL", "JuM6-L", "JeM6-XL", "JaW6-S", "JaM6-S"}; 
		
		//Act:
		List<ProductDTO> foundProducts = productService.getAllInOrderDescByCode();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfCodes[i], foundProducts.get(i).getCode());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por categoría de forma descendente.")
	public void when_getAllInOrderDescByCategory() 
	{
		//Arrange:
		String[] expectedOrderOfCategories = {"Short Sleeve T-Shirts", "Short Sleeve T-Shirts", "Jumpers", "Jeans", "Jackets", "Jackets"}; 
		
		//Act:
		List<ProductDTO> foundProducts = productService.getAllInOrderDescByCategory();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfCategories[i], foundProducts.get(i).getCategory());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por precio de venta de forma ascendente.")
	public void when_getAllInOrderAscBySalePrice() 
	{
		//Arrange:
		float[] expectedOrderOfSalePrices = {25, 25, 50, 54, 60, 60}; 
		
		//Act:
		List<ProductDTO> foundProducts = productService.getAllInOrderAscBySalePrice();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfSalePrices[i], foundProducts.get(i).getSalePrice(), 0);
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los productos ordenados por nombre de forma alfabética.")
	public void when_getAllInOrderAscByName() 
	{
		//Arrange:
		String[] expectedOrderOfNames = {"Classic Printed T-Shirt", "Classic Printed T-Shirt", "Jean", "Printed Jacket", "Printed Jacket", "Printed Jumper"}; 
		
		//Act:
		List<ProductDTO> foundProducts = productService.getAllInOrderAscByName();
		
		//Assert:
		for(int i = 0; i < foundProducts.size(); i++) 
		{
			assertEquals(expectedOrderOfNames[i], foundProducts.get(i).getName());
		}
	}
	
	//Agregar:
	
	@Test
	@DisplayName("Prueba de inserción exitosa de un producto en la base de datos.")
	public void when_insert_succesfull() 
	{
		//Arrange:
		ProductDTO product = new ProductDTO(1, "JaM6-XS", "Jackets", 'M', "26/XS", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", true, "JaM6");
		ProductDTO productNew = null;
	
		//Act:
		try
		{
			productService.insert(product);
			productNew = productService.findByCode("JaM6-XS");
	
			//Assert:
			assertNotNull(productNew);
			assertEquals(product.getCode(), productNew.getCode());
		} 
		catch(Exception e) 
		{
			System.out.println("Excepción!");
		}
	}
	
	@Test
	@DisplayName("Prueba de inserción fallida de un producto en la base de datos.")
	public void when_insert_faild() 
	{
		//Arrange:
		ProductDTO product1 = new ProductDTO(1, "JaM6-XS", "Jackets", 'M', "26/XS", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", true, "JaM6");
		ProductDTO product2 = new ProductDTO(2, "JaM6-XS", "Jackets", 'M', "26/XS", "Brown", 30, 60, "Printed Jacket", "Printed Brown Jacket", true, "JaM6");
		ProductDTO productNew1 = null;
		ProductDTO productNew2 = null;
	
		//Act:
		try
		{
			productService.insert(product1);
			productNew1 = productService.findByCode("JaM6-XS");
			
			//Assert:
			assertNotNull(productNew1);			
			assertEquals(product1.getCode(), productNew1.getCode());
			
			productService.insert(product2);
			productNew2 = productService.findByCode("JaM6-XS");
			
			//Assert:
			assertNotNull(productNew2);			
			assertEquals(product2.getCode(), productNew2.getCode());
		} 
		catch(Exception e) 
		{
			System.out.println("Excepción!");
		}
	}
	
	//Eliminar:
	
	@Test
	@DisplayName("Pruebo de deshabilitación de un producto.")
	public void when_logicalDelete() 
	{
		//Arrange:
		boolean expectedEnabled = false;
		
		//Act:
		productService.logicalDelete(1);
		Product product = productService.findByProductId(1);
		
		//Assert:
		assertEquals(expectedEnabled, product.isEnabled());
	}
}
