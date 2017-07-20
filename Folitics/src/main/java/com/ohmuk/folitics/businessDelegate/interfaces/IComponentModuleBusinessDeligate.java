package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.ComponentModuleStorage;

/**
 * Business Deligate interface for {@link ComponentModuleStorage}
 * 
 * @author
 * 
 *
 */
public interface IComponentModuleBusinessDeligate {
	/**
	 * This method is to read ComponentModuleStorage by passing id
	 * 
	 * @author
	 * @return ComponentModuleStorage
	 * @throws MessageException
	 * @throws Exception
	 */
	public ComponentModuleStorage read(Long id) throws MessageException;

	/**
	 * This method is to readAll ComponentModuleStorage
	 * 
	 * @author
	 * @return List<ComponentModuleStorage>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<ComponentModuleStorage> readAll() throws MessageException;
}
