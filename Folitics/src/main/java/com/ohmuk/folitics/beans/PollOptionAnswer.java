package com.ohmuk.folitics.beans;

import java.io.Serializable;

import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;

/**
 * Bean class for answer poll flow
 * @author Abhishek
 *
 */
public class PollOptionAnswer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PollOption pollOption;
    private User user;

    public PollOptionAnswer() {

    }

    public PollOption getPollOption() {
        return pollOption;
    }

    public void setPollOption(PollOption pollOption) {
        this.pollOption = pollOption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
