package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.task.Task;

/**
 * @author Abhishek
 *
 */
public interface IOpinionBusinessDelegate {

    /**
     * Business delegate method to create opinion by calling com.ohmuk.folitics.service.IOpinionService.create
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion Opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     * @throws Exception
     */
    public Opinion create(Opinion opinion)throws Exception;

    /**
     * Business Delegate method to get opinion object using com.ohmuk.folitics.service.IOpinionService.read by passing
     * the id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     * @throws Exception
     */
    public Opinion read(Long id)throws Exception;

    /**
     * Business Delegate method to get opinion object using com.ohmuk.folitics.service.IOpinionService.readAll
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Opinion>
     * @throws Exception
     */
    public List<Opinion> readAll()throws Exception;

    /**
     * Business Delegate method to update opinion using com.ohmuk.folitics.service.IOpinionService.update
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     * @throws Exception
     */
    public Opinion update(Opinion opinion)throws Exception;

    /**
     * Business Delegate method to soft delete an opinion by giving id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     * @throws Exception
     */
    public Opinion delete(Long id)throws Exception;

    /**
     * Business Delegate method to soft delete an opinion by giving object of com.ohmuk.folitics.jpa.entity.Opinion
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     * @throws Exception
     */
    public Opinion delete(Opinion opinion)throws Exception;

    /**
     * Business Delegate method to permanent delete an opinion using com.ohmuk.folitics.service.IOpinionService.delete
     * by passing id
     * 
     * @author Abhishek
     * @param Long id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Long id)throws Exception;

    /**
     * Business Delegate method to permanent delete an opinion using com.ohmuk.folitics.service.IOpinionService.delete
     * by passing object of com.ohmuk.folitics.jpa.entity.Opinion
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Opinion opinion)throws Exception;
    
    public List<Opinion> getTopMostOpinion() throws Exception;
    
	/**
	 * @param userId
	 * @return List<Opinion>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<Opinion> getOpinionByUser(Long userId) throws MessageException,
			Exception;
}
