package com.ohmuk.folitics.service;

import java.util.List;
import java.util.Set;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;

/**
 * Service interface for entity: {@link Category}
 * @author Abhishek
 *
 */
public interface ICategoryService {

    /**
     * Method to save the com.ohmuk.folitics.jpa.entity.Category object using
     * com.ohmuk.folitics.jpa.repository.ICategoryRepository.save
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category create(Category category)throws MessageException,Exception;

    /**
     * Method to get object of com.ohmuk.folitics.jpa.entity.Category using
     * com.ohmuk.folitics.jpa.repository.ICategoryRepository.find by passing id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category read(Long id)throws MessageException,Exception;

    /**
     * Method to get all categories, subcategories and indicators using
     * com.ohmuk.folitics.jpa.repository.ICategoryRepository.findAll
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> readAll()throws MessageException,Exception;

    /**
     * Method to update an object using com.ohmuk.folitics.jpa.repository.ICategoryRepository.save
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category update(Category category)throws MessageException,Exception;

    /**
     * Method to permanent delete an object using com.ohmuk.folitics.jpa.repository.ICategoryRepository.delete by
     * passing id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public boolean delete(Long id)throws MessageException,Exception;

    /**
     * Method to permanent delete an object using com.ohmuk.folitics.jpa.repository.ICategoryRepository.delete by
     * passing object of com.ohmuk.folitics.jpa.entity.Category
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public boolean delete(Category category)throws MessageException,Exception;

    /**
     * Method to search a category, subcategory or indicator using
     * com.ohmuk.folitics.jpa.repository.ICategoryRepository.findByName by passing String name
     * 
     * @author Abhishek
     * @param String name
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> search(String name)throws MessageException,Exception;

    /**
     * Method to get all the categories, subcategories and indicators which don't have status as Deleted or Disabled
     * using com.ohmuk.folitics.jpa.repository.ICategoryRepository.findByStatusNotIn
     * 
     * @author Abhishek
     * @param java.util.Set<String> setOfStates
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> readAllActiveCategories(Set<String> setOfStates)throws MessageException,Exception;

    /**
     * Method to get all the subcategories which don't have status as Deleted or Disabled using
     * com.ohmuk.folitics.jpa.repository.ICategoryRepository.findByTypeAndStatusNotIn
     * 
     * @param java.util.Set<String> setOfStates
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     * @throws Exception
     */
    public List<Category> readAllActiveSubCategories(Set<String> setOfStates) throws MessageException,Exception;

    /**
     * Method to get all the indicators which don't have status as Deleted or Disabled using
     * com.ohmuk.folitics.jpa.repository.ICategoryRepository.findByTypeAndStatusNotIn
     * 
     * @param java.util.Set<String> setOfStates
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     * @throws Exception
     */
    public List<Category> readAllActiveIndicators(Set<String> setOfStates)throws MessageException,Exception;
    /**
     * Method is to get all {@link Category} by type
     * @param string
     * @return List<{@link Category}>
     */
    public List<Category> findByType(String string);

}
