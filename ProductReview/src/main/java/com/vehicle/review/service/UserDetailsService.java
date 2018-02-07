package com.vehicle.review.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vehicle.review.domain.User;
import com.vehicle.review.repository.UserRepository;
import com.vehicle.review.service.impl.UserServiceImpl;
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);
	@Autowired
	private UserRepository userRepository;
	public UserDetailsService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		if(null == user)
				throw new UsernameNotFoundException("No user found with email : "+email);
		LOGGER.debug("loadUserByUsername : user.getEmail() "+user.getEmail());
		LOGGER.debug("loadUserByUsername : user.getPassword() "+user.getPassword());
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.isEnabled(),true,true,true,getGrantedAuthority()) ;
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthority() {
		// TODO Auto-generated method stub
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("USER"));
		return authorities;
	}

}
