package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.model.ImageModel;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class OpinionService implements IOpinionService {

    private static Logger logger = LoggerFactory.getLogger(OpinionService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * Method to save the com.ohmuk.folitics.jpa.entity.Opinion object using
     * com.ohmuk.folitics.jpa.repository.IOpinionRepository.save
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    @Override
    public Opinion create(Opinion opinion) throws Exception {
        logger.info("Inside OpinionService create method");
        Long id = (Long) getSession().save(opinion);
        logger.info("Exiting from OpinionService create method");
        return read(id);
    }

    /**
     * Method to get object of com.ohmuk.folitics.jpa.entity.Opinion using
     * com.ohmuk.folitics.jpa.repository.IOpinionRepository.find by passing id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    @Override
    public Opinion read(Long id) throws Exception {
        logger.info("Inside OpinionService read method");
        logger.info("Exiting from OpinionService read method");
        return (Opinion) getSession().get(Opinion.class, id);
    }

    /**
     * Method to get all opinions using com.ohmuk.folitics.jpa.repository.IOpinionRepository.findAll
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Opinion>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Opinion> readAll() throws Exception {
        logger.info("Inside OpinionService readAll method");
        logger.info("Exiting from OpinionService readAll method");
        return getSession().createCriteria(Opinion.class).list();
    }

    /**
     * Method to update an object using com.ohmuk.folitics.jpa.repository.IOpinionRepository.save
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    @Override
    public Opinion update(Opinion opinion) throws Exception {
        logger.info("Inside OpinionService update method");
        opinion.setEditTime(DateUtils.getSqlTimeStamp());
        getSession().update(opinion);
        logger.info("Exiting from OpinionService update method");
        return opinion;
    }

    /**
     * Method to permanent delete an object using com.ohmuk.folitics.jpa.repository.IOpinionRepository.delete by passing
     * id
     * 
     * @author Abhishek
     * @param Long id
     * @return boolean
     */
    @Override
    public boolean deleteFromDBById(Long id) throws Exception {
        logger.info("Inside OpinionService deleteFromDBById method");
        Opinion opinion = (Opinion) getSession().get(Opinion.class, id);
        getSession().delete(opinion);
        logger.info("Exiting from OpinionService deleteFromDBById method");
        return true;
    }

    /**
     * Method to permanent delete an object using com.ohmuk.folitics.jpa.repository.IOpinionRepository.delete by passing
     * object of com.ohmuk.folitics.jpa.entity.Opinion
     * 
     * @author Abhishek
     * @param boolean
     * @return com.ohmuk.folitics.jpa.entity.Opinion
     */
    @Override
    public boolean deleteFromDB(Opinion opinion) throws Exception {
        logger.info("Inside OpinionService deleteFromDB method");
        getSession().delete(opinion);
        logger.info("Exiting from OpinionService deleteFromDB method");
        return true;
    }

    @Override
    public ImageModel getImageModel(Long entityId, boolean isThumbnail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ImageModel> getImageModels(String entityIds, boolean isThumbnail) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public List<Opinion> getOpinionByUser(Long userId) throws MessageException, Exception {
		    logger.info("Inside OpinionService getOpinionByUser method");
		    
		    User user = new User();
		    user.setId(userId);
		    
	        Criteria criteria = getSession().createCriteria(Opinion.class);
	        criteria.add(Restrictions.eqOrIsNull("user", user));
	        		
	        List<Opinion> opinions = criteria.list();		
	        logger.info("Exiting from OpinionService getOpinionByUser method");
	        return opinions;
	}
}
