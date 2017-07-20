package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;

/**
 * Embeddable class to create id for entity {@link PollOptionAgeGroup}
 * @author Abhishek
 *
 */
@Embeddable
public class PollOptionAgeGroupId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pollOptionId", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionAgeGroupId.pollOption.notNull")
    private PollOption pollOption;

    @ManyToOne
    @JoinColumn(name = "agegroup", referencedColumnName = "id")
    @NotNull(message = "error.pollOptionAgeGroupId.ageGroup.notNull")
    private AgeGroup ageGroup;

    public PollOptionAgeGroupId() {

    }

    public PollOption getPollOption() {
        return pollOption;
    }

    public void setPollOption(PollOption pollOption) {
        this.pollOption = pollOption;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

}
