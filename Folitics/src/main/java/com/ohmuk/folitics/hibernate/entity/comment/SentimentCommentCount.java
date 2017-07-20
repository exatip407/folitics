package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.task.Task;

/**
 * This entity is for maintaining count for comment on entity: {@link Task}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "sentimentcommentcount")
public class SentimentCommentCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SentimentCommentCountId id;

	@Column(nullable = false)
	private Long commentCount;

	public SentimentCommentCount() {

	}

	/**
	 * @return the id
	 */
	public SentimentCommentCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(SentimentCommentCountId id) {
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
