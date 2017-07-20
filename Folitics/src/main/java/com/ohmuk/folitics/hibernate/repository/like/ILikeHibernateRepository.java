package com.ohmuk.folitics.hibernate.repository.like;

import java.util.List;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Hibernate repository interface for like
 * @author Abhishek
 *
 * @param <T>
 */
public interface ILikeHibernateRepository<T> {

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
     * This method will find the record in database by the given id
     * 
     * @param com.ohmuk.folitics.jpa.entity.like.LikeId id
     * @return T
     */
    public T find(LikeId id);

    /**
     * This method will return all the object present in table and will return them in list
     * 
     * @author Abhishek
     * @return java.util.List<T>
     */
    public List<T> findAll();

    /**
     * This method will delete the record from database where id is equals to the given id
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.like.LikeId id
     */
    public void delete(LikeId id);

    /**
     * This method will find that one record where component id and user id is equal to the given component id and user
     * id respectively
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return T
     */
    public T getByComponentIdAndUserId(Long componentId, Long userId);
    
    /**
     * This method will added user points, after like on any component
     * 
     * @author Harish
     * @param javacom.ohmuk.folitics.beans.LikeDataBean
     * 
     * @return void 
     */
    
	public void addMonetizationPoints(LikeDataBean likeDataBean ,String action) throws Exception;
    
    


}
