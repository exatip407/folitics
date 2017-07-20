package com.ohmuk.folitics.hibernate.entity.verdict.global;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Entity for saving distribution for sex on global verdict
 * 
 * @author Abhishek
 *
 */
@Entity
@Table(name = "globalverdict_sex_distribution")
public class GlobalVerdictSexDistribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne
	@JoinColumn(name = "sex", referencedColumnName = "id", nullable = false)
	private Sex sex;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@NotNull(message = "error.globalVerdict.proCount.notNull")
	private Long proCount;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@NotNull(message = "error.globalVerdict.antiCount.notNull")
	private Long antiCount;

	public GlobalVerdictSexDistribution() {

	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
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
