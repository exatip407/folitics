package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This entity is for maintaining count for airs on entity: {@link Sentiment}
 * @author Abhishek
 *
 */

@Entity
@Table(name = "sentimentaircount")
@NamedQuery(name = "SentimentAirCount.findAll", query = "SELECT s FROM SentimentAirCount s")
@PrimaryKeyJoinColumn(name = "id")
public class SentimentAirCount implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SentimentCountId id;

    @Column(nullable = false)
    @NotNull(message = "error.sentimentAirCount.count.notNull")
    private Long airCount;

   public SentimentAirCount() {
        super();
    }

    public SentimentCountId getId() {
        return id;
    }

    public void setId(SentimentCountId id) {
        this.id = id;
    }

    public Long getAirCount() {
        return airCount;
    }

    public void setAirCount(Long airCount) {
        this.airCount = airCount;
    }

   

}
