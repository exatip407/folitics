package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.enums.ComponentType;
import com.ohmuk.folitics.hibernate.entity.attachment.Attachment;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: Opinion
 * 
 * @author Abhishek
 */
@Entity
@Table(name = "opinion")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
@PrimaryKeyJoinColumn(name = "id")
public class Opinion extends Component implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@NotNull(message = "error.opinion.editTime.notNull")
	private Timestamp editTime;

	@Column(nullable = false, length = 200)
	@NotNull(message = "error.opinion.subject.notNull")
	@Size(min = 1, max = 200, message = "error.opinion.subject.size")
	private String subject;

	@Column(nullable = false, length = 1000)
	@NotNull(message = "error.opinion.text.notNull")
	@Size(min = 1, max = 1000, message = "error.opinion.text.size")
	private String text;

	@Column(nullable = false)
	@NotNull(message = "error.opinion.createTime.notNull")
	private Timestamp createTime;

	@Column(length = 25, columnDefinition = "enum('Anti','Pro')")
	@NotNull(message = "error.opinion.type.notNull")
	private String type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
	@NotNull(message = "error.opinion.category.notNull")
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "componentopinion", joinColumns = { @JoinColumn(name = "opinionId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "componentId", referencedColumnName = "id") })
	@NotNull(message = "error.opinion.component.notNull")
	private Component component;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "sentimentopinion", joinColumns = { @JoinColumn(name = "opinionId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "sentimentId", referencedColumnName = "id") })
	@NotNull(message = "error.opinion.sentiment.notNull")
	private Sentiment sentiment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "useropinion", joinColumns = { @JoinColumn(name = "opinionId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") })
	@NotNull(message = "error.opinion.user.notNull")
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "linkId", referencedColumnName = "id")
	@Cascade(value = CascadeType.ALL)
	private Link link;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "attachmentId", referencedColumnName = "id")
	@Cascade(value = CascadeType.ALL)
	private Attachment attachment;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Active','Deleted')")
	private String state;

	@OneToMany(mappedBy = "component")
	private List<Response> responses;

	public Opinion() {
		setComponentType(ComponentType.OPINION.getValue());
		setCreateTime(DateUtils.getSqlTimeStamp());
		setEditTime(DateUtils.getSqlTimeStamp());
		setState(ComponentState.ACTIVE.getValue());
	}

	public Timestamp getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Sentiment getSentiment() {
		return sentiment;
	}

	public void setSentiment(Sentiment sentiment) {
		this.sentiment = sentiment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

}
