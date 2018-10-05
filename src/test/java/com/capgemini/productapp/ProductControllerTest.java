package com.capgemini.productapp;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	@Mock
	public ProductService service;

	@InjectMocks
	private ProductController productController;

	private MockMvc mockMvc;

	Product product;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		product = new Product();
		
		product.setProductId(123);
		product.setProductName("iphone");
		product.setProductCategory("phone");
		product.setProductPrice(50000);
	}

	@Test
	public void testaddProduct() throws Exception {

		
		when(service.addProduct(Mockito.isA(Product.class))).thenReturn(product);

		mockMvc.perform(post("/product").
				contentType(MediaType.APPLICATION_JSON_UTF8).
				content("{\"productId\":\"123\",\"productName\":\"iphone\",\"productCategory\":\"phone\",\"productPrice\":\"50000\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists());

	}

	@Test
	public void testupdateProduct() throws Exception {
	
		when(service.findProductById(123)).thenReturn(product);
		
		product.setProductPrice(55000);
		
	when(service.updateProduct(Mockito.isA(Product.class))).thenReturn(product);
	
	mockMvc.perform(put("/product").
			contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"productId\":123,\"productName\":\"iphone\",\"productCategory\":\"phone\",\"productPrice\":50000}")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId") .exists())
			.andExpect(jsonPath("$.productName").exists())
			.andExpect(jsonPath("$.productCategory").exists())
			.andExpect(jsonPath("$.productPrice").exists())
			.andDo(print());
			
	verify(service).findProductById(product.getProductId());
}
	
	@Test
	public void testfindProductById() throws Exception {
		
		when(service.findProductById(123)).thenReturn(product);
		
		mockMvc.perform(get("/products/123")
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andDo(print());
	}
	@Test
	public void deleteProductTest() throws Exception{
		
		when(service.findProductById(11)).thenReturn(product);
		
		mockMvc.perform(delete("/products/11")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	
	}
	}

