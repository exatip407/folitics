package com.ohmuk.folitics.service.attachment;

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
import com.ohmuk.folitics.hibernate.entity.attachment.AttachmentFile;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class AttachmentFileService implements IAttachmentFileService {

	final static Logger logger = LoggerFactory.getLogger(AttachmentFileService.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public AttachmentFile create(AttachmentFile attachmentFile) {

		if (attachmentFile.getTimestamp() == null) {
			attachmentFile.setTimestamp(DateUtils.getSqlTimeStamp());
		}

		logger.info("Inside save AttachmentFile");

		Long id = (Long) getSession().save(attachmentFile);

		logger.info("Existing from AttachmentFile save method");

		return read(id);
	}

	@Override
	public AttachmentFile read(Long id) {

		logger.info("Inside read AttachmentFile");

		Criteria criteria = getSession().createCriteria(AttachmentFile.class);
		criteria.add(Restrictions.eq("id", id));
		AttachmentFile attachmentFile = (AttachmentFile) criteria.uniqueResult();

		logger.info("Existing from AttachmentFile read method");

		return attachmentFile;

	}

	// This api is not allowed as imaged would be too much load all images
	/*
	 * @Override public List<AttachmentFile> readAll() { return
	 * repository.findAll(); }
	 */

	@Override
	public AttachmentFile update(AttachmentFile appImage) throws MessageException {

		logger.info("Inside update AttachmentFile");

		getSession().merge(appImage);

		AttachmentFile attachmentFile = (AttachmentFile) getSession().get(AttachmentFile.class, appImage.getId());

		if (attachmentFile == null) {
			logger.error("No records found for the corresponding id: " + appImage.getId());
			throw (new MessageException("No records found for the corresponding id: " + appImage.getId()));
		}

		getSession().update(appImage);

		attachmentFile = (AttachmentFile) getSession().get(AttachmentFile.class, appImage.getId());

		logger.info("Existing from AttachmentFile update method");

		return attachmentFile;
	}

	@Override
	public AttachmentFile delete(Long id) throws MessageException {

		logger.info("Inside delete AttachmentFile");

		AttachmentFile attachmentFile = (AttachmentFile) getSession().get(AttachmentFile.class, id);

		if (attachmentFile == null) {
			logger.error("No records found for the corresponding id: " + id);
			throw (new MessageException("No records found for the corresponding id: " + id));
		}

		getSession().delete(attachmentFile);
		attachmentFile = (AttachmentFile) getSession().get(AttachmentFile.class, id);

		logger.info("Existing from AttachmentFile delete method");

		return attachmentFile;
	}
}
