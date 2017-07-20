package com.ohmuk.folitics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.mongodb.entity.ErrorLog;
import com.ohmuk.folitics.mongodb.repository.IErrorLogRepository;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @Autowired
    private IErrorLogRepository errorLogRepository;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<ErrorLog> getall() {

        List<ErrorLog> errors = errorLogRepository.findAll();

        if (errors != null) {
            return new ResponseDto<ErrorLog>(true, errors);
        }

        return new ResponseDto<ErrorLog>(false);
    }
}
