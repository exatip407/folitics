package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity for like on entity: {@link Task}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "responselike")
public class ResponseLike implements Serializable{

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

	public ResponseLike() {

		setCreateTime(DateUtils.getSqlTimeStamp());
		setEditTime(DateUtils.getSqlTimeStamp());
	}

	/**
	 * @return the id
	 */
	public LikeId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(LikeId id) {
		this.id = id;
	}

	/**
	 * @return the editTime
	 */
	public Timestamp getEditTime() {
		return editTime;
	}

	/**
	 * @param editTime the editTime to set
	 */
	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the likeFlag
	 */
	public boolean isLikeFlag() {
		return likeFlag;
	}

	/**
	 * @param likeFlag the likeFlag to set
	 */
	public void setLikeFlag(boolean likeFlag) {
		this.likeFlag = likeFlag;
	}

	/**
	 * @return the dislikeFlag
	 */
	public boolean isDislikeFlag() {
		return dislikeFlag;
	}

	/**
	 * @param dislikeFlag the dislikeFlag to set
	 */
	public void setDislikeFlag(boolean dislikeFlag) {
		this.dislikeFlag = dislikeFlag;
	}

}
