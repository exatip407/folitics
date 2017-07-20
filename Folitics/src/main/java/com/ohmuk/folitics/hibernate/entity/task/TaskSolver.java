package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.hibernate.entity.User;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@IdClass(SolverId.class)
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class TaskSolver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "taskId", nullable = false, referencedColumnName = "id")
	private Task task;

	@Column(length = 25)
	private String status;

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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
