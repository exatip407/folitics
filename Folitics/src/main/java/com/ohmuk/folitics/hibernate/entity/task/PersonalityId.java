package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



public class PersonalityId implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "personalityId", nullable = false, referencedColumnName = "id")
	private Personality personality;

	@ManyToOne
	@JoinColumn(name = "taskId", nullable = false, referencedColumnName = "id")
	private Task task;

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
	

}

