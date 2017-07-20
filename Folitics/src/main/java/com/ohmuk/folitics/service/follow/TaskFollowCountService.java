package com.ohmuk.folitics.service.follow;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCount;
import com.ohmuk.folitics.hibernate.repository.follow.ITaskFollowCountRepository;

@Service
@Transactional
public class TaskFollowCountService implements
		IFollowCountService<TaskFollowCount> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskFollowCountService.class);
	@Autowired
	private ITaskFollowCountRepository repository;

	@Override
	public TaskFollowCount create(TaskFollowCount taskFollowCount)
			throws MessageException {

		logger.info("Entered TaskFollowCount service create method");

		if (taskFollowCount.getId().getTask() == null) {
			logger.error("Task in TaskFollowCount found to be null");
			throw (new MessageException(
					"Task in TaskFollowCount found to be null"));
		}

		if (taskFollowCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Saving TaskFollowCount with component id = "
				+ taskFollowCount.getId().getTask().getId());
		return repository.save(taskFollowCount);
	}

	@Override
	public List<TaskFollowCount> readAll() {
		logger.info("Entered TaskFollowCount service readAll method");
		logger.debug("Getting all TaskFollowCount");
		return repository.findAll();
	}

	@Override
	public TaskFollowCount update(TaskFollowCount taskFollowCount)
			throws MessageException {

		logger.info("Entered TaskFollowCount service update method");

		if (taskFollowCount.getId().getTask() == null) {
			logger.error("Task in TaskFollowCount is null");
			throw (new MessageException(
					"Task in TaskFollowCount found to be null"));
		}

		if (taskFollowCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id found to be null"));
		}

		logger.debug("Updating TaskFollowCount with component id = "
				+ taskFollowCount.getId().getTask().getId());
		return repository.update(taskFollowCount);
	}

	@Override
	public void delete(TaskFollowCount taskFollowCount) throws MessageException {
		logger.info("Entered TaskFollowCount service delete method");

		if (taskFollowCount.getId().getTask() == null) {
			logger.error("Task in TaskFollowCount is null");
			throw (new MessageException(
					"Task in TaskFollowCount found to be null"));
		}

		if (taskFollowCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id found to be null"));
		}

		logger.debug("Deleting Task with component id = "
				+ taskFollowCount.getId().getTask().getId());
		repository.delete(taskFollowCount.getId());

	}

	@Override
	public Long getByComponentId(Long componentId) throws MessageException {
		logger.info("Entered TaskFollowCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id found be null"));
		}

		logger.debug("Getting TaskFollowCount with component id = "
				+ componentId);

		TaskFollowCount taskFollowCount = repository
				.findByComponentId(componentId);
		Long followCount = taskFollowCount.getFollowCount();
		return followCount;
	}

}
