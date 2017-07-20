package com.ohmuk.folitics.beans;

/**
 * @author Kshitij
 *
 */
public class FollowDataBean {

	private Long componentId;

	private String componentType;

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
}
