package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.hibernate.entity.User;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

//import com.ohmuk.folitics.jpa.entity.User;

/**
 * Entity implementaionn class for entity: Task
 * 
 * @author
 *
 */
@Entity
@Table(name = "task")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 256)
	private String subject;

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

	// @ManyToMany
	// @JoinTable(name = "Task_Participants", joinColumns = @JoinColumn(name =
	// "taskID", referencedColumnName = "id"), inverseJoinColumns =
	// @JoinColumn(name = "participantId", referencedColumnName = "id"))
	// @Cascade(CascadeType.DELETE)
	// private Set<User> participants;

	@OneToMany(mappedBy = "task")
	@Cascade(value = CascadeType.ALL)
	private Set<TaskParticipant> taskParticipants;

	@OneToMany(mappedBy = "task")
	private Set<TaskSolver> taskSolver;

	@OneToMany(mappedBy = "task")
	@Cascade(value = CascadeType.ALL)
	private Set<TaskPersonality> taskPersonality;

	public Set<TaskPersonality> getTaskPersonality() {
		return taskPersonality;
	}

	public void setTaskPersonality(Set<TaskPersonality> taskPersonality) {
		this.taskPersonality = taskPersonality;
	}

	public Set<TaskSolver> getTaskSolver() {
		return taskSolver;
	}

	public void setTaskSolver(Set<TaskSolver> taskSolver) {
		this.taskSolver = taskSolver;
	}

	@Column(length = 25)
	private String status;

	@Column
	private BigDecimal locationLat;

	@Column
	private BigDecimal locationLon;

	@Column
	private Long peopleAffected;

	@ManyToMany
	@JoinTable(name = "Task_category", joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "categoryId", referencedColumnName = "id"))
	private Set<TaskCategory> category;

	@Lob
	@Column
	private byte[] attachment;

	@Column
	private String attachmentType;

	@Lob
	@Column
	private byte[] image;

	@Column
	private String imageType;

	@Column(nullable = true)
	private String otherCategory;

	@ManyToMany
	@JoinTable(name = "Task_department", joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "departmentId", referencedColumnName = "id"))
	private Set<Department> departments;

	@Column(nullable = true)
	private String otherDepartment;

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

	@Column(nullable = true)
	@ManyToMany
	@Cascade(CascadeType.ALL)
	@JoinTable(name = "Task_person", joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "PersonId", referencedColumnName = "id"))
	private Set<PeopleMet> peopleMet;

	@Column(length = 50)
	private String city;

	@Column(length = 200)
	private String address;

	@Column(length = 50)
	private String email;

	@Column(length = 16)
	private String phone;

	@Column
	private boolean isDept;

	@Column
	private boolean isNGO;

	@Column
	private boolean showEmail;

	@Column
	private boolean showMobileNo;

	@OneToMany
	@JoinTable(name = "Task_question", joinColumns = @JoinColumn(name = "taskID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "questionId", referencedColumnName = "id"))
	private Set<Question> questionAnswers;

	@Column
	private boolean isLeader;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the creationDate
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * @return the editDate
	 */
	public Timestamp getEditDate() {
		return editDate;
	}

	/**
	 * @return the completionDate
	 */
	public Timestamp getCompletionDate() {
		return completionDate;
	}

	/**
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	//
	// /**
	// * @return the participants
	// */
	// public Set<User> getParticipants() {
	// return participants;
	// }

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the taskParticipants
	 */
	public Set<TaskParticipant> getTaskParticipants() {
		return taskParticipants;
	}

	/**
	 * @param taskParticipants
	 *            the taskParticipants to set
	 */
	public void setTaskParticipants(Set<TaskParticipant> taskParticipants) {
		this.taskParticipants = taskParticipants;
	}

	/**
	 * @return the locationLat
	 */
	public BigDecimal getLocationLat() {
		return locationLat;
	}

	/**
	 * @return the locationLon
	 */
	public BigDecimal getLocationLon() {
		return locationLon;
	}

	/**
	 * @return the peopleAffected
	 */
	public Long getPeopleAffected() {
		return peopleAffected;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @param editDate
	 *            the editDate to set
	 */
	public void setEditDate(Timestamp editDate) {
		this.editDate = editDate;
	}

	/**
	 * @param completionDate
	 *            the completionDate to set
	 */
	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	//
	// /**
	// * @param participants
	// * the participants to set
	// */
	// public void setParticipants(Set<User> participants) {
	// this.participants = participants;
	// }

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param locationLat
	 *            the locationLat to set
	 */
	public void setLocationLat(BigDecimal locationLat) {
		this.locationLat = locationLat;
	}

	/**
	 * @param locationLon
	 *            the locationLon to set
	 */
	public void setLocationLon(BigDecimal locationLon) {
		this.locationLon = locationLon;
	}

	/**
	 * @param peopleAffected
	 *            the peopleAffected to set
	 */
	public void setPeopleAffected(Long peopleAffected) {
		this.peopleAffected = peopleAffected;
	}

	/**
	 * @return the attachment
	 */
	public byte[] getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	/**
	 * @return the attachmentType
	 */
	public String getAttachmentType() {
		return attachmentType;
	}

	/**
	 * @param attachmentType
	 *            the attachmentType to set
	 */
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	/**
	 * @return the category
	 */
	public Set<TaskCategory> getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Set<TaskCategory> category) {
		this.category = category;
	}

	/**
	 * @return the otherCategory
	 */
	public String getOtherCategory() {
		return otherCategory;
	}

	/**
	 * @param otherCategory
	 *            the otherCategory to set
	 */
	public void setOtherCategory(String otherCategory) {
		this.otherCategory = otherCategory;
	}

	/**
	 * @return the departments
	 */
	public Set<Department> getDepartments() {
		return departments;
	}

	/**
	 * @param departments
	 *            the departments to set
	 */
	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	/**
	 * @return the otherDepartment
	 */
	public String getOtherDepartment() {
		return otherDepartment;
	}

	/**
	 * @param otherDepartment
	 *            the otherDepartment to set
	 */
	public void setOtherDepartment(String otherDepartment) {
		this.otherDepartment = otherDepartment;
	}

	/**
	 * @return the peopleMet
	 */
	public Set<PeopleMet> getPeopleMet() {
		return peopleMet;
	}

	/**
	 * @param peopleMet
	 *            the peopleMet to set
	 */
	public void setPeopleMet(Set<PeopleMet> peopleMet) {
		this.peopleMet = peopleMet;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the isDept
	 */
	public boolean isDept() {
		return isDept;
	}

	/**
	 * @param isDept
	 *            the isDept to set
	 */
	public void setDept(boolean isDept) {
		this.isDept = isDept;
	}

	/**
	 * @return the isNGO
	 */
	public boolean isNGO() {
		return isNGO;
	}

	/**
	 * @param isNGO
	 *            the isNGO to set
	 */
	public void setNGO(boolean isNGO) {
		this.isNGO = isNGO;
	}

	/**
	 * @return the isLeader
	 */
	public boolean isLeader() {
		return isLeader;
	}

	/**
	 * @param isLeader
	 *            the isLeader to set
	 */
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	/**
	 * @return the questionAnswers
	 */
	// public Set<QuestionAnswer> getQuestionAnswers() {
	// return questionAnswers;
	// }

	/**
	 * @param questionAnswers
	 *            the questionAnswers to set
	 */
	// public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) {
	// this.questionAnswers = questionAnswers;
	// }

	/**
	 * Method to dynamically add participant
	 * 
	 * @param participant
	 */
	/*
	 * public void addParticipant(User participant) { if (participant != null) {
	 * participants.add(participant); } }
	 */

	/**
	 * Method to dynamically remove participant
	 * 
	 * @param participant
	 */
	/*
	 * public void removeParticipant(User user,Task task) { if (participant !=
	 * null) { this.participants.remove(participant); } }
	 */

	/**
	 * @return the showEmail
	 */
	public boolean isShowEmail() {
		return showEmail;
	}

	/**
	 * @param showEmail
	 *            the showEmail to set
	 */
	public void setShowEmail(boolean showEmail) {
		this.showEmail = showEmail;
	}

	/**
	 * @return the showMobileNo
	 */
	public boolean isShowMobileNo() {
		return showMobileNo;
	}

	/**
	 * @param showMobileNo
	 *            the showMobileNo to set
	 */
	public void setShowMobileNo(boolean showMobileNo) {
		this.showMobileNo = showMobileNo;
	}
}
