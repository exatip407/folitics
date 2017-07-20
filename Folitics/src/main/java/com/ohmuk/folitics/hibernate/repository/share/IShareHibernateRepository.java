package com.ohmuk.folitics.hibernate.repository.share;

import java.util.List;
import java.util.Set;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.beans.LikeDataBean;

/**
 * Repository interface for share
 * @author Abhishek
 *
 * @param <T>
 */
public interface IShareHibernateRepository<T> {

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
     * @author Abhishek
     * @param java.lang.Long id
     * @return T
     */
    public T find(Long id);

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
     * @param java.lang.Long id
     */
    public void delete(Long id);

    /**
     * This method will find that one record where component id is equal to the given component id
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @return java.util.Set<T>
     */
    public Set<T> getSharesByComponentId(Long componentId);

    /**
     * This method will find that one record where component id and user id is equal to the given component id and user
     * id respectively
     * 
     * @author Abhishek
     * @param java.lang.Long userId
     * @param java.lang.Long componentId
     * @return java.util.Set<T>
     */
    public Set<T> getSharesByUserIdAndComponentId(Long userId, Long componentId);
    
    /**
     * This method will added user points, after share on any component
     * 
     * @author Harish
     * @param javacom.ohmuk.folitics.beans.LikeDataBean
     * 
     * @return void 
     */
    
    public void addMonetizationPoints(AirShareDataBean airShareDataBean ,String action) throws Exception;

}
