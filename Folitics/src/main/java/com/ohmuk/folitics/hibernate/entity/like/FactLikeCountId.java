package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Fact;

/**
 * Embeddable class to create id for entity: {@link FactLikeCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class FactLikeCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "factId", referencedColumnName = "id")
	private Fact fact;

	/**
	 * @return the fact
	 */
	public Fact getFact() {
		return fact;
	}

	/**
	 * @param fact
	 *            the fact to set
	 */
	public void setFact(Fact fact) {
		this.fact = fact;
	}

	

}
