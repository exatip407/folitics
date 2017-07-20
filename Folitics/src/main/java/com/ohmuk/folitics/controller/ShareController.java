package com.ohmuk.folitics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IShareBusinessDeligate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;

@Controller
@RequestMapping("/componentShare")
public class ShareController {

    private static Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private IShareBusinessDeligate shareDelegate;

    /**
     * Spring web service(POST) for adding new entry for share
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.beans.AirShareDataBean share
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Object> add(@RequestBody AirShareDataBean airShareBean) {

        logger.info("Inside add Share");

        try {
            Object shared = shareDelegate.create(airShareBean);

            if (shared != null) {
                return new ResponseDto<Object>(true, shared, "Successfully added Share");
            }
        } catch (MessageException e) {
            logger.error("CustomException while adding the share " + e);
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {
            logger.error("Exception while adding share " + e);
            return new ResponseDto<Object>(false, null, e.getMessage());
        }
        return new ResponseDto<Object>(false, null, "Something went wrong, can't add share");
    }

    /**
     * Spring web service(GET) for getting share counts on the component
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/getShareCount", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> getShareCount(String componentType, Long componentId) {

        logger.info("Inside getShareCount method");

        try {
            Object shareCount = shareDelegate.getShareCount(componentType, componentId);

            if (shareCount != null) {
                return new ResponseDto<Object>(true, shareCount, "Successfully added Share");
            }
        } catch (MessageException e) {
            logger.error("CustomException while getting the share count" + e);
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {
            logger.error("Exception while getting share count" + e);
            return new ResponseDto<Object>(false, null, e.getMessage());
        }
        return new ResponseDto<Object>(false, null, "Something went wrong, can't add share");
    }

    /**
     * Spring web service(GET) for finding on the component with id and component type
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long id
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> find(String componentType, Long id) {

        logger.info("Inside ShareController find method");
        logger.debug("Input componentType = " + componentType + "and componentId = " + id);

        Object likeObject = new Object();
        try {

            logger.info("Trying to get share object. Entering ShareBusinessDelegate find method");
            likeObject = shareDelegate.read(id, componentType);

        } catch (MessageException e) {

            logger.error("Error while getting share object for componentType = " + componentType + ", componentId = "
                    + id);
            logger.error("Error is ", e);
            logger.info("Exiting find method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while getting share object for componentType = " + componentType + ", componentId = "
                    + id);
            logger.error("Error is ", e);
            logger.info("Exiting find method due to error");
            return new ResponseDto<Object>(false, null, e.getMessage());
        }

        if (likeObject != null) {
            return new ResponseDto<Object>(true, likeObject);
        }

        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(GET) for deleting the component with id and component type
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long id
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> delete(String componentType, Long id) {

        logger.info("Inside ShareController delete method");
        logger.debug("Input componentType = " + componentType + "and componentId = " + id);

        Object likeObject = new Object();

        try {

            logger.info("Trying to delete share object. Entering ShareBusinessDelegate delete method");
            likeObject = shareDelegate.delete(id, componentType);

        } catch (MessageException e) {

            logger.error("Error while deleting share object for componentType = " + componentType + ", componentId = "
                    + id);
            logger.error("Error is ", e);
            logger.info("Exiting delete method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while deleting share object for componentType = " + componentType + ", componentId = "
                    + id);
            logger.error("Error is ", e);
            logger.info("Exiting delete method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        }

        if (likeObject == null) {
            return new ResponseDto<Object>(true, likeObject);
        }

        return new ResponseDto<Object>(false);
    }

}
