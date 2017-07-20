package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Service interface for {@link Verdict} lookup entities
 * @author Abhishek
 *
 */
public interface IVerdictLookupService<T> {

    /**
     * This method is for creating the object of T in database
     * 
     * @author Abhishek
     * @param T object of T that is to be saved in database
     * @return T saved object instance
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T create(T t) throws MessageException;

    /**
     * This method is for getting the object of T from database for the given id
     * 
     * @author Abhishek
     * @param java.lang.Long the id that is to be searched in the database table
     * @return T object found in database
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T read(Long id) throws MessageException;

    /**
     * This method is for getting for all the objects from database
     * 
     * @author Abhishek
     * @return java.util.List<T> objects found from database
     */
    public List<T> readAll();

    /**
     * This method is for getting object which has value equal to given value
     * 
     * @author Abhishek
     * @param Object the value which will be matched in the database
     * @return T object found in database for given value
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T readByValue(Object value) throws MessageException;

    /**
     * This method is for updating the object in the database
     * 
     * @author Abhishek
     * @param T object that is to be updated in the database
     * @return T updated object from database
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T update(T t) throws MessageException;

    /**
     * This method is for deleting object from database by id
     * 
     * @author Abhishek
     * @param java.lang.Long id of the object that you want to delete from database
     * @return T the object which is deleted. It will always be null
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T delete(Long id) throws MessageException;

    /**
     * This method is for deleting object from database by passing the same object
     * 
     * @author Abhishek
     * @param T the object which is to be deleted from database
     * @return T the object which is deleted. It will always be null
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T delete(T t) throws MessageException;

}
