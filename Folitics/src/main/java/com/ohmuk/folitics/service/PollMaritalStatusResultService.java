package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.charting.beans.PollResultBean;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatus;
@Aspect
@Service
@Transactional
public class PollMaritalStatusResultService implements IPollResult {

    @Override
    public  List<List<PollResultBean>> getPollResult(Poll poll) throws Exception {



        Double sum = 0.0;
        List<List<PollResultBean>> lists = new ArrayList<List<PollResultBean>>();
        Hibernate.initialize(poll.getOptions());
        for (PollOption pollOption : poll.getOptions()) {

            Hibernate.initialize(pollOption.getPollOptionMaritalStatuses());
            for (PollOptionMaritalStatus sex : pollOption.getPollOptionMaritalStatuses()) {
                sum = sum + sex.getCount();
            }

        }
        for (PollOption pollOption : poll.getOptions()) {
            List<PollResultBean> arrayList = new ArrayList<PollResultBean>();
            for (PollOptionMaritalStatus sex : pollOption.getPollOptionMaritalStatuses()) {
               PollResultBean resultBean = new PollResultBean();
               resultBean.setName(sex.getId().getMaritalStatus().getMaritalStatus());
               resultBean.setValue((sex.getCount()/sum)*100);
               arrayList.add(resultBean);
            }
            lists.add(arrayList);

        }
        return lists;
    
    
    
    }

}
