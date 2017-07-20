package com.ohmuk.folitics.service.newsfeed;

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

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class FeedSourceService implements IFeedSourceService {

    private static Logger logger = LoggerFactory.getLogger(FeedDataService.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public FeedSource createDefault(String name, String url) throws MessageException {

        logger.info("Inside FeedSource save method");

        FeedSource feedSource = FeedSource.getFeedSource(name, url);
       
        Long id = (Long) getSession().save(feedSource);

        logger.info("Existing from FeedSource save method");

        return read(id);
    }

    @Override
    public FeedSource create(FeedSource feedSource) throws MessageException {

        if (feedSource.getTimestamp() == null) {
            feedSource.setTimestamp(DateUtils.getSqlTimeStamp());
        }

        logger.info("Inside FeedSource save method");

        Long id = (Long) getSession().save(feedSource);

        logger.info("Existing from FeedSource save method");

        return read(id);
    }

    @Override
    public FeedSource read(Long id) throws MessageException {

        logger.info("Inside FeedSource read method");

        FeedSource feedSource = (FeedSource) getSession().get(FeedSource.class, id);

        if (feedSource == null) {
            logger.error("No records found in database");
            throw (new MessageException("No records found in database"));
        }

        logger.info("Existing from FeedSource read method");

        return feedSource;
    }

    @Override
    public List<FeedSource> readAll() throws MessageException {

        logger.info("Inside FeedSource save method");

        List<FeedSource> feedSource = getSession().createCriteria(FeedSource.class).list();

        if (feedSource == null) {
            logger.error("No records found in database");
            throw (new MessageException("No records found in database"));
        }

        logger.info("Existing from FeedSource save method");

        return feedSource;
    }

    @Override
    public FeedSource update(FeedSource feedSource) throws MessageException {

        logger.info("Inside FeedSource save method");

        getSession().merge(feedSource);

        FeedSource feedSourceOriginal = read(feedSource.getId());

        if (feedSourceOriginal == null) {
            logger.error("No records found in database for the corresponding obect having id: " + feedSource.getId());
            throw (new MessageException("No records found in database for the corresponding obect having id: "
                    + feedSource.getId()));
        }

        getSession().update(feedSource);
        feedSource = (FeedSource) getSession().get(FeedSource.class, feedSource.getId());

        logger.info("Existing from FeedSource save method");

        return feedSource;
    }

    @Override
    public FeedSource delete(Long id) {

        logger.info("Inside FeedSource delete method");

        FeedSource feedSource = (FeedSource) getSession().get(FeedSource.class, id);
        getSession().delete(feedSource);
        feedSource = (FeedSource) getSession().get(FeedSource.class, id);

        logger.info("Existing from FeedSource delete method");

        return feedSource;
    }

    @Override
    public List<FeedSource> readByName(String name) {

        logger.info("Inside FeedSource readByName method");

        Criteria criteria = getSession().createCriteria(FeedSource.class);
        criteria.add(Restrictions.eqOrIsNull("name", name));
        List<FeedSource> feedSource = criteria.list();

        logger.info("Existing from FeedSource readByName method");

        return feedSource;
    }

    @Override
    public Boolean disableFeedSource(Long id) {

        logger.info("Inside FeedSource disableFeedSource method");

        FeedSource feedSource = (FeedSource) getSession().get(FeedSource.class, id);
        feedSource.setDisabled(true);
        getSession().update(feedSource);

        logger.info("Existing from FeedSource disableFeedSource method");
        return true;
    }
}
