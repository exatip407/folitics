package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.GPIPoint;

public interface IGpiPointBusinessDelegate {
    
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

}
