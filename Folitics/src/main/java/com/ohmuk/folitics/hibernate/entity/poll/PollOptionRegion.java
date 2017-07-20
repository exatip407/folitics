package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;

/**
 * Entity class for {@link Region} counts for {@link PollOption}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "polloptionregion")
@PrimaryKeyJoinColumn(name = "id")
public class PollOptionRegion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PollOptionRegionId id;

    @Column(nullable = false)
    @NotNull(message = "error.pollOptionRegion.count.notNull")
    private Long count;

    public PollOptionRegion() {

    }

    public PollOptionRegionId getId() {
        return id;
    }

    public void setId(PollOptionRegionId id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
