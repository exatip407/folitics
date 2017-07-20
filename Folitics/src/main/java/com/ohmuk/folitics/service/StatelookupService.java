package com.ohmuk.folitics.service;


	

	import java.util.List;

	import org.hibernate.Criteria;
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;

	import org.hibernate.criterion.Restrictions;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

	import com.ohmuk.folitics.hibernate.entity.Statelookup;


	@Service
	@Transactional
	public class StatelookupService implements IStatelookupService {

		private static Logger logger = LoggerFactory.getLogger(StatelookupService.class);

		@Autowired
		SessionFactory _sessionFactory;
		
		private Session getSession() {
			return _sessionFactory.getCurrentSession();
		}
		
		public List<Statelookup>search(String state) throws Exception {
			logger.info("Inside StatelookupService readAll method");
		
			Criteria criteria = getSession().createCriteria(Statelookup.class);
			criteria.add(Restrictions.eq("state", state));
			return criteria.list();

			
		}


		
	}


