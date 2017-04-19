package com.springapp.mvc.services;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by pcdalao on 2017/4/19.
 */
public class ImageServiceImpl implements ImageService {

    /**
     *保存图片流到本地
     * @param name
     * @param inputStream
     * @param userId
     * @return
     * @throws IOException
     */
    @Override
    public String save(String name, InputStream inputStream, String userId) throws IOException {
        String suffix=getSuffix(name);
        String uri=buildUri(userId);



        return null;
    }

    /**
     * 组件图片路径
     * @param userId
     * @return
     */
    public String buildUri(String userId){
        final Date date=new Date();

        if(userId==null||userId=="") userId="01";
        SimpleDateFormat format=new SimpleDateFormat("/yyyyMMdd/");
        return "/"+userId+format.format(date)+ UUID.randomUUID().toString()+"/";
    }

    /**
     * 获取后缀文件名
     * @param name
     * @return
     */
    public String getSuffix(String name){
        if(name!=null){
            int i=name.lastIndexOf('.');
            if(i>=0){
                return name.substring(i);
            }
        }
        return "";
    }

}
