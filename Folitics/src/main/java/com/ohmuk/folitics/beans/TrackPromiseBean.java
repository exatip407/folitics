package com.ohmuk.folitics.beans;

import java.io.Serializable;

public class TrackPromiseBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String category;
    private String subCategory;
    private double totalScore;
    private String direction;
    private Long subCategoryID;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Long getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(Long long1) {
        this.subCategoryID = long1;
    }

}
