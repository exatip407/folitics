package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Controller for Verdict module
 * 
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/verdict")
public class VerdictController {

	final static Logger logger = Logger.getLogger(VerdictController.class);

	@Autowired
	private IVerdictBusinessDelegate businessDelegate;

	@RequestMapping
	public String getVerdictPage() {
		return "verdict-page";
	}

	/**
	 * Spring web service(POST) to add Verdict object in database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Verdict> add(
			@Validated @RequestBody Verdict verdict) {

		logger.debug("Entered VerdictController add method");

		try {

			logger.debug("Trying to save Verdict object with sentimentId = "
					+ verdict.getSentiment().getId());

			verdict = businessDelegate.create(verdict);

		} catch (MessageException e) {

			logger.error("Exception occured while saving verdict object", e);
			logger.debug("Exiting VerdictController add method due to error.");
			return new ResponseDto<Verdict>(false);
			// e.printStackTrace();
		}

		if (verdict != null) {

			logger.debug("Added Verdict object and have id = "
					+ verdict.getId() + " for sentimentId = "
					+ verdict.getSentiment().getId());

			return new ResponseDto<Verdict>(true, verdict);
		}

		logger.debug("Verdict found null after adding into database. Exiting VerdictController add method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to get Verdict object from database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Verdict> find(Long id) {

		logger.debug("Entered VerdictController find method");

		Verdict verdict = new Verdict();
		try {

			logger.debug("Trying to get Verdict object with id = " + id);

			verdict = businessDelegate.read(id);

		} catch (MessageException e) {

			logger.error("Exception occured while getting verdict object", e);
			logger.debug("Exiting VerdictController find method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (verdict != null) {

			logger.debug("Got Verdict object and have id = " + verdict.getId()
					+ " for sentimentId = " + verdict.getSentiment().getId());

			return new ResponseDto<Verdict>(true, verdict);
		}

		logger.debug("Verdict found null while getting from database. Exiting VerdictController find method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to get all Verdict objects from database
	 * 
	 * @author Abhishek
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Verdict> getAll() {

		logger.debug("Entered VerdictController getAll method");

		List<Verdict> verdicts = new ArrayList<Verdict>();

		logger.debug("Trying to get all Verdict objects");

		verdicts = businessDelegate.readAll();

		if (verdicts != null) {

			logger.debug("Got all Verdict objects. Exiting VerdictController getAll method");

			return new ResponseDto<Verdict>(true, verdicts);
		}

		logger.debug("List of Verdict found null while getting from database. Exiting VerdictController getAll method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to get Verdict object for sentimentId from
	 * database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/getVerdictForSentiment", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Verdict> getVerdictForSentiment(
			Long sentimentId) {

		logger.debug("Entered VerdictController getVerdictForSentiment method");

		Verdict verdict = new Verdict();

		try {

			logger.debug("Trying to get Verdict object for sentimentId = "
					+ sentimentId);

			verdict = businessDelegate.getVerdictForSentiment(sentimentId);

		} catch (MessageException e) {

			logger.error("Exception occured while getting verdict object", e);
			logger.debug("Exiting VerdictController getVerdictForSentiment method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (verdict != null) {

			logger.debug("Got Verdict object from database. Exiting VerdictController getVerdictForSentiment method");

			return new ResponseDto<Verdict>(true, verdict);
		}

		logger.debug("Verdict found null while getting from database. Exiting VerdictController getVerdictForSentiment method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to get Verdict headline for sentimentId from
	 * database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return String verdict headline
	 */
	@RequestMapping(value = "/getVerdictHeadline", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Verdict> getVerdictHeadline(
			Long sentimentId) {

		logger.debug("Entered VerdictController getVerdictHeadline method");

		String headline = "";

		try {

			logger.debug("Trying to get Headline object for sentimentId = "
					+ sentimentId);

			headline = businessDelegate.getVerdictHeadline(sentimentId);

		} catch (MessageException e) {

			logger.error("Exception occured while getting headline", e);
			logger.debug("Exiting VerdictController getVerdictHeadline method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (headline != null && !headline.equals("")) {

			logger.debug("Got headline from database. Exiting VerdictController getVerdictHeadline method");

			return new ResponseDto<Verdict>(headline, true);
		}

		logger.debug("Headline found null while getting from database. Exiting VerdictController getVerdictHeadline method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to get Verdict headlines for multiple sentiments
	 * database
	 * 
	 * @author Abhishek
	 * @param int number of verdict headlines required
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/getVerdictHeadlineForNSentiments", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Verdict> getVerdictHeadlineForNSentiments(
			Integer noOfVerdictHeadlines) {

		logger.debug("Entered VerdictController getVerdictHeadlineForNSentiments method");

		List<Verdict> verdicts = new ArrayList<Verdict>();

		try {

			verdicts = businessDelegate
					.getVerdictHeadlineForNSentiments(noOfVerdictHeadlines);

		} catch (MessageException e) {

			logger.error("Exception occured while getting headlines", e);
			logger.debug("Exiting VerdictController getVerdictHeadlineForNSentiments method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (verdicts != null && verdicts.size() > 0) {

			logger.debug("Got headline from database. Exiting VerdictController getVerdictHeadline method");

			return new ResponseDto<Verdict>(true, verdicts);
		}

		logger.debug("Headline found null while getting from database. Exiting VerdictController getVerdictHeadline method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(POST) to edit Verdict object in database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Verdict> edit(@RequestBody Verdict verdict) {

		logger.debug("Entered VerdictController edit method");
		try {

			logger.debug("Trying to edit Verdict object with id = "
					+ verdict.getId() + " and sentimentId = "
					+ verdict.getSentiment().getId());

			verdict = businessDelegate.update(verdict);

		} catch (MessageException e) {

			logger.error("Exception occured while editing verdict object", e);
			logger.debug("Exiting VerdictController edit method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (verdict != null) {

			logger.debug("Edited Verdict object and have id = "
					+ verdict.getId() + " for sentimentId = "
					+ verdict.getSentiment().getId());

			return new ResponseDto<Verdict>(true, verdict);
		}

		logger.debug("Verdict found null after editing in database. Exiting VerdictController edit method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(POST) to delete Verdict object from database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Verdict> delete(
			@RequestBody Verdict verdict) {

		logger.debug("Entered VerdictController delete method");

		try {

			logger.debug("Trying to delete Verdict object with id = "
					+ verdict.getId() + " and sentimentId = "
					+ verdict.getSentiment().getId());

			verdict = businessDelegate.delete(verdict);

		} catch (MessageException e) {

			logger.error("Exception occured while deleting verdict object", e);
			logger.debug("Exiting VerdictController delete method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (verdict == null) {

			logger.debug("Verdict found null after deleting in database that means it is deleted. Exiting VerdictController delete method.");

			return new ResponseDto<Verdict>(true);
		}

		logger.debug("Verdict not null after deleting in database. Exiting VerdictController delete method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to delete Verdict object from database by id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.jpa.entity.verdict
	 *         .Verdict>
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Verdict> delete(@RequestParam Long id) {

		logger.debug("Entered VerdictController delete method");

		Verdict verdict = new Verdict();
		try {

			logger.debug("Trying to delete Verdict object with id = " + id);

			verdict = businessDelegate.delete(id);

		} catch (MessageException e) {

			logger.error("Exception occured while deleting verdict object", e);
			logger.debug("Exiting VerdictController delete method due to error.");
			return new ResponseDto<Verdict>(false);
		}

		if (verdict == null) {

			logger.debug("Verdict found null after deleting in database that means it is deleted. Exiting VerdictController delete method.");

			return new ResponseDto<Verdict>(true);
		}

		logger.debug("Verdict not null after deleting in database. Exiting VerdictController delete method.");

		return new ResponseDto<Verdict>(false);
	}

	/**
	 * Spring web service(GET) to get dummy Verdict object
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	@RequestMapping(value = "/getTestVerdict", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Verdict getTestVerdict() {

		return getDummyVerdict();

	}

	/**
	 * This method returns the dummy object of the Verdict object
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	private Verdict getDummyVerdict() {

		Verdict verdict = new Verdict();
		return verdict;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<List<String>> getVerdict() {
		List<String> result1 = new ArrayList<String>();
		result1.add("AIADMK MP Sacked from Party for Slapping DMK MP, Claims Threat to Life - News18");
		List<String> result2 = new ArrayList<String>();
		List<String> result3 = new ArrayList<String>();
		List<String> result4 = new ArrayList<String>();
		List<List<String>> result = new ArrayList<List<String>>();
		result2.add("India vs West Indies: KL Rahul’s story would probably do a movie scriptwriter proud");
		result3.add("Sasikala breaks down in Rajya Sabha after being sacked, alleges 'threat to life'");
		result4.add("Sasikala breaks down in Rajya Sabha after being sacked, alleges 'threat to life'");
		result.add(result1);
		result.add(result2);
		result.add(result3);
		result.add(result4);

		return new ResponseDto<List<String>>(true, result);
	}

}
