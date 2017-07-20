package com.ohmuk.folitics.businessDelegate.interfaces;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;

/**
 * BusinessDelegate interface for {@link GlobalVerdict}
 * 
 * @author Abhishek
 *
 */
public interface IGlobalVerdictBusinessDelegate {

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
	 * @throws MessageException
	 */
	public GlobalVerdict create(GlobalVerdict globalVerdict)
			throws MessageException;

	/**
	 * This method is for getting the object of {@link GlobalVerdict} from the
	 * database
	 * 
	 * @author Abhishek
	 * @return com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict
	 *         the object of {@link GlobalVerdict} from the database
	 */
	public GlobalVerdict read();

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
	 * @throws MessageException
	 */
	public GlobalVerdict update(GlobalVerdict globalVerdict)
			throws MessageException;

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
	 * @throws MessageException
	 */
	public boolean delete(GlobalVerdict globalVerdict) throws MessageException;

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
	 * @throws MessageException
	 */
	public boolean delete(Long id) throws MessageException;
}
