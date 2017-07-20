package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.FactLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.FactLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.IFactLikeCountRepository;

/**
 * Service implementation for entity: {@link FactLikeCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class FactLikeCountService implements ILikeCountService<FactLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(ChartLikeCountService.class);

	@Autowired
	private IFactLikeCountRepository repository;

	@Override
	public FactLikeCount create(FactLikeCount factLikeCount)
			throws MessageException {
		logger.info("Entered FactLikeCount service create method");

		if (factLikeCount.getId().getFact() == null) {
			logger.error("fact in FactLikeCount is null");
			throw (new MessageException("fact in FactLikeCount can't be null"));
		}

		if (factLikeCount.getId().getFact().getId() == null) {
			logger.error("fact id is null");
			throw (new MessageException("fact id can't be null"));
		}

		logger.debug("Saving FactLikeCount with component id = "
				+ factLikeCount.getId().getFact().getId());
		return repository.save(factLikeCount);
	}

	public FactLikeCount read(FactLikeCountId id) throws MessageException {

		logger.info("Entered FactLikeCount service read method");

		if (id.getFact() == null) {
			logger.error("fact in FactLikeCount is null");
			throw (new MessageException("fact in FactLikeCount can't be null"));
		}

		if (id.getFact().getId() == null) {
			logger.error("fact id is null");
			throw (new MessageException("fact id can't be null"));
		}

		logger.debug("Getting FactLikeCount with component id = "
				+ id.getFact().getId());
		return repository.find(id);
	}

	@Override
	public List<FactLikeCount> readAll() {
		logger.info("Entered FactLikeCount service readAll method");
		logger.debug("Getting all FactLikeCount");
		return repository.findAll();
	}

	@Override
	public FactLikeCount update(FactLikeCount factLikeCount)
			throws MessageException {
		logger.info("Entered FactLikeCount service update method");

		if (factLikeCount.getId().getFact() == null) {
			logger.error("fact in FactLikeCount is null");
			throw (new MessageException("fact in FactLikeCount can't be null"));
		}

		if (factLikeCount.getId().getFact().getId() == null) {
			logger.error("fact id is null");
			throw (new MessageException("fact id can't be null"));
		}

		logger.debug("Updating FactLikeCount with component id = "
				+ factLikeCount.getId().getFact().getId());
		return repository.update(factLikeCount);
	}

	public FactLikeCount delete(FactLikeCountId id) throws MessageException {

		logger.info("Entered FactLikeCount service delete method");

		if (id.getFact() == null) {
			logger.error("fact in FactLikeCount is null");
			throw (new MessageException("fact in FactLikeCount can't be null"));
		}

		if (id.getFact().getId() == null) {
			logger.error("fact id is null");
			throw (new MessageException("fact id can't be null"));
		}

		logger.debug("Deleting FactLikeCount with component id = "
				+ id.getFact().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public FactLikeCount delete(FactLikeCount factLikeCount)
			throws MessageException {
		logger.info("Entered FactLikeCount service delete method");

		if (factLikeCount.getId().getFact() == null) {
			logger.error("fact in FactLikeCount is null");
			throw (new MessageException("fact in FactLikeCount can't be null"));
		}

		if (factLikeCount.getId().getFact().getId() == null) {
			logger.error("fact id is null");
			throw (new MessageException("fact id can't be null"));
		}

		logger.debug("Deleting FactLikeCount with component id = "
				+ factLikeCount.getId().getFact().getId());
		repository.delete(factLikeCount.getId());
		return repository.find(factLikeCount.getId());
	}

	@Override
	public FactLikeCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered FactLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("fact id is null");
			throw (new MessageException("fact id can't be null"));
		}

		logger.debug("Getting FactLikeCount with component id = " + componentId);
		return repository.findByComponentId(componentId);
	}
}
