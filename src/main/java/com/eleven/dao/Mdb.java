package com.eleven.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @ClassName Temperature
 * @Description 连接mongo的类  可以缓存 mongo和coll
 * @Author ELeven
 * @Date 2022/4/19 21:08
 * @Version 1.0
 **/
public class Mdb {
    /**
     * 最后要用mongo.clos();
     */
    public MongoClient mongo;
    /**
     * 可以通过集合获取文档
     */
    public MongoCollection<Document> coll;

    public Mdb(MongoClient mongo, MongoCollection<Document> coll) {
        this.mongo = mongo;
        this.coll = coll;
    }
}
