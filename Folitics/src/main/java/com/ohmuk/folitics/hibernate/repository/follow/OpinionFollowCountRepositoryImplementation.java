package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCountId;

@Component
@Repository
public class OpinionFollowCountRepositoryImplementation implements
		IOpinionFollowCountRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionFollowCount save(OpinionFollowCount opinionFollowCount) {

		OpinionFollowCountId id = (OpinionFollowCountId) getSession().save(
				opinionFollowCount);
		opinionFollowCount = (OpinionFollowCount) getSession().get(
				OpinionFollowCount.class, id);
		return opinionFollowCount;
	}

	@Override
	public OpinionFollowCount update(OpinionFollowCount opinionFollowCount) {
		opinionFollowCount = (OpinionFollowCount) getSession().merge(
				opinionFollowCount);
		getSession().update(opinionFollowCount);
		opinionFollowCount = (OpinionFollowCount) getSession().get(
				OpinionFollowCount.class, opinionFollowCount.getId());
		return opinionFollowCount;
	}

	@Override
	public List<OpinionFollowCount> findAll() {
		@SuppressWarnings("unchecked")
		List<OpinionFollowCount> list = getSession().createCriteria(
				OpinionFollowCount.class).list();
		return list;
	}

	@Override
	public OpinionFollowCount findByComponentId(Long id) {
		Criteria criteria = getSession().createCriteria(
				OpinionFollowCount.class);
		criteria.add(Restrictions.eq("id.opinion.id", id));
		OpinionFollowCount followCount = (OpinionFollowCount) criteria
				.uniqueResult();
		return followCount;
	}

	@Override
	public OpinionFollowCount find(OpinionFollowCountId id) {
		return (OpinionFollowCount) getSession().get(OpinionFollowCount.class,
				id);
	}

	@Override
	public void delete(OpinionFollowCountId id) {
		OpinionFollowCount followCount = (OpinionFollowCount) getSession().get(
				OpinionFollowCount.class, id);
		getSession().delete(followCount);

	}

}
