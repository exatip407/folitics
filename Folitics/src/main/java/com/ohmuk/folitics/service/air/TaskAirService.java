package com.ohmuk.folitics.service.air;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.air.TaskAir;
import com.ohmuk.folitics.hibernate.entity.air.TaskAirCount;
import com.ohmuk.folitics.hibernate.entity.air.TaskCountId;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.repository.air.ITaskAirCountRepository;
import com.ohmuk.folitics.hibernate.repository.air.ITaskAirRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class TaskAirService implements IAirService {

	private static Logger logger = LoggerFactory
			.getLogger(TaskAirService.class);

	@Autowired
	ITaskAirRepository repository;

	@Autowired
	ITaskAirCountRepository taskAirCountRepository;

	@Autowired
	IUserService userService;

	@Override
	public Object create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Inside create Air Task Service");

		TaskAir taskAir = new TaskAir();
		taskAir.setUserId(airShareDataBean.getUserId());
		taskAir.setComponentId(airShareDataBean.getComponentId());
		taskAir.setComponentType(airShareDataBean.getComponentType());
		taskAir.setDescription(airShareDataBean.getDescription());

		repository.save(taskAir);

		// add monetization points to user for Air on any component
		repository.addMonetizationPoints(airShareDataBean, "Air");

		// Adding counter for the component
		TaskAirCount taskAirCount = new TaskAirCount();
		Task task = new Task();
		task.setId(airShareDataBean.getComponentId());

		TaskCountId taskCountId = new TaskCountId();
		taskCountId.setTask(task);

		taskAirCount.setId(taskCountId);

		TaskAirCount taskAirCount2 = taskAirCountRepository.find(taskCountId);
		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component
		if (taskAirCount2 != null) {
			taskAirCount2.setAirCount(taskAirCount2.getAirCount() + 1);
			taskAirCountRepository.save(taskAirCount2);
		} else {
			taskAirCount.setAirCount(1l);
			taskAirCountRepository.save(taskAirCount);
		}

		logger.debug("Exiting create air");
		return taskAir;
	}

	@Override
	public Object update(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside Update Air");

		TaskAir originalData = repository.find(airShareDataBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		originalData.setDescription(airShareDataBean.getDescription());
		TaskAir air = repository.save(originalData);

		if (air == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated air  : " + air);
		logger.debug("Exiting Update");

		return air;
	}

	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside findUserListForComponent");

		List<TaskAir> taskAirs = repository
				.findAirsByComponentId(airShareDataBean.getId());

		logger.debug("Airs fatched: " + taskAirs.size());
		List<Long> userIds = new ArrayList<Long>();
		for (TaskAir air : taskAirs) {
			userIds.add(air.getUserId());

		}
		List<User> users = userService.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting findUserListForComponent");

		return users;
	}

	@Override
	public boolean isComponentAiredByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Exiting isComponentAiredByUser share");
		List<TaskAir> taskAirs = null;
		taskAirs = repository
				.getByComponentIdAndUserId(airShareDataBean.getComponentId(),
						airShareDataBean.getUserId());

		if (taskAirs == null || taskAirs.size() < 1) {
			logger.debug("Component Not air by user");
			return false;
		}

		logger.debug("Air fetched: " + taskAirs.size());
		logger.debug("Exiting isComponentAiredByUser share");

		return true;
	}

	@Override
	public Object deleteFromDB(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside hard delete air by ID");

		if (airShareDataBean.getId() == null) {
			logger.error("Air ID found to be null");
			throw (new MessageException("Air ID can't be null"));
		}

		TaskAir air = repository.find(airShareDataBean.getId());

		if (air == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ airShareDataBean.getId() + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ airShareDataBean.getId()
							+ ", Can't delete record."));
		}

		air.setStatus(ComponentState.DELETED.getValue());
		repository.delete(air.getId());

		logger.debug("Deleted air :" + air);
		logger.debug("Exiting soft delete air by ID");

		return air;
	}

	@Override
	public Object delete(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside soft delete air by ID");

		if (airShareDataBean.getId() == null) {
			logger.error("Air ID found to be null");
			throw (new MessageException("Air ID can't be null"));
		}

		TaskAir air = repository.find(airShareDataBean.getId());

		if (air == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ airShareDataBean.getId() + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ airShareDataBean.getId()
							+ ", Can't delete record."));
		}

		air.setStatus(ComponentState.DELETED.getValue());
		repository.update(air);

		logger.debug("Deleted air:" + air);
		logger.debug("Exiting soft delete air by ID");

		return air;
	}

}
