package com.ohmuk.folitics.beans;

import com.ohmuk.folitics.hibernate.entity.task.Answer;
import com.ohmuk.folitics.hibernate.entity.task.Question;
import com.ohmuk.folitics.hibernate.entity.task.Task;

public class QuestionAnswerBean {

	private Question questionAnswer;
	private Task task;
	private Answer answer;

	public QuestionAnswerBean() {
		super();
	}

	public Question getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(Question questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the answer
	 */
	public Answer getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}
