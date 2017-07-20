package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.attachment.Attachment;


/**
 * Entity implementation class for entity : {@link TaskCategory}
 * @author Sarvesh
 *
 */
@Entity
@Table(name = "taskAttachment")
public class TaskAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "taskId", referencedColumnName = "id")
    Task task;

    @OneToOne
    @JoinColumn(name = "attachmentId", referencedColumnName = "id")
    Attachment attachment;

    public TaskAttachment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

}
