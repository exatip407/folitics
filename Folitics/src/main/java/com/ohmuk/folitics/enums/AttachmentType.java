package com.ohmuk.folitics.enums;

public enum AttachmentType {
    IMAGE("image"), AUDIO("audio"), VIDEO("video"), FILE("file");

    private String value;

    private AttachmentType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final AttachmentType getAttachmentType(String value) {
        if (AttachmentType.IMAGE.getValue().equals(value)) {
            return AttachmentType.IMAGE;
        }
        if (AttachmentType.AUDIO.getValue().equals(value)) {
            return AttachmentType.AUDIO;
        }
        if (AttachmentType.VIDEO.getValue().equals(value)) {
            return AttachmentType.VIDEO;
        }
        if (AttachmentType.FILE.getValue().equals(value)) {
            return AttachmentType.FILE;
        }

        return null;
    }
}
