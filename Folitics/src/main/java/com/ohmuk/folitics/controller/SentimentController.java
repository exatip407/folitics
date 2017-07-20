package com.ohmuk.folitics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ohmuk.folitics.businessDelegate.interfaces.IPollBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.ISentimentBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.enums.SentimentState;
import com.ohmuk.folitics.enums.SentimentType;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Link;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;

/**
 * @author Abhishek
 * 
 */
@Controller
@RequestMapping("/sentiment")
public class SentimentController {

	@Autowired
	private volatile ISentimentBusinessDelegate sentimentBusinessDelegate;
	@Autowired
	private volatile IPollBusinessDelegate pollBusinessDelegate;

	private static Logger logger = Logger.getLogger(SentimentController.class);

	@RequestMapping
	public String getSentimentPage() {

		return "sentiment-page";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Sentiment> getAdd() {

		List<Sentiment> sentiments = new ArrayList<>();
		sentiments.add(getTestSentiment());
		return new ResponseDto<Sentiment>(true, sentiments);
	}

	/**
	 * This method is used to add sentiment. This is a multipart spring web
	 * service that requires image and json for sentiment.
	 * 
	 * @author gautam.yadav
	 * @param image
	 * @param sentiment
	 * @return ResponseDto<Sentiment>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Sentiment> add(
			@RequestPart(value = "file") MultipartFile image,
			@RequestPart(value = "sentiment") Sentiment sentiment) {

		logger.info("Inside SentimentController add method");
		if (image == null) {
			logger.error("Image is null", new Exception(
					"Image is required to add sentiment"));
			return new ResponseDto<Sentiment>(false);
		}
		if (sentiment == null) {
			logger.error("Sentiment is null",
					new Exception("Sentiment is null"));
			return new ResponseDto<Sentiment>(false);
		}
		try {
			logger.debug("Image " + image.getOriginalFilename() + "Received "
					+ "Image size " + image.getSize());
			logger.debug("Sentiment to be saved " + sentiment.getSubject());

			sentiment.setId(null);
			sentiment.setState(SentimentState.NEW.getValue());

			sentiment.setImage(image.getBytes());
			sentiment.setImageType(FileType.getFileType(
					image.getOriginalFilename().split("\\.")[1]).getValue());
			Set<Poll> polls = new HashSet<>();
			for (Poll poll : sentiment.getPolls()) {
				// poll.setSentiment(sentiment);

				Poll sessionPoll = pollBusinessDelegate.getPollById(poll
						.getId());
				sessionPoll.setSentiment(sentiment);
				polls.add(sessionPoll);

				// pollBusinessDelegate.saveAndFlush(sessionPoll);

				logger.debug("Poll :" + sessionPoll.getQuestion() + " saved");
			}
			sentiment.setPolls(polls);
			sentiment = sentimentBusinessDelegate.save(sentiment);
			logger.debug("Sentiment and polls saved/updated");

			return new ResponseDto<Sentiment>(true, sentiment);

		} catch (IOException ioException) {
			logger.error("Error saving sentiment/poll", ioException);
			logger.error("Exiting add sentiment");

			return new ResponseDto<Sentiment>(false);
		} catch (Exception exception) {
			logger.error("Error saving sentiment/poll", exception);
			logger.info("Exiting from SentimentController add method");
			return new ResponseDto<Sentiment>(false);
		}
	}

	/**
	 * Web service is to edit {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> edit(
			@RequestBody Sentiment sentiment) {
		logger.info("Inside SentimentController edit method");
		try {
			sentiment = sentimentBusinessDelegate.update(sentiment);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController edit method");

			return new ResponseDto<Sentiment>(false);
		}
		if (sentiment != null) {
			logger.debug("Sentiment with id: " + sentiment.getId()
					+ " is update");
			logger.info("Exiting from SentimentController edit method");
			return new ResponseDto<Sentiment>(true, sentiment);
		}
		logger.debug("Sentiment is not update");
		logger.info("Exiting from SentimentController edit method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service to clone {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return
	 */
	@RequestMapping(value = "/clone", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> clone(
			@RequestBody Sentiment sentiment) {
		logger.info("Inside SentimentController clone method");
		if (null != sentiment) {
			try {
				sentiment = sentimentBusinessDelegate.clone(sentiment);
			} catch (Exception exception) {
				logger.error("Exception in updating Sentiment");
				logger.error("Exception: " + exception);

				return new ResponseDto<Sentiment>(false);
			}
			logger.debug("Sentiment clone with id :" + sentiment.getId()
					+ " is saved");
			try {
				for (Poll poll : sentiment.getPolls()) {
					poll.setSentiment(sentiment);

					Poll sessionPoll = pollBusinessDelegate.getPollById(poll
							.getId());
					sessionPoll.setSentiment(sentiment);

					pollBusinessDelegate.saveAndFlush(sessionPoll);

					logger.debug("Poll :" + sessionPoll.getQuestion()
							+ " saved");
				}
			} catch (Exception exception) {
				logger.error("Error saving sentiment/poll", exception);
				logger.error("Exiting add sentiment");
				logger.info("Exiting from SentimentController clone method");

				return new ResponseDto<Sentiment>(false);
			}
			logger.info("Exiting from SentimentController clone method");
			return new ResponseDto<Sentiment>(true, sentiment);
		}
		logger.info("Exiting from SentimentController clone method");
		return new ResponseDto<Sentiment>(false);

	}

	/**
	 * Web service is to soft delete {@link Sentiment} by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> delete(Long id) {
		logger.info("Inside SentimentController deleteById method");
		try {
			if (sentimentBusinessDelegate.delete(id)) {
				logger.debug("Sentiment with id: " + id + " is soft delete");
				logger.info("Exiting from SentimentController deleteById method");
				return new ResponseDto<Sentiment>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController deleteById method");

			return new ResponseDto<Sentiment>(false);
		}
		logger.debug("Sentiment with id: " + id + " is not delete");
		logger.info("Exiting from SentimentController deleteById method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to soft delete {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> delete(
			@RequestBody Sentiment sentiment) {
		logger.info("Inside SentimentController delete method");
		try {
			if (sentimentBusinessDelegate.delete(sentiment)) {
				logger.debug("Sentiment with id: " + sentiment.getId()
						+ " is soft delete");
				logger.info("Exiting from SentimentController delete method");
				return new ResponseDto<Sentiment>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController delete method");

			return new ResponseDto<Sentiment>(false);
		}
		logger.debug("Sentiment is not delete");
		logger.info("Exiting from SentimentController delete method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to hard delete {@link Sentiment} by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteFromDbById", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> deleteFromDB(
			@RequestParam Long id) {
		logger.info("Inside SentimentController deleteFromDbById method");
		try {
			if (sentimentBusinessDelegate.deleteFromDB(id)) {
				logger.debug("Sentiment with id: " + id + " is deleted from DB");
				logger.info("Exiting from SentimentController deleteFromDbById method");
				return new ResponseDto<Sentiment>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController deleteFromDbById method");

			return new ResponseDto<Sentiment>(false);
		}
		logger.debug("Sentiment with id: " + id + " is not deleted");
		logger.info("Exiting from SentimentController deleteFromDbById method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to delete {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return
	 */
	@RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> deleteFromDB(
			@RequestBody Sentiment sentiment) {
		logger.info("Inside SentimentController deleteFromDb method");
		try {
			if (sentimentBusinessDelegate.deleteFromDB(sentiment)) {
				logger.debug("Sentiment of id: " + sentiment.getId()
						+ " is deleted form DB");
				return new ResponseDto<Sentiment>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController deleteFromDb method");

			return new ResponseDto<Sentiment>(false);
		}
		logger.debug("Sentiment is not deleted");
		logger.info("Exiting from SentimentController deleteFromDb method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to get all source ( {@link Link} ) by {@link Sentiment} id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getAllSources", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Link> getAllSources(Long id) {
		logger.info("Inside SentimentController getAllSources method");
		Sentiment sentiment = null;
		try {
			sentiment = sentimentBusinessDelegate.read(id);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);

			return new ResponseDto<Link>(false);
		}
		if (sentiment != null) {
			List<Link> linkSet = null;
			try {
				linkSet = sentimentBusinessDelegate
						.getAllSourcesForSentiment(sentiment);
			} catch (Exception exception) {
				logger.error("Exception in updating Sentiment");
				logger.error("Exception: " + exception);
				logger.info("Exiting from SentimentController getAllSources method");

				return new ResponseDto<Link>(false);
			}
			if (linkSet != null) {
				logger.debug(linkSet.size()
						+ " source is found for sentiment with id: " + id);
				logger.info("Exiting from SentimentController getAllSources method");
				return new ResponseDto<Link>(true, linkSet);
			}
		}
		logger.debug("No source is found for sentiment with id: " + id);
		logger.info("Exiting from SentimentController getAllSources method");
		return new ResponseDto<Link>(false);
	}

	/**
	 * Web service is to get {@link Sentiment} by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Sentiment> find(Long id) {
		logger.info("Inside SentimentController find method");
		Sentiment sentiment = null;
		try {
			sentiment = sentimentBusinessDelegate.read(id);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController find method");

			return new ResponseDto<Sentiment>(false);
		}
		if (sentiment != null) {
			logger.debug("Sentiment is found for id: " + id);
			logger.info("Exiting from SentimentController find method");
			return new ResponseDto<Sentiment>(true, sentiment);
		}
		logger.debug("Sentiment is not found for id: " + id);
		logger.info("Exiting from SentimentController find method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to get all {@link Sentiment}
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Sentiment> getall() {
		logger.info("Inside SentimentController getAll method");
		List<Sentiment> sentiments = null;
		try {
			sentiments = sentimentBusinessDelegate.readAll();
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController getAll method");

			return new ResponseDto<Sentiment>(false);
		}
		if (sentiments != null) {
			logger.debug(sentiments.size() + " sentiment is found");
			logger.info("Exiting from SentimentController getAll method");
			return new ResponseDto<Sentiment>(true, sentiments);
		}
		logger.debug("No sentiment is found");
		logger.info("Exiting from SentimentController getAll method");
		return new ResponseDto<Sentiment>(false);
	}

	@RequestMapping(value = "/findByType", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Sentiment> findByType(String type) {
		logger.info("Inside SentimentController findByType method");
		List<Sentiment> sentiments = null;
		try {
			sentiments = sentimentBusinessDelegate.findByType(type);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController findbyType method");

			return new ResponseDto<Sentiment>(false);
		}
		if (sentiments != null) {
			logger.debug(sentiments.size() + " sentiment is found");
			logger.info("Exiting from SentimentController find by type method");
			return new ResponseDto<Sentiment>(true, sentiments);
		}
		logger.debug("No sentiment is found");
		logger.info("Exiting from SentimentController findByType method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to get all {@link Sentiment} not having sentiment in ids
	 * 
	 * @author Mayank Sharma
	 * @return new ResponseDto<Sentiment>
	 */
	@RequestMapping(value = "/getAllSentimentNotIn", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Sentiment> getAllSentimentNotIn(
			@RequestBody Set<Long> ids) {
		logger.info("Inside SentimentController getAllSentimentNotIn method");
		List<Sentiment> sentiments = null;
		try {
			sentiments = sentimentBusinessDelegate.getAllSentimentNotIn(ids);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController getAllSentimentNotIn method");

			return new ResponseDto<Sentiment>(false);
		}
		if (sentiments != null) {
			logger.debug(sentiments.size() + " Sentiment found");
			logger.info("Exiting from SentimentController getAllSentimentNotIn method");
			return new ResponseDto<Sentiment>(true, sentiments);
		}
		logger.debug("No sentiment found");
		logger.info("Exiting from SentimentController getAllSentimentNotIn method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to update status of sentiment by id
	 * 
	 * @author Mayank Sharma
	 * @param allRequestParams
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Sentiment> deleteFromDB(
			@RequestParam Map<String, Object> allRequestParams) {
		logger.info("Inside SentimentController updateStatus method");
		Long id = Long.parseLong((String) allRequestParams.get("id"));
		String status = (String) allRequestParams.get("status");
		if (id != 0 && status != null) {
			try {
				sentimentBusinessDelegate.updateSentimentStatus(id, status);
			} catch (Exception exception) {
				logger.error("Exception in updating Sentiment");
				logger.error("Exception: " + exception);
				logger.info("Exiting from SentimentController updateStatus method");

				return new ResponseDto<Sentiment>(false);
			}
			logger.debug("sentiment with id: " + id + " status is changed to "
					+ status);
			logger.info("Exiting from SentimentController updateStatus method");
			return new ResponseDto<Sentiment>(true);
		}
		logger.debug("sentiment with id: " + id + " status is not changed to "
				+ status);
		logger.info("Exiting from SentimentController updateStatus method");
		return new ResponseDto<Sentiment>(false);

	}

	/**
	 * Web service is to get list of indicator attached with sentiment
	 * 
	 * @param id
	 * @return ResponseDto<Category>
	 * @author Mayank Sharma
	 */
	@RequestMapping(value = "/getAllSentimentIndicator", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Set<Category>> getAllSentimentIndicator(
			Long id) {
		logger.info("Inside SentimentController getAllSentimentIndicator method");
		Set<Category> indicadtors = null;
		try {
			indicadtors = sentimentBusinessDelegate.getAllIndicator(id);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController getAllSentimentIndicator method");

			return new ResponseDto<Set<Category>>(false);
		}
		if (null != indicadtors) {
			logger.debug(indicadtors.size() + " indicators is found");
			logger.info("Exiting from SentimentController getAllSentimentIndicator method");
			return new ResponseDto<Set<Category>>(true, indicadtors);
		} else {
			logger.debug("No indicator is found");
			logger.info("Exiting from SentimentController getAllSentimentIndicator method");
			return new ResponseDto<Set<Category>>(false);
		}
	}

	/**
	 * Web service is to get all {@link Sentiment}
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRelatedSentiment", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Sentiment> getRelatedSentiment(Long id) {
		logger.info("Inside SentimentController getAll method");
		Set<Sentiment> sentiments = null;
		try {
			sentiments = sentimentBusinessDelegate.getRelatedSentiment(id);
		} catch (Exception exception) {
			logger.error("Exception in updating Sentiment");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController getAll method");

			return new ResponseDto<Sentiment>(false);
		}
		if (sentiments != null) {
			logger.debug(sentiments.size() + " sentiment is found");
			logger.info("Exiting from SentimentController getAll method");
			return new ResponseDto<Sentiment>(true, sentiments);
		}
		logger.debug("No sentiment is found");
		logger.info("Exiting from SentimentController getAll method");
		return new ResponseDto<Sentiment>(false);
	}

	/**
	 * Web service is to get all {@link Sentiment}
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSentimentSupport", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<String> getSentimentSupport(
			Long sentimentId) {
		logger.info("Inside SentimentController getSentimentSupport method");
		try {
			return new ResponseDto<String>(false,
					sentimentBusinessDelegate.getSentimentSupport(sentimentId));
		} catch (Exception exception) {
			logger.error("Exception in getting Sentiment support");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController getSentimentSupport method");
			return new ResponseDto<String>(false);
		}
	}

	@RequestMapping(value = "/getTestSentiment", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Sentiment getTestSentiment() {

		return getDummySentiment();
	}

	@RequestMapping(value = "/saveNewsFeed", method = RequestMethod.POST)
	public @ResponseBody void saveNewsFeed() {

		sentimentBusinessDelegate.saveNewsFeed();

	}

	@RequestMapping(value = "/getNewsFeed", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<NewsFeed> getNewsFeed(Long sentimentId) {

		logger.info("Inside SentimentController getNewsFeed method");
		List<NewsFeed> newsFeeds = null;
		try {
			newsFeeds = sentimentBusinessDelegate
					.getNewsFeedBySentimentId(sentimentId);
		} catch (Exception exception) {
			logger.error("Exception in getting NewsFeed");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController getNewsFeed method");

			return new ResponseDto<NewsFeed>(false);
		}
		if (newsFeeds != null) {
			logger.debug(newsFeeds.size() + " NewsFeed is found");
			logger.info("Exiting from SentimentController getNewsFeed method");
			return new ResponseDto<NewsFeed>(true, newsFeeds);
		}
		logger.debug("No Newsfeed is found");
		logger.info("Exiting from SentimentController getNewsFeed method");
		return new ResponseDto<NewsFeed>("No NewsFeed is found", false);

	}

	@RequestMapping(value = "updateNewsFeed", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<NewsFeed> updateNewsFeedWithSentimentId(
			String newsFeedId, Long sentimentId) {

		logger.info("Inside SentimentController updateNewsFeedWithSentimentId method");
		NewsFeed newsFeed = null;
		try {
			newsFeed = sentimentBusinessDelegate.updateNewsFeedWithSentimentId(
					newsFeedId, sentimentId);
		} catch (Exception exception) {
			logger.error("Exception in updating NewsFeed");
			logger.error("Exception: " + exception);
			logger.info("Exiting from SentimentController updateNewsFeedWithSentimentId method");

			return new ResponseDto<NewsFeed>(false);
		}
		if (newsFeed != null) {

			logger.info("Exiting from SentimentController updateNewsFeedWithSentimentId method");
			return new ResponseDto<NewsFeed>(true, newsFeed);
		}

		logger.info("Exiting from SentimentController updateNewsFeedWithSentimentId method");
		return new ResponseDto<NewsFeed>(false);

	}

	private Sentiment getDummySentiment() {

		Sentiment sentiment = new Sentiment();
		sentiment.setSubject("Subject for test Sentiment");
		sentiment.setDescription("Description for test Sentiment");
		sentiment.setType(SentimentType.TEMPORARY.toString());
		return sentiment;

	}
}
