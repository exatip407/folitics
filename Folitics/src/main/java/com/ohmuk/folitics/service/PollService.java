package com.ohmuk.folitics.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.PollOptionAnswer;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollService implements IPollService {

    private static Logger logger = LoggerFactory.getLogger(PollService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public Poll create(Poll poll) throws Exception {
        logger.info("Inside PollService create method");
        Long id = (Long) getSession().save(poll);
        logger.info("Exiting from PollService create method");
        return getPollById(id);
    }

    @Override
    public Poll getPollById(Long id) throws Exception {
        logger.info("Inside PollService getPollById method");
        Poll poll = (Poll) getSession().get(Poll.class, id);
        logger.info("Exiting from PollService getPollById method");
        return poll;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Poll> readAll() throws Exception {
        logger.info("Inside PollService readAll method");
        logger.info("Exiting from PollService readAll method");
        return getSession().createCriteria(Poll.class).list();
    }

    @Override
    public Poll update(Poll poll) throws Exception {
        logger.info("Inside PollService update method");
        getSession().update(poll);
        logger.info("Exiting from PollService update method");
        return poll;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside PollService delete method");
        Poll poll = getPollById(id);
        getSession().delete(poll);
        logger.info("Exiting from PollService delete method");
        return true;
    }

    @Override
    public boolean delete(Poll poll) throws Exception {
        logger.info("Inside PollService delete method");

        getSession().delete(poll);
        logger.info("Exiting from PollService delete method");
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Poll> getPollsForSentiment(Long sentimentId) throws Exception {
        logger.info("Inside PollService getPollsForSentiment method");
        Criteria criteria = getSession().createCriteria(Poll.class).add(Restrictions.eq("sentimentId", sentimentId));
        logger.info("Exiting from PollService getPollsForSentiment method");
        return criteria.list();
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside PollService deleteFromDB method");
        Poll poll = getPollById(id);
        getSession().delete(poll);
        logger.info("Exiting from PollService deleteFromDB method");
        return true;
    }

    @Override
    public boolean deleteFromDB(Poll poll) throws Exception {
        logger.info("Inside PollService deleteFromDB method");
        getSession().delete(poll);
        logger.info("Exiting from PollService deleteFromDB method");
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Poll> readAllActivePoll() throws Exception {
        logger.info("Inside PollService readAllActivePoll method");
        Set<String> setOfStates = new HashSet<String>();
        setOfStates.add(ComponentState.DELETED.getValue());
        setOfStates.add(ComponentState.DISABLED.getValue());

        Criteria criteria = getSession().createCriteria(Poll.class).add(
                Restrictions.not(Restrictions.in("state", setOfStates)));
        logger.info("Exiting from PollService readAllActivePoll method");
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Poll> getIsolatedPolls() throws Exception {
        logger.info("Inside PollService getIsolatedPolls method");
        Criteria criteria = getSession().createCriteria(Poll.class).add(Restrictions.isNull("sentiment"));
        List<Poll> polls = criteria.list();
        for(Poll poll :polls)
        {
        	Hibernate.initialize(poll.getOptions());
        }
        logger.info("Exiting from PollService getIsolatedPolls method");
        return polls;
    }

    @Override
    public Poll saveAndFlush(Poll sessionPoll) throws Exception {
        logger.info("Inside PollService saveAndFlush method");
        Long id = (Long) getSession().save(sessionPoll);
        logger.info("Exiting from PollService saveAndFlush method");
        return getPollById(id);
    }

    @Override
    public Poll save(Poll sessionPoll) throws Exception {
        logger.info("Inside PollService save method");
        Long id = (Long) getSession().save(sessionPoll);
        logger.info("Exiting from PollService save method");
        return getPollById(id);
    }

    @Override
    public PollOption answerPoll(PollOptionAnswer pollOption) throws Exception {
        logger.info("Inside PollService answerPoll method");
        Long id = (Long) getSession().save(pollOption);
        logger.info("Exiting from PollService answerPoll method");
        return (PollOption) getSession().get(PollOption.class, id);
    }

    @Override
    public Long getPollResult(PollOption pollOption) throws Exception {
        logger.info("Inside PollService getPollResult method");

        Long noOfVotes = (Long) getSession().createCriteria(PollOption.class).setProjection(Projections.rowCount())
                .add(Restrictions.eq("id", pollOption.getId())).uniqueResult();
        
        logger.info("Exiting PollService getPollResult method");
        return noOfVotes;
    }

    @Override
    public PollOption getPollOptionById(Long pollOptionId) throws Exception {
         return (PollOption) getSession().get(PollOption.class, pollOptionId);
    }
}
