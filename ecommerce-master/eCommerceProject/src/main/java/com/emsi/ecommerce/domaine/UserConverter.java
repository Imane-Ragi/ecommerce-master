package com.emsi.ecommerce.domaine;

import java.util.ArrayList;
import java.util.List;

import com.emsi.ecommerce.service.model.User;

public class UserConverter {
	
	public static UserVo toVo(User bo) {
		if (bo == null)
			return null;
		UserVo vo = new UserVo();
		vo.setId(bo.getId());
		vo.setUsername(bo.getUsername());
		vo.setPassword(bo.getPassword());
		
		vo.setNom(bo.getNom());
		vo.setPrenom(bo.getPrenom());
		vo.setEmail(bo.getEmail());
		vo.setVille(bo.getVille());
		vo.setTel(bo.getTel());
		
		vo.setRoles(RoleConverter.toVoList(bo.getRoles()));
		//vo.setAchat(AchatConverter.toVoList(bo.getAchat()));
		
		vo.setAccountNonExpired(bo.isAccountNonExpired());
		vo.setAccountNonLocked(bo.isAccountNonLocked());
		vo.setEnabled(bo.isEnabled());
		vo.setCredentialsNonExpired(bo.isCredentialsNonExpired());
		
		
		return vo;
	}

	public static User toBo(UserVo vo) {
		if (vo == null)
			return null;
		User bo = new User();
		if (vo.getId() != null)
			bo.setId(vo.getId());
		bo.setUsername(vo.getUsername());
		bo.setPassword(vo.getPassword());
		bo.setNom(vo.getNom());
		bo.setPrenom(vo.getPrenom());
		bo.setEmail(vo.getEmail());
		bo.setVille(vo.getVille());
		bo.setTel(vo.getTel());
		
		bo.setRoles(RoleConverter.toBoList(vo.getRoles()));
		//bo.setAchat(AchatConverter.toVoList(vo.getAchat()));
		
		bo.setAccountNonExpired(vo.isAccountNonExpired());
		bo.setAccountNonLocked(vo.isAccountNonLocked());
		bo.setEnabled(vo.isEnabled());
		bo.setCredentialsNonExpired(vo.isCredentialsNonExpired());
		return bo;
	}

	public static List<UserVo> toVoList(List<User> boList) {
		if (boList == null || boList.isEmpty())
			return null;
		List<UserVo> voList = new ArrayList<>();
		for (User user : boList) {
			voList.add(toVo(user));
		}
		return voList;
	}

	public static List<User> toBoList(List<UserVo> voList) {
		if (voList == null || voList.isEmpty())
			return null;
		List<User> boList = new ArrayList<>();
		for (UserVo userVo : voList) {
			boList.add(toBo(userVo));
		}
		return boList;
	}
}
