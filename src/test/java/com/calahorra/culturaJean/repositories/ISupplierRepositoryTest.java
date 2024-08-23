package com.calahorra.culturaJean.repositories;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.calahorra.culturaJean.entities.Supplier;

@RunWith(SpringRunner.class)
@DataJpaTest
class ISupplierRepositoryTest 
{
	//Atributos:
	@Autowired
	private ISupplierRepository supplierRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	//Encontrar:
	
	@Test
	@DisplayName("Prueba de obtención de un proveedor mediante su id.")
	public void when_findBySupplierId() 
	{
		//Arrange:
		Supplier supplier = new Supplier("Supplier", "Av. Supplier 123", "+5491112345678", "supplier@email.com");
		testEntityManager.persist(supplier);
		testEntityManager.flush();
		int expectedId = 1;
		
		//Act:
		Supplier foundSupplier = supplierRepository.findBySupplierId(1);
		
		//Assert:
		assertEquals(expectedId, foundSupplier.getSupplierId());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un proveedor mediante su nombre.")
	public void when_findByName() 
	{
		//Arrange:
		Supplier supplier = new Supplier("Supplier", "Av. Supplier 123", "+5491112345678", "supplier@email.com");
		testEntityManager.persist(supplier);
		testEntityManager.flush();
		String expectedName = "Supplier";
		
		//Act:
		Supplier foundSupplier = supplierRepository.findByName("Supplier");
		
		//Assert:
		assertEquals(expectedName, foundSupplier.getName());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un proveedor mediante su número de teléfono.")
	public void when_findByPhoneNumber() 
	{
		//Arrange:
		Supplier supplier = new Supplier("Supplier", "Av. Supplier 123", "+5491112345678", "supplier@email.com");
		testEntityManager.persist(supplier);
		testEntityManager.flush();
		String expectedPhoneNumber = "+5491112345678";
		
		//Act:
		Supplier foundSupplier = supplierRepository.findByPhoneNumber("+5491112345678");
		
		//Assert:
		assertEquals(expectedPhoneNumber, foundSupplier.getPhoneNumber());
	}
	
	@Test
	@DisplayName("Prueba de obtención de un proveedor mediante su email.")
	public void when_findByEmail() 
	{
		//Arrange:
		Supplier supplier = new Supplier("Supplier", "Av. Supplier 123", "+5491112345678", "supplier@email.com");
		testEntityManager.persist(supplier);
		testEntityManager.flush();
		String expectedEmail = "supplier@email.com";
		
		//Act:
		Supplier foundSupplier = supplierRepository.findByEmail("supplier@email.com");
		
		//Assert:
		assertEquals(expectedEmail, foundSupplier.getEmail());
	}
	
	//Ordenar:
	
	@Test
	@DisplayName("Prueba de obtención de los proveedores ordenados de forma descendente por nombre.")
	public void when_getAllInOrderDescByName() 
	{
		//Arrange:
		List<Supplier> suppliers = new ArrayList<Supplier>();
		suppliers.add(new Supplier("Supplier 1", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		suppliers.add(new Supplier("Supplier 2", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		suppliers.add(new Supplier("Supplier 3", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		suppliers.add(new Supplier("Supplier 4", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		
		for(Supplier supplier: suppliers) 
		{
			testEntityManager.persist(supplier);
			testEntityManager.flush();
		}
		
		String[] expectedOrderOfNames = {"Supplier 4", "Supplier 3", "Supplier 2", "Supplier 1"};
		
		//Act:
		List<Supplier> suppliersInOrder = supplierRepository.getAllInOrderDescByName();
		
		//Assert:
		for(int i = 0; i < suppliersInOrder.size(); i++) 
		{
			assertEquals(expectedOrderOfNames[i], suppliersInOrder.get(i).getName());
		}
	}
	
	@Test
	@DisplayName("Prueba de obtención de los proveedores ordenados de forma descendente por id.")
	public void when_getAllInOrderDescBySupplierId() 
	{
		//Arrange:
		List<Supplier> suppliers = new ArrayList<Supplier>();
		suppliers.add(new Supplier("Supplier 3", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		suppliers.add(new Supplier("Supplier 4", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		suppliers.add(new Supplier("Supplier 1", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		suppliers.add(new Supplier("Supplier 2", "Av. Supplier 123", "+5491112345678", "supplier@email.com"));
		
		for(Supplier supplier: suppliers) 
		{
			testEntityManager.persist(supplier);
			testEntityManager.flush();
		}
		
		String[] expectedOrderOfNames = {"Supplier 2", "Supplier 1", "Supplier 4", "Supplier 3"};
		
		//Act:
		List<Supplier> suppliersInOrder = supplierRepository.getAllInOrderDescBySupplierId();
		
		//Assert:
		for(int i = 0; i < suppliersInOrder.size(); i++) 
		{
			assertEquals(expectedOrderOfNames[i], suppliersInOrder.get(i).getName());
		}
	}
}
