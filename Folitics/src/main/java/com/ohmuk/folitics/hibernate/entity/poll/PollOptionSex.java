package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Entity class for {@link Sex} counts for {@link PollOption}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "polloptionsex")
@PrimaryKeyJoinColumn(name = "id")
public class PollOptionSex implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PollOptionSexId id;

    @Column(nullable = false)
    @NotNull(message = "error.pollOptionSex.count.notNull")
    private Long count;

    public PollOptionSex() {

    }

    public PollOptionSexId getId() {
        return id;
    }

    public void setId(PollOptionSexId id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
