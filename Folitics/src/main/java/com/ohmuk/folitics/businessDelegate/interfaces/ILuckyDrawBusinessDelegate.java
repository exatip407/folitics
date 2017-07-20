package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;

public interface ILuckyDrawBusinessDelegate {
    

    /**
     * This method is used to read particular contest from data base
     * corresponding to the given id.
     * 
     * @param id
     *            of contest
     * @return : read corresponding contest
     * @throws MessageException
     *             ,Exception*
     */
    public LuckyDraw read(Long id) throws MessageException, Exception;

    /**
     * This method is used to display all Contest.
     * 
     * @return: list of Contest
     * @throws MessageException
     *             ,Exception
     */

    public List<LuckyDraw> readAll() throws MessageException, Exception;

    /**
     * This method is used to update the Contest.
     * 
     * @param com
     *            .ohmuk.folitics.jpa.entity.Contest
     * @return: updated contest
     * @throws MessageException
     *             ,Exception
     * 
     */
    public LuckyDraw update(LuckyDraw luckyDraw) throws MessageException, Exception;

    /**
     * This method is used to delete the contest by id.
     * 
     * @param id
     *            of contest
     * @return: null
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
     * This method is used to read all the active contest
     * 
     * @return : list of all active contest
     * @throws MessageException
     *             ,Exception
     * 
     */

    public List<LuckyDraw> readAllActiveLuckyDraw() throws MessageException, Exception;
    
    


}
