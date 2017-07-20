package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.Fact;

/**
 * This entity is for maintaining count for likes on entity: {@link Fact}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "factlikecount")
public class FactLikeCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FactLikeCountId id;

	@Column(nullable = false)
	private Long likeCount;

	@Column(nullable = false)
	private Long dislikeCount;

	public FactLikeCount() {

	}

	/**
	 * @return the id
	 */
	public FactLikeCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(FactLikeCountId id) {
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
