package com.emsi.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsi.ecommerce.service.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	List<Role> findByRole(String role);
}
