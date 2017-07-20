package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.task.Task;

/**
 * Embeddable class to create id for entity {@link TaskShareCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class TaskShareCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "taskId", referencedColumnName = "id")
	@NotNull(message = "error.TaskShareCountId.task.notNull")
	private Task task;

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

}
