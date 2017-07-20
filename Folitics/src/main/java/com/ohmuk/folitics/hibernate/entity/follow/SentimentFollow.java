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
 * @author Kshitij
 *
 */
/**
 * 
 *
 */

@Entity
@Table(name = "SentimentFollow")
public class SentimentFollow implements Serializable {

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

	/**
	 * @return
	 */
	public FollowId getFollowId() {
		return followId;
	}

	/**
	 * @param followId
	 */
	public void setFollowId(FollowId followId) {
		this.followId = followId;
	}

	public SentimentFollow() {

	}

	/**
	 * @param followDataBean
	 */
	public SentimentFollow(FollowDataBean followDataBean) {

		followId = new FollowId(followDataBean.getComponentId(),
				followDataBean.getUserId());
		componentType = followDataBean.getComponentType();
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
	 * @return
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
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
