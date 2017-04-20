package com.springapp.mvc.services;

import java.io.File;
import java.io.IOException;

/**
 * Created by pcdalao on 2017/4/20.
 */
public interface ImageCompressor {
    void compressImage(File srcFile, File targetFile) throws IOException;
}
