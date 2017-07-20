package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.Response;

/**
 * This entity is for maintaining count for likes on entity: {@link Response}
 * 
 * @author Harish
 *
 */
@Entity
@Table(name = "responselikecount")
public class ResponseLikeCount  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@EmbeddedId
	private ResponseLikeCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.ResponseLikeCount.count.notNull")
	private Long likeCount;

	@Column(nullable = false)
	@NotNull(message = "error.ResponseLikeCount.count.notNull")
	private Long dislikeCount;

	/**
	 * @return the id
	 */
	public ResponseLikeCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ResponseLikeCountId id) {
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
