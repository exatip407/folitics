package com.ohmuk.folitics.service.module.like;


public enum ModuleType {
    LIKE("Like"), SHARE("Share"),FOLLOW("share");

    private String value;

    private ModuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ModuleType getType(String value) {
        if (LIKE.getValue().equals(value)) {
            return LIKE;
        }
        if (SHARE.getValue().equals(value)) {
            return SHARE;
        }
        if (FOLLOW.getValue().equals(value)) {
            return FOLLOW;
        }
        return null;
    }

}
