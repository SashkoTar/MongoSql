package org.at.mongosql.jdbc.sql.util;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by otarasenko on 3/27/15.
 */
public class UtilTest {
    private static final String HOST_PROPERTY = "host";
    private static final String PORT_PROPERTY = "port";
    private static final String DATABASE_PROPERTY = "database";

    @Test
    public void shouldExtractHost() {
        Properties prop = Util.parseUrl("jdbc:mongodb://localhost:27017/sqlTest");
        assertTrue(prop.containsKey(HOST_PROPERTY));
        assertEquals("localhost", prop.getProperty(HOST_PROPERTY));
    }

    @Test
    public void shouldExtractHostWithDefaultPort() {
        Properties prop = Util.parseUrl("jdbc:mongodb://localhost/sqlTest");
        assertTrue(prop.containsKey(HOST_PROPERTY));
        assertEquals("localhost", prop.getProperty(HOST_PROPERTY));
    }

    @Test
    public void shouldExtractPort() {
        Properties prop = Util.parseUrl("jdbc:mongodb://localhost:27017/sqlTest");
        assertTrue(prop.containsKey(PORT_PROPERTY));
        assertEquals("27017", prop.getProperty(PORT_PROPERTY));
    }

    @Test
    public void shouldExtractDatabaseName() {
        Properties prop = Util.parseUrl("jdbc:mongodb://localhost:27017/sqlTest");
        assertTrue(prop.containsKey(PORT_PROPERTY));
        assertEquals("sqlTest", prop.getProperty(DATABASE_PROPERTY));
    }

}
