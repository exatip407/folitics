package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The persistent class for the component Module Storage database table.
 * 
 */

@Entity
public class ComponentModuleStorage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

	@Column(unique = false, nullable = false)
    private String component;

	@Column(unique = false, nullable = false)
    private String module;
	
	@Column(unique = true, nullable = false)
	private String storage;

	public ComponentModuleStorage() {
	    
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComponent() {
		return this.component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getStorage() {
		return this.storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

    @Override
    public String toString() {
        return "ComponentModuleStorage [id=" + id + ", component=" + component + ", module=" + module + ", storage="
                + storage + "]";
    }

}