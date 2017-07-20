package com.ohmuk.folitics.enums;

/**
 * @author Abhishek Patel
 *
 */
public enum SentimentState {
    NEW("New"), ALIVE("Alive"), EXPIRED("Expired"), HIDDEN("Hidden"), EDITED("Edited"), DELETED("Deleted");

    private String value;

    private SentimentState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final SentimentState getStatus(String value) {
        if (ALIVE.getValue().equals(value)) {
            return ALIVE;
        }
        if (HIDDEN.getValue().equals(value)) {
            return HIDDEN;
        }
        if (EXPIRED.getValue().equals(value)) {
            return EXPIRED;
        }
        if (NEW.getValue().equals(value)) {
            return NEW;
        }
        if (EDITED.getValue().equals(value)) {
            return EDITED;
        }
        if (DELETED.getValue().equals(value)) {
            return DELETED;
        }
        return null;
    }
}