package com.ohmuk.folitics.controller.follow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IFollowBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    private volatile IFollowBusinessDelegate iFollowBusinessDelegate;

    @RequestMapping(value = "/unfollow", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Object> unfollow(@RequestBody FollowDataBean followDataBean)
            throws MessageException {

        logger.info("Inside unfollow FollowController");
        if (followDataBean.getComponentId() == null) {

            logger.error("Null value in field componentId");
            throw (new MessageException("Null value in field componentId"));
        }

        if (followDataBean.getUserId() == null) {

            logger.error("Null value in field userId");
            throw (new MessageException("Null value in field userId"));

        }

        else {

            try {

                iFollowBusinessDelegate.unfollowComponent(followDataBean);

            } catch (Exception e) {

                logger.error(e.getMessage());
            }

        }

        return new ResponseDto<Object>(true);

    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Object> follow(@RequestBody FollowDataBean followDataBean)
            throws MessageException, Exception {

       logger.info("Inside follow FollowController");
        logger.debug("ComponentType " + followDataBean.getComponentType());
        if (followDataBean.getComponentId() == null) {

            logger.error("Null value in field componentId");
            throw (new MessageException("Null value in field componentId"));
        }

        if (followDataBean.getUserId() == null) {

            logger.error("Null value in field userId");
            throw (new MessageException("Null value in field userId"));

        }

        if (followDataBean.getComponentType() == null) {

            logger.error("Null value in field componentType");
            throw (new MessageException("Null value in field componentType"));

        }

        else {

            try {

                iFollowBusinessDelegate.followComponent(followDataBean);

            } catch (Exception e) {

                logger.error("Something went wrong in following component: " + followDataBean.getComponentId());
                logger.error(e.getMessage());
                return new ResponseDto<Object>(false, e.getMessage());
            }
        }
        return new ResponseDto<Object>(true, "follow operation Successful");

    }

    @RequestMapping(value = "/getFollowers", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseDto<Object> followerList(@RequestBody FollowDataBean followDataBean)
            throws MessageException, Exception {

        if (followDataBean.getComponentId() == null) {

            logger.error("Null value in field componentId");
            throw (new MessageException("Null value in field componentId"));
        }

        // if (followDataBean.getUserId() == null) {
        //
        // logger.error("Null value in field userId");
        // throw (new MessageException("Null value in field userId"));
        //
        // }

        if (followDataBean.getComponentType() == null) {

            logger.error("Null value in field componentType");
            throw (new MessageException("Null value in field componentType"));

        }

        List<User> users = null;

        try {

            users = iFollowBusinessDelegate.getFollowersSet(followDataBean);

        }

        catch (Exception e) {

            logger.error(e.getMessage());

        }

        return new ResponseDto<Object>(true, users);

    }

    @RequestMapping(value = "/getFollowCount", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Long> getFollowCount(String componentType, Long componentId) {

        FollowDataBean followDataBean = new FollowDataBean();

        followDataBean.setComponentId(componentId);
        followDataBean.setComponentType(componentType);

        logger.info("Inside Count  for  Follow");

        try {
            Long count = iFollowBusinessDelegate.getFollowCount(followDataBean);

            if (count != null) {
                return new ResponseDto<Long>(true, count, "Successfully got Count");
            }
        } catch (MessageException e) {
            logger.error("CustomException while adding the follow " + e);
            return new ResponseDto(false, null, e.getMessage());

        } catch (Exception e) {
            logger.error("Exception while adding follow " + e);
            return new ResponseDto(false, null, e.getMessage());
        }
        return new ResponseDto<Long>("Something went wrong, can't fethch users", false);
    }

    @RequestMapping(value = "/isComponentFollowedByUser", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseDto<Object> isFollowing(Long componentId, String componentType, Long userId) {

        logger.info("Inside FollowController -> isFollowing()");
        boolean flag = false;

        FollowDataBean followDataBean = new FollowDataBean();

        followDataBean.setComponentId(componentId);
        followDataBean.setComponentType(componentType);
        followDataBean.setUserId(userId);

        try {

            flag = iFollowBusinessDelegate.isFollowing(followDataBean);
            logger.info("Flag = " + flag);

        } catch (MessageException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }

        List<Object> messages = new ArrayList<Object>();

        // if (flag) {
        // messages.add(true);
        //
        // } else {
        //
        // messages.add("No");
        // }
        messages.add(flag);
        logger.info("Loading response...... ");
        return new ResponseDto<Object>(true, messages);

    }

}
