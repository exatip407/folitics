package com.ohmuk.folitics.hibernate.entity.air;

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
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "taskair")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class TaskAir implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "error.TaskAir.editTime.notNull")
	private Timestamp editTime;

	@Column(nullable = false)
	@NotNull(message = "error.TaskAir.createTime.notNull")
	private Timestamp createTime;

	@Column(nullable = false)
	private long componentId;

	@Column(nullable = false, length = 512)
	private String componentType;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Active','Disabled','Deleted')")
	@NotNull(message = "error.TaskAir.status.notNull")
	private String status;

	@Column(nullable = false)
	private long userId;

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
	 * @return the editTime
	 */
	public Timestamp getEditTime() {
		return editTime;
	}

	/**
	 * @param editTime
	 *            the editTime to set
	 */
	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the componentId
	 */
	public long getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId
	 *            the componentId to set
	 */
	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	/**
	 * @return the componentType
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType
	 *            the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskAir() {

		setCreateTime(DateUtils.getSqlTimeStamp());
		setEditTime(DateUtils.getSqlTimeStamp());
		setStatus(ComponentState.ACTIVE.getValue());
	}

}
