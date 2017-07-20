package com.ohmuk.folitics.hibernate.entity.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.util.DateUtils;

@Entity
@Table(name="opinionshare")
public class OpinionShare implements Serializable{

	   /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "error.share.editTime.notNull")
    private Timestamp editTime;

    @Column(nullable = false)
    @NotNull(message = "error.share.createTime.notNull")
    private Timestamp createTime;

    @Column(nullable = false)
    @NotNull(message = "error.share.componentId.notNull")
    private Long componentId;

    @Column(nullable = false)
    @NotNull(message = "error.share.userId.notNull")
    private Long userId;

    @Column(nullable = false, length = 512)
    @NotNull(message = "error.share.platform.notNull")
    private String platform;

    public OpinionShare() {
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
