package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCountId;

/**
 * Interface for entity: {@link GovtSchemeDataCommentCount} repository
 * 
 * @author Harish
 *
 */
public interface IGovtSchemeDataCommentCountRepository extends
		ICommentCountHibernateRepository<GovtSchemeDataCommentCount> {

	public GovtSchemeDataCommentCount find(GovtSchemeDataCommentCountId id);

	public void delete(GovtSchemeDataCommentCountId id);

}
