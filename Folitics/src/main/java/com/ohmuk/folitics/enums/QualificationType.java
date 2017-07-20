package com.ohmuk.folitics.enums;

public enum QualificationType {

    MATRICULATION("Matriculation"), HIGHER_SECONDARY("Higher Secondary"), GRADUATE("Graduate"), POST_GRADUATE(
            "Post Graduate"), DOCTORATE("Doctorate"), ILLITERATE("Illiterate");

    private String value;

    private QualificationType(String value) {

        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final QualificationType getQualificationType(String value) {

        if (MATRICULATION.getValue().equals(value)) {
            return MATRICULATION;
        }
        if (HIGHER_SECONDARY.getValue().equals(value)) {
            return HIGHER_SECONDARY;
        }
        if (GRADUATE.getValue().equals(value)) {
            return GRADUATE;
        }
        if (POST_GRADUATE.getValue().equals(value)) {
            return POST_GRADUATE;
        }
        if (DOCTORATE.getValue().equals(value)) {
            return DOCTORATE;
        }
        if (ILLITERATE.getValue().equals(value)) {
            return ILLITERATE;
        }

        return null;
    }
}
