package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;

/**
 * Embeddable class to create id for entity {@link VerdictAgeGroup}
 * @author Abhishek
 *
 */
@Embeddable
public class VerdictAgeGroupId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "verdictId", referencedColumnName = "id")
    @NotNull(message = "error.verdictAgeGroupId.verdict.notNull")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "agegroup", referencedColumnName = "id")
    @NotNull(message = "error.verdictAgeGroupId.ageGroup.notNull")
    private AgeGroup ageGroup;

    public VerdictAgeGroupId() {

    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

}
