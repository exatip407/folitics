package com.ohmuk.folitics.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A generic dto for sending responses from controllers. Depending on the
 * content-type, the response will be converted either as JSON or XML or in
 * another format.
 * 
 * @param <T>
 *            the domain class to be transferred
 */
public class ResponseDto<T extends Object> {

	protected Boolean success;
	String s;

	protected List<T> messages;
	protected List<String> responseMessages;
	protected Set<T> setMessages;

	public ResponseDto() {
		super();
		this.success = Boolean.FALSE;
	}

	public ResponseDto(Boolean success) {
		super();
		this.success = success;
	}

	public ResponseDto(Boolean success, T message) {
		super();
		this.success = success;
		this.messages = new ArrayList<T>();
		this.messages.add(message);
	}

	public ResponseDto(Boolean success, List<T> messages) {
		super();
		this.success = success;
		this.messages = messages;
	}

	public ResponseDto(Boolean success, List<T> messages, String responseMessage) {
		super();
		this.success = success;
		this.messages = messages;
		this.responseMessages = new ArrayList<String>();
		this.responseMessages.add(responseMessage);
	}

	public ResponseDto(Boolean success, Set<T> setMessages,
			String responseMessage, String test) {
		super();
		this.success = success;
		this.setMessages = new HashSet<T>();
		this.setMessages.addAll(setMessages);
		this.responseMessages = new ArrayList<String>();
		this.responseMessages.add(responseMessage);
		this.s = test;
	}
	
	public ResponseDto(Boolean success, Set<T> setMessages) {
        super();
        this.success = success;
        this.setMessages = new HashSet<T>();
        this.setMessages.addAll(setMessages);
        this.responseMessages = new ArrayList<String>();
    }

	public ResponseDto(String responseMessage, Boolean success) {
		super();
		this.success = success;
		this.responseMessages = new ArrayList<String>();
		this.responseMessages.add(responseMessage);
	}

	public ResponseDto(Boolean success, T message, List<String> responseMessage) {
		super();
		this.success = success;
		this.messages = new ArrayList<T>();
		this.messages.add(message);
		this.responseMessages = responseMessage;
	}

	public ResponseDto(Boolean success, List<T> messages,
			List<String> responseMessage) {
		super();
		this.success = success;
		this.messages = messages;
		this.responseMessages = responseMessage;
	}

	public ResponseDto(Boolean success, T object, String responseMessage) {
		super();
		this.success = success;
		this.messages = new ArrayList<T>();
		this.messages.add(object);
		this.responseMessages = new ArrayList<String>();
		this.responseMessages.add(responseMessage);
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<T> getMessages() {
		return messages;
	}

	public void setMessagse(List<T> messages) {
		this.messages = messages;
	}

	public List<String> getResponseMessage() {
		return responseMessages;
	}

	public void setResponseMessage(List<String> responseMessages) {
		this.responseMessages = responseMessages;
	}
}
