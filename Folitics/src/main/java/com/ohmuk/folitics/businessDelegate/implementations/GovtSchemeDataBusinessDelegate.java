package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IGovtSchemeDataBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.service.IGovtSchemeDataService;

@Component
public class GovtSchemeDataBusinessDelegate implements IGovtSchemeDataBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataBusinessDelegate.class);

    @Autowired
    private volatile IGovtSchemeDataService govtSchemeDataService;

    @Override
    public GovtSchemeData create(GovtSchemeData govtschemedata) throws Exception {

        logger.info("Inside create method in business delegate");
        GovtSchemeData govtScheme = govtSchemeDataService.create(govtschemedata);
        logger.info("Exiting create method in business delegate");
        return govtScheme;
    }

    @Override
    public GovtSchemeData read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        GovtSchemeData govtScheme = govtSchemeDataService.read(id);
        logger.info("Exiting read method in business delegate");
        return govtScheme;
    }

    @Override
    public List<GovtSchemeData> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<GovtSchemeData> govtScheme = govtSchemeDataService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return govtScheme;
    }

    @Override
    public GovtSchemeData update(GovtSchemeData govtschemedata) throws Exception {
        logger.info("Inside update method in business delegate");
        GovtSchemeData govtScheme = govtSchemeDataService.update(govtschemedata);
        logger.info("Exiting update method in business delegate");
        return govtScheme;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = govtSchemeDataService.delete(id);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(GovtSchemeData govtschemedata) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = govtSchemeDataService.delete(govtschemedata);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = govtSchemeDataService.deleteFromDB(id);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }
}
