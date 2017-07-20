package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IGlobalVerdictBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IGlobalVerdictDistributionBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IOpinionBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictDistributionBusinessDelegate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.enums.OpinionType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;
import com.ohmuk.folitics.notification.InterfaceNotificationService;
import com.ohmuk.folitics.notification.NotificationMapping;
import com.ohmuk.folitics.service.IOpinionService;
import com.ohmuk.folitics.service.ISentimentOpinionStatService;

/**
 * @author Abhishek
 *
 */

@Component
public class OpinionBusinessDelegate implements IOpinionBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionBusinessDelegate.class);

	@Autowired
	private volatile IOpinionService service;
	@Autowired
	private volatile ISentimentOpinionStatService sentimentOpinionStatService;

	@Autowired
	private volatile IVerdictDistributionBusinessDelegate verdictDistributionBusinessDelegate;

	@Autowired
	private volatile IGlobalVerdictBusinessDelegate globalVerdictBusinessDelegate;

	@Autowired
	private volatile IGlobalVerdictDistributionBusinessDelegate globalVerdictDistributionBusinessDelegate;
	
	@Autowired
	private volatile InterfaceNotificationService notificationService;

	@Override
	public Opinion create(Opinion opinion) throws Exception {
		logger.info("Inside create method in business delegate");
		if (opinion.getLink() != null) {
			if (opinion.getLink().getSentiment() == null) {
				Sentiment sentiment = new Sentiment();
				sentiment = opinion.getSentiment();
				opinion.getLink().setSentiment(sentiment);
			} else {
				opinion.getLink().setSentiment(opinion.getSentiment());
			}
		}
		opinion = service.create(opinion);
		
		//call for notification		
		if(opinion!=null){
			NotificationMapping notificationMapping=new NotificationMapping();
			notificationMapping.setUserId(opinion.getUser().getId());
			notificationMapping.setComponentId(opinion.getId());
			notificationMapping.setComponentType("opinion");
			
			notificationService.opinionNotification(notificationMapping,opinion.getSentiment().getId());
		}
		
		
		
		if (opinion != null) {
			SentimentOpinionStat sentimentOpinionStat = sentimentOpinionStatService
					.read(opinion.getSentiment().getId());
			if (sentimentOpinionStat == null) {
				sentimentOpinionStat = new SentimentOpinionStat();
				sentimentOpinionStat.setId(opinion.getSentiment().getId());

				long favorPoints = 0, againstPoints = 0;

				if (opinion.getType().equals(OpinionType.PROGOVT.getValue())) {
					favorPoints = Constants.OPINION_WEIGHT;
				} else if (opinion.getType().equals(
						OpinionType.ANTIGOVT.getValue())) {
					againstPoints = Constants.OPINION_WEIGHT;
				}

				sentimentOpinionStat.setFavorPoints(favorPoints);
				sentimentOpinionStat.setAgainstPoints(againstPoints);
				sentimentOpinionStatService.create(sentimentOpinionStat);
			} else {
				long favorPoints = 0, againstPoints = 0;

				favorPoints = sentimentOpinionStat.getFavorPoints();
				againstPoints = sentimentOpinionStat.getAgainstPoints();

				if (opinion.getType().equals(OpinionType.PROGOVT.getValue())) {
					favorPoints = favorPoints + Constants.OPINION_WEIGHT;
				} else if (opinion.getType().equals(
						OpinionType.ANTIGOVT.getValue())) {
					againstPoints = againstPoints + Constants.OPINION_WEIGHT;
				}

				sentimentOpinionStatService.update(sentimentOpinionStat);
			}

			verdictDistributionBusinessDelegate.create(opinion.getSentiment()
					.getId(), opinion.getType(), opinion.getUser());

			GlobalVerdict globalVerdict = globalVerdictBusinessDelegate.read();

			if (globalVerdict != null) {

				if (opinion.getType().equals(OpinionType.PROGOVT.getValue())) {

					globalVerdict.setProCount(globalVerdict.getProCount()
							+ Constants.OPINION_WEIGHT);

				} else if (opinion.getType().equals(
						OpinionType.ANTIGOVT.getValue())) {

					globalVerdict.setAntiCount(globalVerdict.getAntiCount()
							+ Constants.OPINION_WEIGHT);
				}

				globalVerdictBusinessDelegate.update(globalVerdict);

				globalVerdictDistributionBusinessDelegate
						.aggregateGlobalVerdictDistribution(opinion.getType(),
								opinion.getUser());
			} else {

				globalVerdict = new GlobalVerdict();

				if (opinion.getType().equals(OpinionType.PROGOVT.getValue())) {

					globalVerdict.setProCount(Constants.OPINION_WEIGHT);
					globalVerdict.setAntiCount(0l);

				} else if (opinion.getType().equals(
						OpinionType.ANTIGOVT.getValue())) {

					globalVerdict.setAntiCount(Constants.OPINION_WEIGHT);
					globalVerdict.setProCount(0l);
				}

				globalVerdictBusinessDelegate.create(globalVerdict);

				globalVerdictDistributionBusinessDelegate
						.aggregateGlobalVerdictDistribution(opinion.getType(),
								opinion.getUser());
			}
		}
		logger.info("Exiting create method in business delegate");
		
		return opinion;
	}

	@Override
	public Opinion read(Long id) throws Exception {
		logger.info("Inside read method in business delegate");
		Opinion opinion = service.read(id);
		logger.info("Exiting read method in business delegate");
		return opinion;
	}

	@Override
	public List<Opinion> readAll() throws Exception {

		logger.info("Inside readAll method in business delegate");
		List<Opinion> opinions = service.readAll();
		logger.info("Exiting readAll method in business delegate");
		return opinions;
	}

	@Override
	public Opinion update(Opinion opinion) throws Exception {
		logger.info("Inside update method in business delegate");
		Opinion opinionData = service.update(opinion);
		logger.info("Exiting update method in business delegate");
		return opinionData;
	}

	@Override
	public Opinion delete(Long id) throws Exception {
		logger.info("Inside delete method in business delegate");
		Opinion opinion = service.read(id);
		opinion.setState(ComponentState.DELETED.getValue());
		Opinion opinionData = service.update(opinion);
		return opinionData;
	}

	@Override
	public Opinion delete(Opinion opinion) throws Exception {
		logger.info("Inside delete method in business delegate");
		opinion.setState(ComponentState.DELETED.getValue());
		Opinion opinionData = service.update(opinion);
		logger.info("Exiting delete method in business delegate");
		return opinionData;
	}

	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		boolean sucess = service.deleteFromDBById(id);
		logger.info("Exiting deleteFromDB method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDB(Opinion opinion) throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		boolean sucess = service.deleteFromDB(opinion);
		logger.info("Exiting deleteFromDB method in business delegate");
		return sucess;
	}

	@Override
	public List<Opinion> getTopMostOpinion() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Opinion> getOpinionByUser(Long userId) throws MessageException, Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		List<Opinion> opinion = service.getOpinionByUser(userId);
		logger.info("Exiting deleteFromDB method in business delegate");
		return opinion;
	}

}
