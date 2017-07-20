package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.model.ImageModel;

public interface IBaseService {
    ImageModel getImageModel(Long entityId, boolean isThumbnail) throws Exception;

    List<ImageModel> getImageModels(String entityIds, boolean isThumbnail);
}
