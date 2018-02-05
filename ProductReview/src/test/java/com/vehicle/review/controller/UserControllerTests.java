package com.vehicle.review.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vehicle.review.domain.User;
import com.vehicle.review.service.ServiceException;
import com.vehicle.review.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=Controller.class,secure=false)
public class UserControllerTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTests.class);
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@Test
	public void givenId_WhenGetUser_ThenReturnJson()  throws Exception{
		User user = new User((long) 1,"vignesh","vignesh.r90@gmail.com","pwd12345");
		String userJson ="{\"name\":\"vignesh\",\"email\":\"vignesh.r90@gmail.com\",\"password\":\"pwd12345\"}";
		
		LOGGER.debug("Inside givenId_WhenGetUser_ThenReturnJson");
		Mockito.when(userService.get(Mockito.anyLong())).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals("Expected code did not match",200,response.getStatus());
		JSONAssert.assertEquals(userJson, response.getContentAsString(), false);
	}
	
	@Test
	public void givenInvalidId_WhenGetUser_ThenReturnJson()  throws Exception{
		String expErrorJson = "{\"errorCode\":\"INVALID_ID\",\"message\":\"Invalid User Id\"}";
		
		LOGGER.debug("Inside givenInvalidId_WhenGetUser_ThenReturnJson");
		Mockito.when(userService.get(Mockito.anyLong())).thenThrow(new ServiceException("Invalid User Id","INVALID_ID"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals("Expected code did not match",400,response.getStatus());
		JSONAssert.assertEquals(expErrorJson, response.getContentAsString(), false);
	}
	
}
