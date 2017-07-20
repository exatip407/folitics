package com.ohmuk.folitics.hibernate.entity.poll;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: PollOption
 * 
 * @author Abhishek
 * 
 */
@Entity
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class PollOption implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 512)
	@NotNull(message = "error.pollOption.pollOption.notNull")
	@Size(min = 1, max = 512, message = "error.pollOption.pollOption.size")
	private String pollOption;

	@Column(nullable = false)
	@NotNull(message = "error.pollOption.editTime.notNull")
	private Timestamp editTime;

	@Column(nullable = true)
	private Long editedBy;

	@Column(nullable = false)
	@NotNull(message = "error.pollOption.createTime.notNull")
	private Timestamp createTime;

	@Column(nullable = false)
	@NotNull(message = "error.pollOption.createdBy.notNull")
	private Long createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "poll", referencedColumnName = "id")
	@NotNull(message = "error.pollOption.poll.notNull")
	private Poll poll;

	@Column(nullable = false, length = 25, columnDefinition = "enum('Active','Disabled','Deleted')")
	@NotNull(message = "error.pollOption.state.notNull")
	private String state;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UserOption", joinColumns = @JoinColumn(name = "optionId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	private List<User> users;

	@OneToMany(mappedBy = "id.pollOption")
	private Set<PollOptionAgeGroup> pollOptionAgeGroups;

	@OneToMany(mappedBy = "id.pollOption")
	private Set<PollOptionSex> pollOptionSexes;

	@OneToMany(mappedBy = "id.pollOption")
	private Set<PollOptionMaritalStatus> pollOptionMaritalStatuses;

	@OneToMany(mappedBy = "id.pollOption")
	private Set<PollOptionReligion> pollOptionReligions;

	@OneToMany(mappedBy = "id.pollOption")
	private Set<PollOptionRegion> pollOptionRegions;

	@OneToMany(mappedBy = "id.pollOption")
	private Set<PollOptionQualification> pollOptionQualifications;

	@Transient
	private Long noOfVotes;

	public PollOption() {
		setCreateTime(DateUtils.getSqlTimeStamp());
		setEditTime(DateUtils.getSqlTimeStamp());
		setState(ComponentState.ACTIVE.getValue());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPollOption() {
		return pollOption;
	}

	public void setPollOption(String pollOption) {
		this.pollOption = pollOption;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the pollOptionAgeGroups
	 */
	public Set<PollOptionAgeGroup> getPollOptionAgeGroups() {
		return pollOptionAgeGroups;
	}

	/**
	 * @param pollOptionAgeGroups
	 *            the pollOptionAgeGroups to set
	 */
	public void setPollOptionAgeGroups(
			Set<PollOptionAgeGroup> pollOptionAgeGroups) {
		this.pollOptionAgeGroups = pollOptionAgeGroups;
	}

	/**
	 * @return the pollOptionSexes
	 */
	public Set<PollOptionSex> getPollOptionSexes() {
		return pollOptionSexes;
	}

	/**
	 * @param pollOptionSexes
	 *            the pollOptionSexes to set
	 */
	public void setPollOptionSexes(Set<PollOptionSex> pollOptionSexes) {
		this.pollOptionSexes = pollOptionSexes;
	}

	/**
	 * @return the pollOptionMaritalStatuses
	 */
	public Set<PollOptionMaritalStatus> getPollOptionMaritalStatuses() {
		return pollOptionMaritalStatuses;
	}

	/**
	 * @param pollOptionMaritalStatuses
	 *            the pollOptionMaritalStatuses to set
	 */
	public void setPollOptionMaritalStatuses(
			Set<PollOptionMaritalStatus> pollOptionMaritalStatuses) {
		this.pollOptionMaritalStatuses = pollOptionMaritalStatuses;
	}

	/**
	 * @return the pollOptionReligions
	 */
	public Set<PollOptionReligion> getPollOptionReligions() {
		return pollOptionReligions;
	}

	/**
	 * @param pollOptionReligions
	 *            the pollOptionReligions to set
	 */
	public void setPollOptionReligions(
			Set<PollOptionReligion> pollOptionReligions) {
		this.pollOptionReligions = pollOptionReligions;
	}

	/**
	 * @return the pollOptionRegions
	 */
	public Set<PollOptionRegion> getPollOptionRegions() {
		return pollOptionRegions;
	}

	/**
	 * @param pollOptionRegions
	 *            the pollOptionRegions to set
	 */
	public void setPollOptionRegions(Set<PollOptionRegion> pollOptionRegions) {
		this.pollOptionRegions = pollOptionRegions;
	}

	/**
	 * @return the pollOptionQualifications
	 */
	public Set<PollOptionQualification> getPollOptionQualifications() {
		return pollOptionQualifications;
	}

	/**
	 * @param pollOptionQualifications
	 *            the pollOptionQualifications to set
	 */
	public void setPollOptionQualifications(
			Set<PollOptionQualification> pollOptionQualifications) {
		this.pollOptionQualifications = pollOptionQualifications;
	}

	public void addUser(User user) {
		if (user != null) {
			users.add(user);
		}
	}

	public void removeUser(User user) {
		if (user != null) {
			users.remove(user);
		}
	}

	public Long getNoOfVotes() {
		return noOfVotes;
	}

	public void setNoOfVotes(Long noOfVotes) {
		this.noOfVotes = noOfVotes;
	}

	/*
    *//**
	 * @return the poll
	 */
	/*
	 * public Poll getPoll() { return poll; }
	 *//**
	 * @param poll
	 *            the poll to set
	 */
	/*
	 * public void setPoll(Poll poll) { this.poll = poll; }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result
				+ ((editedBy == null) ? 0 : editedBy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		// result = prime * result + ((poll == null) ? 0 : poll.hashCode());
		result = prime * result
				+ ((pollOption == null) ? 0 : pollOption.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollOption other = (PollOption) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (editedBy == null) {
			if (other.editedBy != null)
				return false;
		} else if (!editedBy.equals(other.editedBy))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		/*
		 * if (poll == null) { if (other.poll != null) return false; } else if
		 * (!poll.equals(other.poll)) return false;
		 */
		if (pollOption == null) {
			if (other.pollOption != null)
				return false;
		} else if (!pollOption.equals(other.pollOption))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", pollOption:" + pollOption + ", editTime:"
				+ editTime + ", editedBy:" + editedBy + ", createTime:"
				+ createTime + ", createdBy:" + createdBy /* + ", poll:" + poll */
				+ ", state:" + state + ", users:" + users + "}";
	}
}
