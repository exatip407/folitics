package com.ohmuk.folitics.controller.task;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ohmuk.folitics.beans.TaskWrapper;
import com.ohmuk.folitics.bussinessDelegate.interfaces.task.ITaskBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Answer;
//import com.ohmuk.folitics.jpa.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.task.Personality;
import com.ohmuk.folitics.hibernate.entity.task.Question;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskCategory;
import com.ohmuk.folitics.hibernate.entity.task.TaskParticipant;
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;
import com.ohmuk.folitics.service.IUserService;

/**
 * @author Sarvesh
 *
 */
@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	ITaskBusinessDelegate delegate;

	@Autowired
	IUserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskController.class);

	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> addNewTask(
			@Validated @RequestPart(value = "task") Task task,
			@RequestPart(value = "file") MultipartFile attachment,
			@RequestPart(value = "image") MultipartFile image) {

		try {

			if (task == null) {
				logger.error("No parameter Passed");
				throw (new MessageException("No parameter Passed"));
			}

			if (attachment == null) {
				logger.info("Image not Available");
			}

			if (image == null) {
				logger.info("Image not Available");
			}

			// Adding attachment to Task
			task.setAttachment(attachment.getBytes());
			task.setAttachmentType(FileType.getFileType(
					attachment.getOriginalFilename().split("\\.")[1])
					.getValue());

			// Adding image to Task
			task.setImage(image.getBytes());
			task.setAttachmentType(FileType.getFileType(
					image.getOriginalFilename().split("\\.")[1]).getValue());
			task = delegate.addTask(task);

		} catch (MessageException e) {

			logger.error("CustomException while adding the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Exception while adding the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		logger.debug("Task saved " + task);
		return new ResponseDto<Object>(true, task, "Task Successfully Created");
	}

	@RequestMapping(value = "/editTask", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> editTask(@RequestBody Task task) {

		try {

			if (task == null) {

				logger.error("No parameter Passed");
				throw (new MessageException("No parameter Passed"));
			}

			task = delegate.updateTask(task);

			if (task == null) {

				logger.error("Unable to edit Task");
				throw (new MessageException("Unable to edit Task"));
			}

		} catch (MessageException e) {

			logger.error("CustomException while editing the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Exception while editing the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		return new ResponseDto<Object>(true, task, "Task Successfully Updated");

	}

	@RequestMapping(value = "/setStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> setStatus(Long id, String status) {

		Task task;

		try {

			if (id == null) {
				logger.error("Null value in parameter 'id'");
				throw (new MessageException("Null value in parameter 'id'"));
			}

			task = delegate.changeTaskStatus(id, status);
			if (task == null) {
				logger.error("Unable to edit Task");
				throw (new MessageException("Unable to edit Task"));
			}

		} catch (MessageException e) {

			logger.error("CustomException while editing the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Exception while editing the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		return new ResponseDto<>("Status Updated Successfully", true);

	}

	@RequestMapping(value = "/removeTask", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> removeTask(Long id) {

		try {
			if (id == null) {
				logger.error("No parameter Passed");
				throw (new MessageException("No parameter Passed"));
			}
			delegate.deleteTask(id);
		} catch (MessageException e) {
			logger.error("CustomException while deleting the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		} catch (Exception e) {
			logger.error("Exception while deleting the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		return new ResponseDto<Object>("Task Successfully Deleted", true);
	}

	@RequestMapping(value = "/mytasks", method = RequestMethod.GET)
	@ResponseBody
	ResponseDto<Object> getMyTasks(Long userId) {

		List<Task> list;

		try {
			if (userId == null) {
				throw (new MessageException("No or invalid arguments provided"));
			}

			list = delegate.getTaskCreatedByUser(userId);
			list.size();

		} catch (MessageException e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		catch (Exception e) {
			logger.error("Exception while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		return new ResponseDto<Object>(true, list, "List Fetched Successfully");
	}

	@RequestMapping(value = "/Participated", method = RequestMethod.GET)
	@ResponseBody
	ResponseDto<Object> getParticipatedTask(Long userId) {

		List<Task> list;

		try {
			if (userId == null) {
				throw (new MessageException("No or invalid arguments provided"));
			}

			list = delegate.getParticipatedTask(userId);

		} catch (MessageException e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		catch (Exception e) {
			logger.error("Exception while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		return new ResponseDto<Object>(true, list, "List Fetched Successfully");
	}

	@RequestMapping(value = "/getParticipants", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	ResponseDto<Object> getMyTasks(@RequestBody Task task) {

		Set<User> set;
		try {
			if (task == null) {

				throw (new MessageException("No or invalid arguments provided"));

			}

			set = delegate.getTaskParticipants(task);

		} catch (MessageException e) {

			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		catch (Exception e) {

			logger.error("Exception while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		return new ResponseDto<Object>(true, set, "List Fetched Successfully");

	}

	@RequestMapping(value = "/getByStatus", method = RequestMethod.GET)
	@ResponseBody
	ResponseDto<Object> getByStatus(String status) {

		List<Task> list;

		try {
			if (status == null) {
				throw (new MessageException("No or invalid arguments provided"));
			}
			list = delegate.listTasksByStatus(status);
		} catch (MessageException e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		} catch (Exception e) {
			logger.error("Exception while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, list, "List Fetched Successfully");
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAll() {

		List<Task> tasks;
		try {
			tasks = delegate.getAllTasks();
		} catch (Exception e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, tasks,
				"Sucessfully Fetched list of all tasks");
	}

	@RequestMapping(value = "/getTaskCategories", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllCategories() {

		List<TaskCategory> categories;
		try {
			categories = delegate.getAllCategories();
		} catch (Exception e) {
			logger.error("CustomException while fetching the task categories "
					+ e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, categories,
				"Sucessfully Fetched list of all taskCategories");
	}

	@RequestMapping(value = "/getAllDepartments", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllDepartments() {

		List<Department> departments;
		try {
			departments = delegate.getAllDepartments();
		} catch (Exception e) {
			logger.error("CustomException while fetching the Departments " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, departments,
				"Sucessfully Fetched list of all departments");
	}

	@RequestMapping(value = "/addCategory", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> addCategory(
			@RequestBody TaskCategory category) {

		try {
			category = delegate.addCategory(category);
		} catch (Exception e) {
			logger.error("CustomException while adding new TaskCategory " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, category,
				"Sucessfully Fetched list of all taskCategories");
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> search(Long id, Long userId) {

		Task task;

		TaskWrapper taskwrapper = new TaskWrapper();
		try {
			if (id == null) {
				logger.error("No parameter Passed");
				throw (new MessageException("No parameter Passed"));
			}
			task = delegate.findOne(id);
			User user = userService.findUserById(userId);
			TaskParticipant taskparticipant = delegate.getParticipantForUser(
					task, user);
			TaskSolver tasksolver = delegate.getSolverforUser(task, user);

			taskwrapper.setTask(task);
			taskwrapper.setTaskparticipant(taskparticipant);
			taskwrapper.setTasksolver(tasksolver);

		} catch (Exception e) {
			logger.error("CustomException while fetching the task " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, taskwrapper,
				"Sucessfully Fetched task");
	}

	@RequestMapping(value = "/addParticipant", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> addParticipant(Long taskId,
			Long userId) {

		try {
			if (taskId == null) {
				logger.error("Null in taskId not allowed");
				throw (new MessageException("Null in 'taskId not allowed'"));
			}
			delegate.addParticipant(taskId, userId);

		} catch (Exception e) {
			logger.error("CustomException while adding the participant " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true,
				"Sucessfully Added participant to task");
	}

	@RequestMapping(value = "/addSolver", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> addSolver(Long taskId, Long userId) {

		try {
			if (taskId == null) {
				logger.error("Null in taskId not allowed");
				throw (new MessageException("Null in 'taskId not allowed'"));
			}
			delegate.addSolver(taskId, userId);

		} catch (Exception e) {
			logger.error("CustomException while adding the solver " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, "Sucessfully Added solver to task");
	}

	@RequestMapping(value = "/removeParticipant", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> removeParticipant(Long id,
			Long userId) {

		try {
			if (id == null) {
				logger.error("Null in 'taskId not allowed'");
				throw (new MessageException("Null in 'taskId not allowed'"));
			}
			delegate.removeParticipant(id, userId);

		} catch (Exception e) {
			logger.error("CustomException while removing the participant " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true,
				"Sucessfully Removed participant from task");
	}

	@RequestMapping(value = "/getActiveTasks", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getActiveTasks() {

		List<Task> tasks;
		try {
			tasks = delegate.getActiveTasks();
		} catch (Exception e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, tasks,
				"Sucessfully Fetched list of all tasks");

	}

	@RequestMapping(value = "/askQuestion", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> askQuestion(
			@RequestBody Question questionAnswer) throws MessageException {
		// QuestionAnswer questionAnswer;

		if (questionAnswer == null) {
			logger.error("QuestionAnswer Object not provided");
			throw (new MessageException("QuestionAnswer Object not provided"));
		}
		if (questionAnswer.getTaskId() == null) {
			logger.error("Task Object not provided");
			throw (new MessageException("Task Object not provided"));
		}
		try {
			questionAnswer = delegate.askQuestion(questionAnswer);

		} catch (Exception e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, questionAnswer,
				"Sucessfully Fetched list of all tasks");

	}

	@RequestMapping(value = "/editQuestion", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> editQuestion(
			@RequestBody Question questionAnswer) throws MessageException {

		if (questionAnswer == null) {
			logger.error("QuestionAnswer Object not provided");
			throw (new MessageException("QuestionAnswer Object not provided"));
		}
		try {
			questionAnswer = delegate.editQuestion(questionAnswer);
		} catch (Exception e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, questionAnswer,
				"Sucessfully Fetched list of all tasks");
	}

	@RequestMapping(value = "/postAnswer", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> postAnswer(
			@RequestBody Answer answer) throws MessageException {

		if (answer == null) {
			logger.error("Answer Object not provided");
			throw (new MessageException("Answer Object not provided"));
		}
		try {
			answer = delegate.postAnswer(answer);
		} catch (Exception e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, answer,
				"Sucessfully Fetched list of all tasks");
	}

	@RequestMapping(value = "/addPersonality", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> addPersonality(
			@RequestBody Personality personality) {

		if (personality == null) {
			logger.error("Personality can not be null");
			new ResponseDto<Object>(false, "Personality is not save");
		}
		try {
			personality = delegate.savePersonality(personality);
		} catch (Exception exception) {
			logger.error("Exception while saving the personality " + exception);
			return new ResponseDto<Object>(false, exception.getMessage());
		}
		return new ResponseDto<Object>(true, personality, "Sucessfully Fetched");
	}

	@RequestMapping(value = "/uploadPersonalityImage", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> uploadPersonalityImageById(
			@RequestPart(value = "file") MultipartFile image, Long id) {

		try {
			delegate.uploadPersonalityImage(id, image);
			logger.debug("Pesonality with id: " + id + " is uploaded");
			return new ResponseDto<Object>(true,
					"Personality image is successfully uploaded");
		} catch (MessageException exception) {

			logger.error("CustomException while uploading personality image "
					+ exception);
			return new ResponseDto<Object>(false, null, exception.getMessage());

		} catch (Exception exception) {

			logger.error("Exception while uploading personality image "
					+ exception);
			return new ResponseDto<Object>(false);
		}
	}

	@RequestMapping(value = "/getAllPersonality", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllPersonality() {
		List<Personality> personality = null;
		try {
			personality = delegate.getAllPersonality();
			return new ResponseDto<Object>(true, personality,
					"Sucessfully Fetched list of all personalities");
		} catch (Exception exception) {
			logger.error("Exception while fetching all personalities "
					+ exception);
			return new ResponseDto<Object>(false, exception.getMessage());
		}

	}

	/**
	 * web service is to get List of ten
	 * {com.ohmuk.folitics.hibernate.entity.Task} starting from startId
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Task
	 * @param: startId
	 * 
	 * @throws throws MessageException, Exception
	 * 
	 */

	@RequestMapping(value = "/paginationApi", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Task> paginationApi(int start) {

		logger.info("Inside paginationApi  method in fact controller");

		try {

			List<Task> tasks = delegate.paginationApi(start);

			logger.info("Exiting paginationApi method from Task controller");

			return new ResponseDto<Task>(true, tasks,
					"succesfully fethed Facts ");

		} catch (MessageException exception) {

			logger.error("CustomException while fething  Facts", exception);

			return new ResponseDto<Task>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething tasks", exception);

			return new ResponseDto<Task>(exception.getMessage(), false);
		}

	}

	@RequestMapping(value = "/participateStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> participateStatus(Long id,
			Long userId) {

		logger.info("Inside participateStatus method in task controller");

		try {

			if (id == null) {
				logger.error("Null value in parameter 'id'");
				throw (new MessageException("Null value in parameter 'id'"));
			}

			boolean result = delegate.participateStatus(id, userId);

		} catch (MessageException e) {

			logger.error("CustomException while editing the taskparticipant "
					+ e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Exception while editing the taskparticipant " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		return new ResponseDto<>("Status Updated Successfully", true);

	}
}
