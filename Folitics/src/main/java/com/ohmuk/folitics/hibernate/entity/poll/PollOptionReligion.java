package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;

/**
 * Entity class for {@link Religion} counts for {@link PollOption}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "pollreligion")
@PrimaryKeyJoinColumn(name = "id")
public class PollOptionReligion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PollOptionReligionId id;

    @Column(nullable = false)
    @NotNull(message = "error.pollOptionReligion.count.notNull")
    private Long count;

    public PollOptionReligion() {

    }

    public PollOptionReligionId getId() {
        return id;
    }

    public void setId(PollOptionReligionId id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
