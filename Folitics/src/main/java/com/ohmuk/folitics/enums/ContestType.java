package com.ohmuk.folitics.enums;

public enum ContestType {

    ACTIVE("Active"), INACTIVE("Inactive"),DRAFT("Draft"),DELETED("Deleted"),BUMPER("Bumper"),MINI("Mini"),MEGA("Mega");

    private ContestType(String value) {
        this.value = value;
    }

    private String value;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    public static final ContestType getContestState(String value) {
        if (ACTIVE.getValue().equals(value)) {
            return ACTIVE;
        }
        if (INACTIVE.getValue().equals(value)) {
            return INACTIVE;
        }
        if(DRAFT.getValue().equals(value))
            return DRAFT;
        
        if(BUMPER.getValue().equals(value))
            return BUMPER;
        
        if(MEGA.getValue().equals(value))
            return MEGA;
        
        if(MINI.getValue().equals(value))
            return MINI;
        
        if(DELETED.getValue().equals(value))
            return DELETED;
        
        return null;
    }

}
