package org.at.mongosql.jdbc.sql;

import com.mongodb.*;
import org.at.mongosql.MongoMockDataProvisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by otarasenko on 3/27/15.
 */
public class DriverTest  {
    private static final String DRIVER_CLASS = "org.at.mongosql.jdbc.sql.Driver";

    MongoMockDataProvisor provisor;

    @Before
    public void initDbCollection() throws UnknownHostException {
        provisor = MongoMockDataProvisor.newInstance();
        provisor.putDataToCollection("roma", 45);
        provisor.putDataToCollection("igor", 45);
    }

    @After
    public void dropDbCollection() {
       provisor.dropDbCollection();
    }

    @Test
    public void shouldReturnConnection() throws ClassNotFoundException, SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Class.forName(DRIVER_CLASS);
        // Setup the connection with the DB
        Connection connect = DriverManager
                .getConnection("jdbc:mongodb://localhost:27017/sqlTest");
        assertNotNull(connect);
    }

    @Test
    public void shouldReturnResultSet() throws ClassNotFoundException, SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        Class.forName(DRIVER_CLASS);
        Connection connect = DriverManager
                .getConnection("jdbc:mongodb://localhost:27017/sqlTest");
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("select * from user where  age = 45");
        assertTrue(rs.next());
    }

}
