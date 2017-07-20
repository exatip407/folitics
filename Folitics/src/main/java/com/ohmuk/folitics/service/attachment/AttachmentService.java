package com.ohmuk.folitics.service.attachment;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.attachment.Attachment;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class AttachmentService implements IAttachmentService {
	
final static Logger logger = LoggerFactory.getLogger(AttachmentService.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

    @Override
    public Attachment create(Attachment attachment) {
        if (attachment.getTimestamp() == null) {
            attachment.setTimestamp(DateUtils.getSqlTimeStamp());
        }
        
        logger.info("Inside create Attachment");

		Long id = (Long) getSession().save(attachment);

		logger.info("Existing from Attachment create method");

		return read(id);

    }

    @Override
    public Attachment read(Long id) {
    	
    	logger.info("Inside read Attachment");

		Criteria criteria = getSession().createCriteria(Attachment.class);
		criteria.add(Restrictions.eq("id", id));
		Attachment attachment = (Attachment) criteria.uniqueResult();

		logger.info("Existing from Attachment read method");

		return attachment;

    }

    @Override
    public List<Attachment> readAll() {
    	
    	logger.info("Inside readAll Attachment");
    	
		List<Attachment> attachments = getSession().createCriteria(Attachment.class).list();

		logger.info("Existing from Attachment readAll method");

		return attachments;

    }

    @Override
    public Attachment update(Attachment attachment) throws MessageException {
    	
    	logger.info("Inside update Attachment");

		getSession().merge(attachment);

		Attachment OriginalAttachment = (Attachment) getSession().get(Attachment.class, attachment.getId());

		if (OriginalAttachment == null) {
			logger.error("No records found for the corresponding id: " + attachment.getId());
			throw (new MessageException("No records found for the corresponding id: " + attachment.getId()));
		}

		getSession().update(attachment);

		attachment = (Attachment) getSession().get(Attachment.class, attachment.getId());

		logger.info("Existing from Attachment update method");

		return attachment;
    }

    @Override
    public Attachment delete(Long id) throws MessageException {
    	
    	logger.info("Inside delete Attachment");

    	Attachment attachment = (Attachment) getSession().get(Attachment.class, id);

		if (attachment == null) {
			logger.error("No records found for the corresponding id: " + id);
			throw (new MessageException("No records found for the corresponding id: " + id));
		}

		getSession().delete(attachment);
		attachment = (Attachment) getSession().get(Attachment.class, id);

		logger.info("Existing from AttachmentFile delete method");

		return attachment;
    }
}
