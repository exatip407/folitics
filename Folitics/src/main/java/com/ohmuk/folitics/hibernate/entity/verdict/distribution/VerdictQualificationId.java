package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;

/**
 * Embeddable class to create id for entity {@link VerdictQualification}
 * @author Abhishek
 *
 */
@Embeddable
public class VerdictQualificationId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "verdictId", referencedColumnName = "id")
    @NotNull(message = "error.verdictQualificationId.verdict.notNull")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "qualification", referencedColumnName = "id")
    @NotNull(message = "error.verdictQualificationId.qualification.notNull")
    private Qualification qualification;

    public VerdictQualificationId() {

    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

}
