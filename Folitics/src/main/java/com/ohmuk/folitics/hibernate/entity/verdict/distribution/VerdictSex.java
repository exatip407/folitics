package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Entity class for {@link Sex} counts for {@link Verdict}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "verdictsex")
@PrimaryKeyJoinColumn(name = "id")
public class VerdictSex implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private VerdictSexId id;

    @Column(nullable = false)
    @NotNull(message = "error.verdictSex.proCount.notNull")
    private Long proCount;

    @Column(nullable = false)
    @NotNull(message = "error.verdictSex.antiCount.notNull")
    private Long antiCount;

    public VerdictSex() {

    }

    public VerdictSexId getId() {
        return id;
    }

    public void setId(VerdictSexId id) {
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
