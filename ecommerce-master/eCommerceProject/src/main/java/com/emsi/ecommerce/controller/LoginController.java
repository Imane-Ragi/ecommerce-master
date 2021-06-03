package com.emsi.ecommerce.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.emsi.ecommerce.domaine.RoleVo;
import com.emsi.ecommerce.domaine.UserConverter;
import com.emsi.ecommerce.domaine.UserVo;
import com.emsi.ecommerce.service.IMailService;
import com.emsi.ecommerce.service.IRoleService;
import com.emsi.ecommerce.service.IUserService;
import com.emsi.ecommerce.service.model.User;

@Controller
public class LoginController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IMailService mailService;
	
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(Model m) {
		m.addAttribute("clientVo", new UserVo());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value = {"/createAccount"}, method = RequestMethod.POST)
	public String createAccount(@ModelAttribute("clientVo") @Valid UserVo clientVo , BindingResult result, Model model) {
		if (result.hasErrors()) {
            return "/login";
        }
		UserVo UserByEmail=userService.getUserByEmail(clientVo.getEmail());
		UserVo UserByUsername=userService.getUserByUserName(clientVo.getUsername());
		
		if(UserByEmail !=null || UserByUsername!=null) {
			if(UserByEmail != null) {
				model.addAttribute("EmailException", "Cet Email existe déja");
			}
			if(UserByUsername != null) {
				model.addAttribute("UsernameException", "Ce username existe déja");			
			}
			model.addAttribute("clientVo", clientVo);
			return "/login";
		}
		
		
		
		
		RoleVo roleClient = roleService.getRoleByName("CLIENT");
	    clientVo.setRoles(Arrays.asList(roleClient));
	    mailService.sendValidationAccountEmail(clientVo);
	    userService.save(clientVo);
		
		return "/login";
	}
	

	
	@RequestMapping(value = "/redirection", method = RequestMethod.GET)
	public String redirection() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		UserVo currentUser=userService.getUserByUserName(auth.getName());
		if(currentUser.getRoles().get(0).getRole().equals("CLIENT")) {
			return "redirect:/client/listArticles";
		}
		else 
			return "redirect:/admin/articles/list";
		
		
	}


	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public ModelAndView accessdenied() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

}
