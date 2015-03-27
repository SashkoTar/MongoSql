package org.at.mongosql.jdbc.sql.util;

import java.util.Properties;

/**
 * Created by otarasenko on 3/27/15.
 */
public class Util {

    private static final String HOST_PROPERTY = "host";
    private static final String PORT_PROPERTY = "port";
    private static final String DATABASE_PROPERTY = "database";

    //TODO Investigate if DFA can be used as url parser
    public static Properties parseUrl(String url) {
        Properties urlProps = new Properties();

        int beginningOfSlashes = url.indexOf("//");
        url = url.substring(beginningOfSlashes + 2);
        String host = url.substring(0, url.indexOf("/"));
        if(isPortProvided(host)) {
            urlProps.put(HOST_PROPERTY, host.substring(0, host.indexOf(":")));
            urlProps.put(PORT_PROPERTY, host.substring(host.indexOf(":") + 1));
        } else {
            urlProps.put(HOST_PROPERTY, host);
        }
        String database = url.substring(url.indexOf("/") + 1);
        if (database != null) {
            urlProps.put(DATABASE_PROPERTY, database);
        }

        return urlProps;
    }

    private static boolean isPortProvided(String host) {
        return host.indexOf(":") > 0;
    }

}
