package org.at.mongosql.jdbc.sql;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by otarasenko on 3/19/15.
 */
public class ResultSetMongoDBImplTest {

    private DBCursor mockCursor() {
        DBObject object1 = (DBObject) JSON
                .parse("{ '_id' : { '$oid' : '5512c613319144c5159af5f6'} , 'name' : 'alex' , 'age' : 30 , 'createdDate' : { '$date' : 1427293715017} , 'active' : true, 'weight' : 34.55}");
        DBObject object2 = (DBObject) JSON
                .parse("{'_id' : { '$oid' : '5257df5a24acaf6fdffb9163'} , 'name':'igor', 'age':22}");
        DBCursor myMockCursor = mock(DBCursor.class);
        Mockito.when(myMockCursor.next()).thenReturn(object1).thenReturn(object2).thenReturn(null);
        Mockito.when(myMockCursor.hasNext()).thenReturn(true).thenReturn(false);
        return myMockCursor;
    }

    private ResultSet getFilledResultSet() throws SQLException {
        ResultSet rs = new ResultSetMongoDBImpl(mockCursor());
        rs.next();
        return rs;
    }

    @Test
    public void shouldReturnTrueOnNext() throws SQLException, UnknownHostException {
        ResultSet rs = new ResultSetMongoDBImpl(mockCursor());
        assertTrue(rs.next());
    }

    @Test
    public void shouldGetStringByName() throws SQLException, UnknownHostException {
        assertEquals("alex", getFilledResultSet().getString("name"));
    }

    @Test
    public void shouldGetStringByIndex() throws SQLException, UnknownHostException {
        assertEquals("alex", getFilledResultSet().getString(2));
    }

    @Test
    public void shouldGetIntByName() throws SQLException, UnknownHostException {
        assertEquals(30, getFilledResultSet().getInt("age"));
    }

    @Test
    public void shouldGetIntByIndex() throws SQLException, UnknownHostException {
        assertEquals(30, getFilledResultSet().getInt(3));
    }

    @Test
    public void shouldGetDateByName() throws SQLException, UnknownHostException {
        assertEquals(25, getFilledResultSet().getDate("createdDate").getDate());
    }

    @Test
    public void shouldGetDateByIndex() throws SQLException, UnknownHostException {
        assertEquals(25, getFilledResultSet().getDate(4).getDate());
    }

    @Test
    public void shouldGetBooleanByName() throws SQLException, UnknownHostException {
        assertTrue(getFilledResultSet().getBoolean("active"));
    }

    @Test
    public void shouldGetBooleanByIndex() throws SQLException, UnknownHostException {
        assertTrue(getFilledResultSet().getBoolean(5));
    }

    @Test
    public void shouldGetDoubleByName() throws SQLException, UnknownHostException {
        assertEquals(34.55, getFilledResultSet().getDouble("weight"), 0.0001);
    }

    @Test
    public void shouldGetDoubleByIndex() throws SQLException, UnknownHostException {
        assertEquals(34.55, getFilledResultSet().getDouble(6), 0.0001);
    }
}
