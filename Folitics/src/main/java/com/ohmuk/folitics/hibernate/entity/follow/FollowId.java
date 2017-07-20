package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author 
 *
 */
@Embeddable
public class FollowId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Long componentId;

	@Column(nullable = false)
	private Long userId;

	/**
	 * @return
	 */
	public Long getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId
	 */
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	/**
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @param componentId
	 * @param userId
	 */
	public FollowId(Long componentId, Long userId) {

		this.componentId = componentId;
		this.userId = userId;
	}

	public FollowId() {

	}
	
	

}
