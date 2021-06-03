package com.emsi.ecommerce.service;

import java.util.List;

import com.emsi.ecommerce.domaine.RoleVo;

public interface IRoleService {
	void save(RoleVo roleVo);
	List<RoleVo> getAllRoles();
	RoleVo getRoleByName(String role);
}
