package com.ohmuk.folitics.service.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.elasticsearch.service.IESService;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsComment;
import com.ohmuk.folitics.hibernate.entity.task.Answer;
//import com.ohmuk.folitics.jpa.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.task.Personality;
import com.ohmuk.folitics.hibernate.entity.task.Question;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskCategory;
import com.ohmuk.folitics.hibernate.entity.task.TaskParticipant;
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;
import com.ohmuk.folitics.util.ElasticSearchUtils;
import com.ohmuk.folitics.util.Serialize_JSON;

/**
 * Hibernate Service implementation for module {@link Task}
 * 
 * @author Servesh
 *
 */
@Service
@Transactional
public class TaskService implements ITaskService {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#save(com.ohmuk.folitics.
	 * jpa.entity.task.Task)
	 */
	@Override
	public Task save(Task task) throws MessageException, Exception {
		logger.info("Inside TaskService save method");
		Long id = (Long) getSession().save(task);
		logger.debug("Task is save with id: " + id);

		logger.info("Exiting from TaskService save method");
		task = (Task) getSession().get(Task.class, id);
		
		 if (Constants.useElasticSearch)
		  esService.save(ElasticSearchUtils.INDEX,
		  ElasticSearchUtils.TYPE_TASK, String.valueOf(id),
		  Serialize_JSON.getJSONString(task));
		 
		logger.debug("Task is save in elastic search with id: " + id);
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#update(com.ohmuk.folitics
	 * .jpa.entity.task.Task)
	 */
	@Override
	public Task update(Task task) throws MessageException, Exception {
		logger.info("Inside TaskService update method");
		task = (Task) getSession().merge(task);
		getSession().update(task);

		/*
		 * esService.update(ElasticSearchUtils.INDEX,
		 * ElasticSearchUtils.TYPE_TASK, String.valueOf(task.getId()),
		 * Serialize_JSON.getJSONString(task));
		 */
		logger.debug("Task is updated in elasticsearch with id: "
				+ task.getId());
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#delete(com.ohmuk.folitics
	 * .jpa.entity.task.Task)
	 */
	@Override
	public void delete(Long id) throws MessageException, Exception {
		Task task = searchTask(id);
		getSession().delete(task);
		logger.debug("Task is deleted from DB with id: " + id);
		esService.delete(ElasticSearchUtils.INDEX,
				ElasticSearchUtils.TYPE_TASK, String.valueOf(id));
		logger.debug("Task is deleted from elasticsearch with id: " + id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.service.task.ITaskService#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> findAll() throws MessageException, Exception {
		Criteria criteria = getSession().createCriteria(Task.class).addOrder(
				Order.desc("creationDate"));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#findByCreator(com.ohmuk.
	 * folitics.jpa.entity.User)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> findByCreator(Long userId) throws MessageException,
			Exception {
		Criteria criteria = getSession().createCriteria(Task.class);
		criteria.add(Restrictions.eq("createdBy.id", userId));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#getParticipants(com.ohmuk
	 * .folitics.jpa.entity.task.Task)
	 */
	@Override
	public Set<User> getParticipants(Task task) throws MessageException,
			Exception {

		task = (Task) getSession().get(Task.class, task.getId());

		Criteria criteria = getSession().createCriteria(TaskParticipant.class);
		criteria.add(Restrictions.eq("task", task));

		@SuppressWarnings("unchecked")
		List<TaskParticipant> particpantsList = criteria.list();

		Set<TaskParticipant> participantSet = new HashSet<TaskParticipant>(
				particpantsList);

		Set<User> userParticipant = new HashSet<User>();

		for (TaskParticipant participant : participantSet) {
			userParticipant.add(participant.getUser());
		}

		return userParticipant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.service.task.ITaskService#findByStatus(java.lang.
	 * String )
	 */
	@Override
	public List<Task> findByStatus(String status) throws MessageException,
			Exception {
		Criteria criteria = getSession().createCriteria(Task.class);
		criteria.add(Restrictions.eq("status", status)).addOrder(
				Order.desc("creationDate"));
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.service.task.ITaskService#getCategories()
	 */
	@Override
	public List<TaskCategory> getCategories() {
		return getSession().createCriteria(TaskCategory.class).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.service.task.ITaskService#getDepartments()
	 */
	@SuppressWarnings({ UNCHECKED, UNCHECKED })
	@Override
	public List<Department> getDepartments() {
		return getSession().createCriteria(Department.class).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#addTaskCategory(com.ohmuk
	 * .folitics.jpa.entity.task.TaskCategory)
	 */
	@Override
	public TaskCategory addTaskCategory(TaskCategory category) {
		Long id = (Long) getSession().save(category);
		return (TaskCategory) getSession().get(TaskCategory.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.task.ITaskService#searchTask(java.lang.Long)
	 */
	@Override
	public Task searchTask(Long id) {
		Task task = (Task) getSession().get(Task.class, id);
		Hibernate.initialize(task.getCategory());
		Hibernate.initialize(task.getPeopleMet());
		Hibernate.initialize(task.getOtherDepartment());
		Hibernate.initialize(task.getTaskSolver());
		Hibernate.initialize(task.getDepartments());
		return (Task) getSession().get(Task.class, id);
	}

	@Override
	public Question addQuestion(Question questionAnswer) {
		Long id = (Long) getSession().save(questionAnswer);
		return (Question) getSession().get(Question.class, id);
	}

	@Override
	public void deleteQuestion(Question questionAnswer) {
		getSession().delete(questionAnswer);
	}

	@Override
	public Answer postAnswer(Answer answer) {

		Long id = (Long) getSession().save(answer);
		// answer = (QuestionAnswer) getSession().merge(questionAnswer);
		// getSession().update(questionAnswer);
		Answer answer1 = (Answer) getSession().get(Answer.class, id);
		return answer1;
	}

	@Override
	public List<Personality> getAllPersonality() {
		logger.info("Inside TaskService getAllPersonality method");
		List<Personality> personalities = getSession().createCriteria(
				Personality.class).list();
		logger.info("Exiting TaskService getAllPersonality method");
		return personalities;
	}

	@Override
	public boolean updatePersonality(Personality personality) {
		logger.info("Inside TaskService update method");
		getSession().update(personality);
		logger.info("Exiting TaskService update method");
		return true;
	}

	@Override
	public Personality getPersonalityById(Long id) {
		logger.info("Inside TaskService getPersonalityById method");
		Personality personality = (Personality) getSession().get(
				Personality.class, id);
		logger.info("Exiting TaskService getPersonalityById method");
		return personality;
	}

	@Override
	public boolean deletePersonalityById(Long id) {
		logger.info("Inside TaskService deleteById method");
		getSession().delete(getPersonalityById(id));
		logger.info("Exiting TaskService deleteById method");
		return true;
	}

	@Override
	public Personality savePersonality(Personality personality) {
		logger.info("Inside TaskService save method");
		Long id = (Long) getSession().save(personality);
		logger.info("Exiting TaskService save method");
		return getPersonalityById(id);
	}

	@Override
	public void addMonetizationPoints(Long userId, Long taskId, String action)
			throws Exception {

		logger.info("Inside TaskService addMonetizationPoints method");
		logger.debug("Monetization points added for userId :" + userId
				+ "for action" + action + "on taskId" + taskId);

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType", "Task"));

		Module module = (Module) criteria.uniqueResult();
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType("Task");

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(userId);
		userMonetization.setActionComponentId(taskId);

		userMonetizationBussinessDeligate.addAction(userMonetization);
		logger.info("Exiting TaskService addMonetizationPoints method");

	}

	@Override
	public void saveParticipant(TaskParticipant taskParticipant)
			throws MessageException, Exception {

		logger.info("Inside TaskService saveParticipant method");

		getSession().save(taskParticipant);

		logger.info("Exiting TaskService saveParticipant method");

	}

	@Override
	public void saveSolver(TaskSolver taskSolver) throws MessageException,
			Exception {

		logger.info("Inside TaskService saveSolver method");

		getSession().save(taskSolver);

		logger.info("Exiting TaskService saveSolver method");

	}

	public void removeParticipant(User user, Task task) {

		logger.info("Inside TaskService removeParticipant method");
		logger.debug("Removed participant Id:-" + user.getId()
				+ "From taskId:-" + task.getId());

		Criteria criteria = getSession().createCriteria(TaskParticipant.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("task", task));

		TaskParticipant taskpParticipant = (TaskParticipant) criteria
				.uniqueResult();

		getSession().delete(taskpParticipant);

		logger.info("Exiting TaskService removeParticipant method");

	}

	@Override
	public List<Task> paginationApi(int startId) throws MessageException,
			Exception {
		logger.info("Inside paginationApi method in TaskService");

		int endId = startId + 10;

		Criteria criteria = getSession().createCriteria(Task.class).addOrder(
				Order.desc("creationDate"));
		criteria.setFirstResult(startId);
		criteria.setMaxResults(endId);

		@SuppressWarnings("unchecked")
		List<Task> tasks = criteria.list();

		/*
		 * if (tasks.size() == 0) { logger.error(
		 * "tasks does not exist between start id:" + startId + " and end id:" +
		 * endId); throw new MessageException(
		 * "tasks does not exist between start id  :" + startId + " and end id"
		 * + endId); }
		 */

		logger.debug("Results got from id:" + startId + " to id:" + endId);
		logger.info("Exiting paginationApi method from TaskService");

		return tasks;
	}

	@Override
	public boolean participateStatus(Long id, Long userId)
			throws MessageException, Exception {

		logger.info("Inside participate status service");

		User user = new User();
		user.setId(userId);
		Task task = new Task();
		task.setId(id);

		Criteria criteria = getSession().createCriteria(TaskParticipant.class);
		criteria.add(Restrictions.eqOrIsNull("task", task));
		criteria.add(Restrictions.eqOrIsNull("user", user));

		TaskParticipant taskParticipant = (TaskParticipant) criteria
				.uniqueResult();

		getSession().update(taskParticipant);

		logger.info("Exiting participate status service");

		return true;
	}

	@Override
	public List<Task> getParticipatedTask(Long userId) throws MessageException,
			Exception {

		logger.info("Inside getParticipatedTask service");

		Criteria criteria = getSession().createCriteria(TaskParticipant.class);
		criteria.add(Restrictions.eqOrIsNull("userId", userId));

		return criteria.list();

	}

	@Override
	public Question editQuestion(Question questionAnswer) {
		questionAnswer = (Question) getSession().merge(questionAnswer);
		getSession().update(questionAnswer);
		return questionAnswer;

	}

	@Override
	public List<TaskComment> getTaskComments(Long taskId) throws MessageException,
			Exception {

		logger.info("Inside TaskService getTaskComments method");

		Criteria criteria = getSession().createCriteria(TaskComment.class);
		criteria.add(Restrictions.eqOrIsNull("componentId", taskId)).addOrder(
				Order.desc("createTime"));
		criteria.setMaxResults(3);

		List<TaskComment> taskComments = criteria.list();

		logger.info("Exiting TaskService getTaskComments method");
		return null;
	}

	@Override
	public TaskParticipantsComment getTaskParticipantsComments(Long taskId)
			throws MessageException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getQuestion(Long taskId) throws MessageException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer getAswer(Long taskId) throws MessageException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
