package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.controller.image.ImageComponent;
import com.ohmuk.folitics.model.ImageModel;

public interface IImageService {
    ImageModel getImageModel(ImageComponent imageComponent, Long entityId, boolean isThumbnail) throws Exception;

    List<ImageModel> getImageModels(ImageComponent imageComponent, String entityIds, boolean isThumbnail);
}
