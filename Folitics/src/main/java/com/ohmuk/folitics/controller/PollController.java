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

import com.ohmuk.folitics.beans.PollOptionAnswer;
import com.ohmuk.folitics.businessDelegate.interfaces.IPollBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IPollOptionDistributionBusinessDelegate;
import com.ohmuk.folitics.charting.beans.PollResultBean;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/poll")
public class PollController {

    @Autowired
    private volatile IPollBusinessDelegate businessDelegate;

    @Autowired
    private volatile IPollOptionDistributionBusinessDelegate distributionBusinessDelegate;

    private static Logger logger = LoggerFactory.getLogger(PollController.class);

    @RequestMapping
    public String getPollPage() {

        return "poll-page";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> getAdd() {

        List<Poll> polls = new ArrayList<>();
        polls.add(getTestPoll());
        return new ResponseDto<Poll>(true, polls);
    }

    /**
     * Web service is to add {@link Poll}
     * 
     * @param poll
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Poll> add(@Validated @RequestBody Poll poll) {
        logger.info("Inside PollController add method");
        try {
            poll = businessDelegate.create(poll);
        } catch (Exception exception) {
            logger.error("Exception in adding poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController add method");
            return new ResponseDto<Poll>(false);
        }
        if (poll != null) {
            logger.debug("Poll is save");
            logger.info("Exiting from PollController add method");
            return new ResponseDto<Poll>(true, poll);
        }
        logger.debug("Poll is not save");
        logger.info("Exiting from PollController add method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service is to update {@link Poll}
     * @param poll
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Poll> edit(@RequestBody Poll poll) {
        logger.info("Inside PollController edit method");
        try {
            poll = businessDelegate.update(poll);
        } catch (Exception exception) {
            logger.error("Exception in updating poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController edit method");
            return new ResponseDto<Poll>(false);
        }
        if (poll != null) {
            logger.debug("Poll is updated: " + poll.getId());
            logger.info("Exiting from PollController edit method");
            return new ResponseDto<Poll>(true, poll);
        }
        logger.debug("Poll is not update");
        logger.info("Exiting from PollController edit method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to delete {@link Poll} by id
     * @param id
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> delete(Long id) {
        logger.info("Inside PollController delete method");
        try {
            if (businessDelegate.delete(id)) {
                logger.debug("Poll with id: " + id + " is delete");
                logger.info("Exiting from PollController delete method");
                return new ResponseDto<Poll>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in soft deleting poll by id");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController delete method");
            return new ResponseDto<Poll>(false);
        }
        logger.debug("Poll with id: " + id + " is not delete");
        logger.info("Exiting from PollController delete method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to delete {@link Poll}
     * @param poll
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Poll> delete(@Validated @RequestBody Poll poll) {
        logger.info("Inside PollController delete method");
        try {
            if (businessDelegate.delete(poll)) {
                logger.debug("Poll with id: " + poll.getId() + " is delete");
                logger.info("Exiting from PollController delete method");
                return new ResponseDto<Poll>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in soft deleting poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController delete method");
            return new ResponseDto<Poll>(false);
        }
        logger.debug("Poll with id: " + poll.getId() + " is not delete");
        logger.info("Exiting from PollController delete method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to delete hard {@link Poll} by id
     * @param id
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/deleteFromDbById", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> deleteFromDB(Long id) {
        logger.info("Inside PollController deleteFromDB method");
        try {
            Boolean test = businessDelegate.deleteFromDB(id);

            if (test) {
                logger.debug("Poll with id: " + id + " is delete");
                logger.info("Exiting from PollController deleteFromDB method");
                return new ResponseDto<Poll>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in hard deleting poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController deleteFromDB method");
            return new ResponseDto<Poll>(false);
        }
        logger.debug("Poll with id: " + id + " is not delete");
        logger.info("Exiting from PollController deleteFromDB method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * web service to hard delete {@link Poll}
     * @param poll
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Poll> deleteFromDB(@Validated @RequestBody Poll poll) {
        logger.info("Inside PollController add method");
        try {
            if (businessDelegate.deleteFromDB(poll)) {
                logger.debug("Poll with id: " + poll.getId() + " is delete");
                logger.info("Exiting from PollController deleteFromDB method");
                return new ResponseDto<Poll>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in hard deleting poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController deleteFromDB method");
            return new ResponseDto<Poll>(false);
        }
        logger.debug("Poll with id: " + poll.getId() + " is not delete");
        logger.info("Exiting from PollController deleteFromDB method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to get all {@link Poll}
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> getall() {
        logger.info("Inside PollController getall method");
        List<Poll> polls = null;
        try {
            polls = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in getting getall poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController getall method");
            return new ResponseDto<Poll>(false);
        }
        if (polls != null) {
            logger.debug(polls.size() + " polls is found");
            logger.info("Exiting from PollController getall method");
            return new ResponseDto<Poll>(true, polls);
        }
        logger.debug("No poll is found");
        logger.info("Exiting from PollController getall method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to get all active {@link Poll}
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/getActivePolls", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> getActivePolls() {
        logger.info("Inside PollController getActivePolls method");
        List<Poll> polls = null;
        try {
            polls = businessDelegate.readAllActivePoll();
        } catch (Exception exception) {
            logger.error("Exception in getting all active poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController getActivePolls method");
            return new ResponseDto<Poll>(false);
        }
        if (polls != null) {
            logger.debug(polls.size() + " polls is found");
            logger.info("Exiting from PollController getActivePolls method");
            return new ResponseDto<Poll>(true, polls);
        }
        logger.debug("No poll is found");
        logger.info("Exiting from PollController getActivePolls method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to get {@link Poll} by id
     * @param id
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> find(Long id) {
        logger.info("Inside PollController add method");
        try {
            Poll poll = businessDelegate.getPollById(id);
            logger.debug("Poll with id: " + poll.getId() + " is found");
            logger.info("Exiting from PollController find method");
            logger.info("Exiting from PollController find method");
            return new ResponseDto<Poll>(true, poll);
        } catch (Exception exception) {
            logger.error("Exception in finding poll by id");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController find method");
            return new ResponseDto<Poll>(false);
        }
    }

    /**
     * Web service to get {@link Poll} for {@link com.ohmuk.folitics.hibernate.entity.Sentiment}
     * @param id
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/getPollForSentiment", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> getPollForSentiment(Long id) {
        logger.info("Inside PollController add method");
        List<Poll> polls = null;
        try {
            polls = businessDelegate.getPollsForSentiment(id);
        } catch (Exception exception) {
            logger.error("Exception in getting poll getPollForSentiment sentiment");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController getPollForSentiment method");
            return new ResponseDto<Poll>(false);
        }
        if (polls != null) {
            logger.debug(polls.size() + " polls is found for setiment id: " + id);
            logger.info("Exiting from PollController getPollForSentiment method");
            return new ResponseDto<Poll>(true, polls);
        }
        logger.debug("No poll is found for sentiment id: " + id);
        logger.info("Exiting from PollController getPollForSentiment method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to get {@link Poll} which are not attached with any
     * {@link com.ohmuk.folitics.hibernate.entity.Sentiment}
     * @return ResponseDto<Poll>
     */
    @RequestMapping(value = "/getIsolatedPolls", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Poll> getIsolatedPolls() {
        logger.info("Inside PollController add method");
        List<Poll> polls = null;
        try {
            polls = businessDelegate.getIsolatedPolls();
        } catch (Exception exception) {
            logger.error("Exception in getting getIsolatedPolls poll");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController getIsolatedPolls method");
            return new ResponseDto<Poll>(false);
        }
        if (polls != null) {
            logger.debug(polls.size() + " isolated polls is found");
            logger.info("Exiting from PollController getIsolatedPolls method");
            return new ResponseDto<Poll>(true, polls);
        }
        logger.debug("No isolated poll is found");
        logger.info("Exiting from PollController getIsolatedPolls method");
        return new ResponseDto<Poll>(false);
    }

    /**
     * Web service to save {@link PollOption}
     * @param pollOption
     * @return ResponseDto<PollOption>
     */
    @RequestMapping(value = "/answerPoll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Object> answerPoll(Long pollOptionId, Long userId) {

        try {
            return new ResponseDto<Object>(distributionBusinessDelegate.answerPoll(pollOptionId, userId));

        } catch (Exception exception) {

            logger.error("Exception while answering poll " + exception);
            return new ResponseDto<Object>(false);
        }
    }

    /**
     * Web service to get {@link PollOption} by {@link Poll} id
     * @param id
     * @return ResponseDto<Set<PollOption>>
     */
    @RequestMapping(value = "/getPollResult", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseDto<List<PollResultBean>> getPollResult(Long id, String filter) {
        logger.info("Inside PollController getPollResult method");
        List<List<PollResultBean>> pollResults = null;
        try {
            pollResults = businessDelegate.getPollResult(id, filter);
        } catch (Exception exception) {
            logger.error("Exception in getting getPollResult result");
            logger.error("Exception: " + exception);
            logger.info("Exiting from PollController getPollResult method");
            return new ResponseDto<List<PollResultBean>>(false);
        }
        if (pollResults != null) {
            logger.debug(pollResults + " Poll result is found");
            logger.info("Exiting from PollController getPollResult method");
            return new ResponseDto<List<PollResultBean>>(true, pollResults);
        }
        logger.debug("No poll result is found");
        logger.info("Exiting from PollController getPollResult method");
        return new ResponseDto<List<PollResultBean>>(false);
    }

    /**
     * Web service to get {@link Poll} by id
     * @param id
     * @return ResponseDto<Poll>
     */
    /*
     * @RequestMapping(value = "/getPollOptionCount", method = RequestMethod.GET) public @ResponseBody ResponseDto<Long>
     * getPollOptionCount(Long id) { logger.info("Inside PollController add method"); try { return new
     * ResponseDto<Long>(true, businessDelegate.getPollOptionCount(id)); } catch (Exception exception) {
     * logger.error("Exception in finding poll by id"); logger.error("Exception: " + exception);
     * logger.info("Exiting from PollController find method"); return new ResponseDto<Long>(false); } }
     */

    @RequestMapping(value = "/getTestPoll", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Poll getTestPoll() {

        return getDummyPoll();

    }

    private Poll getDummyPoll() {

        Poll poll = new Poll();
        // poll.setId((new Random()).nextLong());
        PollOption option1 = new PollOption();
        option1.setPollOption("this is option 1");
        PollOption option2 = new PollOption();
        option2.setPollOption("this is option 2");
        List<PollOption> pollOptions = new ArrayList<PollOption>();
        pollOptions.add(option1);
        pollOptions.add(option2);
        poll.setQuestion("What is the qustion?");
        poll.setOptions(pollOptions);
        return poll;
    }

}
