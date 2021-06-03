package com.emsi.ecommerce.configuration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import com.emsi.ecommerce.domaine.RoleVo;
import com.emsi.ecommerce.domaine.UserVo;

import com.emsi.ecommerce.service.IRoleService;
import com.emsi.ecommerce.service.IUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements CommandLineRunner {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;


	@Override
	// L'authentification
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	// L'authorisation
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/createAccount").permitAll();
		http.authorizeRequests().antMatchers("/welcome").hasAnyAuthority("ADMIN", "CLIENT");
		http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers("/client/**").hasAuthority("CLIENT");
		

		// List<ActionVo> list = actionService.getAll();
		// for (ActionVo vo : list) {
		// http.authorizeRequests().antMatchers(vo.getAction()).hasAnyAuthority(toTab(vo.getRoles()));
		//
		// }

		http.authorizeRequests().anyRequest().authenticated();

	
		http.formLogin().loginPage("/login");
		http.formLogin().failureUrl("/login?error=true");
		http.formLogin().defaultSuccessUrl("/redirection");
		http.formLogin().usernameParameter("usrname");
		http.formLogin().passwordParameter("passwd");
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		http.logout().logoutSuccessUrl("/");
		http.exceptionHandling().accessDeniedPage("/access-denied");

	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}


	@Override
	public void run(String... args) throws Exception {
		/*boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		roleService.save(new RoleVo("ADMIN"));
		roleService.save(new RoleVo("CLIENT"));
		
		RoleVo roleAdmin = roleService.getRoleByName("ADMIN");
		RoleVo roleClient = roleService.getRoleByName("CLIENT");
		
		UserVo admin = new UserVo("adminF", "adminL","admin","admin","admin@admin.com","Rabat","0612345678", enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, Arrays.asList(roleAdmin));
		UserVo client1 = new UserVo("El makhtoum", "safae","safae","safae","elm.safae98@gmail.com","Rabat","0612345678", enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, Arrays.asList(roleClient));
	

		userService.save(admin);
		userService.save(client1);*/

	}
}
