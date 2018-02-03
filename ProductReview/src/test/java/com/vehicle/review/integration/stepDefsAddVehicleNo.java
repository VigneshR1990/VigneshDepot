package com.vehicle.review.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.vehicle.review.domain.User;
import com.vehicle.review.repository.UserRepository;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class stepDefsAddVehicleNo extends SpringIntegrationTest {
	
	@Autowired
	UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStepDefs.class);
	
	/*private MockMvc mvc;
	private ResultActions resultActions;*/
	
	/*HttpHeaders headers = new HttpHeaders(); 
	*/
	
	
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIWithJsonData(String uri,String jsonData) throws Exception {
		
		
		/*resultActions = this.mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
				.content(jsonData.getBytes()));*/
		String url = createUrl(uri);
		LOGGER.info("actual url "+url);
		LOGGER.info("JSON data "+jsonData);
		HttpEntity<String> httpEnt = new HttpEntity<>(jsonData,httpHeaders);
		responseEnt = restTemplate.exchange(url, HttpMethod.POST, httpEnt, String.class);
	
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int respCode) throws Exception {
		
		/*resultActions.andExpect(status().is(respCode));*/
		int actualRespCode = responseEnt.getStatusCodeValue();
		LOGGER.info("checkResponseCode "+respCode);
		assertEquals("Response Code is not matcing"+responseEnt.getBody(),respCode,actualRespCode);
	
	}
	
	@Then("^Response body should be '(.*)'$")
	public void checkResponseBodyStringMatch(String expRespMessage) throws Exception {
		
		LOGGER.info("checkResponseBodyStringMatch : expRespMessage "+expRespMessage);
		assertEquals("Response body is not matcing"+responseEnt.getBody(),responseEnt.getBody(),expRespMessage);
	}
	
	
	
	@Then("^user Vehicle no should be updated in DB with '(.*)'$ for id (\\d*)$")
	public void checkUserVehicleNoinDB(String expVehNo,long id) throws Exception {
		
		User user = userRepository.findOne(id);
		String actVehNo = user.getVehicleNo();
		assertEquals(expVehNo, actVehNo);
	
	}

	@Given("^following Vehicle Number exists")
	public void createVehicle(DataTable users) {
		List<User> usersList = users.asList(User.class);
		userRepository.save(usersList);
	}
	
	@Given("^Web contexts is setup")
	public void webContest( ) throws Exception{
	}
}
