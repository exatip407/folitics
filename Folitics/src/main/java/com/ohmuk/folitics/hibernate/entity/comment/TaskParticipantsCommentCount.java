package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * This entity is for maintaining count for comment on entity:
 * {@link TaskParticipantsCommentCount}
 * 
 * @author Harish
 *
 */

@Entity
public class TaskParticipantsCommentCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskParticipantsCommentCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.commentCount.commentCount.notNull")
	private Long commentCount;

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

	/**
	 * @return the id
	 */
	public TaskParticipantsCommentCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TaskParticipantsCommentCountId id) {
		this.id = id;
	}

}
