package com.ohmuk.folitics.enums;

public enum ResponseType {
    AGREE("Agree"), DISAGREE("Disagree");

    private String value;

    private ResponseType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ResponseType getResponseFlag(String value) {
        if (AGREE.getValue().equals(value)) {
            return AGREE;
        }
        if (DISAGREE.getValue().equals(value)) {
            return DISAGREE;
        }
        return null;
    }
}
