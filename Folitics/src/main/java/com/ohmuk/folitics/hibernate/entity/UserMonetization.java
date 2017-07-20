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
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: UserMonetization
 * 
 * 
 */
@Entity
@Table(name = "userMonetization")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class UserMonetization implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "error.UserMonetization.ActionComponentId.NotNull")
	private Long userId;

	/*
	 * @ManyToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name="userId", nullable = false , referencedColumnName =
	 * "id") private User user;
	 */

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "monetizationId", nullable = false,
	 * referencedColumnName = "id") private MonetizationConfig
	 * monetizationConfig;
	 */

	/*
	 * @OneToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "componentId",nullable = true, referencedColumnName =
	 * "id") private Component component;
	 */

	@Column
	@NotNull(message = "error.UserMonetization.ActionComponentId.NotNull")
	private Long ActionComponentId;

	@Column(nullable = true)
	private Long points;

	@Column(nullable = true)
	@NotNull(message = "error.UserMonetization.createdTime.NotNull")
	private Timestamp createdTime;

	@Column(nullable = true)
	private String status;

	@Column(nullable = false, length = 25, columnDefinition = "enum('GPI', 'GA', 'Tasks', 'Verdict', 'Polls','Contest')")
	@NotNull(message = "error.UserMonetization.module.NotNull")
	private String module;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Share', 'Like','Air','Unlike','UnDisLike','DisLike','Comment','Submit', 'Response', 'Referral','Create','Delete', 'Participate', 'RaiseProblem', 'Accept')")
	@NotNull(message = "error.UserMonetization.action.NotNull")
	private String action;

	@Column(nullable = false, length = 100, columnDefinition = "enum('Opinion', 'Sentiment', 'Polls', 'Tasks', 'Image', 'Video', 'Link', 'Graph', 'GlobalIndicatorGraph', 'LocalIndicatorGraph', 'GovtSchemeData', 'ComparisonGraph', 'SubmitFact', 'LocalVerdict','LocalVerdictReport', 'GlobalVerdictReport','Bumper','Mega','Mini','Chart')")
	@NotNull(message = "error.UserMonetization.componentType.NotNull")
	private String componentType;

	public Long getActionComponentId() {
		return ActionComponentId;
	}

	public void setActionComponentId(Long componentId) {
		ActionComponentId = componentId;
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

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public UserMonetization() {
		setCreatedTime(DateUtils.getSqlTimeStamp());
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public Timestamp getTimestamp() {
		return createdTime;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.createdTime = timestamp;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
