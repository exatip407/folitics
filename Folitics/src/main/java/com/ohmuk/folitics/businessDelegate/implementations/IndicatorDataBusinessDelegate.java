package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.ChangeIndicatorDataBean;
import com.ohmuk.folitics.beans.HeaderDataBean;
import com.ohmuk.folitics.beans.TrackPromiseBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorDataBusinessDelegate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;
import com.ohmuk.folitics.service.ICategoryService;
import com.ohmuk.folitics.service.IIndicatorDataService;
import com.ohmuk.folitics.service.IIndicatorThresholdService;
import com.ohmuk.folitics.service.IIndicatorWeightedDataService;

@Component
@Transactional
public class IndicatorDataBusinessDelegate implements IIndicatorDataBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(IndicatorDataBusinessDelegate.class);

    @Autowired
    IIndicatorDataService indicatorDataService;

    @Autowired
    IIndicatorThresholdService indicatorThresholdRepository;

    @Autowired
    IIndicatorWeightedDataService indicatorWeightedDataRepository;

    @Autowired
    ICategoryService categoryRepository;

    @Override
    public IndicatorData create(IndicatorData indicatordata) throws MessageException, Exception {
        // Calculate Weighted Value
        // Find Ideal Value
        // Calculate Ideal Weighted Value
        // Find Ideal value range
        // Find ThreshHold category Belonging
        // get the threshold category with the indicatorID and score

        logger.info("Inside  create method in business delegate");
        long indicatorID = indicatordata.getCategory().getId();

        IndicatorWeightedData indicatorWeightedData = null;
        try {
            indicatorWeightedData = indicatorWeightedDataRepository.findByCategoryLatest(indicatordata.getCategory());
        } catch (Exception exception) {
            logger.error("Exception in finding indicatorWeightedData by latest category");
            logger.error("Exception :" + exception);
            throw new MessageException("Some problem in finding indicatorWeightedData for category: "
                    + indicatordata.getCategory().getName());
        }
        List<IndicatorThreshold> indicatorThresholdlist = null;
        try {
            indicatorThresholdlist = indicatorThresholdRepository.findByCategoryLatest(indicatordata.getCategory());
        } catch (Exception exception) {
            logger.info("Exception in finding indicatorThreshold latest category");
            logger.error("Exception :" + exception);
            throw new MessageException("Some problem in finding indicatorThreshold for category: "
                    + indicatordata.getCategory().getName());
        }
        if (indicatorWeightedData == null) {
            throw new MessageException("IndicatorWeightedData for IndicatorId " + indicatorID
                    + " is not available. Can not save IndicatorData");
        }

        if (indicatorThresholdlist == null) {
            throw new MessageException("IndicatorThreshold for IndicatorId " + indicatorID
                    + " is not available. Can not save IndicatorData");
        }

        IndicatorThreshold indicatorThresholdIdeal = null;
        IndicatorThreshold indicatorThresholdActual = null;
        for (IndicatorThreshold indicatorThreshold : indicatorThresholdlist) {
            if (indicatorThreshold.getThreshHoldCategory().equals("On Track")) {
                indicatorThresholdIdeal = indicatorThreshold;
            }
            if (indicatordata.getScore() >= indicatorThreshold.getThreshold_start()
                    && indicatordata.getScore() < indicatorThreshold.getThreshold_end()) {
                indicatorThresholdActual = indicatorThreshold;
            }
        }
        if (indicatorThresholdIdeal == null) {
            throw new MessageException(
                    "IndicatorThresholdIdeal can't be null, on track entry mandatory. can not save IndicatorData");
        }
        double idealValue = (indicatorThresholdIdeal.getThreshold_start() + indicatorThresholdIdeal.getThreshold_end()) / 2;

        if (indicatorThresholdActual == null) {
            throw new MessageException(
                    "IndicatorThresholdActual can't be null, score value is not lie beetween threshold value");
        }
        String actualValueThreshLabel = indicatorThresholdActual.getThreshold_start() + "-"
                + indicatorThresholdActual.getThreshold_end();

        double idealValueWeightedValue = idealValue * indicatorWeightedData.getWeightage();
        double indicatorWeightedValue = indicatordata.getScore() * indicatorWeightedData.getWeightage();
        indicatordata.setIdealValueRange(actualValueThreshLabel);
        indicatordata.setWeightedIdealValue(idealValueWeightedValue);
        indicatordata.setWeightedValue(indicatorWeightedValue);
        indicatordata.setThresholdcategory(indicatorThresholdActual.getThreshHoldCategory());
        double delta = (indicatorWeightedValue - idealValueWeightedValue) * indicatorWeightedData.getImpactOnChart();
        indicatordata.setDelta(delta);
        IndicatorData indicatorData = indicatorDataService.create(indicatordata);
        logger.info("Exiting  create method in business delegate");
        return indicatorData;

    }

    @Override
    public IndicatorData read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        IndicatorData indicatorData = indicatorDataService.read(id);
        logger.info("Exiting read method in business delegate");
        return indicatorData;
    }

    @Override
    public List<IndicatorData> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<IndicatorData> indicatorDataList = indicatorDataService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return indicatorDataList;
    }

    @Override
    public IndicatorData update(IndicatorData indicatordata) throws Exception {
        logger.info("Inside update method in business delegate");
        IndicatorData indicatorData = indicatorDataService.update(indicatordata);
        logger.info("Exiting update method in business delegate");
        return indicatorData;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = indicatorDataService.delete(id);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(IndicatorData indicatordata) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = indicatorDataService.delete(indicatordata);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public List<HeaderDataBean> readAllByThresholdGroup() throws Exception {
        logger.info("Inside IndicatorDataService create method");
        List<IndicatorData> indicatorDataList = findAllIndicatorDataLatest();
        List<HeaderDataBean> headerDataBeanlist = new ArrayList<HeaderDataBean>();
        Map<String, HeaderDataBean> returnMap = new HashMap<String, HeaderDataBean>();

        for (IndicatorData indicatorData : indicatorDataList) {
            String key = indicatorData.getThresholdcategory();
            indicatorData.setCategoryName(indicatorData.getCategory().getName());
            indicatorData.setCategoryID(indicatorData.getCategory().getId());
            if (!returnMap.containsKey(key)) {

                HeaderDataBean hd = new HeaderDataBean();
                hd.setThresholdCategory(key);
                returnMap.put(key, hd);
            }

            HeaderDataBean hd = returnMap.get(key);
            hd.setTotalIdealVaueScore(hd.getTotalIdealVaueScore() + indicatorData.getWeightedIdealValue());
            hd.setTotalScore(hd.getTotalScore() + indicatorData.getWeightedValue());
            returnMap.get(key).getIndicatorDataList().add(indicatorData);

        }
        for (Map.Entry<String, HeaderDataBean> indicators : returnMap.entrySet()) {
            headerDataBeanlist.add(indicators.getValue());

        }
        return headerDataBeanlist;
    }

    @Override
    public List<TrackPromiseBean> readAllBySubCategory() throws Exception {
        logger.info("Inside readAllBySubCategory method in business delegate");
        List<TrackPromiseBean> trackPromiseBeanList = new ArrayList<TrackPromiseBean>();
        List<Category> categoryList = categoryRepository.findByType("Category");
        for (Category category : categoryList) {
            List<Category> subCategoryList = category.getChilds();

            Map<String, Double> returnMap = new HashMap<String, Double>();
            for (Category subCategory : subCategoryList) {
                Double score = 0.0;
                Hibernate.initialize(subCategory.getChilds());
                List<Category> indicatorForCategoryList = subCategory.getChilds();
              
                boolean indicatorDataExists = false;
                for (Category indicator : indicatorForCategoryList) {
                    IndicatorData indicatorData = findByCategoryLatest(indicator);

                    if (indicatorData != null) {
                        indicatorDataExists = true;
                        score += indicatorData.getDelta();
                    }
                }
                if (indicatorDataExists) {

                    TrackPromiseBean trackPromiseBean = new TrackPromiseBean();
                    trackPromiseBean.setCategory(category.getName());
                    trackPromiseBean.setSubCategory(subCategory.getName());
                    trackPromiseBean.setTotalScore(score);
                    trackPromiseBean.setSubCategoryID(subCategory.getId());
                    if (score > 0) {
                        trackPromiseBean.setDirection("Up");

                    } else
                        trackPromiseBean.setDirection("Down");
                    trackPromiseBeanList.add(trackPromiseBean);
                }
            }
        }
        logger.info("Exiting readAllBySubCategory method in business delegate");
        return trackPromiseBeanList;

    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = indicatorDataService.deleteFromDB(id);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }

    @Override
    public IndicatorData findByCategoryLatest(Category category) {
        logger.info("Inside findByCategoryLatest method in business delegate");
        IndicatorData indicatorData = indicatorDataService.findByCategoryLatest(category);
        logger.info("Exiting findByCategoryLatest method in business delegate");
        return indicatorData;
    }

    @Override
    public List<IndicatorData> findAllIndicatorDataLatest() {
        logger.info("Inside findAllIndicatorDataLatest method in business delegate");
        List<IndicatorData> indicatorDataList = indicatorDataService.findAllIndicatorDataLatest();
        logger.info("Exiting findAllIndicatorDataLatest method in business delegate");
        return indicatorDataList;
    }
    
    @Override
    public List<ChangeIndicatorDataBean> findIndicatorDataForVerdict() {
        logger.info("Inside findAllIndicatorDataLatest method in business delegate");
        List<IndicatorData> indicatorDataList = indicatorDataService.findIndicatorDataForVerdict();
        Map<Long,IndicatorData> categoryMap = new HashMap<Long,IndicatorData>();
        List<ChangeIndicatorDataBean> changeBeans = new ArrayList<ChangeIndicatorDataBean>();
        for (IndicatorData indicatorData : indicatorDataList) {
            if(null!=categoryMap.get(indicatorData.getCategory().getId())){
                    ChangeIndicatorDataBean changeBean = new ChangeIndicatorDataBean();
                    IndicatorData indicatorDataOld = categoryMap.get(indicatorData.getCategory().getId());
                    changeBean.setIndicatorName(indicatorDataOld.getCategory().getName());
                    changeBean.setValueFrom(indicatorDataOld.getScore());
                    changeBean.setValueTo(indicatorData.getScore());
                    changeBean.setLastChange(indicatorData.getEditTime());
                    changeBeans.add(changeBean);
            }else {
                categoryMap.put(indicatorData.getCategory().getId(), indicatorData) ;
            }
        }
        logger.info("Exiting findAllIndicatorDataLatest method in business delegate");
        return changeBeans;
    }
    
    
    @Override
    public List<IndicatorData> findByCategory(Category category) {
        logger.info("Inside findAllIndicatorDataLatest method in business delegate");
        List<IndicatorData> indicatorDataList = indicatorDataService.findAllIndicatorDataLatest();
        logger.info("Exiting findAllIndicatorDataLatest method in business delegate");
        return indicatorDataList;
    }
}
