package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.IChartCountLikeRepository;

/**
 * Service implementation for entity: {@link ChartLikeCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class ChartLikeCountService implements ILikeCountService<ChartLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(ChartLikeCountService.class);

	@Autowired
	private IChartCountLikeRepository repository;

	@Override
	public ChartLikeCount create(ChartLikeCount chartLikeCount)
			throws MessageException {
		logger.info("Entered ChartLikeCount service create method");

		if (chartLikeCount.getId().getChart() == null) {
			logger.error("Chart in ChartLikeCount is null");
			throw (new MessageException("Chart in ChartLikeCount can't be null"));
		}

		if (chartLikeCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Saving ChartLikeCount with component id = "
				+ chartLikeCount.getId().getChart().getId());
		return repository.save(chartLikeCount);
	}

	public ChartLikeCount read(ChartLikeCountId id) throws MessageException {

		logger.info("Entered ChartLikeCount service read method");

		if (id.getChart() == null) {
			logger.error("Chart in ChartLikeCount is null");
			throw (new MessageException("Chart in ChartLikeCount can't be null"));
		}

		if (id.getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Getting ChartLikeCount with component id = "
				+ id.getChart().getId());
		return repository.find(id);
	}

	@Override
	public List<ChartLikeCount> readAll() {
		logger.info("Entered ChartLikeCount service readAll method");
		logger.debug("Getting all ChartLikeCount");
		return repository.findAll();
	}

	@Override
	public ChartLikeCount update(ChartLikeCount chartLikeCount)
			throws MessageException {

		logger.info("Entered ChartLikeCount service update method");

		if (chartLikeCount.getId().getChart() == null) {
			logger.error("Chart in ChartLikeCount is null");
			throw (new MessageException("Chart in ChartLikeCount can't be null"));
		}

		if (chartLikeCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Updating ChartLikeCount with component id = "
				+ chartLikeCount.getId().getChart().getId());
		return repository.update(chartLikeCount);
	}

	public ChartLikeCount delete(ChartLikeCountId id) throws MessageException {

		logger.info("Entered ChartLikeCount service delete method");

		if (id.getChart() == null) {
			logger.error("Chart in ChartLikeCount is null");
			throw (new MessageException("Chart in ChartLikeCount can't be null"));
		}

		if (id.getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Deleting ChartLikeCount with component id = "
				+ id.getChart().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public ChartLikeCount delete(ChartLikeCount chartLikeCount)
			throws MessageException {
		logger.info("Entered ChartLikeCount service delete method");

		if (chartLikeCount.getId().getChart() == null) {
			logger.error("Chart in ChartLikeCount is null");
			throw (new MessageException("Chart in ChartLikeCount can't be null"));
		}

		if (chartLikeCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Deleting ChartLikeCount with component id = "
				+ chartLikeCount.getId().getChart().getId());
		repository.delete(chartLikeCount.getId());
		return repository.find(chartLikeCount.getId());
	}

	@Override
	public ChartLikeCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered ChartLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Getting ChartLikeCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
