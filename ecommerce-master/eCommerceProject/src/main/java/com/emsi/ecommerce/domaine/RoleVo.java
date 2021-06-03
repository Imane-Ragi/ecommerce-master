package com.emsi.ecommerce.domaine;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleVo {
	private Long id;
	private String role;
	public RoleVo(String role) {
		this.role = role;
	}
	
	
}
