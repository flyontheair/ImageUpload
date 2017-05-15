package com.springapp.mvc.services.zip;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by pcdalao on 2017/5/15.
 */
public class ZipUtils {
    /**
     *
     * @param files 文件列表
     * @param destFile 目标zip文件
     * @throws Exception
     */
    public static void doCompress(List<File> files,File destFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile));
        for(File file : files){
            doCompress(file,out);
        }
    }

    public static void doCompress(String pathname, ZipOutputStream out) throws IOException{
        doCompress(new File(pathname), out);
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException {
        if( file.exists() ){
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int len = 0 ;
            /* 读取文件的内容,打包到zip文件 */
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.closeEntry();
            fis.close();
        }
    }
}
