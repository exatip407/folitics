package com.ohmuk.folitics.model;

public class ImageModel {
    private String name;
    private byte[] image;
    private String imageType;
    private String imageComponent;
    private Long entityId;
    private boolean isThumbnail;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageComponent() {
        return imageComponent;
    }

    public void setImageComponent(String imageComponent) {
        this.imageComponent = imageComponent;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public boolean isThumbnail() {
        return isThumbnail;
    }

    public void setThumbnail(boolean isThumbnail) {
        this.isThumbnail = isThumbnail;
    }
}
