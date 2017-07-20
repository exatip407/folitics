package com.ohmuk.folitics.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.CategoryType;
import com.ohmuk.folitics.hibernate.entity.Category;

/**
 * Service implementation for entity: {@link Category}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class CategoryService implements ICategoryService {

    private static Logger logger = Logger.getLogger(CategoryService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * This method to add new {@link Category}
     * 
     * @author Mayank Sharma
     * @return {@link Category}
     * @throws Exception
     */
    @Override
    public Category create(Category category) throws Exception {
        logger.debug("Inside category serivice create method");
        Long id = (Long) getSession().save(category);
        logger.debug("Exiting from category serivice create method");
        return read(id);
    }

    /**
     * This method return {@link Category} by id
     * 
     * @author Mayank Sharma
     * @return {@link Category}
     * @throws Exception
     */
    @Override
    public Category read(Long id) throws Exception {
        return (Category) getSession().get(Category.class, id);
    }

    /**
     * This method return list of all {@link Category}
     * 
     * @author Mayank Sharma
     * @return List< {@link Category} >
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Category> readAll() throws Exception {
        logger.debug("Inside category service readAll method");
        Criteria criteria = getSession().createCriteria(Category.class);
        List<Category> categories = criteria.list();
        logger.debug("Exiting from category serivice readAll method");
        return categories;
    }

    /**
     * This method update {@link Category}
     * 
     * @author Mayank Sharma
     * @return {@link Category}
     * @throws Exception
     */
    @Override
    public Category update(Category category) throws Exception {
        logger.debug("Inside category service update method");
        if (null != category.getId()) {
            getSession().update(category);
            logger.debug("Exiting from category serivice readAll method");
            return read(category.getId());
        }
        logger.debug("Category id is null");
        logger.debug("Exiting from category serivice readAll method");
        return null;
    }

    /**
     * This mehtod give list of all active {@link Category}
     * 
     * @author Mayank Sharma
     * @return List<Category>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Category> readAllActiveCategories(Set<String> setOfStates) throws Exception {
        logger.debug("Inside category service readAllActiveCategories method");
        Criteria criteria = getSession().createCriteria(Category.class);
        criteria.add(Restrictions.not(Restrictions.in("status", setOfStates)));
        List<Category> categories = criteria.list();
        logger.debug("Exiting from category serivice readAllActiveCategories method");
        return categories;
    }

    /**
     * @author gautam.yadav
     * @return List< {@link Category} > : list of subcategory which are not deleted and disabled
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Category> readAllActiveSubCategories(Set<String> setOfStates) throws Exception {
        logger.info("Inside category service ActiveSubCategories method");
        Criteria criteria = getSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("type", CategoryType.SUBCATEGORY.getValue()));
        criteria.add(Restrictions.not(Restrictions.in("status", setOfStates)));
        List<Category> categories = criteria.list();
        logger.debug("Exiting category service readAllActiveSubCategories method");
        return categories;
    }

    /**
     * @author gautam.yadav
     * @return List< {@link Category} > : list of indicators which are not deleted and disabled
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> readAllActiveIndicators(Set<String> setOfStates) throws Exception {
        logger.info("Inside get category service ActiveIndicators");
        Criteria criteria = getSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("type", CategoryType.INDICATOR.getValue()));
        criteria.add(Restrictions.not(Restrictions.in("status", setOfStates)));
        List<Category> indicators = criteria.list();
        logger.info("Exiting form category service ActiveIndicators");
        return indicators;
    }

    /**
     * This method delete {@link Category} with category id
     * 
     * @author Mayank Sharma
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside category service delete method");
        Category category = read(id);
        getSession().delete(category);
        logger.info("Exiting from category service delete");
        return true;
    }

    /**
     * This method delete category
     * 
     * @author Mayank Sharma
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(Category category) throws Exception {
        logger.info("Inside category service delete method");
        category = read(category.getId());
        getSession().delete(category);
        logger.info("Exiting from category service delete");
        return true;
    }

    /**
     * This method find Category by name
     * 
     * @author Mayank Sharma
     * @return List<Category>
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> search(String name) throws Exception {
        logger.info("Inside category service search method");
        Criteria criteria = getSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("name", name));
        List<Category> categories = criteria.list();
        logger.info("Exiting category service search method");
        return categories;
    }

    /**
     * Method is to get all {@link Category} by type
     * @param string
     * @return List<{@link Category}>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Category> findByType(String string) {
        logger.info("Inside category service findByType method");
        List<Category> categories = getSession().createCriteria(Category.class)
                .add(Restrictions.eq("type", "category")).list();
        logger.info("Exiting category service findByType method");
        return categories;
    }
}
