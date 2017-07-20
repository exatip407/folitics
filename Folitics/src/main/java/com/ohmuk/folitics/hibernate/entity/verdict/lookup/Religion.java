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
 * Lookup entity for religions
 * @author Abhishek
 *
 */
@Entity
@Table(name = "religion")
public class Religion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "enum('Christian', 'Muslim', 'Hindu', 'Sikkh', 'Jain')")
    @NotNull(message = "error.religion.religion.notNull")
    private String religion;

    public Religion() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

}
