package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IMilestoneBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.Milestone;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/milestone")
public class MilestoneController {

    @Autowired
    public IMilestoneBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(MilestoneController.class);

    /**
     * Method is to add {@link Milestone} on {@link com.ohmuk.folitics.hibernate.entity.GPIPoint} by gpiId
     * @param milestone
     * @param gpiId
     * @return {@link ResponseDto< Milestone >}
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Milestone> addMilestoneOnGPI(@RequestBody Milestone milestone,
            @RequestParam Long gpiId) {
        logger.info("Inside MilestoneController addMilestoneOnGPI method");
        if (milestone != null) {
            try {
                milestone = businessDelegate.add(gpiId, milestone);
            } catch (Exception exception) {
                logger.error("Exception in adding milestone");
                logger.error("Exception: " + exception);
                logger.info("Exiting from MilestoneController addMilestoneOnGPI method");
                return new ResponseDto<Milestone>(false);
            }
            if (null != milestone) {
                logger.debug("Milestone is add on GPIPoint with id: " + gpiId);
                logger.info("Exiting from MilestoneController addMilestoneOnGPI method");
                return new ResponseDto<Milestone>(true, milestone);
            }
        }
        logger.debug("Milstone is not add");
        logger.info("Exiting from MilestoneController addMilestoneOnGPI method");
        return new ResponseDto<Milestone>(false);
    }

    /**
     * Method is to update {@link Milestone} on {@link com.ohmuk.folitics.hibernate.entity.GPIPoint}
     * @param milestone
     * @param gpiId
     * @return {@link ResponseDto< Milestone >}
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Milestone> updateMilestoneOnGPIPoint(@RequestBody Milestone milestone) {
        logger.info("Inside MilestoneController updateMilestoneOnGPIPoint method");
        if (milestone != null) {
            try {
                milestone = businessDelegate.update(milestone);
            } catch (Exception exception) {
                logger.error("Exception in adding milestone");
                logger.error("Exception: " + exception);
                logger.info("Exiting from MilestoneController updateMilestoneOnGPIPoint method");
                return new ResponseDto<Milestone>(false);
            }
            if (null != milestone) {
                logger.debug("Milestone with id: " + milestone.getId() + " is update");
                logger.info("Exiting from MilestoneController updateMilestoneOnGPIPoint method");
                return new ResponseDto<Milestone>(true, milestone);
            }
        }
        logger.debug("Milestone is not update");
        logger.info("Exiting from MilestoneController updateMilestoneOnGPIPoint method");
        return new ResponseDto<Milestone>(false);
    }

    /**
     * Method is to delete {@link Milestone} on {@link com.ohmuk.folitics.hibernate.entity.GPIPoint} by gpiId
     * @param gpiId
     * @return {@link ResponseDto< Milestone >}
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Milestone> deleteMilestoneOnGPI(Long gpiId) {
        logger.info("Inside MilestoneController deleteMilestoneOnGPI method");
        if (null != gpiId) {
            try {
                if (businessDelegate.delete(gpiId)) {
                    logger.debug("Milestone on GPIPoint of id: " + gpiId + " is delete");
                    logger.info("Exiting from MilestoneController deleteMilestoneOnGPI method");
                    return new ResponseDto<Milestone>(true);
                }
            } catch (Exception exception) {
                logger.error("Exception in adding milestone");
                logger.error("Exception: " + exception);
                logger.info("Exiting from MilestoneController deleteMilestoneOnGPI method");
                return new ResponseDto<Milestone>(false);
            }
        }
        logger.debug("Milestone is not delete");
        logger.info("Exiting from MilestoneController addMilestoneOnGPI method");
        return new ResponseDto<Milestone>(false);
    }

    /**
     * Give all milestone on gpi points
     * @return {@link ResponseDto < Milestone >}
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Milestone> getAllMilestone() {
        logger.info("Inside MilestoneController getAllMilestone method");
        List<Milestone> allMilestone = null;
        try {
            allMilestone = businessDelegate.findAll();
        } catch (Exception exception) {
            logger.error("Exception in adding milestone");
            logger.error("Exception: " + exception);
            logger.info("Exiting from MilestoneController getAllMilestone method");
            return new ResponseDto<Milestone>(false);
        }
        if (null != allMilestone) {
            logger.debug("Milestone's is found");
            logger.info("Exiting from MilestoneController getAllMilestone method");
            return new ResponseDto<Milestone>(true, allMilestone);
        } else {
            logger.debug("No milestone is found");
            logger.info("Exiting from MilestoneController getAllMilestone method");
            return new ResponseDto<Milestone>(false);
        }
    }
    
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Milestone> getMilestone(Long gpiId) {
        logger.info("Inside MilestoneController getAllMilestone method");
        List<Milestone> allMilestone = null;
        try {
            allMilestone = businessDelegate.findAll();
        } catch (Exception exception) {
            logger.error("Exception in adding milestone");
            logger.error("Exception: " + exception);
            logger.info("Exiting from MilestoneController getAllMilestone method");
            return new ResponseDto<Milestone>(false);
        }
        if (null != allMilestone) {
            logger.debug("Milestone's is found");
            logger.info("Exiting from MilestoneController getAllMilestone method");
            return new ResponseDto<Milestone>(true, allMilestone);
        } else {
            logger.debug("No milestone is found");
            logger.info("Exiting from MilestoneController getAllMilestone method");
            return new ResponseDto<Milestone>(false);
        }
    }

}
