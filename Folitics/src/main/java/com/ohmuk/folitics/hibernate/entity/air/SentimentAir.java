package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "sentimentair")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")

public class SentimentAir implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    @NotNull(message = "error.opinion.editTime.notNull")
    private Timestamp editTime;

	@Column(nullable = false)
	@NotNull(message = "error.opinion.createTime.notNull")
	private Timestamp createTime;

	@Column(nullable = false)
	private long componentId;
	
	@Column(nullable = false, length = 512)
	private String componentType;
	
	@Column(nullable = false, length = 25, columnDefinition = "enum('Active','Disabled','Deleted')")
	@NotNull(message = "error.category.status.notNull")
	private String status;

	@Column(nullable = false)
	private long userId;
	
	@Column
	private String description;

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public SentimentAir() {
       
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
        setStatus(ComponentState.ACTIVE.getValue());
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
    public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	

}
