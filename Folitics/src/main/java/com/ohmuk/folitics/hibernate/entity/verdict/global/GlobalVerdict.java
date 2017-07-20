package com.ohmuk.folitics.hibernate.entity.verdict.global;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entity class for storing data of global verdict
 * 
 * @author Abhishek
 *
 */
@Entity
@Table(name = "global_verdict")
public class GlobalVerdict implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@NotNull(message = "error.globalVerdict.proCount.notNull")
	private Long proCount;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 0")
	@NotNull(message = "error.globalVerdict.antiCount.notNull")
	private Long antiCount;

	@Transient
	private Set<GlobalVerdictAgeDistribution> globalVerdictAgeDistributions;

	@Transient
	private Set<GlobalVerdictSexDistribution> globalVerdictSexDistributions;

	@Transient
	private Set<GlobalVerdictMaritalStatusDistribution> globalVerdictMaritalStatusDistributions;

	@Transient
	private Set<GlobalVerdictRegionDistribution> globalVerdictRegionDistributions;

	@Transient
	private Set<GlobalVerdictReligionDistribution> globalVerdictReligionDistributions;

	@Transient
	private Set<GlobalVerdictQualificationDistribution> globalVerdictQualificationDistributions;

	public GlobalVerdict() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<GlobalVerdictAgeDistribution> getGlobalVerdictAgeDistributions() {
		return globalVerdictAgeDistributions;
	}

	public void setGlobalVerdictAgeDistributions(
			Set<GlobalVerdictAgeDistribution> globalVerdictAgeDistributions) {
		this.globalVerdictAgeDistributions = globalVerdictAgeDistributions;
	}

	public Set<GlobalVerdictSexDistribution> getGlobalVerdictSexDistributions() {
		return globalVerdictSexDistributions;
	}

	public void setGlobalVerdictSexDistributions(
			Set<GlobalVerdictSexDistribution> globalVerdictSexDistributions) {
		this.globalVerdictSexDistributions = globalVerdictSexDistributions;
	}

	public Set<GlobalVerdictMaritalStatusDistribution> getGlobalVerdictMaritalStatusDistributions() {
		return globalVerdictMaritalStatusDistributions;
	}

	public void setGlobalVerdictMaritalStatusDistributions(
			Set<GlobalVerdictMaritalStatusDistribution> globalVerdictMaritalStatusDistributions) {
		this.globalVerdictMaritalStatusDistributions = globalVerdictMaritalStatusDistributions;
	}

	public Set<GlobalVerdictRegionDistribution> getGlobalVerdictRegionDistributions() {
		return globalVerdictRegionDistributions;
	}

	public void setGlobalVerdictRegionDistributions(
			Set<GlobalVerdictRegionDistribution> globalVerdictRegionDistributions) {
		this.globalVerdictRegionDistributions = globalVerdictRegionDistributions;
	}

	public Set<GlobalVerdictReligionDistribution> getGlobalVerdictReligionDistributions() {
		return globalVerdictReligionDistributions;
	}

	public void setGlobalVerdictReligionDistributions(
			Set<GlobalVerdictReligionDistribution> globalVerdictReligionDistributions) {
		this.globalVerdictReligionDistributions = globalVerdictReligionDistributions;
	}

	public Set<GlobalVerdictQualificationDistribution> getGlobalVerdictQualificationDistributions() {
		return globalVerdictQualificationDistributions;
	}

	public void setGlobalVerdictQualificationDistributions(
			Set<GlobalVerdictQualificationDistribution> globalVerdictQualificationDistributions) {
		this.globalVerdictQualificationDistributions = globalVerdictQualificationDistributions;
	}
}
