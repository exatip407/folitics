package com.ohmuk.folitics.hibernate.repository.verdict.global;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;

/**
 * Repository implementation for {@link GlobalVerdict}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictRepository implements IGlobalVerdictRepository {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdict save(GlobalVerdict globalVerdict) {

		logger.debug("Entered GlobalVerdictRepository save method");
		logger.debug("Trying to save GlobalVerdict");

		Long id = (Long) getSession().save(globalVerdict);

		logger.debug("Saved GlobalVerdict object and got id = " + id
				+ " and now getting GlobalVerdict object from database");
		globalVerdict = (GlobalVerdict) getSession().get(GlobalVerdict.class,
				id);

		logger.debug("Got GlobalVerdict object from database. Exiting GlobalVerdictRepository save method");
		return globalVerdict;
	}

	@Override
	public GlobalVerdict find() {

		logger.debug("Entered GlobalVerdictRepository find method");
		logger.debug("Trying to get GlobalVerdict");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdict.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdict> globalVerdicts = selectAllCriteria.list();

		logger.debug("Got all verdict objects from database. Exiting GlobalVerdictRepository find method");

		if (globalVerdicts != null && globalVerdicts.size() > 0) {
			return globalVerdicts.get(0);
		} else {
			return null;
		}
	}

	@Override
	public GlobalVerdict update(GlobalVerdict globalVerdict) {

		logger.debug("Entered GlobalVerdictRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdict.getId());

		globalVerdict = (GlobalVerdict) getSession().merge(globalVerdict);

		logger.debug("Now updating the GlobalVerdict object in database with id = "
				+ globalVerdict.getId());
		getSession().update(globalVerdict);

		logger.debug("Getting the verdict object from database");

		globalVerdict = (GlobalVerdict) getSession().get(GlobalVerdict.class,
				globalVerdict.getId());

		logger.debug("Got GlobalVerdict object from database. Exiting GlobalVerdictRepository update method");

		return globalVerdict;
	}

	@Override
	public boolean delete(GlobalVerdict globalVerdict) {

		logger.debug("Entered GlobalVerdictRepository delete method");
		logger.debug("Trying to get GlobalVerdict with id = "
				+ globalVerdict.getId());

		Long id = globalVerdict.getId();

		globalVerdict = (GlobalVerdict) getSession().get(Verdict.class,
				globalVerdict.getId());

		logger.debug("Now trying to delete the GlobalVerdict object with id = "
				+ globalVerdict.getId());

		getSession().delete(globalVerdict);

		globalVerdict = (GlobalVerdict) getSession().get(GlobalVerdict.class,
				id);

		if (globalVerdict == null) {

			logger.debug("Deleted verdict object from database. Exiting GlobalVerdictRepository delete method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(Long id) {

		logger.debug("Entered GlobalVerdictRepository delete method");
		logger.debug("Trying to get GlobalVerdict with id = " + id);

		GlobalVerdict globalVerdict = (GlobalVerdict) getSession().get(
				GlobalVerdict.class, id);

		logger.debug("Now trying to delete the GlobalVerdict object with id = "
				+ globalVerdict.getId());

		getSession().delete(globalVerdict);

		globalVerdict = (GlobalVerdict) getSession().get(GlobalVerdict.class,
				id);

		if (globalVerdict == null) {

			logger.debug("Deleted verdict object from database. Exiting GlobalVerdictRepository delete method");
			return true;
		} else {

			return false;
		}
	}

}
