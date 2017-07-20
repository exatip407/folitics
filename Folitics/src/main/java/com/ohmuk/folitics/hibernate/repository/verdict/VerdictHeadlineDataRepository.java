package com.ohmuk.folitics.hibernate.repository.verdict;

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

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;

/**
 * Repository implementation for {@link VerdictHeadlineData}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictHeadlineDataRepository implements
		IVerdictHeadlineDataRepository {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictHeadlineDataRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public VerdictHeadlineData save(VerdictHeadlineData verdictHeadlineData) {

		logger.debug("Entered VerdictHeadlineDataRepository save method");
		logger.debug("Trying to save VerdictHeadlineData");

		Long id = (Long) getSession().save(verdictHeadlineData);

		logger.debug("Saved VerdictHeadlineData object and got id = " + id
				+ " and now getting VerdictHeadlineData object from database");
		verdictHeadlineData = (VerdictHeadlineData) getSession().get(
				VerdictHeadlineData.class, id);

		logger.debug("Got VerdictHeadlineData object from database. Exiting VerdictHeadlineDataRepository save method");
		return verdictHeadlineData;
	}

	@Override
	public VerdictHeadlineData find(Long id) {

		logger.debug("Entered VerdictHeadlineDataRepository find method");
		logger.debug("Trying to get VerdictHeadlineData with id = " + id);

		VerdictHeadlineData verdictHeadlineData = (VerdictHeadlineData) getSession()
				.get(VerdictHeadlineData.class, id);

		logger.debug("Got VerdictHeadlineData object from database. Exiting VerdictHeadlineDataRepository find method");

		return verdictHeadlineData;
	}

	@Override
	public VerdictHeadlineData findByVerdictAndParameters(
			VerdictHeadlineData verdictHeadlineData) {

		logger.debug("Entered VerdictHeadlineDataRepository findByVerdictAndParameters method");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictHeadlineData.class);
		selectAllCriteria.add(Restrictions.eq("verdictId.id",
				verdictHeadlineData.getVerdictId().getId()));
		selectAllCriteria.add(Restrictions.eq("agegroup.id",
				verdictHeadlineData.getAgegroup().getId()));
		selectAllCriteria.add(Restrictions.eq("sex.id", verdictHeadlineData
				.getSex().getId()));
		selectAllCriteria.add(Restrictions.eq("maritalstatus.id",
				verdictHeadlineData.getMaritalstatus().getId()));
		selectAllCriteria.add(Restrictions.eq("region.id", verdictHeadlineData
				.getRegion().getId()));
		selectAllCriteria.add(Restrictions.eq("religion.id",
				verdictHeadlineData.getReligion().getId()));
		selectAllCriteria.add(Restrictions.eq("qualification.id",
				verdictHeadlineData.getQualification().getId()));

		verdictHeadlineData = (VerdictHeadlineData) selectAllCriteria
				.uniqueResult();

		logger.debug("Got VerdictHeadlineData object from database. Exiting VerdictHeadlineDataRepository findByVerdictAndParameters method");

		return verdictHeadlineData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerdictHeadlineData> findByVerdict(Verdict verdict) {

		logger.debug("Entered VerdictHeadlineDataRepository findByVerdict method");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictHeadlineData.class);
		selectAllCriteria.add(Restrictions.eq("verdictId.id", verdict.getId()));

		List<VerdictHeadlineData> verdictHeadlineDatas = selectAllCriteria
				.list();

		logger.debug("Got VerdictHeadlineData objects from database. Exiting VerdictHeadlineDataRepository findByVerdict method");

		return verdictHeadlineDatas;
	}

	@Override
	public List<VerdictHeadlineData> findAll() {

		logger.debug("Entered VerdictHeadlineDataRepository findAll method");
		logger.debug("Trying to get all VerdictHeadlineData");

		Criteria selectAllCriteria = getSession().createCriteria(
				VerdictHeadlineData.class);
		@SuppressWarnings("unchecked")
		List<VerdictHeadlineData> verdictHeadlineDatas = selectAllCriteria
				.list();

		logger.debug("Got all VerdictHeadlineData objects from database. Exiting VerdictHeadlineDataRepository findAll method");

		return verdictHeadlineDatas;
	}

	@Override
	public VerdictHeadlineData update(VerdictHeadlineData verdictHeadlineData) {

		verdictHeadlineData = (VerdictHeadlineData) getSession().merge(
				verdictHeadlineData);

		logger.debug("Now updating the VerdictHeadlineData object in database with id = "
				+ verdictHeadlineData.getId());
		getSession().update(verdictHeadlineData);

		logger.debug("Getting the VerdictHeadlineData object from database");

		verdictHeadlineData = (VerdictHeadlineData) getSession().get(
				VerdictHeadlineData.class, verdictHeadlineData.getId());

		logger.debug("Got VerdictHeadlineData object from database. Exiting VerdictHeadlineDataRepository update method");

		return verdictHeadlineData;
	}

	@Override
	public boolean delete(Long id) {

		logger.debug("Entered VerdictHeadlineDataRepository delete method");
		logger.debug("Trying to get VerdictHeadlineData with id = " + id);

		VerdictHeadlineData verdictHeadlineData = (VerdictHeadlineData) getSession()
				.get(VerdictHeadlineData.class, id);

		logger.debug("Now trying to delete the VerdictHeadlineData object with id = "
				+ verdictHeadlineData.getId());

		getSession().delete(verdictHeadlineData);

		verdictHeadlineData = (VerdictHeadlineData) getSession().get(
				VerdictHeadlineData.class, id);

		if (verdictHeadlineData == null) {

			logger.debug("Deleted VerdictHeadlineData object from database. Exiting VerdictHeadlineDataRepository delete method");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(VerdictHeadlineData verdictHeadlineData) {

		logger.debug("Entered VerdictHeadlineDataRepository delete method");
		logger.debug("Trying to get VerdictHeadlineData with id = "
				+ verdictHeadlineData.getId());

		verdictHeadlineData = (VerdictHeadlineData) getSession().get(
				VerdictHeadlineData.class, verdictHeadlineData.getId());

		logger.debug("Now trying to delete the VerdictHeadlineData object with id = "
				+ verdictHeadlineData.getId());

		Long id = verdictHeadlineData.getId();

		getSession().delete(verdictHeadlineData);

		verdictHeadlineData = (VerdictHeadlineData) getSession().get(
				VerdictHeadlineData.class, id);

		if (verdictHeadlineData == null) {

			logger.debug("Deleted VerdictHeadlineData object from database. Exiting VerdictHeadlineDataRepository delete method");
			return true;
		} else {
			return false;
		}

	}

}
