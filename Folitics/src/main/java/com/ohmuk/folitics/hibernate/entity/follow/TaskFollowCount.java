package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This entity is used to maintain follow count for Task
 * 
 * @author Harish
 *
 */
@Entity
@Table(name = "taskfollowcount")
@PrimaryKeyJoinColumn(name = "id")
public class TaskFollowCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskFollowCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.TaskFollowCount.count.notNull")
	private Long followCount;

	public TaskFollowCount() {

	}

	/**
	 * @return the id
	 */
	public TaskFollowCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(TaskFollowCountId id) {
		this.id = id;
	}

	/**
	 * @return the followCount
	 */
	public Long getFollowCount() {
		return followCount;
	}

	/**
	 * @param followCount
	 *            the followCount to set
	 */
	public void setFollowCount(Long followCount) {
		this.followCount = followCount;
	}

}
