package com.ohmuk.folitics.businessDelegate.interfaces;

import com.ohmuk.folitics.beans.PollOptionAnswer;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroup;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualification;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSex;

/**
 * BusinessDelegate interface for {@link PollOption} distributions
 * @author Abhishek
 *
 */
public interface IPollOptionDistributionBusinessDelegate {

    /**
     * BusinessDelegate method to create object in database for distribution based on vote on PollOption and User
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option which user selected
     * @param com.ohmuk.folitics.jpa.entity.user.User
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOption
     * @throws com.ohmuk.folitics.exception.MessageException
     * @throws java.lang.Exception
     */

    public PollOption create(PollOptionAnswer pollOptionAnswer) throws MessageException, Exception;

    /**
     * BusinessDelegate method to read object from database for distribution
     * 
     * @author Abhishek
     * @param java.lang.Long poll option id for which poll option is requested
     * @return com.ohmuk.folitics.jpa.entity.verdict.Verdict
     */

    public PollOption read(Long id);

    /**
     * BusinessDelegate method to aggregate the {@link PollOptionAgeGroup} objects
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option on which user voted
     * @param com.ohmuk.folitics.jpa.entity.user.User user who voted for poll option
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOptionAgeGroup the aggregated object of {@link PollOptionAgeGroup}
     * @throws com.ohmuk.folitics.exception.MessageException
     */

    public PollOptionAgeGroup aggregatPollOptionAgeGroup(PollOption pollOption, User user) throws MessageException;

    /**
     * BusinessDelegate method to aggregate the {@link VerdictSex} objects
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option on which user voted
     * @param com.ohmuk.folitics.jpa.entity.user.User user who voted for poll option
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOptionSex the aggregated object of {@link PollOptionSex}
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public PollOptionSex aggregatPollOptionSex(PollOption pollOption, User user) throws MessageException;

    /**
     * BusinessDelegate method to aggregate the {@link VerdictMaritalStatus} objects
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option on which user voted
     * @param com.ohmuk.folitics.jpa.entity.user.User user who voted for poll option
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOptionMaritalStatus the aggregated object of
     *         {@link PollOptionMaritalStatus}
     * @throws com.ohmuk.folitics.exception.MessageException
     */

    public PollOptionMaritalStatus aggregatPollOptionMaritalStatus(PollOption pollOption, User user)
            throws MessageException;

    /**
     * BusinessDelegate method to aggregate the {@link VerdictRegion} objects
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option on which user voted
     * @param com.ohmuk.folitics.jpa.entity.user.User user who voted for poll option
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOptionRegion the aggregated object of {@link PollOptionRegion}
     * @throws com.ohmuk.folitics.exception.MessageException
     */

    public PollOptionRegion aggregatPollOptionRegion(PollOption pollOption, User user) throws MessageException;

    /**
     * BusinessDelegate method to aggregate the {@link VerdictReligion} objects
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option on which user voted
     * @param com.ohmuk.folitics.jpa.entity.user.User user who voted for poll option
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOptionReligion the aggregated object of {@link PollOptionReligion}
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public PollOptionReligion aggregatPollOptionReligion(PollOption pollOption, User user) throws MessageException;

    /**
     * BusinessDelegate method to aggregate the {@link VerdictQualification} objects
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.poll.PollOption the poll option on which user voted
     * @param com.ohmuk.folitics.jpa.entity.user.User user who voted for poll option
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOptionQualification the aggregated object of
     *         {@link PollOptionQualification}
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public PollOptionQualification aggregatPollOptionQualification(PollOption pollOption, User user)
            throws MessageException;
    
    /**
     * Method is to save poll answer
     * @param pollOption
     * @param user
     * @return
     */
    public boolean answerPoll(Long pollOption, Long userId) throws Exception,MessageException;

}
