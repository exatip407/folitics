package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCountId;

/**
 * @author 
 *
 */
@Component
@Repository
public class SentimentFollowCountRepositoryImplementation implements ISentimentFollowCountRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.follow.
	 * IFollowCountHibernateRepository#save(java.lang.Object)
	 */
	@Override
	public SentimentFollowCount save(SentimentFollowCount sentimentFollowCount) {
		SentimentFollowCountId id = (SentimentFollowCountId) getSession().save(sentimentFollowCount);
		sentimentFollowCount = (SentimentFollowCount) getSession().get(SentimentFollowCount.class, id);
		return sentimentFollowCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.follow.
	 * IFollowCountHibernateRepository#update(java.lang.Object)
	 */
	@Override
	public SentimentFollowCount update(SentimentFollowCount sentimentFollowCount) {
		sentimentFollowCount = (SentimentFollowCount) getSession().merge(sentimentFollowCount);
		getSession().update(sentimentFollowCount);
		sentimentFollowCount = (SentimentFollowCount) getSession().get(SentimentFollowCount.class,
				sentimentFollowCount.getId());
		return sentimentFollowCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.follow.
	 * IFollowCountHibernateRepository#findAll()
	 */
	@Override
	public List<SentimentFollowCount> findAll() {
		List<SentimentFollowCount> list = getSession().createCriteria(SentimentFollowCount.class).list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.follow.
	 * IFollowCountHibernateRepository#findByComponentId(java.lang.Long)
	 */
	@Override
	public SentimentFollowCount findByComponentId(Long id) {
		Criteria criteria = getSession().createCriteria(SentimentFollowCount.class);
		criteria.add(Restrictions.eq("id.sentiment.id", id));
		SentimentFollowCount followCount = (SentimentFollowCount) criteria.uniqueResult();
		return followCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.follow.
	 * ISentimentFollowCountRepository#find(com.ohmuk.folitics.jpa.entity.follow
	 * .SentimentFollowCountId)
	 */
	@Override
	public SentimentFollowCount find(SentimentFollowCountId id) {
		return (SentimentFollowCount) getSession().get(SentimentFollowCount.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.follow.
	 * ISentimentFollowCountRepository#delete(com.ohmuk.folitics.jpa.entity.
	 * follow.SentimentFollowCountId)
	 */
	@Override
	public void delete(SentimentFollowCountId id) {
		SentimentFollowCount followCount = (SentimentFollowCount) getSession().get(SentimentFollowCount.class, id);
		getSession().delete(followCount);
	}

}
