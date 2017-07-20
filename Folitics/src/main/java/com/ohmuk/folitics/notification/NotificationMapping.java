package com.ohmuk.folitics.notification;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class NotificationMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Share', 'Like', 'Comment', 'Respond', 'Referral','Create','Delete', 'Participate', 'RaiseProblem', 'Accept','Submit','Unlike','Dislike','Undislike','Air')")
	private String action;

	@Column(nullable = false, length = 100, columnDefinition = "enum('Opinion', 'Sentiment', 'Polls', 'Task', 'Image', 'Video', 'Link', 'Graph', 'GlobalIndicatorGraph', 'LocalIndicatorGraph', 'GovtSchemeData', 'ComparisonGraph', 'SubmitFact', 'LocalVerdict','LocalVerdictReport', 'GlobalVerdictReport','Bumper','Mega','Mini')")
	private String componentType;

	@Column(nullable = false)
	private Long componentId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false, length = 100, columnDefinition = "enum('General', 'Connection', 'Task','Point','Vidopic',''Opinion)")
	private String notificationType;

	@Column(nullable = false, length = 100, columnDefinition = "enum('User', 'Admin', 'Creator','Owner')")
	private String notificationFrom;

	@Column(nullable = false, length = 100, columnDefinition = "enum('User','Connection','Liked','Shared','Followed','Creator','Owner','Commented','Responded','Admin')")
	private String notificationTo;

	@Column(nullable = false)
	private Set<Long> sendingUsers;
	
	@Column(nullable=false)
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationFrom() {
		return notificationFrom;
	}

	public void setNotificationFrom(String notificationFrom) {
		this.notificationFrom = notificationFrom;
	}

	public String getNotificationTo() {
		return notificationTo;
	}

	public void setNotificationTo(String notificationTo) {
		this.notificationTo = notificationTo;
	}

	public Set<Long> getSendingUsers() {
		return sendingUsers;
	}

	public void setSendingUsers(Set<Long> sendingUsers) {
		this.sendingUsers = sendingUsers;
	}

}
