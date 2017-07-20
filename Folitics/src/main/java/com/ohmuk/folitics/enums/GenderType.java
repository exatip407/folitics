package com.ohmuk.folitics.enums;

public enum GenderType {

    MALE("Male"), FEMALE("Female");

    private String value;

    private GenderType(String value) {

        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final GenderType getGenderType(String value) {

        if (MALE.getValue().equals(value)) {
            return MALE;
        }
        if (FEMALE.getValue().equals(value)) {
            return FEMALE;
        }

        return null;
    }
}
