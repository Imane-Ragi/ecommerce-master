package com.emsi.ecommerce.domaine;

import java.util.ArrayList;
import java.util.List;

import com.emsi.ecommerce.service.model.Article;


public class ArticleConverter {
	
	public static ArticleVo toVo(Article bo) {
		if (bo == null)
			return null;
		ArticleVo vo=new ArticleVo();
		vo.setId(bo.getId());
		vo.setPrixUnitaire(bo.getPrixUnitaire());
		vo.setDesignation(bo.getDesignation());
		vo.setQtEnStock(bo.getQtEnStock());
		vo.setReference(bo.getReference());
		return vo;
		
	}
	
	public static Article toBo(ArticleVo vo) {
		if (vo == null)
			return null;
		Article bo=new Article();
		bo.setId(vo.getId());
		bo.setPrixUnitaire(vo.getPrixUnitaire());
		bo.setDesignation(vo.getDesignation());
		bo.setQtEnStock(vo.getQtEnStock());
		bo.setReference(vo.getReference());
		return bo;
	}
	
	public static List<ArticleVo> toVoList(List<Article> boList) {
		if (boList == null || boList.isEmpty())
			return null;
		List<ArticleVo> voList = new ArrayList<>();
		for (Article article : boList) {
			voList.add(toVo(article));
		}
		return voList;
	}

}
