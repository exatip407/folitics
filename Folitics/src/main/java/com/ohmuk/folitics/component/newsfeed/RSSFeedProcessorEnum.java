package com.ohmuk.folitics.component.newsfeed;

public enum RSSFeedProcessorEnum {
    ROME("Rome"), FEED4J("Feed4j");
    private String value;

    private RSSFeedProcessorEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final RSSFeedProcessorEnum getRSSFeedProcessor(String val) {
        if (ROME.getValue().equals(val))
            return ROME;
        if (FEED4J.getValue().equals(val))
            return FEED4J;
        return null;
    }

}
