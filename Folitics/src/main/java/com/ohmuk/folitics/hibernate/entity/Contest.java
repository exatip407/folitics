package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "contest")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Contest implements Serializable {

	/**
	 * Entity implementation class for Entity: Luckydraw
	 * 
	 * @author Harish
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Contest() {
		super();

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "contest", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	private Set<LuckyDraw> luckyDraw;

	@Column(nullable = false, length = 256)
	@NotNull(message = "error.contest.name.notNull")
	private String name;

	@Column(nullable = false, length = 512)
	@NotNull(message = "error.contest.description.notNull")
	private String description;

	@Column(nullable = false)
	@NotNull(message = "error.contest.creationTime.notNull")
	private Timestamp creationTime;

	@Column(nullable = false)
	@NotNull(message = "error.contest.editTime.notNull")
	private Timestamp editTime;

	@Column(nullable = false)
	@NotNull(message = "error.contest.expiryTime.notNull")
	private Timestamp expiryTime;

	@Column(nullable = false, length = 512, columnDefinition = "enum('Active','Inactive','Draft','Deleted')")
	@NotNull(message = "error.contest.state.notNull")
	private String state;

	@Column(nullable = false, length = 1024)
	@NotNull(message = "error.contest.termsAndCondition.notNull")
	private String termsAndCondition;

	@Lob
	@Column(nullable = true)
	// @NotNull(message = "error.contest.image.notNull")
	private byte[] image;

	@Column(nullable = false, length = 25, columnDefinition = "enum('png','jpg','jpeg')")
	@NotNull(message = "error.contest.imageType.notNull")
	private String imageType;

	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType
	 *            the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the luckyDraw
	 */
	public Set<LuckyDraw> getLuckyDraw() {
		return luckyDraw;
	}

	/**
	 * @param luckyDraw
	 *            the luckyDraw to set
	 */
	public void setLuckyDraw(Set<LuckyDraw> luckyDraw) {
		this.luckyDraw = luckyDraw;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the creationTime
	 */
	public Timestamp getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the editTime
	 */
	public Timestamp getEditTime() {
		return editTime;
	}

	/**
	 * @param editTime
	 *            the editTime to set
	 */
	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	/**
	 * @return the expiryTime
	 */
	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime
	 *            the expiryTime to set
	 */
	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the termsAndCondition
	 */
	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	/**
	 * @param termsAndCondition
	 *            the termsAndCondition to set
	 */
	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}
}