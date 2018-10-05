package com.capgemini.productapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	Product product;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		product = new Product();
		product.setProductId(123);
		product.setProductName("iphone");
		product.setProductCategory("phone");
		product.setProductPrice(50000);
	}
	
	@Test
	public void addProductTest() throws Exception{
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.save(Mockito.isA(Product.class))).thenReturn(product);
		assertEquals(optionalProduct.get(), productServiceImpl.findProductById(123));
	}
	
	@Test
	public void updateProductTest() throws Exception {
		product.setProductName("OnePlus 6T");
		when(productRepository.save(Mockito.isA(Product.class))).thenReturn(product);
		assertEquals(product, productServiceImpl.updateProduct(product));
	}
	
	@Test
	public void findProductByIdTest() throws Exception {
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(Mockito.isA(Integer.class))).thenReturn(optionalProduct);
		assertEquals(optionalProduct.get(), productServiceImpl.findProductById(11));
	}
	
	@Test
	public void deleteProductTest() throws Exception {
		productServiceImpl.deleteProduct(product);
		verify(productRepository).delete(product);
	}

}


