package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ohmuk.folitics.hibernate.entity.Chart;

/**
 * This entity is for maintaining count for comment on entity: {@link Chart}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "chartcommentcount")
public class ChartCommentCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChartCommentCountId id;

	@Column(nullable = false)
	private Long commentCount;

	public ChartCommentCount() {
		super();
	}

	/**
	 * @return the id
	 */
	public ChartCommentCountId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ChartCommentCountId id) {
		this.id = id;
	}

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

}
