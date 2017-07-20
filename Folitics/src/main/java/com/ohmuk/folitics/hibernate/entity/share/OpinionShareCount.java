package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "opinionsharecount")
@NamedQuery(name = "opinionShareCount.findAll", query = "SELECT s FROM OpinionShareCount s")
@PrimaryKeyJoinColumn(name = "id")
public class OpinionShareCount implements Serializable{
	
	   /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OpinionShareCountId id;

    @Column(nullable = false)
    @NotNull(message = "error.shareCount.facebookShareCount.notNull")
    private Long facebookShareCount;

    @Column(nullable = false)
    @NotNull(message = "error.shareCount.twitterShareCount.notNull")
    private Long twitterShareCount;
    
    public OpinionShareCount() {

    }

	public OpinionShareCountId getId() {
		return id;
	}

	public void setId(OpinionShareCountId id) {
		this.id = id;
	}

	public Long getFacebookShareCount() {
		return facebookShareCount;
	}

	public void setFacebookShareCount(Long facebookShareCount) {
		this.facebookShareCount = facebookShareCount;
	}

	public Long getTwitterShareCount() {
		return twitterShareCount;
	}

	public void setTwitterShareCount(Long twitterShareCount) {
		this.twitterShareCount = twitterShareCount;
	}

}
