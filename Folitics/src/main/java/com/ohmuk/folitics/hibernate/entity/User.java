package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.hibernate.entity.task.TaskParticipant;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: User
 * 
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(unique = true, nullable = false, length = 255)
	@NotNull(message = "error.user.username.notNull")
	@Size(min = 1, max = 512, message = "error.user.username.size")
	private String username;

	@Column(nullable = false, length = 255)
	@NotNull(message = "error.user.password.notNull")
	@Size(min = 1, max = 255, message = "error.user.password.size")
	private String password;

	@Column(nullable = false, length = 512)
	@NotNull(message = "error.user.emailId.notNull")
	@Size(min = 1, max = 512, message = "error.user.emailId.size")
	private String emailId;

	@Column(nullable = false, length = 512)
	@NotNull(message = "error.user.name.notNull")
	@Size(min = 1, max = 512, message = "error.user.name.size")
	private String name;

	@Column(nullable = false, length = 255, columnDefinition = "enum('Male','Female','Other')")
	@NotNull(message = "error.user.gender.notNull")
	@Size(min = 1, max = 255, message = "error.user.gender.size")
	private String gender;

	@Column(nullable = false, length = 128, columnDefinition = "enum('VerifiedByEmail','VerifiedByMobileNo','VerifiedByBoth','NotVerified','Active','Disabled','Deleted')")
	private String status;

	@Column(nullable = true)
	private Timestamp dob;

	@Column(nullable = true, length = 255)
	private String maritalStatus;

	@Column(nullable = true, length = 255)
	private String state;

	@Column(nullable = true, length = 256)
	private String religion;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Set<UserAssociation> userAssociation;

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Set<UserEducation> userEducation;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Set<UserEmailNotificationSettings> userEmailNotificationSettings;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Set<UserUINotification> userUINotification;

	@OneToOne(fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private UserImage userImage;

	@ManyToMany(mappedBy = "contestParticipants", fetch = FetchType.LAZY)
	private Set<LuckyDraw> contestParticipants;

	@ManyToMany(mappedBy = "contestWinners", fetch = FetchType.LAZY)
	private Set<LuckyDraw> contestWinners;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	private List<Fact> fact;

	@OneToMany(mappedBy = "user")
	private Set<TaskParticipant> taskParticipants;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Achievement> achievements;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Leader> leaders;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<PoliticalView> politicalViews;
	
	/*
	 * @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	 * 
	 * @Cascade(CascadeType.ALL) private Set<UserPrivacyData>
	 * userPrivacySettings;
	 */

	@Column(nullable = true)
	private Double points;

	@Column(nullable = true)
	private String badge;

	@Column(nullable = true)
	private Long inclinationAggregation;

	@Transient
	private UserProfile userprofile;

	@ManyToOne(fetch = FetchType.EAGER)
	private UserRole role;

	public User() {
		super();
	}

	public User(Long id, String username, String password, String emailId,
			String name) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.emailId = emailId;
		this.name = name;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the userAssociation
	 */
	public Set<UserAssociation> getUserAssociation() {
		return userAssociation;
	}

	/**
	 * @param userAssociation
	 *            the userAssociation to set
	 */
	public void setUserAssociation(Set<UserAssociation> userAssociation) {
		this.userAssociation = userAssociation;
	}

	/**
	 * @return the userEducation
	 */
	public Set<UserEducation> getUserEducation() {
		return userEducation;
	}

	/**
	 * @param userEducation
	 *            the userEducation to set
	 */
	public void setUserEducation(Set<UserEducation> userEducation) {
		this.userEducation = userEducation;
	}

	/**
	 * @return the userEmailNotificationSettings
	 */
	public Set<UserEmailNotificationSettings> getUserEmailNotificationSettings() {
		return userEmailNotificationSettings;
	}

	/**
	 * @param userEmailNotificationSettings
	 *            the userEmailNotificationSettings to set
	 */
	public void setUserEmailNotificationSettings(
			Set<UserEmailNotificationSettings> userEmailNotificationSettings) {
		this.userEmailNotificationSettings = userEmailNotificationSettings;
	}

	/**
	 * @return the userUINotification
	 */
	public Set<UserUINotification> getUserUINotification() {
		return userUINotification;
	}

	/**
	 * @param userUINotification
	 *            the userUINotification to set
	 */
	public void setUserUINotification(Set<UserUINotification> userUINotification) {
		this.userUINotification = userUINotification;
	}

	/**
	 * @return the fact
	 */
	public List<Fact> getFact() {
		return fact;
	}

	/**
	 * @param fact the fact to set
	 */
	public void setFact(List<Fact> fact) {
		this.fact = fact;
	}

	/**
	 * @return the taskParticipants
	 */
	public Set<TaskParticipant> getTaskParticipants() {
		return taskParticipants;
	}

	/**
	 * @param taskParticipants the taskParticipants to set
	 */
	public void setTaskParticipants(Set<TaskParticipant> taskParticipants) {
		this.taskParticipants = taskParticipants;
	}

	/**
	 * @return the userImage
	 */
	public UserImage getUserImage() {
		return userImage;
	}

	/**
	 * @return the contestParticipants
	 */
	public Set<LuckyDraw> getContestParticipants() {
		return contestParticipants;
	}

	/**
	 * @param contestParticipants
	 *            the contestParticipants to set
	 */
	public void setContestParticipants(Set<LuckyDraw> contestParticipants) {
		this.contestParticipants = contestParticipants;
	}

	/**
	 * @return the contestWinners
	 */
	public Set<LuckyDraw> getContestWinners() {
		return contestWinners;
	}

	/**
	 * @param contestWinners
	 *            the contestWinners to set
	 */
	public void setContestWinners(Set<LuckyDraw> contestWinners) {
		this.contestWinners = contestWinners;
	}

	/**
	 * @param userImage
	 *            the userImage to set
	 */
	public void setUserImage(UserImage userImage) {
		this.userImage = userImage;
	}

	/*    *//**
	 * @return the userPrivacySettings
	 */
	/*
	 * public Set<UserPrivacyData> getUserPrivacySettings() { return
	 * userPrivacySettings; }
	 *//**
	 * @param userPrivacySettings
	 *            the userPrivacySettings to set
	 */
	/*
	 * public void setUserPrivacySettings(Set<UserPrivacyData>
	 * userPrivacySettings) { this.userPrivacySettings = userPrivacySettings; }
	 */

	/**
	 * @return the points
	 */
	public Double getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(Double points) {
		this.points = points;
	}

	/**
	 * @return the badge
	 */
	public String getBadge() {
		return badge;
	}

	/**
	 * @param badge
	 *            the badge to set
	 */
	public void setBadge(String badge) {
		this.badge = badge;
	}

	/**
	 * @return the inclinationAggregation
	 */
	public Long getInclinationAggregation() {
		return inclinationAggregation;
	}

	/**
	 * @param inclinationAggregation
	 *            the inclinationAggregation to set
	 */
	public void setInclinationAggregation(Long inclinationAggregation) {
		this.inclinationAggregation = inclinationAggregation;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * @return the userprofile
	 */
	public UserProfile getUserprofile() {
		return userprofile;
	}

	/**
	 * @param userprofile
	 *            the userprofile to set
	 */
	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	/**
	 * @return the dob
	 */
	public Timestamp getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Timestamp dob) {
		this.dob = dob;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus
	 *            the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion
	 *            the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}
}
