package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity Implementation class for Entity : {@link TaskCategory}
 * @author Sarvesh
 *
 */
@Entity
@Table(name = "taskCategory1")
public class TaskCategory implements Serializable {

    /**
     * 
     */

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable=false)
    private String name;

    @Column(length = 256)
    private String description;

    public TaskCategory(Long id, String name, String description) {

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TaskCategory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
