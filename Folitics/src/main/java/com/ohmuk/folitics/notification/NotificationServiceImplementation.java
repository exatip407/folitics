package com.ohmuk.folitics.notification;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.enums.NotificationReadStatus;
import com.ohmuk.folitics.hibernate.entity.NotificationLookUp;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserConnection;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionComment;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentComment;
import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollow;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollow;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollow;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLike;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLike;
import com.ohmuk.folitics.hibernate.entity.like.TaskLike;
import com.ohmuk.folitics.mongodb.entity.NotificationMongo;
import com.ohmuk.folitics.mongodb.service.INotificationMongoService;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * @author Harish
 *
 */
@Service
@Transactional
public class NotificationServiceImplementation implements
		InterfaceNotificationService {

	@Autowired
	private IUserService userService;

	@Autowired
	private INotificationMongoService notificationMongoService;

	@Autowired
	private INotificationRepository NotificationRepository;

	private static Logger logger = LoggerFactory
			.getLogger(NotificationServiceImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	@Override
	public void followNotification(NotificationMapping notificationMapping)
			throws Exception {

		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "follow"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskFollow.class);
						criteria.add(Restrictions.eqOrIsNull(
								"followId.componentId",
								notificationMapping.getComponentId()));
						criteria.add(
								Restrictions.eqOrIsNull("isFollowing", true))
								.setProjection(
										Projections.property("followId.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);
					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionFollow.class);
							criteria.add(Restrictions.eqOrIsNull(
									"followId.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions
											.eqOrIsNull("isFollowing", true))
									.setProjection(
											Projections
													.property("followId.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskLike.class);
						criteria.add(Restrictions.eqOrIsNull("id.componentId",
								notificationMapping.getComponentId()));
						criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
								.setProjection(
										Projections.property("id.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionLike.class);
							criteria.add(Restrictions.eqOrIsNull(
									"id.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions.eqOrIsNull("likeFlag", true))
									.setProjection(
											Projections.property("id.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Commentors")) {
				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentComment.class);
					criteria.add(
							Restrictions.eqOrIsNull("componentId",
									notificationMapping.getComponentId()))
							.setProjection(Projections.property("userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskComment.class);
						criteria.add(
								Restrictions.eqOrIsNull("componentId",
										notificationMapping.getComponentId()))
								.setProjection(Projections.property("userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionComment.class);
							criteria.add(
									Restrictions.eqOrIsNull("componentId",
											notificationMapping
													.getComponentId()))
									.setProjection(
											Projections.property("userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getUsername() + " follow "
				+ notificationMapping.getComponentType()
				+ " which you have already followed";
		notificationMapping.setMessage(msg);

		// call for redis
		Notification notification = new Notification();
		notification.setMessage(msg);
		notification.setNotificationType("general");
		notification.setTimeStamp(DateUtils.getSqlTimeStamp());
		notification.setSendingUsers(sendNotificationToForMongo);

		// NotificationRepository.createNotification(notificationMapping);
		NotificationRepository.createNotificationWithType(notification);

		// NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("Follow");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("general");

			NotificationMongo notificationMongo2 = notificationMongoService
					.save(notificationMongo);
		}

	}

	@Override
	public void likeNotification(NotificationMapping notificationMapping)
			throws Exception {

		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "Like"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskFollow.class);
						criteria.add(Restrictions.eqOrIsNull(
								"followId.componentId",
								notificationMapping.getComponentId()));
						criteria.add(
								Restrictions.eqOrIsNull("isFollowing", true))
								.setProjection(
										Projections.property("followId.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);
					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionFollow.class);
							criteria.add(Restrictions.eqOrIsNull(
									"followId.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions
											.eqOrIsNull("isFollowing", true))
									.setProjection(
											Projections
													.property("followId.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskLike.class);
						criteria.add(Restrictions.eqOrIsNull("id.componentId",
								notificationMapping.getComponentId()));
						criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
								.setProjection(
										Projections.property("id.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionLike.class);
							criteria.add(Restrictions.eqOrIsNull(
									"id.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions.eqOrIsNull("likeFlag", true))
									.setProjection(
											Projections.property("id.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Commentors")) {
				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentComment.class);
					criteria.add(
							Restrictions.eqOrIsNull("componentId",
									notificationMapping.getComponentId()))
							.setProjection(Projections.property("userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskComment.class);
						criteria.add(
								Restrictions.eqOrIsNull("componentId",
										notificationMapping.getComponentId()))
								.setProjection(Projections.property("userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionComment.class);
							criteria.add(
									Restrictions.eqOrIsNull("componentId",
											notificationMapping
													.getComponentId()))
									.setProjection(
											Projections.property("userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getUsername() + " Likes "
				+ notificationMapping.getComponentType()
				+ " which you have already like";
		notificationMapping.setMessage(msg);

		Notification notification = new Notification();
		notification.setMessage(msg);
		notification.setNotificationType("general");
		notification.setTimeStamp(DateUtils.getSqlTimeStamp());
		notification.setSendingUsers(sendNotificationToForMongo);

		// NotificationRepository.createNotification(notificationMapping);
		NotificationRepository.createNotificationWithType(notification);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("Like");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setUrl("/"
					+ notificationMapping.getComponentType() + "/"
					+ notificationMapping.getComponentId());
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("general");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void airNotification(NotificationMapping notificationMapping)
			throws Exception {
		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "Air"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskFollow.class);
						criteria.add(Restrictions.eqOrIsNull(
								"followId.componentId",
								notificationMapping.getComponentId()));
						criteria.add(
								Restrictions.eqOrIsNull("isFollowing", true))
								.setProjection(
										Projections.property("followId.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);
					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionFollow.class);
							criteria.add(Restrictions.eqOrIsNull(
									"followId.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions
											.eqOrIsNull("isFollowing", true))
									.setProjection(
											Projections
													.property("followId.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskLike.class);
						criteria.add(Restrictions.eqOrIsNull("id.componentId",
								notificationMapping.getComponentId()));
						criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
								.setProjection(
										Projections.property("id.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionLike.class);
							criteria.add(Restrictions.eqOrIsNull(
									"id.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions.eqOrIsNull("likeFlag", true))
									.setProjection(
											Projections.property("id.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Commentors")) {
				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentComment.class);
					criteria.add(
							Restrictions.eqOrIsNull("componentId",
									notificationMapping.getComponentId()))
							.setProjection(Projections.property("userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskComment.class);
						criteria.add(
								Restrictions.eqOrIsNull("componentId",
										notificationMapping.getComponentId()))
								.setProjection(Projections.property("userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionComment.class);
							criteria.add(
									Restrictions.eqOrIsNull("componentId",
											notificationMapping
													.getComponentId()))
									.setProjection(
											Projections.property("userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getUsername() + " Likes "
				+ notificationMapping.getComponentType()
				+ "which you have already like";
		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("Like");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("general");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void shareNotification(NotificationMapping notificationMapping)
			throws Exception {
		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "Share"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskFollow.class);
						criteria.add(Restrictions.eqOrIsNull(
								"followId.componentId",
								notificationMapping.getComponentId()));
						criteria.add(
								Restrictions.eqOrIsNull("isFollowing", true))
								.setProjection(
										Projections.property("followId.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);
					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionFollow.class);
							criteria.add(Restrictions.eqOrIsNull(
									"followId.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions
											.eqOrIsNull("isFollowing", true))
									.setProjection(
											Projections
													.property("followId.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskLike.class);
						criteria.add(Restrictions.eqOrIsNull("id.componentId",
								notificationMapping.getComponentId()));
						criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
								.setProjection(
										Projections.property("id.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionLike.class);
							criteria.add(Restrictions.eqOrIsNull(
									"id.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions.eqOrIsNull("likeFlag", true))
									.setProjection(
											Projections.property("id.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Commentors")) {
				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentComment.class);
					criteria.add(
							Restrictions.eqOrIsNull("componentId",
									notificationMapping.getComponentId()))
							.setProjection(Projections.property("userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskComment.class);
						criteria.add(
								Restrictions.eqOrIsNull("componentId",
										notificationMapping.getComponentId()))
								.setProjection(Projections.property("userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionComment.class);
							criteria.add(
									Restrictions.eqOrIsNull("componentId",
											notificationMapping
													.getComponentId()))
									.setProjection(
											Projections.property("userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getUsername() + " share "
				+ notificationMapping.getComponentType()
				+ "which you have already shared";
		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("Share");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("general");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void responseNotification(NotificationMapping notificationMapping,
			Response response) throws Exception {
		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "Response"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"response")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId", response.getOpinion()
									.getSentiment().getId()));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				}
			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				Criteria criteria = getSession().createCriteria(
						SentimentLike.class);
				criteria.add(Restrictions.eqOrIsNull("id.componentId", response
						.getOpinion().getSentiment().getId()));
				criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
						.setProjection(Projections.property("id.userId"));
				@SuppressWarnings("unchecked")
				List<Long> list1 = criteria.list();
				// notificationMapping.setSendingUsers(list);
				Set<Long> userSet = new HashSet<Long>(list1);

				Criteria criteria2 = getSession().createCriteria(
						OpinionLike.class);
				criteria2.add(Restrictions.eqOrIsNull("id.componentId",
						response.getOpinion().getId()));
				criteria2.add(Restrictions.eqOrIsNull("likeFlag", true))
						.setProjection(Projections.property("id.userId"));

				@SuppressWarnings("unchecked")
				List<Long> list = criteria2.list();
				userSet = new HashSet<Long>(list);
				notificationMapping.setSendingUsers(userSet);

				sendNotificationToForMongo.addAll(userSet);

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"response")) {

				Criteria criteria = getSession().createCriteria(Response.class);

				criteria.add(
						Restrictions.eqOrIsNull("opinion",
								response.getOpinion())).setProjection(
						Projections.property("user"));
				@SuppressWarnings("unchecked")
				List<Long> list1 = criteria.list();
				// notificationMapping.setSendingUsers(list);
				Set<Long> userSet = new HashSet<Long>(list1);

				notificationMapping.setSendingUsers(userSet);

				sendNotificationToForMongo.addAll(userSet);

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getUsername() + " share "
				+ notificationMapping.getComponentType()
				+ "which you have already shared";
		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("Share");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("opinion");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void dislikeNotification(NotificationMapping notificationMapping)
			throws Exception {
		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "Dislike"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskFollow.class);
						criteria.add(Restrictions.eqOrIsNull(
								"followId.componentId",
								notificationMapping.getComponentId()));
						criteria.add(
								Restrictions.eqOrIsNull("isFollowing", true))
								.setProjection(
										Projections.property("followId.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);
					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionFollow.class);
							criteria.add(Restrictions.eqOrIsNull(
									"followId.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions
											.eqOrIsNull("isFollowing", true))
									.setProjection(
											Projections
													.property("followId.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskLike.class);
						criteria.add(Restrictions.eqOrIsNull("id.componentId",
								notificationMapping.getComponentId()));
						criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
								.setProjection(
										Projections.property("id.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionLike.class);
							criteria.add(Restrictions.eqOrIsNull(
									"id.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions.eqOrIsNull("likeFlag", true))
									.setProjection(
											Projections.property("id.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Dislikers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							notificationMapping.getComponentId()));
					criteria.add(Restrictions.eqOrIsNull("dislikeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskLike.class);
						criteria.add(Restrictions.eqOrIsNull("id.componentId",
								notificationMapping.getComponentId()));
						criteria.add(
								Restrictions.eqOrIsNull("dislikeFlag", true))
								.setProjection(
										Projections.property("id.userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionLike.class);
							criteria.add(Restrictions.eqOrIsNull(
									"id.componentId",
									notificationMapping.getComponentId()));
							criteria.add(
									Restrictions
											.eqOrIsNull("dislikeFlag", true))
									.setProjection(
											Projections.property("id.userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Commentors")) {
				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"sentiment")) {
					Criteria criteria = getSession().createCriteria(
							SentimentComment.class);
					criteria.add(
							Restrictions.eqOrIsNull("componentId",
									notificationMapping.getComponentId()))
							.setProjection(Projections.property("userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				} else {
					if (notificationMapping.getComponentType()
							.equalsIgnoreCase("task")) {
						Criteria criteria = getSession().createCriteria(
								TaskComment.class);
						criteria.add(
								Restrictions.eqOrIsNull("componentId",
										notificationMapping.getComponentId()))
								.setProjection(Projections.property("userId"));
						@SuppressWarnings("unchecked")
						List<Long> list = criteria.list();
						Set<Long> userSet = new HashSet<Long>(list);
						notificationMapping.setSendingUsers(userSet);

						sendNotificationToForMongo.addAll(userSet);

					} else {
						if (notificationMapping.getComponentType()
								.equalsIgnoreCase("opinion")) {
							Criteria criteria = getSession().createCriteria(
									OpinionComment.class);
							criteria.add(
									Restrictions.eqOrIsNull("componentId",
											notificationMapping
													.getComponentId()))
									.setProjection(
											Projections.property("userId"));
							@SuppressWarnings("unchecked")
							List<Long> list = criteria.list();
							Set<Long> userSet = new HashSet<Long>(list);
							notificationMapping.setSendingUsers(userSet);

							sendNotificationToForMongo.addAll(userSet);
						}
					}
				}

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getUsername() + " dislike "
				+ notificationMapping.getComponentType()
				+ "which you have already dislike";
		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("Dislike");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("general");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void taskNotification(NotificationMapping notificationMapping)
			throws Exception {

		Set<Long> sendNotificationToForMongo = new HashSet<Long>();
		sendNotificationToForMongo
				.addAll(notificationMapping.getSendingUsers());
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getName()
				+ " invited you to participate in his problem ";

		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("task");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setUrl("/problem/"
					+ notificationMapping.getComponentId());
			notificationMongo.setNotificationType("general");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void monetizationNotification(NotificationMapping notificationMapping)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void opinionNotification(NotificationMapping notificationMapping,
			Long sentimentId) throws Exception {

		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria1 = getSession().createCriteria(
				NotificationLookUp.class);
		criteria1.add(Restrictions.eqOrIsNull("action", "opinion"));

		@SuppressWarnings("unchecked")
		List<NotificationLookUp> notificationLookUps = criteria1.list();

		for (NotificationLookUp notificationLookUp : notificationLookUps) {

			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"followers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"opinion")) {
					Criteria criteria = getSession().createCriteria(
							SentimentFollow.class);
					criteria.add(Restrictions.eqOrIsNull(
							"followId.componentId", sentimentId));
					criteria.add(Restrictions.eqOrIsNull("isFollowing", true))
							.setProjection(
									Projections.property("followId.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"Likers")) {

				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"opinion")) {
					Criteria criteria = getSession().createCriteria(
							SentimentLike.class);
					criteria.add(Restrictions.eqOrIsNull("id.componentId",
							sentimentId));
					criteria.add(Restrictions.eqOrIsNull("likeFlag", true))
							.setProjection(Projections.property("id.userId"));
					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				}

			}
			if (notificationLookUp.getNotificationTo().equalsIgnoreCase(
					"connections")) {
				if (notificationMapping.getComponentType().equalsIgnoreCase(
						"opinion")) {
					Criteria criteria = getSession().createCriteria(
							UserConnection.class);
					criteria.add(Restrictions.eqOrIsNull("connectionStatus",
							"Accepted"));
					criteria.add(
							Restrictions.eqOrIsNull("userId",
									notificationMapping.getUserId()))
							.setProjection(Projections.property("connectionId"));

					@SuppressWarnings("unchecked")
					List<Long> list = criteria.list();
					// notificationMapping.setSendingUsers(list);
					Set<Long> userSet = new HashSet<Long>(list);
					notificationMapping.setSendingUsers(userSet);

					sendNotificationToForMongo.addAll(userSet);

				}

			}

		}
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getName() + " created a  "
				+ notificationMapping.getComponentType()
				+ " on these sentiment ";
		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("opinion");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setUrl("/sentiment" + "/"
					+ notificationMapping.getComponentId());
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setNotificationType("opinion");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void connectionNotification(NotificationMapping notificationMapping)
			throws Exception {

		Set<Long> sendNotificationToForMongo = new HashSet<Long>();

		Criteria criteria = getSession().createCriteria(UserConnection.class);
		criteria.add(Restrictions.eqOrIsNull("userId",
				notificationMapping.getUserId()));
		criteria.add(Restrictions.eqOrIsNull("connectionStatus", "Pending"))
				.setProjection(Projections.property("connectionId"));

		@SuppressWarnings("unchecked")
		List<Long> list = criteria.list();
		Set<Long> userSet = new HashSet<Long>(list);

		notificationMapping.setSendingUsers(userSet);

		sendNotificationToForMongo
				.addAll(notificationMapping.getSendingUsers());

		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getName() + " sent a friend request to you ";

		notificationMapping.setMessage(msg);

		Notification notification = new Notification();
		notification.setMessage(msg);
		notification.setNotificationType("connection");
		notification.setTimeStamp(DateUtils.getSqlTimeStamp());
		notification.setSendingUsers(userSet);

		NotificationRepository.createNotificationWithType(notification);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("connection");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setUrl("/requesteduser/"

			+ sendToUser);
			notificationMongo.setNotificationType("connection");

			notificationMongoService.save(notificationMongo);
		}

	}

	@Override
	public void participateNotification(NotificationMapping notificationMapping)
			throws Exception {
		Set<Long> sendNotificationToForMongo = new HashSet<Long>();
		sendNotificationToForMongo
				.addAll(notificationMapping.getSendingUsers());
		User user = userService.findUserById(notificationMapping.getUserId());
		String msg = user.getName() + " wants to participate in your problem ";

		notificationMapping.setMessage(msg);

		NotificationRepository.createNotification(notificationMapping);

		for (Long sendToUser : sendNotificationToForMongo) {

			NotificationMongo notificationMongo = new NotificationMongo();

			notificationMongo.setComponentId(notificationMapping
					.getComponentId());
			notificationMongo.setComponentType(notificationMapping
					.getComponentType());
			notificationMongo.setUserId(notificationMapping.getUserId());
			notificationMongo.setAction("participate");
			notificationMongo.setMessage(msg);
			notificationMongo.setSendToUser(sendToUser);
			notificationMongo.setReadStatus(NotificationReadStatus.UNREAD
					.getValue());
			notificationMongo
					.setCreatedAt(new Date(System.currentTimeMillis()));
			notificationMongo.setUrl("/problem/"
					+ notificationMapping.getComponentId());
			notificationMongo.setNotificationType("general");

			notificationMongoService.save(notificationMongo);
		}

	}
}
