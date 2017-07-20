package com.ohmuk.folitics.businessDelegate.interfaces;

import com.ohmuk.folitics.exception.MessageException;

/**
 * Business Delegate interface for Like
 * @author Abhishek
 *
 */
public interface ILikeBusinessDelegate {

    /**
     * Business Delegate method to get the no. of likes and dislikes on a component with given component type and
     * component id
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long id
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object getLikes(String componentType, Long id) throws MessageException;

    /**
     * Business delegate method to create an entry for like event in the database
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long componentType
     * @param java.lang.Long userId
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object like(Long componentId, String componentType, Long userId) throws MessageException,Exception;

    /**
     * Business delegate method to update the entry of like corresponding to given component id, component type and user
     * id in the database for unlike event
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long componentType
     * @param java.lang.Long userId
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object unlike(Long componentId, String componentType, Long userId) throws MessageException,Exception;

    /**
     * Business delegate method to create an entry for dislike event in the database
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long componentType
     * @param java.lang.Long userId
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object dislike(Long componentId, String componentType, Long userId) throws MessageException,Exception;

    /**
     * Business delegate method to update the entry of dislike corresponding to given component id, component type and
     * user id in the database for undislike event
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long componentType
     * @param java.lang.Long userId
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object undislike(Long componentId, String componentType, Long userId) throws MessageException,Exception;

    /**
     * Business delegate method for getting the Like object from database whose component id, user id and component type
     * is equal to the given respective values
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long componentType
     * @param java.lang.Long userId
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object read(Long componentId, Long userId, String componentType) throws MessageException;

    /**
     * Business delegate method for deleting the Like object from database whose component id, user id and component
     * type is equal to the given respective values
     * 
     * @author Abhishek
     * @param java.lang.Long componentId
     * @param java.lang.Long componentType
     * @param java.lang.Long userId
     * @return java.lang.Object
     * @throws MessageException
     */
    public Object delete(Long componentId, Long userId, String componentType) throws MessageException;

}
