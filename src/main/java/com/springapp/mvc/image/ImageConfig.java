package com.springapp.mvc.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImageConfig {
    /**
     * format: 80x80
     */
    private List<String> imageSizes;
    
    /**
     * 图片压缩质量
     */
    private float imageQuality = 0.6f;

//    public static synchronized ImageConfig load() throws IOException {
//        return ConfigLoader.loadYamlAs("image.yaml", ImageConfig.class);
//    }

    public ImageConfig(){
        imageSizes=new ArrayList<String>();
        imageSizes.add("160x160");
        imageSizes.add("120x120");
        imageSizes.add("375x180");
        imageSizes.add("80x80");
        imageSizes.add("60x60");
    }

    public List<String> getImageSizes() {
        return imageSizes;
    }

    public void setImageSizes(List<String> imageSizes) {
        this.imageSizes = imageSizes;
    }

    public float getImageQuality() {
        return imageQuality;
    }

    public void setImageQuality(float imageQuality) {
        this.imageQuality = imageQuality;
    }
}
