package com.vehicle.review.integration;

import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.vehicle.review.domain.User;
import com.vehicle.review.repository.UserRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =ProductReviewApplication.class )
@WebAppConfiguration
@TestPropertySource(locations ="classpath:applicaion.properties")
*/
public class stepDefsEditMobNo extends SpringIntegrationTest{
	
	@Autowired
	UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStepDefs.class);
	
	/*private MockMvc mvc;
	private ResultActions resultActions;
	
	HttpHeaders headers = new HttpHeaders(); */
	
	
	
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIWithJsonData(String uri,String jsonData) throws Exception {
		
		
		String url = createUrl(uri);
		LOGGER.info("actual url "+url);
		LOGGER.info("JSON data "+jsonData);
		HttpEntity<String> httpEnt = new HttpEntity<>(jsonData,httpHeaders);
		responseEnt = restTemplate.exchange(url, HttpMethod.POST, httpEnt, String.class);
	
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int respCode) throws Exception {
		int actualRespCode = responseEnt.getStatusCodeValue();
		LOGGER.info("checkResponseCode "+respCode);
		assertEquals("Response Code is not matcing"+responseEnt.getBody(),respCode,actualRespCode);
	
	}
	
	@Then("^Response body should be '(.*)'$")
	public void checkResponseBodyStringMatch(String expRespMessage) throws Exception {
		
		LOGGER.info("checkResponseBodyStringMatch : expRespMessage "+expRespMessage);
		assertEquals("Response body is not matcing"+responseEnt.getBody(),responseEnt.getBody(),expRespMessage);
	}
	
	
	
	@Then("^user mobile no should be updated in DB with (\\d*)$ for id (\\d*)$")
	public void checkUserMobileNoinDB(long expMobNo,long id) throws Exception {
		
		User user = userRepository.findOne(id);
		long actMobNo = user.getMobNo();
		assertEquals(expMobNo, actMobNo);
	
	}
	

	@Given("^Mobile no is not equal to 10 digits for id (\\d*)$")
	public void checkUserMobileNoForTenDigits(long id) throws Exception {
		
		User user = userRepository.findOne(id);
		int mobNoLength = Long.toString(user.getMobNo()).length();
		assertEquals(10, mobNoLength);
	
	}
}
