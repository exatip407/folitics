package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Embeddable class to create id for entity {@link VerdictSex}
 * @author Abhishek
 *
 */
@Embeddable
public class VerdictSexId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "verdictId", referencedColumnName = "id")
    @NotNull(message = "error.verdictSexId.verdict.notNull")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "sex", referencedColumnName = "id")
    @NotNull(message = "error.verdictSexId.sex.notNull")
    private Sex sex;

    public VerdictSexId() {

    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

}
