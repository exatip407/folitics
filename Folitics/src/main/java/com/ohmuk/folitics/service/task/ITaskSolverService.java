package com.ohmuk.folitics.service.task;


import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskSolver;

public interface ITaskSolverService {


public TaskSolver getSolverforUser(Task task, User user ) throws MessageException,
		Exception;

}
