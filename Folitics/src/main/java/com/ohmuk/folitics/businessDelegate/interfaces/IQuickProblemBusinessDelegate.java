package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickPersonality;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickProblem;

public interface IQuickProblemBusinessDelegate {

	public QuickProblem addQuickProblem(QuickProblem quickProblem)
			throws MessageException, Exception;

	public List<QuickProblem> getAllQuickProblems() throws MessageException,
			Exception;

	public boolean deleteFromDB(Long id) throws MessageException, Exception;

	public boolean delete(Long id) throws MessageException, Exception;

	public List<QuickProblem> readAll(Long userId) throws MessageException,
			Exception;

	public List<QuickPersonality> getAllQuickPersonality()
			throws MessageException;

	public QuickProblem updateQuickProblem(QuickProblem quickProblem)
			throws MessageException, Exception;

}
