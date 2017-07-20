package com.ohmuk.folitics.hibernate.service.share;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.share.ChartShareCount;
import com.ohmuk.folitics.hibernate.repository.share.IChartShareCountRepository;

/**
 * Service implementation for entity: {@link ChartShareCount}
 * 
 * @author Harish
 *
 */

@Service
@Transactional
public class ChartShareCountService implements
		IShareCountService<ChartShareCount> {

	private static Logger logger = LoggerFactory
			.getLogger(ChartShareCountService.class);

	@Autowired
	private IChartShareCountRepository repository;

	@Override
	public ChartShareCount create(ChartShareCount chartShareCount)
			throws MessageException {
		logger.info("Entered ChartShareCount service create method");

		if (chartShareCount.getId().getChart() == null) {
			logger.error("Chart in ChartShareCount is null");
			throw (new MessageException(
					"Chart in ChartShareCount can't be null"));
		}

		if (chartShareCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Saving ChartShareCount with component id = "
				+ chartShareCount.getId().getChart().getId());

		return repository.save(chartShareCount);
	}

	@Override
	public List<ChartShareCount> readAll() {
		logger.info("Entered ChartShareCount service readAll method");
		logger.debug("Getting all ChartShareCount");
		return repository.findAll();
	}

	@Override
	public ChartShareCount update(ChartShareCount chartShareCount)
			throws MessageException {
		logger.info("Entered ChartShareCount service update method");

		if (chartShareCount.getId().getChart() == null) {
			logger.error("Chart in ChartShareCount is null");
			throw (new MessageException(
					"Chart in ChartShareCount can't be null"));
		}

		if (chartShareCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Updating ChartShareCount with component id = "
				+ chartShareCount.getId().getChart().getId());
		return repository.update(chartShareCount);
	}

	@Override
	public ChartShareCount delete(ChartShareCount chartShareCount)
			throws MessageException {
		logger.info("Entered ChartShareCount service delete method");

		if (chartShareCount.getId().getChart() == null) {
			logger.error("Chart in ChartShareCount is null");
			throw (new MessageException(
					"Chart in ChartShareCount can't be null"));
		}

		if (chartShareCount.getId().getChart().getId() == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Deleting ChartShareCount with component id = "
				+ chartShareCount.getId().getChart().getId());
		return repository.update(chartShareCount);
	}

	@Override
	public ChartShareCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered ChartShareCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Chart id is null");
			throw (new MessageException("Chart id can't be null"));
		}

		logger.debug("Getting ChartShareCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
