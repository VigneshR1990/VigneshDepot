package com.vehicle.review.integration;

import static org.junit.Assert.assertEquals;

import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RestStepDefs extends SpringIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestStepDefs.class);
	static String authToken ="";
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIWithJsonData(String uri,String jsonData) throws Exception {
		LOGGER.info("actual url "+uri);
		LOGGER.info("JSON data "+jsonData);
		executePost(uri, jsonData,authToken);
		
	}
	
	
	@When("^client requests GET (.*)$")
	public void performGetOnURI(String uri) throws Exception {
		LOGGER.info("actual url "+uri);
		executeGet(uri, authToken);
	}
	
	
	@And("^User is logged in with UserName : '(.*)' and Password : '(.*)'$")
	public void userLogIn(String userName,String password) throws Exception {
		LOGGER.info("userLogIn : userName :"+userName);
		LOGGER.info("userLogIn : password :"+password);
		String uri = "/api/user/login";
		String jsonData ="{\"userName\":\""+userName+"\",\"password\":\""+password+"\"}";
		LOGGER.info("userLogIn : jsonData :"+jsonData);
		executePost(uri,jsonData);
		if(null != latestResponse.getTheResponse().getHeaders() && null!= latestResponse.getTheResponse().getHeaders().get("X-AUTH-TOKEN"))
			authToken = latestResponse.getTheResponse().getHeaders().get("X-AUTH-TOKEN").get(0);
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int respCode) throws Exception {
		
		//resultActions.andExpect(status().is(respCode));
		int actualRespCode = latestResponse.getTheResponse().getStatusCode().value();
		LOGGER.info("checkResponseCode "+respCode);
		assertEquals("Response Code is not matcing"+latestResponse.getBody(),respCode,actualRespCode);
	}
	
	@Then("^Response body should be '(.*)'$")
	public void checkResponseBodyStringMatch(String expRespMessage) throws Exception {
		
		//resultActions.andExpect(content().string(respMessage));
		
		LOGGER.info("checkResponseBodyStringMatch : expRespMessage "+expRespMessage);
		assertEquals("Response body is not matcing"+latestResponse.getBody(),latestResponse.getBody(),expRespMessage);
	}
	
	
	@Then("^Response JSON should be$")
	public void checkResponseBodyJSONMatch(String expRespMessage) throws Exception {
		LOGGER.info("checkResponseBodyStringMatch : expRespMessage "+expRespMessage);
		String actual = latestResponse.getBody();
		JSONAssert.assertEquals(expRespMessage, actual, false);
	}
	
}
