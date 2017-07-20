package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "chartsharecount")
public class ChartShareCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChartShareCountId id;

	@Column(nullable = false)
	private Long facebookShareCount;

	@Column(nullable = false)
	private Long twitterShareCount;

	public ChartShareCount() {

	}

	/**
	 * @return the id
	 */
	public ChartShareCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ChartShareCountId id) {
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
