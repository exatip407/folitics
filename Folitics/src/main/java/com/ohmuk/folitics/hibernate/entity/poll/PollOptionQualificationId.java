package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;

/**
 * Embeddable class to create id for entity {@link PollOptionQualification}
 * @author Abhishek
 *
 */
@Embeddable
public class PollOptionQualificationId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pollOptionId", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionQualificationId.pollOption.notNull")
    private PollOption pollOption;

    @ManyToOne
    @JoinColumn(name = "qualification", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionQualificationId.qualification.notNull")
    private Qualification qualification;

    public PollOptionQualificationId() {

    }

    public PollOption getPollOption() {
        return pollOption;
    }

    public void setPollOption(PollOption pollOption) {
        this.pollOption = pollOption;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

}
