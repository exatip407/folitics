package com.ohmuk.folitics.service.air;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCount;
import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCountId;
import com.ohmuk.folitics.hibernate.repository.air.IResponseAirCountRepository;

/**
 * Service implementation for entity: {@link ResponseAirCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class ResponseAirCountService implements
		IAirCountService<ResponseAirCount> {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseAirCountService.class);

	@Autowired
	private IResponseAirCountRepository repository;

	@Override
	public ResponseAirCount create(ResponseAirCount responseAirCount) {

		logger.info("Entered ResponseAirCount service create method");

		logger.debug("Saving ResponseAirCount with component id = "
				+ responseAirCount.getId().getResponse().getId());

		return repository.save(responseAirCount);
	}

	@Override
	public List<ResponseAirCount> readAll() {

		logger.info("Entered ResponseAirCount service readAll method");
		return repository.findAll();

	}

	public ResponseAirCount read(ResponseAirCountId id) {
		logger.info("Entered ResponseAirCount service read method");
		return repository.find(id);
	}

	@Override
	public ResponseAirCount update(ResponseAirCount responseAirCount) {
		logger.info("Entered ResponseAirCount service update method");
		return repository.update(responseAirCount);
	}

	public ResponseAirCount delete(ResponseAirCountId id) {
		logger.info("Entered ResponseAirCount service delete method");
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public ResponseAirCount delete(ResponseAirCount responseAirCount) {

		logger.info("Entered ResponseAirCount service delete method");
		repository.delete(responseAirCount.getId());
		return repository.find(responseAirCount.getId());
	}

	@Override
	public Long getAirCountByComponentId(Long componentId) {

		logger.info("Entered ResponseAirCount service getAirCountByComponentId method");
		ResponseAirCount responseAirCount = repository
				.findByComponentId(componentId);
		if (responseAirCount != null)
			return responseAirCount.getAirCount();
		else
			return 0l;
	}

}
