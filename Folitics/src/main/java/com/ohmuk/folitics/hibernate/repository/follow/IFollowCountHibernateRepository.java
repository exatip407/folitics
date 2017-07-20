package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

public interface IFollowCountHibernateRepository<T> {

    public T save(T t);

    public T update(T t);

    public List<T> findAll();

    public T findByComponentId(Long id);

}
