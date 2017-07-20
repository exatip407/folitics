package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ohmuk.folitics.hibernate.entity.Sentiment;

/**
 * This entity is for maintaining count for airs on entity: {@link Sentiment}
 * 
 * @author Harish
 *
 */

@Entity
@Table(name = "taskaircount")
@PrimaryKeyJoinColumn(name = "id")
public class TaskAirCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    @EmbeddedId
    private TaskCountId id;
    
    @Column(nullable = false)
    @NotNull(message = "error.TaskAirCount.count.notNull")
    private Long airCount;

    /**
	 * @return the id
	 */
	public TaskCountId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TaskCountId id) {
		this.id = id;
	}

	/**
	 * @return the airCount
	 */
	public Long getAirCount() {
		return airCount;
	}

	/**
	 * @param airCount the airCount to set
	 */
	public void setAirCount(Long airCount) {
		this.airCount = airCount;
	}

	

}
