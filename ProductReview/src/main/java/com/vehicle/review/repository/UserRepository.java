package com.vehicle.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle.review.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByEmail(String email);
}
