package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity for comment on entity: {@link Task}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "taskcomment")
public class TaskComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "error.componentComment.componentId.notNull")
	private Long componentId;

	@Column(nullable = false)
	@NotNull(message = "error.componentComment.userId.notNull")
	private Long userId;

	@Column(nullable = false, length = 128)
	@NotNull(message = "error.componentComment.componentType.notNull")
	private String componentType;

	@Column(nullable = false)
	@NotNull(message = "error.componentComment.createdTime.notNull")
	private Timestamp createdTime;

	@Column(nullable = false)
	@NotNull(message = "error.componentComment.editedTime.notNull")
	private Timestamp editedTime;

	@Column(nullable = false, length = 512)
	@NotNull(message = "error.componentComment.comment.notNull")
	private String comment;

	public TaskComment() {
		setCreatedTime(DateUtils.getSqlTimeStamp());
		setEditedTime(DateUtils.getSqlTimeStamp());
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the componentId
	 */
	public Long getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId
	 *            the componentId to set
	 */
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the componentType
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType
	 *            the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	/**
	 * @return the createdTime
	 */
	public Timestamp getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the editedTime
	 */
	public Timestamp getEditedTime() {
		return editedTime;
	}

	/**
	 * @param editedTime
	 *            the editedTime to set
	 */
	public void setEditedTime(Timestamp editedTime) {
		this.editedTime = editedTime;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
