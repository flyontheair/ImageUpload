package com.springapp.mvc.services;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pcdalao on 2017/4/19.
 */
public interface ImageService {
    String save(String name, InputStream inputStream,String userId) throws IOException;
}
