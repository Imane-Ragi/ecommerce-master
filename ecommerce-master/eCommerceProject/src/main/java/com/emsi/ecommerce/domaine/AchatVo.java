package com.emsi.ecommerce.domaine;

import javax.validation.constraints.Min;

import com.emsi.ecommerce.service.model.AchatPK;
import com.emsi.ecommerce.service.model.Article;
import com.emsi.ecommerce.service.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class AchatVo {
	private AchatPK id;
	@Min(value = 1, message="La quantité doit etre supérieur à 1")
	private int quantiteAchat;
	
	private double prixAchat;
	
	private ArticleVo article;

	private UserVo client ;

	
	public AchatVo(int quantiteAchat, double prixAchat, ArticleVo articleVo, UserVo clientVo) {
		super();
		this.quantiteAchat = quantiteAchat;
		this.prixAchat = prixAchat;
		this.article = articleVo;
		this.client = clientVo;
	}


	public AchatVo(AchatPK id, int quantiteAchat, double prixAchat, ArticleVo article, UserVo client) {
		super();
		this.id = id;
		this.quantiteAchat = quantiteAchat;
		this.prixAchat = prixAchat;
		this.article = article;
		this.client = client;
	}

	
}
