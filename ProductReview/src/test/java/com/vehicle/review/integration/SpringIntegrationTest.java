package com.vehicle.review.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import com.vehicle.review.ProductReviewApplication;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes =ProductReviewApplication.class )

@ContextConfiguration
public abstract class SpringIntegrationTest {

	@LocalServerPort
	protected int port = 8888;
	
ResponseEntity<String> responseEnt;
	
	HttpHeaders httpHeaders = new HttpHeaders(); 
	
	

	@Autowired
	protected RestTemplate restTemplate;
	
	protected String createUrl(String uri) {
		return "http://localhost:"+port+uri;
		
	}
}
