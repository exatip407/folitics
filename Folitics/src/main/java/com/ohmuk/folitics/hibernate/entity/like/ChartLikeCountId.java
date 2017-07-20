package com.ohmuk.folitics.hibernate.entity.like;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ohmuk.folitics.hibernate.entity.Chart;

/**
 * Embeddable class to create id for entity: {@link ChartLikeCount}
 * 
 * @author Harish
 *
 */
@Embeddable
public class ChartLikeCountId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "chartId", referencedColumnName = "id")
	private Chart chart;

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

}
