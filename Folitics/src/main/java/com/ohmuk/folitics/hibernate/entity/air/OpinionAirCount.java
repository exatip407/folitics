package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "opinionaircount")
@NamedQuery(name = "OpinionAirCount.findAll", query = "SELECT s FROM SentimentAirCount s")
@PrimaryKeyJoinColumn(name = "id")
public class OpinionAirCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OpinionCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.sentimentAirCount.count.notNull")
	private Long airCount;

	

	/**
	 * @return the id
	 */
	public OpinionCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(OpinionCountId id) {
		this.id = id;
	}

	public Long getAirCount() {
		return airCount;
	}

	public void setAirCount(Long airCount) {
		this.airCount = airCount;
	}
}
