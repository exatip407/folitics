package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Opinion;

/**
 * Embeddable class to create id for entity: {@link OpinionCommentCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class OpinionCommentCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "opinionId", referencedColumnName = "id")
	private Opinion opinion;

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
