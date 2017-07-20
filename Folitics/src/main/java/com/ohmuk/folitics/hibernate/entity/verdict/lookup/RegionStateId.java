package com.ohmuk.folitics.hibernate.entity.verdict.lookup;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Embeddable class to create id for entity {@link RegionState}
 * @author Abhishek
 *
 */
@Embeddable
public class RegionStateId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "regionId", referencedColumnName = "id")
    @NotNull(message = "error.regionStateId.region.notNull")
    private Region region;

    @Column(nullable = false)
    @NotNull(message = "error.regionStateId.state.notNull")
    private String state;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
