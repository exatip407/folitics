package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.charting.beans.PollResultBean;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;

public interface IPollResult {
    public  List<List<PollResultBean>> getPollResult(Poll poll) throws Exception;
}
