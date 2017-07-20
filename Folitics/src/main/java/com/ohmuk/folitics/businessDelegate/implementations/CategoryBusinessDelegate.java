package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.ICategoryBusinessDelegate;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.service.ICategoryService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Business Delegate implementation for entity: {@link Category}
 * @author Abhishek
 *
 */

@Component
@Transactional
public class CategoryBusinessDelegate implements ICategoryBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(CategoryBusinessDelegate.class);

    @Autowired
    private volatile ICategoryService categoryService;

    @Override
    public Category create(Category category) throws Exception {
        logger.info("Inside create method in business delegate");
        Category categoryData = categoryService.create(category);
        logger.info("Exiting create method in business delegate");
        return categoryData;

    }

    @Override
    public Category read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        Category categoryData = categoryService.read(id);
        logger.info("Exiting read method in business delegate");
        return categoryData;

    }

    @Override
    public List<Category> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<Category> categories = categoryService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return categories;

    }

    @Override
    public List<Category> readAllActiveCategories() throws Exception {
        logger.info("Inside readAllActiveCategories method in business delegate");
        Set<String> setOfStates = new HashSet<String>();
        setOfStates.add(ComponentState.DELETED.getValue());
        setOfStates.add(ComponentState.DISABLED.getValue());
        logger.info("Exiting readAllActiveCategories method in business delegate");
        return categoryService.readAllActiveCategories(setOfStates);
    }

    @Override
    public List<Category> readAllActiveSubCategories() throws Exception {
        logger.info("Inside readAllActiveSubCategories method in business delegate");
        Set<String> setOfStates = new HashSet<String>();
        setOfStates.add(ComponentState.DELETED.getValue());
        setOfStates.add(ComponentState.DISABLED.getValue());
        logger.info("Exiting readAllActiveSubCategories method in business delegate");
        return categoryService.readAllActiveSubCategories(setOfStates);
    }

    @Override
    public List<Category> readAllActiveIndicators() throws Exception {
        logger.info("Inside readAllActiveIndicators method in business delegate");
        Set<String> setOfStates = new HashSet<String>();
        setOfStates.add(ComponentState.DELETED.getValue());
        setOfStates.add(ComponentState.DISABLED.getValue());
        logger.info("Exiting readAllActiveIndicators method in business delegate");
        return categoryService.readAllActiveIndicators(setOfStates);
    }

    @Override
    public Category update(Category category) throws Exception {
        logger.info("Inside update method in business delegate");
        category.setEdited(DateUtils.getSqlTimeStamp());
        Category categoryData = categoryService.update(category);
        logger.info("Exiting update method in business delegate");
        return categoryData;

    }

    @Override
    public Category delete(Long id) throws Exception {
        logger.info("Inside delete method in business delegate");

        Category category = categoryService.read(id);

        category.setStatus(ComponentState.DELETED.getValue());

        categoryService.create(category);

        List<Category> children = new ArrayList<Category>();

        // if category contains category, getChilds() will give subcategories of this category
        // if category contains subcategory, getChilds() will give indicators of this subcategory
        // if category contains indicator, getChilds() will give null
        children = category.getChilds();

        // if children are not null, then iterate on them. If status of all the parents of each child is Deleted, then
        // set status of this child as Deleted
        if (children != null) {
            for (Category child : children) {
                boolean deleteChild = true;
                List<Category> childParents = child.getParents();
                for (Category childParent : childParents) {

                    // if any parent has status not equal to Deleted, that means we can set the status of child as
                    // deleted
                    if (childParent.getStatus() != ComponentState.DELETED.getValue()) {
                        deleteChild = false;
                        break;
                    }
                }

                // after iterating and checking status of all the parents of child, if the deleteChild is true then set
                // the status of child also as Deleted
                if (deleteChild == true) {
                    child.setStatus(ComponentState.DELETED.getValue());
                    categoryService.create(child);
                }

                List<Category> subchilds = new ArrayList<Category>();

                // if category contains category, getChilds() on child will give indicators of this subcategory
                // in all the other cases it will give null
                subchilds = child.getChilds();

                if (subchilds != null) {

                    // iterating on the indicators and checking if all the subcategories to whom this indicator belongs
                    // have status as Deleted then this set the status of this indicator as deleted
                    for (Category subchild : subchilds) {
                        boolean deletesubchild = true;
                        for (Category subchildParent : subchild.getParents()) {

                            // if any parent has status not equal to Deleted, that means we can set the status of
                            // indicator as deleted
                            if (subchildParent.getStatus() != ComponentState.DELETED.getValue()) {
                                deletesubchild = false;
                                break;
                            }
                        }

                        // after iterating and checking status of all the parents of subchild, if the deleteSubChild is
                        // true then set the status of subchild also as Deleted
                        if (deletesubchild) {
                            subchild.setStatus(ComponentState.DELETED.getValue());
                            categoryService.create(subchild);
                        }
                    }
                }
            }
        }
        Category categoryToReturn = categoryService.read(id);
        logger.info("Exting delete method in business delegate");
        return categoryToReturn;
    }

    @Override
    public Category delete(Category category) throws Exception {
        logger.info("Inside delete method in business delegate");
        Category categoryData = delete(category.getId());
        logger.info("Exiting delete method in business delegate");
        return categoryData;

    }

    @Override
    public Category deleteFromDB(Long id) throws Exception {
      logger.info("Inside deleteFromDB method in business delegate");
       Category category = categoryService.read(id);
       Category categoryData= deleteFromDB(category);
       return categoryData;
    }

    @Override
    public Category deleteFromDB(Category category) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        // set in which we will keep the elements which we have to delete
        Set<Category> toDelete = new HashSet<Category>();

        category = categoryService.read(category.getId());

        // add the category object to the set so that these can be deleted later
        toDelete.add(category);

        List<Category> children = new ArrayList<Category>();

        // if category contains category, getChilds() will give subcategories of this category
        // if category contains subcategory, getChilds() will give indicators of this subcategory
        // if category contains indicator, getChilds() will give null
        children = category.getChilds();
        if (children != null) {
            for (Category child : children) {

                // if the no. of parents of this child is only one and it is only the category then add this object to
                // toDelete set
                if (child.getParents().size() == 1 && child.getParents().get(0) == category) {
                    toDelete.add(child);
                }
            }

            for (Category child : children) {

                if (child.getStatus().equals(ComponentState.DELETED.getValue())) {

                    List<Category> subchilds = new ArrayList<Category>();

                    // if category contains category, getChilds() on child will give indicators of this subcategory
                    // in all the other cases it will give null
                    subchilds = child.getChilds();
                    if (subchilds != null) {
                        for (Category subchild : subchilds) {

                            // set the deleteSubChild true. If any of the parent subcategory of this indicator is not
                            // contained in the toDelete set, then we cannot delete the indicator
                            boolean deleteSubChild = true;
                            for (Category subchildParent : subchild.getParents()) {

                                // if parent of subchild (indicator) is not in toDelete set, then set deletesubchild as
                                // false
                                if (!toDelete.contains(subchildParent)) {
                                    deleteSubChild = false;
                                    break;
                                }
                            }

                            // if status of child is Deleted then add the subchild also in the toDelete set
                            if (deleteSubChild) {
                                toDelete.add(subchild);
                            }
                        }
                    }
                }
            }
        }
        for (Category toDeleteCategory : toDelete) {
            categoryService.delete(toDeleteCategory.getId());
        }
        Category returnCategory = categoryService.read(category.getId());
        logger.info("Exiting deleteFromDB method in business delegate");
        return returnCategory;
    }

    @Override
    public List<Category> search(String name) throws Exception {
        logger.info("Inside search method in business delegate");
        List<Category> categories=categoryService.search(name);
        logger.info("Exiting search method in business delegate");
        return categories;
    }

}
