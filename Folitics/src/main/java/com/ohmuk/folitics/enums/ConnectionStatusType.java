package com.ohmuk.folitics.enums;

/**
 * 
 * @author Mayank Sharma
 *
 */
public enum ConnectionStatusType {

    PENDING("Pending"), ACCEPTED("Accepted");

    private String value;

    private ConnectionStatusType(String value) {

        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ConnectionStatusType getMaritalStatusType(String value) {

        if (PENDING.getValue().equals(value)) {
            return PENDING;
        }
        if (ACCEPTED.getValue().equals(value)) {
            return ACCEPTED;
        }
        return null;
    }
}
