package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Business Delegate interface for {@link Verdict}
 * 
 * @author Abhishek
 *
 */
public interface IVerdictBusinessDelegate {

	/**
	 * Business delegate method to create Verdict by calling
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService.create
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict create(Verdict verdict) throws MessageException;

	/**
	 * Business Delegate method to get Verdict object using
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService.read by
	 * passing the id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict read(Long id) throws MessageException;

	/**
	 * Business Delegate method to get all Verdict objects using
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService.readAll
	 * 
	 * @author Abhishek
	 * @return java.util.List<com.ohmuk.folitics.jpa.entity.verdict.Verdict>
	 */
	public List<Verdict> readAll();

	/**
	 * Business Delegate method to get verdict for sentiment id using
	 * com.ohmuk.folitics
	 * .hibernate.service.verdict.IVerdictService.getVerdictForSentiment
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
	 * BusinessDelegate method for forming the headline for the verdict of
	 * sentiment with given sentiment id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long sentimentId the id of the sentiment for which
	 *            verdict headline is required
	 * @return java.lang.String the headline of the verdict for given sentiment
	 *         idd
	 * @throws MessageException
	 */
	public String getVerdictHeadline(Long sentimentId) throws MessageException;

	/**
	 * This method is to get headlines for n sentiments
	 * 
	 * @author Abhishek
	 * @param Integer
	 *            noOfVerdictHeadlines
	 * @return java.util.List<Verdict>
	 * @throws MessageException
	 */
	public List<Verdict> getVerdictHeadlineForNSentiments(
			Integer noOfVerdictHeadlines) throws MessageException;

	/**
	 * Business Delegate method to update Verdict object using
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService.update
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict update(Verdict verdict) throws MessageException;

	/**
	 * Business Delegate method to permanent delete Verdict object using
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService.delete by
	 * passing id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict delete(Long id) throws MessageException;

	/**
	 * Business Delegate method to permanent delete Verdict object using
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService.delete by
	 * passing object of com.ohmuk.folitics.jpa.entity.verdict.Verdict
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
