package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;

/**
 * Business Delegate interface for entity: {@link Category}
 * @author Abhishek
 *
 */
public interface ICategoryBusinessDelegate {

    /**
     * Business delegate method to create category, subcategory or indicator by calling
     * com.ohmuk.folitics.service.ICategoryService.create
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category create(Category category)throws MessageException,Exception;

    /**
     * Business Delegate method to get category, subcategory or indicator object using
     * com.ohmuk.folitics.service.ICategoryService.read by passing the id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category read(Long id)throws MessageException,Exception;

    /**
     * Business Delegate method to get all category, subcategory and indicators using
     * com.ohmuk.folitics.service.ICategoryService.readAll
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> readAll()throws MessageException,Exception;

    /**
     * Business Delegate method to update category, subcategory or indicator using
     * com.ohmuk.folitics.service.ICategoryService.update
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category update(Category category)throws MessageException,Exception;

    /**
     * Business Delegate method to soft delete a category, subcategory or indicator by giving id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category delete(Long id)throws MessageException,Exception;

    /**
     * Business Delegate method to soft delete a category, subcategory or indicator by giving object of
     * com.ohmuk.folitics.jpa.entity.Category
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category delete(Category category)throws MessageException,Exception;

    /**
     * Business Delegate method to permanent delete a category, subcategory or indicator using
     * com.ohmuk.folitics.service.ICategoryService.delete by passing id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category deleteFromDB(Long id)throws MessageException,Exception;

    /**
     * Business Delegate method to permanent delete a category, subcategory or indicator using
     * com.ohmuk.folitics.service.ICategoryService.delete by passing object of com.ohmuk.folitics.jpa.entity.Category
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category category
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    public Category deleteFromDB(Category category)throws MessageException,Exception;

    /**
     * Business Delegate method to search a category, subcategory or indicator by name using
     * com.ohmuk.folitics.service.ICategoryService.search by passing name
     * 
     * @author Abhishek
     * @param String name
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> search(String name)throws MessageException,Exception;

    /**
     * Business Delegate method to all category, subcategory and indicators which don't have status not equal to Deleted
     * or Disabled using com.ohmuk.folitics.service.ICategoryService.readAllActiveCategories
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> readAllActiveCategories()throws MessageException,Exception;

    /**
     * Business Delegate method to all subcategory which don't have status not equal to Deleted or Disabled using
     * com.ohmuk.folitics.service.ICategoryService.readAllActiveSubCategories
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> readAllActiveSubCategories()throws MessageException,Exception;

    /**
     * Business Delegate method to all indicators which don't have status not equal to Deleted or Disabled using
     * com.ohmuk.folitics.service.ICategoryService.readAllActiveIndicators
     * 
     * @author Abhishek
     * @return java.util.List<com.ohmuk.folitics.jpa.entity.Category>
     */
    public List<Category> readAllActiveIndicators() throws MessageException,Exception;
}
