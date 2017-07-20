package com.ohmuk.folitics.hibernate.repository.verdict;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Hibernate repository interface for {@link Verdict}
 * 
 * @author Abhishek
 *
 */
public interface IVerdictRepository {

	/**
	 * This method saves the object of Verdict in the database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	public Verdict save(Verdict verdict);

	/**
	 * This method gets the object of Verdict from the database for the given id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	public Verdict find(Long id);

	/**
	 * This method gets all the objects of Verdict from the database
	 * 
	 * @author Abhishek
	 * @return java.util.List<com.ohmuk.folitics.jpa.entity.verdict.Verdict>
	 */
	public List<Verdict> findAll();

	/**
	 * This method gets the object of Verdict from the database for the given
	 * sentment id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	public Verdict getVerdictForSentiment(Long sentimentId);

	/**
	 * This method is for getting latest n {@link Verdict}
	 * 
	 * @author Abhishek
	 * @param Integer
	 *            no. of {@link Sentiment}
	 * @return java.util.List<Verdict>
	 */
	public List<Verdict> readLatestNVerdicts(Integer noOfSentiments);

	/**
	 * This method updates the object of Verdict in the database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	public Verdict update(Verdict verdict);

	/**
	 * This deletes the object of Verdict from database with the given id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 */
	public void delete(Long id);

	/**
	 * This deletes the object of Verdict from database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	public void delete(Verdict verdict);

}
