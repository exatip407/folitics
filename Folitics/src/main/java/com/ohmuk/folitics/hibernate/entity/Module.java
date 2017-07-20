package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: MonetizationConfig
 * 
 */
@Entity
@Table(name = "module")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class Module implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false, length = 25, columnDefinition = "enum('GPI', 'GA', 'Task', 'Verdict', 'Poll','Contest')")
	@NotNull(message = "error.Module.module.NotNull")
	private String module;

	@Column(nullable = false, length = 100, columnDefinition = "enum('Opinion','Air', 'Sentiment', 'Polls', 'Task', 'Image', 'Video', 'Link', 'Graph', 'GlobalIndicatorGraph', 'LocalIndicatorGraph', 'GovtSchemeData', 'ComparisonGraph', 'SubmitFact', 'LocalVerdict','LocalVerdictReport', 'GlobalVerdictReport','Bumper','Mega','Mini','Chart')")
	@NotNull(message = "error.Module.componentType.NotNull")
	private String componentType;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the componentType
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}


}
