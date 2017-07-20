package com.ohmuk.folitics.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ohmuk.folitics.businessDelegate.interfaces.IContestBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Contest;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.util.DateUtils;

@Controller
@RequestMapping("/contest")
public class ContestController {

	private static Logger logger = LoggerFactory
			.getLogger(ContestController.class);

	@Autowired
	private IContestBusinessDelegate businessDelegate;

	/**
	 * This method is used to add contest. This is a multipart spring web
	 * service that requires image and json for contest.
	 * 
	 * @param Image
	 * @param Contest
	 * 
	 * @return: ResponseDto<Contest>
	 */

	@RequestMapping(value = "/addContest", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Contest> addContest(
			@RequestPart(value = "file") MultipartFile image,
			@RequestPart(value = "contest") Contest contest) {

		logger.info("Inside addContest method in controller");

		try {

			if (image == null) {

				logger.error("Image is null" + image);
				throw new MessageException("Image is required to add contest");
			}
			contest.setImage(image.getBytes());
			contest.setImageType(FileType.getFileType(
					image.getOriginalFilename().split("\\.")[1]).getValue());
			contest.setEditTime(DateUtils.getSqlTimeStamp());

			contest = businessDelegate.createContest(contest);

			if (contest != null) {

				logger.info("Exiting addContest method from controller");

				return new ResponseDto<Contest>(true, contest,
						"succesfully added contest");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while adding Contest", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while adding Contest", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

		return new ResponseDto<Contest>("Contest not added", false);
	}

	/**
	 * This method is used to display all contest.
	 * 
	 * @return List of contest
	 */

	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Contest> getAll() {

		logger.info("Inside getAll  method");

		try {

			List<Contest> contests = businessDelegate.readAll();

			logger.info("Exiting getAll method from contest controller");

			return new ResponseDto<Contest>(true, contests,
					"succesfully fethed contest ");

		} catch (MessageException exception) {

			logger.error("CustomException while fething all Contest", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething Contest", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to update Contest.
	 * 
	 * @param contest
	 * @return : updated contest
	 */

	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Contest> edit(
			@Validated @RequestBody Contest contest) {

		logger.info("Inside edit method in contest controller");

		try {
			contest.setEditTime(DateUtils.getSqlTimeStamp());
			contest = businessDelegate.update(contest);

			if (contest != null) {

				logger.info("Exiting update method in contest controller");

				return new ResponseDto<Contest>(true, contest,
						"succesfully updated contest");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while updating contest ", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		} catch (Exception exception) {

			logger.error("Exception while updating contest", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}
		return new ResponseDto<Contest>("contest not updated", false);
	}

	/**
	 * This method is used find Contest by id.
	 * 
	 * @param: contest id
	 * @return : corresponding contest
	 */

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Contest> find(Long id) {

		Contest contest;

		logger.info("Inside find method in contest controller");

		try {
			contest = businessDelegate.read(id);

			if (contest != null) {

				logger.info("Exiting find method in contest controller");

				return new ResponseDto<Contest>(true, contest,
						"contest succesfully found");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while finding contest by id",
					exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);

		} catch (Exception exception) {

			logger.error("Exception while finding contest by id", exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

		return new ResponseDto<Contest>("contest not found", false);
	}

	/**
	 * This method is used to Hard delete contest by id.
	 * 
	 * @param id
	 * @return contest
	 */

	@RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Contest> deleteFromDb(Long id) {
		try {
			logger.info(" Inside deleteFromDb method in controller");

			boolean success = businessDelegate.deleteFromDb(id);

			if (success == true) {

				logger.debug("Contest deleted succesfully for these id:-" + id);
				logger.info(" Exiting deleteFromDb method in controller");
				return new ResponseDto<Contest>(true);
			} else {
				logger.error("something went wrong while  deleting contest for these id:-"
						+ id);
				return new ResponseDto<Contest>(false);
			}

		} catch (MessageException exception) {
			logger.error("CustomException while  deleting contest with id",
					exception);
			return new ResponseDto<Contest>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting contest with id", exception);
			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to Soft delete contest by id.
	 * 
	 * @param id
	 * @return contest
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Contest> delete(Long id) {
		try {
			logger.info(" Inside delete method in controller");

			boolean success = businessDelegate.delete(id);

			if (success == true) {

				logger.debug("Contest deleted succesfully for these id:-" + id);
				logger.info(" Exiting delete method in controller");
				return new ResponseDto<Contest>(true);
			} else {
				logger.error("something went wrong while  deleting contest for these id:-"
						+ id);
				return new ResponseDto<Contest>(false);
			}

		} catch (MessageException exception) {
			logger.error("CustomException while  deleting contest with id",
					exception);
			return new ResponseDto<Contest>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting contest with id", exception);
			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to add Contest Participants.
	 * 
	 * @param Long
	 *            contestId, Long userId
	 * @return: ResponseDto<LuckyDraw>
	 */

	@RequestMapping(value = "/addParticipants", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<LuckyDraw> addContestParticipants(
			Long luckyDrawId, Long userId) {

		logger.info("Inside addContestParticipants method in contest controller");

		try {

			LuckyDraw luckyDraw = new LuckyDraw();
			luckyDraw = businessDelegate.addParticipants(luckyDrawId, userId);

			if (luckyDraw != null) {

				logger.info("Exiting addContestParticipants method in contest controller");

				return new ResponseDto<LuckyDraw>(true, luckyDraw,
						"succesfully added participants");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while adding  contest participants",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while adding contest participants",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		return new ResponseDto<LuckyDraw>("participants not added", false);
	}

	/**
	 * This method is used to add Contest Winners for given LuckyDraw id.
	 * 
	 * @param LuckyDraw
	 *            id
	 * @return: ResponseDto<Contest>
	 */

	@RequestMapping(value = "/genrateContestWinner", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<LuckyDraw> genrateContestWinner(
			Long luckyDrawId) {

		logger.info("Inside genrateContestWinner method in contest controller");

		try {

			LuckyDraw luckyDraw = new LuckyDraw();
			luckyDraw = businessDelegate.genrateWinner(luckyDrawId);

			if (luckyDraw != null) {

				logger.info("Exiting genrateContestWinner method in contest controller");

				return new ResponseDto<LuckyDraw>(true, luckyDraw,
						"succesfully added contest winners");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while adding  contest winners",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while adding contest winners", exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		return new ResponseDto<LuckyDraw>(" contest winners not added", false);
	}

	/**
	 * This method is used to display all active contest.
	 * 
	 * @return return list of active contest
	 */

	@RequestMapping(value = "/readAllActiveContest", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Contest> readAllActiveContest() {

		logger.info("Inside readAllActiveContest  method in contest controller");

		try {

			List<Contest> activeContests = businessDelegate
					.readAllActiveContest();

			logger.info("Exiting readAllActiveContest method in contest controller");

			return new ResponseDto<Contest>(true, activeContests,
					"succesfully fethed active contest");

		} catch (MessageException exception) {

			logger.error(" CustomException while fething all active Contest",
					exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething all active Contest",
					exception);

			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to get all participants of given LuckyDraw id.
	 * 
	 * @param luckyDrawId
	 * @return Set of participants
	 */

	@RequestMapping(value = "/readAllParticipants", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<User> readAllParticipants(Long luckyDrawId) {

		logger.info("Inside readAllParticipants  method in contest controller");
		try {

			Set<User> participants = businessDelegate
					.readAllParticipants(luckyDrawId);

			if (participants != null) {
				// TO DO Set is not convert in json. Temporary work around
				List<User> listOfParticipants = new ArrayList<User>();

				for (User user : participants) {

					listOfParticipants.add(user);

				}
				logger.info("Exiting readAllParticipants method in contest controller");

				return new ResponseDto<User>(true, listOfParticipants,
						"succesfully fethed participants");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while fething participants",
					exception);

			return new ResponseDto<User>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething participants", exception);

			return new ResponseDto<User>(exception.getMessage(), false);
		}

		return new ResponseDto<User>("participants does not exist", false);
	}

	/**
	 * This method is used to get all winners of particular LuckyDraw.
	 * 
	 * @param luckyDrawId
	 * @return Set of Winners
	 */

	@RequestMapping(value = "/readAllWinners", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<User> readAllWinners(Long luckyDrawId) {

		logger.info("Inside readAllWinners  method in contest controller");

		try {

			Set<User> winners = businessDelegate.readAllWinners(luckyDrawId);

			if (winners != null) {
				// TO DO Set is not convert in json. Temporary work around
				List<User> listOfWinner = new ArrayList<User>();

				for (User user : winners) {

					listOfWinner.add(user);

				}

				logger.info("Exiting readAllWinners method from contest controller");

				return new ResponseDto<User>(true, listOfWinner,
						"succesfully fethed Winners");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while fething Winners", exception);

			return new ResponseDto<User>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething Winners", exception);

			return new ResponseDto<User>(exception.getMessage(), false);
		}

		return new ResponseDto<User>("Winners doed not exist", false);
	}

	@RequestMapping(value = "/getByDate", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Contest> getByDate(
			@RequestParam Timestamp startDate, @RequestParam Timestamp endDate) {

		logger.info("Inside getByDate method ContestController");

		try {
			if (startDate == null) {
				logger.error("Start Date can't be null");
				throw (new MessageException("Start Date found to be null"));
			}

			if (endDate == null) {
				logger.error("End Date can't be null");
				throw (new MessageException("End Date found to be null"));
			}
			List<Contest> contests = businessDelegate.getByDate(startDate,
					endDate);

			logger.info("Exiting getByDate method from ContestController");
			return new ResponseDto<Contest>(true, contests,
					"Successfully fetched contest by date");

		} catch (MessageException exception) {
			logger.error("CustomException while fetching record of contest by date:"
					+ exception);
			return new ResponseDto<Contest>(exception.getMessage(), false);

		} catch (Exception exception) {
			logger.error("Exception while fatching record of contest by date:"
					+ exception);
			return new ResponseDto<Contest>(exception.getMessage(), false);
		}

	}
	
	@RequestMapping(value = "/fitbit", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Contest> fitbit(
			@RequestParam String fitbit) {
		System.out.println(fitbit);
		
		
		
		return new ResponseDto<Contest>(fitbit,true);
	}

}
