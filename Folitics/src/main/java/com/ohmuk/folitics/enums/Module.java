package com.ohmuk.folitics.enums;

public enum Module {

	GPI("GPI"), GA("GA"),CONTEST("Contest"),TASK("Task"), VERDICT("Vrerdict"), POLLS("Polls");

	private String value;
	
	private Module(String value) {
        this.setValue(value);
    }
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public static final Module getModule(String value){
		
	
	 if (GPI.getValue().equals(value)) {
         return GPI;
     }
     if (GA.getValue().equals(value)) {
         return GA;
     }
     if (CONTEST.getValue().equals(value)) {
         return CONTEST;
     }
     if (TASK.getValue().equals(value)) {
         return TASK;
     }
     if (VERDICT.getValue().equals(value)) {
         return VERDICT;
     }
     if (POLLS.getValue().equals(value)) {
         return POLLS;
     }
     return null;
	}
}
