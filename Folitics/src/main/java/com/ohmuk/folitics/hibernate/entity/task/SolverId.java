package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ohmuk.folitics.hibernate.entity.User;

public class SolverId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "taskId", nullable = false, referencedColumnName = "id")
	private Task task;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

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
