package com.vehicle.review.service;

import com.vehicle.review.domain.User;

public interface UserService {
	
	public long create(User user);

	public User get(Long id);
	
	

}
