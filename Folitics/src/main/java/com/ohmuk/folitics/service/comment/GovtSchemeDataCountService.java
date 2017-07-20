package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataComment;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.IGovtSchemeDataCommentCountRepository;

/**
 * Service implementation for entity: {@link GovtSchemeDataComment}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class GovtSchemeDataCountService implements
		ICommentCountService<GovtSchemeDataCommentCount> {

	@Autowired
	private IGovtSchemeDataCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataCountService.class);

	@Override
	public GovtSchemeDataCommentCount create(
			GovtSchemeDataCommentCount govtSchemeDataCommentCount)
			throws MessageException {

		logger.info("Entered GovtSchemeDataCommentCount service create method");

		if (govtSchemeDataCommentCount.getId().getGovtSchemeData() == null) {
			logger.error("Scheme in GovtSchemeDataCommentCount is null");
			throw (new MessageException(
					"Scheme in GovtSchemeDataCommentCount can'govtSchemeDataCommentCount be null"));
		}

		if (govtSchemeDataCommentCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("Scheme id is null");
			throw (new MessageException("Scheme id is null"));
		}

		logger.debug("Saving GovtSchemeDataCommentCount with component id = "
				+ govtSchemeDataCommentCount.getId().getGovtSchemeData()
						.getId());

		return repository.save(govtSchemeDataCommentCount);
	}

	@Override
	public List<GovtSchemeDataCommentCount> readAll() {

		logger.info("Entered GovtSchemeDataCommentCount service readAll method");
		logger.debug("Getting all GovtSchemeDataCommentCount");
		return repository.findAll();
	}

	@Override
	public GovtSchemeDataCommentCount update(
			GovtSchemeDataCommentCount govtSchemeDataCommentCount)
			throws MessageException {

		logger.info("Entered GovtSchemeDataCommentCount service update method");

		if (govtSchemeDataCommentCount.getId().getGovtSchemeData() == null) {
			logger.error("Scheme in GovtSchemeDataCommentCount is null");
			throw (new MessageException(
					"Scheme in GovtSchemeDataCommentCount can'govtSchemeDataCommentCount be null"));
		}

		if (govtSchemeDataCommentCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("Scheme id is null");
			throw (new MessageException(
					"Scheme id can'govtSchemeDataCommentCount be null"));
		}

		logger.debug("Updating GovtSchemeDataCommentCount with component id = "
				+ govtSchemeDataCommentCount.getId().getGovtSchemeData()
						.getId());
		return repository.update(govtSchemeDataCommentCount);
	}

	@Override
	public void delete(GovtSchemeDataCommentCount govtSchemeDataCommentCount)
			throws MessageException {

		logger.info("Entered GovtSchemeDataCommentCount service delete method");

		if (govtSchemeDataCommentCount.getId().getGovtSchemeData() == null) {
			logger.error("Task in SentimentLikeCount is null");
			throw (new MessageException(
					"Sentiment in GovtSchemeDataCommentCount can'govtSchemeDataCommentCount be null"));
		}

		if (govtSchemeDataCommentCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("Scheme id is null");
			throw (new MessageException(
					"Scheme id can'govtSchemeDataCommentCount be null"));
		}

		logger.debug("Deleting GovtSchemeDataCommentCount with component id = "
				+ govtSchemeDataCommentCount.getId().getGovtSchemeData()
						.getId());
		repository.delete(govtSchemeDataCommentCount.getId());
	}

	@Override
	public GovtSchemeDataCommentCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered GovtSchemeDataCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Scheme id is null");
			throw (new MessageException("Scheme id is nullO"));
		}

		logger.debug("Getting GovtSchemeDataCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
