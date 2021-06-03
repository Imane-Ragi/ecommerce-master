package com.emsi.ecommerce.domaine;

import java.util.ArrayList;
import java.util.List;

import com.emsi.ecommerce.service.model.Achat;

public class AchatConverter {
	public static AchatVo toVo(Achat bo) {
		if (bo == null)
			return null;
		AchatVo vo=new AchatVo();
		vo.setId(bo.getId());
		vo.setArticle(ArticleConverter.toVo(bo.getArticle()));
		vo.setClient(UserConverter.toVo(bo.getClient()));
		vo.setPrixAchat(bo.getPrixAchat());
		vo.setQuantiteAchat(bo.getQuantiteAchat());
		return vo;
		
	}
	
	public static Achat toBo(AchatVo vo) {
		if (vo == null)
			return null;
		Achat bo=new Achat();
		bo.setId(vo.getId());
		bo.setArticle(ArticleConverter.toBo(vo.getArticle()));
		bo.setClient(UserConverter.toBo(vo.getClient()));
		bo.setPrixAchat(vo.getPrixAchat());
		bo.setQuantiteAchat(vo.getQuantiteAchat());
		return bo;
	}
	
	public static List<AchatVo> toVoList(List<Achat> boList) {
		if (boList == null || boList.isEmpty())
			return null;
		List<AchatVo> voList = new ArrayList<>();
		for (Achat Achat : boList) {
			voList.add(toVo(Achat));
		}
		return voList;
	}
	

}
