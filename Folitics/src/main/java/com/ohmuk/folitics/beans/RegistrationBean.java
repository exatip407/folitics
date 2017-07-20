package com.ohmuk.folitics.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.mongodb.repository.UserRepository;

@Component
public class RegistrationBean {

    @Autowired
    private UserRepository userRepository;

    public RegistrationBean() {

    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}