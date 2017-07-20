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

import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualification;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualificationId;

/**
 * Repository implementation for {@link VerdictQualification}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictQualificationRepository
		implements
		IVerdictDistributionRepository<VerdictQualification, VerdictQualificationId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictQualificationRepository.class);

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
	public VerdictQualification save(VerdictQualification verdictQualification) {

		logger.debug("Entered VerdictQualificationRepository save method");
		logger.debug("Trying to save VerdictQualification for verdict id = "
				+ verdictQualification.getId().getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualification.getId().getQualification().getId());

		VerdictQualificationId verdictQualificationId = (VerdictQualificationId) getSession()
				.save(verdictQualification);

		logger.debug("Saved VerdictQualification object and now getting VerdictQualification object from database");

		verdictQualification = (VerdictQualification) getSession().get(
				VerdictQualification.class, verdictQualificationId);

		logger.debug("Got VerdictQualification object from database. Exiting VerdictQualificationRepository save method");

		return verdictQualification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#find(java.lang.Object)
	 */
	@Override
	public VerdictQualification find(
			VerdictQualificationId verdictQualificationId) {

		logger.debug("Entered VerdictQualificationRepository find method");
		logger.debug("Trying to get VerdictQualification for verdict id = "
				+ verdictQualificationId.getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualificationId.getQualification().getId());

		VerdictQualification verdictQualification = (VerdictQualification) getSession()
				.get(VerdictQualification.class, verdictQualificationId);

		logger.debug("Got VerdictQualification object from database. Exiting VerdictQualificationRepository find method");

		return verdictQualification;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictQualification> findByVerdictId(Long verdictId) {

		logger.debug("Entered VerdictQualificationRepository findByVerdictId method");

		Criteria selectCriteria = getSession().createCriteria(
				VerdictQualification.class);
		selectCriteria.add(Restrictions.eq("verdictId", verdictId));

		logger.debug("Got VerdictQualification objects from database. Exiting VerdictQualificationRepository findByVerdictId method");
		return selectCriteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#findAll()
	 */
	@Override
	public List<VerdictQualification> findAll() {

		logger.debug("Entered VerdictQualificationRepository findAll method");
		logger.debug("Trying to get all VerdictQualification");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictQualification.class);
		@SuppressWarnings("unchecked")
		List<VerdictQualification> verdictQualifications = selectAllCriteria
				.list();

		logger.debug("Got all VerdictQualification objects from database. Exiting VerdictQualificationRepository findAll method");

		return verdictQualifications;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#update(java.lang.Object)
	 */
	@Override
	public VerdictQualification update(VerdictQualification verdictQualification) {

		logger.debug("Entered VerdictQualificationRepository update method");
		logger.debug("Merging the object first with verdict id = "
				+ verdictQualification.getId().getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualification.getId().getQualification().getId());

		verdictQualification = (VerdictQualification) getSession().merge(
				verdictQualification);

		logger.debug("Now updating the VerdictQualification object in database with verdict id = "
				+ verdictQualification.getId().getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualification.getId().getQualification().getId());

		getSession().update(verdictQualification);

		logger.debug("Getting the VerdictQualification object from database");

		verdictQualification = (VerdictQualification) getSession().get(
				VerdictQualification.class, verdictQualification.getId());

		logger.debug("Got VerdictQualification object from database. Exiting VerdictQualificationRepository update method");

		return verdictQualification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerdictQualificationId verdictQualificationId) {

		logger.debug("Entered VerdictQualificationRepository delete method");
		logger.debug("Trying to get VerdictQualification with verdict id = "
				+ verdictQualificationId.getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualificationId.getQualification().getId());

		VerdictQualification verdictQualification = (VerdictQualification) getSession()
				.get(VerdictQualification.class, verdictQualificationId);

		logger.debug("Now trying to delete the VerdictQualification object with verdict id = "
				+ verdictQualificationId.getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualificationId.getQualification().getId());

		getSession().delete(verdictQualification);

		logger.debug("Deleted VerdictQualification object from database. Exiting VerdictQualificationRepository delete method");
	}

}
