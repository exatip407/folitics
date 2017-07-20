package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.PollOptionAnswer;
import com.ohmuk.folitics.businessDelegate.interfaces.IPollBusinessDelegate;
import com.ohmuk.folitics.charting.beans.PollResultBean;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;
import com.ohmuk.folitics.service.IPollResult;
import com.ohmuk.folitics.service.IPollService;
import com.ohmuk.folitics.util.DateUtils;

@Component
@Transactional
public class PollBusinessDelegate implements IPollBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(PollBusinessDelegate.class);

    @Autowired
    private volatile IPollService pollService;
    
    @Autowired
    private volatile Map<String,IPollResult> pollResultService;

    @Override
    public Poll create(Poll poll) throws Exception {

        logger.info("Inside  create method in business delegate");
        List<PollOption> list = poll.getOptions();
        for (PollOption options : list) {
            options.setPoll(poll);
        }
        Poll pollData = pollService.create(poll);
        logger.info("Exiting create method in business delegate");
        return pollData;

    }

    @Override
    public Poll getPollById(Long id) throws Exception {
        logger.info("Inside getPollById method in business delegate");
        Poll pollData = pollService.getPollById(id);
        logger.info("Exiting getPollById method in business delegate");
        return pollData;
    }

    @Override
    public List<Poll> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<Poll> pollData = pollService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return pollData;
    }

    @Override
    public List<Poll> readAllActivePoll() throws Exception {
        logger.info("Inside readAllActivePoll method in business delegate");
        List<Poll> pollData = pollService.readAllActivePoll();
        logger.info("Exiting readAllActivePoll method in business delegate");
        return pollData;
    }

    @Override
    public Poll update(Poll poll) throws Exception {
        logger.info("Inside update method in business delegate ");
        /*
         * Poll originalPoll = getPollById(poll.getId()); if (originalPoll == null) return null;
         */
        poll.setEditTime(DateUtils.getSqlTimeStamp());
        List<PollOption> list = poll.getOptions();
        for (PollOption options : list) {
            options.setPoll(poll);
        }
        Poll pollData = pollService.update(poll);
        logger.info("Exiting  update method in business delegate ");
        return pollData;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean poll = pollService.delete(id);
        logger.info("Exiting delete method in business delegate");
        return poll;
    }

    @Override
    public boolean delete(Poll poll) throws Exception {
        logger.info("Inside  delete method in business delegate ");
        poll = getPollById(poll.getId());
        poll.setState(ComponentState.DELETED.getValue());
        List<PollOption> pollOptions = poll.getOptions();
        for (PollOption pollOption : pollOptions) {
            pollOption.setState(ComponentState.DELETED.getValue());
        }
        boolean success = pollService.delete(poll);
        logger.info("Exiting  delete method in business delegate ");
        return success;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = pollService.deleteFromDB(id);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(Poll poll) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = pollService.deleteFromDB(poll);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }

    @Override
    public List<Poll> getPollsForSentiment(Long sentimentId) throws Exception {
        logger.info("Inside getPollsForSentiment method in business delegate");
        List<Poll> poll = pollService.getPollsForSentiment(sentimentId);
        logger.info("Exiting getPollsForSentiment method in business delegate");
        return poll;
    }

    @Override
    public List<Poll> getIsolatedPolls() throws Exception {
        logger.info("Inside getIsolatedPolls method in business delegate");
        List<Poll> poll = pollService.getIsolatedPolls();
        logger.info("Exiting getIsolatedPolls method in business delegate");
        return poll;
    }

    @Override
    public Poll save(Poll sessionPoll) throws Exception {
        logger.info("Inside save method in business delegate");
        Poll poll = pollService.save(sessionPoll);
        logger.info("Exiting save method in business delegate");
        return poll;
    }

    @Override
    public Poll saveAndFlush(Poll sessionPoll) throws Exception {
        logger.info("Inside saveAndFlush method in business delegate");
        Poll poll = pollService.saveAndFlush(sessionPoll);
        logger.info("Exiting saveAndFlush method in business delegate");
        return poll;
    }

    @Override
    public PollOption answerPoll(PollOptionAnswer pollOption) throws Exception {
        logger.info("Inside answerPoll method in business delegate");
        PollOption pollOptionData = pollService.answerPoll(pollOption);
        logger.info("Exiting answerPoll method in business delegate");
        return pollOptionData;
    }

    @Override
    public  List<List<PollResultBean>> getPollResult(Long pollId,String filter) throws Exception {
        Poll poll = pollService.getPollById(pollId);
        IPollResult pollResult = pollResultService.get(filter);
        return pollResult.getPollResult(poll);
    }

}
