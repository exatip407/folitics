package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: MonetizationConfig
 * 
 */
@Entity
@Table(name = "monetizationConfig")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class MonetizationConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, length = 25, columnDefinition = "enum('GPI', 'GA', 'Task', 'Verdict', 'Poll','Contest')")
	@NotNull(message = "error.monetizationConfig.module.NotNull")
	private String module;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Share', 'Like', 'Comment', 'Respond', 'Referral','Create','Delete', 'Participate', 'RaiseProblem', 'Accept','Submit','Unlike','Dislike','Undislike','Air')")
	@NotNull(message = "error.monetizationConfig.action.NotNull")
	private String action;

	@Column(nullable = false, length = 100, columnDefinition = "enum('Opinion', 'Sentiment', 'Polls', 'Task', 'Image', 'Video', 'Link', 'Graph', 'GlobalIndicatorGraph', 'LocalIndicatorGraph', 'GovtSchemeData', 'ComparisonGraph', 'SubmitFact', 'LocalVerdict','LocalVerdictReport', 'GlobalVerdictReport','Bumper','Mega','Mini','Chart')")
	@NotNull(message = "error.monetizationConfig.componentType.NotNull")
	private String componentType;

	@Column(nullable = false)
	@NotNull(message = "error.monetizationConfig.createdTime.NotNull")
	private Timestamp createdTime;

	@Column(nullable = false)
	@NotNull(message = "error.monetizationConfig.Points.NotNull")
	private Long points;

	@Column(nullable = false, columnDefinition = "enum('Active','Deleted','Updated')")
	@NotNull(message = "error.monetizationConfig.status.NotNull")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String component) {
		componentType = component;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long _points) {
		points = _points;
	}

}
