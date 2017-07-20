package com.ohmuk.folitics.enums;

/**
 * @author Abhishek Patel
 *
 */
public enum OpinionState {
    NEW("New"), EDITED("Edited"), DELETED("Deleted"), HIDDEN("Hidden");

    private String value;

    private OpinionState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final OpinionState getOpinionState(String value) {
        if (NEW.getValue().equals(value)) {
            return NEW;
        }
        if (EDITED.getValue().equals(value)) {
            return EDITED;
        }
        if (DELETED.getValue().equals(value)) {
            return DELETED;
        }
        if (HIDDEN.getValue().equals(value)) {
            return HIDDEN;
        }
        return null;
    }
}
