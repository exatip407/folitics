package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.Sentiment;

@Embeddable
public class GovtSchemeDataShareCountId implements Serializable{

	 /**
     * 
     */
    private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "govtSchemeDataId", referencedColumnName = "id")
    @NotNull(message = "error.GovtSchemeDataShareCountId.sentiment.notNull")
    private GovtSchemeData govtSchemeData;

	public GovtSchemeData getGovtSchemeData() {
		return govtSchemeData;
	}

	public void setGovtSchemeData(GovtSchemeData govtSchemeData) {
		this.govtSchemeData = govtSchemeData;
	}
}
