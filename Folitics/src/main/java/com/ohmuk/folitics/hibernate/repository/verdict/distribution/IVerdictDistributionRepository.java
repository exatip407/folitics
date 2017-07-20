package com.ohmuk.folitics.hibernate.repository.verdict.distribution;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Hibernate repository interface for {@link Verdict} distributions
 * 
 * @author Abhishek
 *
 * @param <T>
 */
public interface IVerdictDistributionRepository<T, U> {

	/**
	 * Method to save distribution object in database
	 * 
	 * @author Abhishek
	 * @param T
	 * @return T
	 */
	public T save(T t);

	/**
	 * Method to get distribution object from database for given id
	 * 
	 * @author Abhishek
	 * @param U
	 * @return T
	 */
	public T find(U u);

	/**
	 * Method to get verdict distributions for verdict id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long verdictId
	 * @return java.util.List<T>
	 */
	public List<T> findByVerdictId(Long verdictId);

	/**
	 * Method to get all objects from database
	 * 
	 * @author Abhishek
	 * @return java.util.List<T>
	 */
	public List<T> findAll();

	/**
	 * Method to update object in database
	 * 
	 * @author Abhishek
	 * @param T
	 * @return T
	 */
	public T update(T t);

	/**
	 * Method to delete object in database for given id
	 * 
	 * @author Abhishek
	 * @param U
	 */
	public void delete(U u);

}
