package com.ohmuk.folitics.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/entry")
public class AuthenticationController extends AbstractRestHandler {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/user")
    public Principal user(Principal user) {
        LOGGER.info("Principal : " + user.getName());
        return user;
    }

    @RequestMapping("/token")
    @ResponseBody
    public Map<String, String> token(HttpSession session) {
        String sessionId = session.getId();
        LOGGER.info("Session ID: " + sessionId);
        return Collections.singletonMap("token", sessionId);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session) {
        System.out.println("Logout .........: ");
        // invalidate session
        session.invalidate();
    }
}
