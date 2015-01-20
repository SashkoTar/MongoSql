package org.at.mongosql.adaptor;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by oleksandr.tarasenko on 12/15/2014.
 */
public class MongoAdaptor implements DataSourceAdaptor {

    DB db;

    public MongoAdaptor() throws UnknownHostException {
        this("localhost", 27017, "sqlTest");
    }

    public MongoAdaptor(String host, int port, String database) {
       try {
           MongoClient mongo = new MongoClient(host, port);
           db = mongo.getDB(database);
       } catch (UnknownHostException ex) {
           throw new RuntimeException("Database Not Found", ex);
       }
    }

    public DBCursor find(String collection) {
        DBCollection table = db.getCollection(collection);
        return table.find();
    }

    public void put(String collection, Map<String, Object> data) {
        DBCollection table = db.getCollection(collection);
        BasicDBObject document = new BasicDBObject(data);
        table.insert(document);
    }

    public DBCursor find(String collection, Map<String, Object> conditions) {
        DBCollection table = db.getCollection(collection);
        BasicDBObject query = new BasicDBObject(conditions);
        return table.find(query);
    }

    @Override
    public DBCursor find(String collection, BasicDBObject conditions) {
        DBCollection table = db.getCollection(collection);
        return table.find(conditions);
    }
}
