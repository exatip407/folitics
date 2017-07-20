package com.ohmuk.folitics.enums;

/**
 * @author Abhishek Patel
 *
 */
public enum CategoryType {
    CATEGORY("Category"), SUBCATEGORY("SubCategory"), INDICATOR("Indicator");

    private String value;

    private CategoryType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final CategoryType getStatus(String value) {
        if (CATEGORY.getValue().equals(value)) {
            return CATEGORY;
        }
        if (SUBCATEGORY.getValue().equals(value)) {
            return SUBCATEGORY;
        }
        if (INDICATOR.getValue().equals(value)) {
            return INDICATOR;
        }
        return null;
    }
}
