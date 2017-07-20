package com.ohmuk.folitics.service.module.like;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.ComponentModuleStorage;

/**
 * Service interface for {@link ComponentModuleStorage}
 * 
 * @author Jahid Ali
 * 
 *
 */
public interface IComponentModuleService {

	/**
     * This method is to read ComponentModuleStorage by passing id
     * 
     * @author 
     * @return ComponentModuleStorage
     * @throws Exception
     */
    public ComponentModuleStorage read(Long id);

    /**
     * This method is to readAll ComponentModuleStorage
     * 
     * @author 
     * @return ComponentModuleStorage
     * @throws Exception
     */
    public List<ComponentModuleStorage> readAll();

    
}
