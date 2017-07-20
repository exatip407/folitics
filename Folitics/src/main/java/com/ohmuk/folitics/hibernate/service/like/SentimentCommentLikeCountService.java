package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ISentimentCommentLikeCountRepository;

@Service
@Transactional
public class SentimentCommentLikeCountService implements
		ILikeCountService<SentimentCommentLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentLikeCountService.class);

	@Autowired
	private ISentimentCommentLikeCountRepository repository;

	@Override
	public SentimentCommentLikeCount create(
			SentimentCommentLikeCount sentimentCommentLikeCount)
			throws MessageException {
		logger.info("Entered SentimentCommentLikeCount service create method");

		if (sentimentCommentLikeCount.getId().getSentimentComment() == null) {
			logger.error("sentimentComment in SentimentCommentLikeCount found to be null");
			throw (new MessageException(
					"sentimentComment in SentimentCommentLikeCount found to be null"));
		}

		if (sentimentCommentLikeCount.getId().getSentimentComment().getId() == null) {
			logger.error("sentimentComment id is null");
			throw (new MessageException("sentimentComment id can't be null"));
		}

		logger.debug("Saving SentimentCommentLikeCount with component id = "
				+ sentimentCommentLikeCount.getId().getSentimentComment()
						.getId());
		return repository.save(sentimentCommentLikeCount);
	}

	public SentimentCommentLikeCount read(SentimentCommentLikeCountId id)
			throws MessageException {

		logger.info("Entered SentimentCommentLikeCount service read method");

		if (id.getSentimentComment() == null) {
			logger.error("SentimentComment in SentimentCommentLikeCount is null");
			throw (new MessageException(
					"SentimentComment in SentimentCommentLikeCount can't be null"));
		}

		if (id.getSentimentComment().getId() == null) {
			logger.error("SentimentComment id is null");
			throw (new MessageException("SentimentComment id can't be null"));
		}

		logger.debug("Getting SentimentCommentLikeCount with component id = "
				+ id.getSentimentComment().getId());
		return repository.find(id);
	}

	@Override
	public List<SentimentCommentLikeCount> readAll() {
		logger.info("Entered SentimentCommentLikeCount service readAll method");
		logger.debug("Getting all SentimentCommentLikeCount");
		return repository.findAll();
	}

	@Override
	public SentimentCommentLikeCount update(
			SentimentCommentLikeCount sentimentCommentLikeCount)
			throws MessageException {
		logger.info("Entered SentimentCommentLikeCount service update method");

		if (sentimentCommentLikeCount.getId().getSentimentComment() == null) {
			logger.error("sentimentComment in SentimentCommentLikeCount is null");
			throw (new MessageException(
					"sentimentComment in SentimentCommentLikeCount found to be null"));
		}

		if (sentimentCommentLikeCount.getId().getSentimentComment().getId() == null) {
			logger.error("sentimentComment id is null");
			throw (new MessageException("sentimentComment id found to be null"));
		}

		logger.debug("Updating SentimentCommentLikeCount with component id = "
				+ sentimentCommentLikeCount.getId().getSentimentComment()
						.getId());
		return repository.update(sentimentCommentLikeCount);
	}

	@Override
	public SentimentCommentLikeCount delete(
			SentimentCommentLikeCount sentimentCommentLikeCount)
			throws MessageException {
		logger.info("Entered SentimentCommentLikeCount service delete method");

		if (sentimentCommentLikeCount.getId().getSentimentComment() == null) {
			logger.error("sentimentComment in SentimentCommentLikeCount is null");
			throw (new MessageException(
					"sentimentComment in SentimentCommentLikeCount found to be null"));
		}

		if (sentimentCommentLikeCount.getId().getSentimentComment().getId() == null) {
			logger.error("sentimentComment id is null");
			throw (new MessageException("sentimentComment id found to be null"));
		}

		logger.debug("Deleting sentimentComment with component id = "
				+ sentimentCommentLikeCount.getId().getSentimentComment()
						.getId());
		repository.delete(sentimentCommentLikeCount.getId());
		return repository.find(sentimentCommentLikeCount.getId());
	}

	public SentimentCommentLikeCount delete(SentimentCommentLikeCountId id)
			throws MessageException {

		logger.info("Entered SentimentCommentLikeCount service delete method");

		if (id.getSentimentComment() == null) {
			logger.error("SentimentComment in SentimentCommentLikeCount is null");
			throw (new MessageException(
					"SentimentComment in SentimentCommentLikeCount can't be null"));
		}

		if (id.getSentimentComment().getId() == null) {
			logger.error("SentimentComment id is null");
			throw (new MessageException("SentimentComment id can't be null"));
		}

		logger.debug("Deleting SentimentCommentLikeCount with component id = "
				+ id.getSentimentComment().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public SentimentCommentLikeCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered SentimentCommentLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("sentimentComment id is null");
			throw (new MessageException("sentimentComment id found be null"));
		}

		logger.debug("Getting SentimentCommentLikeCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
