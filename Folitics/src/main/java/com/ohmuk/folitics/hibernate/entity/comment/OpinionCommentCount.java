package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.Opinion;

/**
 * This entity is for maintaining count for comment on entity: {@link Opinion}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "opinioncommentcount")
public class OpinionCommentCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OpinionCommentCountId id;

	@Column(nullable = false)
	private Long commentCount;

	public OpinionCommentCount() {

	}

	/**
	 * @return the id
	 */
	public OpinionCommentCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(OpinionCommentCountId id) {
		this.id = id;
	}

	/**
	 * @return the commentCount
	 */
	public Long getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount
	 *            the commentCount to set
	 */
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

}
