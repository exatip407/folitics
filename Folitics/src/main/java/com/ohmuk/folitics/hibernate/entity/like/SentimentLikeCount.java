package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.Sentiment;

/**
 * This entity is for maintaining count for likes on entity: {@link Sentiment}
 * 
 * @author Abhishek
 *
 */

@Entity
@Table(name = "sentimentlikecount")
@NamedQuery(name = "SentimentLikeCount.findAll", query = "SELECT s FROM SentimentLikeCount s")
@PrimaryKeyJoinColumn(name = "id")
public class SentimentLikeCount implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SentimentLikeCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.sentimentLikeCount.count.notNull")
	private Long likeCount;

	@Column(nullable = false)
	@NotNull(message = "error.sentimentLikeCount.count.notNull")
	private Long dislikeCount;

	public SentimentLikeCount() {
		super();
	}

	public SentimentLikeCountId getId() {
		return id;
	}

	public void setId(SentimentLikeCountId id) {
		this.id = id;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(Long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

}
