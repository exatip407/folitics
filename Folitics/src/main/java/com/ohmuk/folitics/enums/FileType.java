package com.ohmuk.folitics.enums;

public enum FileType {

    PNG("png"), JPEG("jpeg"), JPG("jpg"), GIF("gif"), MP3("mp3"), WMA("wma"), WAV("wav"), AVI("avi"), MKV("mkv"), MOVV(
            "movv"), MP4("mp4"), DIVX("divx"), FLV("flv"), OGG("ogg"), OGV("ogv"), MPEG4("mpeg-4"), MPEG("mpeg"), VOB(
            "vob"), WMV("wmv"), THREEGP("3gp");

    private String value;

    private FileType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final FileType getFileType(String value) {
        if (PNG.getValue().equals(value)) {
            return PNG;
        }
        if (JPEG.getValue().equals(value)) {
            return JPEG;
        }
        if (JPG.getValue().equals(value)) {
            return JPG;
        }
        if (AVI.getValue().equals(value)) {
            return AVI;
        }
        if (DIVX.getValue().equals(value)) {
            return DIVX;
        }
        if (FLV.getValue().equals(value)) {
            return FLV;
        }
        if (GIF.getValue().equals(value)) {
            return GIF;
        }
        if (MKV.getValue().equals(value)) {
            return MKV;
        }
        if (MOVV.getValue().equals(value)) {
            return MOVV;
        }
        if (MP3.getValue().equals(value)) {
            return MP3;
        }
        if (MP4.getValue().equals(value)) {
            return MP4;
        }
        if (MPEG.getValue().equals(value)) {
            return MPEG;
        }
        if (MPEG4.getValue().equals(value)) {
            return MPEG4;
        }
        if (OGG.getValue().equals(value)) {
            return OGG;
        }
        if (OGV.getValue().equals(value)) {
            return OGV;
        }
        if (THREEGP.getValue().equals(value)) {
            return THREEGP;
        }
        if (VOB.getValue().equals(value)) {
            return VOB;
        }
        if (WAV.getValue().equals(value)) {
            return WAV;
        }
        if (WMA.getValue().equals(value)) {
            return WMA;
        }
        if (WMV.getValue().equals(value)) {
            return WMV;
        }
        return null;
    }
}
