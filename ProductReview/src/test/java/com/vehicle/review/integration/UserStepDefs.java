package com.vehicle.review.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.vehicle.review.domain.User;
import com.vehicle.review.repository.UserRepository;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//@RunWith(SpringRunner.class)



public class UserStepDefs extends SpringIntegrationTest{
	
	@Autowired
	UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStepDefs.class);
	///*private MockMvc mvc;*/
	
	
	
	
	
	@Given("^DB is empty")
	public void emptyDB()throws Throwable{
		userRepository.deleteAll();
	}
	
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIWithJsonData(String uri,String jsonData) throws Exception {
		
		
		/*resultActions = this.mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
				.content(jsonData.getBytes()).headers(headers));*/
		
		String url = createUrl(uri);
		LOGGER.info("actual url "+url);
		LOGGER.info("JSON data "+jsonData);
		HttpEntity<String> httpEnt = new HttpEntity<>(jsonData,httpHeaders);
		responseEnt = restTemplate.exchange(url, HttpMethod.POST, httpEnt, String.class);
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int respCode) throws Exception {
		
		//resultActions.andExpect(status().is(respCode));
		int actualRespCode = responseEnt.getStatusCodeValue();
		LOGGER.info("checkResponseCode "+respCode);
		assertEquals("Response Code is not matcing"+responseEnt.getBody(),respCode,actualRespCode);
	}
	
	@Then("^Response body should be '(.*)'$")
	public void checkResponseBodyStringMatch(String expRespMessage) throws Exception {
		
		//resultActions.andExpect(content().string(respMessage));
		
		LOGGER.info("checkResponseBodyStringMatch : expRespMessage "+expRespMessage);
		assertEquals("Response body is not matcing"+responseEnt.getBody(),responseEnt.getBody(),expRespMessage);
	}
	
	@Then("^Response header should have '(.*)' with value '(.*)'$")
	public void checkHeaderMatch(String headerName,String expHeaderValue) {
		
		/*String actualHeaderValue =resultActions.andReturn().getResponse().getHeaderValue(headerName).toString();
		
		assertEquals(expHeaderValue, actualHeaderValue);*/
		LOGGER.info("checkHeaderMatch : headerName "+headerName +" : expHeaderValue "+expHeaderValue);
		HttpHeaders httpHeaders = responseEnt.getHeaders();
		assertEquals("Header is not matcing"+httpHeaders.getFirst(headerName).toString(),httpHeaders.getFirst(headerName).toString(),expHeaderValue);
		
		
		
	}
	
	@Then("^user should be stored in DB with id (\\d*)$")
	public void checkUserIdinDB(long id) throws Exception {
		
		User user = userRepository.findOne(id);
		assertNotNull(user);
	}

	@Given("^following user exist$")
	public void createUsers(DataTable users) {
		List<User> usersList = users.asList(User.class);
		userRepository.save(usersList);
	}
	
	@Given("^Web context is setup")
	public void webContest( ) throws Exception{
	}
	
	
	
}
