package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.Sentiment;

/**
 * Embeddable class to create id for entity {@link SentimentShareCount}
 * @author Abhishek
 *
 */
@Embeddable
public class SentimentShareCountId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "sentimentId", referencedColumnName = "id")
    @NotNull(message = "error.sentimentShareCountId.sentiment.notNull")
    private Sentiment sentiment;

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

}
