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

import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatusId;

/**
 * Repository implementation for {@link VerdictMaritalStatus}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictMaritalStatusRepository
		implements
		IVerdictDistributionRepository<VerdictMaritalStatus, VerdictMaritalStatusId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictMaritalStatusRepository.class);

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
	public VerdictMaritalStatus save(VerdictMaritalStatus verdictMaritalStatus) {

		logger.debug("Entered VerdictMaritalStatusRepository save method");
		logger.debug("Trying to save VerdictMaritalStatus for verdict id = "
				+ verdictMaritalStatus.getId().getVerdict().getId()
				+ " and maritalstatus id = "
				+ verdictMaritalStatus.getId().getMaritalStatus().getId());

		VerdictMaritalStatusId verdictMaritalStatusId = (VerdictMaritalStatusId) getSession()
				.save(verdictMaritalStatus);

		logger.debug("Saved VerdictMaritalStatus object and now getting VerdictMaritalStatus object from database");

		verdictMaritalStatus = (VerdictMaritalStatus) getSession().get(
				VerdictMaritalStatus.class, verdictMaritalStatusId);

		logger.debug("Got VerdictMaritalStatus object from database. Exiting VerdictMaritalStatusRepository save method");

		return verdictMaritalStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#find(java.lang.Object)
	 */
	@Override
	public VerdictMaritalStatus find(
			VerdictMaritalStatusId verdictMaritalStatusId) {

		logger.debug("Entered VerdictMaritalStatusRepository find method");
		logger.debug("Trying to get VerdictMaritalStatus for verdict id = "
				+ verdictMaritalStatusId.getVerdict().getId()
				+ " and maritalstatus id = "
				+ verdictMaritalStatusId.getMaritalStatus().getId());

		VerdictMaritalStatus verdictMaritalStatus = (VerdictMaritalStatus) getSession()
				.get(VerdictMaritalStatus.class, verdictMaritalStatusId);

		logger.debug("Got VerdictMaritalStatus object from database. Exiting VerdictMaritalStatusRepository find method");

		return verdictMaritalStatus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictMaritalStatus> findByVerdictId(Long verdictId) {

		logger.debug("Entered VerdictMaritalStatusRepository findByVerdictId method");

		Criteria selectCriteria = getSession().createCriteria(
				VerdictMaritalStatus.class);
		selectCriteria.add(Restrictions.eq("verdictId", verdictId));

		logger.debug("Got VerdictMaritalStatus objects from database. Exiting VerdictMaritalStatusRepository findByVerdictId method");
		return selectCriteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#findAll()
	 */
	@Override
	public List<VerdictMaritalStatus> findAll() {

		logger.debug("Entered VerdictMaritalStatusRepository findAll method");
		logger.debug("Trying to get all VerdictMaritalStatus");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictMaritalStatus.class);
		@SuppressWarnings("unchecked")
		List<VerdictMaritalStatus> verdictMaritalStatuses = selectAllCriteria
				.list();

		logger.debug("Got all VerdictMaritalStatus objects from database. Exiting VerdictMaritalStatusRepository findAll method");

		return verdictMaritalStatuses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#update(java.lang.Object)
	 */
	@Override
	public VerdictMaritalStatus update(VerdictMaritalStatus verdictMaritalStatus) {

		logger.debug("Entered VerdictMaritalStatusRepository update method");
		logger.debug("Merging the object first with verdict id = "
				+ verdictMaritalStatus.getId().getVerdict().getId()
				+ " and marital status id = "
				+ verdictMaritalStatus.getId().getMaritalStatus().getId());

		verdictMaritalStatus = (VerdictMaritalStatus) getSession().merge(
				verdictMaritalStatus);

		logger.debug("Now updating the VerdictMaritalStatus object in database with verdict id = "
				+ verdictMaritalStatus.getId().getVerdict().getId()
				+ " and marital status id = "
				+ verdictMaritalStatus.getId().getMaritalStatus().getId());

		getSession().update(verdictMaritalStatus);

		logger.debug("Getting the VerdictMaritalStatus object from database");

		verdictMaritalStatus = (VerdictMaritalStatus) getSession().get(
				VerdictMaritalStatus.class, verdictMaritalStatus.getId());

		logger.debug("Got VerdictMaritalStatus object from database. Exiting VerdictMaritalStatusRepository update method");

		return verdictMaritalStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerdictMaritalStatusId verdictMaritalStatusId) {

		logger.debug("Entered VerdictMaritalStatusRepository delete method");
		logger.debug("Trying to get VerdictMaritalStatus with verdict id = "
				+ verdictMaritalStatusId.getVerdict().getId()
				+ " and marital status id = "
				+ verdictMaritalStatusId.getMaritalStatus().getId());

		VerdictMaritalStatus verdictMaritalStatus = (VerdictMaritalStatus) getSession()
				.get(VerdictMaritalStatus.class, verdictMaritalStatusId);

		logger.debug("Now trying to delete the VerdictMaritalStatus object with verdict id = "
				+ verdictMaritalStatusId.getVerdict().getId()
				+ " and marital status id = "
				+ verdictMaritalStatusId.getMaritalStatus().getId());

		getSession().delete(verdictMaritalStatus);

		logger.debug("Deleted VerdictMaritalStatus object from database. Exiting VerdictMaritalStatusRepository delete method");
	}

}
