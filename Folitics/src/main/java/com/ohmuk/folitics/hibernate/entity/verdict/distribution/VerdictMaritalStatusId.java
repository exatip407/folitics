package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;

/**
 * Embeddable class to create id for entity {@link VerdictMaritalStatus}
 * @author Abhishek
 *
 */
@Embeddable
public class VerdictMaritalStatusId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "verdictId", referencedColumnName = "id")
    @NotNull(message = "error.verdictMaritalStatusId.verdict.notNull")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "maritalstatus", referencedColumnName = "id")
    @NotNull(message = "error.verdictMaritalStatusId.maritalStatus.notNull")
    private MaritalStatus maritalStatus;

    public VerdictMaritalStatusId() {

    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

}
