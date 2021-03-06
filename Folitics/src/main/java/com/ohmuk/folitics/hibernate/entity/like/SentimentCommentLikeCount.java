package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;

/**
 * This entity is for maintaining count for likes on entity: {@link TaskComment}
 * 
 * @author Harish
 *
 */
@Entity
@Table(name = "sentimentcommentlikecount")
public class SentimentCommentLikeCount implements Serializable {

	@EmbeddedId
	private SentimentCommentLikeCountId id;

	@Column(nullable = false)
	private Long likeCount;

	@Column(nullable = false)
	private Long dislikeCount;

	/**
	 * @return the id
	 */
	public SentimentCommentLikeCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SentimentCommentLikeCountId id) {
		this.id = id;
	}

	/**
	 * @return the likeCount
	 */
	public Long getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the dislikeCount
	 */
	public Long getDislikeCount() {
		return dislikeCount;
	}

	/**
	 * @param dislikeCount the dislikeCount to set
	 */
	public void setDislikeCount(Long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

}
