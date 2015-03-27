package org.at.mongosql.jdbc.sql;

import org.junit.Test;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by otarasenko on 3/27/15.
 */
public class DriverTest  {


    @Test
    public void shouldReturnConnection() throws ClassNotFoundException, SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out));

        Class.forName("org.at.mongosql.jdbc.sql.Driver");
        // Setup the connection with the DB
        Connection connect = DriverManager
                .getConnection("jdbc:mongodb://localhost:27017/sqlTest");
        assertNotNull(connect);
    }

}
