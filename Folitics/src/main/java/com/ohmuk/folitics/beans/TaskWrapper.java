package com.ohmuk.folitics.beans;

import java.io.Serializable;

import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskParticipant;
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;

public class TaskWrapper implements  Serializable{

	public TaskWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public TaskParticipant getTaskparticipant() {
		return taskparticipant;
	}
	public void setTaskparticipant(TaskParticipant taskparticipant) {
		this.taskparticipant = taskparticipant;
	}
	public TaskSolver getTasksolver() {
		return tasksolver;
	}
	public void setTasksolver(TaskSolver tasksolver) {
		this.tasksolver = tasksolver;
	}
	private Task task;
	private TaskParticipant taskparticipant;
	private TaskSolver tasksolver;
}
