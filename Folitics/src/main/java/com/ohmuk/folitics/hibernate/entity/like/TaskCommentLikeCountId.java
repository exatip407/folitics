package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;

/**
 * Embeddable class to create id for entity: {@link TaskCommentLikeCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class TaskCommentLikeCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "taskCommentId", referencedColumnName = "id")
	private TaskComment taskComment;

	/**
	 * @return the taskComment
	 */
	public TaskComment getTaskComment() {
		return taskComment;
	}

	/**
	 * @param taskComment
	 *            the taskComment to set
	 */
	public void setTaskComment(TaskComment taskComment) {
		this.taskComment = taskComment;
	}

}
