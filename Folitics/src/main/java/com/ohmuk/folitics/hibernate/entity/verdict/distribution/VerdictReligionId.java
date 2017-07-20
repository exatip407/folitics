package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;

/**
 * Embeddable class to create id for entity {@link VerdictReligion}
 * @author Abhishek
 *
 */
@Embeddable
public class VerdictReligionId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "verdictId", referencedColumnName = "id")
    @NotNull(message = "error.verdictReligionId.verdict.notNull")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "religion", referencedColumnName = "id")
    @NotNull(message = "error.verdictReligionId.religion.notNull")
    private Religion religion;

    public VerdictReligionId() {

    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

}
