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
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualification;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSex;

@Aspect
@Service
@Transactional
public class PollOverAllResultService implements IPollResult {

    @Override
    public List<List<PollResultBean>> getPollResult(Poll poll) throws Exception {

        Double sum = 0.0;
        Hibernate.initialize(poll.getOptions());
        List<List<PollResultBean>> lists = new ArrayList<List<PollResultBean>>();
        for (PollOption pollOption : poll.getOptions()) {
            Hibernate.initialize(pollOption.getPollOptionAgeGroups());
            for (PollOptionAgeGroup ageGroup : pollOption.getPollOptionAgeGroups()) {
                sum = sum + ageGroup.getCount();
            }

            Hibernate.initialize(pollOption.getPollOptionMaritalStatuses());
            for (PollOptionMaritalStatus maritalStatus : pollOption.getPollOptionMaritalStatuses()) {
                sum = sum + maritalStatus.getCount();
            }

            Hibernate.initialize(pollOption.getPollOptionQualifications());
            for (PollOptionQualification qualification : pollOption.getPollOptionQualifications()) {
                sum = sum + qualification.getCount();
            }

            Hibernate.initialize(pollOption.getPollOptionRegions());
            for (PollOptionRegion region : pollOption.getPollOptionRegions()) {
                sum = sum + region.getCount();
            }

            Hibernate.initialize(pollOption.getPollOptionReligions());
            for (PollOptionReligion religion : pollOption.getPollOptionReligions()) {
                sum = sum + religion.getCount();
            }

            Hibernate.initialize(pollOption.getPollOptionSexes());
            for (PollOptionSex sex : pollOption.getPollOptionSexes()) {
                sum = sum + sex.getCount();
            }

        }

        for (PollOption pollOption : poll.getOptions()) {
            Double optionCount = 0.0;
            List<PollResultBean> arrayList = new ArrayList<PollResultBean>();
            for (PollOptionAgeGroup ageGroup : pollOption.getPollOptionAgeGroups()) {
                optionCount = optionCount + ageGroup.getCount();
            }

            for (PollOptionMaritalStatus maritalStatus : pollOption.getPollOptionMaritalStatuses()) {
                optionCount = optionCount + maritalStatus.getCount();
            }

            for (PollOptionQualification qualification : pollOption.getPollOptionQualifications()) {
                optionCount = optionCount + qualification.getCount();
            }

            for (PollOptionRegion region : pollOption.getPollOptionRegions()) {
                optionCount = optionCount + region.getCount();
            }

            for (PollOptionReligion religion : pollOption.getPollOptionReligions()) {
                optionCount = optionCount + religion.getCount();
            }

            for (PollOptionSex sex : pollOption.getPollOptionSexes()) {
                optionCount = optionCount + sex.getCount();
            }
            PollResultBean resultBean = new PollResultBean();
            resultBean.setValue((optionCount / sum) * 100);
            arrayList.add(resultBean);
            lists.add(arrayList);
        }
        
        return lists;

    }

}
