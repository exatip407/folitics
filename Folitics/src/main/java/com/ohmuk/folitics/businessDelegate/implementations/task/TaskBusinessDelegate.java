package com.ohmuk.folitics.businessDelegate.implementations.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ohmuk.folitics.bussinessDelegate.interfaces.task.ITaskBusinessDelegate;
import com.ohmuk.folitics.enums.TaskStatus;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;
import com.ohmuk.folitics.hibernate.entity.task.Answer;
//import com.ohmuk.folitics.jpa.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.task.Personality;
import com.ohmuk.folitics.hibernate.entity.task.Question;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskCategory;
import com.ohmuk.folitics.hibernate.entity.task.TaskParticipant;
import com.ohmuk.folitics.hibernate.entity.task.TaskPersonality;
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;
import com.ohmuk.folitics.notification.InterfaceNotificationService;
import com.ohmuk.folitics.notification.NotificationMapping;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.service.task.ITaskParticipantService;
import com.ohmuk.folitics.service.task.ITaskService;
import com.ohmuk.folitics.service.task.ITaskSolverService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Business Delegate Implementation of Interface : ITaskBusinessDelegate
 * 
 * @author
 *
 */
@SuppressWarnings({ "unused", })
@Component
public class TaskBusinessDelegate implements ITaskBusinessDelegate {

	private volatile Logger logger = LoggerFactory
			.getLogger(TaskBusinessDelegate.class);

	@Autowired
	ITaskService service;

	@Autowired
	IUserService UserService;

	@Autowired
	ITaskParticipantService taskParticipantService;

	@Autowired
	ITaskSolverService taskSolverService;

	@Autowired
	private InterfaceNotificationService notificationService;

	Set<Long> usersSet = new HashSet<Long>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #addTask(com.ohmuk.folitics.jpa.entity. task.Task)
	 */
	@Override
	public Task addTask(Task task) throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method :  addTask   ");

		if (task == null) {
			logger.error("Null value in argument not acceptable");
			throw (new MessageException("Null value in argument not acceptable"));
		}

		logger.debug("creating new task with task id: " + task.getId());
		logger.info("Exiting addTask");

		Set<TaskParticipant> taskParticipants = task.getTaskParticipants();
		if (taskParticipants != null)
			for (TaskParticipant taskParticipant : taskParticipants) {
				taskParticipant.setTask(task);
				taskParticipant.setStatus("pending");
				usersSet.add(taskParticipant.getUser().getId());

			}

