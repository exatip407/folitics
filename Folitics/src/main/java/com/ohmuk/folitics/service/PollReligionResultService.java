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
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligion;
@Aspect
@Service
@Transactional
public class PollReligionResultService implements IPollResult {

    @Override
    public  List<List<PollResultBean>> getPollResult(Poll poll) throws Exception {



        Double sum = 0.0;
        Hibernate.initialize(poll.getOptions());
        List<List<PollResultBean>> lists = new ArrayList<List<PollResultBean>>();
        for (PollOption pollOption : poll.getOptions()) {

            Hibernate.initialize(pollOption.getPollOptionReligions());
            for (PollOptionReligion sex : pollOption.getPollOptionReligions()) {
                sum = sum + sex.getCount();
            }

        }
        for (PollOption pollOption : poll.getOptions()) {
            List<PollResultBean> arrayList = new ArrayList<PollResultBean>();
            for (PollOptionReligion sex : pollOption.getPollOptionReligions()) {
               PollResultBean resultBean = new PollResultBean();
               resultBean.setName(sex.getId().getReligion().getReligion());
               resultBean.setValue((sex.getCount()/sum)*100);
               arrayList.add(resultBean);
            }
            lists.add(arrayList);

        }
        return lists;
    
    
    
    }

}
