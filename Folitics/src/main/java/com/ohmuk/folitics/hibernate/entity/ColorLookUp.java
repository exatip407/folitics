package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: ColorLookUp
 * @author Mayank Sharma
 *
 */
@Entity
@Table(name = "color_lookup")
public class ColorLookUp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 10)
    @NotNull(message = "error.colorLookUp.colorCode.notNull")
    @Size(min = 1, max = 10, message = "error.colorLookUp.colorCode.size")
    private String colorCode;

    @Column(nullable = true, length = 255)
    @Size(min = 1, max = 255, message = "error.colorLookUp.description.size")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
