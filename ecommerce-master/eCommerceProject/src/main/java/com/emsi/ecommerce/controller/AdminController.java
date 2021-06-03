package com.emsi.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emsi.ecommerce.domaine.AchatVo;
import com.emsi.ecommerce.domaine.ArticleVo;
import com.emsi.ecommerce.domaine.UserVo;
import com.emsi.ecommerce.service.IAchatService;
import com.emsi.ecommerce.service.IArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private IAchatService achatService;
	
	/************************************GET***************************************/
	@RequestMapping("/articles/list")
	public String listArticles(Model m) {
		List<ArticleVo> list = articleService.getArticles();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		m.addAttribute("userName", "Welcome " + auth.getName());
		m.addAttribute("list", list);
		return "/admin/articles/list";
	}
	
	@RequestMapping(value = "/articles/newArticle")
	public String getArticleForm(Model m) {
		m.addAttribute("articleVo", new ArticleVo());
		return "/admin/articles/add";
	}
	
	@RequestMapping(value = "/articles/edit/{id}")
	public String edit(@PathVariable Long id, Model m) {
		ArticleVo articleVo = articleService.getArticleById(id);
		m.addAttribute("articleVo", articleVo);
		return "/admin/articles/edit";
	}
	
	@RequestMapping(value = "/articles/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id ,Model model) {
		if(achatService.findByArticle(id) != null) {
			model.addAttribute("deleteException", "Cet article ne peut etre supprimé: L'article existe dans un client panier!");	
			List<ArticleVo> list = articleService.getArticles();
			model.addAttribute("list", list);
			return "/admin/articles/list";
		}
		articleService.delete(id);
		return "redirect:/admin/articles/list";
	}
	
	/***********************************POST****************************************/
	@PostMapping(value = "/articles/Save")
	public String Save(@ModelAttribute("articleVo")  @Valid ArticleVo articleVo ,BindingResult result, Model model) {
	
		ArticleVo articleByDesignation=articleService.getArticleByDesignation(articleVo.getDesignation());
		ArticleVo articleByReference=articleService.getArticleByReference(articleVo.getReference());
		
		if(result.hasErrors() || articleByReference !=null || articleByDesignation!=null || articleVo.getPrixUnitaire()<0) {
			if(articleByReference != null) {
				model.addAttribute("referenceException", "Cette référence existe déja!");
			}
			if(articleByDesignation != null) {
				model.addAttribute("designationException", "Cette désignation existe déja!");			
			}
			if( articleVo.getPrixUnitaire()<0) {
				model.addAttribute("prixUnitaireException", "Le prix doit etre positif!");	
			}
			model.addAttribute("articleVo", articleVo);
			return "/admin/articles/add";
		}
		
		articleService.save(articleVo);
		return "redirect:/admin/articles/list";
	}
	
	@PostMapping(value = "/articles/edit")
	public String edit(@ModelAttribute("articleVo")  @Valid ArticleVo articleVo ,BindingResult result, Model model) {
	
		ArticleVo articleById=articleService.getArticleById(articleVo.getId());
		ArticleVo articleByDesignation=null;
		if(!articleById.getDesignation().equals(articleVo.getDesignation())){
			 articleByDesignation=articleService.getArticleByDesignation(articleVo.getDesignation());
			
		}
		
		
		if(result.hasErrors() ||articleVo.getPrixUnitaire()<0 || articleByDesignation != null) {
			
			if( articleVo.getPrixUnitaire()<0) {
				model.addAttribute("prixUnitaireException", "Le prix doit etre positif!");	
			}
			if(articleByDesignation != null) {
				model.addAttribute("designationException", "Cette désignation existe déja!");			
			}
			model.addAttribute("articleVo", articleVo);
			return "/admin/articles/edit";
		}
		
		articleService.save(articleVo);
		return "redirect:/admin/articles/list";
	}
	

	
	

	

}
