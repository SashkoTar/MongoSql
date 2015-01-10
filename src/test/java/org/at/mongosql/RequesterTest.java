package org.at.mongosql;

import com.mongodb.*;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created by oleksandr.tarasenko on 12/15/2014.
 */

//@Ignore
public class RequesterTest {

    DB db;
    DBCollection coll;

    @Before
    public void initDbCollection() throws UnknownHostException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        db = mongo.getDB("creditprocessor");
        coll = db.getCollection("creditRequest");
    }


    @Test
    public void shouldReturnSimpleQuery() {
        DBObject doc = coll.findOne();
        System.out.println(doc);
    }

    @Test
    public void shouldReturnSimpleAnd() {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("bankName", "UBRR");
        whereQuery.put("paymentNetworkName", "AMIGO");
        DBCursor cursor = coll.find(whereQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    @Test
    public void shouldReturnSimpleAnd2() {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("bankName", "UBRR"));
        obj.add(new BasicDBObject("paymentNetworkName", "AMIGO"));
        andQuery.put("$and", obj);
        DBCursor cursor = coll.find(andQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    @Test
    public void shouldReturnSimpleAndWithBuilder() {
        BasicDBObject andQuery = new CriteriaBuilder().
                addCriteria("bankName", "UBRR").
                addCriteria("paymentNetworkName", "AMIGO").
                build("$and");

        DBCursor cursor = coll.find(andQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    @Test
    public void shouldReturnSimpleOrWithBuilder() {
        BasicDBObject andQuery = new CriteriaBuilder().
                addCriteria("bankName", "UBRR").
                addCriteria("paymentNetworkName", "AMIGO").
                build("$or");

        DBCursor cursor = coll.find(andQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }



}
