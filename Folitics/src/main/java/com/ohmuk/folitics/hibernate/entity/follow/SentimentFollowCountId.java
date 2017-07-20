package com.ohmuk.folitics.hibernate.entity.follow;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Sentiment;

/**
 * Composite Key for class SentimentFollowCount
 * 
 * @author 
 *
 */
@Embeddable
public class SentimentFollowCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "sentimentId", referencedColumnName = "id")
	private Sentiment sentiment;

	/**
	 * 
	 */
	public SentimentFollowCountId() {
	}

	/**
	 * @return
	 */
	public Sentiment getSentiment() {
		return sentiment;
	}

	/**
	 * @param sentiment
	 */
	public void setSentiment(Sentiment sentiment) {
		this.sentiment = sentiment;
	}

}
