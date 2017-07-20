package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This entity is used to maintain follow count for Sentiment
 * 
 * @author 
 *
 */

@Entity
@Table(name = "sentimentfollowcount")
@PrimaryKeyJoinColumn(name = "id")
public class SentimentFollowCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SentimentFollowCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.sentimentfollowCount.count.notNull")
	private Long followCount;

	public SentimentFollowCount() {

	}

	public SentimentFollowCountId getId() {
		return id;
	}

	public void setId(SentimentFollowCountId id) {
		this.id = id;
	}

	public Long getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Long followCount) {
		this.followCount = followCount;
	}

}
