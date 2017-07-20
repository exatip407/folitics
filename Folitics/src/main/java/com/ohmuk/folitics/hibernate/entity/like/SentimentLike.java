package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity for like on entity: {@link Sentiment}
 * @author Abhishek
 *
 */

@Entity
@Table(name = "sentimentlike")
@NamedQuery(name = "SentimentLike.findAll", query = "SELECT s FROM SentimentLike s")
public class SentimentLike implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LikeId id;

    @Column(nullable = false)
    @NotNull(message = "error.componentLike.editTime.notNull")
    private Timestamp editTime;

    @Column(nullable = false)
    @NotNull(message = "error.componentLike.createTime.notNull")
    private Timestamp createTime;

    @Column(nullable = false)
    @NotNull(message = "error.componentLike.likeFlag.notNull")
    private boolean likeFlag;

    @Column(nullable = false)
    @NotNull(message = "error.componentLike.dislikeFlag.notNull")
    private boolean dislikeFlag;

    public SentimentLike() {

        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
    }

    public LikeId getLikeId() {
        return id;
    }

    public void setLikeId(LikeId likeId) {
        this.id = likeId;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public boolean isLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(boolean likeFlag) {
        this.likeFlag = likeFlag;
    }

    public boolean isDislikeFlag() {
        return dislikeFlag;
    }

    public void setDislikeFlag(boolean dislikeFlag) {
        this.dislikeFlag = dislikeFlag;
    }
}
