package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Embeddable class to create id for entity {@link PollOptionSex}
 * @author Abhishek
 *
 */
@Embeddable
public class PollOptionSexId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pollOptionId", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionSexId.pollOption.notNull")
    private PollOption pollOption;

    @ManyToOne
    @JoinColumn(name = "sex", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionSexId.sex.notNull")
    private Sex sex;

    public PollOptionSexId() {

    }

    public PollOption getPollOption() {
        return pollOption;
    }

    public void setPollOption(PollOption pollOption) {
        this.pollOption = pollOption;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

}
