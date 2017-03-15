package com.springapp.mvc.services;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Max on 2017/3/15.
 */
public interface ImageService {

    String save(String name, InputStream inputStream) throws IOException;


}
