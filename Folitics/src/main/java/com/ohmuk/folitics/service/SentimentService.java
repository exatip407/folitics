package com.ohmuk.folitics.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.elasticsearch.service.IESService;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.enums.SentimentState;
import com.ohmuk.folitics.hibernate.entity.Link;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.model.ImageModel;
import com.ohmuk.folitics.util.DateUtils;
import com.ohmuk.folitics.util.ElasticSearchUtils;
import com.ohmuk.folitics.util.Serialize_JSON;

/**
 * @author Abhishek
 *
 */
@Service
@Transactional
public class SentimentService implements ISentimentService {

	@Autowired
	IPollService pollService;
	
	@Autowired
	IESService esService;

	private static Logger logger = LoggerFactory
			.getLogger(SentimentOpinionStatService.class);

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	@Override
	public Sentiment save(Sentiment sentiment) throws Exception {
		logger.info("Inside SentimentService save method");
		Long id = (Long) getSession().save(sentiment);
		if(Constants.useElasticSearch)
			esService.save(ElasticSearchUtils.INDEX, ElasticSearchUtils.TYPE_SENTIMENT, String.valueOf(id),
					Serialize_JSON.getJSONString(sentiment));
		logger.info("Existing from SentimentService save method");
		return read(id);
	}

	@Override
	public Sentiment read(Long id) throws Exception {
		logger.info("Inside SentimentService read method");
		Sentiment sentiment = (Sentiment) getSession().get(Sentiment.class, id);
		Hibernate.initialize(sentiment.getPolls());
		for (Poll poll : sentiment.getPolls()) {
			Hibernate.initialize(poll.getOptions());
		}
		Hibernate.initialize(sentiment.getCategories());

		Hibernate.initialize(sentiment.getRelatedSentiments());
		logger.info("Existing from SentimentService read method");
		return sentiment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sentiment> readAll() throws Exception {
		logger.info("Inside SentimentService readAll method");
		List<Sentiment> sentiments = getSession().createCriteria(
				Sentiment.class).list();
		logger.info("Existing from SentimentService readAll method");
		return sentiments;
	}

	@Override
	public Sentiment update(Sentiment sentiment) throws Exception {
		logger.info("Inside SentimentService update method");
		sentiment.setEditTime(DateUtils.getSqlTimeStamp());
		sentiment.setState(SentimentState.EDITED.getValue());
		getSession().update(sentiment);
		logger.info("Existing from SentimentService update method");
		return sentiment;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside SentimentService delete method");
		Sentiment sentiment = read(id);
		sentiment.setState(SentimentState.DELETED.getValue());
		getSession().update(sentiment);
		logger.info("Existing from SentimentService delete method");
		return true;
	}

	@Override
	public boolean delete(Sentiment sentiment) throws Exception {
		logger.info("Inside SentimentService delete method");
		sentiment.setState(ComponentState.DELETED.getValue());
		getSession().update(sentiment);
		logger.info("Existing from SentimentService delete method");
		return true;
	}

	@Override
	public boolean deleteFromDB(Sentiment sentiment) throws Exception {
		logger.info("Inside SentimentService deleteFromDB method");
		getSession().delete(sentiment);
		logger.info("Existing from SentimentService deleteFromDB method");
		return true;
	}

	@Override
	public boolean updateSentimentStatus(Long id, String status)
			throws Exception {
		logger.info("Inside SentimentService updateSentimentStatus method");
		Sentiment sentiment = (Sentiment) getSession().get(User.class, id);
		sentiment.setState(status);
		getSession().update(sentiment);
		logger.info("Existing from SentimentService updateSentimentStatus method");
		return true;
	}

	@Override
	public Sentiment clone(Sentiment sentiment) throws Exception {
		logger.info("Inside SentimentService clone method");
		sentiment = (Sentiment) getSession().save(sentiment);
		logger.info("Existing from SentimentService clone method");
		return sentiment;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Sentiment> getAllSentimentNotIn(Set<Long> sId) throws Exception {
		logger.info("Inside SentimentService getAllSentimentNotIn method");
		Criteria criteria = getSession().createCriteria(Sentiment.class).add(
				Restrictions.not(Restrictions.in("id", sId)));
		List<Sentiment> sentiments = criteria.list();
		logger.info("Existing from SentimentService getAllSentimentNotIn method");
		return sentiments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Link> getAllSourcesForSentiment(Sentiment sentiment)
			throws Exception {
		logger.info("Inside SentimentService getAllSourcesForSentiment method");
		Set<Sentiment> relatedSentiments = sentiment.getRelatedSentiments();
		Criteria criteria = getSession().createCriteria(Link.class).add(
				Restrictions.in("sentiment", relatedSentiments));
		List<Link> links = criteria.list();
		logger.info("Existing from SentimentService getAllSourcesForSentiment method");
		return links;
	}
	 @Override
		public List<Sentiment> findByType(String type) throws Exception {
			logger.info("Inside SentimentService findbytype method");
			Criteria criteria = getSession().createCriteria(Sentiment.class);

	                criteria.add(Restrictions.eq("type", type)).addOrder(Order.desc("createTime"));
	                List<Sentiment>sentiments = criteria.list();
	                return sentiments;
	       
		}

	@Override
	public ImageModel getImageModel(Long entityId, boolean isThumbnail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImageModel> getImageModels(String entityIds, boolean isThumbnail) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Set<Sentiment> getRelatedSentiment(Long sentimentId) throws Exception {
        Sentiment sentiment = read(sentimentId);
        Hibernate.initialize(sentiment.getRelatedSentiments());
        return sentiment.getRelatedSentiments();
    }

    @Override
    public SentimentOpinionStat getSentimentOpinionStat(Long sentimentId) throws Exception {
        Criteria criteria = getSession().createCriteria(SentimentOpinionStat.class);
        return (SentimentOpinionStat) criteria.add(Restrictions.eq("id", sentimentId)).uniqueResult();
    }

}
