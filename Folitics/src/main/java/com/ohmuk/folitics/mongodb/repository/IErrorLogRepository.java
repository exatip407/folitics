package com.ohmuk.folitics.mongodb.repository;

import com.ohmuk.folitics.mongodb.entity.ErrorLog;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jahid Ali
 */
public interface IErrorLogRepository extends MongoRepository<ErrorLog, String> {
}
