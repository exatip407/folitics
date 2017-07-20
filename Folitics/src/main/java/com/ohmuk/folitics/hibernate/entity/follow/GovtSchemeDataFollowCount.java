package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;

/**
 * This entity is used to maintain follow count for Opinion
 * 
 * @author
 *
 */

@Entity
@Table(name = "govtschemedatafollowcount")
public class GovtSchemeDataFollowCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GovtSchemeDataCountId id;

	@Column(nullable = false)
	private Long followCount;

	/**
	 * @return the id
	 */
	public GovtSchemeDataCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(GovtSchemeDataCountId id) {
		this.id = id;
	}

	/**
	 * @return the followCount
	 */
	public Long getFollowCount() {
		return followCount;
	}

	/**
	 * @param followCount
	 *            the followCount to set
	 */
	public void setFollowCount(Long followCount) {
		this.followCount = followCount;
	}

	public GovtSchemeDataFollowCount() {
		super();
		// TODO Auto-generated constructor stub
	}

}
