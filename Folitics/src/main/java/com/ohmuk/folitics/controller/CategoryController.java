package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.ICategoryBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.CategoryType;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;

/**
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    private static Logger logger = Logger.getLogger(CategoryController.class);
    
    @Autowired
    private ICategoryBusinessDelegate businessDelegate;

    @RequestMapping
    public String getCategoryPage() {

        return "category-page";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> getAdd() {

        List<Category> categorys = new ArrayList<>();
        categorys.add(getTestCategory());
        return new ResponseDto<Category>(true, categorys);
    }

    /**
     * Spring web service(POST) to add a category or subcategory or indicator
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Category> add(@Validated @RequestBody Category category) {
    	

    	logger.info("Inside add method");
    	try
    	{
        category = businessDelegate.create(category);
        
        if (category != null) {
           
        	logger.info("Exiting add method");
        	return new ResponseDto<Category>(true, category,"category added successfully");
        }
    	} catch (MessageException e) {

			logger.error("Exception while adding category", e);
			
			return new ResponseDto(false, null, e.getMessage());
		}

		catch (Exception e) {

			logger.error("Exception while adding category", e);

			return new ResponseDto(false, null, e.getMessage());
		}

        return new ResponseDto<Category>("Data not added",false);
    }

    /**
     * Spring web service(POST) to edit a category or subcategory or indicator
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Category> edit(@Validated @RequestBody Category category) {
    	
    	logger.info("Inside edit method");
    	try
    	{
        category = businessDelegate.update(category);
        if (category != null) {
            
        	logger.info("Exiting edit method");
        	return new ResponseDto<Category>(true, category,"category updated successfully");
        }
    	} catch (MessageException e) {

			logger.error("Exception while updating category", e);
			
			return new ResponseDto(false, null, e.getMessage());
		}

		catch (Exception e) {

			logger.error("Exception while updating category", e);

			return new ResponseDto(false, null, e.getMessage());
		}
        return new ResponseDto<Category>("category not updated",false);
    }

    /**
     * Spring web service(POST) to delete a category or subcategory or indicator by giving id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> delete(Long id) {

    	logger.info("Inside soft delete method by id");
    	try
    	{
        Category category = businessDelegate.delete(id);
        if (category != null) {
            if (category.getStatus().equals(ComponentState.DELETED.getValue())) {
              
            	logger.info("Exiting soft delete method by id");
            	return new ResponseDto<Category>("category deleted successfully",true);
            }
        }
    	}catch (MessageException e) {

			logger.error("Exception while soft deleting category by id", e);
			
			return new ResponseDto(false, null, e.getMessage());
		}

		catch (Exception e) {

			logger.error("Exception while soft deleting category by id", e);

			return new ResponseDto(false, null, e.getMessage());
		}
        return new ResponseDto<Category>("category not deleted",false);
    }

    /**
     * Spring web service(POST) to delete a category or subcategory or indicator by giving object
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Category
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Category> delete(@Validated @RequestBody Category category) {

       logger.info("Inside soft delete method by object");
       try
       {
       category = businessDelegate.delete(category);
        if (category != null) {
            if (category.getStatus().equals(ComponentState.DELETED.getValue())) {
               
            	logger.info("Exiting soft delete method by id");
            	return new ResponseDto<Category>("category deleted successfully",true);
            }
        }
       }catch (MessageException e) {

			logger.error("Exception while soft deleting category by object", e);
			
			return new ResponseDto(false, null, e.getMessage());
		}

		catch (Exception e) {

			logger.error("Exception while soft deleting category by object", e);

			return new ResponseDto(false, null, e.getMessage());
		}
        return new ResponseDto<Category>("category not deleted",false);
    }

    /**
     * Spring web service(POST) to permanent delete a category or subcategory or indicator by giving id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/deleteFromDbById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Category> deleteFromDB(Long id) {

    	 logger.info("Inside permanent delete method by id");
    	 try
    	 {
        Category category = businessDelegate.deleteFromDB(id);
        
        if (category == null) {
           
            logger.info("Exiting permanent delete method by id");
        	return new ResponseDto<Category>("category successfully deleted",true);
        }
    	 }catch (MessageException e) {

 			logger.error("Exception while permanent deleting category by id", e);
 			
 			return new ResponseDto(false, null, e.getMessage());
 		}

 		catch (Exception e) {

 			logger.error("Exception while permanent deleting category by id", e);

 			return new ResponseDto(false, null, e.getMessage());
 		}
        return new ResponseDto<Category>("category not deleted",false);
    }

    /**
     * Spring web service(POST) to permanent delete a category or subcategory or indicator by giving object
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    
    @RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Category> deleteFromDB(@Validated @RequestBody Category category) {

    	 logger.info("Inside permanent delete method by object");
    	 try
    	 {
        category = businessDelegate.deleteFromDB(category);
        if (category == null) {
           
            logger.info("Inside permanent delete method by object");
            
        	return new ResponseDto<Category>("data successfully deleted",true);
        }
    	 }catch (MessageException e) {

  			logger.error("Exception while permanent deleting category by object", e);
  			
  			return new ResponseDto(false, null, e.getMessage());
  		}

  		catch (Exception e) {

  			logger.error("Exception while permanent deleting category by object", e);

  			return new ResponseDto(false, null, e.getMessage());
  		}
        return new ResponseDto<Category>("category not deleted",false);
    }

    /**
     * Spring web service(POST) to get all categories, subcategories and indicators
     * 
     * @author Abhishek
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> getall() {

        logger.info("Inside getall method");
        try
        {        
        List<Category> categorys = businessDelegate.readAll();
        if (categorys != null) {
           
            logger.info("Exiting getall method");
        	return new ResponseDto<Category>(true, categorys,"category fethed successfully");
        }
        }catch (MessageException e) {

  			logger.error("Exception while fething category", e);
  			
  			return new ResponseDto(false, null, e.getMessage());
  		}

  		catch (Exception e) {

  			logger.error("Exception while fething category", e);

  			return new ResponseDto(false, null, e.getMessage());
  		}
        return new ResponseDto<Category>("data not found",false);
    }

    /**
     * Spring web service(GET) to return all active categories, subcategories and indicators
     * 
     * @author Abhishek
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/getActiveCategories", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> getActiveCategories() {

    	  logger.info("Inside getActiveCategories method");
    	  try
    	  {   		  
    
         List<Category> categorys = businessDelegate.readAllActiveCategories();
        if (categorys != null) {
           
        	 logger.info("Exiting getActiveCategories method");
        	return new ResponseDto<Category>(true, categorys,"Get Active categories successfully");
        }
    	  }catch (MessageException e) {

    			logger.error("Exception while getting active category", e);
    			
    			return new ResponseDto(false, null, e.getMessage());
    		}

    		catch (Exception e) {

    			logger.error("Exception while getting active category", e);

    			return new ResponseDto(false, null, e.getMessage());
    		}
        return new ResponseDto<Category>("No any active categories found",false);
    }

    /**
     * Spring web service(GET) to return all active subcategories in the system
     * 
     * @author gautam.yadav
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/getActiveSubCategories", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> getActiveSubCategories() {

        logger.info("Ïnside getActiveSubCategories");
        try {
            List<Category> categorys = businessDelegate.readAllActiveSubCategories();
            if (categorys != null) {
                return new ResponseDto<Category>(true, categorys,"getting active subcategories successfully");
            }
        } catch (MessageException e) {

			logger.error("Exception while getting active category", e);
			
			return new ResponseDto(false, null, e.getMessage());
		}catch (Exception e) {

            logger.error("Exception while fetching active subcategories", e);         
            return new ResponseDto(false, null, e.getMessage());
        }

        return new ResponseDto<Category>("no any active sub categories found",false);
    }

    /**
     * Spring web service(GET) to return all active indicators in the system 
     * @author gautam.yadav
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/getActiveIndicators", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> getActiveIndicators() {

        logger.info("Ïnside getActiveIndicators");
        try {
            List<Category> categories = businessDelegate.readAllActiveIndicators();
            logger.debug("Exiting getActiveIndicators");

            return new ResponseDto<Category>(true, categories,"getting active indicator successfully");
            
        } catch (MessageException e) {

			logger.error("Exception while getting active indicator", e);
			
			return new ResponseDto(false, null, e.getMessage());
		} 
        catch (Exception e) {

            logger.error("Exception while fetching active indicators", e);      
        	return new ResponseDto(false, null, e.getMessage()); 
           // return new ResponseDto<Category>(false,"No any active indicator found");
        }
      
        }
    

    /**
     * Spring web service(GET) to get a category or subcategory or indicator by passing the id
     * 
     * @author Abhishek
     * @param Long id
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> find(Long id) {
    	
       logger.info("Ïnside find method");
       try
       {
        Category category = businessDelegate.read(id);
        if (category != null) {
           
          logger.info("Exiting find method");
        	return new ResponseDto<Category>(true, category,"category find successfully");
        }
       } catch (MessageException e) {

			logger.error("Exception while finding category", e);
			
			return new ResponseDto(false, null, e.getMessage());
		}catch (Exception e) {

           logger.error("Exception while finding category", e);         
           return new ResponseDto(false, null, e.getMessage());
       }

        return new ResponseDto<Category>("category not availlable",false);
    }

    /**
     * Spring web service(GET) to search a category or subcategory or indicator by name
     * 
     * @author Abhishek
     * @param String name
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Category> search(String name) {

    	 logger.info("Ïnside search method");
    	 try
    	 {    
    	List<Category> categories = new ArrayList<Category>();
        categories = businessDelegate.search(name);
        if (categories != null) {
           
        	 logger.info("Exiting search method");
        	return new ResponseDto<Category>(true, categories);
        }
    	 } catch (MessageException e) {

 			logger.error("Exception while searching category", e);
 			
 			return new ResponseDto(false, null, e.getMessage());
 		}catch (Exception e) {

            logger.error("Exception while searching category", e);         
            return new ResponseDto(false, null, e.getMessage());
        }


        return new ResponseDto<Category>(false);
    }

    /**
     * Spring web service(GET) to get a dummy category object
     * 
     * @author Abhishek
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Category>(Boolean , List<Category>)
     */
    @RequestMapping(value = "/getTestCategory", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Category getTestCategory() {

        return getDummyCategory();

    }

    /**
     * This method basically create a dummy object of com.ohmuk.folitics.jpa.entity.Category
     * 
     * @author Abhishek
     * @return com.ohmuk.folitics.jpa.entity.Category
     */
    private Category getDummyCategory() {

        Category category = new Category();
        // category.setId((new Random()).nextLong());
        category.setName("TestCategory");
        category.setType(CategoryType.CATEGORY.toString());
        category.setDescription("This is test category");
        return category;
    }
}
