package com.ohmuk.folitics.service.follow;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCount;
import com.ohmuk.folitics.hibernate.repository.follow.IOpinionFollowCountRepository;

@Service
@Transactional
public class OpinionFollowCountService implements
		IFollowCountService<OpinionFollowCount> {

	@Autowired
	private IOpinionFollowCountRepository repository;

	@Override
	public OpinionFollowCount create(OpinionFollowCount opinionFollowCount)
			throws MessageException {
		return repository.save(opinionFollowCount);
	}

	@Override
	public List<OpinionFollowCount> readAll() {
		return repository.findAll();
	}

	@Override
	public OpinionFollowCount update(OpinionFollowCount opinionFollowCount)
			throws MessageException {
		return repository.update(opinionFollowCount);
	}

	@Override
	public void delete(OpinionFollowCount opinionFollowCount)
			throws MessageException {
		repository.delete(opinionFollowCount.getId());

	}

	@Override
	public Long getByComponentId(Long componentId) throws MessageException {
		OpinionFollowCount opinionFollowCount = repository
				.findByComponentId(componentId);
		Long count = opinionFollowCount.getFollowCount();
		return count;
	}

}
