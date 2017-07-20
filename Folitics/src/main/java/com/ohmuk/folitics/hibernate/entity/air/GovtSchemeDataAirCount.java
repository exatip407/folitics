package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;

/**
 * This entity is for maintaining count for airs on entity:
 * {@link GovtSchemeData}
 * 
 * @author Harish
 *
 */
@Entity
@Table(name = "govtschemedataaircount")
@NamedQuery(name = "govtSchemeDataAirCount.findAll", query = "SELECT s FROM GovtSchemeDataAirCount s")
@PrimaryKeyJoinColumn(name = "id")
public class GovtSchemeDataAirCount implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GovtSchemeDataCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.sentimentAirCount.count.notNull")
	private Long airCount;

	public GovtSchemeDataCountId getId() {
		return id;
	}

	public void setId(GovtSchemeDataCountId id) {
		this.id = id;
	}

	public Long getAirCount() {
		return airCount;
	}

	public void setAirCount(Long airCount) {
		this.airCount = airCount;
	}
}
