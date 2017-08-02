package com.springapp.mvc.services.Mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 插入数据，返回id
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @return
     */
    public String inSert(String dbName,String collectionName,String[] keys,Object[] values);

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

    /**
     * 只更新第一条信息
     * @param dbName
     * @param collectionName
     * @param oldValue,查询字段
     * @param newValue，需要更新的字段，老字段不变
     * @return
     */
    public boolean updateOne(String dbName,String collectionName,DBObject oldValue,DBObject newValue);

    public boolean isExist(String dbName,String collectionName,String key,Object value);

    /**
     * 包含条件查找 contain
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @return
     */
    public ArrayList<DBObject> findByInQuery(String dbName, String collectionName, String[] keys, List<Object[]> values, int num);

}
