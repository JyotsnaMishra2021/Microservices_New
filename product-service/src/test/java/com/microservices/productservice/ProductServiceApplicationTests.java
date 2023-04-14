package com.microservices.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.imageio.IIOException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry){
		registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}

	@BeforeAll
	static void initAll(){
		mongoDBContainer.start();
	}



	@Test
	void shouldGetProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		System.out.println("productRepository.findAll().size()"+productRepository.findAll().size());
	//	Assertions.assertTrue(productRepository.findAll().size() == 1);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestInput = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestInput))
				.andExpect(status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	@Test
	 void assertThatPortIsAvailable(){
		try {
			Socket socket= new Socket(mongoDBContainer.getContainerIpAddress(), mongoDBContainer.getFirstMappedPort());
			Assertions.assertTrue(socket != null);
		}catch(IOException e){
			throw new AssertionError("The expected port"+ mongoDBContainer.getContainerIpAddress()+ "is not valid");
		}
		}

		private ProductRequest getProductRequest() {
			return ProductRequest.builder()
					.name("iphone13")
					.description("iphone13")
					.price(new BigDecimal(1200))
					.build();
		}


}
