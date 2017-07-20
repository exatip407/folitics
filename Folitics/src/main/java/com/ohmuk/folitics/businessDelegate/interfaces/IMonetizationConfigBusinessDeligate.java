package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.MonetizationConfig;

public interface IMonetizationConfigBusinessDeligate {

	/**
     * Method to save the com.ohmuk.folitics.jpa.entity.MonetizationConfig object
     * 
     * @author 
     * @param com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * @return com.ohmuk.folitics.jpa.entity.MonetizationConfig
     */
	public MonetizationConfig create(MonetizationConfig monetization) throws MessageException,Exception;
	
	/**
     * Method to get object of com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * 
     * @author 
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.MonetizationConfig
     */
	public MonetizationConfig read(Long id) throws MessageException,Exception;
	
	
	 /**
     * Method to get all objects of com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * 
     * @author 
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.MonetizationConfig>
     */
	public List<MonetizationConfig>readAll() throws MessageException,Exception;
	
	/**
     * Method to update an object of com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * 
     * @author 
     * @param com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * @return com.ohmuk.folitics.jpa.entity.MonetizationConfig
     */
	public MonetizationConfig update(MonetizationConfig monetization) throws MessageException,Exception;
	
	/**
     * Method to permanent delete an object of com.ohmuk.folitics.jpa.entity.MonetizationConfig by
     * passing id
     * 
     * @author 
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.MonetizationConfig
     */
	public MonetizationConfig deleteById(Long id) throws MessageException,Exception;
	
	/**
     * Method to permanent delete an object of com.ohmuk.folitics.jpa.entity.MonetizationConfig by passing an object of com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * 
     * @author 
     * @param com.ohmuk.folitics.jpa.entity.MonetizationConfig
     * @return com.ohmuk.folitics.jpa.entity.MonetizationConfig
     */
	public MonetizationConfig delete(MonetizationConfig monetization) throws MessageException,Exception;

    /**
     * Method to set point status for an object of com.ohmuk.folitics.jpa.entity.MonetizationConfig by passing Long id and String status
     * 
     * @author 
     * @param String name
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.MonetizationConfig>
     */
   /* public MonetizationConfig setPointStatus(Long id, String status) throws MessageException,Exception;
    
    *//**
     * Method to set point status for an object of com.ohmuk.folitics.jpa.entity.MonetizationConfig by passing Long id and String status
     * 
     * @author 
     * @param String name
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.MonetizationConfig>
     */
    //public MonetizationConfig getPointsExpiringNext(Long id,Contest contest);
   
}
