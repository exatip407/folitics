package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Opinion;

/**
 * Embeddable class to create id for entity: {@link OpinionLikeCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class OpinionLikeCountId implements Serializable {

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "opinionId", referencedColumnName = "id")
	private Opinion opinion;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the opinion
	 */
	public Opinion getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion the opinion to set
	 */
	public void setOpinion(Opinion opinion) {
		this.opinion = opinion;
	}

	

}
