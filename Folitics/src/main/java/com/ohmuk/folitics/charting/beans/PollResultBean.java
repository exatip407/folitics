package com.ohmuk.folitics.charting.beans;

import java.io.Serializable;

public class PollResultBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Double value;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }
    
}
