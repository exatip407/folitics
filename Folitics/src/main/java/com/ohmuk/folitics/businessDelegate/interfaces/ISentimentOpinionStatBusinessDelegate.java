package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;

public interface ISentimentOpinionStatBusinessDelegate {
    /**
     * Method is to add {@link SentimentOpinionStat}
     * 
     * @param sentimentOpinionStat
     * @return {@link SentimentOpinionStat}
     * @throws Exception
     */
    public SentimentOpinionStat create(SentimentOpinionStat sentimentOpinionStat) throws Exception;

    /**
     * Method is to get {@link SentimentOpinionStat} by id
     * 
     * @param id
     * @return {@link SentimentOpinionStat}
     * @throws Exception
     */
    public SentimentOpinionStat read(Long id) throws Exception;

    /**
     * Method is to get all {@link SentimentOpinionStat}
     * 
     * @return {@link SentimentOpinionStat}
     * @throws Exception
     */
    public List<SentimentOpinionStat> readAll() throws Exception;

    /**
     * Method is to update {@link SentimentOpinionStat}
     * 
     * @param sentimentOpinionStat
     * @return {@link SentimentOpinionStat}
     * @throws Exception
     */
    public SentimentOpinionStat update(SentimentOpinionStat sentimentOpinionStat) throws Exception;

    /**
     * Method is to hard delete by id
     * 
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Long id) throws Exception;

    /**
     * Method is to hard delete
     * 
     * @param sentimentOpinionStat
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(SentimentOpinionStat sentimentOpinionStat) throws Exception;

}
