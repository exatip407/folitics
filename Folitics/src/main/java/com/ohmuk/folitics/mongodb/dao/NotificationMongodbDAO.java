package com.ohmuk.folitics.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.ohmuk.folitics.MongoAutoConfiguration;
import com.ohmuk.folitics.enums.NotificationReadStatus;
import com.ohmuk.folitics.mongodb.entity.NotificationMongo;

/**
 * @author Abhishek
 *
 */
@Service
public class NotificationMongodbDAO {

	@Autowired
	private MongoAutoConfiguration mongoAutoConfiguration;

	private DBCollection getDBCollection() {
		DB db = mongoAutoConfiguration.getMongo().getDB(
				mongoAutoConfiguration.getProperties().getDatabase());
		DBCollection collection = db.getCollection("Notification");
		return collection;
	}

	private List<NotificationMongo> getNotificationMongo(DBCursor cursor,
			List<NotificationMongo> nfs) throws Exception {

		while (cursor.hasNext()) {

			DBObject obj = cursor.next();
			NotificationMongo nf = mongoAutoConfiguration.getMongoTemplate()
					.getConverter().read(NotificationMongo.class, obj);
			nfs.add(nf);
		}
		return nfs;
	}

	public List<NotificationMongo> findUnreadNotificationsForUser(Long userId,String notificationType)
			throws Exception {

		List<NotificationMongo> nfs = new ArrayList<NotificationMongo>();
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("readStatus", NotificationReadStatus.UNREAD
				.getValue()));
		obj.add(new BasicDBObject("notificationType",notificationType));
		obj.add(new BasicDBObject("sendToUser", userId));
		andQuery.put("$and", obj);

		DBCursor cursor = getDBCollection().find(andQuery);
		return getNotificationMongo(cursor, nfs);
	}

}
