package com.emsi.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.emsi.ecommerce.domaine.UserConverter;
import com.emsi.ecommerce.domaine.UserVo;
import com.emsi.ecommerce.service.IAchatService;
import com.emsi.ecommerce.service.IArticleService;
import com.emsi.ecommerce.service.IExcelService;
import com.emsi.ecommerce.service.IMailService;
import com.emsi.ecommerce.service.IUserService;
import com.emsi.ecommerce.service.model.AchatPK;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAchatService achatService;
	
	@Autowired
	private IExcelService excelService;
	
	@Autowired
	private IMailService mailService;
	
	/************************************GET***************************************/
	@RequestMapping("/listArticles")
	public String listArticles(Model m) {
		List<ArticleVo> list = articleService.getArticles();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		m.addAttribute("userName", "Welcome " + auth.getName());
		m.addAttribute("list", list);
		m.addAttribute("articleVo", new ArticleVo());
		return "/client/listArticles";
	}
	
	
	//Retourne la liste des achats de l'utilisateur courant
	@RequestMapping("/listAchat")
	public String listAchat(Model m) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();// Retourne l'utilisateur courant
		UserVo clientVo=userService.getUserByUserName(auth.getName());//auth.getName : retourne le nom de l'utilisateur courant
		List<AchatVo> list = achatService.getAchatsByClient(clientVo.getId());
		if(list != null) {
		Double totalAchat=0.0;
		for (AchatVo achatVo : list) {
			totalAchat+=achatVo.getPrixAchat();
		}
		m.addAttribute("totalAchat", totalAchat);
		}
		m.addAttribute("list", list);
		
		return "/client/listElementsPanier";
	}
	
	//Prend en parametre l'id d'un article, et elle retourne un objet achatVo pour qu'il soit rempli dans le formulaire addToPanier 
	@RequestMapping(value = "/addToPanierForm/{id}")
	public String addToPanierForm(@PathVariable Long id, Model m) {
		ArticleVo articleVo = articleService.getArticleById(id);

		
		AchatVo achatVo=new AchatVo();
		achatVo.setArticle(articleVo);
		m.addAttribute("achatVo", achatVo);
		
		return "/client/addToPanier";
	}
	

	@RequestMapping(value = "/deleteFromPanier/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable  Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserVo clientVo=userService.getUserByUserName(auth.getName());
		
		AchatPK achatPk=new AchatPK(id, clientVo.getId());
		
		achatService.delete(achatPk);
		return "redirect:/client/listAchat";
	}

	
	@RequestMapping(value = {"/changePasswordForm"}, method = RequestMethod.GET)
	public String changePasswordForm(Model m) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		UserVo clientVo=userService.getUserByUserName(auth.getName());
		
		m.addAttribute("clientVo", clientVo);
		return "/client/changePassword";
	}
	/***********************************POST****************************************/
	//Cette fonction va permettre d'ajouter un nouveau achat : une nouvelle lignePanier 
	@PostMapping(value = "/addToPanier")
	public String addToPanier(@ModelAttribute("achatVo")  @Valid AchatVo achatVo ,BindingResult result, Model model) {
		ArticleVo articleVo = articleService.getArticleById(achatVo.getArticle().getId());
		
		if(result.hasErrors() || articleVo.getQtEnStock()<achatVo.getQuantiteAchat()) {
			if(articleVo.getQtEnStock()<achatVo.getQuantiteAchat()) {
				model.addAttribute("QuantiteException", "La quantité en stock n'est pas suffisante!");	
			}
			model.addAttribute("achatVo", achatVo);
			return "/client/addToPanier";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();// Retourne l'utilisateur courant
		
		UserVo clientVo=userService.getUserByUserName(auth.getName());//auth.getName : retourne le nom de l'utilisateur courant
		

		AchatPK achatPk=new AchatPK(articleVo.getId(), clientVo.getId());// On crée la clé composée de achat 
		AchatVo achat=achatService.getAchatById(achatPk);
		
		
		if(achat != null) {
			int newQuantite=achat.getQuantiteAchat()+achatVo.getQuantiteAchat();
			achat.setQuantiteAchat(newQuantite);
			achatService.save(achat);
			
		}
		else {
			achatVo.setId(achatPk);
			achatVo.setArticle(articleVo);
			achatVo.setClient(clientVo);
			achatService.save(achatVo);
		}
	
		
		
		return "redirect:/client/listAchat";
	}

	
	//MEttre à jour un element de panier
	
	@RequestMapping(value = "/editPanier/{id}")
	public String edit(@PathVariable Long id, Model m) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserVo clientVo=userService.getUserByUserName(auth.getName());
		
		AchatPK achatPk=new AchatPK(id, clientVo.getId());
		AchatVo achatVo = achatService.getAchatById(achatPk);
		
		System.out.println(achatVo.getArticle().getDesignation());
		m.addAttribute("achatVo", achatVo);
		return "/client/editPanier";
	}
	
	//MEttre à jour un element de panier
	
		@RequestMapping(value = "/validerAchat")
		public String validerAchat(Model m) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserVo clientVo = userService.getUserByUserName(auth.getName());
			

			new Thread(new Runnable() {

	            @Override
	            public void run() {
	            	excelService.panierToExcel(clientVo);
	            	
	            	try {
	    				mailService.sendEmailWithExcelAttachement(clientVo);
	    				
	    			} catch (Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			
	            }
	        }).start();
			
			
			List<ArticleVo> list = articleService.getArticles();
			m.addAttribute("list", list);
			m.addAttribute("articleVo", new ArticleVo());
			
			m.addAttribute("panierEnvoye", "Les détails de votre achat sont envoyés à votre adresse mail!");
			
			return "/client/listArticles";
		}
		
		//MEttre à jour un element de panier
		
		@RequestMapping(value = "/searchByDescription")
		public String searchByDescription(Model m,@ModelAttribute("articleVo") ArticleVo articleVo) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserVo clientVo = userService.getUserByUserName(auth.getName());
	
			if(articleVo.getDesignation().isEmpty()) {
				return "redirect:/client/listArticles";
			}
			else
			{
				List<ArticleVo> list = articleService.getArticlesByDesignation(articleVo.getDesignation());
				m.addAttribute("list", list);
				m.addAttribute("articleVo", new ArticleVo());
				return "/client/listArticles";
				
			}
			
			
		}
		
		@RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
		public String changePassword(@ModelAttribute("clientVo") @Valid UserVo clientVo) {
			
			UserVo client = userService.getUserByUserName(clientVo.getUsername());
			client.setPassword(clientVo.getPassword());
		    userService.save(client);
		 
		    return "redirect:/client/listArticles";
		}
		
	
		
	
	
}
