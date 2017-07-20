package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.ISentimentBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictHeadlineDataBusinessDelegate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;
import com.ohmuk.folitics.util.FoliticsUtils;

/**
 * Business Delegate implementation for {@link Verdict}
 * 
 * @author Abhishek
 *
 */
@Component
public class VerdictBusinessDelegate implements IVerdictBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictBusinessDelegate.class);

	@Autowired
	private volatile IVerdictService service;

	@Autowired
	private volatile IVerdictHeadlineDataBusinessDelegate verdictHeadlineDataBusinessDelegate;

	@Autowired
	private volatile ISentimentBusinessDelegate sentimentBusinessDelegate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #create(com.ohmuk.folitics.jpa.entity. verdict.Verdict)
	 */
	@Override
	public Verdict create(Verdict verdict) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate create method");
		logger.debug("Trying to save the verdict object with sentimentId = "
				+ verdict.getSentiment().getId());

		verdict = service.create(verdict);

		logger.debug("Saved verdict object in database. Returning object and exiting VerdictBusinessDelegate create method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #read(java.lang.Long)
	 */
	@Override
	public Verdict read(Long id) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate read method");
		logger.debug("Trying to get the verdict object with id = " + id);

		Verdict verdict = service.read(id);

		logger.debug("Got verdict object from database. Returning object and exiting VerdictBusinessDelegate read method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #readAll()
	 */
	@Override
	public List<Verdict> readAll() {

		logger.debug("Entered VerdictBusinessDelegate readAll method");
		logger.debug("Trying to get all the verdict objects");

		List<Verdict> verdicts = service.readAll();

		logger.debug("Got verdict objects from database. Returning objects and exiting VerdictBusinessDelegate readAll method");

		return verdicts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #getVerdictForSentiment(java.lang.Long)
	 */
	@Override
	public Verdict getVerdictForSentiment(Long sentimentId)
			throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate getVerdictForSentiment method");
		logger.debug("Trying to get verdict object for sentimentId = "
				+ sentimentId);

		Verdict verdict = service.getVerdictForSentiment(sentimentId);

		logger.debug("Got verdict object. Exiting VerdictBusinessDelegate getVerdictForSentiment method");

		return verdict;
	}

	@Override
	public String getVerdictHeadline(Long sentimentId) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate getVerdictHeadline method");

		Verdict verdict = getVerdictForSentiment(sentimentId);

		List<VerdictHeadlineData> verdictHeadlineDatas = verdictHeadlineDataBusinessDelegate
				.readVerdictHeadlineDataForVerdict(verdict);

		if (verdictHeadlineDatas != null && verdictHeadlineDatas.size() > 0) {
			VerdictHeadlineData maxProVerdictHeadlineData = getHighestProVerdictHeadlineData(verdictHeadlineDatas);

			VerdictHeadlineData maxAntiVerdictHeadlineData = getHighestAntiVerdictHeadlineData(verdictHeadlineDatas);

			VerdictHeadlineData highestVerdictHeadlineData = new VerdictHeadlineData();

			String flag = "";
			Double percent = 0d;

			if (maxAntiVerdictHeadlineData.getAntiCount() >= maxProVerdictHeadlineData
					.getProCount()) {

				highestVerdictHeadlineData = maxAntiVerdictHeadlineData;
				flag = "not in favour";
				percent = (double) ((double) highestVerdictHeadlineData
						.getAntiCount()
						/ (highestVerdictHeadlineData.getAntiCount() + highestVerdictHeadlineData
								.getProCount()) * 100);

			} else if (maxProVerdictHeadlineData.getProCount() >= maxAntiVerdictHeadlineData
					.getAntiCount()) {

				highestVerdictHeadlineData = maxProVerdictHeadlineData;
				flag = "in favour";
				percent = (double) ((double) highestVerdictHeadlineData
						.getProCount()
						/ (highestVerdictHeadlineData.getAntiCount() + highestVerdictHeadlineData
								.getProCount()) * 100);
			}

			percent = FoliticsUtils.getRoundedDouble(percent);

			String headline = percent + "% of "
					+ highestVerdictHeadlineData.getRegion().getRegion()
					+ " Indian "
					+ highestVerdictHeadlineData.getReligion().getReligion()
					+ " " + highestVerdictHeadlineData.getSex().getSex()
					+ " are " + flag + " of "
					+ verdict.getSentiment().getSubject();

			logger.debug("Got verdict headline. Exiting VerdictBusinessDelegate getVerdictHeadline method");

			return headline;

		} else {
			return "";
		}
	}

	@Override
	public List<Verdict> getVerdictHeadlineForNSentiments(
			Integer noOfVerdictHeadlines) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate getVerdictHeadlineForNSentiments method");

		List<Verdict> verdicts = getLatestNVerdicts(noOfVerdictHeadlines);

		for (Verdict verdict : verdicts) {

			verdict.setHeadline(getVerdictHeadline(verdict.getSentiment()
					.getId()));
		}

		logger.debug("Got verdict headlines. Exiting VerdictBusinessDelegate getVerdictHeadlineForNSentiments method");

		return verdicts;
	}

	@Override
	public List<Verdict> getLatestNVerdicts(Integer noOfSentiments) {

		logger.info("Inside SentimentBusinessDelegate getLatestNSentiments method");

		List<Verdict> verdicts = service.getLatestNVerdicts(noOfSentiments);

		logger.info("Existing from SentimentBusinessDelegate getLatestNSentiments method");

		return verdicts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #update(com.ohmuk.folitics.jpa.entity. verdict.Verdict)
	 */
	@Override
	public Verdict update(Verdict verdict) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate update method");
		logger.debug("Trying to update verdict object with id = "
				+ verdict.getId() + " for sentimentId = "
				+ verdict.getSentiment().getId());

		verdict = service.update(verdict);

		logger.debug("Updated verdict object in database. Exiting VerdictBusinessDelegate update method.");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #delete(java.lang.Long)
	 */
	@Override
	public Verdict delete(Long id) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate delete method");
		logger.debug("Trying to delete verdict object with id = " + id);

		Verdict verdict = service.delete(id);

		logger.debug("Deleted verdict object in database. Exiting VerdictBusinessDelegate delete method.");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate
	 * #delete(com.ohmuk.folitics.jpa.entity. verdict.Verdict)
	 */
	@Override
	public Verdict delete(Verdict verdict) throws MessageException {

		logger.debug("Entered VerdictBusinessDelegate delete method");
		logger.debug("Trying to delete verdict object with id = "
				+ verdict.getId() + " and sentimentId = "
				+ verdict.getSentiment().getId());

		verdict = service.delete(verdict);

		logger.debug("Deleted verdict object in database. Exiting VerdictBusinessDelegate delete method.");

		return verdict;
	}

	public VerdictHeadlineData getHighestProVerdictHeadlineData(
			List<VerdictHeadlineData> verdictHeadlineDatas) {

		VerdictHeadlineData maxVerdictHeadlineData = new VerdictHeadlineData();

		maxVerdictHeadlineData = verdictHeadlineDatas.get(0);

		for (int i = 1; i < verdictHeadlineDatas.size(); i++) {

			if (verdictHeadlineDatas.get(i).getProCount() > maxVerdictHeadlineData
					.getProCount()) {

				maxVerdictHeadlineData = verdictHeadlineDatas.get(i);
			}
		}

		return maxVerdictHeadlineData;
	}

	public VerdictHeadlineData getHighestAntiVerdictHeadlineData(
			List<VerdictHeadlineData> verdictHeadlineDatas) {

		VerdictHeadlineData maxVerdictHeadlineData = new VerdictHeadlineData();

		maxVerdictHeadlineData = verdictHeadlineDatas.get(0);

		for (int i = 1; i < verdictHeadlineDatas.size(); i++) {

			if (verdictHeadlineDatas.get(i).getAntiCount() > maxVerdictHeadlineData
					.getAntiCount()) {

				maxVerdictHeadlineData = verdictHeadlineDatas.get(i);
			}
		}

		return maxVerdictHeadlineData;
	}

}
