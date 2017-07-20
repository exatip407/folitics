package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IGpiPointBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.service.GPIChartService;
import com.ohmuk.folitics.service.IGPIPointService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/gpiPoint")
public class GPIPointController {

    @Autowired
    private volatile IGpiPointBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(GPIChartService.class);

    @RequestMapping
    public String getGPIPointPage() {
        return "gpiPoint-page";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GPIPoint> getAdd() {
        List<GPIPoint> gpiPoints = new ArrayList<>();
        gpiPoints.add(getTestGPIPoint());
        return new ResponseDto<GPIPoint>(true, gpiPoints);
    }

    /**
     * This method is to add GPIPoint
     * @param gpiPoint
     * @return GPIPoint
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<GPIPoint> add(@RequestBody GPIPoint gpiPoint) {
        logger.info("Inside GPIPointController add method");
        try {
            gpiPoint = businessDelegate.create(gpiPoint);
        } catch (Exception exception) {
            logger.error("Exception in adding GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController add method");
            return new ResponseDto<GPIPoint>(false);
        }
        if (gpiPoint != null) {
            logger.info("Exiting from GPIPointController add method");
            return new ResponseDto<GPIPoint>(true, gpiPoint);
        }
        logger.info("Exiting from GPIPointController add method");
        return new ResponseDto<GPIPoint>(false);
    }

    /**
     * This method is to edit GPIPoint
     * @param gpiPoint
     * @return ResponseDto<GPIPoint>
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<GPIPoint> edit(@RequestBody GPIPoint gpiPoint) {
        logger.info("Inside GPIPointController add method");
        try {
            gpiPoint = businessDelegate.update(gpiPoint);
        } catch (Exception exception) {
            logger.error("Exception in editing GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController edit method");
            return new ResponseDto<GPIPoint>(false);
        }
        if (gpiPoint != null) {
            logger.info("Exiting from GPIPointController edit method");
            return new ResponseDto<GPIPoint>(true, gpiPoint);
        }
        logger.info("Exiting from GPIPointController edit method");
        return new ResponseDto<GPIPoint>(false);
    }

    /**
     * This method is to delete GPIPoint by id
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<GPIPoint>
     */
    @RequestMapping(value = "/deletebyid", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GPIPoint> delete(Long id) {
        logger.info("Inside GPIPointController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.info("Exiting from GPIPointController delete method");
                return new ResponseDto<GPIPoint>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deletebyid GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController delete method");
            return new ResponseDto<GPIPoint>(false);
        }
        logger.info("Exiting from GPIPointController delete method");
        return new ResponseDto<GPIPoint>(false);
    }

    /**
     * This method is to hard delete GPIPoint
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<GPIPoint>
     */
    @RequestMapping(value = "/hardDeleteById", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GPIPoint> hardDelete(Long id) {
        logger.info("Inside GPIPointController add method");
        try {
            if (businessDelegate.hardDelete(id)) {
                logger.info("Exiting from GPIPointController hardDelete method");
                return new ResponseDto<GPIPoint>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in hardDeleteById GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController hardDelete method");
            return new ResponseDto<GPIPoint>(false);
        }
        logger.info("Exiting from GPIPointController hardDelete method");
        return new ResponseDto<GPIPoint>(false);
    }

    /**
     * This method is to delete GPIPoint
     * @author Mayank Sharma
     * @param gpiPoint
     * @return ResponseDto<GPIPoint>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<GPIPoint> delete(@RequestBody GPIPoint gpiPoint) {
        logger.info("Inside GPIPointController add method");
        try {
            if (businessDelegate.delete(gpiPoint)) {
                logger.info("Exiting from GPIPointController delete method");
                return new ResponseDto<GPIPoint>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in delete GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController delete method");
            return new ResponseDto<GPIPoint>(false);
        }
        logger.info("Exiting from GPIPointController delete method");
        return new ResponseDto<GPIPoint>(false);
    }

    /**
     * This method is to get all GPIPoint
     * @author Mayank Sharma
     * @return ResponseDto<GPIPoint>
     */
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GPIPoint> getall() {
        logger.info("Inside GPIPointController getall method");
        List<GPIPoint> gpiPoints = null;
        try {
            gpiPoints = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in getall GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController getall method");
            return new ResponseDto<GPIPoint>(false);
        }
        if (gpiPoints != null) {
            logger.info("Exiting from GPIPointController getall method");
            return new ResponseDto<GPIPoint>(true, gpiPoints);
        }
        logger.info("Exiting from GPIPointController getall method");
        return new ResponseDto<GPIPoint>(false);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<List<GPIPoint>> getALl() {
        logger.info("Inside GPIPointController getALl method");
        List<GPIPoint> gpis = null;
        try {
            gpis = businessDelegate.getALLGPIpoints();
        } catch (Exception exception) {
            logger.error("Exception in getall GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController getALl method");
            return new ResponseDto<List<GPIPoint>>(false);
        }
        if (null != gpis) {
            logger.info("Exiting from GPIPointController getALl method");
            return new ResponseDto<List<GPIPoint>>(true, gpis);
        }
        logger.info("Exiting from GPIPointController getALl method");
        return new ResponseDto<List<GPIPoint>>(false);
    }

    /**
     * This method is to get GPIPoint by id
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<GPIPoint>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<GPIPoint> find(Long id) {
        logger.info("Inside GPIPointController add method");
        GPIPoint gpiPoint = null;
        try {
            gpiPoint = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in find GPIPoint");
            logger.error("Exception: " + exception);
            logger.info("Exiting from GPIPointController find method");
            return new ResponseDto<GPIPoint>(false);
        }
        if (gpiPoint != null) {
            logger.info("Exiting from GPIPointController find method");
            return new ResponseDto<GPIPoint>(true, gpiPoint);
        }
        logger.info("Exiting from GPIPointController find method");
        return new ResponseDto<GPIPoint>(false);
    }

    @RequestMapping(value = "/getTestGPIPoint", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody GPIPoint getTestGPIPoint() {
        return getDummyGPIPoint();

    }

    private GPIPoint getDummyGPIPoint() {
        GPIPoint gpiPoint = new GPIPoint();
        // gpiPoint.setId((new Random()).nextLong());
        gpiPoint.setStartTime(DateUtils.getSqlTimeStamp());
        gpiPoint.setEndTime(DateUtils.getSqlTimeStamp());
        gpiPoint.setOpinionResponseAggregationPoints(1000l);
        gpiPoint.setIndicatorChangePoints(1500l);
        gpiPoint.setEventReportPoints(5000l);
        gpiPoint.setTotalPoints(1000l + 1500l + 5000l);
        return gpiPoint;
    }
}
