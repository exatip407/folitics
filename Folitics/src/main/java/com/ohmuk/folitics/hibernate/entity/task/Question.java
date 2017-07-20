package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.ohmuk.folitics.hibernate.entity.User;

/**
 * Entity Implementaion for module : {@link Task}
 * 
 * @author Sarvesh
 *
 */
@Entity
public class Question implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "taskID", referencedColumnName = "id")
	@Cascade(CascadeType.SAVE_UPDATE)
	private Task taskId;

	@ManyToOne
	@JoinColumn(name = "askedBy", referencedColumnName = "id")
	private User questionUser;

	@OneToMany(mappedBy = "questionAnswer", fetch = FetchType.LAZY)
	private Set<Answer> answer;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "repliedBy", referencedColumnName = "id") private User
	 * answerUser;
	 */

	@Column(length = 500)
	private String question;

	@Column(name = "questionPostedAt")
	private Timestamp questionPostingTime;

	@Column(name = "questionLastEditedAt")
	private Timestamp questionEditingTime;

	/**
	 * Default Constructor for Entity {@link Question}
	 */
	public Question() {

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
	 * @return the taskId
	 */
	public Task getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(Task taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the questionUser
	 */
	public User getQuestionUser() {
		return questionUser;
	}

	/**
	 * @param questionUser
	 *            the questionUser to set
	 */
	public void setQuestionUser(User questionUser) {
		this.questionUser = questionUser;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the questionPostingTime
	 */
	public Timestamp getQuestionPostingTime() {
		return questionPostingTime;
	}

	/**
	 * @param questionPostingTime
	 *            the questionPostingTime to set
	 */
	public void setQuestionPostingTime(Timestamp questionPostingTime) {
		this.questionPostingTime = questionPostingTime;
	}

	/**
	 * @return the questionEditingTime
	 */
	public Timestamp getQuestionEditingTime() {
		return questionEditingTime;
	}

	/**
	 * @param questionEditingTime
	 *            the questionEditingTime to set
	 */
	public void setQuestionEditingTime(Timestamp questionEditingTime) {
		this.questionEditingTime = questionEditingTime;
	}

}
