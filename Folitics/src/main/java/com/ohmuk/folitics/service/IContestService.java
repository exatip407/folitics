package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Contest;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;

public interface IContestService {

	/**
	 * This method is used to add Contest.
	 * 
	 * @param com.ohmuk.folitics.jpa.entity.Contest
	 * @return com.ohmuk.folitics.jpa.entity.Contest
	 * @throws MessageException,Exception
	 * 
	 */
	public Contest create(Contest contest) throws MessageException, Exception;

	/**
	 * This method is used to read all information of contest corresponding to
	 * the particular id.
	 * 
	 * @param id
	 * @return : read contest
	 * @throws MessageException
	 * 
	 */

	public Contest read(Long id) throws MessageException, Exception;

	/**
	 * This method is used to display all Contest.
	 * 
	 * @return: list of Contest
	 * @throws MessageException
	 *             ,Exception
	 */

	public List<Contest> readAll() throws MessageException, Exception;

	/**
	 * This method is used to update the Contest.
	 * 
	 * @param contest
	 * @return: updated contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public Contest update(Contest contest) throws MessageException, Exception;

	/**
	 * This method is used to Hard delete contest by id.
	 * 
	 * @param contest
	 *            id
	 * @return: boolean value true, if successfully deleted contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public boolean deleteFromDb(Long id) throws MessageException, Exception;

	/**
	 * This method is used to add participants for existing LuckyDraw .
	 * 
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.Contest
	 * 
	 * @return com.ohmuk.folitics.jpa.entity.Contest
	 * @throws com.ohmuk.folitics.exception.MessageException
	 *             ,Exception
	 * 
	 */

	public LuckyDraw addParticipants(LuckyDraw luckyDraw) throws MessageException, Exception;

	/**
	 * This method is used to add Winners of already existing LuckyDraw .
	 * 
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.Contest
	 * 
	 * @return com.ohmuk.folitics.jpa.entity.Contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public LuckyDraw genrateWinner(LuckyDraw luckyDraw) throws MessageException, Exception;

	/**
	 * This method is used to read all the active contest
	 * 
	 * @return : list of all active contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public List<Contest> readAllActiveContest() throws MessageException, Exception;

	/**
	 * This method is used to soft delete the contest by id.
	 * 
	 * @param contest
	 *            id
	 * @return: boolean value true, if successfully deleted contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public boolean delete(Long id) throws MessageException, Exception;

	/**
	 * This method is used to read all the active contest by start date and end
	 * date
	 * 
	 * @return : list of all active contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public List<Contest> getByDate(Timestamp startDate, Timestamp endDate) throws MessageException, Exception;
}
