package com.emsi.ecommerce.domaine;



import javax.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleVo {

	private Long id;
	private String reference;
	private String designation;

	@Min(value = 0, message="La quantité doit etre positive")
	private int qtEnStock;

	private double prixUnitaire;
	public ArticleVo(String reference, String designation, int qtEnStock, double prixUnitaire) {
		super();
		this.reference = reference;
		this.designation = designation;
		this.qtEnStock = qtEnStock;
		this.prixUnitaire = prixUnitaire;
	}

	

}
