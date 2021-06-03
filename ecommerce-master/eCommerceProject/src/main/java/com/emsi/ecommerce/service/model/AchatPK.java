package com.emsi.ecommerce.service.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchatPK implements Serializable {
	@Column(name = "ARTICLE_ID")
    private Long articleID;

    @Column(name = "CLIENT_ID")
    private Long clientID;

}
