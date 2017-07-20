package com.ohmuk.folitics.businessDelegate.interfaces;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Contest;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.hibernate.entity.User;

public interface IContestBusinessDelegate {

	/**
	 * This method is used to add Contest .
	 * 
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.Contest
	 * @return com.ohmuk.folitics.jpa.entity.Contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */
	public Contest createContest(Contest contest) throws MessageException,
			Exception;

	/**
	 * This method is used to read particular contest from data base
	 * corresponding to the given id.
	 * 
	 * @param id
	 *            of contest
	 * @return : read corresponding contest
	 * @throws MessageException
	 *             ,Exception*
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
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.Contest
	 * @return: updated contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */
	public Contest update(Contest contest) throws MessageException, Exception;

	/**
	 * This method is used to Hard delete contest by id.
	 * 
	 * @param id
	 *            of contest
	 * @return: true if contest is successfully deleted
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */
	public boolean deleteFromDb(Long id) throws MessageException, Exception;

	/**
	 * This method is used to add participants for existing contest .
	 * 
	 * @param Long
	 *            luckyDrawId, Long userId
	 * @return Contest
	 * @throws com.ohmuk.folitics.exception.MessageException
	 *             ,Exception
	 * 
	 */

	public LuckyDraw addParticipants(Long luckyDrawId, Long userId)
			throws MessageException, Exception;

	/**
	 * This method is used to add Winners of already existing contest .
	 * 
	 * @param Contest
	 *            id
	 * @return Contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public LuckyDraw genrateWinner(Long luckyDrawId) throws MessageException,
			Exception;

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
	 * This method is used to read all the participants of a particular contest
	 * for given id.
	 * 
	 * @param luckyDrawId
	 * @return : set of participants
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */
	public Set<User> readAllParticipants(Long luckyDrawId)
			throws MessageException, Exception;

	/**
	 * This method is used to read all the Winners of a particular contest for
	 * given id.
	 * 
	 * @param luckyDrawId
	 * @return : set of winners
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */
	public Set<User> readAllWinners(Long luckyDrawId) throws MessageException,
			Exception;

	/**
	 * This method is used to read all the active contest		
	 * 
	 * @return : list of all active contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */

	public List<Contest> readAllActiveContest() throws MessageException,
			Exception;
	
	/**
	 * This method is used to read all the active contest  by start date and end date	
	 * 
	 * @return : list of all active contest
	 * @throws MessageException
	 *             ,Exception
	 * 
	 */
	
	public List<Contest> getByDate(Timestamp startDate, Timestamp endDate) throws MessageException, Exception;

}
