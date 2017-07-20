package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "IndicatorData")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class IndicatorData implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "indicatorId", nullable = false)
	private Category category;

	@Column(nullable = false)
	private Timestamp updateddate;

	@Column(nullable = false)
	private Long indicatorvalue;

	@Column(nullable = false)
	private Timestamp effectfromdate;

	@Column(nullable = false)
	private Long deleted;

	@Column(nullable = false)
	private Double score;

	@Column(nullable = false)
	private String thresholdcategory;

	@Column(nullable = false)
	private Timestamp editTime;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private Timestamp createTime;

	@Column(nullable = false)
	private String idealValueRange;

	@Column(nullable = false)
	private double weightedValue;

	@Column(nullable = false)
	private double weightedIdealValue;

	@Column(nullable = false)
	private double delta;

	@Transient
	private String categoryName;

	@Transient
	private Long categoryID;

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	public String getIdealValueRange() {
		return idealValueRange;
	}

	public void setIdealValueRange(String idealValueRange) {
		this.idealValueRange = idealValueRange;
	}

	public double getWeightedValue() {
		return weightedValue;
	}

	public void setWeightedValue(double weightedValue) {
		this.weightedValue = weightedValue;
	}

	public double getWeightedIdealValue() {
		return weightedIdealValue;
	}

	public void setWeightedIdealValue(double weightedIdealValue) {
		this.weightedIdealValue = weightedIdealValue;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public Long getIndicatorvalue() {
		return indicatorvalue;
	}

	public void setIndicatorvalue(Long indicatorvalue) {
		this.indicatorvalue = indicatorvalue;
	}

	public Timestamp getEffectfromdate() {
		return effectfromdate;
	}

	public void setEffectfromdate(Timestamp effectfromdate) {
		this.effectfromdate = effectfromdate;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getThresholdcategory() {
		return thresholdcategory;
	}

	public void setThresholdcategory(String thresholdcategory) {
		this.thresholdcategory = thresholdcategory;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
