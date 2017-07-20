package com.ohmuk.folitics.hibernate.entity.attachment;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.ohmuk.folitics.util.DateUtils;

/**
 * The persistent class for the attachment database table.
 * 
 * @author Jahid Ali
 */
@Entity
@Table(name = "attachment")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 25, columnDefinition = "enum('Image','Audio','Video', 'File')")
    private String attachmentType;

    @Column(nullable = true, length = 500)
    private String description;

    @Column(nullable = false)
    private Timestamp edited;

    @Column(length = 200)
    private String filePath;

    @Column(nullable = false, length = 25, columnDefinition = "enum('png','jpg','jpeg', 'gif','mp3', 'wma', 'wav', 'avi', 'mkv', 'movv', 'mp4', 'divx', 'flv', 'ogg' ,'ogv', 'mpeg-4', 'mpeg', 'vob', 'wmv', '3gp')")
    private String fileType;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = true, length = 200)
    private String title;

    // bi-directional many-to-one association to Attachmentfile
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "fileId")
    private AttachmentFile attachmentFile;

    public Attachment() {
        setTimestamp(DateUtils.getSqlTimeStamp());
        setEdited(DateUtils.getSqlTimeStamp());
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the attachmentType
     */
    public String getAttachmentType() {
        return attachmentType;
    }

    /**
     * @param attachmentType the attachmentType to set
     */
    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the edited
     */
    public Timestamp getEdited() {
        return edited;
    }

    /**
     * @param edited the edited to set
     */
    public void setEdited(Timestamp edited) {
        this.edited = edited;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the attachmentFile
     */
    public AttachmentFile getAttachmentFile() {
        return attachmentFile;
    }

    /**
     * @param attachmentFile the attachmentFile to set
     */
    public void setAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

}