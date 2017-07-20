package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;

/**
 * Embeddable class to create id for entity {@link PollOptionMaritalStatus}
 * @author Abhishek
 *
 */
@Embeddable
public class PollOptionMaritalStatusId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pollOptionId", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionMaritalStatusId.pollOption.notNull")
    private PollOption pollOption;

    @ManyToOne
    @JoinColumn(name = "maritalstatus", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionMaritalStatusId.maritalStatus.notNull")
    private MaritalStatus maritalStatus;

    public PollOptionMaritalStatusId() {

    }

    public PollOption getPollOption() {
        return pollOption;
    }

    public void setPollOption(PollOption pollOption) {
        this.pollOption = pollOption;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

}
