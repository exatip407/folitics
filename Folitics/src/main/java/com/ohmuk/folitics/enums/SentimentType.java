package com.ohmuk.folitics.enums;

/**
 * @author Abhishek Patel
 *
 */
public enum SentimentType {
    TEMPORARY("Temporary"), PERMANENT("Permanent");

    private String value;

    private SentimentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final SentimentType getSentimentType(String value) {
        if (TEMPORARY.getValue().equals(value)) {
            return TEMPORARY;
        }
        if (PERMANENT.getValue().equals(value)) {
            return PERMANENT;
        }
        return null;
    }
}
