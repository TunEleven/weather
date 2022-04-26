package com.eleven.dao;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

//带你封装代码:
//      封装好处:封号了之后,无需关心内部是怎么写的
//      只需要在主函数中调用一下,传入必要的参数即可
public class Mongos {
    //final:最终的,一次赋值,终身不该.
    //final修饰的变量,都是大写字母(行业规范)
    public static final String HOST = "localhost";
    public static final int PORT = 27017;
    public static final String DBNAME = "test";

    //
    public static Mdb newMdb(String coll){
        //创建数据库连接
        MongoClient mongo = new MongoClient(HOST,PORT); //mongo
        //获取指定 库
        MongoDatabase db  = mongo.getDatabase(DBNAME);  //db
                //获取库中集合并返回
      return  new Mdb(mongo,db.getCollection(coll));
    }

    public static String findOne(String coll, Bson bson){
        Mdb db = newMdb(coll);
        MongoCursor<Document> cursor =  db.coll.find(bson).iterator();
        // ="{}" 是默认空集合,写= null会报错
        String json = "{}";
        while(cursor.hasNext()){
            Document doc = cursor.next();
            json = new Gson().toJson(doc);
            break;
        }
        db.mongo.close();
        return json;
    }
    public static String find(String coll){
        Mdb db = newMdb(coll);
        MongoCursor<Document> cursor =  db.coll.find().iterator();
        List<Document> documentList = new ArrayList<>();
        // ="{}" 是默认空集合,写= null会报错
        String json = "{}";
        while(cursor.hasNext()){
            Document doc = cursor.next();
            documentList.add(doc);
        }
        json = new Gson().toJson(documentList);
        db.mongo.close();
        return json;
    }
    //添加一个文档,coll表, doc 添加的文档
    public static void insertOne(String coll,Document doc){
        Mdb db = newMdb(coll);
        db.coll.insertOne(doc);
        db.mongo.close();

    }
    //修改一个文档,coll表,bson条件,doc修改成什么样子
    public static void updateOne(String coll,Bson bson,Document doc){
        Mdb db = newMdb(coll);
        //套娃  {@set:{doc}}  也是个文档,文档套娃
        db.coll.updateMany(bson,new Document("$set",doc));
    }

}
