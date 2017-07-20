package com.ohmuk.folitics;

import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * 
 * @author Mayank Sharma
 *
 */
public interface IPollOptionService {

    public PollOption findById(Long id) throws Exception;
}
