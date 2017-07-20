package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@IdClass(PersonalityId.class)
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class TaskPersonality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "personalityId", nullable = false, referencedColumnName = "id")
	private Personality personality;

	@Id
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
