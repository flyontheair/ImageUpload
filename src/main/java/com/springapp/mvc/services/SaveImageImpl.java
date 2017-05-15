package com.springapp.mvc.services;

import com.springapp.mvc.services.Mongo.MongoDaoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by pcdalao on 2017/5/15.
 */
public class SaveImageImpl implements SaveImage {
    private MongoDaoImpl mongoDao;

    public SaveImageImpl(){
        mongoDao=MongoDaoImpl.getMongoDaoImplInstance();
    }

    @Override
    public String Save(Map<String, String> map) {
        List<String> keys=new ArrayList<String>();
        List<Object> values=new ArrayList<Object>();
        Iterator<Map.Entry<String, String>> iterator=map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> item=iterator.next();
            keys.add(item.getKey());
            values.add(item.getValue());
        }
        return mongoDao.inSert("Demo","Image",keys.toArray(new String[keys.size()]),values.toArray());
    }
}
