package com.ohmuk.folitics.businessDelegate.interfaces;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.attachment.AttachmentFile;

/**
	 * BusinessDeligate interface for {@link AttachmentFile}
	 * 
	 * @author
	 * 
	 */

	public interface IAttachmentFileBusinessDeligate {

		/**
		 * This method is to add attachmentFile
		 * 
		 * @author
		 * @return attachmentFile
		 * @throws Exception
		 */
		public AttachmentFile create(AttachmentFile attachmentFile) throws MessageException;

		/**
		 * This method is to read attachmentFile y passing id
		 * 
		 * @author
		 * @return attachmentFile
		 * @throws Exception
		 */
		public AttachmentFile read(Long id) throws MessageException;

		// Please do not implement this api as this would be too much to load all
		// images
		// public List<AppImage> readAll();

		/**
		 * This method is to update attachmentFile by passing object of
		 * com.ohmuk.folitics.entity.AttachmentFile
		 * 
		 * @author
		 * @return attachmentFile
		 * @throws Exception
		 */
		public AttachmentFile update(AttachmentFile attachmentFile) throws MessageException;

		/**
		 * This method is to delete attachmentFile y passing id
		 * 
		 * @author
		 * @return attachmentFile
		 * @throws Exception
		 */
		public AttachmentFile delete(Long id) throws MessageException;
	}
