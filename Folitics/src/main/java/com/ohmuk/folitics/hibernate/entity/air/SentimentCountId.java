package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Sentiment;

/**
 * Embeddable class to create id for entity: {@link SentimentAirCount}
 * @author Abhishek
 *
 */
@Embeddable
public class SentimentCountId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "sentimentId", referencedColumnName = "id")
    private Sentiment sentiment;

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

}
