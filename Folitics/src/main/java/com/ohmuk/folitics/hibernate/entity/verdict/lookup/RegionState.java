package com.ohmuk.folitics.hibernate.entity.verdict.lookup;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Lookup entity for state to region mapping
 * @author Abhishek
 *
 */
@Entity
@Table(name = "regionstate")
@PrimaryKeyJoinColumn(name = "id")
public class RegionState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RegionStateId id;

    public RegionState() {

    }

    public RegionStateId getId() {
        return id;
    }

    public void setId(RegionStateId id) {
        this.id = id;
    }

}
