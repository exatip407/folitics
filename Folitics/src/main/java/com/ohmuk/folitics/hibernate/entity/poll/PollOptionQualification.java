package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;

/**
 * Entity class for {@link Qualification} counts for {@link PollOption}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "polloptionqualification")
@PrimaryKeyJoinColumn(name = "id")
public class PollOptionQualification implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PollOptionQualificationId id;

    @Column(nullable = false)
    @NotNull(message = "error.pollOptionQualification.count.notNull")
    private Long count;

    public PollOptionQualification() {

    }

    public PollOptionQualificationId getId() {
        return id;
    }

    public void setId(PollOptionQualificationId id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
