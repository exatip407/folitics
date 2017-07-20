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

import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligionId;

/**
 * Repository implementation for {@link VerdictReligion}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictReligionRepository implements
		IVerdictDistributionRepository<VerdictReligion, VerdictReligionId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictReligionRepository.class);

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
	public VerdictReligion save(VerdictReligion verdictReligion) {

		logger.debug("Entered VerdictReligionRepository save method");
		logger.debug("Trying to save VerdictReligion for verdict id = "
				+ verdictReligion.getId().getVerdict().getId()
				+ " and religion id = "
				+ verdictReligion.getId().getReligion().getId());

		VerdictReligionId verdictReligionId = (VerdictReligionId) getSession()
				.save(verdictReligion);

		logger.debug("Saved VerdictReligion object and now getting VerdictReligion object from database");

		verdictReligion = (VerdictReligion) getSession().get(
				VerdictReligion.class, verdictReligionId);

		logger.debug("Got VerdictReligion object from database. Exiting VerdictReligionRepository save method");

		return verdictReligion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#find(java.lang.Object)
	 */
	@Override
	public VerdictReligion find(VerdictReligionId verdictReligionId) {

		logger.debug("Entered VerdictReligionRepository find method");
		logger.debug("Trying to get VerdictReligion for verdict id = "
				+ verdictReligionId.getVerdict().getId()
				+ " and religion id = "
				+ verdictReligionId.getReligion().getId());

		VerdictReligion verdictReligion = (VerdictReligion) getSession().get(
				VerdictReligion.class, verdictReligionId);

		logger.debug("Got VerdictReligion object from database. Exiting VerdictReligionRepository find method");

		return verdictReligion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictReligion> findByVerdictId(Long verdictId) {

		logger.debug("Entered VerdictReligionRepository findByVerdictId method");

		Criteria selectCriteria = getSession().createCriteria(
				VerdictReligion.class);
		selectCriteria.add(Restrictions.eq("verdictId", verdictId));

		logger.debug("Got VerdictReligion objects from database. Exiting VerdictReligionRepository findByVerdictId method");
		return selectCriteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#findAll()
	 */
	@Override
	public List<VerdictReligion> findAll() {

		logger.debug("Entered VerdictReligionRepository findAll method");
		logger.debug("Trying to get all VerdictReligion");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictReligion.class);
		@SuppressWarnings("unchecked")
		List<VerdictReligion> verdictReligions = selectAllCriteria.list();

		logger.debug("Got all VerdictReligion objects from database. Exiting VerdictReligionRepository findAll method");

		return verdictReligions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#update(java.lang.Object)
	 */
	@Override
	public VerdictReligion update(VerdictReligion verdictReligion) {

		logger.debug("Entered VerdictReligionRepository update method");
		logger.debug("Merging the object first with verdict id = "
				+ verdictReligion.getId().getVerdict().getId()
				+ " and religion id = "
				+ verdictReligion.getId().getReligion().getId());

		verdictReligion = (VerdictReligion) getSession().merge(verdictReligion);

		logger.debug("Now updating the VerdictReligion object in database with verdict id = "
				+ verdictReligion.getId().getVerdict().getId()
				+ " and religion id = "
				+ verdictReligion.getId().getReligion().getId());

		getSession().update(verdictReligion);

		logger.debug("Getting the VerdictReligion object from database");

		verdictReligion = (VerdictReligion) getSession().get(
				VerdictReligion.class, verdictReligion.getId());

		logger.debug("Got VerdictReligion object from database. Exiting VerdictReligionRepository update method");

		return verdictReligion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerdictReligionId verdictReligionId) {

		logger.debug("Entered VerdictReligionRepository delete method");
		logger.debug("Trying to get VerdictReligion with verdict id = "
				+ verdictReligionId.getVerdict().getId()
				+ " and religion id = "
				+ verdictReligionId.getReligion().getId());

		VerdictReligion verdictReligion = (VerdictReligion) getSession().get(
				VerdictReligion.class, verdictReligionId);

		logger.debug("Now trying to delete the VerdictReligion object with verdict id = "
				+ verdictReligionId.getVerdict().getId()
				+ " and religion id = "
				+ verdictReligionId.getReligion().getId());

		getSession().delete(verdictReligion);

		logger.debug("Deleted VerdictReligion object from database. Exiting VerdictReligionRepository delete method");
	}

}
