package com.ohmuk.folitics.hibernate.repository.verdict.distribution;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroupId;

/**
 * Repository implementation for {@link VerdictAgeGroup}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictAgeGroupRepository implements
		IVerdictDistributionRepository<VerdictAgeGroup, VerdictAgeGroupId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictAgeGroupRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#save(java.lang.Object)
	 */
	@Override
	public VerdictAgeGroup save(VerdictAgeGroup verdictAgeGroup) {

		logger.debug("Entered VerdictAgeGroupRepository save method");
		logger.debug("Trying to save VerdictAgeGroup for verdict id = "
				+ verdictAgeGroup.getId().getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroup.getId().getAgeGroup().getId());

		VerdictAgeGroupId verdictAgeGroupId = (VerdictAgeGroupId) getSession()
				.save(verdictAgeGroup);

		logger.debug("Saved VerdictAgeGroup object and now getting VerdictAgeGroup object from database");

		verdictAgeGroup = (VerdictAgeGroup) getSession().get(
				VerdictAgeGroup.class, verdictAgeGroupId);

		logger.debug("Got VerdictAgeGroup object from database. Exiting VerdictAgeGroupRepository save method");

		return verdictAgeGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#find(java.lang.Object)
	 */
	@Override
	public VerdictAgeGroup find(VerdictAgeGroupId verdictAgeGroupId) {

		logger.debug("Entered VerdictAgeGroupRepository find method");
		logger.debug("Trying to get VerdictAgeGroup for verdict id = "
				+ verdictAgeGroupId.getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroupId.getAgeGroup().getId());

		VerdictAgeGroup verdictAgeGroup = (VerdictAgeGroup) getSession().get(
				VerdictAgeGroup.class, verdictAgeGroupId);

		logger.debug("Got VerdictAgeGroup object from database. Exiting VerdictAgeGroupRepository find method");

		return verdictAgeGroup;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictAgeGroup> findByVerdictId(Long verdictId) {

		logger.debug("Entered VerdictAgeGroupRepository findByVerdictId method");

		Criteria selectCriteria = getSession().createCriteria(
				VerdictAgeGroup.class);
		selectCriteria.add(Restrictions.eq("verdictId", verdictId));

		logger.debug("Got VerdictAgeGroup objects from database. Exiting VerdictAgeGroupRepository findByVerdictId method");
		return selectCriteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#findAll()
	 */
	@Override
	public List<VerdictAgeGroup> findAll() {

		logger.debug("Entered VerdictAgeGroupRepository findAll method");
		logger.debug("Trying to get all VerdictAgeGroup");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictAgeGroup.class);
		@SuppressWarnings("unchecked")
		List<VerdictAgeGroup> verdictAgeGroups = selectAllCriteria.list();

		logger.debug("Got all VerdictAgeGroup objects from database. Exiting VerdictAgeGroupRepository findAll method");

		return verdictAgeGroups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#update(java.lang.Object)
	 */
	@Override
	public VerdictAgeGroup update(VerdictAgeGroup verdictAgeGroup) {

		logger.debug("Entered VerdictAgeGroupRepository update method");
		logger.debug("Merging the object first with verdict id = "
				+ verdictAgeGroup.getId().getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroup.getId().getAgeGroup().getId());

		verdictAgeGroup = (VerdictAgeGroup) getSession().merge(verdictAgeGroup);

		logger.debug("Now updating the VerdictAgeGroup object in database with verdict id = "
				+ verdictAgeGroup.getId().getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroup.getId().getAgeGroup().getId());

		getSession().update(verdictAgeGroup);

		logger.debug("Getting the VerdictAgeGroup object from database");

		verdictAgeGroup = (VerdictAgeGroup) getSession().get(
				VerdictAgeGroup.class, verdictAgeGroup.getId());

		logger.debug("Got VerdictAgeGroup object from database. Exiting VerdictAgeGroupRepository update method");

		return verdictAgeGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerdictAgeGroupId verdictAgeGroupId) {

		logger.debug("Entered VerdictAgeGroupRepository delete method");
		logger.debug("Trying to get VerdictAgeGroup with verdict id = "
				+ verdictAgeGroupId.getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroupId.getAgeGroup().getId());

		VerdictAgeGroup verdictAgeGroup = (VerdictAgeGroup) getSession().get(
				VerdictAgeGroup.class, verdictAgeGroupId);

		logger.debug("Now trying to delete the VerdictAgeGroup object with verdict id = "
				+ verdictAgeGroupId.getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroupId.getAgeGroup().getId());

		getSession().delete(verdictAgeGroup);

		logger.debug("Deleted VerdictAgeGroup object from database. Exiting VerdictAgeGroupRepository delete method");
	}

}
