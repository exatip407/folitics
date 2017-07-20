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

import com.ohmuk.folitics.businessDelegate.interfaces.IEventReportScoreBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/event")
public class EventReportScoreController {
    @Autowired
    private volatile IEventReportScoreBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(EventReportScoreController.class);

    /**
     * This web service is to add EventReportScore by calling
     * @author Mayank Sharma
     * @param event
     * @return EventReportScore
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<EventReportScore> add(@RequestBody EventReportScore event) {
        logger.info("Inside EventReportScoreController add method");
        try {
            event = businessDelegate.create(event);
        } catch (Exception exception) {
            logger.error("Exception in adding EventReportScore");
            logger.error("Exception: " + exception);
            return new ResponseDto<EventReportScore>(false);
        }
        if (event != null) {
            logger.debug("EventReportScore is added");
            return new ResponseDto<EventReportScore>(true, event);
        }
        logger.debug("EventReportScore is not added");
        return new ResponseDto<EventReportScore>(false);

    }

    /**
     * This web service to get all component
     * @author Mayank Sharma
     * @return EventReportScore
     */
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<EventReportScore> getAll() {
        logger.info("Inside EventReportScoreController method getAll");
        List<EventReportScore> components = null;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in adding EventReportScore");
            logger.error("Exception: " + exception);
            return new ResponseDto<EventReportScore>(false);
        }
        if (components != null) {
            logger.debug("Components is found");
            logger.info("Exicting from EventReportScoreController");
            return new ResponseDto<EventReportScore>(true, components);
        }
        logger.debug("No component is found");
        logger.info("Exicting from EventReportScoreController");
        return new ResponseDto<EventReportScore>(false);
    }

    /**
     * This method is to hard delete EventReportScore
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<EventReportScore>
     */
    @RequestMapping(value = "/deleteFromDB", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<EventReportScore> deleteFromDB(Long id) {
        logger.info("Inside EventReportScoreController deleteFromDB method");
        try {
            if (businessDelegate.deleteFromDB(id)) {
                logger.debug("EventReportScore is deleted from db");
                return new ResponseDto<EventReportScore>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting EventReportScore");
            logger.error("Exception: " + exception);
            return new ResponseDto<EventReportScore>(false);
        }
        logger.debug("EventReportScore is not deleted from db");
        logger.info("Exicting from EventReportScoreController delete method");
        return new ResponseDto<EventReportScore>(false);
    }

    /**
     * This method is to delete EventReportScore
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<EventReportScore>
     */
    @RequestMapping(value = "/deletebyid", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<EventReportScore> delete(Long id) {
        logger.info("Inside EventReportScoreController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.debug("EventReportScore is deleted");
                return new ResponseDto<EventReportScore>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting EventReportScore");
            logger.error("Exception: " + exception);
            logger.debug("EventReportScore is deleted");
            return new ResponseDto<EventReportScore>(false);
        }
        logger.debug("EventReportScore is deleted");
        logger.info("Exicting from EventReportScoreController delete method");
        return new ResponseDto<EventReportScore>(false);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<EventReportScore> delete(@RequestBody EventReportScore eventReportScore) {
        logger.info("Inside EventReportScoreController delete method");
        try {
            if (businessDelegate.delete(eventReportScore)) {
                logger.debug("EventReportScore is deleted");
                return new ResponseDto<EventReportScore>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting EventReportScore");
            logger.error("Exception: " + exception);
            logger.debug("EventReportScore is not deleted");
            return new ResponseDto<EventReportScore>(false);
        }
        logger.debug("EventReportScore is not deleted");
        logger.info("Exicting from EventReportScoreController delete method");
        return new ResponseDto<EventReportScore>(false);
    }

    /**
     * This web service is to find EventReportScore by id
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<EventReportScore>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<EventReportScore> find(Long id) {
        logger.info("Inside EventReportScoreController find method");
        EventReportScore eventReportScore;
        try {
            eventReportScore = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding EventReportScore");
            logger.error("Exception: " + exception);
            logger.info("Exicting from EventReportScoreController find method");
            return new ResponseDto<EventReportScore>(false);
        }
        if (eventReportScore != null) {
            logger.debug("EventReportScore is found");
            logger.info("Exicting from EventReportScoreController find method");
            return new ResponseDto<EventReportScore>(true, eventReportScore);
        }
        logger.debug("EventReportScore is not found");
        logger.info("Exicting from EventReportScoreController find method");
        return new ResponseDto<EventReportScore>(false);
    }

    /**
     * This web service is to edit EventReportScore
     * @author Mayank Sharma
     * @param eventReportScore
     * @return ResponseDto<EventReportScore>
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<EventReportScore> edit(@RequestBody EventReportScore eventReportScore) {
        logger.info("Inside EventReportScoreController edit method");
        try {
            eventReportScore = businessDelegate.update(eventReportScore);
        } catch (Exception exception) {
            logger.error("Exception in editing EventReportScore");
            logger.error("Exception: " + exception);
            logger.info("Exicting from EventReportScoreController edit method");
            return new ResponseDto<EventReportScore>(false);
        }
        if (eventReportScore != null) {
            logger.debug("EventReportScore is updated");
            logger.info("Exicting from EventReportScoreController edit method");
            return new ResponseDto<EventReportScore>(true, eventReportScore);
        }
        logger.debug("EventReportScore is not updated");
        logger.info("Exicting from EventReportScoreController edit method");
        return new ResponseDto<EventReportScore>(false);
    }

}
