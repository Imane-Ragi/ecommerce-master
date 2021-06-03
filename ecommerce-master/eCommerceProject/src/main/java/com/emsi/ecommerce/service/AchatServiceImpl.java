package com.emsi.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsi.ecommerce.dao.AchatRepository;
import com.emsi.ecommerce.domaine.AchatConverter;
import com.emsi.ecommerce.domaine.AchatVo;
import com.emsi.ecommerce.domaine.ArticleConverter;
import com.emsi.ecommerce.service.model.Achat;
import com.emsi.ecommerce.service.model.AchatPK;

@Service
public class AchatServiceImpl implements IAchatService {

	@Autowired
	AchatRepository achatRepository;
	
	@Override
	public List<AchatVo> getAchatsByClient(Long clientID) {
		List<AchatVo> list = AchatConverter.toVoList(achatRepository.findByIdClientID(clientID));
		if(list != null) {
			for (AchatVo achatVo : list) {
				achatVo.setPrixAchat(achatVo.getQuantiteAchat()*achatVo.getArticle().getPrixUnitaire());
			}
			
			}
		return list;
	}

	@Override
	public void save(AchatVo achatVo) {
		achatRepository.save(AchatConverter.toBo(achatVo));

	}

	@Override
	public AchatVo getAchatById(AchatPK achatpk) {
		boolean trouve = achatRepository.existsById(achatpk);
		if (!trouve)
			return null;
		return AchatConverter.toVo(achatRepository.getOne(achatpk));
	}

	@Override
	public void delete(AchatPK achatpk) {
		achatRepository.deleteById(achatpk);

	}

	@Override
	public void deleteByClient(Long idClient) {
		achatRepository.deleteByIdClientID(idClient);
		
	}

	@Override
	public AchatVo findByArticle(Long articleID) {
		AchatVo achat=AchatConverter.toVo(achatRepository.findOneByIdArticleID(articleID));
		return achat;
		
	}

}
