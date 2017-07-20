package com.ohmuk.folitics.enums;

public enum ReligionType {

    CHRISTIANITY("Christianity"), ISLAM("Islam"), HINDUISM("Hinduism"), BUDDHISM("Buddhism"), SIKHISM("Sikhism"), JAINISM(
            "Jainism");

    private String value;

    private ReligionType(String value) {

        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ReligionType getReligionType(String value) {

        if (CHRISTIANITY.getValue().equals(value)) {
            return CHRISTIANITY;
        }
        if (ISLAM.getValue().equals(value)) {
            return ISLAM;
        }
        if (HINDUISM.getValue().equals(value)) {
            return HINDUISM;
        }
        if (BUDDHISM.getValue().equals(value)) {
            return BUDDHISM;
        }
        if (SIKHISM.getValue().equals(value)) {
            return SIKHISM;
        }
        if (JAINISM.getValue().equals(value)) {
            return JAINISM;
        }

        return null;
    }
}
