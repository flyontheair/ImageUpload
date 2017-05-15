package com.springapp.mvc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by pcdalao on 2017/4/19.
 */
public interface ImageService {
    //保存
    Map<String,String> save(String name, InputStream inputStream, String userId) throws IOException;

    //读取
    FileInputStream read(String path) throws IOException;
}
