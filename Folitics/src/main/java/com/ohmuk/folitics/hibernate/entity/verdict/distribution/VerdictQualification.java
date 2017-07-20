package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;

/**
 * Entity class for {@link Qualification} counts for {@link Verdict}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "verdictqualification")
@PrimaryKeyJoinColumn(name = "id")
public class VerdictQualification implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private VerdictQualificationId id;

    @Column(nullable = false)
    @NotNull(message = "error.verdictQualification.proCount.notNull")
    private Long proCount;

    @Column(nullable = false)
    @NotNull(message = "error.verdictQualification.antiCount.notNull")
    private Long antiCount;

    public VerdictQualification() {

    }

    public VerdictQualificationId getId() {
        return id;
    }

    public void setId(VerdictQualificationId id) {
        this.id = id;
    }

    public Long getProCount() {
        return proCount;
    }

    public void setProCount(Long proCount) {
        this.proCount = proCount;
    }

    public Long getAntiCount() {
        return antiCount;
    }

    public void setAntiCount(Long antiCount) {
        this.antiCount = antiCount;
    }

}
