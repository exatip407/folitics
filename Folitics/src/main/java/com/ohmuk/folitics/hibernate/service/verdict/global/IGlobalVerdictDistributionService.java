package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;

/**
 * Service interface for distribution on {@link GlobalVerdict}
 * 
 * @author Abhishek
 *
 * @param <T>
 *            Entity for distribution
 * @param <U>
 *            Id of entity
 */
public interface IGlobalVerdictDistributionService<T, U> {

	/**
	 * This method is for saving the object of distribution in the database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t the object that is to be saved in the database
	 * @return T the saved object from database
	 * @throws MessageException
	 */
	public T create(T t) throws MessageException;

	/**
	 * This method is for getting the object of distribution from the database
	 * with given id
	 * 
	 * @author Abhishek
	 * @param U
	 *            u id of the object
	 * @return T object of distribution found in database for given id
	 * @throws MessageException
	 */
	public T read(U u) throws MessageException;

	/**
	 * This method is for getting all the objects of distribution from database
	 * 
	 * @author Abhishek
	 * @return java.util.List<T>
	 */
	public List<T> readAll();

	/**
	 * This method is for updating the object of distribution in the database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t the object that is to be updated in the database
	 * @return T the updated object of distribution from database
	 * @throws MessageException
	 */
	public T update(T t) throws MessageException;

	/**
	 * This method is for deleting the object of distribution from database by
	 * id
	 * 
	 * @author Abhishek
	 * @param U
	 *            u id of distribution object that is to be deleted from
	 *            database
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 * @throws MessageException
	 */
	public boolean deleteById(U u) throws MessageException;

	/**
	 * This method is for deleting the object of distribution from database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t the object of distribution that is to be deleted from
	 *            database
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 * @throws MessageException
	 */
	public boolean delete(T t) throws MessageException;
}
