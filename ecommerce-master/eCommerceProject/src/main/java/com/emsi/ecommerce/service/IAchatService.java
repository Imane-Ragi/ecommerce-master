package com.emsi.ecommerce.service;

import java.util.List;

import com.emsi.ecommerce.domaine.AchatVo;
import com.emsi.ecommerce.service.model.AchatPK;



public interface IAchatService {
	List<AchatVo> getAchatsByClient(Long id);
	void save(AchatVo achatVo);
	AchatVo getAchatById(AchatPK id);
	void delete(AchatPK id);
	void deleteByClient(Long idClient);
	AchatVo findByArticle(Long articleID);

}
