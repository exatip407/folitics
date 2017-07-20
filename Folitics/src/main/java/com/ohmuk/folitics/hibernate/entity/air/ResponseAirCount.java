package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "responseaircount")
public class ResponseAirCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ResponseAirCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.TaskAirCount.count.notNull")
	private Long airCount;

	/**
	 * @return the id
	 */
	public ResponseAirCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ResponseAirCountId id) {
		this.id = id;
	}

	/**
	 * @return the airCount
	 */
	public Long getAirCount() {
		return airCount;
	}

	/**
	 * @param airCount the airCount to set
	 */
	public void setAirCount(Long airCount) {
		this.airCount = airCount;
	}

}
