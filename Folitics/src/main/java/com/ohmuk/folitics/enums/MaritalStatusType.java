package com.ohmuk.folitics.enums;

public enum MaritalStatusType {

    SINGLE("Single"), MARRIED("Married"), SEPARATED("Separated"), DIVORCED("Divorced"), WIDOWED("Widowed");

    private String value;

    private MaritalStatusType(String value) {

        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final MaritalStatusType getMaritalStatusType(String value) {

        if (SINGLE.getValue().equals(value)) {
            return SINGLE;
        }
        if (MARRIED.getValue().equals(value)) {
            return MARRIED;
        }
        if (SEPARATED.getValue().equals(value)) {
            return SEPARATED;
        }
        if (DIVORCED.getValue().equals(value)) {
            return DIVORCED;
        }
        if (WIDOWED.getValue().equals(value)) {
            return WIDOWED;
        }

        return null;
    }
}
