package com.emsi.ecommerce.service;

import java.util.List;

import com.emsi.ecommerce.domaine.ArticleVo;

public interface IArticleService {
	List<ArticleVo> getArticles();
	void save(ArticleVo articleVo);
	ArticleVo getArticleById(Long id);
	void delete(Long id);
	List<ArticleVo> getArticlesByDesignation(String designation);
	ArticleVo getArticleByDesignation(String designation);
	ArticleVo getArticleByReference(String reference);
}
