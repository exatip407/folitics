package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Category
 * 
 */
@Entity
@Table(name = "chart")
public class Chart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 45)
    private String chartID;

    @Column(nullable = false, length = 45)
    private String chartSecondaryID;

    @OneToMany(mappedBy = "chart", fetch = FetchType.EAGER)
    private List<ChartMetaData> chartMetaData;

    @Column(length = 255)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChartID() {
        return chartID;
    }

    public void setChartID(String chartID) {
        this.chartID = chartID;
    }

    public String getChartSecondaryID() {
        return chartSecondaryID;
    }

    public void setChartSecondaryID(String chartSecondaryID) {
        this.chartSecondaryID = chartSecondaryID;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the chartMetaData
     */
    public List<ChartMetaData> getChartMetaData() {
        return chartMetaData;
    }

    /**
     * @param chartMetaData the chartMetaData to set
     */
    public void setChartMetaData(List<ChartMetaData> chartMetaData) {
        this.chartMetaData = chartMetaData;
    }
    

}
