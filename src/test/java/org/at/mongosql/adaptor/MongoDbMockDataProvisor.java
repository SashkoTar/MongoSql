package org.at.mongosql.adaptor;

import com.mongodb.*;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by otarasenko on 3/24/15.
 */
public class MongoDbMockDataProvisor {

    private static final String COLLECTION_NAME = "user";

    DataSourceAdaptor adapter;

    @Before
    public void initDbCollection() throws UnknownHostException {
        adapter = new MongoAdaptor();
       // MongoClient mongo = new MongoClient("localhost", 27017);
       // db = mongo.getDB("sqlTest");
        putDataToCollection("alex", 35);
    }

    @Test
    public void shouldCallBeforeForDataProvisioning() {
        DBCursor results = adapter.find(COLLECTION_NAME);
        BasicDBObject row = (BasicDBObject)results.next();
        assertTrue(row.getBoolean("active"));
        assertTrue(row.getBoolean("open"));

    }


    private void putDataToCollection(String name, int age) {
        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("age", age);
        document.put("createdDate", new Date());
        document.put("active", true);
        document.put("open", new Boolean("true"));
        document.put("weight", new Double(34.55));
        document.put("height", new Byte((byte)11555));

        adapter.put(COLLECTION_NAME, document);
    }
    /*
    private void putDataToCollection(String name, int age) {
        DBCollection table = db.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("age", age);
        document.put("createdDate", new Date());
        table.insert(document);
    }*/
}
