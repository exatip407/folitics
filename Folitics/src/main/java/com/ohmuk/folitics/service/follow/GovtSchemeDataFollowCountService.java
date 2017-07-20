package com.ohmuk.folitics.service.follow;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.follow.GovtSchemeDataFollowCount;
import com.ohmuk.folitics.hibernate.repository.follow.IGovtSchemeDataFollowCountRepository;

@Service
@Transactional
public class GovtSchemeDataFollowCountService implements
		IFollowCountService<GovtSchemeDataFollowCount> {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataFollowCountService.class);

	@Autowired
	private volatile IGovtSchemeDataFollowCountRepository repository;

	@Override
	public GovtSchemeDataFollowCount create(
			GovtSchemeDataFollowCount govtSchemeDataFollowCount)
			throws MessageException {

		logger.info("Entered GovtSchemeDataFollowCount service create method");

		if (govtSchemeDataFollowCount.getId().getGovtSchemeData() == null) {
			logger.error("govtSchemeData in GovtSchemeDataFollowCount found to be null");
			throw (new MessageException(
					"govtSchemeData in GovtSchemeDataFollowCount found to be null"));
		}

		if (govtSchemeDataFollowCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("govtSchemeData id is null");
			throw (new MessageException("govtSchemeData id can't be null"));
		}

		logger.debug("Saving GovtSchemeDataFollowCount with component id = "
				+ govtSchemeDataFollowCount.getId().getGovtSchemeData().getId());
		return repository.save(govtSchemeDataFollowCount);
	}

	@Override
	public List<GovtSchemeDataFollowCount> readAll() {
		logger.info("Entered GovtSchemeDataFollowCount service readAll method");
		logger.debug("Getting all GovtSchemeDataFollowCount");
		return repository.findAll();
	}

	@Override
	public GovtSchemeDataFollowCount update(
			GovtSchemeDataFollowCount govtSchemeDataFollowCount)
			throws MessageException {
		logger.info("Entered GovtSchemeDataFollowCount service update method");

		if (govtSchemeDataFollowCount.getId().getGovtSchemeData() == null) {
			logger.error("govtSchemeData in GovtSchemeDataFollowCount is null");
			throw (new MessageException(
					"govtSchemeData in GovtSchemeDataFollowCount found to be null"));
		}

		if (govtSchemeDataFollowCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("govtSchemeData id is null");
			throw (new MessageException("govtSchemeData id found to be null"));
		}

		logger.debug("Updating GovtSchemeDataFollowCount with component id = "
				+ govtSchemeDataFollowCount.getId().getGovtSchemeData().getId());
		return repository.update(govtSchemeDataFollowCount);
	}

	@Override
	public void delete(GovtSchemeDataFollowCount govtSchemeDataFollowCount)
			throws MessageException {
		logger.info("Entered GovtSchemeDataFollowCount service delete method");

		if (govtSchemeDataFollowCount.getId().getGovtSchemeData() == null) {
			logger.error("govtSchemeData in GovtSchemeDataFollowCount is null");
			throw (new MessageException(
					"govtSchemeData in GovtSchemeDataFollowCount found to be null"));
		}

		if (govtSchemeDataFollowCount.getId().getGovtSchemeData().getId() == null) {
			logger.error("govtSchemeData id is null");
			throw (new MessageException("govtSchemeData id found to be null"));
		}

		logger.debug("Deleting govtSchemeData with component id = "
				+ govtSchemeDataFollowCount.getId().getGovtSchemeData().getId());
		repository.delete(govtSchemeDataFollowCount.getId());

	}

	@Override
	public Long getByComponentId(Long componentId) throws MessageException {
		logger.info("Entered GovtSchemeDataFollowCount service getByComponentId method");

		if (componentId == null) {
			logger.error("govtSchemeData id is null");
			throw (new MessageException("govtSchemeData id found be null"));
		}

		logger.debug("Getting GovtSchemeDataFollowCount with component id = "
				+ componentId);

		GovtSchemeDataFollowCount govtSchemeDataFollowCount = repository
				.findByComponentId(componentId);
		Long followCount = govtSchemeDataFollowCount.getFollowCount();
		return followCount;
	}

}
