package com.ohmuk.folitics.hibernate.entity.attachment;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ohmuk.folitics.util.DateUtils;

/**
 * The persistent class for the attachment file database table.
 * 
 * @author Jahid Ali
 */
@Entity
@Table(name = "attachmentfile")
public class AttachmentFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Lob
	@Column(nullable = false)
	private byte[] data;

	@Column(nullable = false)
	private Timestamp edited;

	@Lob
	@Column(nullable = true)
	private byte[] thumbnail;

	@Column(nullable = false)
	private Timestamp timestamp;

	@Column(nullable = true, length = 200)
	private String title;

	@Column(nullable = false, length = 25, columnDefinition = "enum('png','jpg','jpeg', 'gif','docx', 'pdf','txt')")
	private String type;

	// bi-directional many-to-one association to Attachment
	// @OneToOne(mappedBy = "fileId")
	// private Set<Attachment> attachments;

	public AttachmentFile() {
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the edited
	 */
	public Timestamp getEdited() {
		return edited;
	}

	/**
	 * @param edited
	 *            the edited to set
	 */
	public void setEdited(Timestamp edited) {
		this.edited = edited;
	}

	/**
	 * @return the thumbnail
	 */
	public byte[] getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
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
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}