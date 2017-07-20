package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;

public interface IUserMonetizationService {

	/**
     * Method to maintain history of users action for com.ohmuk.folitics.jpa.entity.UserMonetization by passing Long id and String status
     * 
     * @author 
     * @param monetizationDataBean
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Monetization>
     */
    public UserMonetization addAction(UserMonetization userMonetization) throws MessageException,Exception;
    
    /**
     * Method to fetch records by passing monetization id
     * 
     * @author 
     * @param Long id
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Monetization>
     */
    public UserMonetization getById(Long id) throws MessageException,Exception;
    
    /**
     * Method to fetch records by passing user id
     * 
     * @author 
     * @param Long id
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Monetization>
     */
    public List<UserMonetization> getByUserId(Long id) throws MessageException,Exception;
    
    /**
     * Method to fetch records by passing startDate and endDate
     * 
     * @author 
     * @param Timestamp startdate,Timestamp endDate
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Monetization>
     */
    public List<UserMonetization> getByDate(Timestamp startDate,Timestamp endDate) throws MessageException,Exception;
}
