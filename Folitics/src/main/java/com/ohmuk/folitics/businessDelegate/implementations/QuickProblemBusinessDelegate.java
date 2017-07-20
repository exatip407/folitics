package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ohmuk.folitics.businessDelegate.interfaces.IQuickProblemBusinessDelegate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickPersonality;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickProblem;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickProblemPersonality;
import com.ohmuk.folitics.service.IQuickProblemService;
import com.ohmuk.folitics.util.DateUtils;

@Component
@Transactional
public class QuickProblemBusinessDelegate implements
		IQuickProblemBusinessDelegate {
	private volatile Logger logger = LoggerFactory
			.getLogger(QuickProblemBusinessDelegate.class);

	@Autowired
	IQuickProblemService service;

	@Override
	public QuickProblem addQuickProblem(QuickProblem quickProblem)
			throws MessageException, Exception {

		logger.info("Inside class: QuickProblemBusinessDelegate method :  addQuickProblem   ");

		if (quickProblem == null) {
			logger.error("Null value in argument not acceptable");
			throw (new MessageException("Null value in argument not acceptable"));
		}

		logger.debug("creating new quickProblem with quickProblem id: "
				+ quickProblem.getId());
		logger.info("Exiting addQuickProblem");

		/*
		 * * Set<QuickProblemParticipant> quickProblemParticipants =
		 * quickProblem.getQuickProblemParticipants(); if
		 * (quickProblemParticipants != null) for (QuickProblemParticipant
		 * QuickProblemParticipant : quickProblemParticipants) {
		 * quickProblemParticipant.setQuickProblem(quickProblem);
		 * usersSet.add(quickProblemParticipant.getUser().getId());
		 * 
		 * }
		 */

		Set<QuickProblemPersonality> quickProblemPersonalitys = quickProblem
				.getQuickProblemPersonality();
		if (quickProblemPersonalitys != null)
			for (QuickProblemPersonality quickProblemPersonality : quickProblemPersonalitys) {
				quickProblemPersonality.setQuickProblem(quickProblem);

			}
		quickProblem.setCreationDate(DateUtils.getSqlTimeStamp());
		quickProblem.setCompletionDate(DateUtils.getSqlTimeStamp());

		// task.setStatus(TaskStatus.CREATED.getValue());
		quickProblem = service.save(quickProblem);
		if (quickProblem != null) {

			/*
			 * * NotificationMapping notificationMapping = new
			 * NotificationMapping();
			 * notificationMapping.setComponentType("quickProblem");
			 * notificationMapping.setSendingUsers(usersSet);
			 * notificationMapping.setComponentId(quickProblem.getId());
			 * notificationMapping
			 * .setUserId(quickProblem.getCreatedBy().getId());
			 * 
			 * notificationService.quickProblemNotification(notificationMapping);
			 */
		}
		System.out.println(quickProblem);
		return quickProblem;
	}

	@Override
	public List<QuickProblem> getAllQuickProblems() throws MessageException,
			Exception {
		logger.info("Inside class: QuickProblemBusinessDelegate method: getAllQuickProblems ");
		logger.info("Fetching List of QuickProblems from DB ");
		logger.info("exiting getAllQuickProblems");

		return service.getAllQuickProblems();
	}

	@Override
	public boolean deleteFromDB(Long id) throws MessageException, Exception {
		logger.info("Inside deleteFromDb method in business delegate");
		boolean success = service.deleteFromDB(id);
		logger.info("Exiting deleteFromDb method from business delegate");
		return success;

	}

	@Override
	public boolean delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete method in business delegate");
		boolean success = service.delete(id);
		logger.info("Exiting delete method from business delegate");
		return success;
	}


	@Override
	public List<QuickProblem> readAll(Long userId) throws MessageException,
			Exception {
		logger.info("Inside readAll quickProblem by userId method in business delegate");
		List<QuickProblem> quickProblems = service.readAll(userId);
		logger.info("Exiting readAll quickProblem by userId method in business delegate");
		return quickProblems;
	}

	@Override
	public List<QuickPersonality> getAllQuickPersonality()
			throws MessageException {
		logger.info("Inside class: QuickProblemBusinessDelegate method: getAllPersonality ");
		List<QuickPersonality> quickpersonalities = service
				.getAllQuickPersonality();
		for (QuickPersonality quickProblem : quickpersonalities) {
			Hibernate.initialize(quickProblem.getQuickPersonality());

		}

		logger.info("Exiting class: QuickProblemBusinessDelegate method: getAllPersonality ");
		return quickpersonalities;
	}

	@Override
	public QuickProblem updateQuickProblem(QuickProblem quickProblem)
			throws MessageException, Exception {
		logger.info("Inside class: QuickProblemBusinessDelegate method :  updateQuickProblem   ");

		if (quickProblem == null) {
			logger.error("Null value in argument not acceptable");
			throw (new MessageException("Null value in argument not acceptable"));
		}

		logger.debug("Updating quickProblem with quickProblem id: "
				+ quickProblem.getId());
		logger.info("Exiting updateQuickProblem");

		quickProblem.setEditDate(DateUtils.getSqlTimeStamp());

		return service.update(quickProblem);
	}

}
