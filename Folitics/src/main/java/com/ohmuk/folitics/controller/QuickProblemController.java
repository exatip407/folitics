package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ohmuk.folitics.businessDelegate.interfaces.IQuickProblemBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickPersonality;
import com.ohmuk.folitics.hibernate.entity.quickproblem.QuickProblem;

@RestController
@RequestMapping("/quickProblem")
public class QuickProblemController {

	@Autowired
	private volatile IQuickProblemBusinessDelegate delegate;

	private static final Logger logger = LoggerFactory
			.getLogger(QuickProblemController.class);

	@RequestMapping(value = "/addQuickProblem", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> addNewQuickProblem(
			@Validated @RequestPart(value = "quickProblem") QuickProblem quickProblem,
			@RequestPart(value = "file") MultipartFile image) {

		try {

			if (quickProblem == null) {
				logger.error("No parameter Passed");
				throw (new MessageException("No parameter Passed"));
			}

			if (image == null) {
				logger.info("Image not Available");
			}

			// Adding image to quickProblem
			quickProblem.setImage(image.getBytes());
			quickProblem.setImageType(FileType.getFileType(
					image.getOriginalFilename().split("\\.")[1]).getValue());
			quickProblem = delegate.addQuickProblem(quickProblem);

			if (quickProblem == null) {
				logger.error("Unable to add QuickProblem");
				throw (new MessageException("Unable to add QuickProblem"));
			}

		} catch (MessageException e) {

			logger.error("CustomException while adding the quickProblem " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Exception while adding the quickProblem " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		logger.debug("QuickProblem saved " + quickProblem);
		return new ResponseDto<Object>(true, quickProblem,
				"QuickProblem Successfully Created");
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAll() {

		List<QuickProblem> quickProblems;
		try {
			quickProblems = delegate.getAllQuickProblems();
		} catch (Exception e) {
			logger.error("CustomException while fetching the quickProblems "
					+ e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, quickProblems,
				"Sucessfully Fetched list of all quickProblems");
	}

	@RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<QuickProblem> deleteFromDb(Long id) {
		logger.info(" Inside deleteFromDb method in controller");
		try {

			boolean success = delegate.deleteFromDB(id);

			if (success == true) {

				logger.debug("QuickProblem deleted succesfully for these id:-"
						+ id);
				logger.info(" Exiting deleteFromDb method in controller");
				return new ResponseDto<QuickProblem>(true);
			} else {
				logger.error("something went wrong while  deleting QuickProblem for these id:-"
						+ id);
				return new ResponseDto<QuickProblem>(false);
			}

		} catch (MessageException exception) {
			logger.error(
					"CustomException while  deleting QuickProblem with id",
					exception);
			return new ResponseDto<QuickProblem>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting QuickProblem with id",
					exception);
			return new ResponseDto<QuickProblem>(exception.getMessage(), false);
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<QuickProblem> delete(Long id) {
		try {
			logger.info(" Inside delete method in quickProblem controller");

			boolean success = delegate.delete(id);

			if (success == true) {

				logger.debug("QuickProblem deleted succesfully for these id:-"
						+ id);
				logger.info(" Exiting delete method from quickProblem controller");
				return new ResponseDto<QuickProblem>(true);
			} else {
				logger.error("something went wrong while  deleting QuickProblem for these id:-"
						+ id);
				return new ResponseDto<QuickProblem>(false);
			}

		} catch (MessageException exception) {
			logger.error(
					"CustomException while  deleting QuickProblem with id",
					exception);
			return new ResponseDto<QuickProblem>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting QuickProblem with id",
					exception);
			return new ResponseDto<QuickProblem>(exception.getMessage(), false);
		}

	}

	@RequestMapping(value = "/editQuickProblem", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> editQuickProblem(
			@RequestBody QuickProblem quickProblem) {

		try {

			if (quickProblem == null) {

				logger.error("No parameter Passed");
				throw (new MessageException("No parameter Passed"));
			}

			quickProblem = delegate.updateQuickProblem(quickProblem);

			if (quickProblem == null) {

				logger.error("Unable to edit QuickProblem");
				throw (new MessageException("Unable to edit QuickProblem"));
			}

		} catch (MessageException e) {

			logger.error("CustomException while editing the quickProblem " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Exception while editing the quickProblem " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		return new ResponseDto<Object>(true, quickProblem,
				"QuickProblem Successfully Updated");

	}

	@RequestMapping(value = "/getAllQuickProblemsByUserId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<QuickProblem> getAllQuickProblems(
			Long userId) {

		logger.info("Inside getAll quickProblems by userId  method in quickProblem controller");

		try {

			List<QuickProblem> quickProblems = delegate.readAll(userId);

			logger.info("Exiting getAll quickProblems by userId  method in quickProblem controller");

			return new ResponseDto<QuickProblem>(true, quickProblems,
					"succesfully fetched QuickProblems ");

		} catch (MessageException exception) {

			logger.error("CustomException while fetching all QuickProblems",
					exception);

			return new ResponseDto<QuickProblem>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fetching QuickProblems", exception);

			return new ResponseDto<QuickProblem>(exception.getMessage(), false);
		}

	}

	@RequestMapping(value = "/getAllQuickPersonality", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllQuickPersonality() {
		List<QuickPersonality> quickpersonality = null;
		try {
			quickpersonality = delegate.getAllQuickPersonality();
			return new ResponseDto<Object>(true, quickpersonality,
					"Sucessfully Fetched list of all quickpersonalities");
		} catch (Exception exception) {
			logger.error("Exception while fetching all quickpersonalities "
					+ exception);
			return new ResponseDto<Object>(false, exception.getMessage());
		}

	}

}
