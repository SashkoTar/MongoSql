package org.at.mongosql.adaptor;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by oleksandr.tarasenko on 12/15/2014.
 */
public interface DataSourceAdaptor {

    public DBCursor find(String collection);

    public void put(String collection, Map<String, Object> data);

    public  DBCursor find(String collection, Map<String, Object> conditions);


    public  DBCursor find(String collection, BasicDBObject conditions, BasicDBObject fields);


}
