package com.ohmuk.folitics.hibernate.repository.verdict.global;

import java.util.List;

/**
 * Repository interface for {@link GlobalVerdict} distributions
 * 
 * @author Abhishek
 *
 * @param <T>
 *            Distribution object
 * @param <U>
 *            Id
 */
public interface IGlobalVerdictDistributionRepository<T, U> {

	/**
	 * This method is for saving the object of distribution in the database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t the object that is to be saved in the database
	 * @return T the saved object from database
	 */
	public T save(T t);

	/**
	 * This method is for getting the object of distribution from the database
	 * with given id
	 * 
	 * @author Abhishek
	 * @param U
	 *            u id of the object
	 * @return T object of distribution found in database for given id
	 */
	public T find(U u);

	/**
	 * This method is for getting all the objects of distribution from database
	 * 
	 * @author Abhishek
	 * @return java.util.List<T>
	 */
	public List<T> findAll();

	/**
	 * This method is for updating the object of distribution in the database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t the object that is to be updated in the database
	 * @return T the updated object of distribution from database
	 */
	public T update(T t);

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
	 */
	public boolean deleteById(U u);

	/**
	 * This method is for deleting the object of distribution from database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t the object of distribution that is to be deleted from
	 *            database
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 */
	public boolean delete(T t);
}
