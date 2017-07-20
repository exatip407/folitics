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

import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSex;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSexId;

/**
 * Repository implementation for {@link VerdictSex}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictSexRepository implements
		IVerdictDistributionRepository<VerdictSex, VerdictSexId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictSexRepository.class);

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
	public VerdictSex save(VerdictSex verdictSex) {

		logger.debug("Entered VerdictSexRepository save method");
		logger.debug("Trying to save VerdictSex for verdict id = "
				+ verdictSex.getId().getVerdict().getId() + " and sex id = "
				+ verdictSex.getId().getSex().getId());

		VerdictSexId verdictSexId = (VerdictSexId) getSession()
				.save(verdictSex);

		logger.debug("Saved VerdictSex object and now getting VerdictSex object from database");

		verdictSex = (VerdictSex) getSession().get(VerdictSex.class,
				verdictSexId);

		logger.debug("Got VerdictSex object from database. Exiting VerdictSexRepository save method");

		return verdictSex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#find(java.lang.Object)
	 */
	@Override
	public VerdictSex find(VerdictSexId verdictSexId) {

		logger.debug("Entered VerdictSexRepository find method");
		logger.debug("Trying to get VerdictSex for verdict id = "
				+ verdictSexId.getVerdict().getId() + " and sex id = "
				+ verdictSexId.getSex().getId());

		VerdictSex verdictSex = (VerdictSex) getSession().get(VerdictSex.class,
				verdictSexId);

		logger.debug("Got VerdictSex object from database. Exiting VerdictSexRepository find method");

		return verdictSex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictSex> findByVerdictId(Long verdictId) {

		logger.debug("Entered VerdictSexRepository findByVerdictId method");

		Criteria selectCriteria = getSession().createCriteria(VerdictSex.class);
		selectCriteria.add(Restrictions.eq("verdictId", verdictId));

		logger.debug("Got VerdictSex objects from database. Exiting VerdictSexRepository findByVerdictId method");
		return selectCriteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#findAll()
	 */
	@Override
	public List<VerdictSex> findAll() {

		logger.debug("Entered VerdictSexRepository findAll method");
		logger.debug("Trying to get all VerdictSex");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictSex.class);
		@SuppressWarnings("unchecked")
		List<VerdictSex> verdictSexes = selectAllCriteria.list();

		logger.debug("Got all VerdictSex objects from database. Exiting VerdictSexRepository findAll method");

		return verdictSexes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#update(java.lang.Object)
	 */
	@Override
	public VerdictSex update(VerdictSex verdictSex) {

		logger.debug("Entered VerdictSexRepository update method");
		logger.debug("Merging the object first with verdict id = "
				+ verdictSex.getId().getVerdict().getId() + " and sex id = "
				+ verdictSex.getId().getSex().getId());

		verdictSex = (VerdictSex) getSession().merge(verdictSex);

		logger.debug("Now updating the VerdictSex object in database with verdict id = "
				+ verdictSex.getId().getVerdict().getId()
				+ " and sex id = "
				+ verdictSex.getId().getSex().getId());

		getSession().update(verdictSex);

		logger.debug("Getting the VerdictSex object from database");

		verdictSex = (VerdictSex) getSession().get(VerdictSex.class,
				verdictSex.getId());

		logger.debug("Got VerdictSex object from database. Exiting VerdictSexRepository update method");

		return verdictSex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.
	 * IVerdictDistributionRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerdictSexId verdictSexId) {

		logger.debug("Entered VerdictSexRepository delete method");
		logger.debug("Trying to get VerdictSex with verdict id = "
				+ verdictSexId.getVerdict().getId() + " and sex id = "
				+ verdictSexId.getSex().getId());

		VerdictSex verdictSex = (VerdictSex) getSession().get(VerdictSex.class,
				verdictSexId);

		logger.debug("Now trying to delete the VerdictSex object with verdict id = "
				+ verdictSexId.getVerdict().getId()
				+ " and sex id = "
				+ verdictSexId.getSex().getId());

		getSession().delete(verdictSex);

		logger.debug("Deleted VerdictSex object from database. Exiting VerdictSexRepository delete method");
	}

}
