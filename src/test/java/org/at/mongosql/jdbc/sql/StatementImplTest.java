package org.at.mongosql.jdbc.sql;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by otarasenko on 3/13/15.
 */
public class StatementImplTest {

    DB db;

    @Before
    public void initDbCollection() throws UnknownHostException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        db = mongo.getDB("sqlTest");
        putDataToCollection("roma", 45);
        putDataToCollection("igor", 45);
    }

    @After
    public void dropDbCollection() {
        DBCollection table = db.getCollection("user");
        table.drop();
    }

    private void putDataToCollection(String name, int age) {
        DBCollection table = db.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("age", age);
        document.put("createdDate", new Date());
        table.insert(document);
    }


    @Test
    public void shouldReturnRows() throws SQLException, UnknownHostException {
        Statement statement = new StatementImpl(new MongoAdaptor());
        ResultSet rs = statement.executeQuery("select * from user where  age = 45");
        assertTrue(rs.next());
    }



}
