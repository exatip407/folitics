package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.IChartCommentCountRepository;

/**
 * Service implementation for entity: {@link ChartCommentCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class ChartCommentCountService implements
		ICommentCountService<ChartCommentCount> {

	@Autowired
	private IChartCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(ChartCommentCountService.class);

	@Override
	public ChartCommentCount create(ChartCommentCount chartCommentCount)
			throws MessageException {

		logger.info("Entered ChartCommentCount service create method");

		if (chartCommentCount.getId().getChart() == null) {
			logger.error("chart in ChartCommentCount is null");
			throw (new MessageException(
					"chart in ChartCommentCount can't be null"));
		}

		if (chartCommentCount.getId().getChart().getId() == null) {
			logger.error("chart id is null");
			throw (new MessageException("chart id can't be null"));
		}

		logger.debug("Saving ChartCommentCount with component id = "
				+ chartCommentCount.getId().getChart().getId());

		return repository.save(chartCommentCount);
	}

	@Override
	public List<ChartCommentCount> readAll() {
		logger.info("Entered ChartCommentCount service readAll method");
		logger.debug("Getting all ChartCommentCount");
		return repository.findAll();
	}

	@Override
	public ChartCommentCount update(ChartCommentCount chartCommentCount)
			throws MessageException {

		logger.info("Entered ChartCommentCount service update method");

		if (chartCommentCount.getId().getChart() == null) {
			logger.error("chart in ChartCommentCount is null");
			throw (new MessageException(
					"chart in ChartCommentCount can't be null"));
		}

		if (chartCommentCount.getId().getChart().getId() == null) {
			logger.error("chart id is null");
			throw (new MessageException("chart id can't be null"));
		}

		logger.debug("Updating ChartCommentCount with component id = "
				+ chartCommentCount.getId().getChart().getId());
		return repository.update(chartCommentCount);
	}

	@Override
	public void delete(ChartCommentCount chartCommentCount)
			throws MessageException {
		logger.info("Entered ChartCommentCount service delete method");

		if (chartCommentCount.getId().getChart() == null) {
			logger.error("Chart in ChartCommentCount is null");
			throw (new MessageException(
					"Chart in ChartCommentCount can't be null"));
		}

		if (chartCommentCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Deleting ChartCommentCount with component id = "
				+ chartCommentCount.getId().getChart().getId());

	}

	@Override
	public ChartCommentCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered ChartCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Getting ChartCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
