package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: Component
 * @author Abhishek
 */
@Entity
@Table(name = "component")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "enum('Sentiment', 'Opinion', 'Response', 'Link', 'GPI', 'GA', 'GlobalVerdict', 'LocalVerdict','Chart')")
    @NotNull(message = "error.component.componentType.notNull")
    private String componentType;

    public Component() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }
}
