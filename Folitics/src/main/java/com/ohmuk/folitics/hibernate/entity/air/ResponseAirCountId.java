package com.ohmuk.folitics.hibernate.entity.air;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Response;
/**
 * Embeddable class to create id for entity: {@link ResponseAirCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class ResponseAirCountId  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "responseId", referencedColumnName = "id")
	private Response response;

	/**
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(Response response) {
		this.response = response;
	}

}
