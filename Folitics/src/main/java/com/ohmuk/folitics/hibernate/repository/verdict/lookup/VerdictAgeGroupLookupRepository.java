package com.ohmuk.folitics.hibernate.repository.verdict.lookup;

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

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;

/**
 * Hibernate repository implementation for {@link AgeGroup} lookup
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictAgeGroupLookupRepository implements
		IVerdictLookupRepository<AgeGroup> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictAgeGroupLookupRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public AgeGroup save(AgeGroup ageGroup) {

		logger.debug("Entered VerdictAgeGroupLookupRepository save method");
		logger.debug("Trying to save AgeGroup with startAge = "
				+ ageGroup.getStartAge() + " and endAge = "
				+ ageGroup.getEndAge());

		Long id = (Long) getSession().save(ageGroup);

		logger.debug("Saved AgeGroup object and now getting AgeGroup object from database");

		ageGroup = (AgeGroup) getSession().get(AgeGroup.class, id);

		logger.debug("Got AgeGroup object from database. Exiting VerdictAgeGroupLookupRepository save method");

		return ageGroup;
	}

	@Override
	public AgeGroup find(Long id) {

		logger.debug("Entered VerdictAgeGroupLookupRepository find method");
		logger.debug("Trying to get AgeGroup for id = " + id);

		AgeGroup ageGroup = (AgeGroup) getSession().get(AgeGroup.class, id);

		logger.debug("Got AgeGroup object from database. Exiting VerdictAgeGroupLookupRepository find method");

		return ageGroup;
	}

	@Override
	public List<AgeGroup> findAll() {

		logger.debug("Entered VerdictAgeGroupLookupRepository findAll method");
		logger.debug("Trying to get all AgeGroup");

		Criteria selectAllCriteria = getSession()
				.createCriteria(AgeGroup.class);
		@SuppressWarnings("unchecked")
		List<AgeGroup> ageGroups = selectAllCriteria.list();

		logger.debug("Got all AgeGroup objects from database. Exiting VerdictAgeGroupLookupRepository findAll method");

		return ageGroups;
	}

	@Override
	public AgeGroup findByValue(Object value) {

		logger.debug("Entered VerdictAgeGroupLookupRepository findByValue method");

		int age = (int) value;

		logger.debug("Trying to get AgeGroup for age = " + age);

		Criteria selectAllCriteria = getSession()
				.createCriteria(AgeGroup.class);
		selectAllCriteria.add(Restrictions.le("startAge", age)).add(
				Restrictions.ge("endAge", age));
		AgeGroup ageGroup = (AgeGroup) selectAllCriteria.uniqueResult();

		logger.debug("Got AgeGroup object from database. Exiting VerdictAgeGroupLookupRepository findByValue method");

		return ageGroup;
	}

	@Override
	public AgeGroup update(AgeGroup ageGroup) {

		logger.debug("Entered VerdictAgeGroupLookupRepository update method");
		logger.debug("Merging the object first with id = " + ageGroup.getId());

		ageGroup = (AgeGroup) getSession().merge(ageGroup);

		logger.debug("Now updating the AgeGroup object in database with id = "
				+ ageGroup.getId());

		getSession().update(ageGroup);

		logger.debug("Getting the AgeGroup object from database");

		ageGroup = (AgeGroup) getSession()
				.get(AgeGroup.class, ageGroup.getId());

		logger.debug("Got AgeGroup object from database. Exiting VerdictAgeGroupLookupRepository update method");

		return ageGroup;
	}

	@Override
	public void delete(Long id) {

		logger.debug("Entered VerdictAgeGroupLookupRepository delete method");
		logger.debug("Trying to get AgeGroup with id = " + id);

		AgeGroup ageGroup = (AgeGroup) getSession().get(AgeGroup.class, id);

		logger.debug("Now trying to delete the VerdictAgeGroup object with id = "
				+ id);

		getSession().delete(ageGroup);

		logger.debug("Deleted AgeGroup object from database. Exiting VerdictAgeGroupLookupRepository delete method");
	}

}
