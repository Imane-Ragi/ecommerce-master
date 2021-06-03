package com.emsi.ecommerce.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name = "achat")
@NoArgsConstructor
@AllArgsConstructor
public class Achat  {
	@EmbeddedId
	private AchatPK id;
	
	private int quantiteAchat;
	
	@Transient
	private double prixAchat;
	
	@ManyToOne
    @MapsId("articleID")
    @JoinColumn(name = "ARTICLE_ID")
	private Article article;

	@ManyToOne
    @MapsId("clientID")
    @JoinColumn(name = "CLIENT_ID")
	private User client ;

	
}
