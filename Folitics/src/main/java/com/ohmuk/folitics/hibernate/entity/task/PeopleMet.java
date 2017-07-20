package com.ohmuk.folitics.hibernate.entity.task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sarvesh
 *
 */
@Entity
@Table(name = "peopleMet")
public class PeopleMet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "pname", length = 50)
    private String name;

    @Column(length = 40)
    private String departmentName;

    @Column(length = 100)
    private String location;

    @Column(length = 250)
    private String actionTaken;

    @Column
    private boolean isHelpfull;

    public PeopleMet() {
    }

    /**
     * @param name
     * @param departmentName
     * @param location
     * @param actionTaken
     */
    public PeopleMet(String name, String departmentName, String location, String actionTaken) {
        this.name = name;
        this.departmentName = departmentName;
        this.location = location;
        this.actionTaken = actionTaken;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the actionTaken
     */
    public String getActionTaken() {
        return actionTaken;
    }

    /**
     * @param actionTaken the actionTaken to set
     */
    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    /**
     * @return the isHelpfull
     */
    public boolean isHelpfull() {
        return isHelpfull;
    }

    /**
     * @param isHelpfull the isHelpfull to set
     */
    public void setHelpfull(boolean isHelpfull) {
        this.isHelpfull = isHelpfull;
    }
}
