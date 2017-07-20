package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;

/**
 * This entity is for maintaining count for comment on entity:
 * {@link GovtSchemeData}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "govtSchemeDataCommentCount")
public class GovtSchemeDataCommentCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GovtSchemeDataCommentCountId id;

	@Column(nullable = false)
	@NotNull(message = "error.GovtSchemeDataCommentCount.count.notNull")
	private Long commentCount;

	/**
	 * @return the commentCount
	 */
	public Long getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount
	 *            the commentCount to set
	 */
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * @return the id
	 */
	public GovtSchemeDataCommentCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(GovtSchemeDataCommentCountId id) {
		this.id = id;
	}

}
