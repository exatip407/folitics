package com.ohmuk.folitics.hibernate.entity.verdict.lookup;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Lookup entity for
 * @author Abhishek
 *
 */
@Entity
@Table(name = "qualification")
public class Qualification implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "enum('Matriculation', 'Higher Secondary', 'Graduate', 'Post Graduate', 'Doctorate', 'Illiterate')")
    @NotNull(message = "error.qualification.qualification.notNull")
    private String qualification;

    public Qualification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

}
