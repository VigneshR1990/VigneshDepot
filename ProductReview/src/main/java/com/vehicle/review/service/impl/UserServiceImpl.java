package com.vehicle.review.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.review.domain.User;
import com.vehicle.review.repository.UserRepository;
import com.vehicle.review.service.ServiceException;
import com.vehicle.review.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public long create(User user) {
		// TODO Auto-generated method stub
		User savedUser = userRepository.save(user);
		LOGGER.debug("Created User Id is " + savedUser.getId());
		return savedUser.getId();
	}

	@Override
	public User get(Long id) {
		// TODO Auto-generated method stub

		User user = userRepository.findOne(id);
		if (null == user)
			throw new ServiceException("ID is Invalid", "INVALID_ID");
		return user;
	}

}
