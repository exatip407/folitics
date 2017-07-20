package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "luckyDraw")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class LuckyDraw implements Serializable {

	/**
	 * Entity implementation class for Entity: Contest
	 * 
	 * @author Harish
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(LuckyDraw.class);

	public LuckyDraw() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "enum('Bumper','Mini','Mega')")
	@NotNull(message = "error.luckyDraw.contestType.notNull")
	private String contestType;

	@Column(nullable = false)
	@NotNull(message = "error.luckyDraw.description.notNull")
	private String description;

	@Column(nullable = false)
	@NotNull(message = "error.luckyDraw.prizeAmount.notNull")
	private String prizeAmount;

	@Column(nullable = false)
	@NotNull(message = "error.luckyDraw.creationTime.notNull")
	private Timestamp creationTime;

	@Column(nullable = false)
	@NotNull(message = "error.luckyDraw.expirtTime.notNull")
	private Timestamp expiryTime;

	@Column(nullable = false)
	@NotNull(message = "error.luckyDraw.editTime.notNull")
	private Timestamp editTime;

	@Column(nullable = false, length = 512, columnDefinition = "enum('Active','Inactive','Draft','Deleted')")
	@NotNull(message = "error.luckyDraw.state.notNull")
	private String state;

	@Column(nullable = false)
	@NotNull(message = "error.contest.participationPoints.notNull")
	private Long participationPoints;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contestParticipants", joinColumns = @JoinColumn(name = "luckyDrawId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	private Set<User> contestParticipants;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contestWinners", joinColumns = @JoinColumn(name = "luckyDrawId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	private Set<User> contestWinners;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contestId", nullable = false, referencedColumnName = "id")
	private Contest contest;

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
	 * @return the contestType
	 */
	public String getContestType() {
		return contestType;
	}

	/**
	 * @param contestType
	 *            the contestType to set
	 */
	public void setContestType(String contestType) {
		this.contestType = contestType;
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
	 * @return the prizeAmount
	 */
	public String getPrizeAmount() {
		return prizeAmount;
	}

	/**
	 * @param prizeAmount
	 *            the prizeAmount to set
	 */
	public void setPrizeAmount(String prizeAmount) {
		this.prizeAmount = prizeAmount;
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
	 * @return the expiryTime
	 */
	public Timestamp getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expirtTime
	 *            the expiryTime to set
	 */
	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
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
	 * @return the participationPoints
	 */
	public Long getParticipationPoints() {
		return participationPoints;
	}

	/**
	 * @param participationPoints
	 *            the participationPoints to set
	 */
	public void setParticipationPoints(Long participationPoints) {
		this.participationPoints = participationPoints;
	}

	/**
	 * @return the contestParticipants
	 */
	public Set<User> getContestParticipants() {
		return contestParticipants;
	}

	/**
	 * @param contestParticipants
	 *            the contestParticipants to set
	 */
	public void setContestParticipants(Set<User> contestParticipants) {
		this.contestParticipants = contestParticipants;
	}

	/**
	 * @return the contestWinners
	 */
	public Set<User> getContestWinners() {
		return contestWinners;
	}

	/**
	 * @param contestWinners
	 *            the contestWinners to set
	 */
	public void setContestWinners(Set<User> contestWinners) {
		this.contestWinners = contestWinners;
	}

	/**
	 * @return the contest
	 */
	public Contest getContest() {
		return contest;
	}

	/**
	 * @param contest
	 *            the contest to set
	 */
	public void setContest(Contest contest) {
		this.contest = contest;
	}

	/**
	 * This is to update the participant list and to add the user as a
	 * participant in the contest.This will throw null pointer exception if Set
	 * of user is empty.
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void updateParticipants(User user) throws Exception {

		logger.info("Inside updateParticipants");

		getContestParticipants().add(user);

		logger.debug("User : " + user.getUsername()
				+ " has been added as a participant in the contest : "
				+ getContestType());
		logger.info("Exiting updateParticipants");
	}

	/**
	 * This is to update the Winner Set and to add the user as a Winner in the
	 * contest.This will throw null pointer exception if Set of user is empty.
	 * 
	 * @param user
	 * @throws Exception
	 */

	public void updateWinner(User user) {
		logger.info("Inside updateWinners");

		getContestWinners().add(user);

		logger.debug("User : " + user.getUsername()
				+ " has been added as a Winner in the contest : "
				+ getContestType());
		logger.info("Exiting updateWinners");

	}

	/**
	 * To add the user as a participant in the contest.This will create new Set
	 * ,if Set of user is empty and the argument as a first participant in the
	 * contest.
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void addParticipant(User user) throws Exception {

		logger.info("addParticipants");

		logger.debug("Participant set empty, creating new set for adding participants");

		Set<User> users = new HashSet<User>();
		users.add(user);
		setContestParticipants(users);

		logger.debug("User : " + user.getUsername()
				+ " has been added as a participant in the contest : "
				+ getContestType());
	}

	/**
	 * To add the user as a Winner in the contest.This will create new Set ,if
	 * Set of user is empty and the argument as a first Winner in the contest.
	 * 
	 * @param user
	 * @throws Exception
	 */

	public void addWinner(User user) {
		logger.info("addWinner");

		logger.debug("Winners set empty, creating new set for adding Winner");

		Set<User> users = new HashSet<User>();
		users.add(user);
		setContestWinners(users);

		logger.debug("User : " + user.getUsername()
				+ " has been added as a Winner in the contest : "
				+ getContestType());
	}
}
