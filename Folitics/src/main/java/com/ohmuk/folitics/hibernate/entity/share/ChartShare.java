package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ohmuk.folitics.util.DateUtils;

@Entity
@Table(name = "chartshare")
public class ChartShare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Timestamp editTime;

	@Column(nullable = false)
	private Timestamp createTime;

	@Column(nullable = false)
	private Long componentId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false, length = 512)
	private String platform;

	public ChartShare() {
		setCreateTime(DateUtils.getSqlTimeStamp());
		setEditTime(DateUtils.getSqlTimeStamp());
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
	public Long getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId
	 *            the componentId to set
	 */
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
