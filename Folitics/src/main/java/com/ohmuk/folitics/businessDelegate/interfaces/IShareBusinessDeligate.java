package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

public interface IShareBusinessDeligate {

    /**
     * Business delegate method to create an entry for share event in the database
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.beans.AirShareDataBean airShareBean
     * @return java.lang.Object
     * @throws MessageException
     * @throws Exception
     */
    public Object create(AirShareDataBean airShareBean) throws MessageException, Exception;

    /**
     * Business delegate method for getting the Share object from database whose id and component type is equal to the
     * given respective values
     * 
     * @author Abhishek
     * @param java.lang.Long id
     * @param java.lang.String componentType
     * @return java.lang.Object
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public Object read(Long id, String componentType) throws MessageException;

    /**
     * Business delegate method for getting all the objects from database table
     * 
     * @author Abhishek
     * @return java.util.List<java.lang.Object>
     */
    public List<Object> readAll();

    /**
     * Business delegate method to update the Share object in database
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.beans.AirShareDataBean airShareBean
     * @return java.lang.Object
     * @throws com.ohmuk.folitics.exception.MessageException
     * @throws java.lang.Exception
     */
    public Object update(AirShareDataBean airShareBean) throws MessageException, Exception;

    /**
     * Business delegate method for deleting the Share object from database whose id and component type is equal to the
     * given respective values
     * 
     * @author Abhishek
     * @param java.lang.Long id
     * @param java.lang.String componentType
     * @return java.lang.Object
     * @throws com.ohmuk.folitics.exception.MessageException
     * @throws java.lang.Exception
     */
    public Object delete(Long id, String componentType) throws MessageException, Exception;

    /**
     * Business delegate method to check whether the user with id equals to
     * com.ohmuk.folitics.beans.AirShareDataBean.userId has shared the component with id equals to
     * com.ohmuk.folitics.beans.AirShareDataBean.componentId. Returns true if user shared this component else false
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.beans.AirShareDataBean airShareDataBean
     * @return boolean true if component share by user else false
     * @throws com.ohmuk.folitics.exception.MessageException
     * @throws java.lang.Exception
     */
    public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean) throws MessageException, Exception;

    /**
     * Business Delegate method to get the no. of shares on a component
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @return java.lang.Object
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public Object getShareCount(String componentType, Long componentId) throws MessageException;

    /**
     * Business delegate method to get the list of users who shared the component
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.beans.AirShareDataBean airShareDataBean
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.User>
     * @throws com.ohmuk.folitics.exception.MessageException
     * @throws java.lang.Exception
     */
    public List<User> findUserListForComponent(AirShareDataBean airShareDataBean) throws MessageException, Exception;

}
