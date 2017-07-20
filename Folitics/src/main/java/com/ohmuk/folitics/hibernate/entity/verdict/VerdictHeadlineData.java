package com.ohmuk.folitics.hibernate.entity.verdict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Entity for maintaining verdict headline data
 * 
 * @author Abhishek
 *
 */
@Entity
@Table(name = "verdict_headline_data")
public class VerdictHeadlineData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "verdictId", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.verdict.notNull")
	private Verdict verdictId;

	@ManyToOne
	@JoinColumn(name = "agegroup", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.ageGroup.notNull")
	private AgeGroup agegroup;

	@ManyToOne
	@JoinColumn(name = "sex", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.sex.notNull")
	private Sex sex;

	@ManyToOne
	@JoinColumn(name = "maritalstatus", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.maritalStatus.notNull")
	private MaritalStatus maritalstatus;

	@ManyToOne
	@JoinColumn(name = "region", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.region.notNull")
	private Region region;

	@ManyToOne
	@JoinColumn(name = "religion", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.religion.notNull")
	private Religion religion;

	@ManyToOne
	@JoinColumn(name = "qualification", referencedColumnName = "id")
	@NotNull(message = "error.verdictHeadlineData.qualification.notNull")
	private Qualification qualification;

	@Column(nullable = false)
	@NotNull(message = "error.verdictHeadlineData.proCount.notNull")
	private Long proCount;

	@Column(nullable = false)
	@NotNull(message = "error.verdictHeadlineData.antiCount.notNull")
	private Long antiCount;

	public VerdictHeadlineData() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Verdict getVerdictId() {
		return verdictId;
	}

	public void setVerdictId(Verdict verdictId) {
		this.verdictId = verdictId;
	}

	public AgeGroup getAgegroup() {
		return agegroup;
	}

	public void setAgegroup(AgeGroup agegroup) {
		this.agegroup = agegroup;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public MaritalStatus getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(MaritalStatus maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
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
