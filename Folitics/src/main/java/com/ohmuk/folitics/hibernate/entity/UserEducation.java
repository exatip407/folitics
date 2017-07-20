package com.ohmuk.folitics.hibernate.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: UserEducation
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name = "User_Education")
public class UserEducation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotNull(message = "error.userEducation.instituteName.notNull")
    @Size(min = 1, max = 255, message = "error.userEducation.instituteName.size")
    private String instituteName;

    @Column(nullable = false, length = 255)
    @NotNull(message = "error.userEducation.courseName.notNull")
    @Size(min = 1, max = 255, message = "error.userEducation.courseName.size")
    private String courseName;

    @Column(nullable = false)
    @NotNull(message = "error.userEducation.startDate.notNull")
    private Timestamp startDate;

    @Column(nullable = false)
    @NotNull(message = "error.userEducation.endDate.notNull")
    private Timestamp endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @NotNull(message = "error.userEducation.user.notNull")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
