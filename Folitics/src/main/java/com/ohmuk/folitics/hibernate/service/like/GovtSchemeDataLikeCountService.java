package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLikeCount;
import com.ohmuk.folitics.hibernate.repository.like.IGovtSchemeDataCountRepository;

/**
 * Service implementation for entity: {@link GovtSchemeDataLikeCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class GovtSchemeDataLikeCountService implements
		ILikeCountService<GovtSchemeDataLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataLikeCountService.class);

	@Autowired
	private IGovtSchemeDataCountRepository repository;

	@Override
	public GovtSchemeDataLikeCount create(
			GovtSchemeDataLikeCount govtSchemeDataLikeCount)
			throws MessageException {
		logger.info("Entered GovtSchemeDataLikeCount service create method");

		if (govtSchemeDataLikeCount.getId().getGovtSchemeData() == null) {
			logger.error("GovtSchemeData in GovtSchemeDataLikeCount is null");
			throw (new MessageException(
					"GovtSchemeData in GovtSchemeDataLikeCount can't be null"));
		}

		if (govtSchemeDataLikeCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("GovtSchemeData id is null");
			throw (new MessageException("GovtSchemeData id can't be null"));
		}

		logger.debug("Saving GovtSchemeDataLikeCount with component id = "
				+ govtSchemeDataLikeCount.getId().getGovtSchemeData().getId());
		return repository.save(govtSchemeDataLikeCount);
	}

	public GovtSchemeDataLikeCount read(GovtSchemeDataCountId id)
			throws MessageException {

		logger.info("Entered GovtSchemeDataLikeCount service read method");

		if (id.getGovtSchemeData() == null) {
			logger.error("GovtSchemeData in GovtSchemeDataLikeCount is null");
			throw (new MessageException(
					"GovtSchemeData in GovtSchemeDataLikeCount can't be null"));
		}

		if (id.getGovtSchemeData().getId() == null) {
			logger.error("GovtSchemeData id is null");
			throw (new MessageException("GovtSchemeData id can't be null"));
		}

		logger.debug("Getting GovtSchemeDataLikeCount with component id = "
				+ id.getGovtSchemeData().getId());
		return repository.find(id);
	}

	@Override
	public List<GovtSchemeDataLikeCount> readAll() {
		logger.info("Entered GovtSchemeDataLikeCount service readAll method");
		logger.debug("Getting all GovtSchemeDataLikeCount");
		return repository.findAll();
	}

	@Override
	public GovtSchemeDataLikeCount update(
			GovtSchemeDataLikeCount govtSchemeDataLikeCount)
			throws MessageException {
		logger.info("Entered GovtSchemeDataLikeCount service update method");

		if (govtSchemeDataLikeCount.getId().getGovtSchemeData() == null) {
			logger.error("GovtSchemeData in GovtSchemeDataLikeCount is null");
			throw (new MessageException(
					"GovtSchemeData in GovtSchemeDataLikeCount can't be null"));
		}

		if (govtSchemeDataLikeCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("GovtSchemeData id is null");
			throw (new MessageException("GovtSchemeData id can't be null"));
		}

		logger.debug("Updating GovtSchemeDataLikeCount with component id = "
				+ govtSchemeDataLikeCount.getId().getGovtSchemeData().getId());
		return repository.update(govtSchemeDataLikeCount);
	}

	public GovtSchemeDataLikeCount delete(GovtSchemeDataCountId id)
			throws MessageException {

		logger.info("Entered GovtSchemeDataLikeCount service delete method");

		if (id.getGovtSchemeData() == null) {
			logger.error("GovtSchemeData in GovtSchemeDataLikeCount is null");
			throw (new MessageException(
					"GovtSchemeData in GovtSchemeDataLikeCount can't be null"));
		}

		if (id.getGovtSchemeData().getId() == null) {
			logger.error("GovtSchemeData id is null");
			throw (new MessageException("GovtSchemeData id can't be null"));
		}

		logger.debug("Deleting GovtSchemeDataLikeCount with component id = "
				+ id.getGovtSchemeData().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public GovtSchemeDataLikeCount delete(
			GovtSchemeDataLikeCount govtSchemeDataLikeCount)
			throws MessageException {
		logger.info("Entered GovtSchemeDataLikeCount service delete method");

		if (govtSchemeDataLikeCount.getId().getGovtSchemeData() == null) {
			logger.error("GovtSchemeData in GovtSchemeDataLikeCount is null");
			throw (new MessageException(
					"GovtSchemeData in GovtSchemeDataLikeCount can't be null"));
		}

		if (govtSchemeDataLikeCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("GovtSchemeData id is null");
			throw (new MessageException("GovtSchemeData id can't be null"));
		}

		logger.debug("Deleting GovtSchemeDataLikeCount with component id = "
				+ govtSchemeDataLikeCount.getId().getGovtSchemeData().getId());
		repository.delete(govtSchemeDataLikeCount.getId());
		return repository.find(govtSchemeDataLikeCount.getId());
	}

	@Override
	public GovtSchemeDataLikeCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered GovtSchemeDataLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("GovtSchemeData id is null");
			throw (new MessageException("GovtSchemeData id can't be null"));
		}

		logger.debug("Getting GovtSchemeDataLikeCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
