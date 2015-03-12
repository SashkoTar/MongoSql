package org.at.mongosql;

import com.mongodb.BasicDBObject;

import java.util.List;

/**
 * Created by otarasenko on 3/12/15.
 */
public class AstInterpreter {

    private void log(String info) {
        System.out.println(info);
    }

    public void select(String ID, List columnNames) {
        System.out.println(ID);
        for(Object name : columnNames) {
            System.out.println(name);

        }
    }

    public BasicDBObject handleAndCondition(List criterias) {
        log("And handling");
        for(Object name : criterias) {
            System.out.println(name);
        }
        return new BasicDBObject();
    }

    public BasicDBObject handleOrCondition(List criterias) {
        log("Or handling");
        for(Object name : criterias) {
            System.out.println(name);
        }
        return new BasicDBObject();
    }

    public BasicDBObject handleBasicCriteria(String id, String operator, String value) {
        log("Preparing DBObject For String");
        return new BasicDBObject();
    }

    public BasicDBObject handleBasicCriteria(String id, String operator, int value) {
        log("Preparing DBObject For Int");
        return new BasicDBObject();
    }
}
