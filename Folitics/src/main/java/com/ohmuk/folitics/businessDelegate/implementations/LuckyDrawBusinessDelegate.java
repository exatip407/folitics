package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IContestBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.ILuckyDrawBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.enums.Action;
import com.ohmuk.folitics.enums.Module;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.service.ILuckyDrawService;

@Component
public class LuckyDrawBusinessDelegate implements ILuckyDrawBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(LuckyDrawBusinessDelegate.class);

	@Autowired
	ILuckyDrawService luckyDrawService;

	@Autowired
	IContestBusinessDelegate ContestBusinessDelegate;

	@Autowired
	IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	@Override
	public LuckyDraw read(Long id) throws MessageException, Exception {

		logger.info("Inside read method in business delegate");
		LuckyDraw luckyDraw = luckyDrawService.read(id);
		logger.info("Exiting read method from business delegate");
		return luckyDraw;
	}

	@Override
	public List<LuckyDraw> readAll() throws MessageException, Exception {
		logger.info("Inside readAll method in business delegate");
		List<LuckyDraw> luckyDraws = luckyDrawService.readAll();
		logger.info("Exiting readAll method from business delegate");
		return luckyDraws;

	}

	@Override
	public LuckyDraw update(LuckyDraw luckyDraw) throws MessageException,
			Exception {

		logger.info("Inside update method in business delegate");
		LuckyDraw luckyDrawData = luckyDrawService.update(luckyDraw);
		logger.info("Exiting update method from business delegate");
		return luckyDrawData;
	}

	@Override
	public boolean deleteFromDb(Long id) throws MessageException, Exception {

		logger.info("Inside deleteFromDb method in business delegate");
		LuckyDraw luckyDraw = read(id);
		Hibernate.initialize(luckyDraw.getContestParticipants());

		Set<User> users = luckyDraw.getContestParticipants();
		if (users.size() > 0) {
			for (User user : users) {
				
				UserMonetization userMonetization = new UserMonetization();
				userMonetization.setAction(Action.DELETE.getValue());
				userMonetization.setComponentType(luckyDraw.getContestType());
				userMonetization.setModule(Module.CONTEST.getValue());
				userMonetization.setPoints(luckyDraw.getParticipationPoints());
				userMonetization.setUserId(user.getId());
				userMonetization.setActionComponentId(id);

				UserMonetizationBussinessDeligate.addAction(userMonetization);

			}
		}
		boolean success = luckyDrawService.deleteFromDb(id);

		logger.info("Exiting deleteFromDb method from business delegate");
		return success;
	}

	@Override
	public List<LuckyDraw> readAllActiveLuckyDraw() throws MessageException,
			Exception {

		logger.info("Inside readAllActiveContest method in business delegate");
		List<LuckyDraw> activeLuckyDraws = luckyDrawService
				.readAllActiveLuckydraw();
		logger.info("Exiting readAllActiveContest method from business delegate");
		return activeLuckyDraws;
	}

	@Override
	public boolean delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete method in business delegate");
		boolean success = luckyDrawService.delete(id);
		logger.info("Exiting delete method from business delegate");
		return success;
	}

}
