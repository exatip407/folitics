package com.ohmuk.folitics.mongodb.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ohmuk.folitics.mongodb.entity.NotificationMongo;

public interface INotificationMongoRepository extends
		MongoRepository<NotificationMongo, String> {

}
