package com.springapp.mvc.services.Mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.ArrayList;

/**
 * Created by Max on 2017/5/13.
 */
public interface MongoDao {
    /**
     * 获取指定数据库
     * @param dbName
     * @return
     */
    public DB getDb(String dbName);

    public DBCollection getCollection(String dbName,String collectionName);

    public boolean inSert(String dbName,String collectionName,String[] keys,Object[] values);

    public boolean delete(String dbName,String collectionName,String[] keys,Object[] value);

    /**
     * 从数据库dbName中查找指定keys的values值
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @param num
     * @return
     */
    public ArrayList<DBObject> find(String dbName,String collectionName,String[] keys,Object[] values,int num);

    public boolean update(String dbName,String collectionName,DBObject oldValue,DBObject newValue);

    public boolean isExist(String dbName,String collectionName,String key,Object value);

}
