package com.ohmuk.folitics.service.task;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.elasticsearch.service.IESService;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;

@Transactional
@Service
public class TaskSolverService implements ITaskSolverService {
	

	private static final String UNCHECKED = "unchecked";

	private volatile Logger logger = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	IESService esService;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskSolver getSolverforUser( Task task, User user) throws MessageException, Exception {
		
		Hibernate.initialize(task.getTaskSolver());
		Criteria criteria = getSession().createCriteria(TaskSolver.class);
		criteria.add(Restrictions.eq("task", task));
		criteria.add(Restrictions.eq("user", user));
		return (TaskSolver) criteria.uniqueResult();
	}


}
