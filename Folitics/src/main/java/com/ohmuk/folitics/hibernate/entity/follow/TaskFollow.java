package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.beans.FollowDataBean;

/**
 * Entity for Follow Module
 * 
 * @author Harish
 *
 */
@Entity
@Table(name = "taskfollow")
public class TaskFollow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Composite Primary Key
	 */

	@EmbeddedId
	private FollowId followId;

	@Column(nullable = false, length = 128)
	@NotNull(message = "error.componentFollow.componentType.notNull")
	private String componentType;

	@Column(nullable = false)
	@NotNull(message = "error.componentFollow.createTime.notNull")
	private Timestamp createTime;

	@Column(nullable = false)
	@NotNull(message = "error.componentFollow.isFollowing.notNull")
	private boolean isFollowing;

	public TaskFollow() {

	}

	/**
	 * @param followDataBean
	 */
	public TaskFollow(FollowDataBean followDataBean) {

		followId = new FollowId(followDataBean.getComponentId(),
				followDataBean.getUserId());
		componentType = followDataBean.getComponentType();
	}

	/**
	 * @return the followId
	 */
	public FollowId getFollowId() {
		return followId;
	}

	/**
	 * @param followId
	 *            the followId to set
	 */
	public void setFollowId(FollowId followId) {
		this.followId = followId;
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
	 * @return the isFollowing
	 */
	public boolean isFollowing() {
		return isFollowing;
	}

	/**
	 * @param isFollowing
	 *            the isFollowing to set
	 */
	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
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

}
