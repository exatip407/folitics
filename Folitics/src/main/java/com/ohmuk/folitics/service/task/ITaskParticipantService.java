package com.ohmuk.folitics.service.task;



import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskParticipant;

public interface ITaskParticipantService {


public TaskParticipant getParticipantForUser(Task task, User user)
		throws MessageException, Exception;

}
