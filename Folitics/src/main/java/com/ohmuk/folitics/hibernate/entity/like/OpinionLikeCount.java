package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.Opinion;

/**
 * This entity is for maintaining count for likes on entity: {@link Opinion}
 * 
 * @author Abhishek
 *
 */

@Entity
@Table(name = "opinionlikecount")
@NamedQuery(name = "OpinionLikeCount.findAll", query = "SELECT o FROM OpinionLikeCount o")
@PrimaryKeyJoinColumn(name = "id")
public class OpinionLikeCount implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OpinionLikeCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.opinionLikeCount.likeCount.notNull")
	private Long likeCount;

	@Column(nullable = false)
	@NotNull(message = "error.opinionLikeCount.dislikeCount.notNull")
	private Long dislikeCount;

	public OpinionLikeCount() {

	}

	public Long getLikeCount() {
		return likeCount;
	}

	/**
	 * @return the id
	 */
	public OpinionLikeCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(OpinionLikeCountId id) {
		this.id = id;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(Long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

}
