package com.emsi.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsi.ecommerce.dao.ArticleRepository;
import com.emsi.ecommerce.domaine.ArticleConverter;
import com.emsi.ecommerce.domaine.ArticleVo;
import com.emsi.ecommerce.domaine.UserConverter;
import com.emsi.ecommerce.domaine.UserVo;
import com.emsi.ecommerce.service.model.Article;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public List<ArticleVo> getArticles() {
		List<Article> list = articleRepository.findAll();
		return ArticleConverter.toVoList(list);
	}

	@Override
	public void save(ArticleVo articleVo) {
		articleRepository.save(ArticleConverter.toBo(articleVo));

	}

	@Override
	public ArticleVo getArticleById(Long id) {
		boolean trouve = articleRepository.existsById(id);
		if (!trouve)
			return null;
		return ArticleConverter.toVo(articleRepository.getOne(id));
	}

	@Override
	public void delete(Long id) {
		articleRepository.deleteById(id);

	}

	@Override
	public List<ArticleVo> getArticlesByDesignation(String designation) {
		List<Article> list = articleRepository.findByDesignationIgnoreCaseContaining(designation);
		return ArticleConverter.toVoList(list);
	}

	@Override
	public ArticleVo getArticleByDesignation(String designation) {
		ArticleVo article=ArticleConverter.toVo(articleRepository.findByDesignation(designation));
		return article;
	}

	@Override
	public ArticleVo getArticleByReference(String reference) {
		ArticleVo article=ArticleConverter.toVo(articleRepository.findByReference(reference));
		return article;
	}

}
