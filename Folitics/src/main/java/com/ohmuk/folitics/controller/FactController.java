package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ohmuk.folitics.businessDelegate.interfaces.IFactBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Fact;
import com.ohmuk.folitics.util.DateUtils;

@Controller
@RequestMapping("/fact")
public class FactController {
	private static Logger logger = LoggerFactory
			.getLogger(FactController.class);

	@Autowired
	private IFactBusinessDelegate businessDelegate;

	/**
	 * This method is used to add Fact. This is a multipart spring web service
	 * that requires attachment and json for fact.
	 * 
	 * @param attachment
	 * @param fact
	 * 
	 * @return: ResponseDto<com.ohmuk.folitics.hibernate.entity.Fact>
	 */

	@RequestMapping(value = "/submitFact", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Fact> submitFact(
			@RequestPart(value = "fact") Fact fact,
			@RequestPart(value = "file") MultipartFile attachment) {

		logger.info("Inside submitFact Fact method in controller");

		try {
			// Adding attachment to fact
			fact.setAttachment(attachment.getBytes());

//			String s = attachment.getOriginalFilename();
//
//			String s2 = attachment.getOriginalFilename().split("\\.")[1];

//			String test = FileType
//					.getFileType(attachment.getOriginalFilename()).getValue();
//
//			FileType f = FileType.getFileType(attachment.getOriginalFilename());
			
			fact.setAttachmentType(FileType.getFileType(
					attachment.getOriginalFilename().split("\\.")[1])
					.getValue());

			fact.setEditTime(DateUtils.getSqlTimeStamp());
			fact.setCreationTime(DateUtils.getSqlTimeStamp());
			fact = businessDelegate.create(fact);

			if (fact != null) {

				logger.info("Exiting submitFact method from controller");

				return new ResponseDto<Fact>(true, fact,
						"succesfully added fact");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while adding fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while adding fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		return new ResponseDto<Fact>("Fact not added", false);
	}

	/**
	 * This method is used to display all Facts.
	 * 
	 * @return List of {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/getAllFacts", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Fact> getAllFacts() {

		logger.info("Inside getAll  method in fact controller");

		try {

			List<Fact> facts = businessDelegate.readAll();

			logger.info("Exiting getAll method from Fact controller");

			return new ResponseDto<Fact>(true, facts,
					"succesfully fethed Facts ");

		} catch (MessageException exception) {

			logger.error("CustomException while fething all Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

	/**
	 * This Web service is used to update
	 * com.ohmuk.folitics.hibernate.entity.Fact.
	 * 
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.Fact
	 * @return : updated {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/editFact", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Fact> edit(
			@Validated @RequestBody Fact fact) {

		logger.info("Inside edit method in Fact controller");

		try {
			fact.setEditTime(DateUtils.getSqlTimeStamp());
			fact = businessDelegate.update(fact);

			if (fact != null) {

				logger.info("Exiting update method in Fact controller");

				return new ResponseDto<Fact>(true, fact,
						"succesfully updated Fact");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while updating Fact ", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		} catch (Exception exception) {

			logger.error("Exception while updating Fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}
		return new ResponseDto<Fact>("Fact not updated", false);
	}

	/**
	 * This Web service is used get com.ohmuk.folitics.hibernate.entity.Fact by
	 * id.
	 * 
	 * @param: com.ohmuk.folitics.hibernate.entity.Fact id
	 * @return : corresponding com.ohmuk.folitics.hibernate.entity.Fact
	 */

	@RequestMapping(value = "/getFact", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Fact> getFact(Long id) {

		logger.info("Inside getFact method in Fact controller");
		Fact fact;
		try {
			fact = businessDelegate.read(id);

			if (fact != null) {

				logger.debug("Fact found succesfully for these id:-" + id);
				logger.info("Exiting getFact method in Fact controller");

				return new ResponseDto<Fact>(true, fact,
						"Fact succesfully found");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while finding Fact by id", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);

		} catch (Exception exception) {

			logger.error("Exception while finding Fact by id", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		return new ResponseDto<Fact>("Fact not found", false);
	}

	/**
	 * This method is used to permanent delete
	 * com.ohmuk.folitics.hibernate.entity.Fact by id.
	 * 
	 * @param {com.ohmuk.folitics.hibernate.entity.Fact} id
	 * @return: true if successfully deleted otherwise return false.
	 */
	@RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Fact> deleteFromDb(Long id) {
		logger.info(" Inside deleteFromDb method in controller");
		try {

			boolean success = businessDelegate.deleteFromDB(id);

			if (success == true) {

				logger.debug("Fact deleted succesfully for these id:-" + id);
				logger.info(" Exiting deleteFromDb method in controller");
				return new ResponseDto<Fact>(true);
			} else {
				logger.error("something went wrong while  deleting Fact for these id:-"
						+ id);
				return new ResponseDto<Fact>(false);
			}

		} catch (MessageException exception) {
			logger.error("CustomException while  deleting Fact with id",
					exception);
			return new ResponseDto<Fact>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting Fact with id", exception);
			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to Soft delete
	 * com.ohmuk.folitics.hibernate.entity.Fact by id.
	 * 
	 * @param {com.ohmuk.folitics.hibernate.entity.Fact} id
	 * @return true if successfully deleted otherwise return false.
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Fact> delete(Long id) {
		try {
			logger.info(" Inside delete method in fact controller");

			boolean success = businessDelegate.delete(id);

			if (success == true) {

				logger.debug("Fact deleted succesfully for these id:-" + id);
				logger.info(" Exiting delete method from fact controller");
				return new ResponseDto<Fact>(true);
			} else {
				logger.error("something went wrong while  deleting Fact for these id:-"
						+ id);
				return new ResponseDto<Fact>(false);
			}

		} catch (MessageException exception) {
			logger.error("CustomException while  deleting Fact with id",
					exception);
			return new ResponseDto<Fact>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting Fact with id", exception);
			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to display all Facts corresponding to particular
	 * userId.
	 * 
	 * @param userId
	 * @return List of {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/getAllFactsByUserId", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Fact> getAllFacts(Long userId) {

		logger.info("Inside getAll facts by userId  method in fact controller");

		try {

			List<Fact> facts = businessDelegate.readAll(userId);

			logger.info("Exiting getAll facts by userId  method in fact controller");

			return new ResponseDto<Fact>(true, facts,
					"succesfully fethed Facts ");

		} catch (MessageException exception) {

			logger.error("CustomException while fething all Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to display all Facts corresponding to status
	 * 
	 * @param: String status
	 * @return: List of {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/getAllFactsByStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Fact> getAllFacts(String status) {

		logger.info("Inside getAll facts by status  method in fact controller");

		try {
			List<Fact> facts = businessDelegate.readAll(status);

			logger.info("Exiting getAll facts by userId  method in fact controller");

			return new ResponseDto<Fact>(true, facts,
					"succesfully fethed Facts ");

		} catch (MessageException exception) {

			logger.error("CustomException while fething all  facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething  Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to display all Facts corresponding to status
	 * 
	 * @param: String status
	 * @return: List of {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/getAllFactsByParentIdAndParentType", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Fact> getAllFacts(Long parentId,
			String parentType) {

		logger.info("Inside getAll facts by status  method in fact controller");

		try {

			List<Fact> facts = businessDelegate.readAll(parentId, parentType);

			logger.info("Exiting getAll facts by userId  method in fact controller");

			return new ResponseDto<Fact>(true, facts,
					"succesfully fethed Facts ");

		} catch (MessageException exception) {

			logger.error(
					"CustomException while fething all facts by parentId and parentType",
					exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error(
					"Exception while fething all facts by parentId and parentType",
					exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

	/**
	 * This Web service is used to approve Fac
	 * 
	 * @param: Long {com.ohmuk.folitics.hibernate.entity.Fact}id
	 * @return : approved {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/approveFact", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Fact> approveFact(
			Long id) {

		logger.info("Inside approvedFact method in Fact controller");
		Fact approvedfact;
		try {
			approvedfact = businessDelegate.approveFact(id);

			if (approvedfact != null) {

				logger.info("Exiting approvedFact method from  Fact controller");

				return new ResponseDto<Fact>(true, approvedfact,
						"Fact succesfully approved");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while approving Fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);

		} catch (Exception exception) {

			logger.error("Exception while approving Fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		return new ResponseDto<Fact>("Fact not approved", false);
	}

	/**
	 * This Web service is used to reject Fact
	 * 
	 * @param: Long {com.ohmuk.folitics.hibernate.entity.Fact} id
	 * @return : approved {com.ohmuk.folitics.hibernate.entity.Fact}
	 */

	@RequestMapping(value = "/rejectFact", method =  RequestMethod.POST)
	public @ResponseBody ResponseDto<Fact> rejectFact(Long factId) {

		logger.info("Inside rejectedFact method in Fact controller");
		Fact rejectedFact;
		try {
			rejectedFact = businessDelegate.rejectFact(factId);

			if (rejectedFact != null) {

				logger.info("Exiting rejectedFact method from  Fact controller");

				return new ResponseDto<Fact>(true, rejectedFact,
						"Fact succesfully rejected");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while rejecting Fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);

		} catch (Exception exception) {

			logger.error("Exception while rejectedFact Fact", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		return new ResponseDto<Fact>("Fact not rejected", false);
	}

	/**
	 * web service is to get List of ten
	 * {com.ohmuk.folitics.hibernate.entity.Fact} starting from startId
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Fact
	 * @param: startId
	 * 
	 * @throws throws MessageException, Exception
	 * 
	 */

	@RequestMapping(value = "/paginationApi", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<Fact> paginationApi(int start) {

		logger.info("Inside paginationApi  method in fact controller");

		try {

			List<Fact> facts = businessDelegate.paginationApi(start);

			logger.info("Exiting paginationApi method from Fact controller");

			return new ResponseDto<Fact>(true, facts,
					"succesfully fethed Facts ");

		} catch (MessageException exception) {

			logger.error("CustomException while fething  Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething Facts", exception);

			return new ResponseDto<Fact>(exception.getMessage(), false);
		}

	}

}
