package com.springapp.mvc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pcdalao on 2017/4/19.
 */
public interface ImageService {
    //保存
    String save(String name, InputStream inputStream,String userId) throws IOException;

    //读取
    FileInputStream read(String path) throws IOException;
}
