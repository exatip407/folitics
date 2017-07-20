package com.ohmuk.folitics.businessDelegate.interfaces;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualification;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSex;

/**
 * BusinessDelegate interface for {@link Verdict} distributions
 * 
 * @author Abhishek
 *
 */
public interface IVerdictDistributionBusinessDelegate {

	/**
	 * BusinessDelegate method to create object in database for distribution
	 * based on Opinion or Response and User
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @param java
	 *            .lang.String
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.user.User
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public Verdict create(Long sentimentId, String flag, User user)
			throws Exception;

	/**
	 * BusinessDelegate method to read object from database for distribution
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long
	 * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
	 */
	public Verdict read(Long id);

	/**
	 * BusinessDelegate method to aggregate the {@link VerdictAgeGroup} objects
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.VerdictAgeGroup the
	 *         aggregated object of {@link VerdictAgeGroup}
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public VerdictAgeGroup aggregatVerdictAgeGroup(Verdict verdict,
			String flag, User user) throws MessageException;

	/**
	 * BusinessDelegate method to aggregate the {@link VerdictSex} objects
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.VerdictSex the aggregated
	 *         object of {@link VerdictSex}
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public VerdictSex aggregatVerdictSex(Verdict verdict, String flag, User user)
			throws MessageException;

	/**
	 * BusinessDelegate method to aggregate the {@link VerdictMaritalStatus}
	 * objects
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.VerdictMaritalStatus the
	 *         aggregated object of {@link VerdictMaritalStatus}
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public VerdictMaritalStatus aggregatVerdictMaritalStatus(Verdict verdict,
			String flag, User user) throws MessageException;

	/**
	 * BusinessDelegate method to aggregate the {@link VerdictRegion} objects
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.VerdictRegion the
	 *         aggregated object of {@link VerdictRegion}
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public VerdictRegion aggregatVerdictRegion(Verdict verdict, String flag,
			User user) throws MessageException;

	/**
	 * BusinessDelegate method to aggregate the {@link VerdictReligion} objects
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.VerdictReligion the
	 *         aggregated object of {@link VerdictReligion}
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public VerdictReligion aggregatVerdictReligion(Verdict verdict,
			String flag, User user) throws MessageException;

	/**
	 * BusinessDelegate method to aggregate the {@link VerdictQualification}
	 * objects
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.jpa.entity.verdict.VerdictQualification the
	 *         aggregated object of {@link VerdictQualification}
	 * @throws com.ohmuk.folitics.exception.MessageException
	 */
	public VerdictQualification aggregatVerdictQualification(Verdict verdict,
			String flag, User user) throws MessageException;

}
