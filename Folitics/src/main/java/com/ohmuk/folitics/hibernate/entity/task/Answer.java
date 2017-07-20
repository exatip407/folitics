package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ohmuk.folitics.hibernate.entity.User;

/**
 * Entity Implementaion for module : {@link Answer}
 * 
 * @author Harish bagora
 *
 */
@Entity
public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(length = 500)
	private String answer;

	@Column(name = "answerPostedAt")
	private Timestamp answerPostingTime;

	@Column(name = "answerLastEditedAt")
	private Timestamp answerEditingTime;

	@ManyToOne
	@JoinColumn(name = "questionId", referencedColumnName = "id")
	private Question questionAnswer;

	@ManyToOne
	@JoinColumn(name = "repliedBy", referencedColumnName = "id")
	private User answerUser;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the answerPostingTime
	 */
	public Timestamp getAnswerPostingTime() {
		return answerPostingTime;
	}

	/**
	 * @param answerPostingTime the answerPostingTime to set
	 */
	public void setAnswerPostingTime(Timestamp answerPostingTime) {
		this.answerPostingTime = answerPostingTime;
	}

	/**
	 * @return the answerEditingTime
	 */
	public Timestamp getAnswerEditingTime() {
		return answerEditingTime;
	}

	/**
	 * @param answerEditingTime the answerEditingTime to set
	 */
	public void setAnswerEditingTime(Timestamp answerEditingTime) {
		this.answerEditingTime = answerEditingTime;
	}

	/**
	 * @return the questionAnswer
	 */
	public Question getQuestionAnswer() {
		return questionAnswer;
	}

	/**
	 * @param questionAnswer the questionAnswer to set
	 */
	public void setQuestionAnswer(Question questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	/**
	 * @return the answerUser
	 */
	public User getAnswerUser() {
		return answerUser;
	}

	/**
	 * @param answerUser the answerUser to set
	 */
	public void setAnswerUser(User answerUser) {
		this.answerUser = answerUser;
	}

}
