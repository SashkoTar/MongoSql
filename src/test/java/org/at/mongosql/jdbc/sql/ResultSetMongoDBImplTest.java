package org.at.mongosql.jdbc.sql;

import com.mongodb.DBCursor;
import org.at.mongosql.adaptor.MongoAdaptor;
import org.junit.Test;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by otarasenko on 3/19/15.
 */
public class ResultSetMongoDBImplTest {

    private DBCursor retrieveCursor() throws UnknownHostException {
        MongoAdaptor adapter = new MongoAdaptor();
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("name", "alex");
        return adapter.find("user", conditions);
    }

    @Test
    public void shouldReturnTrueOnNext() throws SQLException, UnknownHostException {
        ResultSet rs = new ResultSetMongoDBImpl(retrieveCursor());
        assertTrue(rs.next());
    }

    @Test
    public void shouldGetStringByName() throws SQLException, UnknownHostException {
        ResultSet rs = new ResultSetMongoDBImpl(retrieveCursor());
        rs.next();
        assertEquals("alex", rs.getString("name"));
    }

    @Test
    public void shouldGetStringByIndex() throws SQLException, UnknownHostException {
        ResultSet rs = new ResultSetMongoDBImpl(retrieveCursor());
        rs.next();
        assertEquals("alex", rs.getString(2));
    }
}
