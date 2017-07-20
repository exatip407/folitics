package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Entity to maintain count for entity: {@link SentimentShare}
 * 
 * @author Abhishek
 *
 */

@Entity
@Table(name = "sentimentsharecount")
@NamedQuery(name = "SentimentShareCount.findAll", query = "SELECT s FROM SentimentShareCount s")
@PrimaryKeyJoinColumn(name = "id")
public class SentimentShareCount implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SentimentShareCountId id;

	@Column(nullable = false)
	private Long facebookShareCount;

	@Column(nullable = false)
	private Long twitterShareCount;

	public SentimentShareCount() {

	}

	public SentimentShareCountId getId() {
		return id;
	}

	public void setId(SentimentShareCountId id) {
		this.id = id;
	}

	public Long getFacebookShareCount() {
		return facebookShareCount;
	}

	public void setFacebookShareCount(Long facebookShareCount) {
		this.facebookShareCount = facebookShareCount;
	}

	public Long getTwitterShareCount() {
		return twitterShareCount;
	}

	public void setTwitterShareCount(Long twitterShareCount) {
		this.twitterShareCount = twitterShareCount;
	}

}
