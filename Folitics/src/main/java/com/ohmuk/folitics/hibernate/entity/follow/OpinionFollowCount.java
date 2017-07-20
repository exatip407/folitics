package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This entity is used to maintain follow count for Opinion
 * 
 * @author
 *
 */

@Entity
@Table(name = "opinionfollowCount")
@PrimaryKeyJoinColumn(name = "id")
public class OpinionFollowCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OpinionFollowCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.OpinionFollowCount.count.notNull")
	private Long followCount;

	/**
	 * @return the followCount
	 */
	public Long getFollowCount() {
		return followCount;
	}

	/**
	 * @return the id
	 */
	public OpinionFollowCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(OpinionFollowCountId id) {
		this.id = id;
	}

	/**
	 * @param followCount
	 *            the followCount to set
	 */
	public void setFollowCount(Long followCount) {
		this.followCount = followCount;
	}

	public OpinionFollowCount() {

	}

}
