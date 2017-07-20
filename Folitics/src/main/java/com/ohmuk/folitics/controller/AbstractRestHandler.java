package com.ohmuk.folitics.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ohmuk.folitics.model.RestResponse;

public abstract class AbstractRestHandler {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public @ResponseBody RestResponse handleUncaughtException(Exception ex, WebRequest request,
            HttpServletResponse response) {
        LOGGER.info("Converting Uncaught exception to RestResponse : " + ex.getMessage());

        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new RestResponse("Error occurred", ex.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody RestResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request,
            HttpServletResponse response) {
        LOGGER.info("Converting IllegalArgumentException to RestResponse : " + ex.getMessage());

        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new RestResponse("Error occurred", ex.toString());
    }

}