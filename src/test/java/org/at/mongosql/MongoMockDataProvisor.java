package org.at.mongosql;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;

import java.net.UnknownHostException;


public class MongoMockDataProvisor {
    DB db;

    private MongoMockDataProvisor() throws UnknownHostException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        db = mongo.getDB("sqlTest");
    }

    public void dropDbCollection() {
        DBCollection table = db.getCollection("user");
        table.drop();
    }

    public void putDataToCollection(String name, int age) {
        DBCollection table = db.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("age", age);
        document.put("createdDate", new java.util.Date());
        table.insert(document);
    }

    public static MongoMockDataProvisor newInstance() throws UnknownHostException {
        return new MongoMockDataProvisor();
    }
}
