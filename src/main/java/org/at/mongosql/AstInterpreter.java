package org.at.mongosql;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import org.antlr.runtime.Token;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by otarasenko on 3/12/15.
 */
public class AstInterpreter {

    DataSourceAdaptor adaptor;


    public AstInterpreter() {
        try{
            adaptor = new MongoAdaptor();
        }catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private void log(String info) {
        System.out.println(info);
    }

    private void printResult(DBCursor cursor) {
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void select(String collectionName, List columnNames, BasicDBObject criteria) {
        DBCursor cursor = adaptor.find(collectionName, criteria);
        printResult(cursor);
    }

    public BasicDBObject handleAndCondition(List<BasicDBObject> criterias) {
        CriteriaBuilder builderOr = new CriteriaBuilder();
        for (BasicDBObject condition : criterias) {
            builderOr.addCriteria(condition);
        }
        return builderOr.build("$and");
    }

    public BasicDBObject handleOrCondition(List<BasicDBObject> criterias) {
            CriteriaBuilder builderOr = new CriteriaBuilder();
            for (BasicDBObject condition : criterias) {
                builderOr.addCriteria(condition);
            }
            return builderOr.build("$or");
    }


    public BasicDBObject handleBasicCriteria(String id, String operator, Object value) {
        if(operator.equals(">")) {
            return new BasicDBObject(id, new BasicDBObject("$gt", value));
        }
        if(operator.equals(">=")) {
            return new BasicDBObject(id, new BasicDBObject("$gte", value));
        }
        if(operator.equals("<")) {
            return new BasicDBObject(id, new BasicDBObject("$lt", value));
        }
        if(operator.equals("<=")) {
            return new BasicDBObject(id, new BasicDBObject("$lte", value));
        }
        if(operator.equals("<>")) {
            return new BasicDBObject(id, new BasicDBObject("$ne", value));
        }
        return new BasicDBObject(id, value);
    }

    private BasicDBObject handleColumns(List<Token> columns) {
        BasicDBObject fields = new BasicDBObject();
        if(columns.size() == 1 && columns.get(0).getText().equals("*")) {
            return fields;
        }
        for(Token field : columns) {
            fields.append(field.getText(), 1);
        }
        return fields;
    }
}
