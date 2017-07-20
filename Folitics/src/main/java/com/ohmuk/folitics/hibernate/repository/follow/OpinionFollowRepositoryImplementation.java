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
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.follow.FollowId;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollow;

@Component
@Repository
public class OpinionFollowRepositoryImplementation implements
		IOpinionFollowRepository {

	private Logger logger = LoggerFactory
			.getLogger(OpinionFollowRepositoryImplementation.class);

	@Autowired
	private IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionFollow save(OpinionFollow opinionFollow)
			throws MessageException, Exception {
		logger.info("Inside save method");

		FollowId followid = (FollowId) getSession().save(opinionFollow);

		if (followid == null) {

			logger.error("Problem inserting value");
			throw (new MessageException("Problem inserting value"));

		}

		opinionFollow = (OpinionFollow) getSession().get(OpinionFollow.class,
				followid);
		logger.info("Exiting save method");
		return opinionFollow;
	}

	@Override
	public void delete(OpinionFollow opinionFollow) throws MessageException,
			Exception {
		logger.info("Deleting user from opinionFollow table");
		getSession().delete(opinionFollow);
		logger.info("User deleted");

	}

	@Override
	public OpinionFollow findByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException, Exception {
		logger.info("Inside findByComponentIdAndUserId");
		logger.debug("finding a unique element by componentId " + componentId
				+ " and userId " + userId);

		Criteria criteria = getSession().createCriteria(OpinionFollow.class);

		SimpleExpression exp1 = Restrictions.eq("followId.componentId",
				componentId);
		SimpleExpression exp2 = Restrictions.eq("followId.userId", userId);
		LogicalExpression exp3 = Restrictions.and(exp1, exp2);

		criteria.add(exp3);

		OpinionFollow opinionFollow = (OpinionFollow) criteria.uniqueResult();

		if (opinionFollow == null) {

			logger.info("No result found");

		}

		else {

			logger.info("One row found");

		}

		return opinionFollow;
	}

	@Override
	public OpinionFollow update(OpinionFollow opinionFollow)
			throws MessageException, Exception {

		logger.info("Inside update FollowHibernate ");

		opinionFollow = (OpinionFollow) getSession().merge(opinionFollow);
		getSession().update(opinionFollow);

		opinionFollow = (OpinionFollow) getSession().get(OpinionFollow.class,
				opinionFollow.getFollowId());

		if (opinionFollow == null) {

			logger.error("Error updating follower");
			throw (new MessageException("Error updating follower"));

		}

		return opinionFollow;
	}

	@Override
	public List<OpinionFollow> findByComponentIdAndIsFollowing(
			Long componentId, boolean isFollowing) {

		Criteria criteria = getSession().createCriteria(OpinionFollow.class);
		SimpleExpression exp1 = Restrictions.eq("followId.componentId",
				componentId);
		SimpleExpression exp2 = Restrictions.eq("isFollowing", isFollowing);
		LogicalExpression exp3 = Restrictions.and(exp1, exp2);
		criteria.add(exp3);
		@SuppressWarnings("unchecked")
		List<OpinionFollow> followList = criteria.list();

		return followList;
	}

	@Override
	public boolean findByComponentIdUserIdAndIsFollowing(
			FollowDataBean followDataBean) {

		logger.info("Inside findByComponentIdAndIsFollowing : SentimentFollowHibernateImplementation");
		logger.info("Searching User...");

		Criteria criteria = getSession().createCriteria(OpinionFollow.class);
		SimpleExpression expression1 = Restrictions.eq("followId.componentId",
				followDataBean.getComponentId());
		SimpleExpression expression2 = Restrictions.eq("followId.userId",
				followDataBean.getUserId());
		OpinionFollow opinionFollow = (OpinionFollow) criteria.add(
				Restrictions.and(expression1, expression2)).uniqueResult();
		if (opinionFollow == null) {
			logger.info("No user found");
		} else if (opinionFollow.isFollowing()) {
			return true;
		}
		return false;
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

		userMonetizationBussinessDeligate.addAction(userMonetization);
		
	}

}
