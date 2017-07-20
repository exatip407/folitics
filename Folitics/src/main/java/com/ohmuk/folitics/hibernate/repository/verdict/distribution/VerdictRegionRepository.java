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

import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegionId;
import com.ohmuk.folitics.hibernate.repository.verdict.VerdictRepository;

/**
 * Repository implementation for {@link VerdictRegion}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictRegionRepository implements
		IVerdictDistributionRepository<VerdictRegion, VerdictRegionId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictRepository.class);

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
	public VerdictRegion save(VerdictRegion verdictRegion) {

		logger.debug("Entered VerdictRegionRepository save method");
		logger.debug("Trying to save VerdictRegion for verdict id = "
				+ verdictRegion.getId().getVerdict().getId()
				+ " and region id = "
				+ verdictRegion.getId().getRegion().getId());

		VerdictRegionId verdictRegionId = (VerdictRegionId) getSession().save(
				verdictRegion);

		logger.debug("Saved VerdictRegion object and now getting VerdictRegion object from database");

		verdictRegion = (VerdictRegion) getSession().get(VerdictRegion.class,
				verdictRegionId);

		logger.debug("Got VerdictRegion object from database. Exiting VerdictRegionRepository save method");

		return verdictRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#find(java.lang.Object)
	 */
	@Override
	public VerdictRegion find(VerdictRegionId verdictRegionId) {

		logger.debug("Entered VerdictRegionRepository find method");
		logger.debug("Trying to get VerdictRegion for verdict id = "
				+ verdictRegionId.getVerdict().getId() + " and region id = "
				+ verdictRegionId.getRegion().getId());

		VerdictRegion verdictRegion = (VerdictRegion) getSession().get(
				VerdictRegion.class, verdictRegionId);

		logger.debug("Got VerdictRegion object from database. Exiting VerdictRegionRepository find method");

		return verdictRegion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictRegion> findByVerdictId(Long verdictId) {

		logger.debug("Entered VerdictRegionRepository findByVerdictId method");

		Criteria selectCriteria = getSession().createCriteria(
				VerdictRegion.class);
		selectCriteria.add(Restrictions.eq("verdictId", verdictId));

		logger.debug("Got VerdictRegion objects from database. Exiting VerdictRegionRepository findByVerdictId method");
		return selectCriteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#findAll()
	 */
	@Override
	public List<VerdictRegion> findAll() {

		logger.debug("Entered VerdictRegionRepository findAll method");
		logger.debug("Trying to get all VerdictRegion");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictRegion.class);
		@SuppressWarnings("unchecked")
		List<VerdictRegion> verdictReligion = selectAllCriteria.list();

		logger.debug("Got all VerdictRegion objects from database. Exiting VerdictRegionRepository findAll method");

		return verdictReligion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#update(java.lang.Object)
	 */
	@Override
	public VerdictRegion update(VerdictRegion verdictRegion) {

		logger.debug("Entered VerdictRegionRepository update method");
		logger.debug("Merging the object first with verdict id = "
				+ verdictRegion.getId().getVerdict().getId()
				+ " and region id = "
				+ verdictRegion.getId().getRegion().getId());

		verdictRegion = (VerdictRegion) getSession().merge(verdictRegion);

		logger.debug("Now updating the VerdictRegion object in database with verdict id = "
				+ verdictRegion.getId().getVerdict().getId()
				+ " and region id = "
				+ verdictRegion.getId().getRegion().getId());

		getSession().update(verdictRegion);

		logger.debug("Getting the VerdictRegion object from database");

		verdictRegion = (VerdictRegion) getSession().get(VerdictRegion.class,
				verdictRegion.getId());

		logger.debug("Got VerdictRegion object from database. Exiting VerdictRegionRepository update method");

		return verdictRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerdictRegionId verdictRegionId) {

		logger.debug("Entered VerdictRegionRepository delete method");
		logger.debug("Trying to get VerdictRegion with verdict id = "
				+ verdictRegionId.getVerdict().getId() + " and region id = "
				+ verdictRegionId.getRegion().getId());

		VerdictRegion verdictRegion = (VerdictRegion) getSession().get(
				VerdictRegion.class, verdictRegionId);

		logger.debug("Now trying to delete the VerdictRegion object with verdict id = "
				+ verdictRegionId.getVerdict().getId()
				+ " and region id = "
				+ verdictRegionId.getRegion().getId());

		getSession().delete(verdictRegion);

		logger.debug("Deleted VerdictRegion object from database. Exiting VerdictRegionRepository delete method");
	}

}
