package com.ohmuk.folitics.enums;

public enum RegionType {

    NORTH("North"), SOUTH("South"), EAST("East"), WEST("West");

    private String value;

    private RegionType(String value) {

        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final RegionType getRegionType(String value) {

        if (NORTH.getValue().equals(value)) {
            return NORTH;
        }
        if (SOUTH.getValue().equals(value)) {
            return SOUTH;
        }
        if (EAST.getValue().equals(value)) {
            return EAST;
        }
        if (WEST.getValue().equals(value)) {
            return WEST;
        }

        return null;
    }
}
