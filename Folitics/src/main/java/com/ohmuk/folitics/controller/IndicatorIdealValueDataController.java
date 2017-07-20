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

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorIdealValueDataBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.IndicatorIdealValueData;

@Controller
@RequestMapping("/indicatoridealvaluedata")
public class IndicatorIdealValueDataController {
    @Autowired
    public volatile IIndicatorIdealValueDataBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(IndicatorIdealValueDataController.class);

    /**
     * Web service is to add {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return {@link ResponseDto < IndicatorIdealValueData > }
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<IndicatorIdealValueData> add(
            @RequestBody IndicatorIdealValueData indicatoridealvaluedata) {
        logger.info("Inside IndicatorIdealValueDataController add method");
        try {
            indicatoridealvaluedata = businessDelegate.create(indicatoridealvaluedata);
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorIdealValueData");
            logger.error("Exception:" + exception);
            logger.info("Exiting from IndicatorIdealValueDataController add method");
            return new ResponseDto<IndicatorIdealValueData>(false);
        }
        if (indicatoridealvaluedata != null) {
            logger.debug("IndicatorIdealValueData is sucessfully add");
            logger.info("Exiting from IndicatorIdealValueDataController add method");
            return new ResponseDto<IndicatorIdealValueData>(true, indicatoridealvaluedata);
        }
        logger.debug("IndicatorIdealValueData is not add");
        logger.info("Exiting from IndicatorIdealValueDataController add method");
        return new ResponseDto<IndicatorIdealValueData>(false);
    }

    /**
     * Web service is to get all {@link IndicatorIdealValueData}
     * @return {@link ResponseDto < IndicatorIdealValueData > }
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorIdealValueData> getAll() {
        logger.info("Inside IndicatorIdealValueDataController getAll method");
        List<IndicatorIdealValueData> components = null;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorIdealValueData");
            logger.error("Exception:" + exception);
            logger.info("Exiting from IndicatorIdealValueDataController getAll method");
            return new ResponseDto<IndicatorIdealValueData>(false);
        }
        if (components != null) {
            logger.debug("IndicatorIdealValueData's is found");
            logger.info("Exiting from IndicatorIdealValueDataController getAll method");
            return new ResponseDto<IndicatorIdealValueData>(true, components);
        }
        logger.debug("No IndicatorIdealValueData is found");
        logger.info("Exiting from IndicatorIdealValueDataController getAll method");
        return new ResponseDto<IndicatorIdealValueData>(false);
    }

    /**
     * Web service is to update {@link IndicatorIdealValueData}
     * @param indicatorIdealValueData
     * @return {@link ResponseDto < IndicatorIdealValueData > }
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorIdealValueData> edit(
            @RequestBody IndicatorIdealValueData indicatorIdealValueData) {
        logger.info("Inside IndicatorIdealValueDataController edit method");
        try {
            indicatorIdealValueData = businessDelegate.update(indicatorIdealValueData);
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorIdealValueData");
            logger.error("Exception:" + exception);
            logger.info("Exiting from IndicatorIdealValueDataController edit method");
            return new ResponseDto<IndicatorIdealValueData>(false);
        }
        if (indicatorIdealValueData != null) {
            logger.debug("Indicator is update of id: " + indicatorIdealValueData.getId());
            logger.info("Exiting from IndicatorIdealValueDataController edit method");
            return new ResponseDto<IndicatorIdealValueData>(true, indicatorIdealValueData);
        }
        logger.debug("indicatorIdealValueData is not update");
        logger.info("Exiting from IndicatorIdealValueDataController edit method");
        return new ResponseDto<IndicatorIdealValueData>(false);
    }

    /**
     * Web service is to hard delete {@link IndicatorIdealValueData} by id
     * @param id
     * @return {@link ResponseDto < IndicatorIdealValueData > }
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorIdealValueData> delete(Long id) {
        logger.info("Inside IndicatorIdealValueDataController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.debug("IndicatorIdealValueData is delete");
                logger.info("Exiting from IndicatorIdealValueDataController delete method");
                return new ResponseDto<IndicatorIdealValueData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorIdealValueData");
            logger.error("Exception:" + exception);
            logger.info("Exiting from IndicatorIdealValueDataController delete method");
            return new ResponseDto<IndicatorIdealValueData>(false);
        }
        logger.debug("IndicatorIdealValueData is not delete");
        logger.info("Exiting from IndicatorIdealValueDataController delete method");
        return new ResponseDto<IndicatorIdealValueData>(false);
    }

    /**
     * Web service is to soft delete {@link IndicatorIdealValueData}
     * @param indicatorIdealValueData
     * @return {@link ResponseDto < IndicatorIdealValueData > }
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorIdealValueData> delete(
            @RequestBody IndicatorIdealValueData indicatorIdealValueData) {
        logger.info("Inside IndicatorIdealValueDataController delete method");
        try {
            if (businessDelegate.delete(indicatorIdealValueData)) {
                logger.debug("IndicatorIdealValueData is delete with id: " + indicatorIdealValueData.getId());
                logger.info("Exiting from IndicatorIdealValueDataController delete method");
                return new ResponseDto<IndicatorIdealValueData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorIdealValueData");
            logger.error("Exception:" + exception);
            logger.info("Exiting from IndicatorIdealValueDataController delete method");
            return new ResponseDto<IndicatorIdealValueData>(false);
        }
        logger.debug("indicatorIdealValueData is not deleted");
        logger.info("Exiting from IndicatorIdealValueDataController delete method");
        return new ResponseDto<IndicatorIdealValueData>(false);
    }

    /**
     * Web service is to get {@link IndicatorIdealValueData} by id
     * @param id
     * @return {@link ResponseDto < IndicatorIdealValueData > }
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorIdealValueData> find(Long id) {
        logger.info("Inside IndicatorIdealValueDataController find method");
        IndicatorIdealValueData indicatorIdealValueData = null;
        try {
            indicatorIdealValueData = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding IndicatorIdealValueData");
            logger.error("Exception:" + exception);
            logger.info("Exiting from IndicatorIdealValueDataController find method");
            return new ResponseDto<IndicatorIdealValueData>(false);
        }
        if (indicatorIdealValueData != null) {
            logger.debug("IndicatorIdealValueData is found with id: " + indicatorIdealValueData.getId());
            logger.info("Exiting from IndicatorIdealValueDataController find method");
            return new ResponseDto<IndicatorIdealValueData>(true, indicatorIdealValueData);
        }
        logger.debug("IndicatorIdealValueData is not found");
        logger.info("Exiting from IndicatorIdealValueDataController find method");
        return new ResponseDto<IndicatorIdealValueData>(false);
    }

}
