package com.ohmuk.folitics.service;

import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * Service interface for {@link PollOption}
 * @author Abhishek
 *
 */
public interface IPollOptionService {

    /**
     * This method for reading the poll option object from database for a given particular id
     * 
     * @author Abhishek
     * @param java.lang.Long the id for which the poll option is querried
     * @return com.ohmuk.folitics.jpa.entity.poll.PollOption the object of poll option found in database
     * @throws Exception
     */
    public PollOption read(Long id) throws Exception;

}
