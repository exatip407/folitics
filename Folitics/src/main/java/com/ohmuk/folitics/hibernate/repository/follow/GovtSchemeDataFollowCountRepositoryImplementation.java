package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.follow.GovtSchemeDataFollowCount;

@Component
@Repository
public class GovtSchemeDataFollowCountRepositoryImplementation implements
		IGovtSchemeDataFollowCountRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataFollowCount save(
			GovtSchemeDataFollowCount govtSchemeDataFollowCount) {
		GovtSchemeDataCountId id = (GovtSchemeDataCountId) getSession().save(
				govtSchemeDataFollowCount);
		govtSchemeDataFollowCount = (GovtSchemeDataFollowCount) getSession()
				.get(GovtSchemeDataFollowCount.class, id);
		return govtSchemeDataFollowCount;
	}

	@Override
	public GovtSchemeDataFollowCount update(
			GovtSchemeDataFollowCount govtSchemeDataFollowCount) {
		govtSchemeDataFollowCount = (GovtSchemeDataFollowCount) getSession()
				.merge(govtSchemeDataFollowCount);
		getSession().update(govtSchemeDataFollowCount);
		govtSchemeDataFollowCount = (GovtSchemeDataFollowCount) getSession()
				.get(GovtSchemeDataFollowCount.class,
						govtSchemeDataFollowCount.getId());
		return govtSchemeDataFollowCount;
	}

	@Override
	public List<GovtSchemeDataFollowCount> findAll() {
		@SuppressWarnings("unchecked")
		List<GovtSchemeDataFollowCount> list = getSession().createCriteria(
				GovtSchemeDataFollowCount.class).list();
		return list;
	}

	@Override
	public GovtSchemeDataFollowCount findByComponentId(Long id) {
		Criteria criteria = getSession().createCriteria(
				GovtSchemeDataFollowCount.class);
		criteria.add(Restrictions.eq("id.opinion.id", id));
		GovtSchemeDataFollowCount followCount = (GovtSchemeDataFollowCount) criteria
				.uniqueResult();
		return followCount;
	}

	@Override
	public GovtSchemeDataFollowCount find(GovtSchemeDataCountId id) {
		return (GovtSchemeDataFollowCount) getSession().get(
				GovtSchemeDataFollowCount.class, id);
	}

	@Override
	public void delete(GovtSchemeDataCountId id) {
		GovtSchemeDataFollowCount followCount = (GovtSchemeDataFollowCount) getSession()
				.get(GovtSchemeDataFollowCount.class, id);
		getSession().delete(followCount);

	}

}
