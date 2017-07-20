package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;

/**
 * Embeddable class to create id for entity {@link PollOptionReligion}
 * @author Abhishek
 *
 */
@Embeddable
public class PollOptionReligionId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pollOptionId", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionReligionId.pollOption.notNull")
    private PollOption pollOption;

    @ManyToOne
    @JoinColumn(name = "religion", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionReligionId.religion.notNull")
    private Religion religion;

    public PollOptionReligionId() {

    }

    public PollOption getPollOption() {
        return pollOption;
    }

    public void setPollOption(PollOption pollOption) {
        this.pollOption = pollOption;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

}