		Set<TaskPersonality> taskPersonalitys = task.getTaskPersonality();
		if (taskPersonalitys != null)
			for (TaskPersonality taskPersonality : taskPersonalitys) {
				taskPersonality.setTask(task);

			}
		task.setCreationDate(DateUtils.getSqlTimeStamp());
		task.setStatus(TaskStatus.CREATED.getValue());
		task = service.save(task);
		if (task != null) {
			NotificationMapping notificationMapping = new NotificationMapping();
			notificationMapping.setComponentType("task");
			notificationMapping.setSendingUsers(usersSet);
			notificationMapping.setComponentId(task.getId());
			notificationMapping.setUserId(task.getCreatedBy().getId());

			notificationService.taskNotification(notificationMapping);
		}
		System.out.println(task);
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #updateTask(com.ohmuk.folitics.jpa.
	 * entity.task.Task)
	 */
	@Override
	public Task updateTask(Task task) throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method :  updateTask   ");

		if (task == null) {
			logger.error("Null value in argument not acceptable");
			throw (new MessageException("Null value in argument not acceptable"));
		}

		logger.debug("Updating task with task id: " + task.getId());
		logger.info("Exiting updateTask");

		task.setEditDate(DateUtils.getSqlTimeStamp());

		return service.update(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #changeTaskStatus(com.ohmuk.folitics
	 * .jpa.entity.task.Task, java.lang.String)
	 */
	@Override
	public Task changeTaskStatus(Long id, String status)
			throws MessageException, Exception {
		boolean isStatusAvailable = false;

		// Checking whether the requested status is available or not
		for (TaskStatus taskStatus : TaskStatus.values()) {
			if (taskStatus.getValue().equalsIgnoreCase(status)) {
				isStatusAvailable = true;
				status = taskStatus.getValue();
			}

		}

		logger.info("Inside class: TaskBusinessDelegate method :  changeTaskStatus");
		Task task = findOne(id);

		if (!isStatusAvailable) {
			logger.error("Invalid Request status " + status + " is not defined");
			throw (new MessageException("Invalid Request status " + status
					+ " is not defined"));
		}

		if (task == null) {
			logger.error("Task not found");
			throw (new MessageException("Task not found with id " + id));
		}

		if (status == null) {
			logger.error("Null value in argument 'status' not acceptable");
			throw (new MessageException("Null value in argument not acceptable"));
		}

		task.setStatus(status);

		logger.debug("Seting status " + status + " in task with task id: "
				+ task.getId());
		logger.info("Exiting changeTaskStatus");

		return service.update(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #deleteTask(com.ohmuk.folitics.jpa.
	 * entity.task.Task)
	 */
	@Override
	public void deleteTask(Long id) throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getAllTasks ");
		logger.debug("Deleting task with task Id : " + id);
		logger.info("exiting deleteTask");

		service.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #getAllTasks()
	 */
	@Override
	public List<Task> getAllTasks() throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getAllTasks ");
		logger.info("Fetching List of Tasks from DB ");
		logger.info("exiting getAllTasks");

		return service.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #getTasksParticipatedByUser(com.ohmuk.
	 * folitics.jpa.entity.User)
	 */
	@Override
	public List<Task> getTasksParticipatedByUser(User user)
			throws MessageException, Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #getTaskCreatedByUser(com.ohmuk.
	 * folitics.jpa.entity.User)
	 */
	@Override
	public List<Task> getTaskCreatedByUser(Long userId)
			throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getTaskCreatedByUser ");
		logger.info("Fetching List of Tasks from DB ");
		logger.debug("Fetching all tasks created by User with Id : " + userId);
		logger.info("exiting getTasksCreatorByUser");
		return service.findByCreator(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #getTaskParticipants(com.ohmuk.folitics
	 * .jpa.entity.task.Task)
	 */
	@Override
	public Set<User> getTaskParticipants(Task task) throws MessageException,
			Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getTaskCreatedByUser ");
		logger.info("Fetching List of Tasks from DB ");
		logger.debug("Fetching all task participants for task with Id : "
				+ task.getId());
		logger.info("exiting getTaskParticipants");

		if (task == null) {
			throw (new MessageException(
					"Null value not allowed in argument 'task' "));
		}

		return service.getParticipants(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #listTasksByStatus(java.lang.String)
	 */
	@Override
	public List<Task> listTasksByStatus(String status) throws MessageException,
			Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getTaskCreatedByUser ");
		logger.info("Fetching List of Tasks from DB ");
		logger.debug("Fetching all task tasks with status : " + status);
		logger.info("exiting getTaskParticipants");

		if (status == null) {
			throw (new MessageException(
					"Null value not allowed in argument 'status' "));
		}

		return service.findByStatus(status);
	}

	@Override
	public List<TaskCategory> getAllCategories() throws MessageException,
			Exception {
		return service.getCategories();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #getAllDepartments()
	 */
	@Override
	public List<Department> getAllDepartments() throws MessageException,
			Exception {
		return service.getDepartments();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #addCategory(com.ohmuk.folitics.jpa
	 * .entity.task.TaskCategory)
	 */
	@Override
	public TaskCategory addCategory(TaskCategory category)
			throws MessageException, Exception {
		logger.info("Inside class: TaskBusinessDelegate method: addCategory ");
		logger.info("Adding new category to DB");
		logger.info("exiting addCategory");

		if (category == null) {
			throw (new MessageException(
					"Null value not allowed in argument 'category'"));
		}
		return service.addTaskCategory(category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #findOne(java.lang.Long)
	 */
	@Override
	public Task findOne(Long id) throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getAllTasks ");
		logger.info("Fetching List of Tasks from DB ");
		logger.info("exiting getAllTasks");

		if (id == null) {
			throw (new MessageException(
					"Null value not allowed in argument 'id'"));
		}
		return service.searchTask(id);

	}

	@Override
	public TaskParticipant getParticipantForUser(Task task, User user)
			throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getAllTasks ");
		logger.info("Fetching List of Tasks from DB ");
		logger.info("exiting getAllTasks");

		return taskParticipantService.getParticipantForUser(task, user);

	}

	@Override
	public TaskSolver getSolverforUser(Task task, User user)
			throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getAllTasks ");
		logger.info("Fetching List of Tasks from DB ");
		logger.info("exiting getAllTasks");

		return taskSolverService.getSolverforUser(task, user);

	}

	@Override
	public Task findOneWithUser(Long id, Long userId) throws MessageException,
			Exception {

		logger.info("Inside class: TaskBusinessDelegate method: findOneWithUser ");

		if (id == null) {
			throw (new MessageException(
					"Null value not allowed in argument 'id'"));
		}

		if (userId == null) {
			throw (new MessageException(
					"Null value not allowed in argument 'userId'"));
		}
		Task task = service.searchTask(id);
		return service.searchTask(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #addParticipant(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void addParticipant(Long taskId, Long userId)
			throws MessageException, Exception {

		logger.info("Inside addParticipant method in business delegate ");

		User user = UserService.findUserById(userId);

		Task task = service.searchTask(taskId);

		TaskParticipant taskParticipant = new TaskParticipant();

		taskParticipant.setUser(user);
		taskParticipant.setTask(task);
		taskParticipant.setStatus("Pending");

		service.saveParticipant(taskParticipant);

		Set<Long> set = new HashSet<Long>();
		set.add(task.getCreatedBy().getId());

		// call for notification
		NotificationMapping notificationMapping = new NotificationMapping();
		notificationMapping.setAction("participate");
		notificationMapping.setUserId(userId);
		notificationMapping.setSendingUsers(set);
		notificationMapping.setComponentType("task");
		notificationMapping.setComponentId(taskId);

		notificationService.participateNotification(notificationMapping);

		logger.info("Exiting addParticipant method in business delegate ");
	}

	public void addSolver(Long taskId, Long userId) throws MessageException,
			Exception {

		logger.info("Inside addSolver method in business delegate ");

		User user = UserService.findUserById(userId);

		Task task = service.searchTask(taskId);

		TaskSolver taskSolver = new TaskSolver();

		taskSolver.setUser(user);
		taskSolver.setTask(task);
		taskSolver.setStatus("Active");

		service.saveSolver(taskSolver);

		logger.info("Exiting addSolver method in business delegate ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #removeParticipant(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void removeParticipant(Long taskid, Long userId)
			throws MessageException, Exception {

		logger.info("Inside removeParticipant method in business delegate ");
		User user = UserService.findUserById(userId);
		Task task = service.searchTask(taskid);
		service.removeParticipant(user, task);
		logger.info("Exiting removeParticipant method in business delegate ");

	}

	@Override
	public List<Task> getActiveTasks() throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: getActiveTasks ");
		logger.info("Fetching List of Tasks from DB ");
		logger.info("exiting getTaskParticipants");

		List<Task> inputList = service.findAll();
		List<Task> outputList = new ArrayList<Task>();
		for (Task task : inputList) {
			if (!TaskStatus.COMPLETED.getValue().equals(task.getStatus())
					&& !TaskStatus.DELETED.getValue().equals(task.getStatus())
					&& !TaskStatus.BANNED.getValue().equals(task.getStatus())) {
				outputList.add(task);
			}
		}
		return outputList;
	}

	@Override
	public Question askQuestion(Question questionAnswer)
			throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: askQuestion ");
		logger.info("Adding Question to Db");
		logger.info("exiting askQuestion");

		// Task task = questionAnswerBean.getTask();
		// QuestionAnswer questionAnswer =
		// questionAnswerBean.getQuestionAnswer();

		// questionAnswer.setTaskId(task);
		questionAnswer.setQuestionPostingTime(DateUtils.getSqlTimeStamp());
		questionAnswer.setQuestionEditingTime(DateUtils.getSqlTimeStamp());
		return service.addQuestion(questionAnswer);
	}

	@Override
	public Question editQuestion(Question questionAnswer)
			throws MessageException, Exception {

		logger.info("Inside class: TaskBusinessDelegate method: editQuestion ");
		logger.info("Updating Question in Db");
		logger.info("exiting askQuestion");

		questionAnswer.setQuestionEditingTime(DateUtils.getSqlTimeStamp());
		return service.editQuestion(questionAnswer);
	}

	@Override
	public Answer postAnswer(Answer answer) throws MessageException, Exception {
		logger.info("Inside class: TaskBusinessDelegate method: postAnswer ");
		logger.info("Adding answer");
		logger.info("exiting postAnswer");

		answer.setAnswerPostingTime(DateUtils.getSqlTimeStamp());
		return service.postAnswer(answer);
	}

	@Override
	public Personality getPersonalityByid(Long id) throws Exception {
		logger.info("Inside class: TaskBusinessDelegate method: getPersonalityByid ");
		Personality personality = service.getPersonalityById(id);
		logger.info("Exiting class: TaskBusinessDelegate method: getPersonalityByid ");
		return personality;
	}

	@Override
	public Personality savePersonality(Personality personality)
			throws Exception {
		logger.info("Inside class: TaskBusinessDelegate method: getPersonalityByid ");
		personality = service.savePersonality(personality);
		logger.info("Exiting class: TaskBusinessDelegate method: getPersonalityByid ");
		return personality;
	}

	@Override
	public boolean updatePersonality(Personality personality) throws Exception {
		logger.info("Inside class: TaskBusinessDelegate method: updatePersonality ");
		boolean status = service.updatePersonality(personality);
		logger.info("Inside class: TaskBusinessDelegate method: updatePersonality ");
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #deletePersonality(java.lang.Long)
	 */
	@Override
	public boolean deletePersonality(Long id) throws Exception {
		logger.info("Inside class: TaskBusinessDelegate method: deletePersonality ");
		if (service.deletePersonalityById(id)) {
			logger.info("Exiting class: TaskBusinessDelegate method: deletePersonality ");
			return true;
		}
		logger.info("Exiting class: TaskBusinessDelegate method: deletePersonality ");
		logger.debug("Personality with id: " + id + " is not deleted");
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.bussinessDelegate.interfaces.task.
	 * ITaskBusinessDelegate #uploadPersonalityImage()
	 */
	@Override
	public boolean uploadPersonalityImage(Long id, MultipartFile image)
			throws Exception, MessageException {
		logger.info("Inside class: TaskBusinessDelegate method: uploadPersonalityImage ");
		Personality personality = service.getPersonalityById(id);
		personality.setImage(image.getBytes());
		boolean status = service.updatePersonality(personality);
		logger.info("Exiting class: TaskBusinessDelegate method: uploadPersonalityImage ");
		return status;
	}

	@Override
	public List<Personality> getAllPersonality() throws Exception {
		logger.info("Inside class: TaskBusinessDelegate method: getAllPersonality ");
		List<Personality> personalities = service.getAllPersonality();
		logger.info("Exiting class: TaskBusinessDelegate method: getAllPersonality ");
		return personalities;
	}

	@Override
	public List<Task> paginationApi(int start) throws MessageException,
			Exception {

		logger.info("Inside paginationApi method in business delegate");

		List<Task> tasks = service.paginationApi(start);

		logger.info("Exiting paginationApi method from business delegate");

		return tasks;
	}

	@Override
	public boolean participateStatus(Long id, Long userId)
			throws MessageException, Exception {
		logger.info("Inside paginationApi method in business delegate");

		boolean result = service.participateStatus(id, userId);

		logger.info("Exiting paginationApi method from business delegate");

		return result;
	}

	@Override
	public List<Task> getParticipatedTask(Long userId) throws MessageException,
			Exception {
		logger.info("Inside getParticipatedTask method in business delegate");

		List<Task> tasks = service.getParticipatedTask(userId);

		logger.info("Exiting getParticipatedTask method from business delegate");

		return tasks;
	}

	@Override
	public TaskComment getTaskComments(Long taskId) throws MessageException,
			Exception {
		List<TaskComment> taskComments = service.getTaskComments(taskId);
		//return taskComments;
		return null;
	}

}
