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

import com.ohmuk.folitics.businessDelegate.interfaces.IGovtSchemeDataBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.service.IGovtSchemeDataService;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/govtschemedata")
public class GovtSchemeDataController {
    @Autowired
    private volatile IGovtSchemeDataBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataController.class);

    /**
     * This method to add GovtSchemeData
     * @author Mayank Sharma
     * @param govtschemedata
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<GovtSchemeData> add(@RequestBody GovtSchemeData govtschemedata) {
        logger.info("Inside GovtSchemeDataController add method");
        try {
            govtschemedata = businessDelegate.create(govtschemedata);
        } catch (Exception exception) {
            logger.error("Exception in adding GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController add method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        if (govtschemedata != null) {
            logger.info("Exiting from GovtSchemeDataController add method");
            return new ResponseDto<GovtSchemeData>(true, govtschemedata);
        }
        logger.info("Exiting from GovtSchemeDataController add method");
        return new ResponseDto<GovtSchemeData>(false);
    }

    /**
     * This method to get all GovtSchemeData
     * 
     * @author Mayank Sharma
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GovtSchemeData> getAll() {
        logger.info("Inside GovtSchemeDataController getAll method");
        List<GovtSchemeData> components;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in get all GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController getAll method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        if (components != null) {
            logger.info("Exiting from GovtSchemeDataController getAll method");
            return new ResponseDto<GovtSchemeData>(true, components);
        }
        logger.info("Exiting from GovtSchemeDataController getAll method");
        return new ResponseDto<GovtSchemeData>(false);
    }

    /**
     * This method to update GovtSchemeData
     * @author Mayank Sharma
     * @param govtschemedata
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<GovtSchemeData> edit(@RequestBody GovtSchemeData govtschemedata) {
        logger.info("Inside GovtSchemeDataController edit method");
        try {
            govtschemedata = businessDelegate.update(govtschemedata);
        } catch (Exception exception) {
            logger.error("Exception in editing GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController edit method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        if (govtschemedata != null) {
            logger.info("Exiting from GovtSchemeDataController edit method");
            return new ResponseDto<GovtSchemeData>(true, govtschemedata);
        }
        logger.info("Exiting from GovtSchemeDataController edit method");
        return new ResponseDto<GovtSchemeData>(false);
    }

    /**
     * This method to delete GovtSchemeData by id
     * @author Mayank Sharma
     * @param id
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/deletebyid", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<GovtSchemeData> delete(Long id) {
        logger.info("Inside GovtSchemeDataController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.info("Exiting from GovtSchemeDataController delete method");
                return new ResponseDto<GovtSchemeData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController delete method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        logger.info("Exiting from GovtSchemeDataController delete method");
        return new ResponseDto<GovtSchemeData>(false);
    }

    /**
     * This method to delete GovtSchemeData
     * @author Mayank Sharma
     * @param govtschemedata
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<GovtSchemeData> delete(@RequestBody GovtSchemeData govtschemedata) {
        logger.info("Inside GovtSchemeDataController delete method");
        try {
            if (businessDelegate.delete(govtschemedata)) {
                logger.info("Exiting from GovtSchemeDataController delete method");
                return new ResponseDto<GovtSchemeData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController delete method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        logger.info("Exiting from GovtSchemeDataController delete method");
        return new ResponseDto<GovtSchemeData>(false);
    }

    /**
     * This method to find GovtSchemeData by id
     * @author Mayank Sharma
     * @param id
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GovtSchemeData> find(Long id) {
        logger.info("Inside GovtSchemeDataController find method");
        GovtSchemeData govtschemedata = null;
        try {
            govtschemedata = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController find method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        if (govtschemedata != null) {
            logger.info("Exiting from GovtSchemeDataController find method");
            return new ResponseDto<GovtSchemeData>(true, govtschemedata);
        }

        logger.info("Exiting from GovtSchemeDataController find method");
        return new ResponseDto<GovtSchemeData>(false);
    }

    /**
     * This method to hard delete GovtSchemeData by id
     * @author Mayank Sharma
     * @param id
     * @return GovtSchemeData
     */
    @RequestMapping(value = "/deleteFromDB", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<GovtSchemeData> deleteFromDB(Long id) {
        logger.info("Inside GovtSchemeDataController deleteFromDB method");
        try {
            if (businessDelegate.deleteFromDB(id)) {
                logger.info("Exiting from GovtSchemeDataController deleteFromDB method");
                return new ResponseDto<GovtSchemeData>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleteFromDB GovtSchemeData");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GovtSchemeDataController deleteFromDB method");
            return new ResponseDto<GovtSchemeData>(false);
        }
        logger.info("Exiting from GovtSchemeDataController deleteFromDB method");
        return new ResponseDto<GovtSchemeData>(false);
    }
}
