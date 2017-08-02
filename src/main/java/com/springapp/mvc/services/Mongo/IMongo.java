package com.springapp.mvc.services.Mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2017/6/14.
 */
public interface IMongo {
    public DB getDb(String dbName);

    public DBCollection getCollection(String dbName,String collectName);

    public ArrayList<DBObject> find();

    /**
     * 单条新增
     * @param dbName
     * @param collectName
     * @param keys
     * @param values
     * @return
     */
    public String insert(String dbName,String collectName,String[] keys,Object[] values);

    /**
     * 批量新建
     * @param dbName
     * @param collectName
     * @param documents
     * @return
     */
    public String insert(String dbName,String collectName,List<? extends DBObject> documents);
}
