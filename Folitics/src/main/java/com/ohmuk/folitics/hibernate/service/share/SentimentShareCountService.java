package com.ohmuk.folitics.hibernate.service.share;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.repository.share.ISentimentShareCountRepository;

/**
 * Service implementation for entity: {@link SentimentShareCount}
 * @author Abhishek
 *
 */

@Service
@Transactional
public class SentimentShareCountService implements IShareCountService<SentimentShareCount> {

    private static Logger logger = LoggerFactory.getLogger(SentimentShareCountService.class);

    @Autowired
    private ISentimentShareCountRepository repository;

    @Override
    public SentimentShareCount create(SentimentShareCount sentimentShareCount) throws MessageException {

        logger.info("Entered SentimentShareCount service create method");

        if (sentimentShareCount.getId().getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (sentimentShareCount.getId().getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Saving SentimentShareCount with component id = "
                + sentimentShareCount.getId().getSentiment().getId());

        return repository.save(sentimentShareCount);
    }

    @Override
    public List<SentimentShareCount> readAll() {

        logger.info("Entered SentimentShareCount service readAll method");
        logger.debug("Getting all SentimentShareCount");
        return repository.findAll();
    }

    @Override
    public SentimentShareCount update(SentimentShareCount sentimentShareCount) throws MessageException {

        logger.info("Entered SentimentShareCount service update method");

        if (sentimentShareCount.getId().getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (sentimentShareCount.getId().getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Updating SentimentShareCount with component id = "
                + sentimentShareCount.getId().getSentiment().getId());
        return repository.update(sentimentShareCount);
    }

    @Override
    public SentimentShareCount delete(SentimentShareCount sentimentShareCount) throws MessageException {

        logger.info("Entered SentimentShareCount service delete method");

        if (sentimentShareCount.getId().getSentiment() == null) {
            logger.error("Sentiment in SentimentLikeCount is null");
            throw (new MessageException("Sentiment in SentimentLikeCount can't be null"));
        }

        if (sentimentShareCount.getId().getSentiment().getId() == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Deleting SentimentShareCount with component id = "
                + sentimentShareCount.getId().getSentiment().getId());
        return repository.update(sentimentShareCount);
    }

    @Override
    public SentimentShareCount getByComponentId(Long componentId) throws MessageException {

        logger.info("Entered SentimentShareCount service getByComponentId method");

        if (componentId == null) {
            logger.error("Sentiment id is null");
            throw (new MessageException("Sentiment id can't be null"));
        }

        logger.debug("Getting SentimentShareCount with component id = " + componentId);
        return repository.findByComponentId(componentId);
    }

}
