package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.comment.SentimentComment;

/**
 * Embeddable class to create id for entity: {@link SentimentCommentLikeCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class SentimentCommentLikeCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sentimentCommentId", referencedColumnName = "id")
	private SentimentComment sentimentComment;

	/**
	 * @return the sentimentComment
	 */
	public SentimentComment getSentimentComment() {
		return sentimentComment;
	}

	/**
	 * @param sentimentComment the sentimentComment to set
	 */
	public void setSentimentComment(SentimentComment sentimentComment) {
		this.sentimentComment = sentimentComment;
	}

}
