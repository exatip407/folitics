package com.ohmuk.folitics.hibernate.entity.verdict;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualification;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSex;

/**
 * Entity class for Verdict
 * 
 * @author Abhishek
 *
 */
@Entity
@Table(name = "verdict")
public class Verdict implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sentimentId", referencedColumnName = "id")
	@NotNull(message = "error.verdict.sentiment.notNull")
	private Sentiment sentiment;

	@OneToMany(mappedBy = "id.verdict", fetch = FetchType.LAZY)
	private Set<VerdictAgeGroup> verdictAgeGroups;

	@OneToMany(mappedBy = "id.verdict", fetch = FetchType.LAZY)
	private Set<VerdictSex> verdictSexes;

	@OneToMany(mappedBy = "id.verdict", fetch = FetchType.LAZY)
	private Set<VerdictMaritalStatus> verdictMaritalStatuses;

	@OneToMany(mappedBy = "id.verdict", fetch = FetchType.LAZY)
	private Set<VerdictReligion> verdictReligions;

	@OneToMany(mappedBy = "id.verdict", fetch = FetchType.LAZY)
	private Set<VerdictRegion> verdictRegions;

	@OneToMany(mappedBy = "id.verdict", fetch = FetchType.LAZY)
	private Set<VerdictQualification> verdictQualifications;

	@Transient
	private String headline;

	public Verdict() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sentiment getSentiment() {
		return sentiment;
	}

	public void setSentiment(Sentiment sentiment) {
		this.sentiment = sentiment;
	}

	public Set<VerdictAgeGroup> getVerdictAgeGroups() {
		return verdictAgeGroups;
	}

	public void setVerdictAgeGroups(Set<VerdictAgeGroup> verdictAgeGroups) {
		this.verdictAgeGroups = verdictAgeGroups;
	}

	public void addVerdictAgeGroup(VerdictAgeGroup verdictAgeGroup) {

		if (verdictAgeGroup != null) {
			this.verdictAgeGroups.add(verdictAgeGroup);
		}
	}

	public void removeVerdictAgeGroup(VerdictAgeGroup verdictAgeGroup) {

		if (verdictAgeGroup != null) {
			this.verdictAgeGroups.remove(verdictAgeGroup);
		}
	}

	public Set<VerdictSex> getVerdictSexes() {
		return verdictSexes;
	}

	public void setVerdictSexes(Set<VerdictSex> verdictSex) {
		this.verdictSexes = verdictSex;
	}

	public void addVerdictSex(VerdictSex verdictSex) {

		if (verdictSex != null) {
			this.verdictSexes.add(verdictSex);
		}
	}

	public void removeVerdictSex(VerdictSex verdictSex) {

		if (verdictSex != null) {
			this.verdictSexes.remove(verdictSex);
		}
	}

	public Set<VerdictMaritalStatus> getVerdictMaritalStatuses() {
		return verdictMaritalStatuses;
	}

	public void setVerdictMaritalStatuses(
			Set<VerdictMaritalStatus> verdictMaritalStatuses) {
		this.verdictMaritalStatuses = verdictMaritalStatuses;
	}

	public void addVerdictMaritalStatus(
			VerdictMaritalStatus verdictMaritalStatus) {

		if (verdictMaritalStatus != null) {
			this.verdictMaritalStatuses.add(verdictMaritalStatus);
		}
	}

	public void removeVerdictMaritalStatus(
			VerdictMaritalStatus verdictMaritalStatus) {

		if (verdictMaritalStatus != null) {
			this.verdictMaritalStatuses.remove(verdictMaritalStatus);
		}
	}

	public Set<VerdictReligion> getVerdictReligions() {
		return verdictReligions;
	}

	public void setVerdictReligions(Set<VerdictReligion> verdictReligions) {
		this.verdictReligions = verdictReligions;
	}

	public void addVerdictReligion(VerdictReligion verdictReligion) {

		if (verdictReligion != null) {
			this.verdictReligions.add(verdictReligion);
		}
	}

	public void removeReligion(VerdictReligion verdictReligion) {

		if (verdictReligion != null) {
			this.verdictReligions.remove(verdictReligion);
		}
	}

	public Set<VerdictRegion> getVerdictRegions() {
		return verdictRegions;
	}

	public void setVerdictRegions(Set<VerdictRegion> verdictRegions) {
		this.verdictRegions = verdictRegions;
	}

	public void addVerdictRegion(VerdictRegion verdictRegion) {

		if (verdictRegion != null) {
			this.verdictRegions.add(verdictRegion);
		}
	}

	public void removeVerdictRegion(VerdictRegion verdictRegion) {

		if (verdictRegion != null) {
			this.verdictMaritalStatuses.remove(verdictRegion);
		}
	}

	public Set<VerdictQualification> getVerdictQualifications() {
		return verdictQualifications;
	}

	public void setVerdictQualifications(
			Set<VerdictQualification> verdictQualifications) {
		this.verdictQualifications = verdictQualifications;
	}

	public void addVerdictQualification(
			VerdictQualification verdictQualification) {

		if (verdictQualification != null) {
			this.verdictQualifications.add(verdictQualification);
		}
	}

	public void removeVerdictQualification(
			VerdictQualification verdictQualification) {

		if (verdictQualification != null) {
			this.verdictQualifications.remove(verdictQualification);
		}
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}
}
