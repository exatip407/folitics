package com.ohmuk.folitics.businessDelegate.interfaces;

import java.sql.Timestamp;
import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;

public interface IUserMonetizationBusinessDeligate {

	 /**
     * Method to add records of credited points in user account.
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
     * 
     * @author 
     * @param Timestamp startDate, Timestamp endDate
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Monetization>
     */
    public List<UserMonetization> getByDate(Timestamp startDate,Timestamp endDate) throws MessageException,Exception;

}
