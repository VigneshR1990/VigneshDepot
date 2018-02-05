package com.vehicle.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vehicle.review.domain.User;
import com.vehicle.review.repository.UserRepository;
import com.vehicle.review.service.ServiceException;
import com.vehicle.review.service.UserService;



@RunWith(SpringRunner.class)
public class UserServiceImplTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTests.class);
	
	
	@TestConfiguration
	static class UserServiceImplTestConfiguration{
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}
	
	@Autowired
	UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		
		User user = new User( 1L,"vignesh","vignesh.r90@gmail.com","pwd12345");
		Mockito.when(userRepository.findOne(Matchers.eq(1L))).thenReturn(user);
		Mockito.when(userRepository.findOne(AdditionalMatchers.not(Matchers.eq(1L)))).thenReturn(null);
	}
	
	@Test
	public void whenValidId_ThenUserShouldBeFound() {
		User user = userService.get(1L);
		LOGGER.info("User : whenValidId_ThenUserShouldBeFound : " + user);
		assertThat(user.getId()).isEqualTo(1L);
	}
	
	@Test(expected = ServiceException.class)
	public void whenInValidId_ThenShouldThrowServiceException() {
		User user = userService.get(2L);
		
	}

}
