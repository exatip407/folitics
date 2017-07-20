package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.task.Task;

/**
 * This entity is for maintaining count for comment on entity: {@link Task}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "taskcommentcount")
public class TaskCommentCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskCommentCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.commentCount.commentCount.notNull")
	private Long commentCount;

	/**
	 * @return the id
	 */
	public TaskCommentCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(TaskCommentCountId id) {
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

	public TaskCommentCount() {
		super();
	}

}
