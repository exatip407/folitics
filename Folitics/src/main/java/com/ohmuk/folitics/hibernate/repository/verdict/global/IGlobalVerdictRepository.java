package com.ohmuk.folitics.hibernate.repository.verdict.global;

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;

/**
 * Repository interface for {@link GlobalVerdict}
 * 
 * @author Abhishek
 *
 */
public interface IGlobalVerdictRepository {

	/**
	 * This method is for saving the object of {@link GlobalVerdict} in the
	 * database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *            the object of {@link GlobalVerdict} that is to be saved in the
	 *            database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *         the saved object of {@link GlobalVerdict} from the database
	 */
	public GlobalVerdict save(GlobalVerdict globalVerdict);

	/**
	 * This method is for getting the object of {@link GlobalVerdict} from the
	 * database
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *         the object of {@link GlobalVerdict} from the database
	 */
	public GlobalVerdict find();

	/**
	 * This method is for updating the object of {@link GlobalVerdict} in the
	 * database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *            the object of {@link GlobalVerdict} that is to be updated in
	 *            the database
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *         the updated object of {@link GlobalVerdict} from database
	 */
	public GlobalVerdict update(GlobalVerdict globalVerdict);

	/**
	 * This method is for deleting the object of {@link GlobalVerdict} from the
	 * database
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *            the object of {@link GlobalVerdict} that is to be deleted from
	 *            database
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 */
	public boolean delete(GlobalVerdict globalVerdict);

	/**
	 * This method is for deleting the object of {@link GlobalVerdict} with
	 * given id from database
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id of the object that is to be deleted from the
	 *            database
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 */
	public boolean delete(Long id);
}
