package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentType;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: Link
 * @author Abhishek
 *
 */
@Entity
@Table(name = "link")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
@PrimaryKeyJoinColumn(name = "id")
public class Link extends Component implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 500)
    @NotNull(message = "error.link.description.notNull")
    @Size(min = 1, max = 500, message = "error.link.description.size")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "error.link.editTime.notNull")
    private Timestamp editTime;

    @Column(nullable = false, length = 500)
    @NotNull(message = "error.link.link.notNull")
    @Size(min = 1, max = 500, message = "error.link.link.size")
    private String link;

    @Column(nullable = false)
    @NotNull(message = "error.link.createTime.notNull")
    private Timestamp createTime;

    @OneToOne
    @JoinColumn(name = "sentiment", referencedColumnName = "id")
    @NotNull(message = "error.link.sentiment.notNull")
    private Sentiment sentiment;

    public Link() {
        setComponentType(ComponentType.LINK.getValue());
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getEditTime() {
        return this.editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }
}
