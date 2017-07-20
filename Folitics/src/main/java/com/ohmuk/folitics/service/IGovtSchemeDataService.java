package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;

public interface IGovtSchemeDataService {
	

    /**
     * This method to add GovtSchemeData by calling
     * @return GovtSchemeData
     * @throws Exception
     */
	
    public GovtSchemeData create(GovtSchemeData govtschemedata) throws Exception;
    
    /**
     * This mehtod to get GovtSchemeData by id
     * @return GovtSchemeData
     * @throws Exception
     */

    public GovtSchemeData read(Long id) throws Exception;

    /**
     * This method give list of all goverment scheme
     * @return List<GovtSchemeData>
     * @throws Exception
     */

    public List<GovtSchemeData> readAll() throws Exception;

    /**
     * This method update govtschemedata by calling
     * @return Govtschemedata
     * @throws Exception
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
