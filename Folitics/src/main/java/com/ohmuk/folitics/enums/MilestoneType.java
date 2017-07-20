package com.ohmuk.folitics.enums;

/**
 * @author Abhishek Patel
 *
 */
public enum MilestoneType {
    SUPER("Super"), MEGA("Mega"), MINI("Mini");

    private String value;

    private MilestoneType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final MilestoneType getMilestoneType(String value) {
        if (SUPER.getValue().equals(value)) {
            return SUPER;
        }
        if (MEGA.getValue().equals(value)) {
            return MEGA;
        }
        if (MINI.getValue().equals(value)) {
            return MINI;
        }

        return null;
    }
}
