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

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorThresholdBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/indicatorthreshold")
public class IndicatorThresholdController {
    @Autowired
    private volatile IIndicatorThresholdBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(IndicatorThresholdController.class);

    /**
     * This web service is used to add IndicatorThreshold
     * @author Mayank Sharma
     * @param indicatorthreshold
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<IndicatorThreshold> add(@RequestBody IndicatorThreshold indicatorthreshold) {
        logger.info("Inside IndicatorThresholdController add method");
        try {
            indicatorthreshold = businessDelegate.create(indicatorthreshold);
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        if (indicatorthreshold != null) {
            logger.debug("IndicatorThreshold is added");
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(true, indicatorthreshold);
        }
        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

    /**
     * This web service is used to get all IndicatorThreshold
     * @author Mayank Sharma
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorThreshold> getAll() {
        logger.info("Inside IndicatorThresholdController add method");
        List<IndicatorThreshold> components = null;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        if (components != null) {
            logger.debug("List of IndicatorThreshold is found");
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(true, components);
        }
        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

    /**
     * This web service is used to update IndicatorThreshold
     * @author Mayank Sharma
     * @param indicatorthreshold
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorThreshold> edit(@RequestBody IndicatorThreshold indicatorthreshold) {
        logger.info("Inside IndicatorThresholdController add method");
        try {
            indicatorthreshold = businessDelegate.update(indicatorthreshold);
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        if (indicatorthreshold != null) {
            logger.debug("IndicatorThreshold is updated");
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(true, indicatorthreshold);
        }
        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

    /**
     * This web service is used to delete IndicatorThreshold by id
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorThreshold> delete(Long id) {
        logger.info("Inside IndicatorThresholdController add method");
        try {
            if (businessDelegate.delete(id)) {
                logger.debug("IndicatorThreshold of id: " + id + " is deleted ");
                logger.info("Exiting from IndicatorThresholdController add method");
                return new ResponseDto<IndicatorThreshold>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

    /**
     * This web service is used to delete IndicatorThreshold
     * @author Mayank Sharma
     * @param indicatorthreshold
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorThreshold> delete(@RequestBody IndicatorThreshold indicatorthreshold) {
        logger.info("Inside IndicatorThresholdController add method");
        try {
            if (businessDelegate.delete(indicatorthreshold)) {
                logger.debug("IndicatorThreshold with id: " + indicatorthreshold.getId() + " is deleted");
                logger.info("Exiting from IndicatorThresholdController add method");
                return new ResponseDto<IndicatorThreshold>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

    /**
     * This web service is used to hard delete IndicatorThreshold
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/deleteFromDB", method = RequestMethod.GET, consumes = "application/json")
    public @ResponseBody ResponseDto<IndicatorThreshold> deleteFromDB(Long id) {
        logger.info("Inside IndicatorThresholdController add method");
        try {
            if (businessDelegate.deleteFromDB(id)) {
                logger.debug("IndicatorThreshold is deleted with id: " + id);
                logger.info("Exiting from IndicatorThresholdController add method");
                return new ResponseDto<IndicatorThreshold>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

    /**
     * This Web service is used to find IndicatorThreshold by id
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<IndicatorThreshold>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<IndicatorThreshold> find(Long id) {
        logger.info("Inside IndicatorThresholdController add method");
        IndicatorThreshold indicatorthreshold = null;
        try {
            indicatorthreshold = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in adding IndicatorThreshold");
            logger.error("Exception: " + exception);
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(false);
        }
        if (indicatorthreshold != null) {
            logger.debug("IndicatorThreshold of id: " + indicatorthreshold.getId() + " is found");
            logger.info("Exiting from IndicatorThresholdController add method");
            return new ResponseDto<IndicatorThreshold>(true, indicatorthreshold);
        }

        logger.info("Exiting from IndicatorThresholdController add method");
        return new ResponseDto<IndicatorThreshold>(false);
    }

}
