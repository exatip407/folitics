package com.ohmuk.folitics.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ohmuk.folitics.mongodb.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(String id);

    public List<User> findByFullName(String fullName);

    public User findByUserId(String userId);

}
