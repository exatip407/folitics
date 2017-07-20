package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.EventReportScore;
/**
 * 
 * @author Mayank Sharma
 *
 */
public interface IEventReportScoreService {

    /**
     * This method is to add EventReportScore
     * 
     * @author Mayank Sharma
     * @return EventReportScore
     * @throws Exception
     */
    public EventReportScore create(EventReportScore event) throws Exception;

    /**
     * This method is to get EventReportScore by id
     * 
     * @author Mayank Sharma
     * @return EventReportScore
     * @throws Exception
     */

    public EventReportScore read(Long id) throws Exception;
    
    /**
     * This method is to get all EventReportScore
     * 
     * @author Mayank Sharma
     * @return EventReportScore
     * @throws Exception
     */

    public List<EventReportScore> readAll() throws Exception;

    /**
     * This method is to update EventReportScore
     * 
     * @author Mayank Sharma
     * @return boolean
     * @throws Exception
     */

    public EventReportScore update(EventReportScore event) throws Exception;
    /**
     * This method is to soft delete EventReportScore
     * @author Mayank Sharma
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * This method is to hard delete EventReportScore
     * 
     * @author Mayank Sharma
     * @return EventReportScore
     * @throws Exception
     */
    public boolean delete(EventReportScore event) throws Exception;
    /**
     * This method is to hard delete EventReportScore
     * 
     * @author Mayank Sharma
     * @return boolean
     * @throws Exception
     */

    boolean deleteFromDB(Long id) throws Exception;

}
