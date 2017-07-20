package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Milestone;

/**
 * 
 * @author Mayank Sharma
 *
 */
public interface IMilestoneService {

    /**
     * Method is to add {@link Milestone} on {@link GPIPoint}
     * @param id
     * @param milestone
     * @return {@link Milestone}
     * @throws Exception
     */
    public Milestone add(Long id, Milestone milestone) throws Exception;

    /**
     * Method is used to delete {@link Milestone} by gpiId
     * @param gpiId
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long gpiId) throws Exception;

    /**
     * Method is to update {@link Milestone}
     * @param milestone
     * @return {@link Milestone}
     * @throws Exception
     */
    public Milestone update(Milestone milestone) throws Exception;

    /**
     * Method is to get all {@link Milestone}
     * @return {@link List < Milestone >}
     * @throws Exception
     */
    public List<Milestone> findAll() throws Exception;

    /**
     * Method is to get {@link Milestone} on {@link GPIPoint}
     * @param gpiId
     * @return
     * @throws Exception
     */
    public Milestone findByGpi(Long gpiId) throws Exception;

}
