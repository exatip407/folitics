package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ISentimentLikeCountRepository;

/**
 * Service implementation for entity: {@link SentimentLikeCount}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class SentimentLikeCountService implements ILikeCountService<SentimentLikeCount> {

    private static Logger logger = LoggerFactory.getLogger(SentimentLikeCountService.class);

    @Autowired
    private ISentimentLikeCountRepository repository;

    @Override
    public SentimentLikeCount create(SentimentLikeCount sentimentLikeCount) throws MessageException {

        logger.info("Entered SentimentLikeCount service create method");

        if (sentimentLikeCount.getId().getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (sentimentLikeCount.getId().getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Saving SentimentLikeCount with component id = "
                + sentimentLikeCount.getId().getSentiment().getId());
        return repository.save(sentimentLikeCount);
    }

    public SentimentLikeCount read(SentimentLikeCountId id) throws MessageException {

        logger.info("Entered SentimentLikeCount service read method");

        if (id.getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (id.getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Getting SentimentLikeCount with component id = " + id.getSentiment().getId());
        return repository.find(id);
    }

    @Override
    public List<SentimentLikeCount> readAll() {

        logger.info("Entered SentimentLikeCount service readAll method");
        logger.debug("Getting all SentimentLikeCount");
        return repository.findAll();
    }

    @Override
    public SentimentLikeCount update(SentimentLikeCount sentimentLikeCount) throws MessageException {

        logger.info("Entered SentimentLikeCount service update method");

        if (sentimentLikeCount.getId().getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (sentimentLikeCount.getId().getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Updating SentimentLikeCount with component id = "
                + sentimentLikeCount.getId().getSentiment().getId());
        return repository.update(sentimentLikeCount);
    }

    public SentimentLikeCount delete(SentimentLikeCountId id) throws MessageException {

        logger.info("Entered SentimentLikeCount service delete method");

        if (id.getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (id.getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Deleting SentimentLikeCount with component id = " + id.getSentiment().getId());
        repository.delete(id);
        return repository.find(id);
    }

    @Override
    public SentimentLikeCount delete(SentimentLikeCount sentimentLikeCount) throws MessageException {

        logger.info("Entered SentimentLikeCount service delete method");

        if (sentimentLikeCount.getId().getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (sentimentLikeCount.getId().getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Deleting SentimentLikeCount with component id = "
                + sentimentLikeCount.getId().getSentiment().getId());
        repository.delete(sentimentLikeCount.getId());
        return repository.find(sentimentLikeCount.getId());
    }

    @Override
    public SentimentLikeCount getByComponentId(Long componentId) throws MessageException {

        logger.info("Entered SentimentLikeCount service getByComponentId method");

        if (componentId == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Getting SentimentLikeCount with component id = " + componentId);
        return repository.findByComponentId(componentId);
    }

}
