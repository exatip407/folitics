package com.ohmuk.folitics.hibernate.entity.quickproblem;


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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.hibernate.entity.User;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "quickproblem")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class QuickProblem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 256)
	private String description;

	@Column
	private Timestamp creationDate;

	@Column
	private Timestamp editDate;

	@Column
	private Timestamp completionDate;

	@ManyToOne
	private User createdBy;

	@Lob
	@Column
	private byte[] image;

	@Column
	private String imageType;

	@OneToMany(mappedBy = "quickProblem", fetch= FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	private Set<QuickProblemPersonality> quickProblemPersonality;

	/*@Column(nullable = true, length = 128, columnDefinition = "enum ('Twitter','Facebook','Email','Magnet')")
	private String share;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getEditDate() {
		return editDate;
	}

	public void setEditDate(Timestamp editDate) {
		this.editDate = editDate;
	}

	public Timestamp getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public byte[] getImage() {
		return image;
	}

/*	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}
*/
	public void setQuickProblemPersonality(
			Set<QuickProblemPersonality> quickProblemPersonality) {
		this.quickProblemPersonality = quickProblemPersonality;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Set<QuickProblemPersonality> getQuickProblemPersonality() {
		return quickProblemPersonality;
	}

	public void setQuickPersonality(
			Set<QuickProblemPersonality> quickPersonality) {
		this.quickProblemPersonality = quickPersonality;
	}

}
