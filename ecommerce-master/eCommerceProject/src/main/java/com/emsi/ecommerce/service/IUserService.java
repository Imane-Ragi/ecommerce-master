package com.emsi.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.emsi.ecommerce.domaine.UserVo;

public interface IUserService extends UserDetailsService {

	void save(UserVo client);
    UserVo getUserByUserName(String username);
    UserVo getUserByEmail(String email);

}
