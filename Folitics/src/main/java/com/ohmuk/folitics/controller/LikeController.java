package com.ohmuk.folitics.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.ILikeBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;

/**
 * Controller for Like
 * @author Abhishek
 *
 */

@Controller
@RequestMapping("/like")
public class LikeController {

    private static Logger logger = Logger.getLogger(LikeController.class);

    @Autowired
    private ILikeBusinessDelegate businessDelegate;

    /**
     * Spring web service(POST) to get the counts of like and dislike for a component with id and its type
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/getLikeAndDislikeCount", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> getLikes(String componentType, Long componentId) {

        logger.info("Inside LikeController getLikes method");
        logger.debug("Input componentType = " + componentType + " and componentId = " + componentId);

        Object likeCount = new Object();
        try {

            logger.info("Trying to get like and dislike count. Entering LikeBusinessDelegate getLikes method");
            likeCount = businessDelegate.getLikes(componentType, componentId);

        } catch (MessageException e) {

            logger.error("Error while getting like and dislike count for componentType = " + componentType
                    + " and componentId = " + componentId);
            logger.error("Error is ", e);
            logger.info("Exiting getLikes method due to error");
            // e.printStackTrace();

            return new ResponseDto<Object>(false, null, e.getMessage());
        }

        if (likeCount != null) {

            logger.info("Returning like and dislike count. Exiting getLikes method");
            return new ResponseDto<Object>(true, likeCount);
        }

        logger.info("Count found null. Exiting getLikes method");
        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(POST) for like on the component with id and component type for the userid
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST,consumes = "application/json")
    public @ResponseBody ResponseDto<Object> like(@RequestBody LikeDataBean bean) {

        logger.info("Inside LikeController like method");
        logger.debug("Input componentType = " + bean.getComponentType() + ", componentId = " + bean.getComponentId() + " and userId = "
                + bean.getUserId());

        Object likeObject = new Object();
        try {

            logger.info("Trying to save like. Entering LikeBusinessDelegate like method");
            likeObject = businessDelegate.like(bean.getComponentId(), bean.getComponentType(), bean.getUserId());

        } catch (MessageException e) {

            logger.error("Error while saving like for componentType = " + bean.getComponentType() + ", componentId = "
                    + bean.getComponentId() + " and userId = " + bean.getUserId());
            logger.error("Error is ", e);
            logger.info("Exiting like method due to error");
            // e.printStackTrace();

            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while saving like for componentType = " + bean.getComponentType() + ", componentId = "
                    + bean.getComponentId() + " and userId = " + bean.getUserId());
            logger.error("Error is ", e);
            logger.info("Exiting like method due to error");
            // e.printStackTrace();

            return new ResponseDto<Object>(false, null, e.getMessage());
        }

        if (likeObject != null) {

            logger.info("Saved like. Exiting like method");
            return new ResponseDto<Object>(true, likeObject);
        }

        logger.info("Like object after saving is null. Exiting like method");
        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(POST) for unlike on the component with id and component type for the userid
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/unlike", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> unlike(String componentType, Long componentId, Long userId) {

        logger.info("Inside LikeController unlike method");
        logger.debug("Input componentType = " + componentType + ", componentId = " + componentId + " and userId = "
                + userId);

        Object likeObject = new Object();
        try {

            logger.info("Trying to save unlike. Entering LikeBusinessDelegate unlike method");
            likeObject = businessDelegate.unlike(componentId, componentType, userId);

        } catch (MessageException e) {

            logger.error("Error while saving unlike for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting unlike method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while saving unlike for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting unlike method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        }

        if (likeObject != null) {

            logger.info("Saved unlike. Exiting unlike method");
            return new ResponseDto<Object>(true, likeObject);
        }

        logger.info("Like object after saving is null. Exiting unlike method");
        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(POST) for dislike on the component with id and component type for the userid
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Object> dislike(String componentType, Long componentId, Long userId) {

        logger.info("Inside LikeController dislike method");
        logger.debug("Input componentType = " + componentType + ", componentId = " + componentId + " and userId = "
                + userId);

        Object likeObject = new Object();
        try {

            logger.info("Trying to save dislike. Entering LikeBusinessDelegate dislike method");
            likeObject = businessDelegate.dislike(componentId, componentType, userId);

        } catch (MessageException e) {

            logger.error("Error while saving dislike for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting dislike method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while saving dislike for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting dislike method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        }

        if (likeObject != null) {

            logger.info("Saved dislike. Exiting dislike method");
            return new ResponseDto<Object>(true, likeObject);
        }

        logger.info("Like object after saving is null. Exiting dislike method");
        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(POST) for undislike on the component with id and component type for the userid
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/undislike", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Object> undislike(String componentType, Long componentId, Long userId) {

        logger.info("Inside LikeController undislike method");
        logger.debug("Input componentType = " + componentType + ", componentId = " + componentId + " and userId = "
                + userId);

        Object likeObject = new Object();
        try {

            logger.info("Trying to save undislike. Entering LikeBusinessDelegate undislike method");
            likeObject = businessDelegate.undislike(componentId, componentType, userId);

        } catch (MessageException e) {

            logger.error("Error while saving undislike for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting undislike method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while saving undislike for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting undislike method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        }

        if (likeObject != null) {

            logger.info("Saved undislike. Exiting undislike method");
            return new ResponseDto<Object>(true, likeObject);
        }

        logger.info("Like object after saving is null. Exiting undislike method");
        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(POST) for finding on the component with id and component type for the userid
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> find(String componentType, Long componentId, Long userId) {

        logger.info("Inside LikeController find method");
        logger.debug("Input componentType = " + componentType + ", componentId = " + componentId + " and userId = "
                + userId);

        Object likeObject = new Object();
        try {

            logger.info("Trying to get like object. Entering LikeBusinessDelegate read method");
            likeObject = businessDelegate.read(componentId, userId, componentType);

        } catch (MessageException e) {

            logger.error("Error while getting like object for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting find method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while getting like object for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting find method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        }

        if (likeObject != null) {

            logger.info("Got like object. Exiting find method");
            return new ResponseDto<Object>(true, likeObject);
        }

        logger.info("Like object found null. Exiting find method");
        return new ResponseDto<Object>(false);
    }

    /**
     * Spring web service(POST) for deleting the component with id and component type for the userid
     * 
     * @author Abhishek
     * @param java.lang.String componentType
     * @param java.lang.Long componentId
     * @param java.lang.Long userId
     * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Object> delete(String componentType, Long componentId, Long userId) {

        logger.info("Inside LikeController delete method");
        logger.debug("Input componentType = " + componentType + ", componentId = " + componentId + " and userId = "
                + userId);

        Object likeObject = new Object();
        try {

            logger.info("Trying to delete like object. Entering LikeBusinessDelegate delete method");
            likeObject = businessDelegate.delete(componentId, userId, componentType);

        } catch (MessageException e) {

            logger.error("Error while deleting like object for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting delete method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        } catch (Exception e) {

            logger.error("Error while deleting like object for componentType = " + componentType + ", componentId = "
                    + componentId + " and userId = " + userId);
            logger.error("Error is ", e);
            logger.info("Exiting delete method due to error");
            // e.printStackTrace();
            return new ResponseDto<Object>(false, null, e.getMessage());

        }

        if (likeObject == null) {

            logger.info("Deleted like object. Exiting delete method");
            return new ResponseDto<Object>(true, likeObject);
        }

        logger.info("Like object not null. Exiting delete method");
        return new ResponseDto<Object>(false);
    }

}
