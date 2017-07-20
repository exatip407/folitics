package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.beans.ChangeIndicatorDataBean;
import com.ohmuk.folitics.beans.HeaderDataBean;
import com.ohmuk.folitics.beans.TrackPromiseBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorDataBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorThresholdBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;
import com.ohmuk.folitics.service.IIndicatorDataService;
import com.ohmuk.folitics.service.IndicatorDataService;

@Controller
@RequestMapping("/indicatordata")
public class IndicatorDataController {

    @Autowired
    private volatile IIndicatorDataBusinessDelegate businessDelegate;
    
    @Autowired
    private volatile IIndicatorThresholdBusinessDelegate threshHoldBusinessDelegate;


    private static Logger logger = LoggerFactory.getLogger(IndicatorDataController.class);

    /**
     * Web service is to add {@link IndicatorData}
     * @param indicatordata
     * @return {@link ResponseDto<IndicatorData>}
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<IndicatorData> add(@RequestBody IndicatorData indicatordata) {
        logger.info("Inside IndicatorDataController add method");
        try {
            indicatordata = businessDelegate.create(indicatordata);
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController add method");
            return new ResponseDto<IndicatorData>(false);
        }
        if (indicatordata != null) {
            logger.debug("IndicatorData is added");
            logger.info("Exiting from IndicatorDataController add method");
            return new ResponseDto<IndicatorData>(true, indicatordata);
        }
        logger.debug("IndicatorData is not added");
        logger.info("Exiting from IndicatorDataController add method");
        return new ResponseDto<IndicatorData>(false);
    }

    /**
     * Web service to get all {@link IndicatorData}
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorData> getAll() {
        logger.info("Inside IndicatorDataController getAll method");
        List<IndicatorData> components = null;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in get all IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController getAll method");
            return new ResponseDto<IndicatorData>(false);
        }
        if (components != null) {
            logger.debug("List of IndicatorData is found");
            logger.info("Exiting from IndicatorDataController getAll method");
            return new ResponseDto<IndicatorData>(true, components);
        }
        logger.debug("List of IndicatorData is not found");
        logger.info("Exiting from IndicatorDataController getAll method");
        return new ResponseDto<IndicatorData>(false);
    }

    /**
     * Web service is to update {@link IndicatorData}
     * @param indicatordata
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorData> edit(@RequestBody IndicatorData indicatordata) {
        logger.info("Inside IndicatorDataController edit method");
        try {
            indicatordata = businessDelegate.update(indicatordata);
        } catch (Exception exception) {
            logger.error("Exception in editing IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController edit method");
            return new ResponseDto<IndicatorData>(false);
        }
        if (indicatordata != null) {
            logger.debug("IndicatorData is edited");
            logger.info("Exiting from IndicatorDataController edit method");
            return new ResponseDto<IndicatorData>(true, indicatordata);
        }
        logger.debug("IndicatorData is not edited");
        logger.info("Exiting from IndicatorDataController edit method");
        return new ResponseDto<IndicatorData>(false);
    }

    /**
     * Web service is to delete {@link IndicatorData} by id
     * @param id
     * @return
     */
    @RequestMapping(value = "/deletebyid", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorData> delete(Long id) {
        logger.info("Inside IndicatorDataController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.debug("IndicatorData is soft deleted");
                logger.info("Exiting from IndicatorDataController delete method");
                return new ResponseDto<IndicatorData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController delete method");
            return new ResponseDto<IndicatorData>(false);
        }
        logger.debug("IndicatorData is not deleted");
        logger.info("Exiting from IndicatorDataController delete method");
        return new ResponseDto<IndicatorData>(false);
    }

    /**
     * Web service is to soft delete {@link IndicatorData}
     * @param indicatordata
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorData> delete(@RequestBody IndicatorData indicatordata) {
        logger.info("Inside IndicatorDataController delete method");
        try {
            if (businessDelegate.delete(indicatordata)) {
                logger.debug("IndicatorData is soft deleted");
                logger.info("Exiting from IndicatorDataController delete method");
                return new ResponseDto<IndicatorData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController delete method");
            return new ResponseDto<IndicatorData>(false);
        }
        logger.debug("IndicatorData is not deleted");
        logger.info("Exiting from IndicatorDataController delete method");
        return new ResponseDto<IndicatorData>(false);
    }

    /**
     * Web service is to get {@link IndicatorData} by id
     * @param id
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorData> find(Long id) {
        logger.info("Inside IndicatorDataController find method");
        IndicatorData indicatordata = null;
        try {
            indicatordata = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController find method");
            return new ResponseDto<IndicatorData>(false);
        }
        if (indicatordata != null) {
            logger.debug("IndicatorData with id: " + indicatordata.getId() + " is found");
            logger.info("Exiting from IndicatorDataController find method");
            return new ResponseDto<IndicatorData>(true, indicatordata);
        }
        logger.debug("IndicatorData is not found");
        logger.info("Exiting from IndicatorDataController find method");
        return new ResponseDto<IndicatorData>(false);
    }

    /**
     * Web service is to get {@link HeaderDataBean}
     * @return
     */
    @RequestMapping(value = "/getHeaderData", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<HeaderDataBean> getHeaderData() {
        logger.info("Inside IndicatorDataController add method");
        List<HeaderDataBean> headerDataBeanList = null;
        try {
            headerDataBeanList = businessDelegate.readAllByThresholdGroup();
        } catch (Exception exception) {
            logger.error("Exception in getting get header data IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController getHeaderData method");
            return new ResponseDto<HeaderDataBean>(false);
        }
        if (headerDataBeanList != null) {
            logger.debug("HeaderData is found");
            logger.info("Exiting from IndicatorDataController getHeaderData method");
            return new ResponseDto<HeaderDataBean>(true, headerDataBeanList);
        }
        logger.debug("HeaderData is not found");
        logger.info("Exiting from IndicatorDataController getHeaderData method");
        return new ResponseDto<HeaderDataBean>(false);
    }

    /**
     * Web service is to get {@link TrackPromiseBean}
     * @return
     */
    @RequestMapping(value = "/getPromiseData", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<TrackPromiseBean> getPromiseData() {
        logger.info("Inside IndicatorDataController add method");
        List<TrackPromiseBean> trackPromiseBeanList = null;
        try {
            trackPromiseBeanList = businessDelegate.readAllBySubCategory();
        } catch (Exception exception) {
            logger.error("Exception in getting promise data IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController getPromiseData method");
            return new ResponseDto<TrackPromiseBean>(false);
        }
        if (trackPromiseBeanList != null) {
            logger.debug("TrackPromise is found");
            logger.info("Exiting from IndicatorDataController getPromiseData method");
            return new ResponseDto<TrackPromiseBean>(true, trackPromiseBeanList);
        }
        logger.debug("TrackPromise is not found");
        logger.info("Exiting from IndicatorDataController getPromiseData method");
        return new ResponseDto<TrackPromiseBean>(false);
    }

    /**
     * Web service is to hard delete {@link IndicatorData} by id
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteFromDB", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorData> deleteFromDB(Long id) {
        logger.info("Inside IndicatorDataController add method");
        try {
            if (businessDelegate.deleteFromDB(id)) {
                logger.debug("IndicatorData is hard deleted");
                logger.info("Exiting from IndicatorDataController deleteFromDB method");
                return new ResponseDto<IndicatorData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in hard deleting IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController deleteFromDB method");
            return new ResponseDto<IndicatorData>(false);
        }

        logger.debug("IndicatorData is not deleted");
        logger.info("Exiting from IndicatorDataController deleteFromDB method");
        return new ResponseDto<IndicatorData>(false);
    }
    
    @RequestMapping(value = "/getIndicatorDataForVerdict", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<ChangeIndicatorDataBean> getIndicatorDataForVerdict() {
    	  logger.info("Inside IndicatorDataController getAll method");
    	  List<ChangeIndicatorDataBean> components = null;
          try {
              components = businessDelegate.findIndicatorDataForVerdict();
          } catch (Exception exception) {
              logger.error("Exception in get all IndicatorData");
              logger.error("Exception: " + exception);
              logger.info("Exiting from IndicatorDataController getAll method");
              return new ResponseDto<ChangeIndicatorDataBean>(false);
          }
          if (components != null) {
              logger.debug("List of IndicatorData is found");
              logger.info("Exiting from IndicatorDataController getAll method");
              return new ResponseDto<ChangeIndicatorDataBean>(true, components);
          }
          logger.debug("List of IndicatorData is not found");
          logger.info("Exiting from IndicatorDataController getAll method");
          return new ResponseDto<ChangeIndicatorDataBean>(false);
    }
    
    
    @RequestMapping(value = "/getIndicatorDataWithBaseLine", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorData> getIndicatorDataWithBaseLine(Long categoryId) {
    	logger.info("Inside IndicatorDataController find method");
    	
    	Category category = new Category();
    	category.setId(categoryId);
        IndicatorData indicatordata = null;
        try {
            List<IndicatorThreshold> indicatorThreshHoldList  = threshHoldBusinessDelegate.findByCategory(category);
            List<IndicatorData> indicatorDataList = businessDelegate.findByCategory(category);
           
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorDataController find method");
            return new ResponseDto<IndicatorData>(false);
        }
        if (indicatordata != null) {
            logger.debug("IndicatorData with id: " + indicatordata.getId() + " is found");
            logger.info("Exiting from IndicatorDataController find method");
            return new ResponseDto<IndicatorData>(true, indicatordata);
        }
        logger.debug("IndicatorData is not found");
        logger.info("Exiting from IndicatorDataController find method");
        return new ResponseDto<IndicatorData>(false);
    }
}
