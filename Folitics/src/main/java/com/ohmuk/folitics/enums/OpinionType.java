package com.ohmuk.folitics.enums;

/**
 * @author Abhishek Patel
 *
 */
public enum OpinionType {
    ANTIGOVT("Anti"), PROGOVT("Pro");

    private String value;

    private OpinionType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final OpinionType getOpinionCategoryValue(String value) {
        if (ANTIGOVT.getValue().equals(value)) {
            return ANTIGOVT;
        }
        if (PROGOVT.getValue().equals(value)) {
            return PROGOVT;
        }
        return null;
    }
}
