package com.ohmuk.folitics.service;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * Service implementation for {@link PollOption}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionService implements IPollOptionService {

    private static Logger logger = LoggerFactory.getLogger(PollOptionService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public PollOption read(Long id) throws Exception {

        logger.debug("Entered PollOptionService read method");

        if (id == null) {
            logger.error("id found null in PollOptionService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the PollOption object for id = " + id);

        PollOption pollOption = (PollOption) getSession().get(PollOption.class, id);

        logger.debug("Got PollOption object from the database. Exiting PollOptionService.read method");

        return pollOption;
    }

}
