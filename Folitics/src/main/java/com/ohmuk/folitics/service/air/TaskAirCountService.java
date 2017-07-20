package com.ohmuk.folitics.service.air;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.hibernate.entity.air.TaskAirCount;
import com.ohmuk.folitics.hibernate.entity.air.TaskCountId;
import com.ohmuk.folitics.hibernate.repository.air.ITaskAirCountRepository;

/**
 * Service implementation for entity: {@link TaskAirCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class TaskAirCountService implements IAirCountService<TaskAirCount> {

	@Autowired
	private ITaskAirCountRepository repository;

	@Override
	public TaskAirCount create(TaskAirCount taskAirCount) {
		return repository.save(taskAirCount);
	}

	public TaskAirCount read(TaskCountId id) {

		return repository.find(id);
	}

	@Override
	public List<TaskAirCount> readAll() {

		return repository.findAll();
	}

	@Override
	public TaskAirCount update(TaskAirCount taskAirCount) {
		return repository.update(taskAirCount);
	}

	public TaskAirCount delete(TaskCountId id) {

		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public TaskAirCount delete(TaskAirCount taskAirCount) {
		repository.delete(taskAirCount.getId());
		return repository.find(taskAirCount.getId());
	}

	@Override
	public Long getAirCountByComponentId(Long componentId) {
		TaskAirCount taskAirCount = repository.findByComponentId(componentId);
		if (taskAirCount != null)
			return taskAirCount.getAirCount();
		else
			return 0l;
	}

}
