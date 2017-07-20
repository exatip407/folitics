package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickPersonality;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickProblem;

public interface IQuickProblemService {

	public QuickProblem save(QuickProblem quickProblem)
			throws MessageException, Exception;

	public List<QuickProblem> getAllQuickProblems() throws MessageException,
			Exception;

	public boolean deleteFromDB(Long id) throws MessageException, Exception;

	public boolean delete(Long id) throws MessageException, Exception;

	public QuickProblem update(QuickProblem quickProblem)
			throws MessageException, Exception;

	public List<QuickProblem> readAll(Long userId) throws MessageException,
			Exception;

	public List<QuickPersonality> getAllQuickPersonality() throws
	MessageException;

}
