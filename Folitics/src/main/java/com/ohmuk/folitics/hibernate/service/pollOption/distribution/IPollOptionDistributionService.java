package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * Service Interface for {@link PollOption} Distribution
 * @author Abhishek
 *
 * @param <T>
 */
public interface IPollOptionDistributionService<T, U> {

    /**
     * Method to add the count in the respective distribution for {@link PollOption}
     * 
     * @author Abhishek
     * @param T the object of distribution to be saved in database
     * @return T the added object in the database
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T addDistribution(T t) throws MessageException;

    /**
     * Method to get the distribution values for given {@link PollOption} id and distribution id inside u
     * 
     * @author Abhishek
     * @param U the id of the object to get from database
     * @return T object got from database
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T getDistribution(U u) throws MessageException;

    /**
     * Method to update the existing distribution object in the database
     * 
     * @author Abhishek
     * @param T object that is to be updated in the database
     * @return T the updated object returned from database
     * @throws com.ohmuk.folitics.exception.MessageException
     */
    public T updateDistribution(T t) throws MessageException;
}
