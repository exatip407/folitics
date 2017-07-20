package com.ohmuk.folitics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.controller.image.ImageComponent;
import com.ohmuk.folitics.model.ImageModel;
import com.ohmuk.folitics.service.connection.IConnectionService;
import com.ohmuk.folitics.service.problem.IProblemService;
import com.ohmuk.folitics.service.vidopic.IVidopicService;

@Service
public class ImageService implements IImageService {

    @Autowired
    IUserService userService;

    @Autowired
    IOpinionService opinionService;

    @Autowired
    ISentimentService sentimentService;

    @Autowired
    IProblemService problemService;

    @Autowired
    IVidopicService vidopicService;

    @Autowired
    IConnectionService connectionService;

    @Override
    public ImageModel getImageModel(ImageComponent imageComponent, Long entityId, boolean isThumbnail)throws Exception {
        if (ImageComponent.USER == imageComponent) {
            return userService.getImageModel(entityId, isThumbnail);
        }
        if (ImageComponent.OPINION == imageComponent) {
            return opinionService.getImageModel(entityId, isThumbnail);
        }
        if (ImageComponent.SENTIMENT == imageComponent) {
            return sentimentService.getImageModel(entityId, isThumbnail);
        }
        if (ImageComponent.PROBLEM == imageComponent) {
            return problemService.getImageModel(entityId, isThumbnail);
        }
        if (ImageComponent.VIDOPIC == imageComponent) {
            return vidopicService.getImageModel(entityId, isThumbnail);
        }
        if (ImageComponent.CONNECTION == imageComponent) {
            return connectionService.getImageModel(entityId, isThumbnail);
        }
        return null;
    }

    @Override
    public List<ImageModel> getImageModels(ImageComponent imageComponent, String entityIds, boolean isThumbnail) {
        if (ImageComponent.USER.equals(imageComponent)) {
            return userService.getImageModels(entityIds, isThumbnail);
        }
        if (ImageComponent.OPINION.equals(imageComponent)) {
            return opinionService.getImageModels(entityIds, isThumbnail);
        }
        if (ImageComponent.SENTIMENT.equals(imageComponent)) {
            return sentimentService.getImageModels(entityIds, isThumbnail);
        }
        if (ImageComponent.PROBLEM.equals(imageComponent)) {
            return problemService.getImageModels(entityIds, isThumbnail);
        }
        if (ImageComponent.VIDOPIC.equals(imageComponent)) {
            return vidopicService.getImageModels(entityIds, isThumbnail);
        }
        if (ImageComponent.CONNECTION.equals(imageComponent)) {
            return connectionService.getImageModels(entityIds, isThumbnail);
        }
        return null;
    }

}
