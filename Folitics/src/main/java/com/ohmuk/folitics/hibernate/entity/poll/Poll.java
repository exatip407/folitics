package com.ohmuk.folitics.hibernate.entity.poll;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: Poll
 * @author Abhishek
 *
 */
@Entity
@Table(name = "poll")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    @NotNull(message = "error.poll.question.notNull")
    @Size(min = 1, max = 512, message = "error.poll.question.size")
    private String question;

    @Column(nullable = true, length = 512)
    @Size(min = 1, max = 512, message = "error.poll.description.size")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "error.poll.editTime.notNull")
    private Timestamp editTime;

    @Column(nullable = true)
    private Long editedBy;

    @Column(nullable = false)
    @NotNull(message = "error.poll.createTime.notNull")
    private Timestamp createTime;

    @Column(nullable = false)
    @NotNull(message = "error.poll.createdBy.notnull")
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "sentimentId", referencedColumnName = "id")
    private Sentiment sentiment;

    @Column(nullable = false, length = 25, columnDefinition = "enum('Active','Disabled','Deleted')")
    @NotNull(message = "error.poll.state.notNull")
    private String state;
   
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "poll")
    @Cascade(value = CascadeType.ALL)
    @NotNull(message = "error.poll.options.notNull")
    private List<PollOption> options;

    public Poll() {
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
        setState(ComponentState.ACTIVE.getValue());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Long getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Long editedBy) {
        this.editedBy = editedBy;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PollOption> getOptions() {
        return options;
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    public void addOption(PollOption option) {
        if (option != null) {
            options.add(option);
        }
    }

    public void removeOption(PollOption option) {
        if (option != null) {
            options.remove(option);
        }
    }

    @Override
    public String toString() {
        return "{id:" + id + ", question:" + question + ", description:" + description + ", editTime:" + editTime
                + ", editedBy:" + editedBy + ", createTime:" + createTime + ", createdBy:" + createdBy + ", sentiment:"
                + sentiment + ", state:" + state + ", options:" + options + "}";
    }
}
