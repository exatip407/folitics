package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Component;

/**
 * @author Abhishek
 *
 */
public interface IComponentService {
    

    /**
     * This method to create category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @return Component
     */
    public Component create(Component component) throws Exception;

    /**
     * This method is to find category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @return Component
     */
    public Component read(Long id) throws Exception;

    /**
     * This method is to find all category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @return List<Component>
     * @throws Exception
     */
    public List<Component> readAll() throws Exception;
    /**
     * This method is to update category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @return Component
     * @throws Exception
     */
    public Component update(Component component) throws Exception;
    
    /**
     * This method is to hard delete category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @return Component
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;
    
    /**
     * This method is to delete category, subcategory or indicator by calling
     */
    public boolean delete(Component component) throws Exception;

}
