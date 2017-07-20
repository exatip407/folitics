package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.ohmuk.folitics.beans.PollOptionAnswer;
import com.ohmuk.folitics.charting.beans.PollResultBean;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

public interface IPollBusinessDelegate {
    

    /**
     * Method is to add {@link Poll}
     * @param poll
     * @return
     * @throws Exception
     */
    public Poll create(Poll poll) throws Exception;

    /**
     * Method is to get {@link Poll} by id
     * @param id
     * @return {@link Poll}
     * @throws Exception
     */
    public Poll getPollById(Long id) throws Exception;

    /**
     * Method is to get all {@link Poll}
     * @return {@link List < Poll >}
     * @throws Exception
     */
    public List<Poll> readAll() throws Exception;

    /**
     * Method is to get all active {@link Poll}
     * @return {@link Poll}
     * @throws Exception
     */
    public List<Poll> readAllActivePoll() throws Exception;

    /**
     * Method is to update {@link Poll}
     * @param poll
     * @return {@link Poll}
     * @throws Exception
     */
    public Poll update(Poll poll) throws Exception;

    /**
     * Method is to delete {@link Poll} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to delete {@link Poll}
     * @param poll
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Poll poll) throws Exception;

    /**
     * Method is hard delete {@link Poll} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Long id) throws Exception;

    /**
     * Method is to hard delete {@link Poll}
     * @param poll
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Poll poll) throws Exception;

    /**
     * Method is to get {@link Poll} by sentimentId
     * @param sentimentId
     * @return {@link List < Poll > }
     */
    public List<Poll> getPollsForSentiment(Long sentimentId) throws Exception;

    /**
     * Method is to get {@link Poll} which are not attached with any
     * {@link com.ohmuk.folitics.hibernate.entity.Sentiment}
     * @return {@link List < Poll > }
     */
    public List<Poll> getIsolatedPolls() throws Exception;

    /**
     * Method is to save {@link Poll}
     * @param sessionPoll
     * @return {@link Poll}
     */
    public Poll save(Poll sessionPoll) throws Exception;

    /**
     * Method is to save and flush {@link Poll}
     * @param sessionPoll
     * @return {@link Poll}
     * @throws Exception
     */
    public Poll saveAndFlush(Poll sessionPoll) throws Exception;

    /**
     * Method is to save {@link PollOption}
     * @param pollOption
     * @return {@link PollOption}
     */
    public PollOption answerPoll(PollOptionAnswer pollOptionAnswer) throws Exception;

    /**
     * Method is to get {@link PollOption} by pollId
     * @param pollId
     * @return
     */
    public  List<List<PollResultBean>> getPollResult(Long pollId,String filter) throws Exception;
    
}
