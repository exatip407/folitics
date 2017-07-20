package com.ohmuk.folitics.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class ImageUtil {

    public static final String getFileExention(String filename){
//        int index = filename.indexOf(".");
//        String extension=  filename.substring(index, filename.length()-1);
        String ext = FilenameUtils.getExtension(filename);
        return ext;
    }
    
    public static List<Long> getListOfIds(String ids){
        List<String> idsList =  Arrays.asList(ids.split(","));
        List<Long> list = new ArrayList<Long>();
        for(String s:idsList){
            list.add(Long.parseLong(s));
        }
        return list;
    }
}
