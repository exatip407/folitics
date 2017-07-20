package com.ohmuk.folitics.controller.image;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.model.ImageModel;
import com.ohmuk.folitics.service.IImageService;

@Controller
@RequestMapping("/image")
public class ImageController {

    private static Logger logger = Logger.getLogger(ImageController.class);

    @Autowired
    IImageService imageService;

    @RequestMapping(value = "/getimage", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<ImageModel> getImage(String imageComponent, Long id, Boolean isThumbnail) {
        logger.info("getImage : imageComponent: " + imageComponent + ", id : " + id + ", isThumbnail : " + isThumbnail);
        try {
            ImageModel im = imageService.getImageModel(ImageComponent.getImageComponent(imageComponent), id,
                    isThumbnail);
            if (im != null) {
                return new ResponseDto<ImageModel>(true, im);
            }
        } catch (Exception e) {
            logger.error("Could not find the image : " + e.getMessage(), e);
        }
        return new ResponseDto<ImageModel>(false);
    }

    @RequestMapping(value = "/getimages", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<List<ImageModel>> getImages(String imageComponent, String ids, Boolean isThumbnail) {
        logger.info("getImages : imageComponent: " + imageComponent + ", ids : " + ids + ", isThumbnail : "
                + isThumbnail);
        try {
            List<ImageModel> im = imageService.getImageModels(ImageComponent.getImageComponent(imageComponent), ids,
                    isThumbnail);
            if (im != null) {
                return new ResponseDto<List<ImageModel>>(true, im);
            }
        } catch (Exception e) {

        }
        return new ResponseDto<List<ImageModel>>(false);
    }
}
