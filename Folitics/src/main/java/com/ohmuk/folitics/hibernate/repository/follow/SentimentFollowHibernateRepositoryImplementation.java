package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.businessDelegate.implementations.UserMonetizationBussinessDeligate;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.follow.FollowId;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollow;

/**
 * @author
 *
 */
@Component
@Repository
public class SentimentFollowHibernateRepositoryImplementation implements
		ISentimentFollowHibernateRepository {

	private Logger logger = LoggerFactory
			.getLogger(SentimentFollowHibernateRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

/*	@Autowired
	private IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;*/

	private Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.follow.IFollowHibernateRepository
	 * #save(java.lang.Object)
	 */
	@Override
	public SentimentFollow save(SentimentFollow follow)
			throws MessageException, Exception {

		logger.info("Inside save FollowHibernate");

		FollowId followid = (FollowId) getSession().save(follow);

		if (followid == null) {

			logger.error("Problem inserting value");
			throw (new MessageException("Problem inserting value"));

		}

		follow = (SentimentFollow) getSession().get(SentimentFollow.class,
				followid);
		logger.info("Exiting save FollowHibernate");
		return follow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.follow.IFollowHibernateRepository
	 * #delete(java.lang.Object)
	 */
	@Override
	public void delete(SentimentFollow follow) {

		logger.info("Deleting user from follow table");
		getSession().delete(follow);
		logger.info("User deleted");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.follow.IFollowHibernateRepository
	 * #findByComponentIdAndUserId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public SentimentFollow findByComponentIdAndUserId(Long componentId,
			Long userId) {

		logger.info("Inside findByComponentIdAndUserId");
		logger.debug("finding a unique element by componentId " + componentId
				+ " and userId " + userId);

		Criteria criteria = getSession().createCriteria(SentimentFollow.class);

		SimpleExpression exp1 = Restrictions.eq("followId.componentId",
				componentId);
		SimpleExpression exp2 = Restrictions.eq("followId.userId", userId);
		LogicalExpression exp3 = Restrictions.and(exp1, exp2);

		criteria.add(exp3);

		SentimentFollow follow = (SentimentFollow) criteria.uniqueResult();

		if (follow == null) {

			logger.info("No result found");

		}

		else {

			logger.info("One row found");

		}

		return follow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.follow.IFollowHibernateRepository
	 * #update(java.lang.Object)
	 */
	@Override
	public SentimentFollow update(SentimentFollow follow)
			throws MessageException, Exception {

		logger.info("Inside update FollowHibernate ");

		follow = (SentimentFollow) getSession().merge(follow);
		getSession().update(follow);

		follow = (SentimentFollow) getSession().get(SentimentFollow.class,
				follow.getFollowId());

		if (follow == null) {

			logger.error("Error updating follower");
			throw (new MessageException("Error updating follower"));

		}

		return follow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.follow.IFollowHibernateRepository
	 * #findByComponentIdUserIdAndIsFollowing(com.ohmuk.folitics.beans.
	 * FollowDataBean)
	 */
	@Override
	public boolean findByComponentIdUserIdAndIsFollowing(
			FollowDataBean followDataBean) {

		logger.info("Inside findByComponentIdAndIsFollowing : SentimentFollowHibernateImplementation");
		logger.info("Searching User...");

		Criteria criteria = getSession().createCriteria(SentimentFollow.class);
		SimpleExpression expression1 = Restrictions.eq("followId.componentId",
				followDataBean.getComponentId());
		SimpleExpression expression2 = Restrictions.eq("followId.userId",
				followDataBean.getUserId());
		SentimentFollow sentimentFollow = (SentimentFollow) criteria.add(
				Restrictions.and(expression1, expression2)).uniqueResult();
		if (sentimentFollow == null) {
			logger.info("No user found");
		} else if (sentimentFollow.isFollowing()) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.follow.IFollowHibernateRepository
	 * #findByComponentIdAndIsFollowing(java.lang.Long, boolean)
	 */
	@Override
	public List<SentimentFollow> findByComponentIdAndIsFollowing(
			Long componentId, boolean isFollowing) {

		Criteria criteria = getSession().createCriteria(SentimentFollow.class);
		SimpleExpression exp1 = Restrictions.eq("followId.componentId",
				componentId);
		SimpleExpression exp2 = Restrictions.eq("isFollowing", isFollowing);
		LogicalExpression exp3 = Restrictions.and(exp1, exp2);
		criteria.add(exp3);
		@SuppressWarnings("unchecked")
		List<SentimentFollow> followList = criteria.list();

		return followList;
	}

	@Override
	public void addMonetizationPoints(FollowDataBean followDataBean,
			String action) throws Exception {

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				followDataBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ followDataBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ followDataBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(followDataBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(followDataBean.getUserId());
		userMonetization.setActionComponentId(followDataBean.getComponentId());

		/*userMonetizationBussinessDeligate.addAction(userMonetization);*/

	}

}
