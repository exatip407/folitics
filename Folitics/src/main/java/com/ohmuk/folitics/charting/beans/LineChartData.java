package com.ohmuk.folitics.charting.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ohmuk.folitics.charting.interfaces.IChartData;

public class LineChartData implements IChartData {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date timestamp;
    double score;
    double scoreCompare;
    int milestone;
    String url;
    String description = "";
    String milestoneType = "";
    private byte[] image;
    

    /**
     * @return the scoreCompare
     */
    public double getScoreCompare() {
        return scoreCompare;
    }

    /**
     * @param scoreCompare the scoreCompare to set
     */
    public void setScoreCompare(double scoreCompare) {
        this.scoreCompare = scoreCompare;
    }

    public String getMilestoneType() {
        return milestoneType;
    }

    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getTimestamp() {

        return dateFormat.format(timestamp);
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getMilestone() {
        return milestone;
    }

    public void setMilestone(int milestone) {
        this.milestone = milestone;
    }

    /**
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

}
