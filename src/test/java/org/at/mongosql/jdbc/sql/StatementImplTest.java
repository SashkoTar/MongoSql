package org.at.mongosql.jdbc.sql;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

/**
 * Created by otarasenko on 3/13/15.
 */
public class StatementImplTest {


    @Test
    public void shouldReturnRows() throws SQLException {
        Statement statement = obtainStatement();
        ResultSet rs = statement.executeQuery("select * from user where  age = 45");
        assertTrue(rs.next());

    }

    private Statement obtainStatement() {
        return new StatementImpl();
    }

}
