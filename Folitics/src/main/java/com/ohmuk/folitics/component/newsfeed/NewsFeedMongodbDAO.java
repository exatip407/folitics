package com.ohmuk.folitics.component.newsfeed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.ohmuk.folitics.MongoAutoConfiguration;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;

@Service
public class NewsFeedMongodbDAO {

	@Autowired
	private MongoAutoConfiguration mongoAutoConfiguration;

	private DBCollection getDBCollection() {
		DB db = mongoAutoConfiguration.getMongo().getDB(
				mongoAutoConfiguration.getProperties().getDatabase());
		DBCollection collection = db.getCollection("NewsFeed");
		return collection;
	}

	private List<NewsFeed> getNewsFeed(DBCursor cursor, List<NewsFeed> nfs)
			throws Exception {
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			NewsFeed nf = mongoAutoConfiguration.getMongoTemplate()
					.getConverter().read(NewsFeed.class, obj);
			nfs.add(nf);
		}
		return nfs;
	}

	public List<NewsFeed> findByFeedSourceId(Long feedSourceId)
			throws Exception {
		List<NewsFeed> nfs = new ArrayList<NewsFeed>();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("feedSourceId", feedSourceId);
		DBCursor cursor = getDBCollection().find(whereQuery);
		return getNewsFeed(cursor, nfs);
	}

	public List<NewsFeed> findBySentimentId(Long sentimentId) throws Exception {
		List<NewsFeed> nfs = new ArrayList<NewsFeed>();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("sentimentId", sentimentId);
		
		DBCursor cursor = getDBCollection().find(whereQuery);
		return getNewsFeed(cursor, nfs);
	}

	public List<NewsFeed> findByFeedChannelId(Long feedSourceId,
			Long feedChannelId) throws Exception {
		List<NewsFeed> nfs = new ArrayList<NewsFeed>();
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("feedSourceId", feedSourceId));
		obj.add(new BasicDBObject("feedChannelId", feedChannelId));
		andQuery.put("$and", obj);

		DBCursor cursor = getDBCollection().find(andQuery);
		return getNewsFeed(cursor, nfs);
	}

	public List<NewsFeed> findByFeedDataId(Long feedSourceId,
			Long feedChannelId, Long feedDataId) throws Exception {
		List<NewsFeed> nfs = new ArrayList<NewsFeed>();
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("feedSourceId", feedSourceId));
		obj.add(new BasicDBObject("feedChannelId", feedChannelId));
		obj.add(new BasicDBObject("feedDataId", feedDataId));
		andQuery.put("$and", obj);
		DBCursor cursor = getDBCollection().find(andQuery);
		return getNewsFeed(cursor, nfs);
	}

	public int deleteByFeedSourceId(Long feedSourceId) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("feedSourceId", feedSourceId);
		WriteResult result = getDBCollection().remove(whereQuery);
		return result.getN();
	}

	public int deleteByFeedChannelId(Long feedSourceId, Long feedChannelId) {
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("feedSourceId", feedSourceId));
		obj.add(new BasicDBObject("feedChannelId", feedChannelId));
		andQuery.put("$and", obj);

		WriteResult result = getDBCollection().remove(andQuery);
		return result.getN();
	}

	public int deleteByFeedDataId(Long feedSourceId, Long feedChannelId,
			Long feedDataId) {
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("feedSourceId", feedSourceId));
		obj.add(new BasicDBObject("feedChannelId", feedChannelId));
		obj.add(new BasicDBObject("feedDataId", feedDataId));
		andQuery.put("$and", obj);
		WriteResult result = getDBCollection().remove(andQuery);
		return result.getN();
	}
}
