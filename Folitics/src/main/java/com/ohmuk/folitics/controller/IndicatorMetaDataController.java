package com.ohmuk.folitics.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorMetaDataBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.IndicatorMetaData;
import com.ohmuk.folitics.service.IIndicatorMetaDataService;

@Controller
@RequestMapping("/indicatormetadata")
public class IndicatorMetaDataController {
    @Autowired
    private volatile IIndicatorMetaDataBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(IndicatorMetaDataController.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<IndicatorMetaData> add(@RequestBody IndicatorMetaData indicatormetadata) {
        logger.info("Inside IndicatorMetaDataController add method");
        try {
            indicatormetadata = businessDelegate.create(indicatormetadata);
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController add method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        if (indicatormetadata != null) {
            logger.info("Exiting from IndicatorMetaDataController add method");
            return new ResponseDto<IndicatorMetaData>(true, indicatormetadata);
        }
        logger.info("Exiting from IndicatorMetaDataController add method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to get all {@link IndicatorMetaData}
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorMetaData> getAll() {
        logger.info("Inside IndicatorMetaDataController getAll method");
        List<IndicatorMetaData> components = null;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in get all IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController getAll method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        if (components != null) {
            logger.info("Exiting from IndicatorMetaDataController getAll method");
            return new ResponseDto<IndicatorMetaData>(true, components);
        }
        logger.info("Exiting from IndicatorMetaDataController add method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to update {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorMetaData> edit(@RequestBody IndicatorMetaData indicatormetadata) {
        logger.info("Inside IndicatorMetaDataController edit method");
        try {
            indicatormetadata = businessDelegate.update(indicatormetadata);
        } catch (Exception exception) {
            logger.error("Exception in editing IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController edit method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        if (indicatormetadata != null) {
            logger.info("Exiting from IndicatorMetaDataController edit method");
            return new ResponseDto<IndicatorMetaData>(true, indicatormetadata);
        }
        logger.info("Exiting from IndicatorMetaDataController edit method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to soft delete {@link IndicatorMetaData} by id
     * @param id
     * @return {@link IndicatorMetaData}
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorMetaData> delete(Long id) {
        logger.info("Inside IndicatorMetaDataController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.info("Exiting from IndicatorMetaDataController delete method");
                return new ResponseDto<IndicatorMetaData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting by id IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController delete method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        logger.info("Exiting from IndicatorMetaDataController delete method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to soft delete {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorMetaData> delete(@RequestBody IndicatorMetaData indicatormetadata) {
        logger.info("Inside IndicatorMetaDataController delete method");
        try {
            if (businessDelegate.delete(indicatormetadata)) {
                logger.info("Exiting from IndicatorMetaDataController delete method");
                return new ResponseDto<IndicatorMetaData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController delete method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        logger.info("Exiting from IndicatorMetaDataController delete method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to hard delete {@link IndicatorMetaData}
     * @author Mayank Sharma
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     */
    @RequestMapping(value = "/deleteFromDB", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorMetaData> deleteFromDB(@RequestBody IndicatorMetaData indicatormetadata) {
        logger.info("Inside IndicatorMetaDataController deleteFromDB method");
        try {
            if (businessDelegate.deleteFromDB(indicatormetadata)) {
                logger.info("Exiting from IndicatorMetaDataController deleteFromDB method");
                return new ResponseDto<IndicatorMetaData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in hard deleting IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController deleteFromDB method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        logger.info("Exiting from IndicatorMetaDataController deleteFromDB method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to hard delete {@link IndicatorMetaData} by id
     * @author Mayank Sharma
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     */
    @RequestMapping(value = "/deleteFromDBById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorMetaData> deleteFromDBById(Long id) {
        logger.info("Inside IndicatorMetaDataController deleteFromDBById method");
        try {
            if (businessDelegate.deleteFromDBById(id)) {
                logger.info("Exiting from IndicatorMetaDataController deleteFromDBById method");
                return new ResponseDto<IndicatorMetaData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in hard deleting IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController deleteFromDBById method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        logger.info("Exiting from IndicatorMetaDataController deleteFromDBById method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

    /**
     * Web service is to find {@link IndicatorMetaData} by id
     * @param id
     * @return {@link ResponseDto < IndicatorMetaData >}
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorMetaData> find(Long id) {
        logger.info("Inside IndicatorMetaDataController find method");
        IndicatorMetaData indicatormetadata = null;
        try {
            indicatormetadata = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorMetaData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorMetaDataController find method");
            return new ResponseDto<IndicatorMetaData>(false);
        }
        if (indicatormetadata != null) {
            logger.info("Exiting from IndicatorMetaDataController find method");
            return new ResponseDto<IndicatorMetaData>(true, indicatormetadata);
        }
        logger.info("Exiting from IndicatorMetaDataController find method");
        return new ResponseDto<IndicatorMetaData>(false);
    }

}
