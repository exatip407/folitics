package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.ISentimentCommentCountRepository;

/**
 * Service implementation for entity: {@link SentimentCommentCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class SentimentCommentCountService implements
		ICommentCountService<SentimentCommentCount> {

	@Autowired
	private ISentimentCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentCountService.class);

	@Override
	public SentimentCommentCount create(
			SentimentCommentCount sentimentCommentCount)
			throws MessageException {
		logger.info("Entered SentimentCommentCount service create method");

		if (sentimentCommentCount.getId().getSentiment() == null) {
			logger.error("sentiment in SentimentCommentCount is null");
			throw (new MessageException(
					"sentiment in SentimentCommentCount can't be null"));
		}

		if (sentimentCommentCount.getId().getSentiment().getId() == null) {
			logger.error("sentiment id is null");
			throw (new MessageException("sentiment id can't be null"));
		}

		logger.debug("Saving SentimentCommentCount with component id = "
				+ sentimentCommentCount.getId().getSentiment().getId());

		return repository.save(sentimentCommentCount);
	}

	@Override
	public List<SentimentCommentCount> readAll() {
		logger.info("Entered SentimentCommentCount service readAll method");
		logger.debug("Getting all SentimentCommentCount");
		return repository.findAll();
	}

	@Override
	public SentimentCommentCount update(
			SentimentCommentCount sentimentCommentCount)
			throws MessageException {
		logger.info("Entered SentimentCommentCount service update method");

		if (sentimentCommentCount.getId().getSentiment() == null) {
			logger.error("sentiment in SentimentCommentCount is null");
			throw (new MessageException(
					"sentiment in SentimentCommentCount can't be null"));
		}

		if (sentimentCommentCount.getId().getSentiment().getId() == null) {
			logger.error("sentiment id is null");
			throw (new MessageException("sentiment id can't be null"));
		}

		logger.debug("Updating SentimentCommentCount with component id = "
				+ sentimentCommentCount.getId().getSentiment().getId());
		return repository.update(sentimentCommentCount);
	}

	@Override
	public void delete(SentimentCommentCount sentimentCommentCount)
			throws MessageException {
		logger.info("Entered SentimentCommentCount service delete method");

		if (sentimentCommentCount.getId().getSentiment() == null) {
			logger.error("Sentiment in SentimentCommentCount is null");
			throw (new MessageException(
					"Sentiment in SentimentCommentCount can't be null"));
		}

		if (sentimentCommentCount.getId().getSentiment().getId() == null) {
			logger.error("Sentiment id is null");
			throw (new MessageException("Sentiment id can't be null"));
		}

		logger.debug("Deleting SentimentCommentCount with component id = "
				+ sentimentCommentCount.getId().getSentiment().getId());
		repository.delete(sentimentCommentCount.getId());
	}

	@Override
	public SentimentCommentCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered SentimentCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Sentiment id is null");
			throw (new MessageException("Sentiment id can't be null"));
		}

		logger.debug("Getting SentimentCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
