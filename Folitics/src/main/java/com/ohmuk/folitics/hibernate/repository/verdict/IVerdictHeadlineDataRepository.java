package com.ohmuk.folitics.hibernate.repository.verdict;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;

/**
 * Repository interface for {@link VerdictHeadlineData}
 * 
 * @author Abhishek
 *
 */
public interface IVerdictHeadlineDataRepository {

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
	 *         the saved object of {@link VerdictHeadlineData} from database
	 */
	public VerdictHeadlineData save(VerdictHeadlineData verdictHeadlineData);

	/**
	 * This method is for getting the object of {@link VerdictHeadlineData} with
	 * given id from database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id of the object of {@link VerdictHeadlineData}
	 *            that is to be queried from database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *         the object of {@link VerdictHeadlineData} found in database for
	 *         given id
	 */
	public VerdictHeadlineData find(Long id);

	/**
	 * This method is for getting the object of {@link VerdictHeadlineData} from
	 * database for given verdict and parameters
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *            the object of {@link VerdictHeadlineData} that is to be
	 *            searched in database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *         the object of {@link VerdictHeadlineData} found in database else
	 *         null
	 */
	public VerdictHeadlineData findByVerdictAndParameters(
			VerdictHeadlineData verdictHeadlineData);

	/**
	 * This method is for getting objects of {@link VerdictHeadlineData} for
	 * given {@link Verdict}
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.Verdict
	 * @return java.util.List<com.ohmuk.folitics.hibernate.entity.verdict.
	 *         VerdictHeadlineData> the list of all the objects of
	 *         {@link VerdictHeadlineData} from database for given
	 *         {@link Verdict}
	 */
	public List<VerdictHeadlineData> findByVerdict(Verdict verdict);

	/**
	 * This method is for getting all the objects of {@link VerdictHeadlineData}
	 * from database
	 * 
	 * @author Abhishek
	 * @return java.util.List<com.ohmuk.folitics.hibernate.entity.verdict.
	 *         VerdictHeadlineData> the list of all the objects of
	 *         {@link VerdictHeadlineData} from database
	 */
	public List<VerdictHeadlineData> findAll();

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
	 */
	public VerdictHeadlineData update(VerdictHeadlineData verdictHeadlineData);

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
	 */
	public boolean delete(Long id);

	/**
	 * This method is for deleting the object of {@link VerdictHeadlineData}
	 * from database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData
	 *            the object of {@link VerdictHeadlineData} that is to be
	 *            deleted from database
	 * @return boolean true if object of {@link VerdictHeadlineData} is
	 *         successfully deleted from database else false
	 */
	public boolean delete(VerdictHeadlineData verdictHeadlineData);
}
