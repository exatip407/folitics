package com.ohmuk.folitics.businessDelegate.interfaces;

import com.ohmuk.folitics.hibernate.entity.User;

/**
 * BusinessDelegate interface for Global Verdict Distributions
 * 
 * @author Abhishek
 *
 */
public interface IGlobalVerdictDistributionBusinessDelegate {

	/**
	 * This method is for aggregating the counts for Global Verdict
	 * 
	 * @author Abhishek
	 * @param String
	 *            opinionType
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.User user
	 * @throws Exception
	 */
	public void aggregateGlobalVerdictDistribution(String opinionType, User user) throws Exception;
}
