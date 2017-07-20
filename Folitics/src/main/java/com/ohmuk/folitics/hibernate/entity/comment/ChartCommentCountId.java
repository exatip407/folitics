package com.ohmuk.folitics.hibernate.entity.comment;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Chart;

/**
 * Embeddable class to create id for entity: {@link ChartCommentCOunt}
 * 
 * @author Harish
 *
 */
@Embeddable
public class ChartCommentCountId implements Serializable  {

	/**
	 * @return the chart
	 */
	public Chart getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(Chart chart) {
		this.chart = chart;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "chartId", referencedColumnName = "id")
	private Chart chart;

}
