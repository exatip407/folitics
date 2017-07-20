package com.ohmuk.folitics.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickPersonality;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickProblem;
import com.ohmuk.folitics.elasticsearch.service.IESService;
import com.ohmuk.folitics.exception.MessageException;


@Service
@Transactional
public class QuickProblemService implements IQuickProblemService {

	private static final String UNCHECKED = "unchecked";

	private volatile Logger logger = LoggerFactory
			.getLogger(QuickProblemService.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	IESService esService;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public QuickProblem save(QuickProblem quickProblem)
			throws MessageException, Exception {
		logger.info("Inside QuickProblemService save method");
		Long id = (Long) getSession().save(quickProblem);
		logger.debug("QuickProblem is save with id: " + id);

		logger.info("Exiting from QuickProblemService save method");
		quickProblem = (QuickProblem) getSession().get(QuickProblem.class, id);
		
		 /** if(Constants.useElasticSearch)
		 * esService.save(ElasticSearchUtils.INDEX,
		 * ElasticSearchUtils.TYPE_QUICKPROBLEM, String.valueOf(id),
		 * Serialize_JSON.getJSONString(quickProblem));*/
		 
		logger.debug("QuickProblem is save in elastic search with id: " + id);
		return quickProblem;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuickProblem> getAllQuickProblems() throws MessageException,
			Exception {
		Criteria criteria = getSession().createCriteria(QuickProblem.class).addOrder(Order.desc("creationDate"));
		return criteria.list();
	}

	@Override
	public boolean deleteFromDB(Long id) throws MessageException, Exception{
		logger.info("Inside deleteFromDb method in QuickProblemService");

		if (id == null) {
			logger.error("Id found to be null, can not delete QuickProblem corrosponding to these id"
					+ id);
			throw (new MessageException(
					"Id found to be null, can not delete QuickProblem corrosponding to these id :"
							+ id));
		}
		logger.debug("QuickProblem to be deleted whose id is:" + id);

		QuickProblem quickProblem = (QuickProblem) getSession().get(QuickProblem.class, id);
		getSession().delete(quickProblem);

		logger.debug("quickProblem deleted" + quickProblem);
		logger.info("Exiting deleteFromDb method from QuickProblem Service");
		return true;
	}

	@Override
	public boolean delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete method in QuickProblemService");

		if (id == null) {
			logger.error("Id found to be null, can not delete QuickProblem corrosponding to these id"
					+ id);
			throw (new MessageException(
					"Id found to be null, can not delete QuickProblem corrosponding to these id :"
							+ id));
		}
		logger.debug("QuickProblem to be deleted whose id is:" + id);

		QuickProblem quickProblem = (QuickProblem) getSession().get(QuickProblem.class, id);
		//quickProblem.setStatus(FactType.DELETED.getValue());

		quickProblem = (QuickProblem) getSession().merge(quickProblem);
		getSession().update(quickProblem);

		logger.info("Exiting delete method from QuickProblemService");
		return true;
	}

	

	@Override
	public List<QuickProblem> readAll(Long userId) throws MessageException,
			Exception {
		Criteria criteria= getSession().createCriteria(QuickProblem.class);
				criteria.add(Restrictions.eq("createdBy.id", userId));
				return criteria.list() ;
}
	@SuppressWarnings("unchecked")
	@Override
	public List<QuickPersonality> getAllQuickPersonality() {
		logger.info("Inside QuickProblemService getAllPersonality method");
		List<QuickPersonality> quickpersonalities = getSession().createCriteria(QuickPersonality.class).list();
		logger.info("Exiting QuickProblemService getAllPersonality method");
		return quickpersonalities;
	}

	@Override
	public QuickProblem update(QuickProblem quickProblem)
			throws MessageException, Exception {
		logger.info("Inside QuickProblemService update method");
		quickProblem = (QuickProblem) getSession().merge(quickProblem);
		getSession().update(quickProblem);
		logger.debug("QuickProblem is updated in elasticsearch with id: " + quickProblem.getId());
		return quickProblem;
	}

	
}
