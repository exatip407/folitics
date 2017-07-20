package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Opinion;

/**
 * Composite Key for class opinionFollowCount
 * 
 * @author
 *
 */
@Embeddable
public class OpinionFollowCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "opinionId", referencedColumnName = "id")
	private Opinion opinion;
	
	public OpinionFollowCountId(){
		
	}
	
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
