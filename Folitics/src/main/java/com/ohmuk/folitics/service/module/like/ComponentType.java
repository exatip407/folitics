package com.ohmuk.folitics.service.module.like;


public enum ComponentType {
    OPINION("Opinion"), SENTIMENT("Sentiment"),RESPONSE("Response"),NEWS("News"), TREND("Trend"),TASK("Task");

    private String value;

    private ComponentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final ComponentType getType(String value) {
        if (OPINION.getValue().equals(value)) {
            return OPINION;
        }
        if (SENTIMENT.getValue().equals(value)) {
            return SENTIMENT;
        }
        if (RESPONSE.getValue().equals(value)) {
            return RESPONSE;
        }
        if (NEWS.getValue().equals(value)) {
            return NEWS;
        }
        if (TREND.getValue().equals(value)) {
            return TREND;
        }
        if (TASK.getValue().equals(value)) {
            return TASK;
        }
        return null;
    }

}
