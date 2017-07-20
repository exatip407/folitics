package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentType;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * @author Abhishek
 *
 */
@Entity
@Table(name = "sentiment")
@PrimaryKeyJoinColumn(name = "id")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Sentiment extends Component implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 512)
	@NotNull(message = "error.sentiment.subject.notNull")
	@Size(min = 1, max = 512, message = "error.sentiment.subject.size")
	private String subject;

	@Column(nullable = false)
	@NotNull(message = "error.sentiment.editTime.notNull")
	private Timestamp editTime;

	@Column(nullable = true)
	private Long editedBy;

	@Column(nullable = false)
	@NotNull(message = "error.sentiment.createTime.notNull")
	private Timestamp createTime;

	@Column(nullable = false)
	@NotNull(message = "error.sentiment.createdBy.notNull")
	private Long created_By;

	@Column(nullable = false, length = 1000)
	@NotNull(message = "error.sentiment.description.notNull")
	@Size(min = 1, max = 1000, message = "error.sentiment.description.size")
	private String description;

	@Column(nullable = false)
	@NotNull(message = "error.sentiment.startTime.notNull")
	// @Future(message = "error.sentiment.startTime.future")
	private Timestamp startTime;

	@Column(nullable = false)
	@NotNull(message = "error.sentiment.endTime.notNull")
	// @Future(message = "error.sentiment.endTime.future")
	private Timestamp endTime;

	@Lob
	@NotNull(message = "error.sentiment.image.notNull")
	private byte[] image;

	@Column(nullable = false, length = 25, columnDefinition = "enum('png','jpg','jpeg')")
	@NotNull(message = "error.sentiment.imageType.notNull")
	private String imageType;

	@Column(length = 25, columnDefinition = "enum('New','Alive','Hidden','Expired','Edited','Deleted')")
	private String state;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Temporary','Permanent')")
	@NotNull(message = "error.sentiment.type.notNull")
	private String type;

	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(value = CascadeType.ALL)
	@JoinTable(name = "sentimentcategory", joinColumns = { @JoinColumn(name = "sentimentId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "categoryId", referencedColumnName = "id") })
	@NotNull(message = "error.sentiment.categories.notNull")
	private Set<Category> categories;

	@OneToMany(mappedBy = "permanentSentiments")
	private Set<Sentiment> temporarySentiments;

	@ManyToMany
	@JoinColumn(name = "permanentSentiments", referencedColumnName = "id")
	private Set<Sentiment> permanentSentiments;

	@ManyToMany
	@JoinTable(name = "RelatedSentiment", joinColumns = @JoinColumn(name = "sentimentId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "relatedSentimentId", referencedColumnName = "id"))
	private Set<Sentiment> relatedSentiments;
	
	@ManyToMany(mappedBy = "relatedSentiments")
	private Set<Sentiment> relatedToSentiments;

	@OneToMany(mappedBy = "sentiment", fetch = FetchType.LAZY)
	@NotNull(message = "error.sentiment.polls.notNull")
	@Cascade(value = CascadeType.ALL)
	private Set<Poll> polls;

	public Sentiment() {
		setComponentType(ComponentType.SENTIMENT.getValue());
		setCreateTime(DateUtils.getSqlTimeStamp());
		setEditTime(DateUtils.getSqlTimeStamp());
	}

	public Sentiment(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public byte[] getImage() {
		return image;
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

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public Long getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(Long editedBy) {
		this.editedBy = editedBy;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Sentiment> getRelatedToSentiments() {
		return relatedToSentiments;
	}

	public void setRelatedToSentiments(Set<Sentiment> relatedToSentiments) {
		this.relatedToSentiments = relatedToSentiments;
	}

	public void addRelatedToSentiment(Sentiment relatedToSentiment) {
		if (relatedToSentiment != null) {
			relatedToSentiments.add(relatedToSentiment);
		}
	}

	public void removeRelatedToSentiment(Sentiment relatedToSentiment) {
		if (relatedToSentiment != null) {
			relatedToSentiments.remove(relatedToSentiment);
		}
	}

	public Set<Poll> getPolls() {
		return polls;
	}

	public void setPolls(Set<Poll> polls) {
		this.polls = polls;
	}

	public void addPoll(Poll poll) {
		if (poll != null) {
			polls.add(poll);
		}
	}

	public void removePoll(Poll poll) {
		if (poll != null) {
			polls.remove(poll);
		}
	}

	public void addCategory(Category category) {
		if (category != null) {
			categories.add(category);
		}
	}

	public void removeCategory(Category category) {
		if (category != null) {
			categories.remove(category);
		}
	}

	public Set<Sentiment> getTemporarySentiments() {
		return temporarySentiments;
	}

	public void setTemporarySentiments(Set<Sentiment> temporarySentiments) {
		this.temporarySentiments = temporarySentiments;
	}

	public void addTemporarySentiment(Sentiment sentiment) {
		if (sentiment != null) {
			temporarySentiments.add(sentiment);
		}
	}

	public void removeTemporarySentiment(Sentiment sentiment) {
		if (sentiment != null) {
			temporarySentiments.remove(sentiment);
		}
	}

	public Set<Sentiment> getPermanentSentiments() {
		return permanentSentiments;
	}

	public void setPermanentSentiments(Set<Sentiment> permanentSentiments) {
		this.permanentSentiments = permanentSentiments;
	}

	public void addPermanentSentiment(Sentiment permanentSentiment) {
		if (permanentSentiment != null) {
			permanentSentiments.add(permanentSentiment);
		}
	}

	public void removePermanentSentiment(Sentiment permanentSentiment) {
		if (permanentSentiment != null) {
			permanentSentiments.remove(permanentSentiment);
		}
	}

	public Set<Sentiment> getRelatedSentiments() {
		return relatedSentiments;
	}

	public void setRelatedSentiments(Set<Sentiment> relatedSentiments) {
		this.relatedSentiments = relatedSentiments;
	}

	public void addRelatedSentiment(Sentiment relatedSentiment) {
		if (relatedSentiment != null) {
			relatedSentiments.add(relatedSentiment);
		}
	}

	public void removeRelatedSentiment(Sentiment relatedSentiment) {
		if (relatedSentiment != null) {
			relatedSentiments.remove(relatedSentiment);
		}
	}

	public Long getCreated_By() {
		return created_By;
	}

	public void setCreated_By(Long created_By) {
		this.created_By = created_By;
	}
}
