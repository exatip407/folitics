package com.ohmuk.folitics.hibernate.entity.verdict.distribution;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;

/**
 * Embeddable class to create id for entity {@link VerdictRegion}
 * @author Abhishek
 *
 */
@Embeddable
public class VerdictRegionId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "verdictId", referencedColumnName = "id")
    @NotNull(message = "error.verdictRegionId.verdict.notNull")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "region", referencedColumnName = "id")
    @NotNull(message = "error.verdictRegionId.region.notNull")
    private Region region;

    public VerdictRegionId() {

    }

    public Verdict getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
