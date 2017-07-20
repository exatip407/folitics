package com.ohmuk.folitics.service.task;

import java.util.List;
import java.util.Set;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
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

/**
 * Service Interface for Module : {@link Task}
 * 
 * @author Sarvesh
 *
 */
public interface ITaskService {

	/**
	 * @param task
	 * @return Task
	 * @throws MessageException
	 * @throws Exception
	 */
	public Task save(Task task) throws MessageException, Exception;

	/**
	 * These method is used for added participant in task
	 * 
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.task.TaskParticipant
	 * @return void
	 * @throws MessageException
	 * @throws Exception
	 */
	public void saveParticipant(TaskParticipant taskParticipant)
			throws MessageException, Exception;

	/**
	 * @param task
	 * @return Task
	 * @throws MessageException
	 * @throws Exception
	 */
	public Task update(Task task) throws MessageException, Exception;

	/**
	 * @param id
	 * @throws MessageException
	 * @throws Exception
	 */
	public void delete(Long id) throws MessageException, Exception;

	/**
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> findAll() throws MessageException, Exception;

	/**
	 * @param user
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> findByCreator(Long userId) throws MessageException,
			Exception;

	/**
	 * @param id
	 * @param userId
	 * @return TaskParticipate
	 * @throws MessageException
	 * @throws Exception
	 */
	public boolean participateStatus(Long id, Long userId)
			throws MessageException, Exception;

	/**
	 * @param task
	 * @return Set<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public Set<User> getParticipants(Task task) throws MessageException,
			Exception;

	/**
	 * @param status
	 * @return List<Task>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Task> findByStatus(String status) throws MessageException,
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
	 * Method to get list of All categories
	 * 
	 * @return
	 */
	public List<TaskCategory> getCategories();

	/**
	 * Method to get list of All departments
	 * 
	 * @return
	 */
	public List<Department> getDepartments();

	/**
	 * API to add new TaskCategory
	 * 
	 * @param category
	 * @return TaskCategory
	 */
	public TaskCategory addTaskCategory(TaskCategory category);

	/**
	 * API to search task by Id
	 * 
	 * @param id
	 * @return Task
	 */
	public Task searchTask(Long id);

	/**
	 * @param questionAnswer
	 * @return QuestionAnswer
	 */
	public Question addQuestion(Question questionAnswer);

	/**
	 * @param questionAnswer
	 */
	public void deleteQuestion(Question questionAnswer);

	/**
	 * @param questionAnswer
	 * @return QuestionAnswer
	 */
	public Answer postAnswer(Answer answer);

	public Question editQuestion(Question questionAnswer);

	/**
	 * Method is to get all {@link Personality}
	 * 
	 * @return List<Personality>
	 */
	public List<Personality> getAllPersonality();

	/**
	 * Method is to save {@link Personality}
	 * 
	 * @param personality
	 * @return Personality
	 */
	public Personality savePersonality(Personality personality);

	/**
	 * Method is update {@link Personality}
	 * 
	 * @param personality
	 * @return boolean
	 */
	public boolean updatePersonality(Personality personality);

	/**
	 * Method is find {@link Personality} by id
	 * 
	 * @param personality
	 * @return Personality
	 */
	public Personality getPersonalityById(Long id);

	/**
	 * Method is to delete {@link Personality} by id
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean deletePersonalityById(Long id);

	/**
	 * Method is to delete participant from task {@link TaskParticipant}
	 * 
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.User
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.task.Task
	 * @return void
	 */

	public void removeParticipant(User user, Task task);

	/**
	 * This method will added user points, after created,raise problem,Accepted
	 * task
	 * 
	 * @author Harish
	 * @param javacom
	 *            .ohmuk.folitics.beans.LikeDataBean
	 * 
	 * @return void
	 */

	public void addMonetizationPoints(Long userId, Long taskId, String action)
			throws Exception;

	/**
	 * Method is to get List of ten {com.ohmuk.folitics.hibernate.entity.Task}
	 * starting from startId
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Task
	 * @param: startId
	 * 
	 *         @ throws MessageException, Exception
	 * 
	 */

	public List<Task> paginationApi(int start) throws MessageException,
			Exception;

	void saveSolver(TaskSolver taskSolver) throws MessageException, Exception;

	public List<TaskComment> getTaskComments(Long taskId) throws MessageException,
			Exception;

	public TaskParticipantsComment getTaskParticipantsComments(Long taskId)
			throws MessageException, Exception;

	public Question getQuestion(Long taskId) throws MessageException, Exception;

	public Answer getAswer(Long taskId) throws MessageException, Exception;

}
