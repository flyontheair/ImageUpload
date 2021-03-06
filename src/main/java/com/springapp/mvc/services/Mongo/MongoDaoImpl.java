package com.springapp.mvc.services.Mongo;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Max on 2017/5/14.
 */
public class MongoDaoImpl implements MongoDao {
    private MongoConfig mongoConfig;

    /**
     * 单例模式
     */
    private MongoClient mongoClient = null;

    private MongoDaoImpl() {
        if (mongoClient == null) {
            LoadConfig();//读取配置
            MongoClientOptions.Builder build = new MongoClientOptions.Builder();
            build.connectionsPerHost(mongoConfig.getConnectionsPerHost());//最大连接数50
            //build.autoConnectRetry(mongoConfig.isAutoConnectRetry());//自动重连
            build.threadsAllowedToBlockForConnectionMultiplier(mongoConfig.getThreadsAllowedToBlockForConnectionMultiplier());//如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
               /*
             * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
             * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
             * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
             */
            build.maxWaitTime(1000 * mongoConfig.getMaxWaitTime());
            build.connectTimeout(1000 * mongoConfig.getConnectTimeout());//一分钟timeout
            MongoClientOptions myOptions = build.build();
            try {
                mongoClient = new MongoClient(new ServerAddress(mongoConfig.getHost(), mongoConfig.getPort()), myOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static final MongoDaoImpl mongoDaoImpl = new MongoDaoImpl();

    public static MongoDaoImpl getMongoDaoImplInstance() {
        return mongoDaoImpl;
    }

    //读取yaml
    public void LoadConfig() {
        try {
            mongoConfig = MongoConfig.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DB getDb(String dbName) {
        return mongoClient.getDB(dbName);
    }

    @Override
    public DBCollection getCollection(String dbName, String collectionName) {
        return mongoClient.getDB(dbName).getCollection(collectionName);
    }

    @Override
    public String inSert(String dbName, String collectionName, String[] keys, Object[] values) {
        DB db = null;
        DBCollection dbCollection = null;
        WriteResult result = null;
        String resultString = null;
        if (keys != null && values != null) {
            if (keys.length != values.length) {
                return "";
            } else {
                db = mongoClient.getDB(dbName); //获取数据库实例
                dbCollection = db.getCollection(collectionName);    //获取数据库中指定的collection集合
                BasicDBObject insertObj = new BasicDBObject();
                for (int i = 0; i < keys.length; i++) {   //构建添加条件
                    insertObj.put(keys[i], values[i]);
                }
                insertObj.put("createTime",new Date());
                insertObj.put("updateTime",new Date());
                try {
                    result = dbCollection.insert(insertObj);
                    System.out.println(result);
                    //resultString = result.getUpsertedId()
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (null != db) {
                        //db.requestDone();   //请求结束后关闭db
                    }
                }
                return (resultString != null) ? "" : insertObj.get("_id").toString();
            }
        }
        return "";
    }

    @Override
    public boolean delete(String dbName, String collectionName, String[] keys, Object[] value) {
        return false;
    }

    @Override
    public ArrayList<DBObject> find(String dbName, String collectionName, String[] keys, Object[] values, int num) {
        ArrayList<DBObject> resultList = new ArrayList<DBObject>(); //创建返回的结果集
        if (keys != null && values != null) {
            if (keys.length != values.length) {
                return resultList;  //如果传来的查询参数对不对，直接返回空的结果集
            } else {
                BasicDBObject queryObj = new BasicDBObject();   //构建查询条件

                for (int i = 0; i < keys.length; i++) {   //填充查询条件
                    if (keys[i].equals("_id")) {//根据id查找
                        queryObj.put(keys[i], new ObjectId(values[i].toString()));
                    }else
                        queryObj.put(keys[i], values[i]);
                }

                resultList = find(dbName, collectionName, queryObj, num);
            }
        }

        return resultList;
    }

    @Override
    public boolean update(String dbName, String collectionName, DBObject oldValue, DBObject newValue) {
        DB db = null;
        DBCollection dbCollection = null;
        WriteResult result = null;
        String resultString = null;

        if(oldValue.equals(newValue)){
            return true;
        }else{
            try {
                db = mongoClient.getDB(dbName); //获取数据库实例
                dbCollection = db.getCollection(collectionName);    //获取数据库中指定的collection集合
                newValue.put("updateTime",new Date());

                BasicDBObject update =new BasicDBObject();
                update.append("$set",newValue);
                result = dbCollection.updateMulti(oldValue,update);
                System.out.println("update"+result);

                resultString = "";//result.getError();

                return resultString == null;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally{
                if(null != db){
                    //db.requestDone();   //关闭db
                    db = null;
                }
            }

        }

        return false;
    }

    @Override
    public boolean updateOne(String dbName, String collectionName, DBObject oldValue, DBObject newValue) {
//        DB db = null;
//        DBCollection dbCollection = null;
//        WriteResult result = null;
//        String resultString = null;
//
//        if(oldValue.equals(newValue)){
//            return true;
//        }else{
//            try {
//                db = mongoClient.getDB(dbName); //获取数据库实例
//                dbCollection = db.getCollection(collectionName);    //获取数据库中指定的collection集合
//                //dbCollection.findAndModify()
//                result = dbCollection.update(oldValue, newValue);
//                resultString = result.getError();
//
//                return resultString == null;
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            } finally{
//                if(null != db){
//                    db.requestDone();   //关闭db
//                    db = null;
//                }
//            }
//
//        }

        return false;
    }

    public static void main(String[] args) {
//        MongoDaoImpl mongoDao=MongoDaoImpl.getMongoDaoImplInstance();
//        BasicDBObject query = new BasicDBObject();
//        query.put("fileName", "max9");
//        BasicDBObject stuFound = new BasicDBObject();
//        stuFound.append("fileName", "max10-new");
//        stuFound.append("progress", "100%");
//        BasicDBObject n=new BasicDBObject();
//        n.append("$set",stuFound);
//
//        mongoDao.update("Demo","TaskCompress",query,n);

        MongoDaoImpl mongoDao=MongoDaoImpl.getMongoDaoImplInstance();
        String[] keys={"name"};
        Object[] values={"max"};
        mongoDao.inSert("Demo","test",keys,values);

        BasicDBObject query = new BasicDBObject();
        query.put("name", "max");
        BasicDBObject stuFound = new BasicDBObject();
        stuFound.append("desc", "?????");
        stuFound.append("progress", "100%");
        mongoDao.update("Demo","test",query,stuFound);
    }

    @Override
    public boolean isExist(String dbName, String collectionName, String key, Object value) {
        return false;
    }

    @Override
    public ArrayList<DBObject> findByInQuery(String dbName, String collectionName, String[] keys, List<Object[]> values, int num) {
        ArrayList<DBObject> resultList = new ArrayList<DBObject>(); //创建返回的结果集
        if (keys != null && values != null) {
            if (keys.length != values.size()) {
                return resultList;  //如果传来的查询参数对不对，直接返回空的结果集
            } else {

                BasicDBObject queryObj = new BasicDBObject();   //构建查询条件
                for (int i = 0; i < keys.length; i++) {   //填充查询条件
                    BasicDBList cond = new BasicDBList();
                    if(keys[i].equals("_id")){
                        for (int j = 0; j < values.get(i).length; j++) {
                            cond.add(new ObjectId(values.get(i)[j].toString()));
                        }
                    }else{
                        for (int j = 0; j < values.get(i).length; j++) {
                            cond.add(values.get(i)[j]);
                        }
                    }

                    queryObj.put(keys[i], new BasicDBObject("$in", cond));
                }

                resultList = find(dbName, collectionName, queryObj, num);
            }
        }

        return resultList;
    }

    //查找方法
    private ArrayList<DBObject> find(String dbName, String collectionName, BasicDBObject queryObj, int num) {
        ArrayList<DBObject> resultList = new ArrayList<DBObject>(); //创建返回的结果集
        DB db = null;
        DBCollection dbCollection = null;
        DBCursor cursor = null;
        if (queryObj != null) {
            try {
                db = mongoClient.getDB(dbName); //获取数据库实例
                dbCollection = db.getCollection(collectionName);    //获取数据库中指定的collection集合

                cursor = dbCollection.find(queryObj);   //查询获取数据
                int count = 0;
                if (num != -1) {  //判断是否是返回全部数据，num=-1返回查询全部数据，num!=-1则返回指定的num数据
                    while (count < num && cursor.hasNext()) {
                        resultList.add(cursor.next());
                        count++;
                    }
                    return resultList;
                } else {
                    while (cursor.hasNext()) {
                        resultList.add(cursor.next());
                    }
                    return resultList;
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if (null != cursor) {
                    cursor.close();
                }
                if (null != db) {
                    //db.requestDone();   //关闭数据库请求
                }
            }

        }

        return resultList;
    }
}
