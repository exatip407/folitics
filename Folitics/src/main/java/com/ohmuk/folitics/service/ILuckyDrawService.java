package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Fact;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;

public interface ILuckyDrawService {

  

    /**
     * This method is used to read all information of Luckydraw corresponding to
     * the particular id.
     * 
     * @param id
     * @return : read Luckydraw
     * @throws MessageException,{@link Exception}
     * 
     */

    public LuckyDraw read(Long id) throws MessageException, Exception;

    /**
     * This method is used to display all luckydraw.
     * 
     * @return: list of luckydraw
     * @throws MessageException
     *             ,Exception
     */

    public List<LuckyDraw> readAll() throws MessageException, Exception;

    /**
     * This method is used to update the LuckyDraw.
     * 
     * @param luckyDraw
     * @return: updated LuckyDraw
     * @throws MessageException
     *             ,Exception
     * 
     */

    public LuckyDraw update(LuckyDraw luckyDraw) throws MessageException, Exception;

    /**
     * This method is used to hard delete the LuckyDraw by id.
     * 
     * @param LuckyDraw
     *            id
     * @return: boolean value true, if successfully deleted contest
     * @throws MessageException
     *             ,Exception
     * 
     */

    public boolean deleteFromDb(Long id) throws MessageException, Exception;
    /**
     * This method is used to soft delete the  contest by id.
     * 
     * @param contest
     *            id
     * @return: boolean value true, if successfully deleted contest
     * @throws MessageException
     *             ,Exception
     * 
     */

    public boolean delete(Long id) throws MessageException,Exception;

    /**
     * This method is used to read all the active LuckyDraw
     * 
     * @return : list of all active contest
     * @throws MessageException
     *             ,Exception
     * 
     */

    public List<LuckyDraw> readAllActiveLuckydraw() throws MessageException, Exception;
    
   

}
