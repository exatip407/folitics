package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity to maintain count for entity: {@link TaskShare}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "Tasksharecount")
@PrimaryKeyJoinColumn(name = "id")
public class TaskShareCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TaskShareCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.TaskShareCount.facebookShareCount.notNull")
	private Long facebookShareCount;

	@Column(nullable = false)
	@NotNull(message = "error.TaskShareCount.twitterShareCount.notNull")
	private Long twitterShareCount;

	public TaskShareCount() {

	}

	/**
	 * @return the id
	 */
	public TaskShareCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(TaskShareCountId id) {
		this.id = id;
	}

	/**
	 * @return the facebookShareCount
	 */
	public Long getFacebookShareCount() {
		return facebookShareCount;
	}

	/**
	 * @param facebookShareCount
	 *            the facebookShareCount to set
	 */
	public void setFacebookShareCount(Long facebookShareCount) {
		this.facebookShareCount = facebookShareCount;
	}

	/**
	 * @return the twitterShareCount
	 */
	public Long getTwitterShareCount() {
		return twitterShareCount;
	}

	/**
	 * @param twitterShareCount
	 *            the twitterShareCount to set
	 */
	public void setTwitterShareCount(Long twitterShareCount) {
		this.twitterShareCount = twitterShareCount;
	}

}
