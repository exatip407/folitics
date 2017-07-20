package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity class for {@link AgeGroup} counts for {@link PollOption}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "polloptionagegroup")
@PrimaryKeyJoinColumn(name = "id")
public class PollOptionAgeGroup implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PollOptionAgeGroupId id;

    @Column(nullable = false)
    @NotNull(message = "error.pollOptionAgeGroup.count.notNull")
    private Long count;

    public PollOptionAgeGroup() {

    }

    public PollOptionAgeGroupId getId() {
        return id;
    }

    public void setId(PollOptionAgeGroupId id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
