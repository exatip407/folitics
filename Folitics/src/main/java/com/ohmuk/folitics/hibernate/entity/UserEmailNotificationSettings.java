package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity implementation class for Entity: UserEmailNotificationSettings
 * 
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name = "User_Email_Notification_Settings")
public class UserEmailNotificationSettings implements Serializable {

	public UserEmailNotificationSettings() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = true, length = 128)
	@Size(min = 1, max = 128, message = "error.UserEmailNotificationSettings.componentType.size")
	private String componentType;

	@Column(nullable = false)
	private Boolean notificationType = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@NotNull(message = "error.UserEmailNotificationSettings.user.notNull")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public Boolean getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(Boolean notificationType) {
		this.notificationType = notificationType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
