package com.springapp.mvc.image.services;

import com.springapp.mvc.image.ImageSize;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Max on 2017/3/15.
 */
public interface ImageService {

    InputStream read(String path) throws IOException;

    String save(String name, InputStream inputStream) throws IOException;

    public String saveToVague(String name, InputStream inputStream) throws IOException;

    ImageSize getImageSize(String imageUri) throws IOException;

    String downloadImageFromUri(String imageUri);


}
