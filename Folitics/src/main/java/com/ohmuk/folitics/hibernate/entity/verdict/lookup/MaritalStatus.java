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
 * Lookup entity for marital status
 * @author Abhishek
 *
 */
@Entity
@Table(name = "maritalstatus")
public class MaritalStatus implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "enum('Single', 'Married', 'Separated', 'Divorced', 'Widowed')")
    @NotNull(message = "error.maritalStatus.maritalStatus.notNull")
    private String maritalStatus;

    public MaritalStatus() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
