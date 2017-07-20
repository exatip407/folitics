package com.ohmuk.folitics.hibernate.repository.air;

import java.util.List;

/**
 * Hibernate repository interface for Like Count
 * @author Abhishek
 *
 * @param <T>
 */
public interface IAirCountHibernateRepository<T> {

    public T save(T t);

    public T update(T t);

    public List<T> findAll();

    public T findByComponentId(Long id);

}
