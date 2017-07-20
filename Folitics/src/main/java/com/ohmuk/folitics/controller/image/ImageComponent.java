package com.ohmuk.folitics.controller.image;

public enum ImageComponent {

    USER("User"), PROBLEM("Problem"), VIDOPIC("Vidopic"), SENTIMENT("Sentiment"), OPINION("Opinion"), CONNECTION(
            "Connection");

    private String value;

    private ImageComponent(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ImageComponent getImageComponent(String value) {
        if (SENTIMENT.getValue().equals(value)) {
            return SENTIMENT;
        }
        if (OPINION.getValue().equals(value)) {
            return OPINION;
        }
        if (USER.getValue().equals(value)) {
            return USER;
        }
        if (PROBLEM.getValue().equals(value)) {
            return PROBLEM;
        }
        if (VIDOPIC.getValue().equals(value)) {
            return VIDOPIC;
        }
        return null;
    }

}
