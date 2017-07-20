package com.ohmuk.folitics.enums;

public enum Action {
		SHARE("Share"), LIKE("Like"), COMMENT("Comment"),SUBMIT("Submit"), RESPONSE("Response"), REFERRAL("Referral"), CREATE("Create"),
		PARTICIPATE("Participate"), RAISEPROBLEM("RaiseProblem"), ACCEPT("Accept"), SUBMITFACT("SubmitFact"),DELETE("Delete"),UNLIKE("Unlike"),
		UNDISLIKE("UnDislike"),DISLIKE("Dislike"),AIR("Air");
	
	private String value;

	 private Action(String value) {
	        this.setValue(value);
	    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public static final Action getAction(String value) {
		
	if(SHARE.getValue().equals(value)){
		return SHARE;
	}
	if(LIKE.getValue().equals(value)){
		return LIKE;
	}
	if(COMMENT.getValue().equals(value)){
		return COMMENT;
	}
	if(RESPONSE.getValue().equals(value)){
		return RESPONSE;
	}
	if(REFERRAL.getValue().equals(value)){
		return REFERRAL;
	}
	if(CREATE.getValue().equals(value)){
		return CREATE;
	}
	if(PARTICIPATE.getValue().equals(value)){
		return PARTICIPATE;
	}
	if(RAISEPROBLEM.getValue().equals(value)){
		return RAISEPROBLEM;
	}
	if(ACCEPT.getValue().equals(value)){
		return ACCEPT;
	}
	if(SUBMITFACT.getValue().equals(value)){
		return SUBMITFACT;
	}
	if(DELETE.getValue().equals(value)){
		return DELETE;
	}
	if(SUBMIT.getValue().equals(value)){
		return SUBMIT;
	}
	if(AIR.getValue().equals(value)){
		return AIR;
	}
	if(DISLIKE.getValue().equals(value)){
		return DISLIKE;
	}
	if(UNDISLIKE.getValue().equals(value)){
		return UNDISLIKE;
	}
		return null;
	}
	
	
	
}
