package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

/**
 * @author Sarvesh
 *
 */
public interface IFollowBusinessDelegate {

	public static final String FOLLOW_SUFFIXs = "FollowService";

	/**
	 * @param followDataBean
	 * @return
	 * @throws MessageException
	 * @throws Exception
	 */
	public Object followComponent(FollowDataBean followDataBean) throws MessageException, Exception;

	/**
	 * @param followDataBean
	 * @return
	 * @throws MessageException
	 * @throws Exception
	 */
	public Object unfollowComponent(FollowDataBean followDataBean) throws MessageException, Exception;

	/**
	 * @param followDataBean
	 * @return
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<User> getFollowersSet(FollowDataBean followDataBean) throws MessageException, Exception;

	/**
	 * @param followDataBean
	 * @return
	 * @throws MessageException
	 * @throws Exception
	 */
	public Long getFollowCount(FollowDataBean followDataBean) throws MessageException, Exception;

	/**
	 * @param followDataBean
	 * @return
	 * @throws MessageException
	 * @throws Exception
	 */
	public boolean isFollowing(FollowDataBean followDataBean) throws MessageException, Exception;

}
