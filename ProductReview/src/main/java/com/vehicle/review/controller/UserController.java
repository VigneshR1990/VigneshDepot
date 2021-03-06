package com.vehicle.review.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.review.domain.User;
import com.vehicle.review.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody @Valid User user,HttpServletRequest request) throws URISyntaxException{
		
		long savedUserId = userService.create(user);
		LOGGER.debug("User Id "+savedUserId);
		HttpHeaders headers = new HttpHeaders(); 
		//URI location = linkTo(methodOn(this.getClass()).getById(savedUserId)).toUri(); 
		//LOGGER.debug(location.toString());
		String uri = request.getRequestURI();
		LOGGER.debug("uri"+uri);
		URI location = new URI("http://localhost:8888"+uri+savedUserId);
		headers.setLocation(location);
		return new ResponseEntity<String>("User registration successful",headers, HttpStatus.OK);
		
	}
	
	/*@GetMapping(value = "/{id}")
	public ResponseEntity<User> getById(@PathVariable long id) {
		
		
		return null;
		
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable Long id) {
		User user = userService.get(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
	}
}
