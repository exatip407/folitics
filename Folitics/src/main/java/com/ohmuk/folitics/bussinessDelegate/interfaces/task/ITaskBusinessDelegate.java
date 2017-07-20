package com.ohmuk.folitics.bussinessDelegate.interfaces.task;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

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
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;

/**
 * Business Delegate Interface for for Task module
 * 
 * @author Sarvesh
 *
 */

public interface ITaskBusinessDelegate {

	/**
	 * @param task
	 * @return Task
	 * @throws MessageException
	 * @throws Exception
	 */
	public Task addTask(Task task) throws MessageException, Exception;

	/**
	 * @param task
	 * @return Task
	 * @throws MessageException
	 * @throws Exception
	 */
	public Task updateTask(Task task) throws MessageException, Exception;

	/**
	 * @param id
	 * @return Task
	 * @throws MessageException
	 * @throws Exception
	 */
	public Task changeTaskStatus(Long id, String status)
			throws MessageException, Exception;
	
	/**
	 * @param id
	 * @param userId
	 * @return TaskParticipant
	 * @throws MessageException
	 * @throws Exception
	 */
	public boolean participateStatus(Long id,Long userId)
			throws MessageException, Exception;

	/**
	 * @param task
	 * @throws MessageException
	 * @throws Exception
	 */
	public void deleteTask(Long id) throws MessageException, Exception;

	/**
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> getAllTasks() throws MessageException, Exception;

	/**
	 * @param user
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> getTaskCreatedByUser(Long userId) throws MessageException,
			Exception;
	
	/**
	 * @param userId
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> getParticipatedTask(Long userId) throws MessageException,
			Exception;

	/**
	 * @param user
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> getTasksParticipatedByUser(User user)
			throws MessageException, Exception;

	/**
	 * @param task
	 * @return Set<User>
	 * @throws MessageException
	 * @throws Exception
	 */
	public Set<User> getTaskParticipants(Task task) throws MessageException,
			Exception;

	/**
	 * @param status
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> listTasksByStatus(String status) throws MessageException,
			Exception;

	/**
	 * @return List<TaskCategory>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<TaskCategory> getAllCategories() throws MessageException,
			Exception;

	/**
	 * @return List<Department>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Department> getAllDepartments() throws MessageException,
			Exception;

	/**
	 * @param category
	 * @return TaskCategory
	 * @throws MessageException
	 * @throws Exception
	 */
	public TaskCategory addCategory(TaskCategory category)
			throws MessageException, Exception;

	/**
	 * @param id
	 * @return Task
	 * @throws MessageException
	 * @throws Exception
	 */
	public Task findOne(Long id) throws MessageException, Exception;

	/**
	 * These method is used to added participant in task
	 * 
	 * @param taskId
	 *            `
	 * @param userId
	 * @throws MessageException
	 * @throws Exception
	 */
	public void addParticipant(Long taskId, Long userId)
			throws MessageException, Exception;
	public void addSolver(Long taskId, Long userId)throws MessageException, Exception;

	/**
	 * These method is used to remove participant from task
	 * 
	 * @param taskId
	 * @param userId
	 * @throws MessageException
	 * @throws Exception
	 */
	public void removeParticipant(Long taskId, Long userId)
			throws MessageException, Exception;

	/**
	 * API to get tasks which are not banned or completed
	 * 
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> getActiveTasks() throws MessageException, Exception;

	/**
	 * @param questionAnswer
	 * @return QuestionAnswer
	 * @throws MessageException
	 * @throws Exception
	 */
	public Question askQuestion(Question questionAnswer)
			throws MessageException, Exception;

	/**
	 * @param questionAnswer
	 * @return QuestionAnswer
	 * @throws MessageException
	 * @throws Exception
	 */
	public Question editQuestion(Question questionAnswer)
			throws MessageException, Exception;

	/**
	 * @param questionAnswer
	 * @return QuestionAnswer
	 * @throws MessageException
	 * @throws Exception
	 */
	public Answer postAnswer(Answer answer)
			throws MessageException, Exception;

	/**
	 * Method is to get {@link Personality} by id
	 * 
	 * @param id
	 * @return Personality
	 */
	public Personality getPersonalityByid(Long id) throws Exception;

	/**
	 * Method is to {@link Personality}
	 * 
	 * @param personality
	 * @return Personality
	 */
	public Personality savePersonality(Personality personality)
			throws Exception;

	/**
	 * Method is to update {@link Personality}
	 * 
	 * @param personality
	 * @return boolean
	 */
	public boolean updatePersonality(Personality personality) throws Exception;

	/**
	 * Method is to delete {@link Personality} by id
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deletePersonality(Long id) throws Exception;

	/**
	 * Method is to get all personality
	 * 
	 * @return List<Personality>
	 * @throws Exception
	 */
	public List<Personality> getAllPersonality() throws Exception;

	/**
	 * Method is to upload {@link Personality} image by id
	 * 
	 * @param id
	 * @param image
	 * @return boolean
	 * @throws Exception
	 * @throws MessageException
	 */
	public boolean uploadPersonalityImage(Long id, MultipartFile image)
			throws Exception, MessageException;
	
	/**
	 * Method is to get List of ten {com.ohmuk.folitics.hibernate.entity.Task}
	 * starting from startId
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Task
	 * @param: startId
	 * 
	 *  @ throws
	 *         MessageException, Exception
	 * 
	 */

	public List<Task> paginationApi(int start)
			throws MessageException, Exception;

	Task findOneWithUser(Long id, Long userId) throws MessageException,
			Exception;

	public TaskParticipant getParticipantForUser(Task task, User user)
			throws MessageException, Exception;

	public TaskSolver getSolverforUser(Task task, User user)throws MessageException, Exception;
	
	public  TaskComment getTaskComments(Long taskId) throws MessageException,
	Exception;

	

	
}
