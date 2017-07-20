package com.ohmuk.folitics.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.mongodb.entity.Address;
import com.ohmuk.folitics.mongodb.entity.User;
import com.ohmuk.folitics.mongodb.repository.UserRepository;

@Component
public class RegistrationRunner {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RegistrationRunner.class);

    @Autowired
    public RegistrationBean registrationBean;

    public void CreateUser(String id) {
        User user = new User();
        user.setUserId(id);
        user.setFullName("FullName");
        user.setStatus("B");
        user.setAge("25");
        Address address = new Address();
        address.setAddressId("201");
        address.setAddressValue("UK/Manchester");
        user.setAddress(address);
        registrationBean.getUserRepository().save(user);
    }

    private UserRepository getUserRepository() {
        return registrationBean.getUserRepository();
    }

    public void Test() {
        try {
            getUserRepository().deleteAll();

            // Create By Spring Data
            CreateUser("200");
            CreateUser("201");

            // fetch all customers
            LOGGER.info("User found with findAll():");
            LOGGER.info("-------------------------------");
            for (User o : getUserRepository().findAll()) {
                LOGGER.info("User : ",o);
            }
            
            // fetch an individual customer
            LOGGER.info("User found with user id 200:");
            LOGGER.info("--------------------------------");
            LOGGER.info("User : ",getUserRepository().findByUserId("200"));

            LOGGER.info("Users found with findByFullName('FullName'):");
            LOGGER.info("--------------------------------");
            for (User o : getUserRepository().findByFullName("FullName")) {
                LOGGER.info("User: ",o);
            }
        } catch (Exception e) {
            LOGGER.error("Testing",e);
        }
    }
}