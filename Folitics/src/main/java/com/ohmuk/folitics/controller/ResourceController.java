package com.ohmuk.folitics.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohmuk.folitics.beans.RegistrationRunner;

@RestController
public class ResourceController extends AbstractRestHandler {
    @Autowired
    private RegistrationRunner registrationRunner;

    @RequestMapping("/resource")
    public Map<String, Object> getResource() {
        LOGGER.info("Returing the resource!");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World Jahid");
        registrationRunner.Test();
        return model;
    }
}