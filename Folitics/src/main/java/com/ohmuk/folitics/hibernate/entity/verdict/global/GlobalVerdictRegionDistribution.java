package com.ohmuk.folitics.hibernate.entity.verdict.global;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;

/**
 * Entity for saving distribution for region on global verdict
 * 
 * @author Abhishek
 *
 */
@Entity
@Table(name = "globalverdict_region_distribution")
public class GlobalVerdictRegionDistribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne
	@JoinColumn(name = "region", referencedColumnName = "id", nullable = false)
	private Region region;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@NotNull(message = "error.globalVerdict.proCount.notNull")
	private Long proCount;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@NotNull(message = "error.globalVerdict.antiCount.notNull")
	private Long antiCount;

	public GlobalVerdictRegionDistribution() {

	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Long getProCount() {
		return proCount;
	}

	public void setProCount(Long proCount) {
		this.proCount = proCount;
	}

	public Long getAntiCount() {
		return antiCount;
	}

	public void setAntiCount(Long antiCount) {
		this.antiCount = antiCount;
	}
}
