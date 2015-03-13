package org.at.mongosql.adaptor;

import com.mongodb.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by oleksandr.tarasenko on 12/15/2014.
 */

//@Ignore
public class MongoAdaptorTest {

    DB db;

    DataSourceAdaptor adapter;

    @Before
    public void initDbCollection() throws UnknownHostException {
        adapter = new MongoAdaptor();
        MongoClient mongo = new MongoClient("localhost", 27017);
        db = mongo.getDB("sqlTest");
        putDataToCollection("alex", 35);
        putDataToCollection("roma", 45);
        putDataToCollection("igor", 45);
        putDataToCollection("igor", 15);
        putDataToCollection("nick", 15);

    }

    @After
    public void dropDbCollection() {
        DBCollection table = db.getCollection("user");
        table.drop();
    }

    @Test
    public void shouldSelectDataFromCollectionWithAdapter() {
        DBCursor cursor = adapter.find("user");
        assertEquals(5, cursor.size());
    }

    @Test
    public void shouldSaveDataToCollection() {
        adapter.put("user", prepareData());
        DBCursor cursor = adapter.find("user");
        assertEquals(6, cursor.size());
    }

    @Test
    public void shouldFindByConditions() {
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("name", "alex");
        DBCursor cursor = adapter.find("user", conditions);
        assertEquals(1, cursor.size());
    }

    @Test
    public void shouldFindUserByNameAndAge() {
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("name", "igor");
        conditions.put("age", new BasicDBObject("$gt", 15));
        DBCursor cursor = adapter.find("user", conditions);
        assertEquals(1, cursor.size());
    }

    @Test
    public void shouldFindUserByNames() {
        DBObject igor = new BasicDBObject("name", "igor");
        DBObject alex = new BasicDBObject("name", "alex");
        BasicDBList or = new BasicDBList();
        or.add(igor);
        or.add(alex);
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("$or", or);
        DBCursor cursor = adapter.find("user", conditions);
        assertEquals(3, cursor.size());
    }

    private void putDataToCollection(String name, int age) {
        DBCollection table = db.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("age", age);
        document.put("createdDate", new Date());
        table.insert(document);
    }

    private Map<String, Object> prepareData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "test_user");
        map.put("age", 100);
        map.put("createdDate", new Date());
        return map;
    }

}
