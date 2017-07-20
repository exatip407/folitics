package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.businessDelegate.interfaces.IAttachmentBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.attachment.Attachment;
import com.ohmuk.folitics.service.attachment.IAttachmentService;

public class AttachmentBusinessDeligate implements IAttachmentBusinessDeligate {

	final static Logger logger = LoggerFactory.getLogger(AttachmentBusinessDeligate.class);

	@Autowired
	private IAttachmentService attachmentService;

	@Override
	public Attachment create(Attachment attachment) throws MessageException {

		logger.info("Inside create AttachementBusinessDeligate");

		attachment = attachmentService.create(attachment);

		logger.info("Exiting create AttachementBusinessDeligate");

		return attachment;
	}

	@Override
	public Attachment read(Long id) throws MessageException {

		logger.info("Inside read AttachementBusinessDeligate");

		Attachment attachment = attachmentService.read(id);

		logger.info("Exiting read AttachementBusinessDeligate");

		return attachment;
	}

	@Override
	public List<Attachment> readAll() throws MessageException {

		logger.info("Inside readAll AttachementBusinessDeligate");

		List<Attachment> attachment = attachmentService.readAll();

		logger.info("Exiting readAll AttachementBusinessDeligate");

		return attachment;
	}

	@Override
	public Attachment update(Attachment attachment) throws MessageException {

		logger.info("Inside update AttachementBusinessDeligate");

		attachment = attachmentService.update(attachment);

		logger.info("Exiting update AttachementBusinessDeligate");

		return attachment;
	}

	@Override
	public Attachment delete(Long id) throws MessageException {

		logger.info("Inside delete AttachementBusinessDeligate");

		Attachment attachment = attachmentService.delete(id);

		logger.info("Exiting delete AttachementBusinessDeligate");

		return attachment;
	}

}
