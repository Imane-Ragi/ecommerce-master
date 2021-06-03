package com.emsi.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsi.ecommerce.service.model.Achat;
import com.emsi.ecommerce.service.model.AchatPK;
import com.emsi.ecommerce.service.model.Article;

public interface AchatRepository extends JpaRepository<Achat, AchatPK> {
	
	List<Achat> findByIdClientID(Long clientID);
	List<Achat> deleteByIdClientID(Long clientID);
	Achat findOneByIdArticleID(Long articleID);
}
