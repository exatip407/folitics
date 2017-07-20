package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.ohmuk.folitics.hibernate.entity.follow.GovtSchemeDataFollow;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Repository implementation for entity: {@link GovtSchemeDataFollow}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class GovtSchemeDataFollowRepositoryImplementation implements
		IGovtSchemeDataFollowRepository {

	private Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataFollowRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	@Override
	public GovtSchemeDataFollow save(GovtSchemeDataFollow govtSchemeDataFollow)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataFollow save method");
		logger.debug("Saving follow for govtSchemeData id = "
				+ govtSchemeDataFollow.getFollowId().getComponentId()
				+ " and user id = "
				+ govtSchemeDataFollow.getFollowId().getUserId());

		LikeId id = (LikeId) getSession().save(govtSchemeDataFollow);
		govtSchemeDataFollow = (GovtSchemeDataFollow) getSession().get(
				GovtSchemeDataFollow.class, id);

		logger.info("GovtSchemeDataFollow saved. Exiting save method");
		return govtSchemeDataFollow;
	}

	@Override
	public void delete(GovtSchemeDataFollow govtSchemeDataFollow)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataFollow delete method");

		getSession().delete(govtSchemeDataFollow);
		logger.info("Deleted GovtSchemeDataFollow. Exiting delete method");

	}

	@Override
	public GovtSchemeDataFollow findByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataFollow getByComponentIdAndUserId method");
		logger.debug("Getting follow for govtSchemeData id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				GovtSchemeDataFollow.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		GovtSchemeDataFollow govtSchemeDataFollow = (GovtSchemeDataFollow) selectCriteria
				.uniqueResult();

		logger.info("Returning GovtSchemeDataFollow. Exiting getByComponentIdAndUserId method");
		return govtSchemeDataFollow;
	}

	@Override
	public GovtSchemeDataFollow update(GovtSchemeDataFollow govtSchemeDataFollow)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataFollow update method");
		logger.debug("Updating follow for govtSchemeData id = "
				+ govtSchemeDataFollow.getFollowId().getComponentId()
				+ " and user id = "
				+ govtSchemeDataFollow.getFollowId().getUserId());

		govtSchemeDataFollow = (GovtSchemeDataFollow) getSession().merge(
				govtSchemeDataFollow);
		getSession().update(govtSchemeDataFollow);

		govtSchemeDataFollow = (GovtSchemeDataFollow) getSession().get(
				GovtSchemeDataFollow.class, govtSchemeDataFollow.getFollowId());

		logger.info("Updated GovtSchemeDataFollow. Exiting update method");
		return govtSchemeDataFollow;
	}

	@Override
	public List<GovtSchemeDataFollow> findByComponentIdAndIsFollowing(
			Long componentId, boolean isFollowing) {
		logger.info("Entered GovtSchemeDataFollow getByComponentIdAndUserId method");
		logger.debug("Getting follow for govtSchemeData id = " + componentId);

		Criteria criteria = getSession().createCriteria(
				GovtSchemeDataFollow.class);
		criteria.add(Restrictions.eqOrIsNull("followId.componentId",
				componentId));
		criteria.add(Restrictions.eqOrIsNull("isFollowing", isFollowing));

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataFollow> followList = criteria.list();
		logger.info("Returning GovtSchemeDataFollow. Exiting getByComponentIdAndUserId method");
		return followList;
	}

	@Override
	public boolean findByComponentIdUserIdAndIsFollowing(
			FollowDataBean followDataBean) {
		logger.info("Entered GovtSchemeDataFollow findByComponentIdUserIdAndIsFollowing method");
		logger.info("Searching User...");

		Criteria criteria = getSession().createCriteria(
				GovtSchemeDataFollow.class);

		SimpleExpression expression1 = Restrictions.eq("followId.componentId",
				followDataBean.getComponentId());

		SimpleExpression expression2 = Restrictions.eq("followId.userId",
				followDataBean.getUserId());

		GovtSchemeDataFollow govtSchemeDataFollow = (GovtSchemeDataFollow) criteria
				.add(Restrictions.and(expression1, expression2)).uniqueResult();

		if (govtSchemeDataFollow == null) {

			logger.info("No user found");

		} else if (govtSchemeDataFollow.isFollowing()) {
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
