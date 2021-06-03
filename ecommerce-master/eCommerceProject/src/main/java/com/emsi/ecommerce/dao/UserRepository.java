package com.emsi.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsi.ecommerce.service.model.User;
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String userName);
	User findByEmail(String email);
	
}
