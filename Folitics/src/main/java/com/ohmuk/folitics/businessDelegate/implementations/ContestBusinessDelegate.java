package com.ohmuk.folitics.businessDelegate.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IContestBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Contest;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.service.IContestService;
import com.ohmuk.folitics.service.ILuckyDrawService;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Component
@Transactional
public class ContestBusinessDelegate implements IContestBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(ContestBusinessDelegate.class);

	@Autowired
	private volatile IContestService contestService;

	@Autowired
	private ILuckyDrawService luckyDrawService;

	@Autowired
	private volatile IUserService userService;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	@Override
	public Contest createContest(Contest contest) throws MessageException,
			Exception {

		logger.info("Inside createContest method in Business Delegate");
		if (contest == null) {

			logger.error(" Input data found to be null, can not save contest");
			throw (new MessageException(
					" Input data found to be null, can not save contest"));
		}

		Set<LuckyDraw> luckyDrawSet = contest.getLuckyDraw();

		if (luckyDrawSet != null) {

			for (LuckyDraw luckyDraw : luckyDrawSet) {
				luckyDraw.setContest(contest);
				luckyDraw.setEditTime(DateUtils.getSqlTimeStamp());
			}

			Contest contestData = contestService.create(contest);
			logger.info("Exiting createContest method from Business Delegate");
			return contestData;
		} else {
			Contest contestData = contestService.create(contest);
			logger.info("Exiting createContest method from Business Delegate");
			return contestData;

		}
	}

	@Override
	public Contest read(Long id) throws MessageException, Exception {

		logger.info("Inside read method in business delegate");
		Contest contestData = contestService.read(id);
		logger.info("Exiting read method from business delegate");
		return contestData;

	}

	@Override
	public List<Contest> readAll() throws MessageException, Exception {

		logger.info("Inside readAll method in business delegate");
		List<Contest> contests = contestService.readAll();
		logger.info("Exiting readAll method from business delegate");
		return contests;
	}

	@Override
	public Contest update(Contest contest) throws MessageException, Exception {

		logger.info("Inside update method in business delegate");
		Contest contestData = contestService.update(contest);
		logger.info("Exiting update method from business delegate");
		return contestData;
	}

	@Override
	public boolean deleteFromDb(Long id) throws MessageException, Exception {

		logger.info("Inside deleteFromDb method in business delegate");
		boolean success = contestService.deleteFromDb(id);
		logger.info("Exiting deleteFromDb method from business delegate");
		return success;
	}

	@Override
	public LuckyDraw addParticipants(Long luckyDrawId, Long userId)
			throws MessageException, Exception {

		logger.info("Inside addParticipants in business delegate");

		if (luckyDrawId == null) {
			logger.error("LuckyDraw id is null,  participatants can't be added :"
					+ luckyDrawId);

			throw (new MessageException(
					"LuckyDraw id is null, participatants can't be added"
							+ luckyDrawId));
		}
		if (userId == null) {

			logger.error("User id is null,  participatants can't be added"
					+ userId);

			throw (new MessageException(
					"User id is null, participatants can't be added" + userId));
		}

		logger.debug("Fetching luckyDraw for luckyDrawId " + luckyDrawId);

		LuckyDraw luckyDraw = luckyDrawService.read(luckyDrawId);

		if (luckyDraw == null) {

			logger.error("Contest for luckyDraw id " + luckyDrawId
					+ " does not exist, participatants can't be added");

			throw (new MessageException("Contest for luckyDrawId "
					+ luckyDrawId
					+ " does not exist,  participatants can't be added"));
		}

		logger.debug("Contest for luckyDrawId " + luckyDrawId + " found "
				+ " contest type " + luckyDraw.getContestType());
		logger.debug("Fetching user for userId " + userId);

		User user = userService.findUserById(userId);

		if (user == null) {

			logger.error("User for userId " + userId
					+ " does not exist, participatants can't be added");

			throw (new MessageException("User for userId " + userId
					+ " does not exist, participatants can't be added"));
		}

		logger.debug("User for userId " + userId + " found " + " user name "
				+ user.getUsername());

		if (user.getPoints() == null) {

			logger.error("User points found to be null, Participants can't be added");

			throw (new MessageException(
					"User points found to be null, Participants can't be added"));
		}

		if (user.getPoints() >= luckyDraw.getParticipationPoints()) {

			UserMonetization userMonetization = new UserMonetization();
			userMonetization.setAction("Participate");
			userMonetization.setComponentType(luckyDraw.getContestType());
			userMonetization.setModule("Contest");
			userMonetization.setPoints(luckyDraw.getParticipationPoints());
			userMonetization.setUserId(userId);
			userMonetization.setActionComponentId(luckyDrawId);

			UserMonetizationBussinessDeligate.addAction(userMonetization);

			Hibernate.initialize(luckyDraw.getContestParticipants());
			Set<User> users = luckyDraw.getContestParticipants();

			int participantsSize = users.size();

			if (participantsSize > 0) {

				logger.debug("Updating participant set with new participant");
				logger.debug("Total participants before update "
						+ luckyDraw.getContestParticipants().size());

				luckyDraw.updateParticipants(user);

				LuckyDraw addedParticipant = contestService
						.addParticipants(luckyDraw);

				logger.debug("Participant list updated");
				logger.debug("Total participants after update "
						+ luckyDraw.getContestParticipants().size());

				return addedParticipant;

			} else {

				logger.debug("No participants yet for this contest");
				logger.debug("Adding participants");

				luckyDraw.addParticipant(user);

				LuckyDraw addedParticipant = contestService
						.addParticipants(luckyDraw);

				logger.debug("Participant added");
				logger.debug("Total participants after update "
						+ luckyDraw.getContestParticipants().size());

				return addedParticipant;
			}
		} else {
			logger.error("You don't have suffcient points to participate in these LuckyDraw: "
					+ luckyDraw.getParticipationPoints()
					+ "points is required for participate in these contest");

			throw (new MessageException(
					"You don't have suffcient points to participate in these LuckyDraw: "
							+ luckyDraw.getParticipationPoints()
							+ "points is required for participate in these contest"));
		}
	}

	@Override
	public LuckyDraw genrateWinner(Long luckyDrawId) throws MessageException,
			Exception {

		if (luckyDrawId == null) {

			logger.error("luckyDrawId id is null,  Winners can't be added"
					+ luckyDrawId);

			throw (new MessageException(
					"luckyDrawId id is null,  Winners can't be added"
							+ luckyDrawId));
		}

		LuckyDraw luckyDraw = luckyDrawService.read(luckyDrawId);

		if (luckyDraw == null) {

			logger.error("Contest for luckyDrawId " + luckyDrawId
					+ " does not exist, Winners can't be added");

			throw (new MessageException("Contest for luckyDrawId "
					+ luckyDrawId + " does not exist,  Winners can't be added"));
		}
		Hibernate.initialize(luckyDraw.getContestParticipants());
		Set<User> users = luckyDraw.getContestParticipants();

		ArrayList<User> usersList = new ArrayList<User>();

		usersList.addAll(users);

		int userCount = users.size();

		if (userCount > 0) {

			Set<User> set = luckyDraw.getContestParticipants();

			Long random = (long) set.size();

			int index = 1 + (int) (Math.random() * (random - 1));
			index -= 1;
			User user = usersList.get(index);
			Hibernate.initialize(luckyDraw.getContestWinners());
			Set<User> winners = luckyDraw.getContestWinners();
			int winnerSize = winners.size();

			if (winnerSize > 0) {

				logger.debug("Updating Winner set with new Winner");
				logger.debug("Total Winner before update "
						+ luckyDraw.getContestWinners().size());

				luckyDraw.updateWinner(user);

				LuckyDraw addedWinners = contestService
						.genrateWinner(luckyDraw);

				logger.debug("Winners list updated");

				return addedWinners;
			} else {
				logger.debug("No Winners yet for this contest");
				logger.debug("Adding Winners");

				luckyDraw.addWinner(user);
				LuckyDraw addedWinners = contestService
						.genrateWinner(luckyDraw);

				logger.debug("Winner added succesfully");
				return addedWinners;

			}

		} else {

			logger.debug("No Participants yet for this contest,please added Participants");
			throw new MessageException(
					"No Participants yet for this contest,please added Participants");
		}

	}

	@Override
	public Set<User> readAllParticipants(Long luckyDrawId)
			throws MessageException, Exception {

		logger.info("Inside readAllParticipants method in business delegate");

		if (luckyDrawId == null) {

			logger.error("luckyDrawId can't be null, can not get participants corrosponding to these id"
					+ luckyDrawId);

			throw (new MessageException(
					"luckyDrawId can't be null, can not get participants corrosponding to these id :"
							+ luckyDrawId));
		}
		LuckyDraw luckyDraw = luckyDrawService.read(luckyDrawId);
		if (luckyDraw == null) {

			logger.error("Contest for luckyDrawId " + luckyDrawId
					+ " does not exist");

			throw (new MessageException("Contest for luckyDrawId "
					+ luckyDrawId + " does not exist"));
		}
		Hibernate.initialize(luckyDraw.getContestParticipants());
		Set<User> users = luckyDraw.getContestParticipants();
		int size = users.size();

		if (size > 0) {

			logger.info("Exiting readAllParticipants method from business delegate");

			return users;

		} else {
			logger.debug("No participants yet for this LuckyDraw id"
					+ luckyDrawId);
			throw new MessageException(
					"No participants yet for this LuckyDraw id" + luckyDrawId);
		}

	}

	@Override
	public Set<User> readAllWinners(Long luckyDrawId) throws MessageException,
			Exception {

		logger.info("Inside readAllWinners method in Business Delegate");

		if (luckyDrawId == null) {

			logger.error("luckyDrawId can't be null, can not get winners corrosponding to these id"
					+ luckyDrawId);

			throw (new MessageException(
					"luckyDrawId can't be null, can not get winners corrosponding to these id :"
							+ luckyDrawId));
		}
		LuckyDraw luckyDraw = luckyDrawService.read(luckyDrawId);
		if (luckyDraw == null) {

			logger.error("Contest for luckyDrawId " + luckyDrawId
					+ " does not exist");

			throw (new MessageException("Contest for luckyDrawId "
					+ luckyDrawId + " does not exist"));
		}
		Hibernate.initialize(luckyDraw.getContestParticipants());
		Set<User> users = luckyDraw.getContestParticipants();
		int participantsSize = users.size();

		if (participantsSize > 0) {
			Hibernate.initialize(luckyDraw.getContestWinners());
			Set<User> winners = luckyDraw.getContestWinners();

			if (winners.size() > 0) {

				logger.info("Exiting readAllWinners method from Business Delegate");

				return winners;
			} else {
				logger.debug("No Winners yet for this LuckyDraw id"
						+ luckyDrawId);
				throw new MessageException(
						"No Winners yet for this LuckyDraw id" + luckyDrawId);
			}
		} else {
			logger.debug("No participants yet for this LuckyDraw id"
					+ luckyDrawId + " So Winners does not exist");
			throw new MessageException(
					"No participants yet for this LuckyDraw id" + luckyDrawId
							+ " So Winners does not exist");
		}

	}

	@Override
	public List<Contest> readAllActiveContest() throws MessageException,
			Exception {

		logger.info("Inside readAllActiveContest method in business delegate");
		List<Contest> activeContests = contestService.readAllActiveContest();
		logger.info("Exiting readAllActiveContest method from business delegate");
		return activeContests;
	}

	@Override
	public boolean delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete method in business delegate");
		boolean success = contestService.delete(id);
		logger.info("Exiting delete method from business delegate");
		return success;
	}

	@Override
	public List<Contest> getByDate(Timestamp startDate, Timestamp endDate)
			throws MessageException, Exception {
		logger.info("Inside getByDate method in business delegate");
		List<Contest> Contests = contestService.getByDate(startDate, endDate);
		logger.info("Exiting getByDate method from business delegate");
		return Contests;
	}

}
