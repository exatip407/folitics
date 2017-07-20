package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;

/**
 * Service interface for LikeCount
 * @author Abhishek
 *
 */
public interface ILikeCountService<T> {

    /**
     * This method calls the repository save method to save the object t in database
     * 
     * @author Abhishek
     * @param T t
     * @return T
     * @throws MessageException
     */
    public T create(T t) throws MessageException;

    /**
     * This method calls the repository finaAll method to return all the objects of T present in database table
     * 
     * @author Abhishek
     * @return java.util.List<T>
     */
    public List<T> readAll();

    /**
     * This method calls the repository update method to update the object t in database and then return the updated
     * object
     * 
     * @author Abhishek
     * @param T t
     * @return T
     * @throws MessageException
     */
    public T update(T t) throws MessageException;

    /**
     * This method calls the repository delete method to delete the object t from database table
     * 
     * @author Abhishek
     * @param T t
     * @return T
     * @throws MessageException
     */
    public T delete(T t) throws MessageException;

    /**
     * This method finds the object which have component id equals to the given id
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @return T
     * @throws MessageException
     */
    public T getByComponentId(Long componentId) throws MessageException;

}
