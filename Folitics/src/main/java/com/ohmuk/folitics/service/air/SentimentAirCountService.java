package com.ohmuk.folitics.service.air;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.hibernate.entity.air.SentimentAirCount;
import com.ohmuk.folitics.hibernate.entity.air.SentimentCountId;
import com.ohmuk.folitics.hibernate.repository.air.ISentimentAirCountRepository;


/**
 * Service implementation for entity: {@link SentimentAirCount}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class SentimentAirCountService implements IAirCountService<SentimentAirCount> {

    @Autowired
    private ISentimentAirCountRepository repository;

    @Override
    public SentimentAirCount create(SentimentAirCount sentimentAirCount) {

        return repository.save(sentimentAirCount);
    }

    public SentimentAirCount read(SentimentCountId id) {

        return repository.find(id);
    }

    @Override
    public List<SentimentAirCount> readAll() {

        return repository.findAll();
    }

    @Override
    public SentimentAirCount update(SentimentAirCount sentimentAirCount) {

        return repository.update(sentimentAirCount);
    }

    public SentimentAirCount delete(SentimentCountId id) {

        repository.delete(id);
        return repository.find(id);
    }

    @Override
    public SentimentAirCount delete(SentimentAirCount sentimentAirCount) {

        repository.delete(sentimentAirCount.getId());
        return repository.find(sentimentAirCount.getId());
    }

    @Override
    public Long getAirCountByComponentId(Long componentId) {
    	SentimentAirCount sentimentAirCount = repository.findByComponentId(componentId);
    	if(sentimentAirCount != null)
        return sentimentAirCount.getAirCount();
    	else return 0l;
    }

}
 