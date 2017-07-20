package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.businessDelegate.interfaces.IFactBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.enums.FactType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Fact;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.notification.Notification;
import com.ohmuk.folitics.notification.NotificationMapping;
import com.ohmuk.folitics.service.IFactService;

@Component
@Transactional
public class FactBusinessDelegate implements IFactBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(FactBusinessDelegate.class);

	@Autowired
	private volatile IFactService factService;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	@Override
	public Fact create(Fact fact) throws Exception {
		logger.info("Inside create method in business delegate");
		Fact factData = factService.create(fact);
		logger.info("Exiting read method from business delegate");
		return factData;
	}

	@Override
	public Fact read(Long id) throws Exception {
		logger.info("Inside read method in business delegate");
		Fact factData = factService.read(id);
		logger.info("Exiting read method from business delegate");
		return factData;
	}

	@Override
	public List<Fact> readAll() throws Exception {
		logger.info("Inside readAll method in business delegate");
		List<Fact> facts = factService.readAll();
		logger.info("Exiting readAll method from business delegate");
		return facts;
	}

	@Override
	public Fact update(Fact fact) throws Exception {
		logger.info("Inside update method in business delegate");
		Fact factData = factService.update(fact);
		logger.info("Exiting update method from business delegate");
		return factData;
	}

	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside deleteFromDb method in business delegate");
		boolean success = factService.deleteFromDB(id);
		logger.info("Exiting deleteFromDb method from business delegate");
		return success;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean success = factService.delete(id);
		logger.info("Exiting delete method from business delegate");
		return success;
	}

	@Override
	public List<Fact> readAll(Long userId) throws Exception {

		logger.info("Inside readAll fact by userId method in business delegate");
		List<Fact> facts = factService.readAll(userId);
		logger.info("Exiting readAll fact by userId method in business delegate");
		return facts;
	}

	@Override
	public List<Fact> readAll(String status) throws Exception {
		logger.info("Inside readAll fact by status method in business delegate");
		List<Fact> facts = factService.readAll(status);
		logger.info("Exiting readAll fact by status method in business delegate");
		return facts;
	}

	@Override
	public List<Fact> readAll(Long parentId, String parentType)
			throws Exception {
		logger.info("Inside readAll fact by parentId and parentType method in business delegate");
		List<Fact> facts = factService.readAll(parentId, parentType);
		logger.info("Exiting readAll fact by parentId and parentType method in business delegate");
		return facts;
	}

	@Override
	public Fact approveFact(Long id) throws MessageException, Exception {

		logger.info("Inside approveFact method in business delegate");

		if (id == null) {
			logger.error("Fact id found to be null, can not Approved Fact");
			throw (new MessageException(
					"Fact id found to be null, can not Approved Fact"));
		}
		Fact fact=read(id);
		fact.setStatus(FactType.ACCEPTED.getValue());

		logger.debug("Change fact status by Accepted");

		Fact approvedFact = factService.approveFact(fact);

		User user = fact.getUser();
		UserMonetization userMonetization = new UserMonetization();
		userMonetization.setAction("Submit");
		userMonetization.setComponentType("Submit Fact");
		userMonetization.setModule("GA");
		userMonetization.setUserId(user.getId());
		userMonetization.setActionComponentId(fact.getId());

		UserMonetizationBussinessDeligate.addAction(userMonetization);
		
		NotificationMapping notificationMapping=new NotificationMapping();
		/*notificationMapping.setComponentType(componentType);*/
		

		logger.info("Exiting approveFact method from business delegate");
		return approvedFact;
	}

	@Override
	public Fact rejectFact(Long factId) throws MessageException, Exception {

		logger.info("Inside rejectFact method in business delegate");

		if (factId == null) {
			logger.error("Id  found to be null, can not rejected Fact corrosponding to these id"
					+ factId);
			throw (new MessageException(
					"Id found to be null, can not rejected Fact corrosponding to these id :"
							+ factId));
		}

		Fact fact = factService.read(factId);
		fact.setStatus(FactType.REJECTED.getValue());

		logger.debug("Change fact status by Rejected");

		Fact rejectedFact = factService.rejectFact(fact);

		logger.info("Exiting rejectFact method from business delegate");
		return rejectedFact;
	}

	@Override
	public List<Fact> paginationApi(int start)
			throws MessageException, Exception {

		logger.info("Inside paginationApi method in business delegate");

		List<Fact> facts = factService.paginationApi(start);

		logger.info("Exiting paginationApi method from business delegate");

		return facts;

	}

}
