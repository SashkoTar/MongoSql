package org.at.mongosql.jdbc.sql;

import com.mysql.jdbc.StringUtils;
import org.at.mongosql.jdbc.sql.util.Util;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by otarasenko on 3/27/15.
 */
public class Driver implements java.sql.Driver {
    private static final String URL_PREFIX = "jdbc:mongodb://";
    private static final String HOST_PROPERTY = "host";
    private static final String PORT_PROPERTY = "port";
    private static final String DATABASE_PROPERTY = "database";


    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (url != null) {
            if (StringUtils.startsWithIgnoreCase(url, URL_PREFIX)) {

                Properties props = Util.parseUrl(url);
                String host = props.getProperty(HOST_PROPERTY);
                int port = props.getProperty(PORT_PROPERTY) != null ? Integer.valueOf(props.getProperty(PORT_PROPERTY)) : 27017;
                String database = props.getProperty(DATABASE_PROPERTY);
                return new MongoSqlConnection(host, port, database);
            }
        }
        return null;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
