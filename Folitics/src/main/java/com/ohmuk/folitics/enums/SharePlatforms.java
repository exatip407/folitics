package com.ohmuk.folitics.enums;

public enum SharePlatforms {

    FACEBOOK("facebook"), TWITTER("twitter");

    private String value;

    private SharePlatforms(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final SharePlatforms getSharePlatforms(String value) {

        if (FACEBOOK.getValue().equals(value)) {
            return FACEBOOK;
        }
        if (TWITTER.getValue().equals(value)) {
            return TWITTER;
        }
        return null;
    }
}
