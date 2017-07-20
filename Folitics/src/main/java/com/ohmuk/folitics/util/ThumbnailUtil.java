package com.ohmuk.folitics.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

/**
 * 
 * @author Jahid Ali
 */

public class ThumbnailUtil {
    
    private static final int STANDARD_THUMBNAIL_WIDTH = 100;
    
    private static final int STANDARD_THUMBNAIL_HEIGHT = 100;

    public static byte[] getImageThumbnail(byte[] imageInByte, String imageType) throws IOException {
        return getImageThumbnail(imageInByte, imageType, STANDARD_THUMBNAIL_WIDTH, STANDARD_THUMBNAIL_HEIGHT);
    }

    private static BufferedImage getBufferedImage(byte[] imageInByte) throws IOException{
        InputStream in = new ByteArrayInputStream(imageInByte);
        BufferedImage image = ImageIO.read(in);
        return image;
    }

    public static byte[] getImageThumbnail(byte[] fullImageBytes, String imageType, int width, int height)
            throws IOException {
        BufferedImage fullImage= getBufferedImage(fullImageBytes);
        return getImageThumbnail(fullImage, imageType,width,height);
    }
    
    public static byte[] getImageThumbnail(BufferedImage fullImage, String imageType, int width, int height)
            throws IOException {
        
        // Quality indicate that the scaling implementation should do everything
        // create as nice of a result as possible , other options like speed
        // will return result as fast as possible
        // Automatic mode will calculate the resultant dimensions according
        // to image orientation .so resultant image may be size of 50*36.if you
        // want
        // fixed size like 50*50 then use FIT_EXACT
        // other modes like FIT_TO_WIDTH..etc also available.
        BufferedImage thumbImg = Scalr.resize(fullImage, Method.QUALITY, Mode.AUTOMATIC, width, height,
                Scalr.OP_ANTIALIAS);

        // convert bufferedImage to outpurstream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(thumbImg, imageType, os);
        return os.toByteArray();
    }

}