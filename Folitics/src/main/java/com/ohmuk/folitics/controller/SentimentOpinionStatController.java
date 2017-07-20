package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.ISentimentOpinionStatBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;

/**
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/sentimentOpinionStat")
public class SentimentOpinionStatController {

    @Autowired
    private volatile ISentimentOpinionStatBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(SentimentOpinionStatController.class);

    @RequestMapping
    public String getSentimentOpinionStatPage() {
        return "sentimentOpinionStat-page";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<SentimentOpinionStat> getAdd() {
        List<SentimentOpinionStat> sentimentOpinionStats = new ArrayList<>();
        sentimentOpinionStats.add(getTestSentimentOpinionStat());
        return new ResponseDto<SentimentOpinionStat>(true, sentimentOpinionStats);
    }

    /**
     * Web service is to add {@link SentimentOpinionStat}
     * 
     * @param sentimentOpinionStat
     * @return ResponseDto<SentimentOpinionStat>
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<SentimentOpinionStat> add(
            @Validated @RequestBody SentimentOpinionStat sentimentOpinionStat) {
        logger.info("Inside SentimentOpinionStatController add method");
        try {
            sentimentOpinionStat = businessDelegate.create(sentimentOpinionStat);
        } catch (Exception exception) {
            logger.error("Exception in adding SentimentOpinionStat");
            logger.error("Exception: " + exception);
            logger.info("Exiting from SentimentOpinionStatController add method");
            return new ResponseDto<SentimentOpinionStat>(false);
        }
        if (sentimentOpinionStat != null) {
            logger.debug("SentimentOpinionStat with id: " + sentimentOpinionStat.getId() + " is added");
            logger.info("Exiting from SentimentOpinionStatController add method");
            return new ResponseDto<SentimentOpinionStat>(true, sentimentOpinionStat);
        }
        logger.debug("SentimentOpinionStat is not add");
        logger.info("Exiting from SentimentOpinionStatController add method");
        return new ResponseDto<SentimentOpinionStat>(false);
    }

    /**
     * Web service is to update {@link SentimentOpinionStat}
     * 
     * @param sentimentOpinionStat
     * @return ResponseDto<SentimentOpinionStat>
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<SentimentOpinionStat> edit(
            @Validated @RequestBody SentimentOpinionStat sentimentOpinionStat) {
        logger.info("Inside SentimentOpinionStatController edit method");
        try {
            sentimentOpinionStat = businessDelegate.update(sentimentOpinionStat);
        } catch (Exception exception) {
            logger.error("Exception in update SentimentOpinionStat");
            logger.error("Exception: " + exception);
            logger.info("Exiting from SentimentOpinionStatController edit method");
            return new ResponseDto<SentimentOpinionStat>(false);
        }
        if (sentimentOpinionStat != null) {
            logger.debug("SentimentOpinionStat with id: " + sentimentOpinionStat.getId() + " is update");
            logger.info("Exiting from SentimentOpinionStatController edit method");
            return new ResponseDto<SentimentOpinionStat>(true, sentimentOpinionStat);
        }
        logger.debug("SentimentOpinionStat is not update");
        logger.info("Exiting from SentimentOpinionStatController edit method");
        return new ResponseDto<SentimentOpinionStat>(false);
    }

    /**
     * Web service is to soft delete {@link SentimentOpinionStat} by id
     * 
     * @param id
     * @return ResponseDto<SentimentOpinionStat>
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<SentimentOpinionStat> delete(Long id) {
        logger.info("Inside SentimentOpinionStatController deleteById method");
        try {
            if (businessDelegate.deleteFromDB(id)) {
                logger.debug("SentimentOpinionStat with id: " + id + " is delete");
                logger.info("Exiting from SentimentOpinionStatController deleteById method");
                return new ResponseDto<SentimentOpinionStat>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in delete SentimentOpinionStat by id");
            logger.error("Exception: " + exception);
            logger.info("Exiting from SentimentOpinionStatController deleteById method");
            return new ResponseDto<SentimentOpinionStat>(false);
        }
        logger.debug("SentimentOpinionStat with id: " + id + " is not delete");
        logger.info("Exiting from SentimentOpinionStatController deleteById method");
        return new ResponseDto<SentimentOpinionStat>(false);
    }

    /**
     * Web service is to soft delete {@link SentimentOpinionStat}
     * 
     * @param sentimentOpinionStat
     * @return ResponseDto<SentimentOpinionStat>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<SentimentOpinionStat> delete(
            @Validated @RequestBody SentimentOpinionStat sentimentOpinionStat) {
        logger.info("Inside SentimentOpinionStatController delete method");
        try {
            if (businessDelegate.deleteFromDB(sentimentOpinionStat)) {
                logger.debug("SentimentOpinionStat with id: " + sentimentOpinionStat.getId() + " is deleted");
                logger.info("Exiting from SentimentOpinionStatController delete method");
                return new ResponseDto<SentimentOpinionStat>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting SentimentOpinionStat");
            logger.error("Exception: " + exception);
            logger.info("Exiting from SentimentOpinionStatController delete method");
            return new ResponseDto<SentimentOpinionStat>(false);
        }
        logger.debug("SentimentOpinionStat with id: " + sentimentOpinionStat.getId() + " is not deleted");
        logger.info("Exiting from SentimentOpinionStatController delete method");
        return new ResponseDto<SentimentOpinionStat>(false);
    }

    /**
     * Web service is to get all {@link SentimentOpinionStat}
     * 
     * @return ResponseDto<SentimentOpinionStat>
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<SentimentOpinionStat> getAll() {
        logger.info("Inside SentimentOpinionStatController getAll method");
        List<SentimentOpinionStat> sentimentOpinionStats = null;
        try {
            sentimentOpinionStats = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in get all SentimentOpinionStat");
            logger.error("Exception: " + exception);
            logger.info("Exiting from SentimentOpinionStatController getAll method");
            return new ResponseDto<SentimentOpinionStat>(false);
        }
        if (sentimentOpinionStats != null) {
            logger.debug(sentimentOpinionStats.size() + " SentimentOpinionStat is found");
            logger.info("Exiting from SentimentOpinionStatController getAll method");
            return new ResponseDto<SentimentOpinionStat>(true, sentimentOpinionStats);
        }
        logger.debug("No SentimentOpinionStat is found");
        logger.info("Exiting from SentimentOpinionStatController getAll method");
        return new ResponseDto<SentimentOpinionStat>(false);
    }

    /**
     * Web service is to get {@link SentimentOpinionStat} by id
     * 
     * @param id
     * @return ResponseDto<SentimentOpinionStat>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<SentimentOpinionStat> find(Long id) {
        logger.info("Inside SentimentOpinionStatController find method");
        SentimentOpinionStat sentimentOpinionStat = null;
        try {
            sentimentOpinionStat = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding SentimentOpinionStat");
            logger.error("Exception: " + exception);
            logger.info("Exiting from SentimentOpinionStatController find method");
            return new ResponseDto<SentimentOpinionStat>(false);
        }
        if (sentimentOpinionStat != null) {
            logger.debug("SentimentOpinionStat with id: " + id + " is found");
            logger.info("Exiting from SentimentOpinionStatController find method");
            return new ResponseDto<SentimentOpinionStat>(true, sentimentOpinionStat);
        }
        logger.debug("SentimentOpinionStat with id: " + id + " is not found");
        logger.info("Exiting from SentimentOpinionStatController find method");
        return new ResponseDto<SentimentOpinionStat>(false);
    }

    @RequestMapping(value = "/getTestSentimentOpinionStat", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody SentimentOpinionStat getTestSentimentOpinionStat() {
        return getDummySentimentOpinionStat();

    }

    private SentimentOpinionStat getDummySentimentOpinionStat() {
        SentimentOpinionStat sentimentOpinionStat = new SentimentOpinionStat();
        return sentimentOpinionStat;
    }
}
