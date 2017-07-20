package com.ohmuk.folitics.enums;

public enum UserStatus {
    ACTIVE("Active"), PENDING("Pending"), DELETED("Deleted"),SUSSPENDED("Suspended"), ;
    
    private String value;

    private UserStatus(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final UserStatus getUserStatus(String value) {
        if (ACTIVE.getValue().equals(value)) {
            return ACTIVE;
        }
        if (PENDING.getValue().equals(value)) {
            return PENDING;
        }
        if (DELETED.getValue().equals(value)) {
            return DELETED;
        }
        if (SUSSPENDED.getValue().equals(value)) {
            return SUSSPENDED;
        }
        return null;
    }
}
