package com.emsi.ecommerce.service.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "article")
@NoArgsConstructor
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	@NotEmpty(message = "*Merci de saisir la reference")
	private String reference;
	

	@NotEmpty(message = "*Merci de saisir la désignation")
	private String designation;
	
	
	private int qtEnStock;
	
	private double prixUnitaire;
	
	@OneToMany(mappedBy = "article")
	private List<Achat> achat;
	

}
