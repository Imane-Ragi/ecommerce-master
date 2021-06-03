package com.emsi.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsi.ecommerce.service.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
	List <Article> findByDesignationIgnoreCaseContaining(String designation);
	Article findByDesignation(String designation);
	Article findByReference(String reference);

}
