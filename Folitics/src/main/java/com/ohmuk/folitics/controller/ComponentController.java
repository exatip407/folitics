package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IComponentBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.Component;

/**
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/component")
public class ComponentController {
    @Autowired
    private volatile IComponentBusinessDelegate businessDelegate;

    private static Logger logger = LoggerFactory.getLogger(ComponentController.class);

    @RequestMapping
    public String getComponentPage() {
        return "component-page";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Component> getAdd() {
        try {
            List<Component> components = new ArrayList<>();
            components.add(getTestComponent());
            return new ResponseDto<Component>(true, components);
        } catch (Exception e) {
            logger.error("Exception while adding dummy component", e);

            return new ResponseDto(false, null, e.getMessage());
        }
    }

    /**
     * This web service is to add category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @param component
     * @return ResponseDto<Component>
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Component> add(@RequestBody Component component) {
        logger.info("Inside component controller add method");
        try {
            component = businessDelegate.create(component);
        } catch (Exception exception) {
            logger.error("Exception in adding component");
            logger.error("Exception: " + exception);
            return new ResponseDto<Component>(false);
        }
        if (component != null) {
            logger.debug("Component is successfully added");
            return new ResponseDto<Component>(true, component);
        }
        logger.debug("Component is not added");
        return new ResponseDto<Component>(false);
    }

    /**
     * This web service is to edit category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @param component
     * @return ResponseDto<Component>
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Component> edit(@RequestBody Component component) {
        logger.info("Inside component controller edit method");
        try {
            component = businessDelegate.update(component);
        } catch (Exception exception) {
            logger.error("Exception in editing component");
            logger.error("Exception: " + exception);
            return new ResponseDto<Component>(false);
        }
        if (component != null) {
            logger.debug("Component is edited");
            return new ResponseDto<Component>(true, component);
        }
        logger.debug("Component is not edited");
        return new ResponseDto<Component>(false);
    }

    /**
     * This web service to delete category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<Component>
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Component> delete(Long id) {
        try {
            if (businessDelegate.delete(id)) {
                return new ResponseDto<Component>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting component");
            logger.error("Exception: " + exception);
            return new ResponseDto<Component>(false);
        }
        return new ResponseDto<Component>(false);
    }

    /**
     * This web service to delete category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @param component
     * @return ResponseDto<Component>
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Component> delete(@RequestBody Component component) {
        logger.info("Inside component controller");
        try {
            if (businessDelegate.delete(component)) {
                logger.debug("Component is deleted");
                return new ResponseDto<Component>(true);
            }
        } catch (Exception exception) {
            logger.error("Exception in deleting component");
            logger.error("Exception: " + exception);
            return new ResponseDto<Component>(false);
        }
        logger.debug("Component is not deleted");
        return new ResponseDto<Component>(false);
    }

    /**
     * This web businessDelegate is to get all category, subcategory or indicator by calling
     * @author Mayank Sharma
     * @return ResponseDto<Component>
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Component> getAll() {
        logger.info("Inside component controller getAll method");
        List<Component> components = null;
        try {
            components = businessDelegate.readAll();
        } catch (Exception exception) {
            logger.error("Exception in reading all component");
            logger.error("Exception: " + exception);
            return new ResponseDto<Component>(false);
        }
        if (components != null) {
            logger.debug("Components are found");
            return new ResponseDto<Component>(true, components);
        }
        logger.debug("Components is not found");
        return new ResponseDto<Component>(false);
    }

    /**
     * This web service is to find component by id
     * @author Mayank Sharma
     * @param id
     * @return ResponseDto<Component>
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Component> find(Long id) {
        logger.info("Inside component controller find method");
        Component component = null;
        try {
            component = businessDelegate.read(id);
        } catch (Exception exception) {
            logger.error("Exception in finding component");
            logger.error("Exception: " + exception);
            return new ResponseDto<Component>(false);
        }
        if (component != null) {
            logger.debug("Component is found with id :" + component.getId());
            return new ResponseDto<Component>(true, component);
        }

        logger.debug("Component is not found");
        return new ResponseDto<Component>(false);
    }

    @RequestMapping(value = "/getTestComponent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Component getTestComponent() {
        return getDummyComponent();

    }

    private Component getDummyComponent() {
        Component component = new Component();
        return component;
    }
}
