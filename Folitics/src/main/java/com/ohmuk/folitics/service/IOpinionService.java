package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.task.Task;

/**
 * Service interface for entity: {@link Opinion}
 * @author Abhishek
 *
 */
public interface IOpinionService extends IBaseService {

    /**
     * Method to save the com.ohmuk.folitics.jpa.entity.Opinion object using
     * com.ohmuk.folitics.jpa.repository.IOpinionRepository.save
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    public Opinion create(Opinion opinion) throws Exception;

    /**
     * Method to get object of com.ohmuk.folitics.jpa.entity.Opinion using
     * com.ohmuk.folitics.jpa.repository.IOpinionRepository.find by passing id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    public Opinion read(Long id)throws Exception;

    /**
     * Method to get all opinions using com.ohmuk.folitics.jpa.repository.IOpinionRepository.findAll
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Opinion>
     */
    public List<Opinion> readAll()throws Exception;

    /**
     * Method to update an object using com.ohmuk.folitics.jpa.repository.IOpinionRepository.save
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    public Opinion update(Opinion opinion)throws Exception;

    /**
     * Method to permanent delete an object using com.ohmuk.folitics.jpa.repository.IOpinionRepository.delete by passing
     * id
     * 
     * @author Abhishek
     * @param Long id
     * @return boolean
     */
    public boolean deleteFromDBById(Long id)throws Exception;

    /**
     * Method to permanent delete an object using com.ohmuk.folitics.jpa.repository.IOpinionRepository.delete by passing
     * object of com.ohmuk.folitics.jpa.entity.Opinion
     * 
     * @author Abhishek
     * @param boolean
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    public boolean deleteFromDB(Opinion opinion)throws Exception;
    
	/**
	 * @param user
	 * @return List<Opinion>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Opinion> getOpinionByUser(Long userId) throws MessageException,
			Exception;
}
