package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;

/**
 * This entity is for maintaining count for likes on entity:
 * {@link GovtSchemeData}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "govtschemedatalikecount")
public class GovtSchemeDataLikeCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GovtSchemeDataCountId id;

	@Column(nullable = false)
	private Long likeCount;

	@Column(nullable = false)
	private Long dislikeCount;

	public GovtSchemeDataLikeCount() {
		super();
	}

	/**
	 * @return the id
	 */
	public GovtSchemeDataCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(GovtSchemeDataCountId id) {
		this.id = id;
	}

	/**
	 * @return the likeCount
	 */
	public Long getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the dislikeCount
	 */
	public Long getDislikeCount() {
		return dislikeCount;
	}

	/**
	 * @param dislikeCount the dislikeCount to set
	 */
	public void setDislikeCount(Long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

}
