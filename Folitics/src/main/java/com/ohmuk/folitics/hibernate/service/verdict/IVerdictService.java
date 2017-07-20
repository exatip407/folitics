package com.ohmuk.folitics.hibernate.service.verdict;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Service interface for {@link Verdict}
 * 
 * @author Abhishek
 *
 */
public interface IVerdictService {

	/**
	 * This method saves the object of Verdict in database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict create(Verdict verdict) throws MessageException;

	/**
	 * This method returns object where id is equals to the given id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict read(Long id) throws MessageException;

	/**
	 * This method returns all the objects of {@link Verdict} present in
	 * database table
	 * 
	 * @author Abhishek
	 * @return java.util.List<com.ohmuk.folitics.jpa.entity.verdict.Verdict>
	 */
	public List<Verdict> readAll();

	/**
	 * This method returns object where sentiment id is equals to the given
	 * sentiment id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict getVerdictForSentiment(Long sentimentId)
			throws MessageException;

	/**
	 * This method updates the object in database and then return the updated
	 * object
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict update(Verdict verdict) throws MessageException;

	/**
	 * This method deletes the object from database table
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict delete(Long id) throws MessageException;

	/**
	 * This method deletes the object from database table
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict delete(Verdict verdict) throws MessageException;

	/**
	 * This method is for getting latest n {@link Verdict}
	 * 
	 * @author Abhishek
	 * @param Integer
	 *            noOfSentiments
	 * @return java.util.List<Verdict>
	 */
	public List<Verdict> getLatestNVerdicts(Integer noOfSentiments);
}
