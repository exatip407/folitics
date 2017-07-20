package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;

/**
 * Business Delegate interface for entity: {@link GovtSchemeData}
 * 
 */
public interface IGovtSchemeDataBusinessDelegate {
	  /**
     * Business delegate method to create GovtScheme
     * @param com.ohmuk.folitics.hibernate.entity.GovtSchemeData
     * @param com.ohmuk.folitics.hibernate.entity.GovtSchemeData
     */
    public GovtSchemeData create(GovtSchemeData govtschemedata) throws Exception;

    /**
     * Business Delegate method to get GovtSchemeData by passing id     
     * @param Long id
     * @return com.ohmuk.folitics.hibernate.entity.GovtSchemeData
     */

    public GovtSchemeData read(Long id) throws Exception;
    

    /**
     * Business Delegate method to get all GovtSchemeData  
     * @return java.util.List<com.ohmuk.folitics.hibernate.entity.GovtSchemeData>
     */

    public List<GovtSchemeData> readAll() throws Exception;
    

    /**
     * Business Delegate method to update GovtSchemeData      
     * @param com.ohmuk.folitics.hibernate.entity.GovtSchemeData
     * @return com.ohmuk.folitics.hibernate.entity.GovtSchemeData
     */

    public GovtSchemeData update(GovtSchemeData govtschemedata) throws Exception;
    

    /**
     * This method is to soft delete GovtSchemeData by id
     * @return boolean
     * @throws Exception
     */

    public boolean delete(Long id) throws Exception;
    
    /**
     * This method is to soft delete GovtSchemeData
     * @return GovtSchemeData
     * @throws Exception
     */
    public boolean delete(GovtSchemeData govtschemedata) throws Exception;
    /**
     * This method is to used hard delete GovtSchemeData by id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Long id) throws Exception;

}
