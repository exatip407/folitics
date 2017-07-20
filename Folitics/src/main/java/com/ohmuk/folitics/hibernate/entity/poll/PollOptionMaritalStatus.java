package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;

/**
 * Entity class for {@link MaritalStatus} counts for {@link PollOption}
 * @author Abhishek
 *
 */
@Entity
@Table(name = "polloptionmaritalstatus")
@PrimaryKeyJoinColumn(name = "id")
public class PollOptionMaritalStatus implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PollOptionMaritalStatusId id;

    @Column(nullable = false)
    @NotNull(message = "error.pollOptionMaritalStatus.count.notNull")
    private Long count;

    public PollOptionMaritalStatus() {

    }

    public PollOptionMaritalStatusId getId() {
        return id;
    }

    public void setId(PollOptionMaritalStatusId id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
