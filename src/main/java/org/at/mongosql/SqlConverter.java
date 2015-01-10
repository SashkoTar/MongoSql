package org.at.mongosql;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;

/**
 * Created by oleksandr.tarasenko on 12/24/2014.
 */
public class SqlConverter {
    public BasicDBObject handle(List<List<BasicCriteria>> searchList) {
        BasicDBObject result = new BasicDBObject();
        for(List<BasicCriteria> criterias : searchList) {
            for(BasicCriteria criteria : criterias) {
                result.put(criteria.getColumn(), criteria.getValue());
            }
        }
        return result;
    }
}
