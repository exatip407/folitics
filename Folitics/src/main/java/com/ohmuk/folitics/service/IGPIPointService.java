package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.List;

import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.Opinion;

public interface IGPIPointService {
    /**
     * This method to add GPIPoint
     * @author Mayank Sharma
     * @return GPIPoint
     * @throws Exception
     */
  
    public GPIPoint create(GPIPoint gpiPoint) throws Exception;
    /**
     * This method to get GPIPoint by id
     * @author Mayank Sharma
     * @return Exception
     */
  
    public GPIPoint read(Long id) throws Exception;
    /**
     * This method is to get all GPI point
     * @author Mayank Sharma
     * @return GPIPoint
     * @throws Exception
     */
   
    public List<GPIPoint> readAll() throws Exception;

    /**
     * This method is to update GPIPoint
     * @author Mayank Sharma
     * @return GPIPoint
     * 
     */

    public GPIPoint update(GPIPoint gpiPoint) throws Exception;

    /**
     * This method is to hard delete GPIPoint
     * @author Mayank Sharma
     * @return boolean
     */
    public boolean hardDelete(Long id) throws Exception;
  
    /**
     * This method is to soft delete GPIPoint
     * @author Mayank Sharma
     * @return GPIPoint
     */

    public boolean delete(GPIPoint gpiPoint) throws Exception;

    /**
     * This method is to get all GPIPoint
     * @author Mayank Sharma
     * @return List<GPIPoint>
     */
    public List<GPIPoint> getALLGPIpoints() throws Exception;
    
    /**
     * This method is to  soft delete GPIPoint by id
     * @author Mayank Sharma
     * @return boolean
     */
    public boolean delete(Long id) throws Exception;
   
    /**
     * This method is to return the EventReportScore between start time and end time
     * @param startTime
     * @param endTime
     * @return List of EventReportScore
     * @throws Exception
     */
    public List<EventReportScore> reportEventAggregation(Timestamp startTime, Timestamp endTime) throws Exception ;
    

    /**
     * This method is to aggregate indicator value by time
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public  List<IndicatorData> indicatorChangeValueAggregation(Timestamp startTime, Timestamp endTime) throws Exception;
    

    /**
     * This method is to get opinion by timestamp
     * @param startTime
     * @param endTime
     * @return List<Opinion>
     * @throws Exception
     */
    
    public List<Opinion> getOpinions(Timestamp startTime, Timestamp endTime) throws Exception;
}
