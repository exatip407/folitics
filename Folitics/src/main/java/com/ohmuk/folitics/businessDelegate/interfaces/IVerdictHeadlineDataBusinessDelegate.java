package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;

/**
 * BusinessDelegate interface for {@link VerdictHeadlineData}
 * 
 * @author Abhishek
 *
 */
public interface IVerdictHeadlineDataBusinessDelegate {

	/**
	 * This method is for saving the object of {@link VerdictHeadlineData} in
	 * the database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *            the object of {@link VerdictHeadlineData} that is to be saved
	 *            in the database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *         the saved object of {@link VerdictHeadlineData} from the database
	 * @throws MessageException
	 */
	public VerdictHeadlineData create(VerdictHeadlineData verdictHeadlineData)
			throws MessageException;

	/**
	 * This method is for getting the object of {@link VerdictHeadlineData} for
	 * given id from database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id of the object of {@link VerdictHeadlineData}
	 *            that is to be queried in database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *         the object of {@link VerdictHeadlineData} found in database for
	 *         given id
	 * @throws MessageException
	 */
	public VerdictHeadlineData read(Long id) throws MessageException;

	/**
	 * This method is for getting the object of {@link VerdictHeadlineData} for
	 * given verdict and parameters value
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *            the object of {@link VerdictHeadlineData} that is to be
	 *            searched in database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *         the object of {@link VerdictHeadlineData} found in database else
	 *         null
	 * @throws MessageException
	 */
	public VerdictHeadlineData readVerdictHeadlineDataForParameters(
			VerdictHeadlineData verdictHeadlineData) throws MessageException;

	/**
	 * This method is for getting the objects of {@link VerdictHeadlineData} for
	 * the given {@link Verdict}
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.Verdict
	 * @return java.util.List<com.ohmuk.folitics.hibernate.entity.verdict.
	 *         VerdictHeadlineData> the list of objects of
	 *         {@link VerdictHeadlineData} from database for given
	 *         {@link Verdict}
	 */
	public List<VerdictHeadlineData> readVerdictHeadlineDataForVerdict(
			Verdict verdict);

	/**
	 * This method is for getting all the objects of {@link VerdictHeadlineData}
	 * from the database
	 * 
	 * @author Abhishek
	 * @return java.util.List<com.ohmuk.folitics.hibernate.entity.verdict.
	 *         VerdictHeadlineData> the list of all the objects of
	 *         {@link VerdictHeadlineData} from the database
	 */
	public List<VerdictHeadlineData> readAll();

	/**
	 * This method is for updating the object of {@link VerdictHeadlineData} in
	 * the database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *            the object of {@link VerdictHeadlineData} that is to be
	 *            updated in the database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *         the updated object of {@link VerdictHeadlineData} from the
	 *         database
	 * @throws MessageException
	 */
	public VerdictHeadlineData update(VerdictHeadlineData verdictHeadlineData)
			throws MessageException;

	/**
	 * This method is for deleting the object of {@link VerdictHeadlineData}
	 * with given id from the database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id of the object of {@link VerdictHeadlineData}
	 *            that is to be deleted from database
	 * @return boolean true if object of {@link VerdictHeadlineData} is
	 *         successfully deleted from database else false
	 * @throws MessageException
	 */
	public boolean delete(Long id) throws MessageException;

	/**
	 * This method is for deleting the given object of
	 * {@link VerdictHeadlineData} from the database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *            the object of {@link VerdictHeadlineData} that is to be
	 *            deleted from the database
	 * @return boolean true if object of {@link VerdictHeadlineData} is
	 *         successfully deleted from database else false
	 * @throws MessageException
	 */
	public boolean delete(VerdictHeadlineData verdictHeadlineData)
			throws MessageException;
}
