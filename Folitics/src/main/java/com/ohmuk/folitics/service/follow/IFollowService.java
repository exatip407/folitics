package com.ohmuk.folitics.service.follow;

import java.util.List;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

public interface IFollowService<T> {

    /**
     * Add new user to follow table or update isFollowing true
     * @param followDataBean
     * @return
     * @throws MessageException
     * @throws Exception
     */
    public T create(FollowDataBean followDataBean) throws MessageException, Exception;

    /**
     * To delete an entry from database
     * @param followDataBean
     * @throws MessageException
     * @throws Exception
     */

    public void delete(FollowDataBean followDataBean) throws MessageException, Exception;

    /**
     * To set isFollowing false in order to unfollow component
     * @param followDataBean
     * @return
     * @throws MessageException
     * @throws Exception
     */
    public T update(FollowDataBean followDataBean) throws MessageException, Exception;

    /**
     * Returns a set of followers for a particular component
     * @param followDataBean
     * @return
     * @throws MessageException
     * @throws Exception
     */
    public List<User> getFollowersForComponent(FollowDataBean followDataBean) throws MessageException, Exception;

    /**
     * Check whether user is following a component or not
     * @param followDataBean
     * @return
     */
    public boolean getFollowing(FollowDataBean followDataBean) throws MessageException, Exception;

}
