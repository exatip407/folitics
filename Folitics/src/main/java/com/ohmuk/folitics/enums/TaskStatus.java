package com.ohmuk.folitics.enums;

public enum TaskStatus {

    ACTIVE("Active"), CREATED("Created"), PENDING("Pending"), DELETED("Deleted"), BANNED("Banned"), COMPLETED(
            "Completed"), ;

    private String value;

    private TaskStatus(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final TaskStatus getUserStatus(String value) {
        if (ACTIVE.getValue().equals(value)) {
            return ACTIVE;
        }
        if (PENDING.getValue().equals(value)) {
            return PENDING;
        }
        if (DELETED.getValue().equals(value)) {
            return DELETED;
        }
        return null;
    }

}
