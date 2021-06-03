package com.emsi.ecommerce.domaine;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.emsi.ecommerce.service.model.Achat;
import com.emsi.ecommerce.service.model.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo {

	private Long id;

	private String nom;
	private String prenom;
	private String username;
	private String password;
	private String email;
	private String ville;
	private String tel;
	
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	private boolean accountNonLocked;
	
	private List<RoleVo> roles = new ArrayList<RoleVo>();
	//private List<AchatVo> achat; ajoute la aussi au constructeur 
	
	public UserVo(String nom, String prenom, String username, String password, String email, String ville, String tel,
			List<RoleVo> roles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.password = password;
		this.email = email;
		this.ville = ville;
		this.tel = tel;
		this.roles = roles;
		//this.achat = achat;
	}

	public UserVo(String nom, String prenom, String username, String password, String email, String ville, String tel,
			boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			List<RoleVo> roles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.password = password;
		this.email = email;
		this.ville = ville;
		this.tel = tel;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.roles = roles;
		//this.achat = achat;
	}
	
	
	
	
}
