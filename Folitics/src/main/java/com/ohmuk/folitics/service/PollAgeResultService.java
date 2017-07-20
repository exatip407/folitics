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
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroup;
@Aspect
@Service
@Transactional
public class PollAgeResultService implements IPollResult{

    @Override
    public  List<List<PollResultBean>> getPollResult(Poll poll) throws Exception {
        Double sum = 0.0;
        Hibernate.initialize(poll.getOptions());
        List<List<PollResultBean>> lists = new ArrayList<List<PollResultBean>>();
        for (PollOption pollOption : poll.getOptions()) {

            Hibernate.initialize(pollOption.getPollOptionAgeGroups());
            for (PollOptionAgeGroup ageGroup : pollOption.getPollOptionAgeGroups()) {
                sum = sum + ageGroup.getCount();
            }

        }
        for (PollOption pollOption : poll.getOptions()) {
            List<PollResultBean> arrayList = new ArrayList<PollResultBean>();
            for (PollOptionAgeGroup ageGroup : pollOption.getPollOptionAgeGroups()) {
               PollResultBean resultBean = new PollResultBean();
               resultBean.setName(ageGroup.getId().getAgeGroup().getStartAge()+"-"+ageGroup.getId().getAgeGroup().getEndAge());
               resultBean.setValue((ageGroup.getCount()/sum)*100);
               arrayList.add(resultBean);
            }
            lists.add(arrayList);

        }
        return lists;
    }

}
