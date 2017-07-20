package com.ohmuk.folitics.hibernate.repository.like;

import java.util.List;

/**
 * Hibernate repository interface for Like Count
 * @author Abhishek
 *
 * @param <T>
 */
public interface ILikeCountHibernateRepository<T> {

    /**
     * This method will save the object t in the database and then will return the object
     * 
     * @author Abhishek
     * @param T t
     * @return T
     */
    public T save(T t);

    /**
     * This method will update the given object in the database and then will return the updated object
     * 
     * @author Abhishek
     * @param T t
     * @return T
     */
    public T update(T t);

    /**
     * This method will return all the object present in table and will return them in list
     * 
     * @author Abhishek
     * @return java.util.List<T>
     */
    public List<T> findAll();

    /**
     * This method is for finding row with component id equals to given id
     * 
     * @author Abhishek
     * @param Long id
     * @return T
     */
    public T findByComponentId(Long id);

}
