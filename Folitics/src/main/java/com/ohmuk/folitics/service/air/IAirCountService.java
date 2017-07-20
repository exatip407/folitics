package com.ohmuk.folitics.service.air;

import java.util.List;

/**
 * Service interface for LikeCount
 * @author Abhishek
 *
 */
public interface IAirCountService<T> {

    public T create(T t);

    public List<T> readAll();

    public T update(T t);

    public T delete(T t);

    public Long getAirCountByComponentId(Long componentId);

}
