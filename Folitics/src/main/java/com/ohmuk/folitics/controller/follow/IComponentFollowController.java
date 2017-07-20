package com.ohmuk.folitics.controller.follow;

import org.springframework.stereotype.Controller;

import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollow;

@Controller
public interface IComponentFollowController<T extends Object> {

    ResponseDto<T> follow(SentimentFollow follow);

    ResponseDto<T> unFollow(Long componentType, Long userId);

}
