package com.ohmuk.folitics.service.follow;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCount;
import com.ohmuk.folitics.hibernate.repository.follow.ISentimentFollowCountRepository;

/**
 * @author Sarvesh
 *
 */
@Service
@Transactional
public class SentimentFollowCountService implements IFollowCountService<SentimentFollowCount> {

	@Autowired
	private ISentimentFollowCountRepository repository;

	@Override
	public SentimentFollowCount create(SentimentFollowCount count) {
		return repository.save(count);
	}

	@Override
	public List<SentimentFollowCount> readAll() {
		return repository.findAll();
	}

	@Override
	public SentimentFollowCount update(SentimentFollowCount count) {
		return repository.update(count);
	}

	@Override
	public void delete(SentimentFollowCount count) {
		repository.delete(count.getId());
	}

	@Override
	public Long getByComponentId(Long componentId) {
		SentimentFollowCount sentimentFollowCount= repository.findByComponentId(componentId);
		Long count=sentimentFollowCount.getFollowCount();
		return count;
	}
	

}
