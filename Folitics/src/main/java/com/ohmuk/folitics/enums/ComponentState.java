package com.ohmuk.folitics.enums;

public enum ComponentState {
     ACTIVE("Active"), DISABLED("Disabled"), DELETED("Deleted");

    private String value;

    private ComponentState(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ComponentState getCategoryState(String value) {
        if (ACTIVE.getValue().equals(value)) {
            return ACTIVE;
        }
        if (DISABLED.getValue().equals(value)) {
            return DISABLED;
        }
        if (DELETED.getValue().equals(value)) {
            return DELETED;
        }

        return null;
    }
}
