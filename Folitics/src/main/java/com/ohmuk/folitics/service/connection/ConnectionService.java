package com.ohmuk.folitics.service.connection;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ohmuk.folitics.model.ImageModel;

@Service
public class ConnectionService implements IConnectionService {

    @Override
    public ImageModel getImageModel(Long entityId, boolean isThumbnail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ImageModel> getImageModels(String entityIds, boolean isThumbnail) {
        // TODO Auto-generated method stub
        return null;
    }

}
