package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.attachment.Attachment;

public interface IAttachmentBusinessDeligate {

	/**
	 * This method is to add Attachment
	 * 
	 * @author
	 * @return Attachment
	 * @throws Exception
	 */
	public Attachment create(Attachment Attachment) throws MessageException;

	/**
	 * This method is to read Attachment by passing id
	 * 
	 * @author
	 * @return Attachment
	 * @throws Exception
	 */
	public Attachment read(Long id) throws MessageException;

	/**
	 * This method is to readAll Attachments
	 * 
	 * @author
	 * @return Attachment
	 * @throws Exception
	 */
	public List<Attachment> readAll() throws MessageException;

	/**
	 * This method is to update Attachment by passing object of
	 * com.ohmuk.folitics.entity.Attachment
	 * 
	 * @author
	 * @return Attachment
	 * @throws Exception
	 */
	public Attachment update(Attachment Attachment) throws MessageException;

	/**
	 * This method is to delete Attachment by passing id
	 * 
	 * @author
	 * @return Attachment
	 * @throws Exception
	 */
	public Attachment delete(Long id) throws MessageException;

}
