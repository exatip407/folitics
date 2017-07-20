package com.ohmuk.folitics.businessDelegate.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.businessDelegate.interfaces.IAttachmentFileBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.attachment.AttachmentFile;
import com.ohmuk.folitics.service.attachment.IAttachmentFileService;

public class AttachementFileBusinessDeligate implements IAttachmentFileBusinessDeligate {

	final static Logger logger = LoggerFactory.getLogger(AttachementFileBusinessDeligate.class);

	@Autowired
	private IAttachmentFileService attachmentFileService;

	@Override
	public AttachmentFile create(AttachmentFile attachmentFile) throws MessageException {

		logger.info("Inside create AttachementFileBusinessDeligate");

		attachmentFile = attachmentFileService.create(attachmentFile);

		logger.info("Exiting create AttachementFileBusinessDeligate");

		return attachmentFile;
	}

	@Override
	public AttachmentFile read(Long id) throws MessageException {

		logger.info("Inside read AttachementFileBusinessDeligate");

		AttachmentFile attachmentFile = attachmentFileService.read(id);

		logger.info("Exiting read AttachementFileBusinessDeligate");

		return attachmentFile;
	}

	@Override
	public AttachmentFile update(AttachmentFile attachmentFile) throws MessageException {

		logger.info("Inside update AttachementFileBusinessDeligate");

		attachmentFile = attachmentFileService.update(attachmentFile);

		logger.info("Exiting update AttachementFileBusinessDeligate");

		return attachmentFile;
	}

	@Override
	public AttachmentFile delete(Long id) throws MessageException {

		logger.info("Inside delete AttachementFileBusinessDeligate");

		AttachmentFile attachmentFile = attachmentFileService.delete(id);

		logger.info("Exiting delete AttachementFileBusinessDeligate");

		return attachmentFile;
	}

}
