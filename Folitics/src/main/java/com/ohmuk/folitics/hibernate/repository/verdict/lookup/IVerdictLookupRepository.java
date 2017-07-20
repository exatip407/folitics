package com.ohmuk.folitics.hibernate.repository.verdict.lookup;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Hibernate repository interface for {@link Verdict} lookup entities
 * @author Abhishek
 *
 */
public interface IVerdictLookupRepository<T> {

    /**
     * Method to save distribution lookup object in database
     * 
     * @author Abhishek
     * @param T the object that you want to save in the database
     * @return T saved object from database
     */
    public T save(T t);

    /**
     * Method to get distribution lookup object from database for given id
     * 
     * @author Abhishek
     * @param java.lang.Long id of the object that you want to search in the database
     * @return T object found in database
     */
    public T find(Long id);

    /**
     * Method to get all objects from database
     * 
     * @author Abhishek
     * @return java.util.List<T> list of all the objects from database
     */
    public List<T> findAll();

    /**
     * @param Object value that you want to search in the database
     * @return T the object found in the database for given value
     */
    public T findByValue(Object value);

    /**
     * Method to update lookup object in database
     * 
     * @author Abhishek
     * @param T
     * @return T
     */
    public T update(T t);

    /**
     * Method to delete lookup object in database for given id
     * 
     * @author Abhishek
     * @param java.lang.Long
     */
    public void delete(Long id);

}
