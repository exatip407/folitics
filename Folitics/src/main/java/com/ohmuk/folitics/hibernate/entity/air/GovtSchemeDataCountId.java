package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;


import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
@Embeddable
public class GovtSchemeDataCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "govtSchemeId", referencedColumnName = "id")
	private GovtSchemeData govtSchemeData;

	/**
	 * @return the govtSchemeData
	 */
	public GovtSchemeData getGovtSchemeData() {
		return govtSchemeData;
	}

	/**
	 * @param govtSchemeData
	 *            the govtSchemeData to set
	 */
	public void setGovtSchemeData(GovtSchemeData govtSchemeData) {
		this.govtSchemeData = govtSchemeData;
	}

}
