package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCountId;

public interface IChartCommentCountRepository extends
		ICommentCountHibernateRepository<ChartCommentCount> {

	public ChartCommentCount find(ChartCommentCountId id);

	public void delete(ChartCommentCountId id);

}
